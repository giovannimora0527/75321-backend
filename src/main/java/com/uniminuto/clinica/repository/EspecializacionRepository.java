package com.uniminuto.clinica.repository;

import com.uniminuto.clinica.entity.Especializacion;
import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

/**
 *
 * @author lmora
 */
@Repository
public interface EspecializacionRepository 
        extends JpaRepository<Especializacion, Long> {
    
    Optional<Especializacion> findByCodigoEspecializacion(String codigo);
    
}
