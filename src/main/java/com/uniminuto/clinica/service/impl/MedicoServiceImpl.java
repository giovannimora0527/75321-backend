package com.uniminuto.clinica.service.impl;

import com.uniminuto.clinica.entity.Especializacion;
import com.uniminuto.clinica.entity.Medico;
import com.uniminuto.clinica.repository.EspecializacionRepository;
import com.uniminuto.clinica.repository.MedicoRepository;
import com.uniminuto.clinica.service.MedicoService;
import java.util.List;
import java.util.Optional;
import org.apache.coyote.BadRequestException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * Implementación del servicio para la lógica de negocio de médicos.
 * Maneja operaciones de consulta y filtrado de médicos por especialización.
 *
 * @author lmora
 */
@Service
public class MedicoServiceImpl implements MedicoService {

    @Autowired
    private MedicoRepository medicoRepository;

    @Autowired
    private EspecializacionRepository especializacionRepository;

    /**
     * Lista todos los médicos registrados en el sistema.
     * No aplica ningún filtro o criterio de ordenamiento.
     *
     * @return lista completa de médicos registrados, vacía si no hay médicos
     */
    @Override
    public List<Medico> listarTodo() {
        return this.medicoRepository.findAll();
    }

    /**
     * Obtiene médicos filtrados por código de especialización.
     * Primero valida que la especialización exista antes de buscar médicos.
     *
     * @param codigoEspec código de la especialización a buscar, no puede ser inválido
     * @return lista de médicos que tienen la especialización indicada, vacía si no hay coincidencias
     * @throws BadRequestException si el código de especialización no existe en el sistema
     */
    @Override
    public List<Medico> obtenerMedicosPorEspecializacion(String codigoEspec)
            throws BadRequestException {
        Optional<Especializacion> optEsp = this.especializacionRepository
                .findByCodigoEspecializacion(codigoEspec);
        if (!optEsp.isPresent()) {
            throw new BadRequestException("No existe la especializacion");
        }

        return this.medicoRepository.findByEspecializacion(optEsp.get());
    }
}