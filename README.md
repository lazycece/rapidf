# Rapidf
Rapid development framework

## Quick Start

### Maven dependency
```xml
    <!-- add sonatype repository when use SNAPSHOT version-->
    <repositories>
        <repository>
            <id>sonatype</id>
            <name>sonatype</name>
            <url>https://oss.sonatype.org/content/groups/public</url>
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