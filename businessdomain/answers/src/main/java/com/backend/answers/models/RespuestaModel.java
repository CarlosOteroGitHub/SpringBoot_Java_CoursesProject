package com.backend.answers.models;

import java.io.Serializable;
import javax.persistence.Id;
import javax.validation.constraints.NotNull;
import lombok.Data;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.data.mongodb.core.index.Indexed;

@Data
@Document(collection = "respuestas")
public class RespuestaModel implements Serializable {
    
    @Id
    @NotNull
    private Long id;
    
    @NotNull
    @Indexed(unique = true)
    private String texto;
}