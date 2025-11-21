package com.uniminuto.clinica.repository;

import com.uniminuto.clinica.entity.Cita;
import com.uniminuto.clinica.entity.Medicamento;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

/**
 * @author AORUS
 */
@Repository
public interface CitaRepository extends JpaRepository<Cita, Integer> {
    
    // Verificar si existe una cita en la misma fecha y hora para un médico
    boolean existsByMedicoIdAndFechaHora(Integer medicoId, LocalDateTime fechaHora);
    
    // Verificar si existe una cita en la misma fecha y hora para un paciente
    boolean existsByPacienteIdAndFechaHora(Integer pacienteId, LocalDateTime fechaHora);
    
    // Listar citas por paciente
    List<Cita> findByPacienteId(Integer pacienteId);
    
    // Listar citas por médico
    List<Cita> findByMedicoId(Integer medicoId);
    
    // Listar citas por estado
    List<Cita> findByEstado(String estado);

    public List<Cita> findByFechaHoraLessThanEqualOrderByFechaHoraDesc(LocalDateTime now);

    Optional<Medicamento> findByid(Integer id);
}
