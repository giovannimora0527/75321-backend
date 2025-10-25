package com.uniminuto.clinica.repository;

import com.uniminuto.clinica.entity.Cita;
import com.uniminuto.clinica.entity.Receta;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import java.util.List;

/**
 * Repositorio para operaciones de base de datos de la entidad Cita.
 *
 * @author lmora
 */
@Repository
public interface CitaRepository extends JpaRepository<Cita, Long> {

    /**
     * Busca todas las citas ordenadas por fecha y hora descendente.
     *
     * @return lista de citas ordenadas
     */
    List<Cita> findAllByOrderByFechaHoraDesc();

    /**
     * Busca todas las citas ordenadas por fecha de creación descendente.
     *
     * @return lista de citas ordenadas por creación
     */
    List<Cita> findAllByOrderByFechaCreacionRegistroDesc();
}