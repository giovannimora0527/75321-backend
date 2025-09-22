package com.uniminuto.clinica.repository;


import com.uniminuto.clinica.entity.Receta;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

/**
 *
 * @author AORUS
 */
public interface RecetaRepository extends JpaRepository<Receta, Integer>{
    List<Receta> findByCitaId(Integer citaId);
    List<Receta> findByMedicamentoId(Integer medicamentoId);
}
