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

/**
 * Implementación del servicio para la lógica de negocio de citas médicas.
 * Proporciona funcionalidades para guardar y listar citas.
 *
 * @author lmora
 */
@Service
public class CitaServiceImpl implements CitaService {
    @Autowired
    private CitaRepository citaRepository;

    @Autowired
    private MedicoRepository medicoRepository;

    @Autowired
    private PacienteRepository pacienteRepository;

    /**
     * Guarda una nueva cita en el sistema validando la existencia del médico y paciente.
     * Establece las relaciones correspondientes antes de persistir la cita.
     *
     * @param cita datos de la cita a guardar, debe contener médico y paciente válidos
     * @return cita guardada con las relaciones establecidas
     * @throws RuntimeException si el médico o paciente no son encontrados
     */
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

    /**
     * Lista todas las citas ordenadas por fecha y hora de manera descendente.
     * Las citas más recientes aparecen primero en la lista.
     *
     * @return lista de citas ordenadas por fecha/hora descendente, vacía si no hay citas
     */
    @Override
    public List<Cita> listarCitasOrdenadas() {
        return citaRepository.findAllByOrderByFechaHoraDesc();
    }
}