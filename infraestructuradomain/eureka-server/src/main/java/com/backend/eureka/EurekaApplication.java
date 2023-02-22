package com.backend.eureka;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.netflix.eureka.server.EnableEurekaServer;

@SpringBootApplication
@EnableEurekaServer
public class EurekaApplication {

    public static void main(String[] args) {
        SpringApplication.run(EurekaApplication.class, args);
    }
}

/*

java -jar C:\Users\carlo\.m2\repository\com\backend\infraestructuradomain\eureka\0.0.1-SNAPSHOT\eureka-0.0.1-SNAPSHOT.jar

*/