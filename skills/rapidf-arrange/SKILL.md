---
name: rapidf-arrange
description: 当用户需要编排业务逻辑、用生命周期模式组织服务方法、串联多个处理器、构建过滤器-处理器管道、或使用Arranger/ProcessTemplate/PriorityHandler/ArrangeStream 时使用。覆盖 rapidf-arrange 组件的全部四种编排模式，含 API 签名与常见陷阱。
metadata:
  author: lazycece
  version: "3.0.1"
---

# rapidf-arrange — 业务逻辑编排

统一入口类 `com.lazycece.rapidf.arrange.Arranger`，四个静态方法对应四种编排模式。所有函数式接口（`Handler`、`Filter`、`Answer`、`Command`）均为 `@FunctionalInterface`，可用 lambda。

核心约束：
- **`Arranger.process(ProcessTemplate)` 的 `preHandle()` 返回 `false` 会跳过 `handle()` 和 `postHandle()`**——两者在同一个 `if` 块内。不要把必须执行的清理逻辑放在 `postHandle()` 里，用 try-finally 包裹调用。
- **PriorityHandler 的排序取决于构造方式**——手动 `new` 的 list 需显式排序；Spring `@Autowired List<X extends PriorityHandler>` 则自动按 `getOrder()` 排序（因为 `PriorityHandler` 继承 Spring 的 `PriorityOrdered`，Spring 容器会排序 `Ordered` 类型的注入列表）。
- **API 签名接受 `List<? extends PriorityHandler<C>>`**——即允许传入 `PriorityHandler` 的任意子类型列表，这是支持 Spring 自动注入子类型的基础。
- **`ArrangeStream.parallel(List<Handler>)` 内部用并行流执行**——所有并行 handler 必须线程安全且相互独立。

---

## 四种模式速查

### ProcessTemplate — 生命周期模板

`Arranger.process(ProcessTemplate<R>)` 返回 `R`。执行顺序：`checkParam()` → `preHandle()`（返回值决定是否继续）→ `handle()` → `postHandle()`。

```java
// 内联方式（非 Spring）
int result = Arranger.process(new ProcessTemplate<>() {
    @Override public void checkParam() {
        if (bound <= 5) throw new ParamException("bound must be > 5");
    }
    @Override public Integer handle() {
        return new Random().nextInt(bound);
    }
});

// Spring Bean 方式：继承 AbstractTemplateHandler<Context>
Arranger.process(context, new AbstractTemplateHandler<SampleContext>() {
    @Override protected boolean preHandle(SampleContext ctx) { return /* gate */; }
    @Override protected void doHandle(SampleContext ctx) { /* core logic */ }
    @Override protected void postHandle(SampleContext ctx) { /* cleanup */ }
});
```

### Simple Handler — 直接调用

`Arranger.process(context, Handler<C>)` —— 最简单，无返回值，无过滤，无生命周期。

```java
Arranger.process(context, ctx -> doSomething(ctx));
```

### Priority Handler — 有序条件链

API 签名：`Arranger.process(C context, List<? extends PriorityHandler<C>>)`。顺序遍历，每个 handler 先 `accept(context)` 决定是否执行，再 `handle(context)`。`PriorityHandler<C>` 继承了 `Handler<C>`、`Filter<C>` 和 Spring 的 `PriorityOrdered`。

**手动构造方式**（非 Spring 场景）—— 需显式排序：

```java
List<PriorityHandler<MyCtx>> handlers = List.of(
    new ValidateHandler(),   // getOrder() = 1
    new ProcessHandler(),    // getOrder() = 2
    new NotifyHandler()      // getOrder() = 3
);
handlers.sort(Comparator.comparingInt(PriorityHandler::getOrder));
Arranger.process(context, handlers);
```

**Spring 自动注入方式**（推荐）—— 自定义子接口 + `@Autowired`，Spring 自动排序：

```java
// 1. 定义子接口，限定 handler 类型
interface SampleHandler<Context> extends PriorityHandler<Context> { }

// 2. 各 handler 实现该子接口并注册为 Spring Bean
@Component
public static class ValidateHandler implements SampleHandler<SampleContext> {
    @Override public boolean accept(SampleContext ctx) { return /* gate */; }
    @Override public void handle(SampleContext ctx) { /* logic */ }
    @Override public int getOrder() { return 1; }
}

// 3. 业务类中自动注入，直接使用（已按 order 排序）
@Service
public class MyService {
    @Autowired
    private List<SampleHandler<SampleContext>> handlerList;  // Spring 自动排序

    public void handle(SampleContext context) {
        Arranger.process(context, handlerList);
    }
}
```

Spring 注入的 `List<X>` 中，若 `X` 实现了 `Ordered` 接口，Spring 会自动按 `getOrder()` 升序排列。由于 `PriorityHandler` → `PriorityOrdered` → `Ordered`，注入后无需手动排序。

### Stream — 流式管道

`Arranger.stream(context)` 返回 `ArrangeStream<C>`，支持链式调用。`.filter()` → false 时终止后续处理；`.parallel()` → 并行执行；`.answer()` → 终结并提取返回值；`.end()` → 终结不返回值。

```java
Integer result = Arranger.stream(context)
    .filter(ctx -> ctx.num < 2)           // gate
    .handler(ctx -> ++ctx.num)            // sequential
    .handler(ctx -> ++ctx.num)            // sequential
    .parallel(List.of(new PlusA(), new PlusB(), new PlusC()))  // parallel
    .answer(ctx -> ctx.a + ctx.b + ctx.c); // terminal
```

---

## Gotchas

1. **`ProcessTemplate.preHandle()` 返回 `false` 时 `handle()` 不执行，但 `postHandle()` 也不执行**——源代码中 `postHandle()` 在 `if (template.preHandle())` 块内部。生命周期不是 `checkParam → preHandle → handle → postHandle`（全部执行），而是 `checkParam → preHandle → [handle → postHandle]`（后两者被 preHandle 门控）。
2. **手动构造的 List 不会自动排序**——`Arranger.process()` 内部直接用 `priorityHandlers.stream()`，不调用 `sorted()`。手动 new 的 list 必须先排序再传入。但如果用 Spring `@Autowired List<X extends PriorityHandler>`，Spring 容器会按 `Ordered` 接口排序，不需额外处理。
3. **Stream `.filter()` 返回 false 后管道终止**——不是跳过当前 handler，而是后续所有 handler 和 answer 都不会执行。
4. **`.parallel()` 后的 context 修改对不同并行 handler 可见**——确保各 handler 修改的是 context 的不同字段，避免竞态。
5. **使用`.parallel()`是需要考虑线程上下文传递参数的问题**——确保在父子线程的场景下参数传递正确。
---

## 检查清单

- [ ] ProcessTemplate：需要无条件执行的清理逻辑放在 `checkParam()` 的 try-finally 中，不依赖 `postHandle()`
- [ ] PriorityHandler：手动构造时已排序；Spring 注入时依赖容器自动排序，无需手动处理
- [ ] Stream parallel：handler 之间无共享可变状态，handler的执行需要有线程池包裹
- [ ] 所有 Handler/Filter 实现用 lambda 或正确实现对应的 `@FunctionalInterface`

---

## 参考
- 框架源码：https://github.com/lazycece/rapidf/releases ，框架发布版本与当前skill版本对应
- 源码：`rapidf-components/rapidf-arrange/src/main/java/com/lazycece/rapidf/arrange/Arranger.java`
- 示例：`rapidf-samples/rapidf-arrange-sample/` — 四种模式的完整可运行示例
  - `ArrangeHandlerSample` — Simple Handler + TemplateHandler
  - `ArrangeProcessTemplateSample` — ProcessTemplate 内联方式
  - `ArrangePriorityHandlerSample` — PriorityHandler 手动构造方式
  - `ArrangePriorityHandlerSpringSample` — PriorityHandler Spring 自动注入方式（v3.0+）
  - `ArrangeStreamSample` — Stream 流式管道
