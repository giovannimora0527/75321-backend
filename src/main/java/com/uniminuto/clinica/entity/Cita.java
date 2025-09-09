package com.uniminuto.clinica.entity;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.Getter;
import lombok.Setter;

import javax.persistence.Entity;
import javax.persistence.*;
import java.time.LocalDateTime;

@Entity
@Table(name="cita")
@Getter
@Setter
public class Cita {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY) //LLave foranea cita.paciente_id a paciente.id
    @JoinColumn(name="paciente_id",nullable = false)
    @JsonIgnoreProperties({"hibernateLazyInitializer","handler"})
    private Paciente paciente;

    @ManyToOne(fetch = FetchType.LAZY) //cita.medico_id a medico.id
    @JoinColumn(name = "medico_id",nullable = false)
    @JsonIgnoreProperties({"hibernateLazyInitializer","handler"})
    private Medico medico;

    @Column(name="fecha_hora",nullable = false)
    private LocalDateTime fechaHora;

    @Column(name="estado",nullable = false,length=20)
    private String estado="PROGRAMADA";

    @Column(name="motivo",columnDefinition = "text")
    private String motivo;
}
