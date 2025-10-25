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
        Optional<Medicamento> optMedicamento = this
                .medicamentoRepository
                .findById(recetaRq.getMedicamentoId());
        if (optMedicamento.isEmpty()) {
            throw new BadRequestException("El medicamento no existe");
        }

        Optional<Cita> optCita = this.citaRepository.findById(recetaRq.getCitaId());
        if (optCita.isEmpty()) {
            throw new BadRequestException("La cita no existe");
        }

        //findByCitaAndMedicamento
        List<Receta> recetasExistentes = this
                .recetaRepository
                .findByCitaAndMedicamento(optCita.get(), optMedicamento.get());
        if (!recetasExistentes.isEmpty()) {
            throw new BadRequestException("La receta ya existe para la cita y el medicamento");
        }

        Receta recetaGuardar = this.converterCitaRqToCita(recetaRq,
                optCita.get(), optMedicamento.get());
        this.recetaRepository.save(recetaGuardar);
        RespuestaRs rta = new RespuestaRs();
        rta.setMensaje("Receta guardada exitosamente");
        rta.setStatus(200);
        return rta;
    }

    private Receta converterCitaRqToCita(RecetaRq recetaRq, Cita cita, Medicamento medicamento) {
        Receta receta = new Receta();
        receta.setIndicaciones(recetaRq.getIndicaciones());
        receta.setFechaCreacionRegistro(java.time.LocalDateTime.now());
        receta.setCita(cita);
        receta.setMedicamento(medicamento);
        receta.setFechaCreacionRegistro(LocalDateTime.now());
        return receta;
    }
}
