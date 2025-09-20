package com.uniminuto.clinica.repository;

import com.uniminuto.clinica.entity.Paciente;
import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface PacienteRepository extends JpaRepository<Paciente, Long> {
    Optional<Paciente> findByNumeroDocumento(String numeroDocumento);
    List<Paciente> findAllByOrderByFechaNacimientoDesc();
}