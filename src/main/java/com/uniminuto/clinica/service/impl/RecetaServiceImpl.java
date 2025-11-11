package com.uniminuto.clinica.service.impl;

import com.uniminuto.clinica.entity.Cita;
import com.uniminuto.clinica.entity.Medicamento;
import com.uniminuto.clinica.entity.Receta;
import com.uniminuto.clinica.model.CrearRecetaRq;
import com.uniminuto.clinica.model.RecetaRs;
import com.uniminuto.clinica.repository.CitaRepository;
import com.uniminuto.clinica.repository.MedicamentoRepository;
import com.uniminuto.clinica.repository.RecetaRepository;
import com.uniminuto.clinica.service.RecetaService;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.server.ResponseStatusException;

import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

@Service
@AllArgsConstructor
public class RecetaServiceImpl implements RecetaService {
    private final RecetaRepository recetaRepository;
    private final CitaRepository citaRepository;
    private final MedicamentoRepository medicamentoRepository;

    @Override
    @Transactional
    public RecetaRs crear(CrearRecetaRq rq) {
        //Validar la existencia de una cita

        Cita cita=citaRepository.findById(rq.getCitaId())
                .orElseThrow(()->new ResponseStatusException(HttpStatus.NOT_FOUND,"lA cita no existe"));

        //validar existencia de un medicamento

        Medicamento med=medicamentoRepository.findById(rq.getMedicamentoId())
                .orElseThrow(()->new ResponseStatusException(HttpStatus.NOT_FOUND,"El medicamento no existe"));

        //Construir y guardar la receta

        Receta r=new Receta();
        r.setCita(cita);
        r.setMedicamento(med);
        r.setDosis(rq.getDosis());
        r.setIndicaciones(rq.getIndicaciones());
        r.setFechaActualizacionRegistro(null);

        r=recetaRepository.save(r);

        //Respuesta

        return new RecetaRs(
                r.getId(),
                cita.getId(),
                med.getId(),
                med.getName(),
                r.getDosis(),
                r.getIndicaciones(),
                r.getFechaCreacionRegistro()
        );
    }

    @Override
    @Transactional
    public List<RecetaRs> listarRecientes() {
        return recetaRepository.findAllByOrderByFechaCreacionRegistroDesc().stream().map(this::toDto).collect(Collectors.toList());

    }

    @Override
    public RecetaRs actualizar(Long id, CrearRecetaRq rq) {
        //Buscar la receta existente
         Receta receta=recetaRepository.findById(id)
                 .orElseThrow(()->new ResponseStatusException(HttpStatus.NOT_FOUND,"la receta no existe"));
         //Validar la existencia de una cita y medicamento
        Cita cita=citaRepository.findById(rq.getCitaId())
                .orElseThrow(()->new ResponseStatusException(HttpStatus.NOT_FOUND,"La cita no existe"));

        Medicamento medicamento=medicamentoRepository.findById(rq.getMedicamentoId())
                .orElseThrow(()->new ResponseStatusException(HttpStatus.NOT_FOUND,"El medicamento no existe"));

        //actualizamos campos
        receta.setCita(cita);
        receta.setMedicamento(medicamento);
        receta.setDosis(rq.getDosis());
        receta.setIndicaciones(rq.getIndicaciones());
        receta.setFechaActualizacionRegistro(LocalDateTime.now());
        //guardamos campos
        recetaRepository.save(receta);
        //retornar la respuesta
        return new RecetaRs(
                receta.getId(),
                receta.getCita().getId(),
                receta.getMedicamento().getId(),
                receta.getMedicamento().getName(),
                receta.getDosis(),
                receta.getIndicaciones(),
                receta.getFechaCreacionRegistro()
        );
    }

    //Hacemos privado este metodo usamos map.collectors y stream
    private RecetaRs toDto(Receta r){
        Medicamento m=r.getMedicamento();
        return new RecetaRs(
               r.getId(),
               r.getCita().getId(), //espera un Long
                m.getId(),
                m.getName(),
                r.getDosis(),
                r.getIndicaciones(),
                r.getFechaCreacionRegistro()

        );

    }

}
