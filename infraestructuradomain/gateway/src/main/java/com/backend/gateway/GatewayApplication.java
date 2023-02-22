package com.backend.gateway;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.netflix.eureka.EnableEurekaClient;

@SpringBootApplication
@EnableEurekaClient
public class GatewayApplication {

    public static void main(String[] args) {
        SpringApplication.run(GatewayApplication.class, args);
    }
}

/*

java -jar C:\Users\carlo\.m2\repository\com\backend\gateway\0.0.1-SNAPSHOT\gateway-0.0.1-SNAPSHOT.jar

*/