package com.uniminuto.clinica.apicontroller;

import com.uniminuto.clinica.entity.AuditLog;
import com.uniminuto.clinica.repository.AuditLogRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.*;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.web.bind.annotation.*;

import java.time.OffsetDateTime;
import java.util.List;

@RestController
@RequestMapping("/audit")
public class AuditLogController {

    @Autowired
    private AuditLogRepository auditLogRepository;

    @GetMapping("/logs")
    public Page<AuditLog> getLogs(
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "20") int size,
            @RequestParam(required = false) String username,
            @RequestParam(required = false) String event,
            @RequestParam(required = false) @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME) OffsetDateTime from,
            @RequestParam(required = false) @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME) OffsetDateTime to
    ) {
        Pageable pageable = PageRequest.of(page, size, Sort.by("fecha").descending());

        Specification<AuditLog> spec = (root, query, cb) -> cb.conjunction();
        if (username != null && !username.isEmpty()) {
            Specification<AuditLog> s = (root, query, cb) -> cb.like(cb.lower(root.get("usuarioIngresado")), "%" + username.toLowerCase() + "%");
            spec = spec.and(s);
        }
        if (event != null && !event.isEmpty()) {
            Specification<AuditLog> s = (root, query, cb) -> cb.equal(root.get("evento"), event);
            spec = spec.and(s);
        }
        if (from != null) {
            Specification<AuditLog> s = (root, query, cb) -> cb.greaterThanOrEqualTo(root.get("fecha"), from);
            spec = spec.and(s);
        }
        if (to != null) {
            Specification<AuditLog> s = (root, query, cb) -> cb.lessThanOrEqualTo(root.get("fecha"), to);
            spec = spec.and(s);
        }

        return auditLogRepository.findAll(spec, pageable);
    }
}
