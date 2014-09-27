SchemaSpy Maven Plugin
=======================

SchemaSpy Maven Plugin

Generate SchemaAnalyze from imported sql files (embedded database).


## Usage

```
<plugin>
	<groupId>com.benasmussen.maven</groupId>
	<artifactId>schemaspy-maven-plugin</artifactId>
	<version>0.0.1-SNAPSHOT</version>
	<executions>
		<execution>
			<id>analyze</id>
			<phase>package</phase>
			<goals>
				<goal>spy</goal>
			</goals>
		</execution>
	</executions>
	<configuration>
		<sqlFiles>
			<sqlFile>file:${project.build.directory}/generated-sources/sql/create.sql</sqlFile>
		</sqlFiles>
	</configuration>
</plugin>
```




## Bug tracker

Have a bug or a feature request? Please create an issue here on GitHub.

http://github.com/basmussen/schemaspy-maven-plugin/issues


## Contributing

Fork the repository and submit pull requests.


## Author

**Ben Asmussen**

+ http://github.com/basmussen
