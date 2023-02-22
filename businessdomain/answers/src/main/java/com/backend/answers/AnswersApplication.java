package com.backend.answers;

import org.springdoc.core.GroupedOpenApi;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.netflix.eureka.EnableEurekaClient;
import org.springframework.context.annotation.Bean;

@SpringBootApplication
@EnableEurekaClient
public class AnswersApplication {

    public static void main(String[] args) {
        SpringApplication.run(AnswersApplication.class, args);
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

URL Swagger: http://localhost:8083/swagger/index.html
java -jar

*/