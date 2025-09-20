package com.uniminuto.clinica.service.impl;

import com.uniminuto.clinica.entity.Cita;
import com.uniminuto.clinica.entity.Medico;
import com.uniminuto.clinica.entity.Paciente;
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
public Cita crearCita(Long pacienteId, Long medicoId, LocalDateTime fechaHora, String motivo) 
        throws BadRequestException {
    // Validar que el paciente existe
    Optional<Paciente> pacienteOpt = pacienteRepository.findById(pacienteId);
    if (!pacienteOpt.isPresent()) {
        throw new BadRequestException("Paciente no encontrado");
    }
    
    // Validar que el médico existe
    Optional<Medico> medicoOpt = medicoRepository.findById(medicoId);
    if (!medicoOpt.isPresent()) {
        throw new BadRequestException("Médico no encontrado");
    }
    
    // Crear la cita
    Cita nuevaCita = new Cita();
    nuevaCita.setPaciente(pacienteOpt.get());
    nuevaCita.setMedico(medicoOpt.get());
    nuevaCita.setFechaHora(fechaHora);
    nuevaCita.setMotivo(motivo);
    nuevaCita.setEstado(Cita.EstadoCita.programada);
    
    return citaRepository.save(nuevaCita);
}

    @Override
    public Cita actualizarCita(Long citaId, LocalDateTime nuevaFechaHora, String nuevoMotivo) 
            throws BadRequestException {
        Cita cita = buscarCitaPorId(citaId);
        
        if (nuevaFechaHora != null) {
            cita.setFechaHora(nuevaFechaHora);
        }
        
        if (nuevoMotivo != null) {
            cita.setMotivo(nuevoMotivo);
        }
        
        return citaRepository.save(cita);
    }

    @Override
    public Cita cambiarEstado(Long citaId, Cita.EstadoCita nuevoEstado) 
            throws BadRequestException {
        Cita cita = buscarCitaPorId(citaId);
        cita.setEstado(nuevoEstado);
        return citaRepository.save(cita);
    }

    @Override
    public List<Cita> listarCitas() {
        return citaRepository.findAll();
    }

    @Override
    public List<Cita> listarCitasPorPaciente(Long pacienteId) {
        Optional<Paciente> paciente = pacienteRepository.findById(pacienteId);
        if (paciente.isPresent()) {
            return citaRepository.findByPaciente(paciente.get());
        }
        return List.of();
    }

    @Override
    public List<Cita> listarCitasPorMedico(Long medicoId) {
        Optional<Medico> medico = medicoRepository.findById(medicoId);
        if (medico.isPresent()) {
            return citaRepository.findByMedico(medico.get());
        }
        return List.of();
    }

    @Override
    public Cita buscarCitaPorId(Long citaId) throws BadRequestException {
        Optional<Cita> cita = citaRepository.findById(citaId);
        if (!cita.isPresent()) {
            throw new BadRequestException("Cita no encontrada con ID: " + citaId);
        }
        return cita.get();
    }

    @Override
public List<Cita> listarCitasPorFechaReciente() {
    return citaRepository.findAllByOrderByFechaHoraDesc();
}
}