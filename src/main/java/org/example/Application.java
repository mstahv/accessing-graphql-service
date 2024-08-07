package org.example;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

/**
 * The entry point of the Spring Boot application.
 * This app needs a priming build, probably better to use DevModeApplication.java
 * in src/test/java/org/example and run the demo in development mode.
 */
@SpringBootApplication
public class Application {

    public static void main(String[] args) {
        SpringApplication.run(Application.class, args);
    }

}
