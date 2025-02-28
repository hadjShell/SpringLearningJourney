# Spring Notes

***

## Maven

* Project management tool

* Handle dependencies and building process of Java-based projects

* How Maven works

  1. Read config file

  2. Check local repo `.m2`

  3. Get from remote repo

  4. Save in local repo

* Standrad directory structure

  * `mvnw`
    * Allows you to run a Maven project
    * No need to have Maven installed or present on your path
    * If correct version of Maven is not found on the computer, it will automaticallt download it and run it
    * Two files are provided
      * `mvnw.cmd` for Windows
      * `mvnw.sh` for Unix-like
  * `pom.xml`
    * Maven config file
  * `src/main/java`
    * Java source code
  * `src/main/resources`
    * Properties / config files used by your app
  * `src/main/webapp`
    * JSP files and web config files, other web assets
    * DON'T use this directory if your package is packed as a JAR
    * Only work with WAR packaging 
  * `src/test`
    * Unit testing code and properties
  * `target`
    * Destination directory for compiled code
    * Automatically created by Maven

* `pom.xml`

  * **P**roject **O**bject **M**odel file
  * File structure
    * Project metadata
      * Project name, version, output file type, etc.
    * Dependencies
    * Plugins
      * Additional custom tasks to run

  * Project coordinates

    * Uniquely identify a project

    * ```xml
      <dependencies>
        <dependency>
            <!--Name of company, group, or organisation-->
            <groupId>org.hibernate.orm</groupId>
            <!--Name of the project-->
            <artifactId>hibernate-core</artifactId>		
            <version>6.1.4.Final</version>	
          </dependency>
        </dependencies>
      ```

* Maven archetype
  * Prototype `pom.xml`

***

## Spring Core

* Spring vs. Spring Boot
  * | Feature           | Spring Framework | Spring Boot |
  |------------------|----------------|------------|
  | **Definition** | A comprehensive framework for Java enterprise applications. | A framework built on top of Spring that simplifies application setup and development. |
  | **Configuration** | Requires a lot of manual XML or Java-based configuration. | Comes with auto-configuration to reduce boilerplate code. |
  | **Standalone Applications** | Needs an external server (e.g., Tomcat, Jetty) for deployment. | Embedded servers (Tomcat, Jetty, Undertow) allow applications to run standalone. |
  | **Dependency Management** | Requires developers to manually manage dependencies. | Provides "starters" (e.g., `spring-boot-starter-web`) to simplify dependency management. |
  | **Complexity** | More flexible but requires more setup and understanding of components. | Opinionated defaults make development faster and easier. |
  | **Microservices** | Can be used for microservices but requires additional configuration. | Designed with microservices in mind, making development smoother. |
  | **Production Readiness** | Requires additional setup for metrics, logging, and monitoring. | Includes built-in support for monitoring, metrics, and externalized configuration. |

* Write code as POJOs (Plain Old Java Objects)

### Spring Beans

* A Spring Bean is simply a Java object created and managed by Spring IoC container

* Why do we need Beans?

  * Without Spring Beans, developers must manually create and manage objects, leading to tight coupling
  * Spring Beans allow dependencies to be injected instead of instantiated inside a class, which enables loose coupling, easier unit testing, and better code reusability

* How to create bean

  * XML-based configuration

    * ```xml
      <!--/src/main/resources/spring.xml-->
      <bean id="laptop" class="com.example.Laptop" scope="singleton"/>
      ```

    * ```java
      ApplicationContext context = new ClassPathXmlApplicationContext("spring.xml");
      Laptop l1 = (Laptop) context.getBean("laptop");
      // or
      Laptop l2 = context.getBean(Laptop.class);
      ```
      
    * 

  * Java-based configuration

  * Component scanning

    * Spring Boot style

* Scope

  * Singleton
    * By default beans are singleton scope
  * Prototype
    * A new instance is created everytime it is requested
  * Request 
    * A new instance is created for each HTTP request (Web Applications)
  * Session
    * A new instance is created per HTTP session
  * Application
    * A single instance shared across the entire web application

* Beans can be initialised lazily when they are created not at application startup but only when they are first needed

  * XML-based Config

    * ```xml
      <bean id="laptop" class="com.example.Laptop" lazy-init="true"/>
      ```

  * Java-based Config

  * Spring Boot style

  * When to use lazy initialisation

    * The bean is **rarely used**
    * The bean is **resource-heavy** (e.g., database connections, third-party APIs)
    * You want **faster application startup**


### Inversion of Control

* Focusing on business logic instead of managing objects (creating, maintaining, destroying) as a programmer
* Spring IoC container takes care of it
* It is a Spring principle

### Dependency Injection

* In order to achieve IoC we use this design pattern DI

#### Construction Injection

* It is the recommended approach for injecting **mandatory dependencies** because it ensures that an object is always created with its required dependencies

* Steps

  1. Spring creates an instance of the dependent class
  2. It injects dependencies via the constructor
  3. The bean is then ready for use

* XML-based Config

  * ```xml
    <bean id="laptop" class="com.example.Laptop">
      <!--Must follow the sequence in the parameter list of the constructor-->
      <!--primitive fields-->
    	<constructor-arg name="price" value="100"/>
      <!--references-->
      <constructor-arg name="compiler" ref="compiler"/>
      
      <!--Or use index-->
      <constructor-arg index="1" ref="compiler"/>
      <constructor-arg index="0" value="100"/>
    </bean>
    
    <bean id="compiler" class="com.example.Compiler"/>
    ```

  * 

* Java-based Config

* Spring Boot Style

* 

#### Setter Injection

* It allows Spring to inject beans after object creation
* Steps

  1. Spring creates an instance of the dependent class
  2. It injectsdependencies using setter methods
  3. The bean is then ready for use
* XML-based Config
	* ```xml
    <bean id="lap" class="com.example.Laptop">
      <!--primitive fields-->
    	<property name="price" value="100"/>
      <!--references-->
      <property name="compiler" ref="comp"/>
    </bean>
    
    <bean id="comp" class="com.example.Compiler"/>
* Java-based Config
* Spring Boot style

* | **Use Setter Injection When...**                             | **Use Constructor Injection When...**                        |
  | ------------------------------------------------------------ | ------------------------------------------------------------ |
  | Dependency is **optional** (not always needed).              | Dependency is **mandatory** for the object to function.      |
  | You want to allow **modification after object creation**.    | You want to ensure **immutability** (no changes after construction). |
  | The class has **many dependencies**, making constructor injection harder to manage. | The class has **few dependencies**, making constructor injection clearer. |

#### Autowiring

* Autowiring is a mechanism that automatically injects dependencies into Spring Beans, reducing the need for manual bean wiring in configuration files

* XML-based Config

  * ```java
    class Student {
      private Computer computer;
    }
    
    interface Computer {}
    class Laptop implements Computer {}
    class Desktop implements Computer {}
    ```

  * ```xml
    <!--Autowiring by name-->
    <!--Works when the bean name matches the field name-->
    <bean id="computer" class="com.example.Laptop"/>
    <bean id="Student" class="com.example.Student" autowire="byName"/>
    <!--The field must be named computer in Student-->
    ```

  * ```xml
    <!--Autowiring by type-->
    <!--Injects a bean if only one bean of that type exists-->
    <!--Set a primary bean when there is a conflict-->
    <bean id="laptop" class="com.example.Laptop" primary="true"/>
    <bean id="desktop" class="com.example.Desktop"/>
    <bean id="Student" class="com.example.Student" autowire="byType"/>
    <!--The field must be named computer in Student-->
    ```

  * 

* Java-based Config

* Spring Boot Style

***

## Spring Boot

* Spring Initialzr

  * `https://start.spring.io/`

  * Spring Boot Starter

    * A collection of dependencies with compatible versions to boot up the project

    * `spring-boot-starter-xyz`

    * `spring-boot-starter-parent`

      * Default Maven config

        * Java version, UTF-coding, etc.

        * Override a default

          * ```xml
            <properties>
              <java.version>17</java.version>
            </properties>
            ```

      * Use version on parent only, children will inherit it

      * Default plugin config

* Application properties

  * `src/resources/application.properties`

  * Config the application

    * ```properties
      server.port=8484
      logging.file.name=mylog.log
      
      ## customer
      author.name=David
      ```

  * Access the properties in Java files

    * ```java
      @Value("${author.name}")
      private String authorName;
      ```

* By default, Spring Boot will load static resources from `resources/static` directory

* By default, Spring Boot will load templates from `resources/templates` directory

* Spring Boot Dev Tools

  * `spring-boot-devtools`
  * Automatically restarts your application when your code is updated

* Spring Boot Actuator

  * Expose endpoints to monitor and manage your application

  * `spring-boot-starter-actuator`

  * `/health`

    * Checks the status of your application

  * `info`

    * Provides information about your application

    * To expose it, add properties

    * ```properties
      management.endpoints.web.exposure.include=health,info
      management.info.env.enabled=true
      
      info.app.name=My Cool App
      info.app.version=1.0.0
      ```

    * Anything start with `info` will be added to the endpoint

* Running a Spring application on CLI

  * `mvn package`
  * `java -jar myapp.jar` or `mvn spring-boot:run`



