package com.uniminuto.clinica.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.Getter;
import lombok.Setter;
import lombok.EqualsAndHashCode;
import lombok.ToString;

import javax.persistence.*;
import java.io.Serializable;
import java.time.LocalDate;

@Getter @Setter //generar automaticamente
@EqualsAndHashCode(onlyExplicitlyIncluded = true)
@ToString(exclude = "usuario")
@Entity
@Table(name = "paciente")
public class Paciente implements Serializable {
    private static final long serialVersionUID = 1L; //Compatibilidad de serilializador

    @Id
    @EqualsAndHashCode.Include
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY, optional = true)
    @JoinColumn(name = "usuario_id", referencedColumnName = "id")
    @JsonIgnore
    private Usuario usuario;

    @Column(name = "tipo_documento", length = 10)
    private String tipoDocumento;

    @Column(name = "numero_documento", length = 20, unique = true)
    private String numeroDocumento;

    @Column(name = "nombres", length = 100)
    private String nombres;

    @Column(name = "apellidos", length = 100)
    private String apellidos;

    @Column(name = "fecha_nacimiento")
    private LocalDate fechaNacimiento;

    @Column(name = "genero", columnDefinition = "CHAR(1)")
    private String genero; // 'M' o 'F'

    @Column(name = "telefono", length = 20)
    private String telefono;

    @Column(name = "direccion", columnDefinition = "TEXT")
    private String direccion;
}
