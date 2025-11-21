package com.uniminuto.clinica.apicontroller;

import com.uniminuto.clinica.api.EspecializacionApi;
import com.uniminuto.clinica.entity.Especializacion;
import com.uniminuto.clinica.service.EspecializacionService;
import java.util.List;
import org.apache.coyote.BadRequestException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author lmora
 */
@RestController
public class EspecializacionApiController implements EspecializacionApi {
    
    @Autowired
    private EspecializacionService especializacionService;
    
    @Override
    public ResponseEntity<List<Especializacion>> listarEspecializaciones() {
        return ResponseEntity.ok(this.especializacionService.listarTodo());
    }
    
    @Override
    public ResponseEntity<Especializacion> buscarPorCodigo(String codigo) 
        throws BadRequestException {
        return ResponseEntity.ok(this.especializacionService.obtenerPorCodigo(codigo));
    }
    
    @Override
    public ResponseEntity<Especializacion> crearEspecializacion(Especializacion especializacion) 
        throws BadRequestException {
        Especializacion nuevaEspecializacion = this.especializacionService.crear(especializacion);
        return ResponseEntity.status(HttpStatus.CREATED).body(nuevaEspecializacion);
    }
    
    @Override
    public ResponseEntity<Especializacion> actualizarEspecializacion(Long id, 
        Especializacion especializacion) throws BadRequestException {
        Especializacion especializacionActualizada = 
            this.especializacionService.actualizar(id, especializacion);
        return ResponseEntity.ok(especializacionActualizada);
    }
}
