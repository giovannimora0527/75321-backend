package com.uniminuto.clinica.repository;

import com.uniminuto.clinica.entity.Cita;
import com.uniminuto.clinica.entity.Medico;
import com.uniminuto.clinica.entity.Paciente;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.time.LocalDateTime;
import java.util.List;

@Repository
public interface CitaRepository extends JpaRepository<Cita, Long> {
    List<Cita> findByPaciente(Paciente paciente);
    List<Cita> findByMedico(Medico medico);
    List<Cita> findByFechaHoraBetween(LocalDateTime inicio, LocalDateTime fin);
    List<Cita> findByMedicoAndFechaHoraBetween(Medico medico, LocalDateTime inicio, LocalDateTime fin);
    List<Cita> findByEstado(Cita.EstadoCita estado);
      List<Cita> findAllByOrderByFechaHoraDesc();
}