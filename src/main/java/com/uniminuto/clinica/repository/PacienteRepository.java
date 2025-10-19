package com.uniminuto.clinica.repository;

import com.uniminuto.clinica.entity.Paciente;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

/**
 *
 * @author lmora
 */
public interface PacienteRepository extends JpaRepository<Paciente, Integer> {

    List<Paciente> findAllByOrderByFechaNacimientoAsc();

    Optional<Paciente> findByNumeroDocumento(String numeroDocumento);



}
