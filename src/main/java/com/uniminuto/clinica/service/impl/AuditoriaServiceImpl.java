package com.uniminuto.clinica.service.impl;

import com.uniminuto.clinica.entity.Auditoria;
import com.uniminuto.clinica.repository.AuditoriaRepository;
import com.uniminuto.clinica.service.AuditoriaService;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;

@Service
@AllArgsConstructor
public class AuditoriaServiceImpl implements AuditoriaService {

    private final AuditoriaRepository auditoriaRepository;

    @Override
    public void registrarError(String usernameIngresado, String descripcionError) {

        //Creamos el objeto auditoria
        Auditoria audit=new Auditoria();
        audit.setFechaEvento(LocalDateTime.now());
        audit.setUsernameIngresado(usernameIngresado);
        audit.setDescripcionError(descripcionError);

        //Campos que no aplican a la recuperacion
        audit.setUserId(null);
        audit.setToken(null);
        audit.setFechaIniSesion(null);
        audit.setFechaExpiracion(null);

        //Guardamos en la base de datos
        auditoriaRepository.save(audit);
    }

    @Override
    public void registrarRecuperacion(Integer userId, String username) {
        Auditoria audit=new Auditoria();
        audit.setFechaEvento(LocalDateTime.now());
        audit.setUserId(userId);
        audit.setUsernameIngresado(username);
        audit.setDescripcionError("Recuperacion de contraseña exitosa");

        //Guardamos en la base de datos
        auditoriaRepository.save(audit);
    }


}
