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

import java.util.List;

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
