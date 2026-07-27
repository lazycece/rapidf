---
name: rapidf-restful
description: 当用户需要标准化 REST API 响应格式、使用 RespData/RespMap、配置全局异常处理、添加断言守卫、或使用 Rapidf 异常体系时使用。覆盖响应类型、全部异常类、Assert 工具和全局异常处理器基类。
metadata:
  author: lazycece
  version: "3.0.1"
---

# rapidf-restful — REST API 标准化

三个核心工具：**统一响应体**（`RespData`/`RespMap`）、**异常体系**（`AbstractBaseException` 子类 + 全局异常处理器handler）、**断言守卫**（`Assert`）。三者独立可用。

---

## 响应体

### RespData<T> — 有固定类型时

```java
// 成功
RespData.success(orderData);            // code=200, body=orderData
RespData.success();                     // code=200, body=null

// 失败
RespData.fail();                        // code=700 (FAIL)
RespData.fail("reason");
RespData.fail(RespStatus.PARAM_ERROR, "name required");

// 从 Status
RespData.status(RespStatus.NEED_TO_RETRY);

// 运行时检查
respData.isSuccess();    // code == 200
respData.isNeedRetry();  // code == 704
```

JSON 输出：`{"code":200,"message":null,"body":{...},"traceId":null,"success":true}`

### RespMap — 动态 body 时

`RespMap extends HashMap<String, Object>`，支持流式 `.putting(key, value)`：

```java
RespMap.success("hello");
RespMap.success().putting("orderId", id).putting("status", "CREATED");
RespMap.fail("reason");
```

### 关键约束

- **Controller 不要返回裸对象**——始终包装为 `RespData<T>` 或 `RespMap`。
- **`RespData.fail()` 不带参时 message 为 null**——需要用户可读的错误信息时用 `fail("message")` 或 `fail(status, "message")`。

---

## 异常体系

```
AbstractBaseException (abstract)
├── AuthException          → 100 (AUTH_FAIL)
├── ClientException        → 400 (CLIENT_ERROR)
├── ParamException         → 701 (PARAM_ERROR)
├── BusinessException      → 700 (FAIL)
├── ServerException        → 500 (INTERNAL_SERVER_ERROR)
├── IntegrationException   → 600 (INTEGRATION_ERROR)
├── UserBizException       → 705 (USER_BIZ_FAIL)
├── CommonException        → Status 可配置
└── AssertException        → Status 可配置（由 Assert.* 抛出）
```

所有异常通过 `getMessage()` 返回 `"Status.toString()|用户消息"` 格式。全局异常 handler 按 `|` 分割提取用户消息。

工厂类 `ExceptionFactory` 提供静态方法快速创建：

```java
ExceptionFactory.paramException("name required");
ExceptionFactory.businessException("insufficient inventory");
```

---

## 全局异常处理

两个基类模板，继承即可：

```java
// 返回 RespData<?>
public class GlobalExceptionHandler extends RespDataExceptionHandler {
    // BindException → ParamException(701)
    // ValidationException → ParamException(701)
    // AbstractBaseException → 提取 Status + message
    // Exception → ServerException(500)
}

// 返回 RespMap
public class GlobalExceptionHandler extends RespMapExceptionHandler { }
```

不需要重写任何方法——基类已经处理了所有常见异常类型。

---

## Assert 守卫

`Assert` 全部为静态方法，失败时抛 `AssertException`（携带指定的 `Status` + 格式化消息）。消息格式使用 `{}` 占位符（SLF4J 风格）：

```java
// 空值检查
Assert.notNull(user, RespStatus.PARAM_ERROR, "user not found: id={}", userId);
Assert.notBlank(name, RespStatus.PARAM_ERROR, "name required");
Assert.notEmpty(items, RespStatus.PARAM_ERROR, "items required");

// 布尔守卫
Assert.isTrue(amount > 0, RespStatus.PARAM_ERROR, "amount must be > 0: {}", amount);
Assert.isFalse(locked, RespStatus.FAIL, "resource locked");

// 比较
Assert.greater(amount, 0, RespStatus.PARAM_ERROR, "amount must be > 0");
Assert.less(amount, 100, RespStatus.PARAM_ERROR, "amount must be < 100");
Assert.greaterOrEqual(age, 18, RespStatus.PARAM_ERROR, "age must be >= 18");
Assert.lessOrEqual(count, limit, RespStatus.FAIL, "exceeded limit");

// 相等 & 类型
Assert.equal(status, "ACTIVE", RespStatus.FAIL, "invalid status: {}", status);
Assert.assignableFrom(clazz, MyInterface.class, RespStatus.FAIL, "type mismatch");
```

---

## RespStatus 状态码速查

| 范围 | Family | 关键常量 |
|------|--------|----------|
| 1xx | AUTH | AUTH_FAIL(100)、AUTH_TOKEN_FAIL(101)、AUTH_SIGN_FAIL(102)、AUTH_PARAM_FAIL(103) |
| 2xx | SUCCESS | SUCCESS(200) |
| 4xx | CLIENT | CLIENT_ERROR(400)、ACCESS_DENIED(403)、SERVICE_NOT_FOUND(404) |
| 5xx | SERVER | INTERNAL_SERVER_ERROR(500)、DB_EXCEPTION(501) |
| 6xx | INTEGRATION | INTEGRATION_ERROR(600) |
| 7xx | FAIL | FAIL(700)、PARAM_ERROR(701)、DATA_NOT_EXIST(702)、DATA_STATUS_ERROR(703)、NEED_TO_RETRY(704)、USER_BIZ_FAIL(705) |

---

## Gotchas

1. **`Assert.*` 的格式化参数是惰性求值的**——使用 `{}` 占位符，底层用 `MessageFormatter.arrayFormat()`。不要把字符串拼接放在参数里（`"error: " + expensive()`），应当用 `"error: {}"` 占位。
2. **`RespData.fail()` vs `RespData.fail(RespStatus.FAIL)` 的区别**——无参版本 `code=700`，message=null。无 code 单参版本 `RespData.fail("msg")` 也是 `code=700`，message="msg"。
3. **`RespDataExceptionHandler` 中 `AbstractBaseException.getMessage()` 的解析依赖 `|` 分隔符**——自定义异常的 `getMessage()` 如果覆写为非标准格式，全局 handler 会解析失败，直接将整个 message 作为用户消息（Status 信息会泄露给客户端）。
4. **`ParamException` 和 Jakarta `ValidationException` 是不同的**——后者由 `@Validated` 触发，全局 handler 转换为 `ParamException(701)` 处理。不要在代码中混用。

---

## 检查清单

- [ ] Controller 返回 `RespData<T>` 或 `RespMap`，不返回裸对象
- [ ] 全局异常处理器已继承基类
- [ ] 守卫子句使用 `Assert.*` 且消息用 `{}` 占位符
- [ ] 自定义异常继承 `AbstractBaseException` 且不覆写 `getMessage()` 为非标准格式
- [ ] 需要参数校验的接口加 `@Validated`

---

## 参考

- 框架源码：https://github.com/lazycece/rapidf/releases ，框架发布版本与当前skill版本对应
- 异常体系：`rapidf-components/rapidf-restful/src/main/java/com/lazycece/rapidf/restful/exception/`
- 响应结构：`rapidf-components/rapidf-restful/src/main/java/com/lazycece/rapidf/restful/response/`
- 示例：`rapidf-samples/rapidf-restful-sample/` — controller、异常、校验完整示例
