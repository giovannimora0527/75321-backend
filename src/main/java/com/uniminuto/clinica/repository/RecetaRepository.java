package com.uniminuto.clinica.repository;

import com.uniminuto.clinica.entity.Especializacion;
import com.uniminuto.clinica.entity.Medico;
import java.util.List;
import java.util.Optional;

import com.uniminuto.clinica.entity.Receta;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;


public interface RecetaRepository extends JpaRepository<Receta, Long> {
    List<Receta> findByCitaId(Long citaId);
    List<Receta> findAllByOrderByFechaCreacionRegistroDesc();

}