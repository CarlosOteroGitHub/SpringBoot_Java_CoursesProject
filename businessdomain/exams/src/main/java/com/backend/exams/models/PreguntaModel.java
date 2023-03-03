package com.backend.exams.models;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import io.swagger.v3.oas.annotations.media.Schema;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import lombok.Data;

@Entity
@Data
@Table(name = "preguntas")
@Schema(name="PreguntaModel", description = "Entidad de base de datos que representa las preguntas")
public class PreguntaModel {
    
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(unique = true, nullable = false)
    @Schema(name="id", required = true, example = "1", description = "Define el identificador del registro de la pregunta")
    private Long id;

    @Column(length = 100, unique = false, nullable = false)
    @NotEmpty
    @Schema(name="texto", required = true, example = "Answer", description = "Define el texto de la pregunta")
    private String texto;
    
    @JsonIgnoreProperties({"hibernateLazyInitializer", "handler"})
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "examen_id", unique = false, nullable = false)
    @NotNull
    @Schema(name="examen", required = true, example = "1", description = "Define la relación entre la Pregunta - Examen")
    private ExamenModel examen;
    
    @Override
    public boolean equals(Object object) {
        if (this == object) {
            return true;
        }

        if (!(object instanceof PreguntaModel)) {
            return false;
        }

        PreguntaModel preguntaModel = (PreguntaModel) object;
        return this.id != null && this.id.equals(preguntaModel.getId());
    }
}