package com.backend.courses.models;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.PrePersist;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.validation.constraints.NotEmpty;
import lombok.Data;

@Entity
@Data
@Table(name = "cursos")
public class CursoModel {
    
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(unique = true, nullable = false)
    private Long id;

    @Column(length = 50, unique = false, nullable = false)
    @NotEmpty
    private String nombre;
    
    @JsonIgnoreProperties(value = {"curso"}, allowSetters = true)
    @OneToMany(mappedBy = "curso", fetch = FetchType.LAZY, cascade = CascadeType.ALL, orphanRemoval = true)
    private List<CursoAlumnoModel> cursoAlumnos;
    
    public CursoModel() {
        this.cursoAlumnos = new ArrayList<>();
    }
    
    @Column(name = "create_at")
    @Temporal(TemporalType.TIMESTAMP)
    private Date createAt;
    
    @PrePersist
    public void prePersist() {
        this.createAt = new Date();
    }
    
    public void addCursoAlumno(CursoAlumnoModel cursoAlumnoModel) {
        this.cursoAlumnos.add(cursoAlumnoModel);
    }

    public void removeCursoAlumno(CursoAlumnoModel cursoAlumnoModel) {
        this.cursoAlumnos.remove(cursoAlumnoModel);
    }
}