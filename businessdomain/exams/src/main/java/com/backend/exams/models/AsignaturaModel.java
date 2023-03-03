package com.backend.exams.models;

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
@Table(name = "asignaturas")
@Schema(name="AsignaturaModel", description = "Entidad de base de datos que representa las asignaturas")
public class AsignaturaModel {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(unique = true, nullable = false)
    @Schema(name="id", required = true, example = "1", description = "Define el identificador del registro de la asignatura")
    private Long id;

    @Column(length = 50, unique = false, nullable = false)
    @NotEmpty
    @Schema(name="nombre", required = true, example = "Asignatura 1", description = "Define el nombre de la asignatura")
    private String nombre;

    @JsonIgnoreProperties(value = {"asignatura"}, allowSetters = true)
    @OneToMany(mappedBy = "asignatura", fetch = FetchType.LAZY, cascade = CascadeType.ALL, orphanRemoval = true)
    private List<ExamenModel> examenes;

    public AsignaturaModel() {
        this.examenes = new ArrayList<>();
    }
}