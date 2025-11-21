package com.uniminuto.clinica.service.impl;

import com.uniminuto.clinica.entity.Auditoria;
import com.uniminuto.clinica.model.AuditoriaRs;
import com.uniminuto.clinica.repository.AuditoriaRepository;
import com.uniminuto.clinica.service.AuditoriaService;
import lombok.AllArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;

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

    @Override
    public List<AuditoriaRs> filtrar(String username, String tipo, String desde, String hasta) {

        //Encontrar los registros
        List<Auditoria> lista = auditoriaRepository.findAll();

        //convertimos las fechas
        LocalDateTime fechaDesde = null;
        LocalDateTime fechaHasta = null;

        try {
            if (desde != null && !desde.isEmpty()) {
                fechaDesde = LocalDateTime.parse(desde);
            }
            if (hasta != null && !hasta.isEmpty()) {
                fechaHasta = LocalDateTime.parse(hasta);
            }
        } catch (Exception e) {
            throw new RuntimeException("Formato de fecha incorrecto. Debe ser: 2025-11-20T10:00:00");
        }

        //  variables efectivamente finales para el stream
        final LocalDateTime fDesde = fechaDesde;
        final LocalDateTime fHasta = fechaHasta;

        //Filtramos la tabla
        List<Auditoria> filtrado = lista.stream()

                //Filtro por username
                .filter(a -> username == null || username.isEmpty() ||
                        a.getUsernameIngresado().equalsIgnoreCase(username))

                //Filtro por tipo (texto que aparece en descripcionError)
                .filter(a -> tipo == null || tipo.isEmpty() ||
                        a.getDescripcionError().toLowerCase().contains(tipo.toLowerCase()))

                //Filtro fecha desde
                .filter(a -> fDesde == null || !a.getFechaEvento().isBefore(fDesde))

                //Filtro fecha hasta
                .filter(a -> fHasta == null || !a.getFechaEvento().isAfter(fHasta))

                .toList();

        //Mapeamos la entidad → DTO
        return filtrado.stream()
                .map(a -> {
                    AuditoriaRs rs = new AuditoriaRs();
                    rs.setId(a.getSessionId());
                    rs.setUserId(a.getUserId());
                    rs.setUsernameIngresado(a.getUsernameIngresado());
                    rs.setDescripcionError(a.getDescripcionError());
                    rs.setFechaEvento(a.getFechaEvento());
                    return rs;
                })
                .toList();
    }


}
