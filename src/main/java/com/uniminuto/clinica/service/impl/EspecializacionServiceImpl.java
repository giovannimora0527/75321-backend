package com.uniminuto.clinica.service.impl;

import com.uniminuto.clinica.entity.Especializacion;
import com.uniminuto.clinica.model.EspecializacionRq;
import com.uniminuto.clinica.model.RespuestaRs;
import com.uniminuto.clinica.repository.EspecializacionRepository;
import com.uniminuto.clinica.service.EspecializacionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class EspecializacionServiceImpl implements EspecializacionService {

    @Autowired
    private EspecializacionRepository especializacionRepository;

    @Override
    public List<Especializacion> listarEspecializaciones() {
        return this.especializacionRepository.findAll();
    }

    @Override
    public RespuestaRs guardarEspecializacion(EspecializacionRq rq) {
        RespuestaRs respuesta = new RespuestaRs();

        Optional<Especializacion> existentePorCodigo =
                especializacionRepository.findByCodigoEspecializacion(rq.getCodigoEspecializacion());

        Especializacion especializacion = existentePorCodigo.orElse(new Especializacion());

        Optional<Especializacion> existentePorNombre =
                especializacionRepository.findByNombre(rq.getNombre());

        if (existentePorNombre.isPresent() &&
                (!existentePorCodigo.isPresent() ||
                        !existentePorNombre.get().getCodigoEspecializacion().equals(rq.getCodigoEspecializacion()))) {
            respuesta.setStatus(0);
            respuesta.setMensaje("Ya existe una especialización con ese nombre");
            return respuesta;
        }

        // Asignar o actualizar campos
        especializacion.setNombre(rq.getNombre());
        especializacion.setDescripcion(rq.getDescripcion());
        especializacion.setCodigoEspecializacion(rq.getCodigoEspecializacion());

        // Guardar (inserta o actualiza según corresponda)
        especializacionRepository.save(especializacion);

        if (existentePorCodigo.isPresent()) {
            respuesta.setStatus(1);
            respuesta.setMensaje("Especialización actualizada correctamente");
        } else {
            respuesta.setStatus(1);
            respuesta.setMensaje("Especialización creada correctamente");
        }

        return respuesta;
    }
}
