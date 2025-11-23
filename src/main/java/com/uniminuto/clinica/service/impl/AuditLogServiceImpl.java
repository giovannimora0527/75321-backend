package com.uniminuto.clinica.service.impl;

import com.uniminuto.clinica.entity.AuditLog;
import com.uniminuto.clinica.repository.AuditLogRepository;
import com.uniminuto.clinica.service.AuditLogService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.servlet.http.HttpServletRequest;

import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

@Service
public class AuditLogServiceImpl implements AuditLogService {

    @Autowired
    private AuditLogRepository auditLogRepository;

    @Override
    public void record(String evento, String usuarioIngresado, Long userId, String descripcion, String ip) {
        if(ip == null || ip.isEmpty() || ip.equals("unknown")) {
            ip = getClientIp();
        }

        AuditLog log = new AuditLog();
        log.setEvento(evento);
        log.setUsuarioIngresado(usuarioIngresado);
        log.setUserId(userId);
        log.setDescripcion(descripcion);
        log.setIp(ip);
        auditLogRepository.save(log);
    }

    private String getClientIp() {
        ServletRequestAttributes attr = (ServletRequestAttributes) RequestContextHolder.getRequestAttributes();
        if (attr != null) {
            HttpServletRequest request = attr.getRequest();
            String ip = request.getHeader("X-Forwarded-For");
            if (ip == null || ip.isEmpty()) {
                ip = request.getRemoteAddr();
            }
            // Convertir IPv6 localhost a IPv4
            if ("0:0:0:0:0:0:0:1".equals(ip)) {
                ip = "127.0.0.1";
            }
            return ip;
        }
        return "unknown";
    }
}