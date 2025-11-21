package com.uniminuto.clinica.service.impl;
import com.uniminuto.clinica.entity.Medicamento;
import com.uniminuto.clinica.entity.Receta;
import com.uniminuto.clinica.entity.Cita;
import com.uniminuto.clinica.model.RecetaRq;
import com.uniminuto.clinica.model.RespuestaRs;
import com.uniminuto.clinica.repository.MedicamentoRepository;
import com.uniminuto.clinica.repository.RecetaRepository;
import com.uniminuto.clinica.repository.CitaRepository;
import com.uniminuto.clinica.service.RecetaService;
import java.time.LocalDateTime;
import java.util.Collections;
import java.util.List;
import java.util.Optional;
import org.apache.coyote.BadRequestException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * @author AORUS
 */
@Service
public class RecetaServiceImpl implements RecetaService {
    @Autowired
    private RecetaRepository recetaRepository;
    @Autowired
    private CitaRepository citaRepository;
    @Autowired
    private MedicamentoRepository medicamentoRepository;
    
    @Override
    public List<Receta> listarTodasLasRecetas() {
        List<Receta> lista = this.recetaRepository.findAll();
        return !lista.isEmpty() ? lista : Collections.EMPTY_LIST;
    }
    
    @Override
    public List<Receta> listarRecetasPorCita(Integer citaId) {
        List<Receta> lista = this.recetaRepository.findByCitaId(citaId);
        return !lista.isEmpty() ? lista : Collections.EMPTY_LIST;
    }
    
    @Override
    public List<Receta> listarRecetasPorMedicamento(Integer medicamentoId) {
        return List.of();
    }
    
    @Override
    public RespuestaRs guardarReceta(RecetaRq recetaNueva) throws BadRequestException {
        validarCamposObligatorios(recetaNueva);
        
        Optional<Cita> citaOpt = this.citaRepository.findById(recetaNueva.getCitaId());
        if (!citaOpt.isPresent()) {
            throw new BadRequestException("La cita no existe");
        }
        
        Optional<Medicamento> optMedicamento = this.medicamentoRepository.findById(recetaNueva.getMedicamentoId());
        if (!optMedicamento.isPresent()) {
            throw new BadRequestException("El medicamento no existe");
        }
        
        Receta nueva = new Receta();
        nueva.setCita(citaOpt.get());
        nueva.setMedicamento(optMedicamento.get());
        nueva.setDosis(recetaNueva.getDosis());
        nueva.setIndicaciones(recetaNueva.getIndicaciones());
        nueva.setFechaCreacionRegistro(LocalDateTime.now());
        
        this.recetaRepository.save(nueva);
        
        RespuestaRs respuesta = new RespuestaRs();
        respuesta.setStatus(200);
        respuesta.setMensaje("La Receta se ha guardado con éxito.");
        return respuesta;
    }
    
    @Override
    public RespuestaRs actualizarReceta(RecetaRq recetaActualizar) throws BadRequestException {
        // Paso 1. Validar campos obligatorios
        validarCamposObligatorios(recetaActualizar);
        
        // Paso 2. Buscar receta por ID
        Optional<Receta> optReceta = this.recetaRepository.findById(recetaActualizar.getId());
        
        if (!optReceta.isPresent()) {
            throw new BadRequestException("La receta no existe.");
        }
        
        // Paso 3. Validar que existan la cita y el medicamento
        Optional<Cita> citaOpt = this.citaRepository.findById(recetaActualizar.getCitaId());
        if (!citaOpt.isPresent()) {
            throw new BadRequestException("La cita no existe");
        }
        
        Optional<Medicamento> optMedicamento = this.medicamentoRepository.findById(recetaActualizar.getMedicamentoId());
        if (!optMedicamento.isPresent()) {
            throw new BadRequestException("El medicamento no existe");
        }
        
        // Paso 4. Actualizar campos
        Receta recetaExistente = optReceta.get();
        recetaExistente.setCita(citaOpt.get());
        recetaExistente.setMedicamento(optMedicamento.get());
        recetaExistente.setDosis(recetaActualizar.getDosis());
        recetaExistente.setIndicaciones(recetaActualizar.getIndicaciones());
        // NO se actualiza fechaCreacionRegistro porque es la fecha original
        
        this.recetaRepository.save(recetaExistente);
        
        // Paso 5. Devolver respuesta
        RespuestaRs respuesta = new RespuestaRs();
        respuesta.setStatus(200);
        respuesta.setMensaje("La receta se ha actualizado con éxito.");
        return respuesta;
    }
    
    private void validarCamposObligatorios(RecetaRq recetaNuevo) throws BadRequestException {
        if (recetaNuevo.getCitaId() == null) {
            throw new BadRequestException("El campo cita es obligatorio");
        }
        if (recetaNuevo.getMedicamentoId() == null) {
            throw new BadRequestException("El campo medicamento es obligatorio");
        }
        if (recetaNuevo.getDosis() == null || recetaNuevo.getDosis().trim().isEmpty()) {
            throw new BadRequestException("El campo dosis es obligatorio");
        }
        if (recetaNuevo.getIndicaciones() == null || recetaNuevo.getIndicaciones().trim().isEmpty()) {
            throw new BadRequestException("El campo indicaciones es obligatorio");
        }
    }
}
