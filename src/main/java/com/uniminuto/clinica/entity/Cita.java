package com.uniminuto.clinica.entity;

import java.io.Serializable;
import java.time.LocalDateTime;
import javax.persistence.*;
import lombok.Data;

@Data
@Entity
@Table (name = "cita")
public class Cita implements Serializable {


    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)

    @Column(name = "id")
    private Long id;

    // Relación con Usuario (paciente)
    @Column(name = "paciente_id")
    private Integer pacienteId;

    // Relación con Medico
    @Column(name = "medico_id")
    private Integer medicoId;

    @Column(name = "fecha_hora")
    private LocalDateTime fechaHora;

    @Column(name = "estado")
    private String estado;

    @Column(name = "motivo")
    private String motivo;

}
