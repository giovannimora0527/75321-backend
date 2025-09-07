package com.uniminuto.clinica.service.impl;

import com.uniminuto.clinica.entity.Especializacion;
import com.uniminuto.clinica.entity.Paciente;
import com.uniminuto.clinica.repository.EspecializacionRepository;
import com.uniminuto.clinica.repository.PacienteRepository;
import com.uniminuto.clinica.service.PacienteService;
import java.util.List;
import java.util.Optional;
import org.apache.coyote.BadRequestException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.uniminuto.clinica.model.RespuestaRs;
import com.uniminuto.clinica.model.PacienteRq;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.time.LocalDateTime;
import java.util.Collections;

/**
 *
 * @author AORUS
 */
@Service
public class PacienteServiceImpl implements PacienteService{
    @Autowired
    private PacienteRepository pacienteRepository;


    @Override
    public List<Paciente> listarTodo() {
        return this.pacienteRepository.findAll();
    }
    
    @Override
    public Paciente buscarPacientePorDocumento(String numDoc)
            throws BadRequestException {
        Optional<Paciente> optPac = this.pacienteRepository
                .findByNumDoc(numDoc);
        if (!optPac.isPresent()) {
            throw new BadRequestException("No existe paciente");
        }
        return optPac.get();
    }
}
