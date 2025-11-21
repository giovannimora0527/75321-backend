package com.uniminuto.clinica.model;

import java.time.LocalDateTime;
import lombok.Data;

/**
 * @author AORUS
 */
@Data
public class RecetaRq {
    private Integer id;
    private Integer citaId;
    private Integer medicamentoId;
    private String dosis;
    private String indicaciones;
    private LocalDateTime fechaCreacionRegistro; 
}
