package com.uniminuto.clinica.model;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Data;
import lombok.Getter;
import lombok.Setter;

import javax.validation.constraints.NotNull;
import java.time.LocalDateTime;

// El objeto que vamos enviar
//Traemos lo minimo
@Data
@Getter
@Setter
public class CrearCitaRq {
    @NotNull
    private Long pacienteId;
    @NotNull
    private Long medicoId;

    @NotNull
    @JsonFormat(pattern = "yyy-MM-dd'T'HH:mm:ss")
    private LocalDateTime fechaHora;

    private String motivo;
}
