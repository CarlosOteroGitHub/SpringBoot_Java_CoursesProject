package com.backend.courses.auxiliar;

public class Config {
    
    private static Config instance = null;
    private final String http_servicio_alumno = "http://localhost:8081";
    private final String http_gateway_alumno = "http://localhost:8090/gateway/api-alumno";

    public String getHttp_servicio_alumno() {
        return http_servicio_alumno;
    }

    public String getHttp_gateway_alumno() {
        return http_gateway_alumno;
    }
    
    public static Config getInstance() {
        if (instance == null) {
            instance = new Config();
        }
        return instance;
    }
}