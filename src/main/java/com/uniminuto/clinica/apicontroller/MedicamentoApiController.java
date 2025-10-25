package com.uniminuto.clinica.apicontroller;

import com.uniminuto.clinica.api.MedicamentoApi;
import com.uniminuto.clinica.model.CrearMedicamentoRq;
import com.uniminuto.clinica.model.MedicamentoRs;
import com.uniminuto.clinica.service.MedicamentoService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequiredArgsConstructor
public class MedicamentoApiController implements MedicamentoApi {

    private final MedicamentoService medicamentoService;
    @Override
    public MedicamentoRs crear(CrearMedicamentoRq rq) {
        return medicamentoService.crear(rq);
    }

    @Override
    public List<MedicamentoRs> listar() {
        return medicamentoService.listar();
    }

    @Override
    public MedicamentoRs actualizar(Long id, CrearMedicamentoRq rq) {
        return medicamentoService.actualizar(id,rq);
    }
}
