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
import javax.persistence.Transient;
import javax.validation.constraints.NotNull;
import lombok.Data;

@Entity
@Data
@Table(name = "examenes_has_cursos")
@Schema(name="CursoAlumnoModel", description = "Entidad de base de datos que representa la relación entre examenes y cursos")
public class ExamenCursoModel {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(unique = true, nullable = false)
    @Schema(name="id", required = true, example = "1", description = "Define el identificador del registro del curso - alumno")
    private Long id;
    
    @Column(name="curso_id", unique = false, nullable = false)
    @Schema(name="cursoId", required = true, example = "1", description = "Define la relación con el ID del curso")
    private Long cursoId;
    
    @Transient
    @Schema(name="cursoNombre", required = false, example = "Cálculo Integral", description = "Define el nombre del curso afiliado a un examen")
    private String cursoNombre;
    
    @JsonIgnoreProperties({"hibernateLazyInitializer", "handler"})
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "examen_id")
    @NotNull
    @Schema(name="examen", required = true, example = "1", description = "Define la relación con el ID del Examen")
    private ExamenModel examen;
    
    @Override
    public boolean equals(Object object) {
        if (this == object) {
            return true;
        }

        if (!(object instanceof ExamenCursoModel)) {
            return false;
        }

        ExamenCursoModel cursoAlumnoModel = (ExamenCursoModel) object;
        return this.cursoId != null && this.cursoId.equals(cursoAlumnoModel.getCursoId());
    }
}