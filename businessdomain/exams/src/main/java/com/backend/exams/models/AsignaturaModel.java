package com.backend.exams.models;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
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
@Table(name = "asignaturas")
public class AsignaturaModel {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(unique = true, nullable = false)
    private Long id;

    @Column(length = 50, unique = false, nullable = false)
    @NotEmpty
    private String nombre;

    @JsonIgnoreProperties(value = {"asignatura"}, allowSetters = true)
    @OneToMany(mappedBy = "asignatura", fetch = FetchType.LAZY, cascade = CascadeType.ALL, orphanRemoval = true)
    private List<ExamenModel> examenes;

    public AsignaturaModel() {
        this.examenes = new ArrayList<>();
    }
}