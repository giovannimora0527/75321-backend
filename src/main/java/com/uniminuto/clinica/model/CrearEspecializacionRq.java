package com.uniminuto.clinica.model;

import lombok.Data;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;

//Lo que enviamos al frontend
@Data
public class CrearEspecializacionRq {
    @NotBlank(message = "El nombre es obligatorio")
    @Size(min = 3, max = 100, message = "El nombre debe tener entre 3 y 100 caracteres")
    private String nombre;

    @NotBlank(message = "La descripción es obligatoria")
    @Size(min = 5, message = "La descripción debe tener al menos 5 caracteres")
    private String descripcion;

    @NotBlank(message = "El código de especialización es obligatorio")
    @Size(min = 3, max = 10, message = "El código debe tener entre 3 y 10 caracteres")
    private String codigoEspecializacion;
}
