package com.uniminuto.clinica.service.impl;

import com.uniminuto.clinica.service.CitaService;
import com.uniminuto.clinica.model.CitaRs;
import com.uniminuto.clinica.entity.Cita;
import com.uniminuto.clinica.entity.Medico;
import com.uniminuto.clinica.entity.Paciente;
import com.uniminuto.clinica.repository.CitaRepository;
import com.uniminuto.clinica.repository.MedicoRepository;
import com.uniminuto.clinica.repository.PacienteRepository;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;

@Service
public class CitaServiceImpl implements CitaService{

    private final CitaRepository citaRepository;
    private final PacienteRepository pacienteRepository;
    private final MedicoRepository medicoRepository;

    public CitaServiceImpl(CitaRepository citaRepository,
                           PacienteRepository pacienteRepository,
                           MedicoRepository medicoRepository) {
        this.citaRepository = citaRepository;
        this.pacienteRepository = pacienteRepository;
        this.medicoRepository = medicoRepository;
    }

    @Override
    public Cita guardarCita(CitaRs citaRs) {

        Paciente paciente = pacienteRepository.findById(citaRs.getPacienteId())
                .orElseThrow(() -> new RuntimeException(""));
        Medico medico = medicoRepository.findById(citaRs.getMedicoId())
                .orElseThrow(() -> new RuntimeException(""));

        Cita cita = new Cita();
        cita.setMedico(medico);
        cita.setPaciente(paciente);
        cita.setFechaHora(String.valueOf(LocalDateTime.now()));
        cita.setEstado(citaRs.getEstado());
        cita.setMotivo(citaRs.getMotivo());

        return citaRepository.save(cita);
    }

    @Override
    public List<Cita> listarCitas() {
        return citaRepository.findAllByOrderByFechaHoraDesc();
    }

}
