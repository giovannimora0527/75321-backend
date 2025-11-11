package com.uniminuto.clinica.repository;

import com.uniminuto.clinica.entity.Especializacion;
import com.uniminuto.clinica.entity.Medico;
import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

/**
 *
 * @author lmora
 */
@Repository
public interface MedicoRepository extends JpaRepository<Medico, Integer> {
    List<Medico> findByEspecializacion(Especializacion e);

    Optional<Medico> findByDocumento(String numeroDocumento);

    Optional<Medico> findByRegistroProfesional(String registroProfesional);
}
