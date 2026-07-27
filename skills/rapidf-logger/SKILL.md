---
name: rapidf-logger
description: 当用户需要配置 AOP 方法日志、记录方法调用耗时、分离摘要和明细日志、配置 LogInterceptor、使用 @Logger 注解、或自定义 LogParser/LogInfo 时使用。覆盖 rapidf-logger 组件从配置到自定义扩展的完整链路。
metadata:
  author: lazycece
  version: "3.0.1"
---

# rapidf-logger — AOP 方法日志

四个组件构成完整链：`@Logger`（标记）→ `LogInterceptor`（拦截）→ `LogParser`（结果解析）→ `LogInfo`（格式化输出）。通过 SLF4J 写出，不绑定具体日志实现。

核心约束：
- **LogInterceptor 通过 `BeanNameAutoProxyCreator` 挂载**——不是自动扫描 `@Logger` 注解，必须显式配置 `beanNames` 列表。忘记配置则日志不生效。
- **`@Logger` 的 `digestLogName` / `detailLogName` 指定的是 SLF4J Logger name**——需要在 `log4j2.xml`（或 logback）中定义同名的 Logger 和 Appender，否则日志会走默认 Logger。
- **摘要日志和明细日志使用不同的 Logger name，可以路由到不同文件**——这是该组件最有价值的特性。

---

## 日志格式

**摘要**（单行）：`[(标识)(类名.方法名,是否成功,耗时ms)(结果码)]`
示例：`[(OrderService)(OrderService.createOrder,true,45)(200)]`

**明细**（多行）：`[标识,类名.方法名][REQUEST(arg1,arg2)][RESULT(result)]`
示例：`[OrderService,OrderService.createOrder][REQUEST(req1,req2)][RESULT(OrderResponse(orderId=xxx))]`

---

## 配置

### 1. LogInterceptor + BeanNameAutoProxyCreator

```java
@Configuration
public class LoggerConfig {

    @Bean(name = "logInterceptor")
    public LogInterceptor logInterceptor() {
        LogInterceptor interceptor = new LogInterceptor();
        // 自定义结果解析（按需）
        interceptor.setLogParser(new CustomLogParser());
        // 自定义日志格式（按需）
        interceptor.setLogInfoClass(CustomLogInfo.class);
        return interceptor;
    }

    @Bean
    public static BeanNameAutoProxyCreator beanNameAutoProxyCreator() {
        BeanNameAutoProxyCreator creator = new BeanNameAutoProxyCreator();
        creator.setBeanNames("logController", "helloService*");  // 支持 * 通配
        creator.setInterceptorNames("logInterceptor");
        return creator;
    }
}
```

### 2. 标注方法

```java
@Logger(
    symbol = "UserQuery",                    // 必填 — 操作标识
    digestLogName = "digestLogger",          // 可选 — 摘要日志分流
    detailLogName = "detailLogger",          // 可选 — 明细日志分流
    blacklist = {"password", "token"}        // 可选 — 参数脱敏
)
public RespData<?> hello(@RequestParam String name) {
    return RespData.success("hello, " + name);
}
```

不填 `digestLogName` / `detailLogName` 时，使用当前类自己的 Logger。

### 3. Log4j2 分流配置

```xml
<Logger name="digestLogger" level="info" additivity="false">
    <AppenderRef ref="DIGEST_FILE"/>
</Logger>
<Logger name="detailLogger" level="info" additivity="false">
    <AppenderRef ref="DETAIL_FILE"/>
</Logger>
```

---

## 自定义扩展点

### LogParser — 控制 `success` 和 `code` 字段

```java
public class CustomLogParser extends DefaultLogParser {
    @Override
    public boolean isSuccess(Object result) {
        if (result instanceof RespData<?> resp) return resp.isSuccess();
        return super.isSuccess(result);
    }

    @Override
    public String getCode(Object result) {
        if (result instanceof RespData<?> resp) return String.valueOf(resp.getCode());
        return super.getCode(result);
    }
}
```

### LogInfo — 扩展日志字段

继承 `LogInfo`，重写 `digestLog()` / `detailLog()`。`LogInfo` 自带属性：symbol、success、code、result、className、methodName、enterTime、outTime、args、blacklist。

```java
public class CustomLogInfo extends LogInfo {
    private String traceId;
    @Override public String digestLog() { return super.digestLog(); }
    @Override public String detailLog() { return super.detailLog(); }
}
```

---

## Gotchas

1. **`BeanNameAutoProxyCreator.setBeanNames()` 匹配的是 Spring Bean name，不是类名**——默认 Bean name 是类名首字母小写（如 `LogController` → `"logController"`）。用 `*` 通配可覆盖一组。
2. **`LogInterceptor` 在方法抛出异常时不写日志**——源码中 `invocation.proceed()` 被 try-catch 包裹，但 catch 块只做 `throw e`，不写日志。如果方法抛异常，这次调用不会出现在日志中。
3. **`blacklist` 只影响明细日志的 REQUEST 部分**——摘要日志不受 blacklist 影响（摘要不包含参数内容）。
4. **自定义 `LogInfo` 子类需要一个无参构造器**——`LogInterceptor` 通过 `logInfoClass.newInstance()`（反射）创建实例。

---

## 检查清单

- [ ] `BeanNameAutoProxyCreator` 的 `beanNames` 和 `interceptorNames` 都正确
- [ ] `@Logger(symbol=...)` 已标注，symbol 有意义
- [ ] `digestLogName` / `detailLogName` 的值与 `log4j2.xml` 中的 Logger name 一致
- [ ] 日志 appender 已配置（否则日志写入默认 logger）
- [ ] `blacklist` 包含所有敏感字段
- [ ] 如果需要从业务对象提取 success/code，已配置自定义 `LogParser`

---

## 参考

- 框架源码：https://github.com/lazycece/rapidf/releases ，框架发布版本与当前skill版本对应
- 拦截器：`rapidf-components/rapidf-logger/src/main/java/com/lazycece/rapidf/logger/interceptor/LogInterceptor.java`
- 注解：`rapidf-components/rapidf-logger/src/main/java/com/lazycece/rapidf/logger/annotation/Logger.java`
- 示例：`rapidf-samples/rapidf-logger-sample/` — 完整配置、自定义 Parser、Log4j2
