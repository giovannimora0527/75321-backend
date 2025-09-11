package com.uniminuto.clinica.service.impl;

import com.uniminuto.clinica.entity.Especializacion;
import com.uniminuto.clinica.entity.Medico;
import com.uniminuto.clinica.repository.EspecializacionRepository;
import com.uniminuto.clinica.repository.MedicoRepository;
import com.uniminuto.clinica.service.MedicoService;
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
public class MedicoServiceImpl implements MedicoService {

    @Autowired
    private MedicoRepository medicoRepository;

    @Autowired
    private EspecializacionRepository especializacionRepository;

    @Override
    public List<Medico> listarTodo() {
        return this.medicoRepository.findAll();
    }

    @Override
    public List<Medico> obtenerMedicosPorEspecializacion(String codigoEspec)
            throws BadRequestException {       
        Optional<Especializacion> optEsp = this.especializacionRepository
                .findByCodigoEspecializacion(codigoEspec);
        if (!optEsp.isPresent()) {
       
            throw new BadRequestException("No existe la especializacion");
        }

        return this.medicoRepository.findByEspecializacion(optEsp.get());
    }

}
