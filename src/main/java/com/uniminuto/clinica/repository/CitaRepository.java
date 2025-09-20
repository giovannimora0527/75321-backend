package com.uniminuto.clinica.repository;

import com.uniminuto.clinica.entity.Cita;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface CitaRepository extends JpaRepository<Cita, Integer> {
    List<Cita> findAllByOrderByFechaHoraDesc();
}
