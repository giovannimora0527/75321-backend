package com.uniminuto.clinica.apicontroller;

import com.uniminuto.clinica.entity.Medicamento;
import com.uniminuto.clinica.service.MedicamentoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/medicamentos")
@CrossOrigin(origins = "*")
public class MedicamentoController {
    
    @Autowired
    private MedicamentoService medicamentoService;
    
    @GetMapping
    public ResponseEntity<List<Medicamento>> listarMedicamentos() {
        List<Medicamento> medicamentos = medicamentoService.listarTodos();
        return ResponseEntity.ok(medicamentos);
    }
    
    @GetMapping("/{id}")
    public ResponseEntity<Medicamento> obtenerMedicamento(@PathVariable Long id) {
        Medicamento medicamento = medicamentoService.buscarPorId(id);
        if (medicamento != null) {
            return ResponseEntity.ok(medicamento);
        }
        return ResponseEntity.notFound().build();
    }
    
    @PostMapping
    public ResponseEntity<Medicamento> crearMedicamento(@RequestBody Medicamento medicamento) {
        Medicamento nuevoMedicamento = medicamentoService.guardar(medicamento);
        return ResponseEntity.status(HttpStatus.CREATED).body(nuevoMedicamento);
    }
    
    @PutMapping("/{id}")
    public ResponseEntity<Medicamento> actualizarMedicamento(
            @PathVariable Long id, 
            @RequestBody Medicamento medicamento) {
        medicamento.setId(id);
        Medicamento actualizado = medicamentoService.guardar(medicamento);
        return ResponseEntity.ok(actualizado);
    }
    
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> eliminarMedicamento(@PathVariable Long id) {
        medicamentoService.eliminar(id);
        return ResponseEntity.noContent().build();
    }
}