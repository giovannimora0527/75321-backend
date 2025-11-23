package com.uniminuto.clinica.apicontroller;

import com.uniminuto.clinica.api.EspecializacionApi;
import com.uniminuto.clinica.entity.Especializacion;
import com.uniminuto.clinica.service.EspecializacionService;
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
public class EspecializacionApiController implements EspecializacionApi {
    
    @Autowired
    private EspecializacionService servicio;

    @Override
    public ResponseEntity<List<Especializacion>> listarEspecializaciones() {
        return ResponseEntity.ok(this.servicio.listarTodo());
    }

    @Override
    public ResponseEntity<Especializacion> buscarPorCodigo(String codigo) 
            throws BadRequestException {
        return ResponseEntity.ok(this.servicio
                .buscarEspecializacionPorCod(codigo));
    }
    
}
