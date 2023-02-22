package com.backend.exams.models;

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
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.PrePersist;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.persistence.Transient;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import lombok.Data;

@Entity
@Data
@Table(name = "examenes")
public class ExamenModel {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(unique = true, nullable = false)
    private Long id;

    @Column(length = 50, unique = false, nullable = false)
    @NotEmpty
    private String nombre;

    @Column(name = "create_at")
    @Temporal(TemporalType.TIMESTAMP)
    private Date createAt;

    @Transient
    private boolean respondido;
    
    @JsonIgnoreProperties({"hibernateLazyInitializer", "handler"})
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "asignatura_id", unique = false, nullable = false)
    @NotNull
    private AsignaturaModel asignatura;
    
    @JsonIgnoreProperties(value = {"examen"}, allowSetters = true)
    @OneToMany(mappedBy = "examen", fetch = FetchType.LAZY, cascade = CascadeType.ALL, orphanRemoval = true)
    private List<PreguntaModel> preguntas;

    @PrePersist
    public void prePersist() {
        this.createAt = new Date();
    }

    public ExamenModel() {
        this.preguntas = new ArrayList<>();
    }

    public List<PreguntaModel> getPreguntas() {
        return preguntas;
    }

    public void setPreguntas(List<PreguntaModel> preguntaModel) {
        this.preguntas.clear();
        preguntaModel.forEach(p -> {
            this.addPregunta(p);
        });
    }

    public void addPregunta(PreguntaModel preguntaModel) {
        this.preguntas.add(preguntaModel);
        preguntaModel.setExamen(this);
    }

    public void removePregunta(PreguntaModel preguntaModel) {
        this.preguntas.remove(preguntaModel);
        preguntaModel.setExamen(null);
    }

    @Override
    public boolean equals(Object object) {
        if (this == object) {
            return true;
        }

        if (!(object instanceof ExamenModel)) {
            return false;
        }

        ExamenModel examenModel = (ExamenModel) object;
        return this.id != null && this.id.equals(examenModel.getId());
    }
}
