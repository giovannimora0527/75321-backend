package com.uniminuto.clinica.repository;

import com.uniminuto.clinica.entity.Paciente; //Importar Clase
import org.springframework.data.jpa.repository.EntityGraph;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;


//Poner decorador @Repository
@Repository
public interface PacienteRepository extends JpaRepository<Paciente,Long> {
    //Ya spring por default nos da en listar findall()

    //Traiga en este caso el paciente junto a su usuario en la misma consulta
    //ya tener cargado el usuario

    @EntityGraph(attributePaths = "usuario")
    Optional<Paciente> findByNumeroDocumento(String numeroDocumento);
    //Pasar service interfaz para la firma

    //ordenar por fecha de nacimiento(creamosMetodo)
    List<Paciente> findAllByOrderByFechaNacimientoAsc();
}
