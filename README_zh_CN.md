# Rapidf: `Rapi`d `d`evelopment `f`ramework
[![Maven Central](https://img.shields.io/maven-central/v/com.lazycece.rapidf/rapidf-parent)](https://search.maven.org/search?q=rapidf)
[![License](https://img.shields.io/badge/license-Apache--2.0-green)](https://www.apache.org/licenses/LICENSE-2.0.html)
[![GitHub release](https://img.shields.io/badge/release-download-orange.svg)](https://github.com/lazycece/rapidf/releases)

[英文](./README.md)

全称是 `Rapi`d `d`evelopment `f`ramework（快速开发框架），Rapidf 目前提供了restful（响应、异常、断言）、验证、api 级别记录器（方法级别记录器）和自定义工具。

## Quick Start

完整的示例可以看 [rapidf-example](https://github.com/lazycece/rapidf/tree/main/rapidf-example).

### Maven dependency
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
            <artifactId>rapidf-xxx</artifactId>
            <version>${rapidf.xxx.version}</version>
        </dependency>
    </dependencies>
```

## License

[Apache-2.0](https://www.apache.org/licenses/LICENSE-2.0.html)