package com.uniminuto.clinica.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
//Respuesta
public class RecetaRs {
    private Long id;
    private Long citaid;
    private Long medicamentoid;
    private String medicamentoNombre;
    private String dosis;
    private String indicaciones;
}
