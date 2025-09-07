package com.uniminuto.clinica.repository;

import com.uniminuto.clinica.entity.Paciente; //Importar Clase
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;


//Poner decorador @Repository
@Repository
public interface PacienteRepository extends JpaRepository<Paciente,Long> {
    //Ya spring por default nos da en listar findall()
}
