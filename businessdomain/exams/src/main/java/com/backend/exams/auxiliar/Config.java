package com.backend.exams.auxiliar;

public class Config {
    
    private static Config instance = null;
    private final String http_servicio_curso = "http://localhost:8082";
    private final String http_gateway_curso = "http://localhost:8090/gateway/api-curso";

    public String getHttp_servicio_curso() {
        return http_servicio_curso;
    }

    public String getHttp_gateway_curso() {
        return http_gateway_curso;
    }
    
    public static Config getInstance() {
        if (instance == null) {
            instance = new Config();
        }
        return instance;
    }
}