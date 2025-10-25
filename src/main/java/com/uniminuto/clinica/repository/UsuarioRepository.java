package com.uniminuto.clinica.repository;

import com.uniminuto.clinica.entity.Usuario;
import java.util.List;
import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

/**
 * Repositorio para operaciones de base de datos de la entidad Usuario.
 *
 * @author lmora
 */
@Repository
public interface UsuarioRepository extends JpaRepository<Usuario, Integer> {

    /**
     * Busca un usuario por nombre de usuario.
     *
     * @param nombre nombre de usuario
     * @return Optional con el usuario encontrado
     */
    Optional<Usuario> findByUsername(String nombre);

    /**
     * Busca usuarios por rol del sistema.
     *
     * @param rol rol del usuario
     * @return lista de usuarios con el rol especificado
     */
    List<Usuario> findByRol(String rol);

    /**
     * Busca un usuario por número de documento.
     *
     * @param numeroDocumento número de documento del usuario
     * @return Optional con el usuario encontrado
     */
    Optional<Usuario> findByNumeroDocumento(String numeroDocumento);
}