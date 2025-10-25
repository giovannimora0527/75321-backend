package com.uniminuto.clinica.service.impl;

import com.uniminuto.clinica.entity.Medicamento;
import com.uniminuto.clinica.repository.MedicamentoRepository;
import com.uniminuto.clinica.service.MedicamentoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@Transactional
public class MedicamentoServiceImpl implements MedicamentoService {
    
    @Autowired
    private MedicamentoRepository medicamentoRepository;
    
    @Override
    @Transactional(readOnly = true)
    public List<Medicamento> listarTodos() {
        return medicamentoRepository.findAllByOrderByIdDesc();
    }
    
    @Override
    @Transactional(readOnly = true)
    public Medicamento buscarPorId(Long id) {
        return medicamentoRepository.findById(id).orElse(null);
    }
    
    @Override
    public Medicamento guardar(Medicamento medicamento) {
        return medicamentoRepository.save(medicamento);
    }
    
    @Override
    public void eliminar(Long id) {
        medicamentoRepository.deleteById(id);
    }
}