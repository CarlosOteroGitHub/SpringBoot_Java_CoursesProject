package com.backend.answers.auxiliar;

public class Config {
    
    private static Config instance = null;
    private final String http_servicio_pregunta = "http://localhost:8083";
    private final String http_gateway_pregunta = "http://localhost:8090/gateway/api-pregunta";

    public String getHttp_servicio_pregunta() {
        return http_servicio_pregunta;
    }

    public String getHttp_gateway_pregunta() {
        return http_gateway_pregunta;
    }
    public static Config getInstance() {
        if (instance == null) {
            instance = new Config();
        }
        return instance;
    }
}