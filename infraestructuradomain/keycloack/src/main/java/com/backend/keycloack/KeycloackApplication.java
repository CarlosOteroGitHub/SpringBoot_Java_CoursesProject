package com.backend.keycloack;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.netflix.eureka.EnableEurekaClient;

@SpringBootApplication
@EnableEurekaClient
public class KeycloackApplication {

    public static void main(String[] args) {
        SpringApplication.run(KeycloackApplication.class, args);
    }
}

/*

java -jar C:\Users\carlo\.m2\repository\com\backend\infraestructuradomain\keycloack\0.0.1-SNAPSHOT\keycloack-0.0.1-SNAPSHOT.jar

*/