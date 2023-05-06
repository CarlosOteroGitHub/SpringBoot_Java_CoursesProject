package com.backend.exams.components;

import com.backend.exams.auxiliar.HttpClientCommunication;
import com.fasterxml.jackson.databind.JsonNode;
import java.util.Collections;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.MediaType;
import org.springframework.http.client.reactive.ReactorClientHttpConnector;
import org.springframework.web.reactive.function.client.WebClient;

public class Respuestas {
    
    private static Respuestas instance = null;
    
    public boolean validIdRespuesta(int id) {
        boolean bandera = true;
        WebClient build = HttpClientCommunication.getInstance().getWebClientBuilder().clientConnector(new ReactorClientHttpConnector(HttpClientCommunication.getInstance().getHttpClient()))
                .baseUrl("http://localhost:8084/detalle-respuesta")
                .defaultHeader(HttpHeaders.CONTENT_TYPE, MediaType.APPLICATION_JSON_VALUE)
                .defaultUriVariables(Collections.singletonMap("url", "http://localhost:8084/detalle-respuesta"))
                .build();
        JsonNode block = build.method(HttpMethod.GET).uri("/" + id)
                .retrieve().bodyToMono(JsonNode.class).block();

        if (block.get("id").asText().isEmpty()) {
            bandera = false;
        }
        return bandera;
    }
    
    public String getRespuestaTexto(int id) {
        WebClient build = HttpClientCommunication.getInstance().getWebClientBuilder().clientConnector(new ReactorClientHttpConnector(HttpClientCommunication.getInstance().getHttpClient()))
                .baseUrl("http://localhost:8084/detalle-respuesta")
                .defaultHeader(HttpHeaders.CONTENT_TYPE, MediaType.APPLICATION_JSON_VALUE)
                .defaultUriVariables(Collections.singletonMap("url", "http://localhost:8084/detalle-respuesta"))
                .build();
        JsonNode block = build.method(HttpMethod.GET).uri("/" + id)
                .retrieve().bodyToMono(JsonNode.class).block();
        StringBuilder sb = new StringBuilder(100);
        sb.append(block.get("texto").asText());
        return sb.toString();
    }
    
    public static Respuestas getInstance() {
        if (instance == null) {
            instance = new Respuestas();
        }
        return instance;
    }
}