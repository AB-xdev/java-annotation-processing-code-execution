# Java Annotation Processing Backdoor 
<sup><i>Since Java 6</i></sup>

This is a test library to test if your compiler automatically executes Java Annotation Processors.

⚠ This library might crash the compiler

## Background

<img src="./JSR269InANutshell.png" height=450 />

[JSR 269](https://jcp.org/en/jsr/detail?id=269) introduced Annotation processing into Java.<br/>
Annotation processing is done by the Java compiler itself (because it comes from a time before buildtools like Maven or Gradle).

This itself is not a problem but the following design aspects are:
https://docs.oracle.com/en/java/javase/17/docs/specs/man/javac.html#annotation-processing
> Unless annotation processing is disabled with the `-proc:none` option, the compiler searches for any annotation processors that are available. The search path can be specified with the `-processorpath` option. If no path is specified, then the user class path is used. Processors are located by means of service provider-configuration files named `META-INF/services/javax.annotation.processing` Processor on the search path.

So if a malicous or manipulated library somehow ends up in your project, it can execute ANY code when compilation happens.

Everything that uses a Java Compiler (e.g. `javac` or `ecj`) with the default options (IDEs like Eclipse/IntelliJ or the maven-compiler-plugin) are affected.

## Building the library

Run ``mvn clean install``.


## Testing if your code is attackable

Build the library as mentioned above.

⚠ If you use Eclipse: Make sure that "Build automatically" is disabled! (see below)

Add the following dependency to your code:
```xml
<dependency>
	<groupId>software.xdev</groupId>
	<artifactId>java-annotation-processing-backdoor</artifactId>
	<version>1.0-SNAPSHOT</version>
</dependency>
```

## Mitigation / How to fix this?

### Java-Compiler
There is a compiler flag [`-proc:none`](https://docs.oracle.com/en/java/javase/17/docs/specs/man/javac.html#option-proc) that can disable the above mentioned behavior.

### Maven
Ensure that the compiler is executed with `-proc:none`, like this:
```xml
<plugin>
	<groupId>org.apache.maven.plugins</groupId>
	<artifactId>maven-compiler-plugin</artifactId>
	<version>3.10.1</version>
	<configuration>
		<compilerArgs>
			<arg>-proc:none</arg>
		</compilerArgs>
	</configuration>
</plugin>
```

The best solution is to configure this in all modules by doing it inside the parent poms ``build -> pluginManagement -> plugins``-section

### IDEs

IDEs usually have custom support for Annotation Processing.

#### IntelliJ IDEA

IDEA automatically imports the `-proc:none` argument from Maven if configured correctly.<br/>
However this doesn't disable the Annotation Processors and they are still executed when building, which leads to errors like `java: java.lang.ClassNotFoundException: org.hibernate.jpamodelgen.JPAMetaModelEntityProcessor`.<br/>
[You have to disable them in the settings](https://www.jetbrains.com/help/idea/annotation-processors-support.html).

#### Eclipse

⚠ Adding this library might cause the IDE to crash because the build process is not sandboxed.<br/>
You might be no longer able to open your project because Eclipse instantly rebuilds the project on restart which causes a crash again.<br/>
So make sure that "Build automatically" is disabled!

As far as I have seen Eclipse is not affected by default because Annotation processing isn't enabled by default (or not implemented in a stable way?).
However if you e.g. set `Settings: Maven > Annotation Processing : Annotation Processing Mode` to `Experimental: Delegate annotation processing to maven plugins` 
or if you enable it inside a project (`Right click project : Properties : Java Compiler > Annotation Processing : Enable annotation processing`) the complete IDE will crash.

