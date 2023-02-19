# Rapidf: Rapid Development Framework

[![Maven Central](https://img.shields.io/maven-central/v/com.lazycece.rapidf/rapidf-parent)](https://search.maven.org/search?q=rapidf)
[![License](https://img.shields.io/badge/license-Apache--2.0-green)](https://www.apache.org/licenses/LICENSE-2.0.html)
[![GitHub release](https://img.shields.io/badge/release-download-orange.svg)](https://github.com/lazycece/rapidf/releases)

[英文](./README_en.md)

## Rapidf 是做什么的？

Rapidf (`Rapi`d `D`evelopment `F`ramework ) 是一个快速开发框架，其构思是将我们日常开发工作中业务低耦合相关的代码抽取并形成可复用、可迁移的组件资产。Rapidf 目前有如下组件：

- rapidf-domain：从领域驱动的视角，定义领域相关的基本信息
- rapidf-logger：日志输出框架，可用于常规日志输出或日志监控
- rapidf-restful：restful相关，如api协议、服务异常等等
- rapidf-utils：沉淀可复用的工具类
- rapidf-validation：hibernate-validation的扩展


## 快速开始

完整的示例可以看 [rapidf-samples](https://github.com/lazycece/rapidf/tree/main/rapidf-samples).

### Maven 依赖
```xml
    <!-- add sonatype repository when use SNAPSHOT version-->
    <repositories>
        <repository>
            <id>sonatype</id>
            <name>sonatype</name>
            <url>https://s01.oss.sonatype.org/content/groups/public</url>
            <snapshots>
                <enabled>true</enabled>
            </snapshots>
            <releases>
                <enabled>true</enabled>
            </releases>
        </repository>
    </repositories>
    <dependencies>
        <dependency>
            <groupId>com.lazycece.rapidf</groupId>
            <artifactId>rapidf-dependencies</artifactId>
            <version>${rapidf-dependencies.version}</version>
            <type>pom</type>
            <scope>import</scope>
        </dependency>
    </dependencies>
```

## License

[Apache-2.0](https://www.apache.org/licenses/LICENSE-2.0.html)