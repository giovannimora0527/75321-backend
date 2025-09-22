package com.uniminuto.clinica.service.impl;

import com.uniminuto.clinica.entity.Medicamento;
import com.uniminuto.clinica.entity.Paciente;
import com.uniminuto.clinica.model.MedicamentoRq;
import com.uniminuto.clinica.model.RespuestaRs;
import com.uniminuto.clinica.repository.MedicamentoRepository;
import com.uniminuto.clinica.service.MedicamentoService;
import org.apache.coyote.BadRequestException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.Comparator;
import java.util.List;
import java.util.Optional;

@Service

public class MedicamentoServiceImpl implements MedicamentoService {

    @Autowired
    private MedicamentoRepository medicamentoRepository;

    @Override
    public List<Medicamento> listarMedicamentos() {
        List<Medicamento> lista = medicamentoRepository.findAll();
        lista.sort(Comparator.comparing(Medicamento::getFechaCompra));
        return lista;
    }

    @Override
    public Medicamento listarMedNombre(String nombre)
            throws BadRequestException {
        Optional<Medicamento> optMedicamento;
        optMedicamento = this.medicamentoRepository
                .findByNombre(nombre);
        if (!optMedicamento.isPresent()) {
            throw new BadRequestException("No existe el medicamento");
        }
        return optMedicamento.get();
    }

    @Override
    public RespuestaRs guardarMedicamento(MedicamentoRq medicamentoRq) throws BadRequestException {
        // Paso 1. Validar que los campos de entrada lleguen
        this.validarCampos(medicamentoRq);

        // Paso 2. Validar que no exista un medicamento con el mismo nombre
        Optional<Medicamento> optMedicamento = this.medicamentoRepository
                .findByNombre(medicamentoRq.getNombre());
        if (optMedicamento.isPresent()) {
            throw new BadRequestException("Ya existe un medicamento con el mismo nombre");
        }
        // Paso 3. Crar el objeto medicamento a guardar
        Medicamento medicamentoAGuardar = this.convertirAMedicamento(medicamentoRq);

        // Paso 4. Guardar el medicamento
        this.medicamentoRepository.save(medicamentoAGuardar);

        // Paso 5. Crear la respuesta y retornar la respuesta
        RespuestaRs respuestaRs = new RespuestaRs();
        respuestaRs.setStatus(200);
        respuestaRs.setMensaje("Medicamento guardado correctamente");
        return respuestaRs;
    }

    private void validarCampos(MedicamentoRq medicamentoRq) throws BadRequestException {
        if (medicamentoRq.getNombre() == null || medicamentoRq.getNombre().isEmpty()) {
            throw new BadRequestException("El campo nombre es obligatorio");
        }
        if (medicamentoRq.getDescripcion() == null || medicamentoRq.getDescripcion().isEmpty()) {
            throw new BadRequestException("El campo descripcion es obligatorio");
        }
        if (medicamentoRq.getPresentacion() == null || medicamentoRq.getPresentacion().isEmpty()) {
            throw new BadRequestException("El campo presentacion es obligatorio");
        }
        if (medicamentoRq.getFechaCompra() == null) {
            throw new BadRequestException("El campo fecha de compra es obligatorio");
        }
        if (medicamentoRq.getFechaVence() == null) {
            throw new BadRequestException("El campo fecha vencimiento es obligatorio");
        }
    }

    private Medicamento convertirAMedicamento(MedicamentoRq medicamentoRq) {
        Medicamento nueva = new Medicamento();
        nueva.setNombre(medicamentoRq.getNombre());
        nueva.setPresentacion(medicamentoRq.getPresentacion());
        nueva.setDescripcion(medicamentoRq.getDescripcion());
        nueva.setFechaCreacionRegistro(LocalDateTime.now());
        nueva.setFechaCompra(medicamentoRq.getFechaCompra());
        nueva.setFechaVence(medicamentoRq.getFechaVence());
        return nueva;
    }

    @Override
    public RespuestaRs actualizarMedicamento(MedicamentoRq medicamentoRq) throws BadRequestException {
        if (medicamentoRq.getId() == null) {
            throw new BadRequestException("El id del medicamento es obligatorio");
        }
        // Paso 1. Consultar si existe el registro a modificar
        // y traerlo
        Optional<Medicamento> optMedicamento = this.medicamentoRepository.findById(medicamentoRq.getId());
        if (!optMedicamento.isPresent()) {
            throw new BadRequestException("No existe el registro a modificar.");
        }

        Medicamento medicamentoActualizar = optMedicamento.get();

        // Paso 2. Verificar que no exista el medicamento por nombre si este cambia
        if (!medicamentoActualizar.getNombre().toLowerCase()
                .equals(medicamentoRq.getNombre().toLowerCase())) {
            Optional<Medicamento> optMedicamentoPorNombre = this.medicamentoRepository
                    .findByNombre(medicamentoRq.getNombre());
            if (optMedicamentoPorNombre.isPresent()) {
                throw new BadRequestException("El medicamento ya exite");
            }
        }

        // Paso 3. Actualizar los campos del medicamento consultado
        medicamentoActualizar.setNombre(medicamentoRq.getNombre() == null ? medicamentoActualizar.getNombre() : medicamentoRq.getNombre());
        medicamentoActualizar.setDescripcion(medicamentoRq.getDescripcion() == null ? medicamentoActualizar.getDescripcion() : medicamentoRq.getDescripcion());
        medicamentoActualizar.setPresentacion(medicamentoRq.getPresentacion() == null ? medicamentoActualizar.getPresentacion() : medicamentoRq.getPresentacion());
        medicamentoActualizar.setFechaCompra(medicamentoRq.getFechaCompra() == null ? medicamentoActualizar.getFechaCompra() : medicamentoRq.getFechaCompra());
        medicamentoActualizar.setFechaVence(medicamentoRq.getFechaVence() == null ? medicamentoActualizar.getFechaVence() : medicamentoRq.getFechaVence());
        medicamentoActualizar.setFechaModificacionRegistro(LocalDateTime.now());
        // Paso 4. Guardar el medicamento
        this.medicamentoRepository.save(medicamentoActualizar);

        // Paso 5. Crear la respuesta y retornar la respuesta
        RespuestaRs rta = new RespuestaRs();
        rta.setMensaje("Se ha actualizado el registro satisfactoriamente");
        rta.setStatus(200);
        return rta;
    }
}
