package com.uniminuto.clinica.model;

import lombok.Data;


import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

@Data
public class CrearRecetaRq {
    @NotNull
    private Long citaId;
    @NotNull
    private Long medicamentoId;

    @NotBlank
    private String dosis;

    private String indicaciones;

}
