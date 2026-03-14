package com.medshield.medshieldbackend;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class MedshieldBackendApplication {

    public static void main(String[] args) {
        try {
            SpringApplication.run(MedshieldBackendApplication.class, args);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
} // <--- You were missing this one!