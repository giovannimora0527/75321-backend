package com.uniminuto.clinica.model;

import lombok.Data;

@Data
public class MedicoRq {
    private Integer id;
    private String tipoDocumento;
    private String numeroDocumento;
    private String nombres;
    private String apellidos;
    private String telefono;
    private String registroProfesional;
    private Integer especializacion;
}
