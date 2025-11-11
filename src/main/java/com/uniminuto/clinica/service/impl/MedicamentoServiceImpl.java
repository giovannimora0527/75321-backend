package com.uniminuto.clinica.service.impl;

import com.uniminuto.clinica.entity.Medicamento;
import com.uniminuto.clinica.model.MedicamentoRq;
import com.uniminuto.clinica.model.RespuestaRs;
import com.uniminuto.clinica.repository.MedicamentoRepository;
import com.uniminuto.clinica.service.MedicamentoService;
import org.apache.coyote.BadRequestException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

@Service
public class MedicamentoServiceImpl implements MedicamentoService {

    @Autowired
    private MedicamentoRepository medicamentoRepository;

    @Override
    public List<Medicamento> listarMedicamentos() {
        return medicamentoRepository.findAll()
                .stream()
                .sorted((m1, m2) -> m2.getFechaCompra().compareTo(m1.getFechaCompra()))
                .toList();
    }

    @Override
    public RespuestaRs guardarMedicamento(MedicamentoRq medicamentoRq) throws BadRequestException {
        // Paso 1. Validar el formulario
        this.validarFormulario(medicamentoRq);
        // Paso 2. Consultar si el medicamento ya existe por nombre
        Optional<Medicamento> optMedicamento = medicamentoRepository
                .findByNombre(medicamentoRq.getNombre());

        // Paso 3. Si existe lanzo error
        if (optMedicamento.isPresent()) {
            throw new BadRequestException("El medicamento ya existe");
        }
        // Paso 4. Si no existe, creo el medicamento y lo guardo
        Medicamento nuevo = this.mapearAMedicamento(medicamentoRq);
        medicamentoRepository.save(nuevo);

        // Paso 5. Retorno la respuesta
        RespuestaRs rta = new RespuestaRs();
        rta.setMensaje("Medicamento creado exitosamente");
        rta.setStatus(200);

        return rta;
    }

    @Override
    public RespuestaRs actualizarMedicamento(MedicamentoRq medicamentoRq)
            throws BadRequestException {
        // Paso 1. Consultar si el campo id existe y viene en el request
        if (medicamentoRq.getId() == null) {
            throw new BadRequestException("El id del medicamento es obligatorio");
        }
        // Paso 2. Consultar si el medicamento existe por id
        Optional<Medicamento> optMedicamento = medicamentoRepository
                .findById(medicamentoRq.getId());
        // Paso 3. Si no existe lanzo error
        if (!optMedicamento.isPresent()) {
            throw new BadRequestException("El medicamento no existe y no se puede actualizar");
        }
        // Paso 4. Si existe voy y valido que el atributo nombre cambie y si cambia lo consulto por nombre
        Medicamento medicamentoActual = optMedicamento.get();
        if (!medicamentoActual.getNombre()
                .toLowerCase().equals(medicamentoRq.getNombre().toLowerCase())) {
            Optional<Medicamento> optMedicamentoPorNombre = medicamentoRepository
                    .findByNombre(medicamentoRq.getNombre());
            // Paso 5. Si existe por nombre lanzo error
            if (optMedicamentoPorNombre.isPresent()) {
                throw new BadRequestException("El nombre del medicamento ya existe");
            }
        }

        // Paso 6. Si no existe por nombre, actualizo los datos del medicamento
        medicamentoActual.setNombre(medicamentoRq.getNombre() == null ? medicamentoActual.getNombre() : medicamentoRq.getNombre());
        medicamentoActual.setDescripcion(medicamentoRq.getDescripcion() == null ? medicamentoActual.getDescripcion() : medicamentoRq.getDescripcion());
        medicamentoActual.setPresentacion(medicamentoRq.getPresentacion() == null ? medicamentoActual.getPresentacion() : medicamentoRq.getPresentacion());
        medicamentoActual.setFechaCompra(medicamentoRq.getFechaCompra() == null ? medicamentoActual.getFechaCompra() : medicamentoRq.getFechaCompra());
        medicamentoActual.setFechaVence(medicamentoRq.getFechaVence() == null ? medicamentoActual.getFechaVence() : medicamentoRq.getFechaVence());
        medicamentoActual.setFechaModificacionRegistro(LocalDateTime.now());
        this.medicamentoRepository.save(medicamentoActual);
        // Paso 7. Retorno la respuesta
        RespuestaRs rta = new RespuestaRs();
        rta.setMensaje("Medicamento actualizado exitosamente");
        rta.setStatus(200);

        return rta;
    }


    /**
     * Mapea el request a la entidad Medicamento
     * @param medicamentoRq dato de entrada.
     * @return objeto mapeado medicamento.
     */
    private Medicamento mapearAMedicamento(MedicamentoRq medicamentoRq) {
        Medicamento nuevo = new Medicamento();
        nuevo.setNombre(medicamentoRq.getNombre());
        nuevo.setDescripcion(medicamentoRq.getDescripcion());
        nuevo.setPresentacion(medicamentoRq.getPresentacion());
        nuevo.setFechaCompra(medicamentoRq.getFechaCompra());
        nuevo.setFechaVence(medicamentoRq.getFechaVence());
        nuevo.setFechaCreacionRegistro(LocalDateTime.now());
        return nuevo;
    }

    private void validarFormulario(MedicamentoRq medicamentoRq) throws BadRequestException {
        if (medicamentoRq.getNombre() == null || medicamentoRq.getNombre().isBlank()) {
            throw new BadRequestException("El nombre es obligatorio");
        }
        if (medicamentoRq.getDescripcion() == null || medicamentoRq.getDescripcion().isBlank()) {
            throw new BadRequestException("El campo descripcion es obligatorio");
        }
        if (medicamentoRq.getPresentacion() == null || medicamentoRq.getPresentacion().isBlank()) {
            throw new BadRequestException("El campo presentacion es obligatorio");
        }
        if (medicamentoRq.getFechaCompra() == null) {
            throw new BadRequestException("El campo fecha de compra es obligatorio");
        }
        if (medicamentoRq.getFechaVence() == null) {
            throw new BadRequestException("El campo fecha de vencimiento es obligatorio");
        }
    }


}
