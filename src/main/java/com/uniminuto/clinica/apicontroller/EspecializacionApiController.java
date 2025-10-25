package com.uniminuto.clinica.apicontroller;

import com.uniminuto.clinica.api.EspecializacionApi;
import com.uniminuto.clinica.entity.Especializacion;
import com.uniminuto.clinica.service.EspecializacionService;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
    @RequestMapping("/especializacion")
    @CrossOrigin("*")
    public class EspecializacionApiController implements EspecializacionApi {

        @Autowired
        private EspecializacionService service;

        @GetMapping("/listar")
        public ResponseEntity<List<Especializacion>> listar() {
            return ResponseEntity.ok(service.listarTodo());
        }

        @GetMapping("/buscar")
        public ResponseEntity<Especializacion> buscarPorCodigo(@RequestParam String codigo) {
            return ResponseEntity.ok(service.buscarPorCodigo(codigo));
        }

        @PostMapping("/crear")
        public ResponseEntity<Especializacion> crear(@RequestBody Especializacion especializacion) {
            return ResponseEntity.ok(service.crear(especializacion));
        }

        @PutMapping("/actualizar/{id}")
        public ResponseEntity<Especializacion> actualizar(@PathVariable Long id, @RequestBody Especializacion especializacion) {
            return ResponseEntity.ok(service.actualizar(id, especializacion));
        }
    }