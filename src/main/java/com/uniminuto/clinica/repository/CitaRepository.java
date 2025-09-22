package com.uniminuto.clinica.repository;

import com.uniminuto.clinica.entity.Cita;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.List;

/**
 * Repositorio para la entidad Cita.
 */
public interface CitaRepository extends JpaRepository<Cita, Long> {

    /**
     * Lista todas las citas ordenadas por fecha y hora descendente
     * (más recientes primero).
     */
    List<Cita> findAllByOrderByFechaHoraDesc();
}
