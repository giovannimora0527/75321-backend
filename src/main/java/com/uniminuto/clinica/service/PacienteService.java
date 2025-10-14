package com.uniminuto.clinica.service;

import com.uniminuto.clinica.entity.Paciente;
import com.uniminuto.clinica.model.PacienteRq;
import com.uniminuto.clinica.model.PacienteRs;
import org.apache.coyote.BadRequestException;

import java.util.List;//Importar List
import java.util.Map;

public interface PacienteService {
    //Firmamos el metodo

    //Hay que implentarlo
    List<Paciente> obtenerTodos(); //Definimos Obtener todos

    //Nuevo metodo organizar Fecha_Nacimiento
    List<Paciente>listarPorFechaNacimientoAsc();

    //metodo para guardar el Paciente
    PacienteRs guardarPaciente(PacienteRq pacienteNuevo)throws BadRequestException;

    //metodo para actualizar el Paciente
    PacienteRs actualizarPaciente(PacienteRq pacienteEditado)throws  BadRequestException;

    //patch actulizar ciertos campos
    PacienteRs actualizarParcial(Long id, Map<String, Object> camposActualizados) throws BadRequestException;

}
