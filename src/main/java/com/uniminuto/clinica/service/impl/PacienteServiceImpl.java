package com.uniminuto.clinica.service.impl;

import com.uniminuto.clinica.entity.Paciente;
import com.uniminuto.clinica.model.RespuestaRs;
import com.uniminuto.clinica.repository.PacienteRepository;
import com.uniminuto.clinica.service.PacienteService;
import com.uniminuto.clinica.model.PacienteRq;

import java.time.LocalDate;
import java.util.List;

import org.apache.coyote.BadRequestException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Optional;

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
    public RespuestaRs guardarPaciente(PacienteRq pacienteRq) throws BadRequestException {
        // Paso 1. Validar que los campos de entrada lleguen
        this.validarCampos(pacienteRq);

        if (pacienteRq.getTipo_documento() == null || pacienteRq.getTipo_documento().isEmpty()) {
            throw new BadRequestException("El campo tipoDocumento es obligatorio");
        }

        // Paso 2. Validar que no exista un paciente con el mismo número de documento
        Optional<Paciente> optPaciente = this.pacienteRepository.findByNumeroDocumento(pacienteRq.getNumero_documento());
        if (optPaciente.isPresent()) {
            throw new BadRequestException("El número de documento ya se encuentra registrado.");
        }

        // Paso 3. Crear el objeto paciente a guardar
        Paciente pacienteAGuardar = this.convertirAPaciente(pacienteRq);

        // Paso 4. Guardar el paciente
        this.pacienteRepository.save(pacienteAGuardar);

        // Paso 5. Crear la respuesta y retornar la respuesta
        RespuestaRs respuestaRs = new RespuestaRs();
        respuestaRs.setStatus(200);
        respuestaRs.setMensaje("Paciente guardado correctamente");
        return respuestaRs;
    }

    private void validarCampos(PacienteRq pacienteRq) throws BadRequestException {

        if (pacienteRq.getTipo_documento() == null || pacienteRq.getTipo_documento().isEmpty()) {
            throw new BadRequestException("El campo tipoDocumento es obligatorio");
        }
        if (pacienteRq.getNumero_documento() == null || pacienteRq.getNumero_documento().isEmpty()) {
            throw new BadRequestException("El campo numeroDocumento es obligatorio");
        }
        if (pacienteRq.getNombres() == null || pacienteRq.getNombres().isEmpty()) {
            throw new BadRequestException("El campo nombres es obligatorio");
        }
        if (pacienteRq.getApellidos() == null || pacienteRq.getApellidos().isEmpty()) {
            throw new BadRequestException("El campo apellidos es obligatorio");
        }
        if (pacienteRq.getFecha_nacimiento() == null || pacienteRq.getFecha_nacimiento().isEmpty()) {
            throw new BadRequestException("El campo fechaNacimiento es obligatorio");
        }
        if (pacienteRq.getGenero() == null || pacienteRq.getGenero().isEmpty()) {
            throw new BadRequestException("El campo genero es obligatorio");
        }
        if (pacienteRq.getTelefono() == null || pacienteRq.getTelefono().isEmpty()) {
            throw new BadRequestException("El campo telefono es obligatorio");
        }
        if (pacienteRq.getDireccion() == null || pacienteRq.getDireccion().isEmpty()) {
            throw new BadRequestException("El campo direccion es obligatorio");
        }
    }

    private Paciente convertirAPaciente(PacienteRq pacienteRq) {
        Paciente nuevo = new Paciente();
        nuevo.setTipoDocumento(pacienteRq.getTipo_documento());
        nuevo.setNumeroDocumento(pacienteRq.getNumero_documento());
        nuevo.setNombres(pacienteRq.getNombres());
        nuevo.setApellidos(pacienteRq.getApellidos());
        nuevo.setFechaNacimiento(LocalDate.parse(pacienteRq.getFecha_nacimiento()));
        nuevo.setGenero(pacienteRq.getGenero());
        nuevo.setTelefono(pacienteRq.getTelefono());
        nuevo.setDireccion(pacienteRq.getDireccion());
        return nuevo;
    }

    @Override
    public RespuestaRs actualizarPaciente(PacienteRq pacienteRq) throws BadRequestException {
        if (pacienteRq.getId() == null) {
            throw new BadRequestException("El id del paciente es obligatorio");
        }

        // Paso 1. Consultar si existe el registro a modificar
        Optional<Paciente> optPaciente = this.pacienteRepository
                .findById(Integer.valueOf(pacienteRq.getId()));
        if (!optPaciente.isPresent()) {
            throw new BadRequestException("No existe el registro a modificar.");
        }

        Paciente pacienteActualizar = optPaciente.get();

        // Paso 2. Verificar que no exista otro paciente con el mismo número de documento si este cambia
        if (!pacienteActualizar.getNumeroDocumento().equalsIgnoreCase(pacienteRq.getNumero_documento())) {
            Optional<Paciente> optPacientePorDocumento = this.pacienteRepository
                    .findByNumeroDocumento(pacienteRq.getNumero_documento());
            if (optPacientePorDocumento.isPresent()) {
                throw new BadRequestException("Ya existe un paciente con ese número de documento");
            }
        }

        // Paso 3. Actualizar los campos del paciente

        pacienteActualizar.setTipoDocumento(pacienteRq.getTipo_documento() == null ? pacienteActualizar.getTipoDocumento() : pacienteRq.getTipo_documento());
        pacienteActualizar.setNumeroDocumento(pacienteRq.getNumero_documento() == null ? pacienteActualizar.getNumeroDocumento() : pacienteRq.getNumero_documento());
        pacienteActualizar.setNombres(pacienteRq.getNombres() == null ? pacienteActualizar.getNombres() : pacienteRq.getNombres());
        pacienteActualizar.setApellidos(pacienteRq.getApellidos() == null ? pacienteActualizar.getApellidos() : pacienteRq.getApellidos());
        pacienteActualizar.setFechaNacimiento(pacienteRq.getFecha_nacimiento() == null ? pacienteActualizar.getFechaNacimiento() : LocalDate.parse(pacienteRq.getFecha_nacimiento()));
        pacienteActualizar.setGenero(pacienteRq.getGenero() == null ? pacienteActualizar.getGenero() : pacienteRq.getGenero());
        pacienteActualizar.setTelefono(pacienteRq.getTelefono() == null ? pacienteActualizar.getTelefono() : pacienteRq.getTelefono());
        pacienteActualizar.setDireccion(pacienteRq.getDireccion() == null ? pacienteActualizar.getDireccion() : pacienteRq.getDireccion());

        // Paso 4. Guardar el paciente actualizado
        this.pacienteRepository.save(pacienteActualizar);

        // Paso 5. Crear la respuesta y retornar la respuesta
        RespuestaRs rta = new RespuestaRs();
        rta.setMensaje("Se ha actualizado el paciente satisfactoriamente");
        rta.setStatus(200);
        return rta;
    }
    }