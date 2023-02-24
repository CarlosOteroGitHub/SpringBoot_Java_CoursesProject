package com.backend.courses.models;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
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
public class CursoAlumnoModel {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(unique = true, nullable = false)
    private Long id;
    
    @Column(name="alumno_id", unique = false, nullable = false)
    @NotEmpty
    private Long alumnoId;
    
    @JsonIgnoreProperties({"hibernateLazyInitializer", "handler"})
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "curso_id")
    @NotNull
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
