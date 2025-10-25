package com.uniminuto.clinica.entity;

import java.io.Serializable;
import java.time.LocalDateTime;
import javax.persistence.*;
import lombok.Data;

/**
 * Entidad que representa una receta médica en el sistema.
 *
 * @author lmora
 */
@Entity
@Table(name = "receta")
public class Receta implements Serializable {

    /**
     * ID de serialización para la clase.
     */
    private static final long serialVersionUID = 1L;

    /**
     * Identificador único de la receta.
     */
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long id;

    /**
     * ID del medicamento prescrito.
     */
    @Column(name = "medicamento_id", nullable = false)
    private Integer medicamentoId;

    /**
     * Dosis del medicamento prescrito.
     */
    @Column(name = "dosis", nullable = false, columnDefinition = "TEXT")
    private String dosis;

    /**
     * Indicaciones para el uso del medicamento.
     */
    @Column(name = "indicaciones", columnDefinition = "TEXT")
    private String indicaciones;

    /**
     * Cita médica asociada a la receta.
     */
    @ManyToOne
    @JoinColumn(name = "cita_id", nullable = false)
    private Cita cita;

    /**
     * Fecha de creación del registro.
     */
    @Column(name = "fecha_creacion_registro")
    private LocalDateTime fechaCreacionRegistro;

    /**
     * Constructor por defecto que inicializa la fecha de creación.
     */
    public Receta() {
        this.fechaCreacionRegistro = LocalDateTime.now();
    }

    /**
     * Obtiene el ID de la receta.
     *
     * @return ID de la receta
     */
    public Long getId() {
        return id;
    }

    /**
     * Establece el ID de la receta.
     *
     * @param id ID de la receta
     */
    public void setId(Long id) {
        this.id = id;
    }

    /**
     * Obtiene el ID del medicamento.
     *
     * @return ID del medicamento
     */
    public Integer getMedicamentoId() {
        return medicamentoId;
    }

    /**
     * Establece el ID del medicamento.
     *
     * @param medicamentoId ID del medicamento
     */
    public void setMedicamentoId(Integer medicamentoId) {
        this.medicamentoId = medicamentoId;
    }

    /**
     * Obtiene la dosis del medicamento.
     *
     * @return dosis del medicamento
     */
    public String getDosis() {
        return dosis;
    }

    /**
     * Establece la dosis del medicamento.
     *
     * @param dosis dosis del medicamento
     */
    public void setDosis(String dosis) {
        this.dosis = dosis;
    }

    /**
     * Obtiene las indicaciones del medicamento.
     *
     * @return indicaciones del medicamento
     */
    public String getIndicaciones() {
        return indicaciones;
    }

    /**
     * Establece las indicaciones del medicamento.
     *
     * @param indicaciones indicaciones del medicamento
     */
    public void setIndicaciones(String indicaciones) {
        this.indicaciones = indicaciones;
    }

    /**
     * Obtiene la cita asociada.
     *
     * @return cita médica asociada
     */
    public Cita getCita() {
        return cita;
    }

    /**
     * Establece la cita asociada.
     *
     * @param cita cita médica asociada
     */
    public void setCita(Cita cita) {
        this.cita = cita;
    }

    /**
     * Obtiene la fecha de creación del registro.
     *
     * @return fecha de creación
     */
    public LocalDateTime getFechaCreacionRegistro() {
        return fechaCreacionRegistro;
    }

    /**
     * Establece la fecha de creación del registro.
     *
     * @param fechaCreacionRegistro fecha de creación
     */
    public void setFechaCreacionRegistro(LocalDateTime fechaCreacionRegistro) {
        this.fechaCreacionRegistro = fechaCreacionRegistro;
    }
}