package com.backend.alumns.models;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import io.swagger.v3.oas.annotations.media.Schema;
import java.util.ArrayList;
import java.util.List;
import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.validation.constraints.NotEmpty;
import lombok.Data;

@Entity
@Data
@Table(name = "sexo")
@Schema(name="SexoModel", description = "Entidad de base de datos que representa los tipos de sexo")
public class SexoModel {
    
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(unique = true, nullable = false)
    @Schema(name="id", required = true, example = "1", description = "Define el identificador del registro del sexo")
    private Long id;

    @Column(length = 20, unique = true, nullable = false)
    @NotEmpty
    @Schema(name="nombre", required = true, example = "Masculino/Femenino", description = "Define el nombre del sexo")
    private String nombre;
    
    @JsonIgnoreProperties(value = {"sexo"}, allowSetters = true)
    @OneToMany(mappedBy = "sexo", fetch = FetchType.LAZY, cascade = CascadeType.ALL, orphanRemoval = true)
    private List<AlumnoModel> alumnos;

    public SexoModel() {
        this.alumnos = new ArrayList<>();
    }

    public SexoModel(Long id, String nombre) {
        this.id = id;
        this.nombre = nombre;
    }

    public void setAlumnos(List<AlumnoModel> alumnos) {
        this.alumnos.clear();
        alumnos.forEach(p -> this.addAlumno(p));
    }
    
    public void addAlumno(AlumnoModel alumno){
        this.alumnos.add(alumno);
        alumno.setSexo(this);
    }
    
    public void removeAlumno(AlumnoModel alumno){
        this.alumnos.remove(alumno);
        alumno.setSexo(null);
    }
}