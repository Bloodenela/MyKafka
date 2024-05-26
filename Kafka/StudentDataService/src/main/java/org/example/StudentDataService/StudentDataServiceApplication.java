package org.example.StudentDataService;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.ComponentScan;


@SpringBootApplication
@ComponentScan(basePackages = {
                "org.example.StudentDataService",
                "org.example.StudentEntityService"
        })
public class StudentDataServiceApplication {
    public static void main(String[] args) {
        SpringApplication.run(StudentDataServiceApplication.class, args);
    }
}
