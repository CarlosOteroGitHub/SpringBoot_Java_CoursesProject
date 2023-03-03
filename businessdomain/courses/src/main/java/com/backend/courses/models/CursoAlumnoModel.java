package com.backend.courses.models;

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
@Table(name = "cursos_has_alumnos")
@Schema(name="CursoAlumnoModel", description = "Entidad de base de datos que representa la relación entre cursos y alumnos")
public class CursoAlumnoModel {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(unique = true, nullable = false)
    @Schema(name="id", required = true, example = "1", description = "Define el identificador del registro del curso - alumno")
    private Long id;
    
    @Column(name="alumno_id", unique = false, nullable = false)
    @NotEmpty
    @Schema(name="alumnoId", required = true, example = "1", description = "Define la relación con el ID del alumno")
    private Long alumnoId;
    
    @JsonIgnoreProperties({"hibernateLazyInitializer", "handler"})
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "curso_id")
    @NotNull
    @Schema(name="curso", required = true, example = "1", description = "Define la relación con el ID del curso")
    private CursoModel curso;
    
    @Override
    public boolean equals(Object object) {
        if (this == object) {
            return true;
        }

        if (!(object instanceof CursoAlumnoModel)) {
            return false;
        }

        CursoAlumnoModel cursoAlumnoModel = (CursoAlumnoModel) object;
        return this.alumnoId != null && this.alumnoId.equals(cursoAlumnoModel.getAlumnoId());
    }
}
