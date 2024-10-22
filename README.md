# Rule Engine Application

## Overview
This project is a Rule Engine built using Spring Boot that allows users to create, evaluate, and manage rules using an Abstract Syntax Tree (AST). Rules are saved in a MySQL database and can be evaluated based on user-defined attributes (such as age, department, salary, and experience).

## Features
- Create Rules: Create rules using simple logical expressions (AND/OR).
- AST Representation: Convert rules into an AST and store them as JSON.
- Rule Evaluation: Evaluate rules against user attributes.
- Rule Combination: Combine multiple rules with logical operators.
- Validation: Only valid rules are saved to the database.
- Database Persistence: Rules are stored and retrieved from a MySQL database.

## Prerequisites
- Java 17+
- Maven 3.6+
- MySQL database

## Dependencies
The project uses the following dependencies, which are managed via Maven. Ensure you have these in your `pom.xml` file:

```xml
dependencies>
    <!-- Spring Boot Starter Data JPA for database access -->
    <dependency>
        <groupId>org.springframework.boot</groupId>
        <artifactId>spring-boot-starter-data-jpa</artifactId>
    </dependency>

    <!-- Spring Boot Starter Thymeleaf for templating engine -->
    <dependency>
        <groupId>org.springframework.boot</groupId>
        <artifactId>spring-boot-starter-thymeleaf</artifactId>
    </dependency>

    <!-- Spring Boot Starter Web for building web applications -->
    <dependency>
        <groupId>org.springframework.boot</groupId>
        <artifactId>spring-boot-starter-web</artifactId>
    </dependency>

    <!-- Spring Boot DevTools for development convenience -->
    <dependency>
        <groupId>org.springframework.boot</groupId>
        <artifactId>spring-boot-devtools</artifactId>
        <scope>runtime</scope>
        <optional>true</optional>
    </dependency>

    <!-- MySQL Connector/J for MySQL database connection -->
   <!-- For Maven -->
<dependency>
    <groupId>mysql</groupId>
    <artifactId>mysql-connector-java</artifactId>
    <version>8.0.28</version> <!-- Check for the latest version -->
</dependency>

    <!-- Spring Boot Starter Test for testing support -->
    <dependency>
        <groupId>org.springframework.boot</groupId>
        <artifactId>spring-boot-starter-test</artifactId>
        <scope>test</scope>
    </dependency>

    <!-- Jackson Annotations for JSON processing -->
    <dependency>
        <groupId>com.fasterxml.jackson.core</groupId>
        <artifactId>jackson-annotations</artifactId>
        <version>2.15.0</version> <!-- Use the latest version compatible with Spring Boot -->
    </dependency>

    <!-- Hamcrest for assertions in tests -->
    <dependency>
        <groupId>org.hamcrest</groupId>
        <artifactId>hamcrest</artifactId>
        <version>2.2</version> <!-- or another compatible version -->
        <scope>test</scope>
    </dependency>
</dependencies>

steps for installation:
**## Installation**

### Prerequisites
Before you begin, ensure you have the following installed on your machine:
- [Java 17+] (https://www.oracle.com/java/technologies/javase/jdk17-archive-downloads.html)

- [Maven 3.6+] (https://maven.apache.org/download.cgi)

- [MySQL] (https://www.mysql.com/) database installed and running

- [Eclipse IDE](https://www.eclipse.org/downloads/) (preferably with EGit plugin)



### Steps to Clone the Repository Using Eclipse IDE

1. **Install Eclipse IDE**:
   - Download and install Eclipse from the [Eclipse Downloads](https://www.eclipse.org/downloads/) page.

2. **Open Eclipse**:
   - Launch the Eclipse IDE after installation.

3. **Open the Git Repositories View**:
   - Go to `Window` > `Show View` > `Other`.
   - In the dialog that appears, type "Git" in the filter box and select `Git Repositories`. Click `Open`.

4. **Clone the Repository**:
   - In the **Git Repositories** view, click on the `Clone a Git repository` button.
   - Enter the repository URL:
     ```plaintext
             --->  https://github.com/yourusername/rule-engine.git
     ```
   - Select the branches you want to clone and click `Next`. 
OR **Clone the Repository**:(another way to clone)
1. Go to File > Import.
2. Select Git > Projects from Git and click Next.
3. Choose Clone URI :  https://github.com/yourusername/rule-engine.git  (enter this url to clone ) and click Next.
4. Enter the repository URI and your credentials, then select the branches you want to clone.

 

5. **Choose Local Destination**:
   - Select a local directory for the cloned repository and click `Finish`.

6. **Import the Project**:
   - Go to `File` > `Import`.
   - Select `Existing Maven Projects` under the `Maven` category and click `Next`.
   - Click `Browse` to select the root directory of the cloned repository and click `Finish`.



### Configure MySQL Database

1. **Create a New Database**:
   Open your MySQL command line or a MySQL client (like MySQL Workbench) and run:
   ```sql
   CREATE DATABASE rule_engine; (rule_engine is your database name you can change according to you)

2. **Update the database credentials in src/main/resources/application.properties:(open application.properties and add below credentials)

spring.datasource.url=jdbc:mysql://localhost:3306/rule_engine   (database name-rule_engine)
spring.datasource.username=yourusername  ( provide your mySql username)
spring.datasource.password=yourpassword  (provide your mySql password)
spring.jpa.hibernate.ddl-auto=update
(Replace yourusername and yourpassword with your actual MySQL username and password.)



### Build and Run the Project

1. Run the Application:
  - Right-click on the project in the Project Explorer.

  -  Select Run As > JAVA APPLICATION/SPRING BOOT APP/MAVEN BUILD

  - Enter spring-boot:run in the Goals field and click Run.

2.Access the Application
Once the application is running, you can access it in your web browser at:
http://localhost:8080         ****( 8080 IS PORT NUMBER - you have to modify the port number according to your application working on which port)***
(Ensure to modify the port number if your application uses a different one.)


