package com.uniminuto.clinica.repository;

import com.uniminuto.clinica.entity.Auditoria;
import org.springframework.data.jpa.repository.JpaRepository;

public interface AuditoriaRepository extends JpaRepository<Auditoria,Integer> {
    //Querys
    void deleteByUserId(Integer userId);
}
