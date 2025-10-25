package com.uniminuto.clinica.service;

import com.uniminuto.clinica.model.CrearMedicamentoRq;
import com.uniminuto.clinica.model.MedicamentoRs;

import java.util.List;

public interface MedicamentoService {
    //Firma de crear Medicamentos devolviendo el RS
    MedicamentoRs crear(CrearMedicamentoRq rq);

    //listar si se traba revisar las Relaciones de Medicamento
    List<MedicamentoRs> listar();

    //Actualizar con el id
    MedicamentoRs actualizar(Long id, CrearMedicamentoRq rq);

}
