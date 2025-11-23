package com.uniminuto.clinica.service.impl;

import com.uniminuto.clinica.entity.Cita;
import com.uniminuto.clinica.entity.Medicamento;
import com.uniminuto.clinica.entity.Receta;
import com.uniminuto.clinica.model.RecetaRq;
import com.uniminuto.clinica.model.RespuestaRs;
import com.uniminuto.clinica.repository.CitaRepository;
import com.uniminuto.clinica.repository.MedicamentoRepository;
import com.uniminuto.clinica.repository.RecetaRepository;
import com.uniminuto.clinica.service.RecetaService;
import org.apache.coyote.BadRequestException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

@Service
public class RecetaServiceImpl implements RecetaService {

    /**
     * Repositorio de datos para recetas médicas.
     */
    @Autowired
    private RecetaRepository recetaRepository;

    /**
     * CitaRepository.
     */
    @Autowired
    private CitaRepository citaRepository;

    /**
     * MedicamentoRepository.
     */
    @Autowired
    private MedicamentoRepository medicamentoRepository;

    @Override
    public List<Receta> listarRecetas() {
        return this.recetaRepository.findAll();
    }

    @Override
    public RespuestaRs guardarReceta(RecetaRq recetaRq) throws BadRequestException {
        Optional<Medicamento> optMedicamento = this.medicamentoRepository.findById(recetaRq.getMedicamentoId());
        if (optMedicamento.isEmpty()) {
            throw new BadRequestException("El medicamento con ID " + recetaRq.getMedicamentoId() + " no existe.");
        }

        Optional<Cita> optCita = this.citaRepository.findById(recetaRq.getCitaId());
        if(optCita.isEmpty()) {
            throw new BadRequestException("La cita con ID " + recetaRq.getCitaId() + " no existe.");
        }

        Cita cita = optCita.get();
        Medicamento medicamento = optMedicamento.get();
        List<Receta> recetasExistentes = this.recetaRepository.findByCitaAndMedicamento(cita, medicamento);
        if (!recetasExistentes.isEmpty()) {
            throw new BadRequestException("Ya existe una receta para la cita con ID " + recetaRq.getCitaId() +
                    " y el medicamento con ID " + recetaRq.getMedicamentoId() + ".");
        }

        Receta receta = this.convertirRqAEntidad(recetaRq, optCita.get(), optMedicamento.get());
        this.recetaRepository.save(receta);
        RespuestaRs rta = new RespuestaRs();
        rta.setMensaje("Receta guardada exitosamente.");
        rta.setStatus(200);
        return rta;
    }

    @Override
    public RespuestaRs actualizarReceta(RecetaRq recetaRq) throws BadRequestException {
        Optional<Receta> optReceta = this.recetaRepository.findById(recetaRq.getId());
        if (optReceta.isEmpty()) {
            throw new BadRequestException("La receta con ID " + recetaRq.getId() + " no existe.");
        }

        Receta recetaActual = optReceta.get();
        if (!recetaActual.getMedicamento().getId().equals(recetaRq.getMedicamentoId())) {
            Optional<Medicamento> optMedicamento = this.medicamentoRepository.findById(recetaRq.getMedicamentoId());
            if (optMedicamento.isEmpty()) {
                throw new BadRequestException("El medicamento con ID " + recetaRq.getMedicamentoId() + " no existe.");
            }
            recetaActual.setMedicamento(optMedicamento.get());
        }

        recetaActual.setDosis(recetaRq.getDosis());
        recetaActual.setIndicaciones(recetaRq.getIndicaciones());
        recetaActual.setFechaActualizacionRegistro(LocalDateTime.now());
        this.recetaRepository.save(recetaActual);
        RespuestaRs rta = new RespuestaRs();
        rta.setMensaje("Receta actualizada exitosamente.");
        rta.setStatus(200);
        return rta;
    }

    /**
     * Convierte un objeto RecetaRq a una entidad Receta.
     * @param recetaRq receta de entrada.
     * @return entidad receta.
     */
    private Receta convertirRqAEntidad(RecetaRq recetaRq, Cita cita, Medicamento medicamento) {
        Receta receta = new Receta();
        receta.setCita(cita);
        receta.setMedicamento(medicamento);
        receta.setDosis(recetaRq.getDosis());
        receta.setIndicaciones(recetaRq.getIndicaciones());
        receta.setFechaCreacionRegistro(LocalDateTime.now());
        return receta;
    }
}
