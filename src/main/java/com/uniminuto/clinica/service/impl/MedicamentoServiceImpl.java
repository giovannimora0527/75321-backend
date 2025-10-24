package com.uniminuto.clinica.service.impl;

import com.uniminuto.clinica.entity.Medicamento;
import com.uniminuto.clinica.model.CrearMedicamentoRq;
import com.uniminuto.clinica.model.MedicamentoRs;
import com.uniminuto.clinica.repository.MedicamentoRepository;
import com.uniminuto.clinica.service.MedicamentoService;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.server.ResponseStatusException;

import java.time.LocalDate;
import java.util.List;
import java.util.stream.Collectors;

@Service
@AllArgsConstructor
public class MedicamentoServiceImpl implements MedicamentoService {

    private final MedicamentoRepository medicamentoRepository;

    @Override
    @Transactional
    public MedicamentoRs crear(CrearMedicamentoRq rq) {
        //Validaciones del Negocio
        //Validacion que el nombre es obligatorio
        if(rq.getNombre()==null||rq.getNombre().trim().isEmpty()){
            throw  new ResponseStatusException(HttpStatus.BAD_REQUEST,"El nombre es obligatorio");
        }
        //Validar la presentacion
        if(rq.getPresentacion()==null||rq.getPresentacion().trim().isEmpty()){
            throw  new ResponseStatusException(HttpStatus.BAD_REQUEST,"La presentacion es obligatoria");
        }
        //Validar cantidades
        if (rq.getCantidad() == null || rq.getCantidad() <= 0) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "La cantidad debe ser mayor que cero");
        }
        //Verificar la fecha de vencimiento
        if (rq.getFechaVencimiento() == null || !rq.getFechaVencimiento().isAfter(LocalDate.now())) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "La fecha de vencimiento debe ser posterior a hoy");
        }
        //Evitar Duplicados Nombre y misma presentacion
        String nombre = rq.getNombre().trim();
        String presentacion = rq.getPresentacion().trim();

        //Llamamos el metodo del repositorio para evitar duplicados
        if (medicamentoRepository.existsByNameIgnoreCaseAndPresentacionIgnoreCase(nombre, presentacion)) {
            throw new ResponseStatusException(
                    HttpStatus.CONFLICT,
                    "Ya existe un medicamento con el mismo nombre y presentación"
            );
        }
        //Construir la Respuesta
        Medicamento med = new Medicamento();
        //Trim se usa para eliminar los espacios
        med.setName(rq.getNombre().trim());
        med.setDescripcion(rq.getDescripcion()); // puede ser null
        med.setPresentacion(rq.getPresentacion().trim());
        med.setCantidad(rq.getCantidad());
        med.setFechaVencimiento(rq.getFechaVencimiento());

        med = medicamentoRepository.save(med);
        //Devolver la respuesta Mapeada
        return toDto(med);
    }

    @Override
    public List<MedicamentoRs> listar() {
        return medicamentoRepository.findAll()
                .stream()
                .map(this::toDto)
                .collect(Collectors.toList());
    }
    //Metodo Privado para mapear los campos
    private MedicamentoRs toDto(Medicamento medicamento) {
        return new MedicamentoRs(
                medicamento.getId(),
                medicamento.getName(),
                medicamento.getPresentacion(),
                medicamento.getCantidad(),
                medicamento.getFechaVencimiento(),
                medicamento.getDescripcion()
        );
    }

}
