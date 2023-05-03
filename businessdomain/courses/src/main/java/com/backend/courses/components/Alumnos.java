package com.backend.courses.components;

import com.backend.courses.auxiliar.HttpClientCommunication;
import com.fasterxml.jackson.databind.JsonNode;
import java.util.Collections;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.MediaType;
import org.springframework.http.client.reactive.ReactorClientHttpConnector;
import org.springframework.web.reactive.function.client.WebClient;

public class Alumnos {
    
    public boolean validIdAlumno(int id) {
        boolean bandera = true;
        HttpClientCommunication a = new HttpClientCommunication();
        WebClient build = a.getWebClientBuilder().clientConnector(new ReactorClientHttpConnector(a.getHttpClient()))
                .baseUrl("http://localhost:8081/detalle-alumno")
                .defaultHeader(HttpHeaders.CONTENT_TYPE, MediaType.APPLICATION_JSON_VALUE)
                .defaultUriVariables(Collections.singletonMap("url", "http://localhost:8081/detalle-alumno"))
                .build();
        JsonNode block = build.method(HttpMethod.GET).uri("/" + id)
                .retrieve().bodyToMono(JsonNode.class).block();
        
        if(block.get("id").asText().isEmpty()){
            bandera = false;
        }
        return bandera;
    }

    public String getAlumnoName(int id) {
        HttpClientCommunication a = new HttpClientCommunication();
        WebClient build = a.getWebClientBuilder().clientConnector(new ReactorClientHttpConnector(a.getHttpClient()))
                .baseUrl("http://localhost:8081/detalle-alumno")
                .defaultHeader(HttpHeaders.CONTENT_TYPE, MediaType.APPLICATION_JSON_VALUE)
                .defaultUriVariables(Collections.singletonMap("url", "http://localhost:8081/detalle-alumno"))
                .build();
        JsonNode block = build.method(HttpMethod.GET).uri("/" + id)
                .retrieve().bodyToMono(JsonNode.class).block();
        StringBuilder sb = new StringBuilder(100);
        sb.append(block.get("nombre").asText());
        sb.append(" ");
        sb.append(block.get("apellido").asText());
        return sb.toString();
    }
}