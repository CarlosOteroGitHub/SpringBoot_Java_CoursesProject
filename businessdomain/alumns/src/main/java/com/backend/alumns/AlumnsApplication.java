package com.backend.alumns;

import org.springframework.boot.SpringApplication;
import org.springdoc.core.GroupedOpenApi;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.netflix.eureka.EnableEurekaClient;
import org.springframework.context.annotation.Bean;

@SpringBootApplication
@EnableEurekaClient
public class AlumnsApplication {

    public static void main(String[] args) {
        SpringApplication.run(AlumnsApplication.class, args);
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

URL Swagger: http://localhost:8080/swagger/index.html
java -jar C:/Users/carlo/.m2/repository/com/backend/alumns/0.0.1-SNAPSHOT/alumns-0.0.1-SNAPSHOT.jar

*/
