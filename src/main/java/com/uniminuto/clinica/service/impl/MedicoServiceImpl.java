package com.uniminuto.clinica.service.impl;

import com.uniminuto.clinica.entity.Especializacion;
import com.uniminuto.clinica.entity.Medico;
import com.uniminuto.clinica.exception.EspecializacionNotFoundException;
import com.uniminuto.clinica.repository.EspecializacionRepository;
import com.uniminuto.clinica.repository.MedicoRepository;
import com.uniminuto.clinica.service.MedicoService;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
public class MedicoServiceImpl implements MedicoService {

    private final MedicoRepository medicoRepository;
    private final EspecializacionRepository especializacionRepository;

    // Inyección por constructor
    public MedicoServiceImpl(MedicoRepository medicoRepository,
                             EspecializacionRepository especializacionRepository) {
        this.medicoRepository = medicoRepository;
        this.especializacionRepository = especializacionRepository;
    }

    @Override
    @Transactional(readOnly = true)
    public List<Medico> listarTodo() {
        return medicoRepository.findAll();
    }

    @Override
    @Transactional(readOnly = true)
    public List<Medico> obtenerMedicosPorEspecializacion(String codigoEspecializacion) {
        Especializacion especializacion = especializacionRepository
                .findByCodigoEspecializacion(codigoEspecializacion)
                .orElseThrow(() -> new EspecializacionNotFoundException(
                        "No existe especialización con código: " + codigoEspecializacion));

        return medicoRepository.findByEspecializacion(especializacion);
    }
}

