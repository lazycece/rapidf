# Rapidf
[![Maven Central](https://img.shields.io/maven-central/v/com.lazycece.rapidf/rapidf-parent)](https://search.maven.org/search?q=rapidf)
[![License](https://img.shields.io/badge/license-Apache--2.0-green)](https://www.apache.org/licenses/LICENSE-2.0.html)
[![GitHub release](https://img.shields.io/badge/release-download-orange.svg)](https://github.com/lazycece/rapidf/releases)

[中文](./README_zh_CN.md)

The full name is <font size=4 color=blue>Rapi</font>d <font size=4 color=blue>d</font>evelopment <font size=4 color=blue>f</font>ramework. Rapidf currently provides the restful (response, exception, assert) ,
validation, api level logger (method level logger) and custom utils.

## Quick Start

Complete example can view [rapidf-example](https://github.com/lazycece/rapidf/tree/main/rapidf-example).

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