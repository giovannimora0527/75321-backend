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
import java.time.LocalDateTime;
import java.util.Collections;
import java.util.List;
import java.util.Optional;
import org.apache.coyote.BadRequestException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * @author AORUS
 */
@Service
public class CitaServiceImpl implements CitaService {

    @Autowired
    private CitaRepository citaRepository;

    @Autowired
    private PacienteRepository pacienteRepository;

    @Autowired
    private MedicoRepository medicoRepository;

    @Override
    public List<Cita> listarTodasLasCitas() {
        return this.citaRepository.findAll();
    }

    @Override
    public List<Cita> listarCitasPorPaciente(Integer pacienteId) {
        List<Cita> lista = this.citaRepository.findByPacienteId(pacienteId);
        return !lista.isEmpty() ? lista : Collections.EMPTY_LIST;
    }

    @Override
    public List<Cita> listarCitasPorMedico(Integer medicoId) {
        List<Cita> lista = this.citaRepository.findByMedicoId(medicoId);
        return !lista.isEmpty() ? lista : Collections.EMPTY_LIST;
    }

    @Override
    public List<Cita> listarCitasPorEstado(String estado) {
        List<Cita> lista = this.citaRepository.findByEstado(estado.toUpperCase());
        return !lista.isEmpty() ? lista : Collections.EMPTY_LIST;
    }

    //lista las citas por hora teniendo en cuenta la fecha y hora actual
    @Override
    public List<Cita> listarCitasPasadasOrdenadas() {
        return this.citaRepository
                .findByFechaHoraLessThanEqualOrderByFechaHoraDesc(LocalDateTime.now());
    }

    @Override
    public RespuestaRs guardarCita(CitaRq citaNueva) throws BadRequestException {
        validarCamposObligatorios(citaNueva);

        Optional<Paciente> pacOpt = this.pacienteRepository.findById(citaNueva.getPacienteId());
        if (!pacOpt.isPresent()) throw new BadRequestException("El paciente no existe");

        Optional<Medico> medOpt = this.medicoRepository.findById(citaNueva.getMedicoId());
        if (!medOpt.isPresent()) throw new BadRequestException("El médico no existe");

        if (this.citaRepository.existsByMedicoIdAndFechaHora(citaNueva.getMedicoId(), citaNueva.getFechaHora()))
            throw new BadRequestException("El médico ya tiene una cita en esa fecha/hora");

        if (this.citaRepository.existsByPacienteIdAndFechaHora(citaNueva.getPacienteId(), citaNueva.getFechaHora()))
            throw new BadRequestException("El paciente ya tiene una cita en esa fecha/hora");

        Cita nueva = new Cita();
        nueva.setPaciente(pacOpt.get());
        nueva.setMedico(medOpt.get());
        nueva.setFechaHora(citaNueva.getFechaHora());
        nueva.setEstado(citaNueva.getEstado().toUpperCase());
        nueva.setMotivo(citaNueva.getMotivo());

        this.citaRepository.save(nueva);

        RespuestaRs respuesta = new RespuestaRs();
        respuesta.setStatus(200);
        respuesta.setMensaje("La cita se ha guardado con éxito.");
        return respuesta;
    }

    private void validarCamposObligatorios(CitaRq c) throws BadRequestException {
        if (c.getPacienteId() == null) throw new BadRequestException("El campo pacienteId es obligatorio");
        if (c.getMedicoId() == null) throw new BadRequestException("El campo medicoId es obligatorio");
        if (c.getFechaHora() == null) throw new BadRequestException("El campo fechaHora es obligatorio");
        if (c.getEstado() == null || c.getEstado().isBlank()) throw new BadRequestException("El campo estado es obligatorio");
        if (c.getMotivo() == null || c.getMotivo().isBlank()) throw new BadRequestException("El campo motivo es obligatorio");
    }
}
