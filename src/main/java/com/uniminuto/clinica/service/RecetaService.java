package com.uniminuto.clinica.service;

import com.uniminuto.clinica.entity.Receta;
import com.uniminuto.clinica.model.RecetaRq;
import com.uniminuto.clinica.model.RespuestaRs;
import java.util.List;
import org.apache.coyote.BadRequestException;

/**
 * @author AORUS
 */

public interface RecetaService {

    List<Receta> listarTodasLasRecetas();

    List<Receta> listarRecetasPorCita(Integer citaId);

    List<Receta> listarRecetasPorMedicamento(Integer medicamentoId);

    RespuestaRs guardarReceta(RecetaRq recetaNueva)
            throws BadRequestException;
}