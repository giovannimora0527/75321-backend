package com.uniminuto.clinica.repository;

import com.uniminuto.clinica.entity.Cita;
import org.springframework.data.jpa.repository.EntityGraph;
import org.springframework.data.jpa.repository.JpaRepository;

import javax.persistence.Entity;
import javax.validation.constraints.NotNull;
import java.time.LocalDateTime;
import java.util.List;

public interface CitaRepository extends JpaRepository<Cita,Long> {

    //Trae paciente y medico en la misma consulta
    @EntityGraph(attributePaths = {"paciente","medico"})
    List<Cita> findAllByOrderByFechaHoraDesc();//Definimos el CRUD

    //Validar de que un medico no tenga 2 citas al mismo tiempo
    boolean existsByMedicoAndFechaHora(Long medicoId, LocalDateTime fechaHora);

    boolean existsByMedico_IdAndFechaHora(@NotNull Long medicoId, @NotNull LocalDateTime fechaHora);

}
