package com.uniminuto.clinica.service.impl;

import com.uniminuto.clinica.entity.Paciente;
import com.uniminuto.clinica.entity.Usuario;
import com.uniminuto.clinica.model.PacienteRq;
import com.uniminuto.clinica.model.PacienteRs;
import com.uniminuto.clinica.repository.PacienteRepository;
import com.uniminuto.clinica.repository.UsuarioRepository;
import com.uniminuto.clinica.service.PacienteService;
import lombok.RequiredArgsConstructor;
import org.apache.coyote.BadRequestException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.util.List;
import java.util.Map;

@Service //  Para indetificar que es un servicio
@RequiredArgsConstructor// para no hacer otra vez el constructor
public class PacienteServiceImpl implements PacienteService {

    private final PacienteRepository pacienteRepository;
    private final UsuarioRepository usuarioRepository;

    @Override //Implementamos el metodo
    public List<Paciente> obtenerTodos() {

        return pacienteRepository.findAll();
    }
    //Implentamos la firma necesario un return llamando repositorio+firma de la interfaz
    @Override
    public List<Paciente> listarPorFechaNacimientoAsc() {
        return pacienteRepository.findAllByOrderByFechaNacimientoAsc();
    }

    @Override
    public PacienteRs guardarPaciente(PacienteRq pacienteNuevo) throws BadRequestException {
    //Validar Numero de documento
        if(pacienteNuevo.getNumeroDocumento()==null||pacienteNuevo.getNumeroDocumento().isBlank()){
            throw  new BadRequestException("El numero de documento es obligatorio");
        }
        //Validar que el nombre del paciente es obligatorio
        if(pacienteNuevo.getNombres()==null||pacienteNuevo.getNombres().isBlank()){
            throw  new BadRequestException("El nombre del paciente es obligatorio");
        }
        //Validar que el paciente debe estar asociado a un usuario
        if(pacienteNuevo.getUsuarioId()==null){
            throw  new BadRequestException("Debe asociar el paciente a un usuario");
        }
        //validacion de que ese paciente ya existe con el numero de documento
        if (pacienteRepository.existsByUsuarioId(pacienteNuevo.getUsuarioId())) {
            throw new BadRequestException("El usuario ya tiene un paciente asociado.");
        }
        //Cargar usuario
        Usuario usuario=usuarioRepository.findById(pacienteNuevo.getUsuarioId())
                .orElseThrow(()->new BadRequestException("el usuario asociado no existe"));

        //Devolver respuesta y guardar en la base de datos

        Paciente nuevo=new Paciente();
        nuevo.setUsuario(usuario);
        nuevo.setTipoDocumento(pacienteNuevo.getTipoDocumento());
        nuevo.setNumeroDocumento(pacienteNuevo.getNumeroDocumento());
        nuevo.setNombres(pacienteNuevo.getNombres());
        nuevo.setApellidos(pacienteNuevo.getApellidos());
        nuevo.setFechaNacimiento(pacienteNuevo.getFechaNacimiento());
        nuevo.setGenero(pacienteNuevo.getGenero());
        nuevo.setTelefono(pacienteNuevo.getTelefono());
        nuevo.setDireccion(pacienteNuevo.getDireccion());

        //Guardar
        pacienteRepository.save(nuevo);

        //Responder con el Dto paciente Rs
        return toRs(nuevo);

    }

    @Override
    @Transactional
    public PacienteRs actualizarPaciente(PacienteRq pacienteEditado) throws BadRequestException {
        // 0) Cargar el paciente existente
        Paciente existente = pacienteRepository.findById(pacienteEditado.getId())
                .orElseThrow(() -> new BadRequestException("No existe un paciente con id " + pacienteEditado.getId()));

        // 1) Validar obligatorios mínimos
        if (pacienteEditado.getNumeroDocumento() == null || pacienteEditado.getNumeroDocumento().isBlank()) {
            throw new BadRequestException("El número de documento es obligatorio.");
        }
        if (pacienteEditado.getNombres() == null || pacienteEditado.getNombres().isBlank()) {
            throw new BadRequestException("El nombre del paciente es obligatorio.");
        }

        // 2) Validar unicidad de documento (excluyendo el propio id)
        if (pacienteRepository.existsByNumeroDocumentoAndIdNot(pacienteEditado.getNumeroDocumento(), existente.getId())) {
            throw new BadRequestException("Ya existe un paciente con ese número de documento.");
        }

        // 3) Validar usuario si lo envían:
        if (pacienteEditado.getUsuarioId() != null) {
            // 3.1) verificar que exista el usuario
            Usuario usuario = usuarioRepository.findById(pacienteEditado.getUsuarioId())
                    .orElseThrow(() -> new BadRequestException("El usuario asociado no existe."));

            // 3.2) validar que ese usuario no esté asociado a otro paciente
            if (pacienteRepository.existsByUsuario_IdAndIdNot(pacienteEditado.getUsuarioId(), existente.getId())) {
                throw new BadRequestException("Ese usuario ya está asociado a otro paciente.");
            }
            // 3.3) asignar usuario (solo si cambió)
            existente.setUsuario(usuario);
        }
        //Actualizamos los valores
        existente.setTipoDocumento(pacienteEditado.getTipoDocumento());
        existente.setNumeroDocumento(pacienteEditado.getNumeroDocumento());
        existente.setNombres(pacienteEditado.getNombres());
        existente.setApellidos(pacienteEditado.getNombres());
        existente.setFechaNacimiento(pacienteEditado.getFechaNacimiento());
        existente.setGenero(pacienteEditado.getGenero());
        existente.setTelefono(pacienteEditado.getTelefono());
        existente.setDireccion(pacienteEditado.getDireccion());

        //Guardamos Cambios
        pacienteRepository.save(existente);

        //Devolvemos Respuesta
        return new PacienteRs(
                existente.getId(),
                existente.getTipoDocumento(),
                existente.getNumeroDocumento(),
                existente.getNombres(),
                existente.getApellidos(),
                existente.getFechaNacimiento(),
                existente.getGenero(),
                existente.getTelefono(),
                existente.getDireccion(),
                existente.getUsuario() != null ? existente.getUsuario().getId() : null,
                existente.getUsuario() != null ? existente.getUsuario().getUsername() : null
        );
    }

    @Override
    public PacienteRs actualizarParcial(Long id, Map<String, Object> camposActualizados) throws BadRequestException {
        return null;
        //Implementacion Futura

    }

    // --- Mapeo entidad -> DTO de respuesta
    private PacienteRs toRs(Paciente p) {
        return new PacienteRs(
                p.getId(),
                p.getTipoDocumento(),
                p.getNumeroDocumento(),
                p.getNombres(),
                p.getApellidos(),
                p.getFechaNacimiento(),
                p.getGenero(),
                p.getTelefono(),
                p.getDireccion(),
                p.getUsuario() != null ? p.getUsuario().getId() : null,
                p.getUsuario() != null ? p.getUsuario().getUsername() : null
        );
    }

}
