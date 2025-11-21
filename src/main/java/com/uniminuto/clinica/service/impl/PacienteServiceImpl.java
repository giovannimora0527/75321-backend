package com.uniminuto.clinica.service.impl;

import com.uniminuto.clinica.entity.Paciente;
import com.uniminuto.clinica.model.RespuestaRs;
import com.uniminuto.clinica.model.PacienteRq;
import com.uniminuto.clinica.repository.PacienteRepository;
import com.uniminuto.clinica.service.PacienteService;
import java.util.List;
import java.util.Optional;
import org.apache.coyote.BadRequestException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * @author AORUS
 */

@Service
public class PacienteServiceImpl implements PacienteService {
    
    @Autowired
    private PacienteRepository pacienteRepository;
    
    @Override
    public List<Paciente> listarTodo() {
        return this.pacienteRepository.findAll();
    }
    
    @Override
    public Paciente buscarPacientePorDocumento(String numeroDocumento)
            throws BadRequestException {
        Optional<Paciente> optPac = this.pacienteRepository
                .findByNumeroDocumento(numeroDocumento);
        if (!optPac.isPresent()) {
            throw new BadRequestException("No existe paciente");
        }
        return optPac.get();
    }
    
    @Override
    public List<Paciente> listarPacientesOrdenadosPorFechaNacimiento() {
        return this.pacienteRepository.findAllByOrderByFechaNacimientoDesc();
    }
    
    @Override
    public RespuestaRs guardarPaciente(PacienteRq pacienteNuevo)
            throws BadRequestException {
        // Paso 1. Validar campos obligatorios
        this.validarCamposObligatorios(pacienteNuevo);
        
        // Paso 2. Validar si el paciente existe por número de documento
        if (this.pacienteRepository
                .existsByNumeroDocumento(pacienteNuevo.getNumeroDocumento())) {
            throw new BadRequestException(
                "El paciente con ese número de documento ya existe. Intente de nuevo.");
        }
        
        // Paso 3. Crear nueva instancia y guardar
        Paciente nuevo = new Paciente();
        nuevo.setUsuarioId(pacienteNuevo.getUsuarioId());
        nuevo.setTipoDocumento(pacienteNuevo.getTipoDocumento());
        nuevo.setNumeroDocumento(pacienteNuevo.getNumeroDocumento());
        nuevo.setNombres(pacienteNuevo.getNombres());
        nuevo.setApellidos(pacienteNuevo.getApellidos());
        nuevo.setFechaNacimiento(pacienteNuevo.getFechaNacimiento());
        nuevo.setGenero(pacienteNuevo.getGenero());
        nuevo.setTelefono(pacienteNuevo.getTelefono());
        nuevo.setDireccion(pacienteNuevo.getDireccion());
        
        this.pacienteRepository.save(nuevo);
        
        // Paso 4. Devolver respuesta
        RespuestaRs respuesta = new RespuestaRs();
        respuesta.setStatus(200);
        respuesta.setMensaje("El paciente se ha guardado con éxito.");
        return respuesta;
    }
    
    @Override
    public RespuestaRs actualizarPaciente(PacienteRq pacienteActualizar)
            throws BadRequestException {
        // Paso 1. Validar campos obligatorios
        this.validarCamposObligatorios(pacienteActualizar);
        
        // Paso 2. Buscar paciente por ID
        Optional<Paciente> optPaciente = this.pacienteRepository
                .findById(pacienteActualizar.getId());
        
        if (!optPaciente.isPresent()) {
            throw new BadRequestException("El paciente no existe.");
        }
        
        // Paso 3. Actualizar campos
        Paciente pacienteExistente = optPaciente.get();
        
        // Validar si cambia el número de documento y si ya existe
        if (!pacienteExistente.getNumeroDocumento()
                .equals(pacienteActualizar.getNumeroDocumento())) {
            if (this.pacienteRepository
                    .existsByNumeroDocumento(pacienteActualizar.getNumeroDocumento())) {
                throw new BadRequestException(
                    "Ya existe otro paciente con ese número de documento.");
            }
        }
        
        pacienteExistente.setUsuarioId(pacienteActualizar.getUsuarioId());
        pacienteExistente.setTipoDocumento(pacienteActualizar.getTipoDocumento());
        pacienteExistente.setNumeroDocumento(pacienteActualizar.getNumeroDocumento());
        pacienteExistente.setNombres(pacienteActualizar.getNombres());
        pacienteExistente.setApellidos(pacienteActualizar.getApellidos());
        pacienteExistente.setFechaNacimiento(pacienteActualizar.getFechaNacimiento());
        pacienteExistente.setGenero(pacienteActualizar.getGenero());
        pacienteExistente.setTelefono(pacienteActualizar.getTelefono());
        pacienteExistente.setDireccion(pacienteActualizar.getDireccion());
        
        this.pacienteRepository.save(pacienteExistente);
        
        // Paso 4. Devolver respuesta
        RespuestaRs respuesta = new RespuestaRs();
        respuesta.setStatus(200);
        respuesta.setMensaje("El paciente se ha actualizado con éxito.");
        return respuesta;
    }
    
    private void validarCamposObligatorios(PacienteRq paciente)
            throws BadRequestException {
        if (paciente.getTipoDocumento() == null 
                || paciente.getTipoDocumento().isBlank() 
                || paciente.getTipoDocumento().isEmpty()) {
            throw new BadRequestException("El campo tipo de documento es obligatorio");
        }
        
        if (paciente.getNumeroDocumento() == null 
                || paciente.getNumeroDocumento().isBlank() 
                || paciente.getNumeroDocumento().isEmpty()) {
            throw new BadRequestException("El campo número de documento es obligatorio");
        }
        
        if (paciente.getNombres() == null 
                || paciente.getNombres().isBlank() 
                || paciente.getNombres().isEmpty()) {
            throw new BadRequestException("El campo nombres es obligatorio");
        }
        
        if (paciente.getApellidos() == null 
                || paciente.getApellidos().isBlank() 
                || paciente.getApellidos().isEmpty()) {
            throw new BadRequestException("El campo apellidos es obligatorio");
        }
        
        if (paciente.getFechaNacimiento() == null) {
            throw new BadRequestException("El campo fecha de nacimiento es obligatorio");
        }
        
        if (paciente.getGenero() == null 
                || paciente.getGenero().isBlank() 
                || paciente.getGenero().isEmpty()) {
            throw new BadRequestException("El campo género es obligatorio");
        }
    }
}
