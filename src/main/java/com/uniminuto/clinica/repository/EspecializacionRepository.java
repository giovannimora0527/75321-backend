package com.uniminuto.clinica.repository;

import com.uniminuto.clinica.entity.Especializacion;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface EspecializacionRepository
        extends JpaRepository<Especializacion, Long> {

    Optional<Especializacion> findByCodigoEspecializacion(String codigoEspecializacion);

    // útiles también:
    boolean existsByCodigoEspecializacion(String codigo);
    Optional<Especializacion> findByNombre(String nombre);

    //Validar el nombre de la especilizacion
    boolean existsByNombreIgnoreCase(String nombre);

    //Buscar por nombre exacto
    Optional<Especializacion> findByNombreIgnoreCase(String nombre);
}