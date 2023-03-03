package com.backend.answers;

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
public class AnswersApplication {

    public static void main(String[] args) {
        SpringApplication.run(AnswersApplication.class, args);
    }

    @Bean
    public OpenAPI customOpenAPI() {
        return new OpenAPI()
                .components(new Components())
                .info(new Info()
                        .title("Microservicio Respuestas")
                        .description("Microservicio que Administra las API´s de las Respuestas")
                        .version("1.0.0")
                );
    }
}

/*

URL Swagger: http://localhost:8083/swagger/index.html
java -jar

*/
