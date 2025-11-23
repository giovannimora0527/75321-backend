package com.uniminuto.clinica.repository;

import com.uniminuto.clinica.entity.Cita;
import com.uniminuto.clinica.entity.Medico;
import com.uniminuto.clinica.entity.Paciente;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.time.LocalDateTime;
import java.util.List;

/**
 * Interfaz que define las operaciones CRUD para la entidad Cita.
 */
@Repository
public interface CitaRepository extends JpaRepository<Cita, Integer> {

    /**
     * Query que obtiene las citas organizadas por fecha y hora.
     *
     * @return lista de citas del sistema.
     */
    List<Cita> findAllByOrderByFechaHoraDesc();

    /**
     * Consulta que obtiene las citas de un médico en un rango de fechas.
     * @param medicoId Id del medico.
     * @param fechaIni fecha inicio de consulta.
     * @param fechaFin posible fecha fin de consulta.
     * @return lista de citas asignadas al medico en las fechas.
     */
    List<Cita> findByMedicoAndFechaHoraBetween(
            Medico medicoId, LocalDateTime fechaIni, LocalDateTime fechaFin);

    /**
     * Consulta que obtiene las citas de un paciente en un rango de fechas.
     * @param pacienteId id del paciente.
     * @param fechaIni fecha inicio de consulta.
     * @param fechaFin fecha final de la consulta.
     * @return lista de citas.
     */
    List<Cita> findByPacienteAndFechaHoraBetween(
            Paciente pacienteId, LocalDateTime fechaIni, LocalDateTime fechaFin);


    /**
     * Consulta que obtiene las citas de un paciente.
     * @param paciente paciente.
     * @return lista de citas.
     */
    List<Cita> findByPacienteOrderByFechaHoraDesc(Paciente paciente);
}
