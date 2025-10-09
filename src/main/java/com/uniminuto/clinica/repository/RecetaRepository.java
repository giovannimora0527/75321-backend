package com.uniminuto.clinica.repository;

import com.uniminuto.clinica.entity.Cita;
import com.uniminuto.clinica.entity.Medicamento;
import com.uniminuto.clinica.entity.Receta;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface RecetaRepository extends JpaRepository<Receta, Long> {
    List<Receta> findAllByOrderByFechaCreacionRegistroDesc();

    List<Receta> findByCitaAndMedicamento(Cita cita, Medicamento medicamento);
}
