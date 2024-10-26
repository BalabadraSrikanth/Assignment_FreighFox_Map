package com.Assignment;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.web.client.RestTemplate;

@SpringBootApplication(scanBasePackages = "com.Assignment") // Adjust package as necessary
public class AssignmentFreighFoxApplication {

    public static void main(String[] args) {
        SpringApplication.run(AssignmentFreighFoxApplication.class, args);
    }

    @Bean
    public RestTemplate restTemplate() {
        return new RestTemplate();
    }
}
