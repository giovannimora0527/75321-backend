package com.uniminuto.clinica.repository;

import com.uniminuto.clinica.entity.Auditoria;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.time.LocalDateTime;
import java.util.List;

@Repository
public interface AuditoriaRepository extends JpaRepository<Auditoria, Long> {
    
    List<Auditoria> findByNombreUsuarioOrderByFechaHoraDesc(String nombreUsuario);
    
    @Query("SELECT COUNT(a) FROM Auditoria a WHERE a.nombreUsuario = :nombreUsuario " +
           "AND a.fechaHora >= :fechaDesde")
    Long contarIntentosRecientes(@Param("nombreUsuario") String nombreUsuario, 
                                 @Param("fechaDesde") LocalDateTime fechaDesde);
    
    @Query("SELECT a FROM Auditoria a WHERE a.nombreUsuario = :nombreUsuario " +
           "AND a.fechaHora >= :fechaDesde ORDER BY a.fechaHora DESC")
    List<Auditoria> obtenerIntentosRecientes(@Param("nombreUsuario") String nombreUsuario,
                                            @Param("fechaDesde") LocalDateTime fechaDesde);
}
