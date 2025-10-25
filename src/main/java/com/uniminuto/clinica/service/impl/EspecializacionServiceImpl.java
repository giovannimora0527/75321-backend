package com.uniminuto.clinica.service.impl;

import com.uniminuto.clinica.entity.Especializacion;
import com.uniminuto.clinica.model.CrearEspecializacionRq;
import com.uniminuto.clinica.model.EspecializacionRs;
import com.uniminuto.clinica.repository.EspecializacionRepository;
import com.uniminuto.clinica.service.EspecializacionService;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;
import java.util.stream.Collectors;

@Service
@AllArgsConstructor
public class EspecializacionServiceImpl implements EspecializacionService {
    private final EspecializacionRepository especializacionRepository;

    @Override
    @Transactional
    public EspecializacionRs crear(CrearEspecializacionRq rq) {
        // Normalizar datos
        String nombre = rq.getNombre().trim();
        String descripcion = rq.getDescripcion().trim();
        String codigo = rq.getCodigoEspecializacion().trim().toUpperCase();

        //Validar extension de la especilizacion
        if (nombre.isEmpty() || nombre.length() < 3) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "El nombre de la especialización es demasiado corto");
        }
        // validar la especilizacion
        if (especializacionRepository.existsByNombreIgnoreCase(nombre)) {
            throw new ResponseStatusException(HttpStatus.CONFLICT, "Ya existe una especialización con ese nombre");
        }
        //validar codigo de la especilizacion
        if (especializacionRepository.existsByCodigoEspecializacion(codigo)) {
            throw new ResponseStatusException(HttpStatus.CONFLICT, "Ya existe una especialización con ese código");
        }
        //Guardamos en la base de datos la nueva especialidad
        Especializacion especializacion = new Especializacion();
        especializacion.setNombre(nombre);
        especializacion.setDescripcion(descripcion);
        especializacion.setCodigoEspecializacion(codigo);

        especializacionRepository.save(especializacion);

        return toDto(especializacion);
    }

    @Override
    @Transactional(readOnly = true)
    public List<EspecializacionRs> listar() {
        return especializacionRepository.findAll()
                .stream()
                .map(this::toDto)
                .collect(Collectors.toList());
    }

    @Override
    @Transactional
    public EspecializacionRs actualizar(Long id, CrearEspecializacionRq rq) {
        // 1) Buscar existente
        Especializacion existente = especializacionRepository.findById(id)
                .orElseThrow(() -> new ResponseStatusException(
                        HttpStatus.NOT_FOUND, "La especialización con id " + id + " no existe"
                ));
        // 2) Normalizar entrada
        String nombre  = rq.getNombre().trim();
        String desc    = rq.getDescripcion().trim();
        String codigo  = rq.getCodigoEspecializacion().trim().toUpperCase();

        // 3) Validar duplicado por nombre (ignorando el mismo id)
        especializacionRepository.findByNombre(nombre)
                .filter(e -> !e.getId().equals(id))
                .ifPresent(e -> { throw new ResponseStatusException(
                        HttpStatus.CONFLICT, "Ya existe una especialización con ese nombre"
                );});

        // 4) Validar duplicado por código (ignorando el mismo id)
        especializacionRepository.findByCodigoEspecializacion(codigo)
                .filter(e -> !e.getId().equals(id))
                .ifPresent(e -> { throw new ResponseStatusException(
                        HttpStatus.CONFLICT, "Ya existe una especialización con ese código"
                );});

        // 5) Actualizar campos
        existente.setNombre(nombre);
        existente.setDescripcion(desc);
        existente.setCodigoEspecializacion(codigo);

        // 6) Guardar y mapear a RS
        Especializacion guardada = especializacionRepository.save(existente);

        return EspecializacionRs.builder()
                .id(guardada.getId())
                .nombre(guardada.getNombre())
                .descripcion(guardada.getDescripcion())
                .codigoEspecializacion(guardada.getCodigoEspecializacion())
                .build();

    }

    //Metodo Privado Para Mapear
    private EspecializacionRs toDto(Especializacion e) {
        return EspecializacionRs.builder()
                .id(e.getId())
                .nombre(e.getNombre())
                .descripcion(e.getDescripcion())
                .codigoEspecializacion(e.getCodigoEspecializacion())
                .build();
    }
}
