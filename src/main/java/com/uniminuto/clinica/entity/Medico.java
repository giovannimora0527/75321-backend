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
 * Entidad que representa un médico en el sistema de clínica.
 *
 * @author lmora
 */
@Data
@Entity
@Table(name = "medico")
public class Medico implements Serializable {

    /**
     * ID de serialización para la clase.
     */
    private static final long serialVersionUID = 1L;

    /**
     * Identificador único del médico.
     */
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Integer id;

    /**
     * Tipo de documento de identidad.
     */
    @Column(name = "tipo_documento")
    private String tipoDocumento;

    /**
     * Número de documento de identidad.
     */
    @Column(name = "numero_documento")
    private String numeroDocumento;

    /**
     * Nombres del médico.
     */
    @Column(name = "nombres")
    private String nombres;

    /**
     * Apellidos del médico.
     */
    @Column(name = "apellidos")
    private String apellidos;

    /**
     * Número de teléfono del médico.
     */
    @Column(name = "telefono")
    private String telefono;

    /**
     * Registro profesional del médico.
     */
    @Column(name = "registro_profesional")
    private String registroProfesional;

    /**
     * Especialización médica del profesional.
     */
    @ManyToOne
    @JoinColumn(name="especializacion_id")
    private Especializacion especializacion;

    /**
     * Fecha de creación del registro.
     */
    @Column(name = "fecha_creacion_registro")
    private LocalDateTime fechaCreacionRegistro;

    /**
     * Constructor por defecto que inicializa la fecha de creación.
     */
    public Medico() {
        this.fechaCreacionRegistro = LocalDateTime.now();
    }

    /**
     * Obtiene el ID del médico.
     *
     * @return ID del médico
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