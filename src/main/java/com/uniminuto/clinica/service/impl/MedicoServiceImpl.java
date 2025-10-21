package com.uniminuto.clinica.service.impl;

import com.uniminuto.clinica.entity.Especializacion;
import com.uniminuto.clinica.model.RespuestaRs;
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
            // lanzar excepcion
            throw new BadRequestException("No existe la especializacion");
        }

        return this.medicoRepository.findByEspecializacion(optEsp.get());
    }

    @Override
    public RespuestaRs guardarMedico(Medico medico) throws BadRequestException {
        this.isCamposObligatorios(medico);

        if(this.medicoRepository.existsByNumeroDocumento(medico.getNumeroDocumento().toLowerCase())){
            throw new BadRequestException("El medico ya existe. Intente de nuevo.");
        }

        Medico nuevoMedico = new Medico();
        nuevoMedico.setTipoDocumento(nuevoMedico.getTipoDocumento().toLowerCase());
        nuevoMedico.setNumeroDocumento(nuevoMedico.getNumeroDocumento().toLowerCase());
        nuevoMedico.setNombres(nuevoMedico.getNombres().toLowerCase());
        nuevoMedico.setApellidos(nuevoMedico.getApellidos().toLowerCase());
        nuevoMedico.setRegistroProfesional(nuevoMedico.getRegistroProfesional().toLowerCase());
        nuevoMedico.setTelefono(nuevoMedico.getTelefono().toLowerCase());
        nuevoMedico.setEspecializacion(nuevoMedico.getEspecializacion());

        this.medicoRepository.save(nuevoMedico);

        RespuestaRs respuesta = new RespuestaRs();
        respuesta.setStatus(200);
        respuesta.setMensaje("Medico creado exitosamente.");
        return respuesta;
    }

    @Override
    public RespuestaRs actualizarMedico(Medico medico) throws BadRequestException {
        Optional<Medico> optMedico = this.medicoRepository.findById(medico.getId());
        if (optMedico.isEmpty()) {
            throw new BadRequestException("El medico no existe. No se puede actualizar");
        }

        Medico medicoActual = optMedico.get();
        if (!medicoActual.getNumeroDocumento().equals(medico.getNumeroDocumento())) {
            // Consulto si existe un medico por número de documento
            Optional<Medico> optMedicoByDocumento = this.medicoRepository.findByNumeroDocumento(medico.getNumeroDocumento());
            if (optMedicoByDocumento.isPresent()) {
                throw new BadRequestException("El medico ya existe. No se puede actualizar");
            }
            medicoActual.setNumeroDocumento(medico.getNumeroDocumento());
        }

        medicoActual.setNombres(medico.getNombres());
        medicoActual.setApellidos(medico.getApellidos());
        medicoActual.setRegistroProfesional(medico.getRegistroProfesional());
        medicoActual.setEspecializacion(medico.getEspecializacion());
        medicoActual.setTelefono(medico.getTelefono());
        this.medicoRepository.save(medicoActual);
        RespuestaRs rta = new RespuestaRs();
        rta.setStatus(200);
        rta.setMensaje("Se ha actualizado el medico con exito.");
        return rta;
    }


    private void isCamposObligatorios(Medico medico) throws BadRequestException {
        if (medico.getNumeroDocumento() == null
                || medico.getNumeroDocumento().isEmpty()) {
            throw new BadRequestException("El numero de documento es obligatorio.");
        }
        if (medico.getNombres() == null
                || medico.getNombres().isEmpty()) {
            throw new BadRequestException("El nombre es obligatorio.");
        }
        if (medico.getApellidos() == null
                || medico.getApellidos().isEmpty()) {
            throw new BadRequestException("El apellido es obligatorio.");
        }
        if (medico.getEspecializacion() == null) {
            throw new BadRequestException("La especializacion es obligatoria.");
        }
    }

}
