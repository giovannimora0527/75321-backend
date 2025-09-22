package com.uniminuto.clinica.repository;

import com.uniminuto.clinica.entity.Paciente;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface PacienteRepository extends JpaRepository<Paciente, Integer> {

    // Buscar paciente por número de documento
    Paciente findByNumeroDocumento(String numeroDocumento);

    // Listar pacientes ordenados por fecha de nacimiento (para calcular edad)
    List<Paciente> findAllByOrderByFechaNacimientoDesc();
}
