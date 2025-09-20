package com.uniminuto.clinica.entity;

import javax.persistence.*;
import lombok.Data;
import java.util.Date;

@Entity
@Table(name = "paciente")
@Data
public class Paciente {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "usuario_id")
    private Integer usuarioId;

    @Column(name = "tipo_documento")
    private String tipoDocumento;

    @Column(name = "numero_documento")
    private String numeroDocumento;

    private String nombres;

    private String apellidos;

    @Temporal(TemporalType.DATE)
    @Column(name = "fecha_nacimiento")
    private Date fechaNacimiento;

    private String genero;

    private String telefono;

    private String direccion;
}