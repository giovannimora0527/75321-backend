package com.uniminuto.clinica.repository;

import com.uniminuto.clinica.entity.Especializacion;
import com.uniminuto.clinica.entity.Medico;
import java.util.List;
import java.util.Optional;
import com.uniminuto.clinica.entity.Receta;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

/**
 * Repositorio para operaciones de base de datos de la entidad Receta.
 *
 * @author lmora
 */
@Repository
public interface RecetaRepository extends JpaRepository<Receta, Long> {

    /**
     * Busca recetas por ID de cita médica.
     *
     * @param citaId ID de la cita médica
     * @return lista de recetas de la cita
     */
    List<Receta> findByCitaId(Long citaId);

    /**
     * Busca todas las recetas ordenadas por fecha de creación descendente.
     *
     * @return lista de recetas ordenadas
     */
    List<Receta> findAllByOrderByFechaCreacionRegistroDesc();
}