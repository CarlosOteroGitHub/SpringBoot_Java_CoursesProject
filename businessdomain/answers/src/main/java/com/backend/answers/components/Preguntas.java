package com.backend.answers.components;

import com.backend.answers.auxiliar.Config;
import com.backend.answers.auxiliar.HttpClientCommunication;
import com.fasterxml.jackson.databind.JsonNode;
import java.util.Collections;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.MediaType;
import org.springframework.http.client.reactive.ReactorClientHttpConnector;
import org.springframework.web.reactive.function.client.WebClient;

public class Preguntas {
    
    private static Preguntas instance = null;
    
    public boolean validIdPregunta(int id) {
        boolean bandera = true;
        WebClient build = HttpClientCommunication.getInstance().getWebClientBuilder().clientConnector(new ReactorClientHttpConnector(HttpClientCommunication.getInstance().getHttpClient()))
                .baseUrl(Config.getInstance().getHttp_servicio_pregunta()+ "/detalle-pregunta")
                .defaultHeader(HttpHeaders.CONTENT_TYPE, MediaType.APPLICATION_JSON_VALUE)
                .defaultUriVariables(Collections.singletonMap("url", Config.getInstance().getHttp_servicio_pregunta() + "/detalle-pregunta"))
                .build();
        JsonNode block = build.method(HttpMethod.GET).uri("/" + id)
                .retrieve().bodyToMono(JsonNode.class).block();

        if (block.get("id").asText().isEmpty()) {
            bandera = false;
        }
        return bandera;
    }

    public String getPreguntaName(int id) {
        WebClient build = HttpClientCommunication.getInstance().getWebClientBuilder().clientConnector(new ReactorClientHttpConnector(HttpClientCommunication.getInstance().getHttpClient()))
                .baseUrl(Config.getInstance().getHttp_servicio_pregunta() + "/detalle-pregunta")
                .defaultHeader(HttpHeaders.CONTENT_TYPE, MediaType.APPLICATION_JSON_VALUE)
                .defaultUriVariables(Collections.singletonMap("url", Config.getInstance().getHttp_servicio_pregunta() + "/detalle-pregunta"))
                .build();
        JsonNode block = build.method(HttpMethod.GET).uri("/" + id)
                .retrieve().bodyToMono(JsonNode.class).block();
        StringBuilder sb = new StringBuilder(100);
        sb.append(block.get("texto").asText());
        return sb.toString();
    }
    
    public static Preguntas getInstance() {
        if (instance == null) {
            instance = new Preguntas();
        }
        return instance;
    }
}