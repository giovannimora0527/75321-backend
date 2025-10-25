package com.uniminuto.clinica.entity;

import java.io.Serializable;

import jakarta.persistence.*;
import lombok.Data;

/**
 *
 * @author lmora
 */
@Data
@Entity
@Table(name = "medico")
public class Medico implements Serializable {
    /**
     * Id serializable.
     */
    private static final long serialVersionUID = 1L;
    
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
    @JoinColumn(name="especializacion_id")
    private Especializacion especializacion;
    
}
