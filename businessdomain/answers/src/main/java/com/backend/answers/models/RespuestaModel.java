package com.backend.answers.models;

import io.swagger.v3.oas.annotations.media.Schema;
import java.io.Serializable;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.persistence.Transient;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.Size;
import lombok.Data;

@Entity
@Data
@Table(name = "respuestas")
@Schema(name="RespuestaModel", description = "Entidad de base de datos que representa las respuestas")
public class RespuestaModel implements Serializable {
    
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(unique = true, nullable = false)
    @Schema(name="id", required = true, example = "1", description = "Define el identificador del registro de la respuesta")
    private Long id;
    
    @Column(length = 50, unique = false, nullable = false)
    @NotEmpty
    @Size(min = 4, max = 30)
    @Schema(name="texto", required = true, example = "Answer", description = "Define el texto de la respuesta")
    private String texto;
    
    @Column(name="pregunta_id", unique = false, nullable = false)
    @Schema(name="preguntaId", required = true, example = "1", description = "Define la relación con el ID de la pregunta")
    private Long preguntaId;
    
    @Transient
    @Schema(name="preguntaTexto", required = false, example = "Respuesta 1", description = "Define el texto de una pregunta afiliada a una pregunta")
    private String preguntaTexto;
    
    @Override
    public boolean equals(Object object) {
        if (this == object) {
            return true;
        }

        if (!(object instanceof RespuestaModel)) {
            return false;
        }

        RespuestaModel respuestaModel = (RespuestaModel) object;
        return this.id != null && this.id.equals(respuestaModel.getId());
    }
}