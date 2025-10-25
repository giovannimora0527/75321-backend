package com.uniminuto.clinica.entity;

import java.io.Serializable;
import java.time.LocalDateTime;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import lombok.Data;

/**
 * Entidad que representa una cita médica en el sistema.
 *
 * @author lmora
 */
@Entity
@Data
public class Cita implements Serializable {

    /**
     * Identificador único de la cita.
     */
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    /**
     * Fecha y hora de la cita médica.
     */
    private LocalDateTime fechaHora;

    /**
     * Estado actual de la cita (PENDIENTE, COMPLETADA, CANCELADA).
     */
    @Column(nullable = false)
    private String estado = "PENDIENTE";

    /**
     * Motivo de la consulta médica.
     */
    private String motivo;

    /**
     * Médico asignado a la cita.
     */
    @ManyToOne
    @JoinColumn(name = "medico_id", nullable = false)
    private Medico medico;

    /**
     * Paciente que solicita la cita.
     */
    @ManyToOne
    @JoinColumn(name = "paciente_id", nullable = false)
    private Paciente paciente;

    /**
     * Fecha de creación del registro.
     */
    @Column(name = "fecha_creacion_registro")
    private LocalDateTime fechaCreacionRegistro;

    /**
     * Constructor por defecto que inicializa la fecha de creación.
     */
    public Cita() {
        this.fechaCreacionRegistro = LocalDateTime.now();
    }

    /**
     * Establece el paciente de la cita.
     *
     * @param paciente paciente a asignar
     */
    public void setPaciente(Paciente paciente) {
        this.paciente = paciente;
    }

    /**
     * Establece el médico de la cita.
     *
     * @param medico médico a asignar
     */
    public void setMedico(Medico medico) {
        this.medico = medico;
    }

    /**
     * Obtiene el paciente de la cita.
     *
     * @return paciente asignado
     */
    public Paciente getPaciente() {
        return paciente;
    }

    /**
     * Obtiene el médico de la cita.
     *
     * @return médico asignado
     */
    public Medico getMedico() {
        return medico;
    }

    /**
     * Obtiene el ID de la cita.
     *
     * @return ID de la cita
     */
    public Number getId() {
        return id;
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