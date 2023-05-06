package com.backend.exams.auxiliar;

import java.util.HashMap;
import java.util.Map;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;

public class Auxiliar {
    
    private static Auxiliar instance = null;
    
    public ResponseEntity<?> mensajes_error(BindingResult result){
        Map<String, Object> errores = new HashMap<>();
        result.getFieldErrors().forEach(err -> {
            errores.put(err.getField(), "El campo " + err.getField() + " " + err.getDefaultMessage());
        });
        return ResponseEntity.badRequest().body(errores);
    }
    
    public static Auxiliar getInstance() {
        if (instance == null) {
            instance = new Auxiliar();
        }
        return instance;
    }
}