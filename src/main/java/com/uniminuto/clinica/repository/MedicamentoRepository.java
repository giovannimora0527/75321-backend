package com.uniminuto.clinica.repository;

import com.uniminuto.clinica.entity.Medicamento;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface MedicamentoRepository extends JpaRepository<Medicamento, Integer> {
    Optional<Medicamento> findByNombre(String nombre);
}
