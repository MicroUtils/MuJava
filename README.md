# JavaMicroUtils

JavaMicroUtils is a tiny library with classes (micro-utils) designed each with a specific goal, usually some small helper methods that can save a few lines of boilerplates code.  
The library assists using some of the JDK core api's and [Guava](https://github.com/google/guava) api's.  
In Addition Java newcomers can learn some concepts and best practices from the source code of the JavaMicroUtils.

## Getting started

### Maven
```xml
<dependencies>
 <dependency>
   <groupId>microutils</groupId>
   <artifactId>JavaMicroUtils</artifactId>
   <version>1.2</version>
 </dependency>
</dependencies>
<repositories>
  <repository>
    <id>central</id>
    <name>bintray</name>
    <url>http://jcenter.bintray.com</url>
  </repository>
</repositories>
```

### Gradle
```Groovy
compile 'microutils:JavaMicroUtils:1.2'
```

## MicroUtils Overview

* [FilesMu](https://github.com/MicroUtils/JavaMicroUtils/blob/master/src/main/java/mu/FilesMu.java) - read/write operations on files
* [ExceptionsMu](https://github.com/MicroUtils/JavaMicroUtils/blob/master/src/main/java/mu/ExceptionsMu.java) - exceptions handling, or in other words helping the world salvation from checked exceptions
* [EnumKeyUtils](https://github.com/MicroUtils/JavaMicroUtils/blob/master/src/main/java/mu/enums/EnumKeyUtils.java) - functions to help working with enums with indexes
* [EnumNameUtils](https://github.com/MicroUtils/JavaMicroUtils/blob/master/src/main/java/mu/enums/EnumNameUtils.java) - functions to help working with enums with names

## Major Dependencies

* Java 1.8
* [Guava](https://github.com/google/guava)
 
can be seen in [build.gradle](https://github.com/MicroUtils/JavaMicroUtils/blob/master/build.gradle)

