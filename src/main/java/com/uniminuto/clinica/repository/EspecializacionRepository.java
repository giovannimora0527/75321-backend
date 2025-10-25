package com.uniminuto.clinica.repository;

import com.uniminuto.clinica.entity.Especializacion;
import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

/**
 * Repositorio para operaciones de base de datos de la entidad Especializacion.
 *
 * @author lmora
 */
@Repository
public interface EspecializacionRepository extends JpaRepository<Especializacion, Long>{

    /**
     * Busca una especialización por su código único.
     *
     * @param codigo código de la especialización
     * @return Optional con la especialización encontrada
     */
    Optional<Especializacion> findByCodigoEspecializacion(String codigo);
}