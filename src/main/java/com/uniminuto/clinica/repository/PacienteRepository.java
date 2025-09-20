package com.uniminuto.clinica.repository;

import com.uniminuto.clinica.entity.Paciente;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;


@Repository
public interface PacienteRepository extends JpaRepository<Paciente, Long> {
    Optional<Paciente> findByNumDoc(String numDoc);

    List<Paciente> findAllByOrderByFecNacAsc();

    Optional<Paciente> findById(Integer pacienteId);
}