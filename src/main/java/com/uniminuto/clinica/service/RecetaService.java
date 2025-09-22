package com.uniminuto.clinica.service;

import com.uniminuto.clinica.entity.Receta;
import java.util.List;

/**
 * Interfaz para definir las operaciones de Receta.
 */
public interface RecetaService {

    /**
     * Crear una receta nueva.
     * 
     * @param receta entidad receta a guardar
     * @return la receta guardada
     */
    Receta crearReceta(Receta receta);

    /**
     * Listar todas las recetas.
     * 
     * @return lista de recetas existentes
     */
    List<Receta> listarRecetas();
}
