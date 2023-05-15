package com.backend.exams.auxiliar;

public class Config {
    
    private static Config instance = null;
    private final String http_gateway = "http://localhost:8090/gateway";

    public String getHttp_gateway() {
        return http_gateway;
    }
    
    public static Config getInstance() {
        if (instance == null) {
            instance = new Config();
        }
        return instance;
    }
}