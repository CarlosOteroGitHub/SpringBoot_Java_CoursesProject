package com.backend.courses;

import org.springdoc.core.GroupedOpenApi;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.netflix.eureka.EnableEurekaClient;
import org.springframework.context.annotation.Bean;

@SpringBootApplication
@EnableEurekaClient
public class CoursesApplication {

    public static void main(String[] args) {
        SpringApplication.run(CoursesApplication.class, args);
    }

    @Bean
    public GroupedOpenApi publicApi() {
        return GroupedOpenApi.builder()
                .group("springshop-public")
                .packagesToScan("com.backend")
                .build();
    }
}

/*

URL Swagger: http://localhost:8081/swagger/index.html
java -jar C:\Users\carlo\.m2\repository\com\backend\courses\0.0.1-SNAPSHOT\courses-0.0.1-SNAPSHOT.jar

 */