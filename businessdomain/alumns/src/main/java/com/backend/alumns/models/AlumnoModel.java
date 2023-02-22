package com.backend.alumns.models;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
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
public class AlumnoModel {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(unique = true, nullable = false)
    private Long id;

    @Column(length = 50, unique = false, nullable = false)
    @NotEmpty
    @Size(min = 4, max = 30)
    private String nombre;

    @Column(length = 50, unique = false, nullable = false)
    @NotEmpty
    @Size(min = 3, max = 30)
    private String apellido;

    @Column(length = 50, unique = true, nullable = false)
    @NotEmpty
    @Email
    private String email;

    @Lob
    @JsonIgnore
    private byte[] foto;

    @Column(name = "create_at")
    @Temporal(TemporalType.TIMESTAMP)
    private Date createAt;

    @JsonIgnoreProperties({"hibernateLazyInitializer", "handler"})
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "sexo_id")
    @NotNull
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
