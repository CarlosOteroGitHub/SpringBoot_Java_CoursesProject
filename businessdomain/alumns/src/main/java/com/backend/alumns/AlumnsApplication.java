package com.backend.alumns;

import io.swagger.v3.oas.models.Components;
import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.Info;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.security.servlet.SecurityAutoConfiguration;
import org.springframework.cloud.netflix.eureka.EnableEurekaClient;
import org.springframework.context.annotation.Bean;

@SpringBootApplication(exclude = {SecurityAutoConfiguration.class})
@EnableEurekaClient
public class AlumnsApplication {

    public static void main(String[] args) {
        SpringApplication.run(AlumnsApplication.class, args);
    }

    @Bean
    public OpenAPI customOpenAPI() {
        return new OpenAPI()
                .components(new Components())
                .info(new Info()
                        .title("Microservicio Alumnos")
                        .description("Microservicio que Administra las API´s de los Alumnos")
                        .version("1.0.0")
                );
    }
}

/*

URL Swagger: http://localhost:8081/swagger/index.html
java -jar C:/Users/carlo/.m2/repository/com/backend/alumns/0.0.1-SNAPSHOT/alumns-0.0.1-SNAPSHOT.jar

*/
