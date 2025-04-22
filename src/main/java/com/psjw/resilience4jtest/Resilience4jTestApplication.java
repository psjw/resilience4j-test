package com.psjw.resilience4jtest;

import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@Slf4j
@SpringBootApplication
public class Resilience4jTestApplication {

    public static void main(String[] args) {
        SpringApplication.run(Resilience4jTestApplication.class, args);
    }

}
