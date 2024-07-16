package org.example;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

/**
 * Use this main method to run the application in development mode.

 */
@SpringBootApplication
public class DevModeApplication {
    public static void main(String[] args) {
        SpringApplication.run(Application.class, args);
    }
}
