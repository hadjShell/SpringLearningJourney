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
      // src/main/java/com.example/App.java
      ApplicationContext context = new ClassPathXmlApplicationContext("spring.xml");
      Laptop l1 = (Laptop) context.getBean("laptop");
      // or
      Laptop l2 = context.getBean(Laptop.class);
      ```

  * Java-based configuration

    * ```java
      // src/main/java/com.example/config/AppConfig.java
      
      @Configuration
      public class AppConfig {
        
        // the name of the bean is the method name by default
        @Bean(name={"machine1", "beast"})
        @Scope("prototype")
        public Laptop laptop() {
          return new Laptop();
        }
      }
      ```

    * ```java
      // src/main/java/com.example/App.java
      ApplicationContext context = 
        new AnnotationConfigApplicationContext(AppConfig.class);
      Laptop l = context.getBean(Laptop.class);
      ```

  * Component scanning

    * Stereotype annotations

      * | Annotation        | Description                                                  |
        | ----------------- | ------------------------------------------------------------ |
        | `@Component`      | Generic stereotype for any Spring-managed bean.              |
        | `@Service`        | Specialized for **service layer** components (business logic). |
        | `@Repository`     | Specialized for **DAO (Data Access Object) layer** (database interactions). |
        | `@Controller`     | Specialized for **web controllers** in Spring MVC.           |
        | `@RestController` | Combines `@Controller` and `@ResponseBody` for REST APIs.    |

    * ```java
      @Component
      @Scope("prototype")
      public class Laptop {}
      ```

    * ```java
      @SpringBootApplication 	// Include @ComponentScan by default
      public class SpringBootDemoApplication {
        public static void main(String[] args) {
          ApplicationContext context = 
            SpringApplication.run(SpringBootDemoApplication.class, args);
          Laptop l = context.getBean(Laptop.class);
        }
      }
      ```

    * Spring automatically **scans packages** for stereotype annotations using `@ComponentScan`

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

    * ```java
      @Configuration
      public class AppConfig {
        
        @Bean
        @Lazy
        public Laptop laptop() {
          return new Laptop();
        }
      }
      ```
  
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

* Java-based Config

  * ```java
    @Configuration
    public class AppConfig {
      
      @Bean
      public Compiler compiler() {
        return new Compiler();
      }
      
      @Bean
      public Laptop laptop(Compiler compiler) {
        return new Laptop(compiler);
      }
    }
    ```


#### Setter Injection

* It allows Spring to inject beans after object creation

* Steps

  1. Spring creates an instance of the dependent class
  2. It injects dependencies using setter methods
  3. The bean is then ready for use

* XML-based Config
  * ```xml
    <bean id="laptop" class="com.example.Laptop">
      <!--primitive fields-->
    	<property name="price" value="100"/>
      <!--references-->
      <property name="compiler" ref="compiler"/>
    </bean>
    
    <bean id="compiler" class="com.example.Compiler"/>

* Java-based Config

  * ```java
    @Configuration
    public class AppConfig {
      
      @Bean
      public Compiler compiler() {
        return new Compiler();
      }
      
      @Bean
      public Laptop laptop(Compiler compiler) {
        Laptop l = new Laptop();
        l.setCompiler(compiler);
        return l;
      }
    }
    ```

* | **Use Setter Injection When...**                             | **Use Constructor Injection When...**                        |
  | ------------------------------------------------------------ | ------------------------------------------------------------ |
  | Dependency is **optional** (not always needed).              | Dependency is **mandatory** for the object to function.      |
  | You want to allow **modification after object creation**.    | You want to ensure **immutability** (no changes after construction). |
  | The class has **many dependencies**, making constructor injection harder to manage. | The class has **few dependencies**, making constructor injection clearer. |

* **It is not recommended to use both Constructor Injection and Setter Injection for the same field in a Spring Bean**

* if you define both Constructor Injection and Setter Injection for the same field, Spring will prioritize **Constructor Injection** if both are present

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

* Java-based Config

  * ```java
    @Configuration
    public class AppConfig {
      
      @Bean
      @Primary
      public Laptop laptop() {
        return new Laptop();
      }
      
      @Bean 
      public Desktop desktop() {
        return new Desktop();
      }
      
      @Bean
      public Student student(@Qualifier("desktop") Computer computer) {
        return new Student(computer);
      }
    }
    ```

* Spring Boot Style

  * ```java
    @Component
    public class Student {
      // Field injection (least recommended)
      @Autowired
      private Card IdCard;
      private final Computer computer;
      private Bag bag;
      
      // Constructor injection
      @Autowired
      public Student(@Qualifier("laptop") Computer computer) {
        this.computer = computer;
      }
      
      // Setter injection
      // If a dependency may not always be available, set required = false
      // If no bean is found, Spring will not throw an error
      @Autowired(required = false)
      public void setBag(Bag bag) {
        this.bag = bag;
      }
    }
    ```


***

## Spring JDBC

* Spring JDBC is a lightweight module in the Spring Framework that simplifies database interaction using JDBC
* For larger applications, Spring Data JPA (with Hibernate) might be preferable, but for simple use cases, Spring JDBC is lightweight and efficient

### Key Components 

* `DataSource`

  * A `DataSource` object provides database connection pooling

  * Instead of creating a new connection for every request, Spring uses a connection pool to reuse existing connections efficiently

  * ```properties
    # application.properties
    spring.datasource.url=jdbc:mysql://localhost:3306/mydb
    spring.datasource.username=root
    spring.datasource.password=secret
    spring.datasource.driver-class-name=com.mysql.cj.jdbc.Driver
    spring.datasource.hikari.maximum-pool-size=10
    ```

  * ```java
    // or programmatically using DataSource
    @Bean
    public DataSource getDataSource() {
        HikariDataSource dataSource = new HikariDataSource();
        dataSource.setDriverClassName("com.mysql.cj.jdbc.Driver");
        dataSource.setJdbcUrl("jdbc:mysql://localhost:3306/mydb");
        dataSource.setUsername("root");
        dataSource.setPassword("secret");
        return dataSource;
    }
    ```

* `JdbcTemplate`

  * `JdbcTemplate` is the core class in Spring JDBC that provides methods to execute SQL queries. It internally manages:
    * Connection establish
    * Statement preparation
    * Query execution
    * Exception handling
    * Resource cleanup

### CRUD Operations

* Insert

  * ```java
    public void save(Employee e) {
        String sql = "INSERT INTO employees (id, name, department) VALUES (?, ?, ?)";
        jdbcTemplate.update(sql, e.getId(), e.getName(), e.getDepartment());
    }
    ```

* Select

  * A `RowMapper` is used to map database rows to Java objects

  * `RowMapper` is a functional interface with an abstract method `T mapRow(ResultSet rs, int rowNum)`

  * ```java
    public List<Student> findAll() {
        String sql = "select * from student";
        public class StudentRowMapper implements RowMapper<Student> {
          @Override
          public Student mapRow(ResultSet rs, int rowNum) throws SQLException {
              Student student = new Student();
              student.setRollNo(rs.getInt("rollno"));
              student.setName(rs.getString("name"));
              student.setMarks(rs.getInt("marks"));
              return student;
          }
      	}
    
        return jdbc.query(sql, new StudentRowMapper());
    }
    ```

    ```java
    // lambda expression
    public List<Student> findAll() {
        String sql = "select * from student";
        return jdbc.query(sql, (rs, rowNum) ->
                new Student(rs.getInt("rollno"),
                            rs.getString("name"),
                            rs.getInt("marks"))
        );
    }
    ```

    ```java
    // query for one object
    public Student find(int no) {
        String sql = "select * from student where rollno = ?";
        return jdbc.queryForObject(sql, (rs, rowNum) ->
                new Student(rs.getInt("rollno"),
                        rs.getString("name"),
                        rs.getInt("marks")),
                no);
    }
    ```

* Update

  * ```java
    public void update(int id, String name, String department) {
        String sql = "UPDATE employees SET name = ?, department = ? WHERE id = ?";
        jdbcTemplate.update(sql, name, department, id);
    }
    ```

* Delete

  * ```java
    public void delete(int id) {
        String sql = "DELETE FROM employees WHERE id = ?";
        jdbcTemplate.update(sql, id);
    }
    ```

### Transaction Management

* `@Transactional`

### Exception Handling

* Spring JDBC translates `SQLExceptions` into **`DataAccessException`**, which is a runtime exception hierarchy

* | Exception                                | Description                                           |
  | ---------------------------------------- | ----------------------------------------------------- |
  | `DataAccessException`                    | Root exception for Spring JDBC                        |
  | `EmptyResultDataAccessException`         | Thrown when `queryForObject()` returns no result      |
  | `IncorrectResultSizeDataAccessException` | Thrown when an unexpected number of rows are returned |

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



