package com.uniminuto.clinica.service;

import com.uniminuto.clinica.entity.Medicamento;
import java.util.List;

public interface MedicamentoService {
    List<Medicamento> listarTodos();
    Medicamento buscarPorId(Long id);
    Medicamento guardar(Medicamento medicamento);
    void eliminar(Long id);
}