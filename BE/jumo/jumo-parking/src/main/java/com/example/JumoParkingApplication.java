package com.example;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.web.servlet.ServletComponentScan;

@SpringBootApplication
@ServletComponentScan
public class JumoParkingApplication {
    public static void main(String[] args) {
        SpringApplication.run(JumoParkingApplication.class, args);
    }
}
