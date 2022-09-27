# Java Annotation Processing Backdoor 
<sup><i>Since Java 6</i></sup>

This is a test library to test if your compiler automatically executes Java Annotation Processors.

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

Add the following dependency to your code:
```xml
<dependency>
	<groupId>software.xdev</groupId>
	<artifactId>java-annotation-processing-backdoor</artifactId>
	<version>1.0-SNAPSHOT</version>
</dependency>
```
