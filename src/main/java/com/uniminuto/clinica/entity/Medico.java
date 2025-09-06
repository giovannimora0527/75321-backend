package com.uniminuto.clinica.entity;

import lombok.Data;

import javax.persistence.*;

@Data
@Entity
@Table(name = "medico")
public class Medico {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long id;

    @Column(name = "tipo_documento")
    private String tipoDocumento;

    @Column(name = "numero_documento")
    private String numeroDocumento;

    @Column(name = "nombres")
    private String nombres;

    @Column(name = "apellidos")
    private String apellidos;

    @Column(name = "telefono")
    private String telefono;

    @Column(name = "registro_profesional")
    private String registroProfesional;


    @ManyToOne
    @JoinColumn(name="especializacion_id")//Unir tablas 'Join'
    private Especializacion especializacion; //Debe ser igual a la base de datos

}
