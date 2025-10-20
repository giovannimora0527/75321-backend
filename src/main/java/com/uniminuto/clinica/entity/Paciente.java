package com.uniminuto.clinica.entity;

import java.io.Serializable;
import java.time.LocalDate;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;
import lombok.Data;

/**
 * @author AORUS
 */

@Data
@Entity
@Table(name = "paciente")
public class Paciente implements Serializable {
    private static final long serialVersionUID = 1L;
    
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Integer id;
    
    @Column(name = "usuario_id")
    private Integer usuarioId;  
    
    @Column(name = "tipo_documento")
    private String tipoDocumento;  
    
    @Column(name = "numero_documento")
    private String numeroDocumento;  
    
    @Column(name = "nombres")
    private String nombres; 
    
    @Column(name = "apellidos")
    private String apellidos;  
    
    @Column(name = "fecha_nacimiento")
    private LocalDate fechaNacimiento;  
    
    @Column(name = "genero")
    private String genero;  
    
    @Column(name = "telefono")
    private String telefono;  
    
    @Column(name = "direccion")
    private String direccion;  
}
