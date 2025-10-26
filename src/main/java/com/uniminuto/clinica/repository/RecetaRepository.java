package com.uniminuto.clinica.repository;


import com.uniminuto.clinica.entity.Receta;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

/**
 *
 * @author AORUS
 */
public interface RecetaRepository extends JpaRepository<Receta, Integer>{
    List<Receta> findByCitaId(Integer citaId);
    List<Receta> findByMedicamentoId(Integer medicamentoId);

    Optional<Receta> findById(Integer id);
    boolean existsById(Integer id);
    List<Receta> findAllByOrderByFechaCreacionRegistroDesc();
}
