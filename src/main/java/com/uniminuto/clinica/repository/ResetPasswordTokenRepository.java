package com.uniminuto.clinica.repository;
import com.uniminuto.clinica.entity.Cita;
import com.uniminuto.clinica.entity.Medicamento;
import com.uniminuto.clinica.entity.Receta;
import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import com.uniminuto.clinica.entity.ResetPasswordToken;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;


public interface ResetPasswordTokenRepository extends JpaRepository<ResetPasswordToken, Long> {
    Optional<ResetPasswordToken> findByTokenAndUsadoFalse(String token);
    Optional<ResetPasswordToken> findByUserIdAndUsadoFalse(Long userId);
}
