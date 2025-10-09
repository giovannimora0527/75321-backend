package com.uniminuto.clinica.service.impl;

import com.uniminuto.clinica.entity.Cita;
import com.uniminuto.clinica.entity.Medico;
import com.uniminuto.clinica.entity.Paciente;
import com.uniminuto.clinica.model.CitaRq;
import com.uniminuto.clinica.model.RespuestaRs;
import com.uniminuto.clinica.repository.CitaRepository;
import com.uniminuto.clinica.repository.MedicoRepository;
import com.uniminuto.clinica.repository.PacienteRepository;
import com.uniminuto.clinica.service.CitaService;
import org.apache.coyote.BadRequestException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

@Service
public class CitaServiceImpl implements CitaService {

    @Autowired
    private CitaRepository citaRepository;

    @Autowired
    private PacienteRepository pacienteRepository;

    @Autowired
    private MedicoRepository medicoRepository;

    @Override
    public List<Cita> listarCitasOrdenadas() {
        return this.citaRepository.findAllByOrderByFechaHoraDesc();
    }

    @Override
    public RespuestaRs guardarCita(CitaRq citaRq) throws BadRequestException {
        Optional<Paciente> optPaciente = this.pacienteRepository.findById(citaRq.getPacienteId());
        if (optPaciente.isEmpty()) {
            throw new BadRequestException("El paciente no existe");
        }

        Optional<Medico> optMedico = this.medicoRepository.findById(citaRq.getMedicoId());
        if (optMedico.isEmpty()) {
            throw new BadRequestException("El medico no existe");
        }

        LocalDateTime fechaInicioCita = LocalDateTime.parse(citaRq.getFechaHora());
        LocalDateTime fechaFinCita = fechaInicioCita.plusMinutes(15);

        List<Cita> citasExistentesMedico = this.citaRepository.findByMedicoAndFechaHoraBetween(
                optMedico.get(), fechaInicioCita, fechaFinCita);

        if (!citasExistentesMedico.isEmpty()) {
            throw new BadRequestException("El medico ya tiene una cita en ese horario");
        }

        List<Cita> citasExistentesPaciente = this.citaRepository.findByPacienteAndFechaHoraBetween(
                optPaciente.get(), fechaInicioCita, fechaFinCita);
        if (!citasExistentesPaciente.isEmpty()) {
            throw new BadRequestException("El paciente ya tiene una cita en ese horario");
        }

        Cita citaGuardar = this.converterCitaRqToCita(citaRq, optPaciente.get(), optMedico.get());
        this.citaRepository.save(citaGuardar);
        RespuestaRs rta = new RespuestaRs();
        rta.setStatus(200);
        rta.setMensaje("Cita creada exitosamente");
        return rta;
    }

    private Cita converterCitaRqToCita(CitaRq citaRq, Paciente paciente, Medico medico) {
        Cita cita = new Cita();
        cita.setPaciente(paciente);
        cita.setMedico(medico);
        cita.setFechaHora(LocalDateTime.parse(citaRq.getFechaHora()));
        cita.setMotivo(citaRq.getMotivo());
        cita.setEstado("PROGRAMADA");
        return cita;
    }



}
