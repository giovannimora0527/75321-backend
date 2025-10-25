package com.uniminuto.clinica.entity;

import java.io.Serializable;
import java.time.LocalDateTime;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;
import lombok.Data;

/**
 * Entidad que representa un paciente en el sistema de clínica.
 *
 * @author lmora
 */
@Data
@Entity
@Table(name = "paciente")
public class Paciente implements Serializable {

    /**
     * ID de serialización para la clase.
     */
    private static final long serialVersionUID = 1L;

    /**
     * Identificador único del paciente.
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
     * Nombres del paciente.
     */
    @Column(name = "nombres")
    private String nombre;

    /**
     * Apellidos del paciente.
     */
    @Column(name = "apellidos")
    private String apellido;

    /**
     * Número de teléfono del paciente.
     */
    @Column(name = "telefono")
    private String telefono;

    /**
     * Dirección de residencia del paciente.
     */
    @Column(name = "direccion")
    private String direccion;

    /**
     * Fecha de nacimiento del paciente.
     */
    @Column(name = "fecha_nacimiento")
    private LocalDateTime fechaNacimiento;

    /**
     * Fecha de creación del registro.
     */
    @Column(name = "fecha_creacion_registro")
    private LocalDateTime fechaCreacionRegistro;

    /**
     * Obtiene el ID del paciente.
     *
     * @return ID del paciente
     */
    public Integer getId() {
        return id;
    }

    /**
     * Establece el ID del paciente.
     *
     * @param id ID del paciente
     */
    public void setId(Integer id) {
        this.id = id;
    }

    /**
     * Obtiene el tipo de documento.
     *
     * @return tipo de documento
     */
    public String getTipoDocumento() {
        return tipoDocumento;
    }

    /**
     * Establece el tipo de documento.
     *
     * @param tipoDocumento tipo de documento
     */
    public void setTipoDocumento(String tipoDocumento) {
        this.tipoDocumento = tipoDocumento;
    }

    /**
     * Obtiene el número de documento.
     *
     * @return número de documento
     */
    public String getNumeroDocumento() {
        return numeroDocumento;
    }

    /**
     * Establece el número de documento.
     *
     * @param numeroDocumento número de documento
     */
    public void setNumeroDocumento(String numeroDocumento) {
        this.numeroDocumento = numeroDocumento;
    }

    /**
     * Obtiene el nombre del paciente.
     *
     * @return nombre del paciente
     */
    public String getNombre() {
        return nombre;
    }

    /**
     * Establece el nombre del paciente.
     *
     * @param nombre nombre del paciente
     */
    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    /**
     * Obtiene el apellido del paciente.
     *
     * @return apellido del paciente
     */
    public String getApellido() {
        return apellido;
    }

    /**
     * Establece el apellido del paciente.
     *
     * @param apellido apellido del paciente
     */
    public void setApellido(String apellido) {
        this.apellido = apellido;
    }

    /**
     * Obtiene el teléfono del paciente.
     *
     * @return teléfono del paciente
     */
    public String getTelefono() {
        return telefono;
    }

    /**
     * Establece el teléfono del paciente.
     *
     * @param telefono teléfono del paciente
     */
    public void setTelefono(String telefono) {
        this.telefono = telefono;
    }

    /**
     * Obtiene la dirección del paciente.
     *
     * @return dirección del paciente
     */
    public String getDireccion() {
        return direccion;
    }

    /**
     * Establece la dirección del paciente.
     *
     * @param direccion dirección del paciente
     */
    public void setDireccion(String direccion) {
        this.direccion = direccion;
    }

    /**
     * Obtiene la fecha de nacimiento.
     *
     * @return fecha de nacimiento
     */
    public LocalDateTime getFechaNacimiento() {
        return fechaNacimiento;
    }

    /**
     * Establece la fecha de nacimiento.
     *
     * @param fechaNacimiento fecha de nacimiento
     */
    public void setFechaNacimiento(LocalDateTime fechaNacimiento) {
        this.fechaNacimiento = fechaNacimiento;
    }
}