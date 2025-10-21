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

    @Autowired
    private RecetaRepository recetaRepository;

    @Autowired
    private MedicamentoRepository medicamentoRepository;

    @Autowired
    private CitaRepository citaRepository;

    @Override
    public List<Receta> listarRecetasOrdenadas() {
        return this.recetaRepository.findAllByOrderByFechaCreacionRegistroDesc();
    }

    @Override
    public RespuestaRs guardarReceta(RecetaRq recetaRq) throws BadRequestException {
        // Validar existencia de medicamento
        Optional<Medicamento> optMedicamento = this.medicamentoRepository.findById(recetaRq.getMedicamentoId());
        if (optMedicamento.isEmpty()) {
            throw new BadRequestException("El medicamento no existe");
        }

        // Validar existencia de cita
        Optional<Cita> optCita = this.citaRepository.findById(recetaRq.getCitaId());
        if (optCita.isEmpty()) {
            throw new BadRequestException("La cita no existe");
        }

        // Buscar receta existente por cita y medicamento
        Optional<Receta> recetaExistente = this.recetaRepository
                .findByCitaAndMedicamento(optCita.get(), optMedicamento.get())
                .stream()
                .findFirst();

        // Crear nueva receta si no existe
        Receta recetaGuardar = recetaExistente.orElse(new Receta());

        // Asignar campos
        recetaGuardar.setCita(optCita.get());
        recetaGuardar.setMedicamento(optMedicamento.get());
        recetaGuardar.setIndicaciones(recetaRq.getIndicaciones());
        recetaGuardar.setDosis(recetaRq.getDosis());

        // Solo establecer fecha de creación si es nueva receta
        if (recetaExistente.isEmpty()) {
            recetaGuardar.setFechaCreacionRegistro(LocalDateTime.now());
        }

        // Guardar receta
        this.recetaRepository.save(recetaGuardar);

        // Construir respuesta
        RespuestaRs rta = new RespuestaRs();
        rta.setStatus(200);
        rta.setMensaje(recetaExistente.isPresent() ?
                "Receta actualizada exitosamente" : "Receta creada exitosamente");

        return rta;
    }
}
