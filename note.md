# Spring Notes

***

## Introduction

* Spring is a popular framework for building Java applications
* The problem is that building a traditional Spring application is really hard
  * Which JAR dependencies do I need?
  * How do I set up configuration (xml or Java)?
  * How do I install the server (Tomcat, JBoss, etc.)?
  * Etc.
* Spring Boot solution
  * Build on top of Spring
  * Minimises the configuration required for setting up Spring projects
  * Microservice based framework
  * Provide an embedded HTTP server
  * Can be run standalone
* Spring project
  * Additional Spring modules built on top of the core Spring framework
  * Only use what you need
  * Spring Cloud, Spring Data
  * Spring Batch, Spring Security
  * Spring Web Services, Spring LDAP
  * Etc.

***

## Maven

* Project management tool

* Handle dependencies and building process of Java-based projects

* How Maven works

  1. Read config file

  2. Check local repo 

  3. Get from remote repo

  4. Save in local repo

* Standrad directory structure

  * `mvnw`
    * Allows you to run a Maven project
    * No need to have Maven installed or present on your path
    * If correct version of Maven is not founf on the computer, it will automaticallt download it and run it
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
      <dependency>
        <!--Name of company, group, or organisation-->
        <groupId>org.hibernate.orm</groupId>
        <!--Name of the project-->
        <artifactId>hibernate-core</artifactId>		
        <version>6.1.4.Final</version>	
      </dependency>
      ```

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

***





