package com.uniminuto.clinica.service.impl;

import com.uniminuto.clinica.entity.Especializacion;
import com.uniminuto.clinica.repository.EspecializacionRepository;
import com.uniminuto.clinica.service.EspecializacionService;
import java.util.List;
import java.util.Optional;
import org.apache.coyote.BadRequestException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * @author jorge
 */
@Service
public class EspecializacionServiceImpl implements EspecializacionService {
    
    @Autowired
    private EspecializacionRepository especializacionRepository;
    
    @Override
    @Transactional(readOnly = true)
    public List<Especializacion> listarTodo() {
        return this.especializacionRepository.findAll();
    }
    
    @Override
    @Transactional(readOnly = true)
    public Especializacion obtenerPorCodigo(String codigoEspecializacion) throws BadRequestException {
        Optional<Especializacion> especializacion = 
            this.especializacionRepository.findByCodigoEspecializacion(codigoEspecializacion);
        
        if (!especializacion.isPresent()) {
            throw new BadRequestException(
                "No se encontró la especialización con código: " + codigoEspecializacion);
        }
        return especializacion.get();
    }
    
    @Override
    @Transactional
    public Especializacion crear(Especializacion especializacion) throws BadRequestException {
        // Validar campos obligatorios
        if (especializacion.getCodigoEspecializacion() == null || 
            especializacion.getCodigoEspecializacion().trim().isEmpty()) {
            throw new BadRequestException("El código de la especialización es obligatorio");
        }
        
        if (especializacion.getNombre() == null || 
            especializacion.getNombre().trim().isEmpty()) {
            throw new BadRequestException("El nombre de la especialización es obligatorio");
        }
        
        // Validar longitud del código
        if (especializacion.getCodigoEspecializacion().length() > 10) {
            throw new BadRequestException(
                "El código de la especialización no puede exceder 10 caracteres");
        }
        
        // Validar longitud del nombre
        if (especializacion.getNombre().length() > 100) {
            throw new BadRequestException(
                "El nombre de la especialización no puede exceder 100 caracteres");
        }
        
        // Validar que no exista una especialización con el mismo código
        Optional<Especializacion> existeCodigo = 
            this.especializacionRepository.findByCodigoEspecializacion(
                especializacion.getCodigoEspecializacion());
        
        if (existeCodigo.isPresent()) {
            throw new BadRequestException(
                "Ya existe una especialización con el código: " + 
                especializacion.getCodigoEspecializacion());
        }
        
        // Validar que no exista una especialización con el mismo nombre
        Optional<Especializacion> existeNombre = 
            this.especializacionRepository.findByNombre(especializacion.getNombre());
        
        if (existeNombre.isPresent()) {
            throw new BadRequestException(
                "Ya existe una especialización con el nombre: " + 
                especializacion.getNombre());
        }
        
        return this.especializacionRepository.save(especializacion);
    }
    
    @Override
    @Transactional
    public Especializacion actualizar(Long id, Especializacion especializacion) 
        throws BadRequestException {
        
        // Validar que exista la especialización
        Optional<Especializacion> existe = this.especializacionRepository.findById(id);
        if (!existe.isPresent()) {
            throw new BadRequestException("No se encontró la especialización con ID: " + id);
        }
        
        Especializacion especializacionExistente = existe.get();
        
        // Validar campos obligatorios
        if (especializacion.getCodigoEspecializacion() == null || 
            especializacion.getCodigoEspecializacion().trim().isEmpty()) {
            throw new BadRequestException("El código de la especialización es obligatorio");
        }
        
        if (especializacion.getNombre() == null || 
            especializacion.getNombre().trim().isEmpty()) {
            throw new BadRequestException("El nombre de la especialización es obligatorio");
        }
        
        // Validar longitud del código
        if (especializacion.getCodigoEspecializacion().length() > 10) {
            throw new BadRequestException(
                "El código de la especialización no puede exceder 10 caracteres");
        }
        
        // Validar longitud del nombre
        if (especializacion.getNombre().length() > 100) {
            throw new BadRequestException(
                "El nombre de la especialización no puede exceder 100 caracteres");
        }
        
        // Validar que si se cambia el código, no exista otro con ese código
        if (!especializacionExistente.getCodigoEspecializacion()
                .equals(especializacion.getCodigoEspecializacion())) {
            
            Optional<Especializacion> codigoExiste = 
                this.especializacionRepository.findByCodigoEspecializacion(
                    especializacion.getCodigoEspecializacion());
            
            if (codigoExiste.isPresent()) {
                throw new BadRequestException(
                    "Ya existe una especialización con el código: " + 
                    especializacion.getCodigoEspecializacion());
            }
        }
        
        // Validar que si se cambia el nombre, no exista otro con ese nombre
        if (!especializacionExistente.getNombre().equals(especializacion.getNombre())) {
            Optional<Especializacion> nombreExiste = 
                this.especializacionRepository.findByNombre(especializacion.getNombre());
            
            if (nombreExiste.isPresent()) {
                throw new BadRequestException(
                    "Ya existe una especialización con el nombre: " + 
                    especializacion.getNombre());
            }
        }
        
        // Actualizar campos
        especializacionExistente.setCodigoEspecializacion(especializacion.getCodigoEspecializacion());
        especializacionExistente.setNombre(especializacion.getNombre());
        especializacionExistente.setDescripcion(especializacion.getDescripcion());
        
        return this.especializacionRepository.save(especializacionExistente);
    }
}
