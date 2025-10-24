package com.uniminuto.clinica.repository;

import com.uniminuto.clinica.entity.Medicamento;
import org.springframework.data.jpa.repository.JpaRepository;

public interface MedicamentoRepository extends JpaRepository<Medicamento,Long> {
    //Consulta para manejar los duplicados
    boolean existsByNameIgnoreCaseAndPresentacionIgnoreCase(String name, String presentacion);

}


