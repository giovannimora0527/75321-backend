package com.uniminuto.clinica.apicontroller;

import com.uniminuto.clinica.api.MedicoApi;
import com.uniminuto.clinica.entity.Medico;
import com.uniminuto.clinica.service.MedicoService;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
//Este es el contralador llamamos service para poder recibir peticiones
@RestController
public class MedicoApiController implements MedicoApi {

    private final MedicoService medicoService;

 //Constructor para inyectar  service
    public MedicoApiController(MedicoService medicoService) {
        this.medicoService = medicoService;
    }

    @Override
    public List<Medico> listarTodo() {
        return medicoService.listarTodo();
    }

    @Override // llamar al servicie
    public List<Medico> obtenerMedicosPorEspecializacion(String codigoEspecializacion) {
        return medicoService.obtenerMedicosPorEspecializacion(codigoEspecializacion);
    }
}

