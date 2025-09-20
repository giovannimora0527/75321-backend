package com.uniminuto.clinica.service.impl;

import com.uniminuto.clinica.entity.Cita;
import com.uniminuto.clinica.entity.Medico;
import com.uniminuto.clinica.entity.Paciente;
import com.uniminuto.clinica.repository.CitaRepository;
import com.uniminuto.clinica.repository.MedicoRepository;
import com.uniminuto.clinica.repository.PacienteRepository;
import com.uniminuto.clinica.service.CitaService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.util.List;


@Service
public class CitaServiceImpl implements CitaService {
    @Autowired
    private CitaRepository citaRepository;

    @Autowired
    private MedicoRepository medicoRepository;

    @Autowired
    private PacienteRepository pacienteRepository;

    @Override
    public Cita guardarCita(Cita cita) {
        Medico medico = medicoRepository.findById(cita.getMedico().getId().intValue())
                .orElseThrow(() -> new RuntimeException("Médico no encontrado"));

        Paciente paciente = pacienteRepository.findById(cita.getPaciente().getId().intValue())
                .orElseThrow(() -> new RuntimeException("Paciente no encontrado"));

        cita.setMedico(medico);
        cita.setPaciente(paciente);

        return citaRepository.save(cita);
    }
    @Override
    public List<Cita> listarCitasOrdenadas() {
        return citaRepository.findAllByOrderByFechaHoraDesc();
    }
}