package com.backend.courses.components;

import com.backend.courses.auxiliar.Config;
import com.backend.courses.auxiliar.HttpClientCommunication;
import com.fasterxml.jackson.databind.JsonNode;
import java.util.Collections;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.MediaType;
import org.springframework.http.client.reactive.ReactorClientHttpConnector;
import org.springframework.web.reactive.function.client.WebClient;

public class Alumnos {

    private static Alumnos instance = null;

    public boolean validIdAlumno(int id) {
        boolean bandera = true;
        WebClient build = HttpClientCommunication.getInstance().getWebClientBuilder().clientConnector(new ReactorClientHttpConnector(HttpClientCommunication.getInstance().getHttpClient()))
                .baseUrl(Config.getInstance().getHttp_gateway() + "/api-alumno/detalle-alumno")
                .defaultHeader(HttpHeaders.CONTENT_TYPE, MediaType.APPLICATION_JSON_VALUE)
                .defaultUriVariables(Collections.singletonMap("url", Config.getInstance().getHttp_gateway() + "/api-alumno/detalle-alumno"))
                .build();
        JsonNode block = build.method(HttpMethod.GET).uri("/" + id)
                .retrieve().bodyToMono(JsonNode.class).block();

        if (block.get("id").asText().isEmpty()) {
            bandera = false;
        }
        return bandera;
    }

    public String getAlumnoName(int id) {
        WebClient build = HttpClientCommunication.getInstance().getWebClientBuilder().clientConnector(new ReactorClientHttpConnector(HttpClientCommunication.getInstance().getHttpClient()))
                .baseUrl(Config.getInstance().getHttp_gateway() + "/api-alumno/detalle-alumno")
                .defaultHeader(HttpHeaders.CONTENT_TYPE, MediaType.APPLICATION_JSON_VALUE)
                .defaultUriVariables(Collections.singletonMap("url", Config.getInstance().getHttp_gateway() + "/api-alumno/detalle-alumno"))
                .build();
        JsonNode block = build.method(HttpMethod.GET).uri("/" + id)
                .retrieve().bodyToMono(JsonNode.class).block();
        StringBuilder sb = new StringBuilder(100);
        sb.append(block.get("nombre").asText()).append(" ").append(block.get("apellido").asText());
        return sb.toString();
    }

    public static Alumnos getInstance() {
        if (instance == null) {
            instance = new Alumnos();
        }
        return instance;
    }
}
