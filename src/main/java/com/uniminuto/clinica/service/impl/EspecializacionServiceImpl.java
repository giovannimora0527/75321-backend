package com.uniminuto.clinica.service.impl;

import com.uniminuto.clinica.entity.Especializacion;
import com.uniminuto.clinica.repository.EspecializacionRepository;
import com.uniminuto.clinica.service.EspecializacionService;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class EspecializacionServiceImpl implements EspecializacionService {

    @Autowired
    private EspecializacionRepository repository;

    @Override
    public List<Especializacion> listarTodo() {
        return repository.findAll();
    }

    @Override
    public Especializacion buscarPorCodigo(String codigo) {
        return repository.findByCodigoEspecializacion(codigo)
                .orElse(null);
    }

    @Override
    public Especializacion crear(Especializacion especializacion) {
        return repository.save(especializacion);
    }

    @Override
    public Especializacion actualizar(Long id, Especializacion especializacion) {
        Especializacion entity = repository.findById(id).orElseThrow();
        entity.setNombre(especializacion.getNombre());
        entity.setDescripcion(especializacion.getDescripcion());
        entity.setCodigoEspecializacion(especializacion.getCodigoEspecializacion());
        return repository.save(entity);
    }
}
