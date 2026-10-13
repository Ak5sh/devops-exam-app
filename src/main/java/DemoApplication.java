package com.example.demo;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@SpringBootApplication
@RestController
public class DemoApplication {

    @GetMapping("/")
    public String home() {
        return "DevOps CI/CD Pipeline Deployed Successfully - Version 2.0 (Automated Rolling Update)";
    }

    public static void main(String[] args) {
        SpringApplication.run(DemoApplication.class, args);
    }
}