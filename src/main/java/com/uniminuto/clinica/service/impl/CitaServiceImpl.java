package com.uniminuto.clinica.service.impl;

import com.uniminuto.clinica.entity.Cita;
import com.uniminuto.clinica.entity.Medico;
import com.uniminuto.clinica.entity.Paciente;
import com.uniminuto.clinica.model.CitaRs;
import com.uniminuto.clinica.model.CrearCitaRq;
import com.uniminuto.clinica.repository.CitaRepository;
import com.uniminuto.clinica.repository.MedicoRepository;
import com.uniminuto.clinica.repository.PacienteRepository;
import com.uniminuto.clinica.service.CitaService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import org.springframework.transaction.annotation.Transactional;


import java.util.List;
import java.util.stream.Collectors;

//Poner decoradores
@Service
@RequiredArgsConstructor
public class CitaServiceImpl implements CitaService {
    private final CitaRepository citaRepository;
    private final PacienteRepository pacienteRepository;
    private final MedicoRepository medicoRepository;

    @Override
    @Transactional
    public CitaRs crear(CrearCitaRq rq) {
        // Validar el paciente y el médico
        Paciente paciente = pacienteRepository.findById(rq.getPacienteId())
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Paciente no existe"));

        Medico medico = medicoRepository.findById(rq.getMedicoId())
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "El médico no existe"));

        // Validar conflicto de horario para el médico
        boolean existeConflicto = citaRepository.existsByMedico_IdAndFechaHora(rq.getMedicoId(), rq.getFechaHora());
        if (existeConflicto) {
            throw new ResponseStatusException(HttpStatus.CONFLICT,
                    "El médico ya tiene una cita programada en ese horario");
        }

        // Construir y guardar la cita
        Cita c = new Cita();
        c.setPaciente(paciente);
        c.setMedico(medico);
        c.setFechaHora(rq.getFechaHora());
        c.setEstado("PROGRAMADA");
        c.setMotivo(rq.getMotivo());

        c = citaRepository.save(c);

        // DTO de respuesta
        return new CitaRs(
                c.getId(),
                paciente.getId(),
                paciente.getNumeroDocumento(),
                paciente.getNombres(),
                medico.getId(),
                medico.getNombres(),
                c.getFechaHora(),
                c.getEstado(),
                c.getMotivo()
        );
    }

    @Override
    @Transactional(readOnly = true)
    public List<CitaRs> listaPorFechaReciente() {
        //Ir a la base de datos traer entidades cita ordenadas mas nuevas
        List<Cita> citas=citaRepository.findAllByOrderByFechaHoraDesc();


        //Transformar cada cita (entidad) a citaRS a la respuesta
        return citas.stream().map(this::toDto).collect(Collectors.toList());

    }
    //mapear flujo interno
    private CitaRs toDto(Cita c) {
        Paciente p=c.getPaciente();
        Medico m=c.getMedico();

        return new CitaRs(
                c.getId(),
                p.getId(),
                p.getNumeroDocumento(),
                p.getNombres(),
                m.getId(),
                m.getNombres(),
                c.getFechaHora(),
                c.getEstado(),
                c.getMotivo()
        );
    }


}
