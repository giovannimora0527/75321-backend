package com.uniminuto.clinica.entity;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.CreationTimestamp;

import javax.persistence.*;
import java.io.Serializable;
import java.time.LocalDateTime;

@Data
@Entity
@NoArgsConstructor
@AllArgsConstructor
@Table(name="receta")
public class Receta implements Serializable {

    private static final long serialVersionUID=1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name="id")
    private Long id;

    //Llave foranea receta.cita_id a cita.id
    @ManyToOne(fetch = FetchType.LAZY,optional = false)
    @JoinColumn(name="cita_id",
            nullable = false,
            foreignKey = @ForeignKey(name = "fk_receta_cita")
    )
    @JsonIgnoreProperties({"hibernateLazyInitializer","handler"})
    private Cita cita;

    @ManyToOne(fetch = FetchType.LAZY,optional = false)
    @JoinColumn(
            name="medicamento_id",
            nullable = false,
            foreignKey = @ForeignKey(name="fk_receta_medicamento")
    )
    @JsonIgnoreProperties({"hibernateLazyInitializer","handler"})
    private Medicamento medicamento;

    @Column(name="dosis",columnDefinition = "text",nullable = false)
    private String dosis;

    @Column(name = "indicaciones",columnDefinition = "text")
    private String indicaciones;

    //Nuevo campo
    @Column(name="fecha_creacion_registro",nullable = false)
    @CreationTimestamp
    private LocalDateTime fechaCreacionRegistro;

    @Column(name = "fecha_actualizacion_registro")
    private LocalDateTime fechaActualizacionRegistro;


}
