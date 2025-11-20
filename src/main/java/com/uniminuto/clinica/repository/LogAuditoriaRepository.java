package com.uniminuto.clinica.repository;

import com.uniminuto.clinica.entity.LogAuditoria;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.time.LocalDateTime;

@Repository
public interface LogAuditoriaRepository extends JpaRepository<LogAuditoria, Long> {

    // Consulta personalizada para filtrar dinámicamente
    @Query("SELECT l FROM LogAuditoria l WHERE " +
            "(:username IS NULL OR l.username LIKE %:username%) AND " +
            "(:tipo IS NULL OR l.tipoEvento = :tipo) AND " +
            "(:fechaInicio IS NULL OR l.fecha >= :fechaInicio) AND " +
            "(:fechaFin IS NULL OR l.fecha <= :fechaFin)")
    Page<LogAuditoria> buscarLogs(
            @Param("username") String username,
            @Param("tipo") String tipo,
            @Param("fechaInicio") LocalDateTime fechaInicio,
            @Param("fechaFin") LocalDateTime fechaFin,
            Pageable pageable
    );
}