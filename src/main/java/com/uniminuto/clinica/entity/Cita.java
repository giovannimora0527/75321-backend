package com.uniminuto.clinica.entity;

import lombok.Data;

import javax.persistence.*;
import java.time.LocalDateTime;

@Entity
@Table(name = "cita")
@Data
public class Cita {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "paciente_id", nullable = false)
    private Paciente paciente;

    @ManyToOne
    @JoinColumn(name = "medico_id", nullable = false)
    private Medico medico;

    @Column(name = "fecha_hora", nullable = false)
    private LocalDateTime fechaHora;

    @Column(nullable = false)
    @Enumerated(EnumType.STRING)
    private EstadoCita estado = EstadoCita.programada;

    @Column(columnDefinition = "TEXT")
    private String motivo;

    public enum EstadoCita {
        programada, confirmada, atendida, cancelada
    }
}