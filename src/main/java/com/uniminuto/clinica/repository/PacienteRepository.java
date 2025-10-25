package com.uniminuto.clinica.repository;

import com.uniminuto.clinica.entity.Paciente;
import java.util.List;
import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

/**
 * Repositorio para operaciones de base de datos de la entidad Paciente.
 *
 * @author lmora
 */
@Repository
public interface PacienteRepository extends JpaRepository<Paciente, Integer> {

    /**
     * Busca un paciente por número de documento.
     *
     * @param numeroDocumento número de documento del paciente
     * @return Optional con el paciente encontrado
     */
    Optional<Paciente> findByNumeroDocumento(String numeroDocumento);

    /**
     * Busca todos los pacientes ordenados por fecha de nacimiento descendente.
     *
     * @return lista de pacientes ordenados
     */
    List<Paciente> findAllByOrderByFechaNacimientoDesc();
}