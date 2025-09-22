package com.uniminuto.clinica.entity;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;
import lombok.Data;

/**
 *
 * @author AORUS
 */

@Data
@Entity
@Table(name = "paciente")
public class Paciente implements Serializable{
    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Integer id;
    
    @Column (name = "usuario_id")
    private Integer UsrId;
    
    @Column (name = "tipo_documento")
    private String TipDoc;
    
    @Column (name = "numero_documento")
    private String numDoc;
    
    @Column (name = "nombres")
    private String Nom;
    
    @Column (name = "apellidos")
    private String Ape;

    @Column(name = "fecha_nacimiento")
    private String fechaNacimiento;
    
    @Column (name = "genero")
    private String Gen;
    
    @Column (name = "telefono")
    private String Tel;
    
    @Column (name = "direccion")
    private String Dir;
}

