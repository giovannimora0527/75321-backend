package com.uniminuto.clinica.service.impl;

import com.uniminuto.clinica.entity.Cita;
import com.uniminuto.clinica.entity.Medico;
import com.uniminuto.clinica.entity.Usuario;

import com.uniminuto.clinica.repository.CitaRepository;
import com.uniminuto.clinica.repository.MedicoRepository;
import com.uniminuto.clinica.repository.UsuarioRepository;
import com.uniminuto.clinica.service.CitaService;
import org.springframework.stereotype.Service;
import java.util.List;

@Service
public class CitaServiceImpl implements CitaService {

    private final CitaRepository citaRepository;
    private final UsuarioRepository usuarioRepository;
    private final MedicoRepository medicoRepository;

    public CitaServiceImpl(CitaRepository citaRepository, UsuarioRepository usuarioRepository, MedicoRepository medicoRepository) {
        this.citaRepository = citaRepository;
        this.usuarioRepository = usuarioRepository;
        this.medicoRepository = medicoRepository;
    }

    /**Guardar cita validando que el paciente y el médico existan.*/
    @Override



    public Cita guardarCita(Cita cita) {

        Usuario paciente = usuarioRepository.findById(cita.getPacienteId())
                .orElseThrow(() -> new RuntimeException("Paciente no encontrado con ID " + cita.getPacienteId()));

        Medico medico = medicoRepository.findById(Long.valueOf(cita.getMedicoId()))
                .orElseThrow(() -> new RuntimeException("Médico no encontrado con ID " + cita.getMedicoId()));

        return citaRepository.save(cita);
    }

    @Override
    public List<Cita> listarCitas() {
        return citaRepository.findAll();
    }

}
