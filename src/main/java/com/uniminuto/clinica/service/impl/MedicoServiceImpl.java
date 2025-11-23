package com.uniminuto.clinica.service.impl;

import com.uniminuto.clinica.entity.Especializacion;
import com.uniminuto.clinica.entity.Medico;
import com.uniminuto.clinica.model.MedicoRq;
import com.uniminuto.clinica.model.RespuestaRs;
import com.uniminuto.clinica.repository.EspecializacionRepository;
import com.uniminuto.clinica.repository.MedicoRepository;
import com.uniminuto.clinica.service.EspecializacionService;
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
    private EspecializacionService especializacionService;


    @Autowired
    private EspecializacionRepository especializacionRepository;

    @Override
    public List<Medico> listarMedicos() {
        return this.medicoRepository.findAll();
    }

    @Override
    public List<Medico> buscarPorEspecialidad(String codigo)
            throws BadRequestException {
        Especializacion e = this.especializacionService
                .buscarEspecializacionPorCod(codigo);
        return this.medicoRepository.findByEspecializacion(e);
    }

    @Override
    public RespuestaRs guardarMedico(MedicoRq medicoRq) throws BadRequestException {
        Optional<Medico> optmedico = this.medicoRepository.findByDocumento(
                medicoRq.getDocumento()
        );
        if (optmedico.isPresent()) {
            throw new BadRequestException("El número de documento ya está registrado");
        }

        optmedico = this.medicoRepository.findByRegistroProfesional(medicoRq.getRegistroProfesional());
        if (optmedico.isPresent()) {
            throw new BadRequestException("Existe otro medico con el registro profesional ingresado");
        }

        Optional<Especializacion> optEsp = this.especializacionRepository
                .findById(medicoRq.getEspecializacion());
        if (optEsp.isEmpty()) {
            throw new BadRequestException("La especialización no existe");
        }

        Medico medicoGuardar = this.convertToRqToEntidad(medicoRq, optEsp.get());
        this.medicoRepository.save(medicoGuardar);
        RespuestaRs rta = new RespuestaRs();
        rta.setMensaje("Médico guardado exitosamente");
        rta.setStatus(200);
        return rta;
    }

    private Medico convertToRqToEntidad(MedicoRq medicoRq, Especializacion especialidad) {
        Medico medico = new Medico();
        medico.setNombres(medicoRq.getNombres());
        medico.setApellidos(medicoRq.getApellidos());
        medico.setDocumento(medicoRq.getDocumento());
        medico.setTipoDocumento(medicoRq.getTipoDocumento());
        medico.setRegistroProfesional(medicoRq.getRegistroProfesional());
        medico.setEspecializacion(especialidad);
        medico.setTelefono(medicoRq.getTelefono());
        return medico;
    }

    @Override
    public RespuestaRs actualizarsMedico(MedicoRq medicoRq) throws BadRequestException {
        return null;
    }

}
