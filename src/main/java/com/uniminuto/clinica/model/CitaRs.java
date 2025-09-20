package com.uniminuto.clinica.model;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class CitaRs {

        private Integer pacienteId;
        private Integer medicoId;
        private String motivo;
        private String estado;
        private String fechaHora;
}
