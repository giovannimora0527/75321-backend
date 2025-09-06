package com.uniminuto.clinica.api;

import com.uniminuto.clinica.entity.Medico;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.List;
//Definimos ENDPOINTS MAS NO LOGICA
@CrossOrigin(origins = "*")
@RequestMapping("/medico")//ENDPOINT parte 1
public interface MedicoApi {

    @GetMapping(value = "/listar", produces = "application/json")
    List<Medico> listarTodo();  // firma del endpoint /METODO DEL ENDPONT

    @GetMapping(value = "/por-especializacion", produces = "application/json")
    List<Medico> obtenerMedicosPorEspecializacion(@RequestParam("codigo") String codigoEspecializacion);

    //EJEMPLO https://localhost:8000/clinica/medico/listar

}