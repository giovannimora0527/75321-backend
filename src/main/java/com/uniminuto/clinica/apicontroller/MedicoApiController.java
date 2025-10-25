package com.uniminuto.clinica.apicontroller;

import com.uniminuto.clinica.api.MedicoApi;
import com.uniminuto.clinica.entity.Medico;
import com.uniminuto.clinica.service.MedicoService;
import java.util.List;
import org.apache.coyote.BadRequestException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.GetMapping;

@RestController
@RequestMapping("/medico")
@CrossOrigin(origins = "*")
public class MedicoApiController implements MedicoApi {

    @Autowired
    private MedicoService medicoService;

    @Override
    @GetMapping("/listar")
    public ResponseEntity<List<Medico>> listarMedicos() {
        return ResponseEntity.ok(this.medicoService.listarTodo());
    }

    @Override
    @GetMapping("/buscar-por-especialidad")
    public ResponseEntity<List<Medico>> listarPorEspecialidad(
            @RequestParam String codigoEspecializacion) throws BadRequestException {
        return ResponseEntity.ok(
                this.medicoService.obtenerMedicosPorEspecializacion(codigoEspecializacion)
        );
    }
}
