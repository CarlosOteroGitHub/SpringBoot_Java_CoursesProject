package com.backend.alumns.controllers;

import com.backend.alumns.models.SexoModel;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest
public class AlumnoRestControllerTest {
    
    private final ObjectMapper mapper = new ObjectMapper();
    
    @Autowired
    private MockMvc mockMvc;
    
    @Test
    public void insertar_alumno() throws Exception {
        SexoModel sexoModel = new SexoModel(null, "Otro");
        mockMvc.perform(MockMvcRequestBuilders
                .post("/agregar-sexo")
                .content(mapper.writeValueAsString(sexoModel))
                .contentType(MediaType.APPLICATION_JSON)
                .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isCreated());
    }
}