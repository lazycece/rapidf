# Rapidf: Rapid Development Framework

[![Maven Central](https://img.shields.io/maven-central/v/com.lazycece.rapidf/rapidf-parent)](https://search.maven.org/search?q=rapidf)
[![License](https://img.shields.io/badge/license-Apache--2.0-green)](https://www.apache.org/licenses/LICENSE-2.0.html)
[![GitHub release](https://img.shields.io/badge/release-download-orange.svg)](https://github.com/lazycece/rapidf/releases)

[中文](./README.md)

## What does it do ?

Rapidf (`Rapi`d `D`development `F`ramework ) is a development framework. Committed to extracting business low-coupling related codes in daily development work and forming portable component assets to solve the standardization and manageability of application architecture.  

Rapidf currently has the following components:

- rapidf-arrange: Using to standardized arrange application business logic.
- rapidf-auapi: Based on rapidf component integration [AuApi](https://github.com/lazycece/au-api-spring-boot) framework, out of the box
- rapidf-dispatcher：Based on the application of the hexagonal architecture idea, the inbound adapter framework is implemented, and two modes of service handler and facade service are supported
- rapidf-domain: From a domain-driven perspective, define the basic information related to the domain (model, event, statemachine and so on)
- rapidf-logger: Log output framework, which can be used for regular log output or log monitoring
- rapidf-restful: Restful related, such as api protocol, service exception and so on.
- rapidf-utils: Precipitate reusable tools
- rapidf-validation: Extension of hibernate-validation

![component_architecture](./document/picture/component_architecture.png)


## Environment

Rapidf environment dependency as follow::

|Rapidf|Java|Spring Boot|Remark|
|---|---|---| --- |
|1.x|1.8+|2.x| stop support new features |
|2.x|17+|3.x| continuously updating|


## Quick Start

Complete example can view [rapidf-samples](https://github.com/lazycece/rapidf/tree/main/rapidf-samples).

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
            <artifactId>rapidf-dependencies</artifactId>
            <version>${rapidf-dependencies.version}</version>
            <type>pom</type>
            <scope>import</scope>
        </dependency>
    </dependencies>
```

## License

[Apache-2.0](https://www.apache.org/licenses/LICENSE-2.0.html)