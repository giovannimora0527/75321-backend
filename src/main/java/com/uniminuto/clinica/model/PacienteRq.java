package com.uniminuto.clinica.model;

import lombok.Data;

public class PacienteRq {

    private String id;
    private String usuario_id;
    private String tipo_documento;
    private String numero_documento;
    private String nombres;
    private String apellidos;              
    private String fecha_nacimiento;
    private String genero;
    private String telefono;
    private String direccion;
}
