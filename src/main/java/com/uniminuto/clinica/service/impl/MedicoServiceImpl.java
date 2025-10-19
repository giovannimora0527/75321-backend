package com.uniminuto.clinica.service.impl;

import com.uniminuto.clinica.entity.Especializacion;
import com.uniminuto.clinica.entity.Medicamento;
import com.uniminuto.clinica.entity.Medico;
import com.uniminuto.clinica.model.MedicamentoRq;
import com.uniminuto.clinica.model.MedicoRq;
import com.uniminuto.clinica.model.RespuestaRs;
import com.uniminuto.clinica.repository.EspecializacionRepository;
import com.uniminuto.clinica.repository.MedicoRepository;
import com.uniminuto.clinica.service.MedicoService;

import java.time.LocalDateTime;
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
            // lanzar excepcion
            throw new BadRequestException("No existe la especializacion");
        }

        return this.medicoRepository.findByEspecializacion(optEsp.get());
    }

    @Override
    public Medico guardar(Medico medico){
        return medicoRepository.save(medico);
    }

    private void validarCampos(MedicoRq medicoRq) throws BadRequestException {
        if (medicoRq.getNombres() == null || medicoRq.getNombres().isEmpty()) {
            throw new BadRequestException("El campo nombre es obligatorio");
        }
        if (medicoRq.getApellidos() == null || medicoRq.getApellidos().isEmpty()) {
            throw new BadRequestException("El campo apellidos es obligatorio");
        }
        if (medicoRq.getTelefono() == null || medicoRq.getTelefono().isEmpty()) {
            throw new BadRequestException("El campo telefono es obligatorio");
        }
        if (medicoRq.getNumeroDocumento() == null || medicoRq.getNumeroDocumento().isEmpty()) {
            throw new BadRequestException("El campo documento es obligatorio");
        }
    }

    private Medico convertirAMedico(MedicoRq medicoRq) {
        Medico nueva = new Medico();
        nueva.setNombres(medicoRq.getNombres());
        nueva.setNombres(medicoRq.getApellidos());
        nueva.setTelefono(medicoRq.getTelefono());
        nueva.setNumeroDocumento(medicoRq.getNumeroDocumento());
        return nueva;
    }


    @Override
    public RespuestaRs actualizarMedico(MedicoRq medicoRq) throws BadRequestException {
        return null;
    }

}