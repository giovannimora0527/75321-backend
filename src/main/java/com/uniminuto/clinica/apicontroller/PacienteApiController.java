package com.uniminuto.clinica.apicontroller;

import com.uniminuto.clinica.api.PacienteApi;
import com.uniminuto.clinica.entity.Paciente;
import com.uniminuto.clinica.model.RespuestaRs;
import com.uniminuto.clinica.service.PacienteService;
import java.util.List;
import org.apache.coyote.BadRequestException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RestController;

/**
 *
 * @author lmora
 */
@RestController
public class PacienteApiController implements PacienteApi {


    @Autowired
    private PacienteService pacienteService;

    @Override
    public ResponseEntity<List<Paciente>> listarPacientes() {
        return ResponseEntity.ok(pacienteService.encontrarTodosLosPacientes());
    }

    @Override
    public ResponseEntity<Paciente> buscarPacienteXIdentificacion(String numeroDocumento)
            throws BadRequestException {
        return ResponseEntity.ok(pacienteService.buscarPacientePorDocumento(numeroDocumento));
    }

    @Override
    public ResponseEntity<List<Paciente>> listarPacientesXOrden(String orden) {
        return ResponseEntity.ok(pacienteService.listarOrdenadoPorFechaNacimiento(orden.equals("asc")));
    }

    @Override
    public ResponseEntity<RespuestaRs> guardarPaciente(Paciente paciente) throws BadRequestException {
        pacienteService.guardarPaciente(paciente);

        RespuestaRs respuesta = new RespuestaRs();
        respuesta.setMensaje("Paciente guardado exitosamente");
        return ResponseEntity.ok(respuesta);
    }
}
