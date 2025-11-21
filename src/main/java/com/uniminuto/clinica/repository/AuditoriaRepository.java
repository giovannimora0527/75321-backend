package com.uniminuto.clinica.repository;

import com.uniminuto.clinica.entity.Auditoria;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.time.LocalDateTime;
import java.util.List;

public interface AuditoriaRepository extends JpaRepository<Auditoria,Integer> {
    //Querys
    void deleteByUserId(Integer userId);

    //Requerimiento 5
    //Traemostodo para luego aplicar filtros
    List<Auditoria>findAll();
}
