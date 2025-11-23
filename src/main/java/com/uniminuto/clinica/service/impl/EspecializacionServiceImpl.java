package com.uniminuto.clinica.service.impl;

import com.uniminuto.clinica.entity.Especializacion;
import com.uniminuto.clinica.repository.EspecializacionRepository;
import com.uniminuto.clinica.service.EspecializacionService;

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
public class EspecializacionServiceImpl implements EspecializacionService {

    @Autowired
    private EspecializacionRepository repo;

    @Override
    public List<Especializacion> listarTodo() {
        return this.repo.findAll()
                .stream()
                .sorted((e1, e2) -> e1.getNombre().compareToIgnoreCase(e2.getNombre()))
                .toList();
    }

    @Override
    public Especializacion buscarEspecializacionPorCod(String codigo)
            throws BadRequestException {
        Optional<Especializacion> optEspc = this.repo
                .findByCodigoEspecializacion(codigo);
        if (!optEspc.isPresent()) {
            throw new BadRequestException("No se encuentra la especializacion");
        }

        return optEspc.get();
    }

}
