package com.uniminuto.clinica.service.impl;

import com.uniminuto.clinica.entity.Paciente;
import com.uniminuto.clinica.model.RespuestaRs;
import com.uniminuto.clinica.model.PacienteRq;
import com.uniminuto.clinica.repository.PacienteRepository;
import com.uniminuto.clinica.service.PacienteService;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.time.LocalDate;
import java.util.Collections;
import java.util.List;
import java.util.Optional;
import org.apache.coyote.BadRequestException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;


/**
 *
 * @author lmora
 */
@Service
public class PacienteServiceImpl implements PacienteService {

    @Autowired
    private PacienteRepository pacienteRepository;

    @Override
    public List<Paciente> listarTodo() {
        return pacienteRepository.findAll();
    }

    @Override
    public List<Paciente> obtenerPacientePorDocumento(String numeroDocumento) throws BadRequestException {
        throw new UnsupportedOperationException("Not supported yet."); // Generated from nbfs://nbhost/SystemFileSystem/Templates/Classes/Code/GeneratedMethodBody
    }

    @Override
    public List<Paciente> listarPacientesMayorAMenor() {
        return this.pacienteRepository.findAllByOrderByFechaNacimientoAsc();
    }

    @Override
    public RespuestaRs guardarPaciente(PacienteRq pacienteNuevo)
    throws BadRequestException {

        this.isCamposObligatorios(pacienteNuevo);

        if (this.pacienteRepository
            .existsByNumeroDocumento(pacienteNuevo.getNumeroDocumento().toLowerCase())) {
            throw new BadRequestException("El paciente ya existe. Intente de nuevo.");
        }

        Paciente nuevoPaciente = new Paciente();
        nuevoPaciente.setTipoDocumento(pacienteNuevo.getTipoDocumento().toLowerCase());
        nuevoPaciente.setNumeroDocumento(pacienteNuevo.getNumeroDocumento().toLowerCase());
        nuevoPaciente.setNombres(pacienteNuevo.getNombres().toLowerCase());
        nuevoPaciente.setApellidos(pacienteNuevo.getApellidos().toLowerCase());
        nuevoPaciente.setFechaNacimiento(pacienteNuevo.getFechaNacimiento()); // ✅ corregido
        nuevoPaciente.setGenero(pacienteNuevo.getGenero().toLowerCase());
        nuevoPaciente.setDireccion(pacienteNuevo.getDireccion().toLowerCase());
        nuevoPaciente.setTelefono(pacienteNuevo.getTelefono().toLowerCase());

        this.pacienteRepository.save(nuevoPaciente);

        RespuestaRs respuesta = new RespuestaRs();
        respuesta.setStatus(200);
        respuesta.setMensaje("Paciente creado exitosamente");
        return respuesta;
    }

    @Override
    public RespuestaRs actualizarPaciente(PacienteRq paciente) throws BadRequestException {
        if (paciente.getId() == null) {
            throw new BadRequestException("El ID del paciente es obligatorio para actualizar");
        }

        Optional<Paciente> optPaciente = this.pacienteRepository.findById(paciente.getId());
        if (optPaciente.isEmpty()) {
            throw new BadRequestException("El paciente no existe. No se puede actualizar");
        }

        Paciente pacienteActual = optPaciente.get();

        if (!pacienteActual.getNumeroDocumento().equals(paciente.getNumeroDocumento())) {
            Optional<Paciente> optPacienteByDocumento = this.pacienteRepository.findByNumeroDocumento(paciente.getNumeroDocumento());
            if (optPacienteByDocumento.isPresent()) {
                throw new BadRequestException("El paciente ya existe. No se puede actualizar");
            }
            pacienteActual.setNumeroDocumento(paciente.getNumeroDocumento());
        }

        pacienteActual.setNombres(paciente.getNombres());
        pacienteActual.setApellidos(paciente.getApellidos());
        pacienteActual.setFechaNacimiento(paciente.getFechaNacimiento());
        pacienteActual.setGenero(paciente.getGenero());
        pacienteActual.setDireccion(paciente.getDireccion());
        pacienteActual.setTelefono(paciente.getTelefono());

        this.pacienteRepository.save(pacienteActual);

        RespuestaRs rta = new RespuestaRs();
        rta.setStatus(200);
        rta.setMensaje("Se ha actualizado el paciente con éxito.");
        return rta;
    }

    private void isCamposObligatorios(PacienteRq pacienteNuevo) throws BadRequestException {
        if (pacienteNuevo.getTipoDocumento() == null || pacienteNuevo.getTipoDocumento().isBlank()) {
            throw new BadRequestException("El tipo de documento es obligatorio.");
        }
        if (pacienteNuevo.getNumeroDocumento() == null || pacienteNuevo.getNumeroDocumento().isBlank()) {
            throw new BadRequestException("El número de documento es obligatorio.");
        }
        if (pacienteNuevo.getNombres() == null || pacienteNuevo.getNombres().isBlank()) {
            throw new BadRequestException("El nombre es obligatorio.");
        }
        if (pacienteNuevo.getApellidos() == null || pacienteNuevo.getApellidos().isBlank()) {
            throw new BadRequestException("El apellido es obligatorio.");
        }
        if (pacienteNuevo.getFechaNacimiento() == null) {
            throw new BadRequestException("La fecha de nacimiento es obligatoria.");
        }
        if (pacienteNuevo.getGenero() == null || pacienteNuevo.getGenero().isBlank()) {
            throw new BadRequestException("El género es obligatorio.");
        }
    }


}
