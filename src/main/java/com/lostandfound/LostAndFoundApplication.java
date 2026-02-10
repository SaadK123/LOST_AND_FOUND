package com.lostandfound;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;

@SpringBootApplication
@EnableJpaAuditing
public class LostAndFoundApplication {

    public static void main(String[] args) {
        SpringApplication.run(LostAndFoundApplication.class, args);
        System.out.println("\n========================================");
        System.out.println("🚀 Lost & Found Backend is running!");
        System.out.println("📍 API: http://localhost:8080");
        System.out.println("🗄️  H2 Console: http://localhost:8080/h2-console");
        System.out.println("========================================\n");
    }
}
