package com.uniminuto.clinica.model;

import lombok.Data;
import java.time.LocalDateTime;

/**
 * @author AORUS
 */
@Data
public class CitaRq {
    private Integer pacienteId;
    private Integer medicoId;
    private LocalDateTime fechaHora;
    private String estado;
    private String motivo;
}
