package com.uniminuto.clinica.repository;

import com.uniminuto.clinica.entity.Especializacion;
import com.uniminuto.clinica.entity.Medico;
import java.util.List;
import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

/**
 * Repositorio para operaciones de base de datos de la entidad Medico.
 *
 * @author lmora
 */
@Repository
public interface MedicoRepository extends JpaRepository<Medico, Integer> {

    /**
     * Busca médicos por especialización.
     *
     * @param e especialización médica
     * @return lista de médicos con la especialización
     */
    List<Medico> findByEspecializacion(Especializacion e);

    /**
     * Busca un médico por registro profesional.
     *
     * @param registro registro profesional del médico
     * @return Optional con el médico encontrado
     */
    Optional<Medico> findByRegistroProfesional(String registro);
}