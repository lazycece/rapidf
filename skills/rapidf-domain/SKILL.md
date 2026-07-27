---
name: rapidf-domain
description: 当用户需要用 DDD 建模领域、实现状态机（状态驱动生命周期）、发布或处理领域事件、分离命令与查询（CQRS）、或使用 DDD 分层注解（@DomainService、@DomainRepository 等）时使用。覆盖状态机、领域事件（含 SpEL 匹配规则）、CQRS 契约和领域模型基类。
metadata:
  author: lazycece
  version: "3.0.1"
---

# rapidf-domain — DDD 建模组件

三个独立子系统：**状态机**、**领域事件**、**CQRS 契约**，外加**领域模型基类**和**分层注解**。每个子系统可单独使用。

---

## 领域模型基类

- `Entity<ID>` — 定义领域实体时使用，内含审计字段（creator、updater、createTime、updateTime、deleted）+ `Identity<ID>`（`ID getId()`）
- `Aggregate<ID>` — 定义领域聚合时使用，结构同 Entity，语义区别。
- `BaseEnum<T>` — 定义枚举时使用，`T getCode()` + `String getDesc()`，枚举契约。
- `Pagination` — 分页查询时使用，字段 page/size/count。

---

## 状态机

### 核心 API

| 接口/类 | 作用 |
|---------|------|
| `State<T>` extends `BaseEnum<T>` | 状态枚举 |
| `StateEvent<T>` extends `BaseEnum<T>` | 事件枚举 |
| `StateApply` | `State<?> getState()` — 实体需实现此接口 |
| `AbstractTransition(event, sourceState, targetState)` | 转移——构造函数绑定三者 |
| `AbstractStateMachine(List<Transition>)` | 状态机——内部构建 `Map<sourceState, List<Transition>>` |
| `StateMachine.execute(StateApply, StateEvent)` → `State<?>` | 查找匹配转移 → 执行 → 返回目标状态 |

### 完整示例

```java
// 1. 状态枚举
public enum AuditStatus implements State<String> {
    DRAFT("DRAFT", "草稿"),
    AUDITING("AUDITING", "审核中"),
    PASSED("PASSED", "已通过");
    private final String code, desc;
    AuditStatus(String code, String desc) { this.code = code; this.desc = desc; }
    @Override public String getCode() { return code; }
    @Override public String getDesc() { return desc; }
}

// 2. 实体实现 StateApply
public class Goods extends Entity<String> implements StateApply {
    private AuditStatus status;
    @Override public State<?> getState() { return status; }
    public void setState(State<?> state) { this.status = (AuditStatus) state; }
}

// 3. 转移——构造函数绑定 (event, source, target)
public class AuditSubmitTransition extends AbstractTransition {
    public AuditSubmitTransition() {
        super(AuditEvent.SUBMIT_AUDIT, AuditStatus.DRAFT, AuditStatus.AUDITING);
    }
    @Override
    public void execute(StateApply apply) {
        Goods goods = (Goods) apply;
        // 转移特有的副作用
    }
}

// 4. 状态机
public class GoodsAuditStateMachine extends AbstractStateMachine {
    public GoodsAuditStateMachine(List<AbstractGoodsAuditStateTransition> transitions) {
        super(transitions);
    }
    // 事件枚举通常作为状态机的内部类
    public enum AuditEvent implements StateEvent<String> {
        SUBMIT_AUDIT("SUBMIT_AUDIT", "提交审核"),
        AUDIT_PASS("AUDIT_PASS", "审核通过"),
        AUDIT_REJECT("AUDIT_REJECT", "审核拒绝");
        private final String code, desc;
        AuditEvent(String c, String d) { code = c; desc = d; }
        @Override public String getCode() { return code; }
        @Override public String getDesc() { return desc; }
    }
}

// 5. 执行
State<?> newState = stateMachine.execute(goods, AuditEvent.SUBMIT_AUDIT);
```

### Gotchas

- **`AbstractStateMachine.execute()` 对未匹配的转移是静默的**——如果 `sourceState` 下找不到匹配的 `StateEvent`，不会抛异常，而是直接返回**传入时的当前状态**（即 `apply.getState()`）。这意味着非法转移不会报错，只是状态不变。如需严格校验，在调用方做断言。
- **Transition 的 `sourceState` 必须精确匹配**——不是"当前状态是 sourceState 的子类"这种宽松匹配，而是 `equals()` 精确匹配。

---

## 领域事件

### 发布

```java
@Autowired private DomainEventPublisher publisher;

DomainEvent event = DomainEventBuilder.builder()
    .type(OrderDomainEvent.class.getName())  // type 是全类名
    .source("local")
    .version("1.0.0")                        // 默认 "1.0.0"
    .data(orderData)
    .extension("tag1", true)
    .extension("tag2", true)
    .build();

publisher.publish(event);  // 先 store，再 dispatch
```

### 处理

```java
@Order(3)
@EventHandler(
    type = "com.example.OrderDomainEvent",
    source = "local",
    version = "1.0.0",
    extension = {"tag1", "tag2"},
    expression = "{'success'.equals(data.status)}"
)
public class OrderSuccessEventHandler implements DomainEventHandler {

    @Override
    public void handle(DomainEvent event) {
        OrderDomainEvent data = event.getData(OrderDomainEvent.class);
        // 处理逻辑
    }
}
```

`@EventHandler` 各属性都是可选的（`type` 除外），用于缩小匹配范围。未填的属性不做过滤。

### 自定义事件存储

```java
@Component  // 必须 — DefaultDomainEventPublisher 通过 BeanPostProcessor 自动检测
public class CustomDomainEventStore implements DomainEventStore {
    @Override public void append(DomainEvent event) { /* 持久化 */ }
    @Override public List<DomainEvent> load(String type, String identity) { /* 查询 */ }
    @Override public List<DomainEvent> load(String type, String identity, int offset, int count) { /* 分页 */ }
}
```

### 自动发现与匹配机制

`DefaultDomainEventPublisher`（`BeanPostProcessor`）：
1. 扫描所有带 `@EventHandler` + 实现 `DomainEventHandler` 的 Bean
2. 扫描所有实现 `DomainEventStore` 的 Bean（替代默认的 `DefaultDomainEventStore`）
3. 发布时：先 `store.append(event)`，再 `dispatcher.publish(event)`（`DefaultDomainEventDispatcher` 做内存匹配）

### Gotchas

1. **`@EventHandler(expression=...)` 中的 SpEL 访问 payload 字段用 `data.xxx`**——上下文根是 `DomainEvent` 对象本身。要访问业务数据，写 `data.status`，不是 `status`。源码验证：`DefaultDomainEventDispatcher` 使用 `SpelExpressionParser` 对 `DomainEvent` 对象求值。
2. **`expression` 使用单引号包裹字符串**——SpEL 语法：`{'success'.equals(data.status)}`，不是 `{"success".equals(data.status)}`。
3. **事件 handler 是无序通知**——虽然 `@Order` 控制执行顺序，但不支持"前一个 handler 失败则后续不执行"的语义。每个 handler 独立执行。
4. **`DefaultDomainEventStore` 的 `load()` 会抛异常**——不自定义 store 时，事件只做内存分发，不做持久化和查询。如果需要事件溯源或重放，必须提供自定义 `DomainEventStore`。

---

## CQRS 契约

纯标记接口，无内置分发机制（配合 rapidf-dispatcher 或其他分发器使用）：

```java
public class CreateOrderCmd implements Command { }
public class OrderQuery implements Query { }

// Handler 契约
public interface CommandHandler<R, C extends Command> { R handle(C cmd); }
public interface QueryHandler<R, Q extends Query> { R handle(Q query); }
```

> 注意区分：`rapidf-domain.cqrs.CommandHandler` 和 `rapidf-dispatcher.core.service.CommandHandler` 是两个不同的接口。前者是纯 DDD 标记，后者是 dispatcher 的具体分发契约。如果同时引入两个模块，注意 import 正确的包。

---

## 分层注解

**运行时标记**（也是 `@Component` 别名，自动注册 Spring Bean）：

| 注解 | 等价于 | 所属层 |
|------|--------|--------|
| `@DomainService` | `@Component` | 领域层 |
| `@DomainRepository` | `@Component` | 领域层 |
| `@ApplicationService` | `@Component` | 应用层 |
| `@ApplicationHandler` | `@Component` | 应用层 |
| `@InfrastructureService` | `@Component` | 基础设施层 |

**纯文档标记**（CLASS 保留，无运行时行为）：`@DomainEntity`、`@DomainAggregate`、`@ValueObject`、`@DomainFactory`、`@DomainLayer`、`@ApplicationLayer`、`@AdapterLayer`、`@InfrastructureLayer`。

自动装配入口：`DomainAutoConfiguration` — `@ComponentScan(basePackages = "com.lazycece.rapidf.domain")`。

---

## 检查清单

- [ ] State 和 StateEvent 均实现 `BaseEnum<T>`（code + desc）
- [ ] 实体实现 `StateApply.getState()` 返回正确的当前状态
- [ ] Transition 构造函数绑定了正确的 `(event, source, target)`
- [ ] `@EventHandler(expression=...)` 使用 `data.field` 访问 payload + 单引号包裹字符串
- [ ] 需要事件持久化时提供了自定义 `DomainEventStore`（`@Component`）
- [ ] 注意区分 domain 和 dispatcher 各自的 `CommandHandler` 接口

---

## 参考

- 框架源码：https://github.com/lazycece/rapidf/releases ，框架发布版本与当前skill版本对应
- 状态机：`rapidf-components/rapidf-domain/src/main/java/com/lazycece/rapidf/domain/statemachine/`
- 事件：`rapidf-components/rapidf-domain/src/main/java/com/lazycece/rapidf/domain/event/`
- 示例：`rapidf-samples/rapidf-domain-sample/` — `GoodsAuditStateMachine`、`DomainEventSample`、`OrderSuccessEventHandler`
