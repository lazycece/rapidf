---
name: rapidf-dispatcher
description: 当用户需要创建六边形架构适配器、搭建服务分发器、实现 CommandHandler 或 QueryHandler、构建门面端点、或使用 @ServiceHandler/@FacadeService 注解时使用。覆盖两种分发模式，含完整代码模板与自动发现机制说明。
metadata:
  author: lazycece
  version: "3.0.1"
---

# rapidf-dispatcher — 六边形架构入站适配器

两种分发模式，通过 `rapidf.dispatcher.pattern` 配置切换。核心入口：`com.lazycece.rapidf.dispatcher.core.Dispatcher.dispatch(DispatchCmd)`。

核心约束：
- **两种模式都需要用户提供一个 `DispatchRequestParser` Bean**——框架不内置 JSON/XML 解析。
- **`@ServiceHandler` 和 `@FacadeService` 的 `name` + `version` 组合构成唯一分发键**——Facade 模式额外需要 `action`（对应接口方法名）。
- **自动发现依赖 `BeanPostProcessor` + 泛型反射**——Handler 必须直接实现 `Handler<R, Q>` 接口（不能通过抽象父类间接实现导致泛型信息丢失）。
- **校验默认开启**（`rapidf.dispatcher.validate-request=true`），依赖 Jakarta Validation。

---

## 模式对比

| | Service Handler | Facade Service |
|---|---|---|
| 配置 | `pattern=service` | `pattern=facade` |
| 粒度 | 一个类一个操作 | 一个接口多个操作 |
| 注解 | `@ServiceHandler(name, version)` | `@FacadeService(name, version)` |
| 接口 | `Handler<R,Q>` / `CommandHandler` / `QueryHandler` | 自定义接口 `extends Facade` |
| 分发键 | `name + version` | `name + version + action` |
| 注册方式 | 反射提取泛型 Request 类型 | 反射接口方法签名 |

---

## Service Handler 模式

### 配置

```yaml
rapidf:
  dispatcher:
    pattern: service
    validate-request: true
```

### Handler 实现

```java
@Component
@ServiceHandler(name = "order_create", version = "1.0.0")
public class OrderCreateCommandHandler
        implements CommandHandler<OrderCreateResponse, OrderCreateRequest> {

    @Override
    public OrderCreateResponse handle(OrderCreateRequest request) {
        OrderCreateResponse resp = new OrderCreateResponse();
        resp.setOrderId(UUID.randomUUID().toString());
        return resp;
    }
}
```

写操作实现 `CommandHandler<R, C>`，读操作实现 `Handler<R, Q>`（`QueryHandler` 同理）。泛型第一个参数是响应类型，第二个是请求类型。

### REST 适配器

```java
@RestController
@RequestMapping("/service")
public class ServiceHandlerAdapter {
    @Autowired private Dispatcher dispatcher;

    @PostMapping("/handle")
    public RespData<?> handle(@RequestBody @Validated ServiceRequest request) {
        ServiceCmd cmd = new ServiceCmd();
        cmd.setName(request.getName());
        cmd.setVersion(request.getVersion());
        cmd.setRequest(request.getRequestData());
        return RespData.success(dispatcher.dispatch(cmd));
    }
}
```

### DispatchRequestParser（必须提供）

```java
@Component
public class CustomDispatchRequestParser implements DispatchRequestParser {
    @Override
    public <T> T parse(String requestData, Class<T> clazz) {
        return JsonUtils.parseObject(requestData, clazz);  // 使用项目中的 JSON 工具
    }
}
```

---

## Facade Service 模式

### 门面接口

接口方法只接收一个参数（请求 DTO），返回响应 DTO：

```java
public interface OrderCommandFacade extends Facade {
    OrderCreateResponse create(OrderCreateRequest request);
    OrderCancelResponse cancel(OrderCancelRequest request);
}
```

### 门面实现

```java
@Service
@FacadeService(name = "order", version = "1.0.0")
public class OrderCommandFacadeImpl implements OrderCommandFacade {
    @Override
    public OrderCreateResponse create(OrderCreateRequest request) { /* ... */ }
    @Override
    public OrderCancelResponse cancel(OrderCancelRequest request) { /* ... */ }
}
```

### REST 适配器（多了 `action` 字段）

```java
@PostMapping("/handle")
public RespData<?> handle(@RequestBody @Validated FacadeRequest request) {
    FacadeCmd cmd = new FacadeCmd();
    cmd.setName(request.getName());
    cmd.setVersion(request.getVersion());
    cmd.setAction(request.getAction());  // 映射到接口方法名
    cmd.setRequest(request.getRequestData());
    return RespData.success(dispatcher.dispatch(cmd));
}
```

---

## 全局异常处理

两个模式通用——继承 `RespDataExceptionHandler`（返回 `RespData`）或 `RespMapExceptionHandler`（返回 `RespMap`）：

```java
public class GlobalExceptionHandler extends RespDataExceptionHandler {
    // BindException → ParamException(701)
    // ValidationException → ParamException(701)
    // AbstractBaseException → 提取 Status + message
    // Exception → ServerException(500)
}
```

---

## 自动发现原理（调用方不需要关心，但有助于排查）

- `ServiceDispatcher`（`BeanPostProcessor.postProcessAfterInitialization`）：扫 `@ServiceHandler` + `Handler` 接口，`ParameterizedType` 反射取泛型 Request 类型。注册 key = `"name_version"`。
- `FacadeDispatcher`：扫 `@FacadeService`，反射自定义接口中**只有一个参数的方法**。注册 key = `"name_version_action"`。

---

## Gotchas

1. **Handler 必须直接实现 `Handler<R, Q>` 接口**——如果通过抽象父类间接实现，泛型信息可能被擦除，`ServiceHelper` 的 `ParameterizedType` 反射会失败。如果确实需要抽象父类，确保泛型参数在父类中声明为具体类型参数（而非在子类中才绑定）。
2. **Facade 接口的方法必须正好一个参数**——`FacadeHelper` 过滤条件：方法有 `@Override` 注解 + 参数个数 = 1（定义在 `DispatcherConstants.FACADE_ACTION_PARAMETER_LEN`）。
3. **`DispatchRequestParser.parse()` 的 `clazz` 参数是反射出来的请求类型**——用 JSON 反序列化时直接 `parseObject(requestData, clazz)` 即可，不需要手动判断类型。
4. **校验在 `ValidateHelper.validate()` 中执行**——使用 `jakarta.validation.Validator`，校验失败抛 `ValidationException`（由 `RespDataExceptionHandler` 转换为 `ParamException(701)`）。

---

## 检查清单

- [ ] `rapidf.dispatcher.pattern` 已配置
- [ ] Handler/Facade 的 `name` + `version` 已填写且全局唯一
- [ ] `DispatchRequestParser` Bean 存在
- [ ] 全局异常处理器继承 `RespDataExceptionHandler` 或 `RespMapExceptionHandler`
- [ ] Facade 接口方法只有一个参数
- [ ] Handler 直接实现 `Handler` 接口（泛型信息不丢失）

---

## 参考
- 框架源码：https://github.com/lazycece/rapidf/releases ，框架发布版本与当前skill版本对应
- 核心：`rapidf-components/rapidf-dispatcher/src/main/java/com/lazycece/rapidf/dispatcher/core/`
- 辅助类：`rapidf-components/rapidf-dispatcher/src/main/java/com/lazycece/rapidf/dispatcher/helper/`（`ServiceHelper`、`FacadeHelper`、`ValidateHelper`）
- 示例：`rapidf-samples/rapidf-dispatcher-sample/` — 两种模式的完整实现
