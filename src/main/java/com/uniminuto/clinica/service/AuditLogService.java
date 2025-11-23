package com.uniminuto.clinica.service;

public interface AuditLogService {
    void record(String evento, String usuarioIngresado, Long userId, String descripcion, String ip);
}