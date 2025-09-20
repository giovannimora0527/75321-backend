package com.uniminuto.clinica.entity;

import java.io.Serializable;
import java.time.LocalDateTime;
import javax.persistence.*;

import lombok.Data;

@Entity
@Table(name = "receta")
public class Receta implements Serializable {
    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long id;

    // ✅ Columna que SÍ existe en la BD
    @Column(name = "medicamento_id", nullable = false)
    private Integer medicamentoId;

    // ✅ Columna que SÍ existe en la BD
    @Column(name = "dosis", nullable = false, columnDefinition = "TEXT")
    private String dosis;

    // ✅ Columna que SÍ existe en la BD
    @Column(name = "indicaciones", columnDefinition = "TEXT")
    private String indicaciones;

    // ✅ Relación con cita (columna cita_id existe en BD)
    @ManyToOne
    @JoinColumn(name = "cita_id", nullable = false)
    private Cita cita;



    // ✅ Campo adicional que necesitas agregar a la BD después
    @Column(name = "fecha_creacion_registro")
    private LocalDateTime fechaCreacionRegistro;

    // ✅ Constructor
    public Receta() {
        this.fechaCreacionRegistro = LocalDateTime.now();
    }

    // ✅ Getters y Setters
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Integer getMedicamentoId() {
        return medicamentoId;
    }

    public void setMedicamentoId(Integer medicamentoId) {
        this.medicamentoId = medicamentoId;
    }

    public String getDosis() {
        return dosis;
    }

    public void setDosis(String dosis) {
        this.dosis = dosis;
    }

    public String getIndicaciones() {
        return indicaciones;
    }

    public void setIndicaciones(String indicaciones) {
        this.indicaciones = indicaciones;
    }

    public Cita getCita() {
        return cita;
    }

    public void setCita(Cita cita) {
        this.cita = cita;
    }

    public LocalDateTime getFechaCreacionRegistro() {
        return fechaCreacionRegistro;
    }

    public void setFechaCreacionRegistro(LocalDateTime fechaCreacionRegistro) {
        this.fechaCreacionRegistro = fechaCreacionRegistro;
    }
}
