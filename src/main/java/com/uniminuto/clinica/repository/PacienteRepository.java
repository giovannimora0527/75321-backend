package com.uniminuto.clinica.repository;

import com.uniminuto.clinica.entity.Paciente;
import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

/**
 *
 * @author lmora
 */
public interface PacienteRepository extends JpaRepository<Paciente, Long> {

    Optional<Paciente> findByNumeroDocumento(String numeroDocumento);
    
    List<Paciente> findAllByOrderByFechaNacimientoAsc();
    
    boolean existsByNumeroDocumento(String numeroDocumento);
    
}
