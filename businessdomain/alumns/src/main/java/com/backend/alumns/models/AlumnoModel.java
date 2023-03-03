package com.backend.alumns.models;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import io.swagger.v3.oas.annotations.media.Schema;
import java.util.Date;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.Lob;
import javax.persistence.ManyToOne;
import javax.persistence.PrePersist;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.validation.constraints.Email;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import lombok.Data;

@Entity
@Data
@Table(name = "alumnos")
@Schema(name="AlumnoModel", description = "Entidad de base de datos que representa los alumnos")
public class AlumnoModel {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(unique = true, nullable = false)
    @Schema(name="id", required = true, example = "1", description = "Define el identificador del registro del alumno")
    private Long id;

    @Column(length = 50, unique = false, nullable = false)
    @NotEmpty
    @Size(min = 4, max = 30)
    @Schema(name="nombre", required = true, example = "Carlos", description = "Define el nombre del alumno")
    private String nombre;

    @Column(length = 50, unique = false, nullable = false)
    @NotEmpty
    @Size(min = 3, max = 30)
    @Schema(name="apellido", required = true, example = "Otero", description = "Define el apellido del alumno")
    private String apellido;

    @Column(length = 50, unique = true, nullable = false)
    @NotEmpty
    @Email
    @Schema(name="email", required = true, example = "carlos.otero.r12@gmail.com", description = "Define el email del alumno")
    private String email;

    @Lob
    @JsonIgnore
    @Schema(name="foto", required = true, example = "foto.png", description = "Define la foto del alumno")
    private byte[] foto;

    @Column(name = "create_at")
    @Temporal(TemporalType.TIMESTAMP)
    @Schema(name="createAt", required = true, example = "2023-02-18T06:33:59", description = "Define la fecha/hora de creación del registro")
    private Date createAt;

    @JsonIgnoreProperties({"hibernateLazyInitializer", "handler"})
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "sexo_id")
    @NotNull
    @Schema(name="sexo", required = true, example = "Masculino/Femenino", description = "Define la relación entre el Alumno - Sexo")
    private SexoModel sexo;

    @PrePersist
    public void prePersist() {
        this.createAt = new Date();
    }

    public Integer getFotoHashCode() {
        return (this.foto != null) ? this.foto.hashCode() : null;
    }

    @Override
    public boolean equals(Object object) {
        if (this == object) {
            return true;
        }

        if (!(object instanceof AlumnoModel)) {
            return false;
        }

        AlumnoModel alumnoModel = (AlumnoModel) object;
        return this.id != null && this.id.equals(alumnoModel.getId());
    }
}
