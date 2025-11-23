package com.uniminuto.clinica.apicontroller;

import com.uniminuto.clinica.service.ResetPasswordService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;


import javax.servlet.http.HttpServletRequest;
import java.util.Map;

@RestController
@RequestMapping("/auth")
public class ResetPasswordController {

    @Autowired
    private ResetPasswordService resetPasswordService;

    @PostMapping("/request-password-reset")
    public ResponseEntity<?> requestReset(@RequestBody Map<String, String> body, HttpServletRequest req) {
        String username = body.get("username");
        String ip = req.getRemoteAddr();
        String token = resetPasswordService.createTokenForUsername(username, ip);
        // Respuesta genérica al cliente
        if (token != null) {
            // DEV: devolvemos token para pruebas. Quita esto en producción.
            return ResponseEntity.ok(Map.of("message", "Si existe una cuenta asociada, se enviaron instrucciones al correo registrado.", "token", token));
        }
        return ResponseEntity.ok(Map.of("message", "Si existe una cuenta asociada, se enviaron instrucciones al correo registrado."));
    }

    @PostMapping("/reset-password")
    public ResponseEntity<?> resetPassword(@RequestBody Map<String, String> body, HttpServletRequest req) {
        String token = body.get("token");
        String newPassword = body.get("newPassword");
        String ip = req.getRemoteAddr();
        boolean ok = resetPasswordService.resetPasswordByToken(token, newPassword, ip);
        if (!ok) {
            return ResponseEntity.badRequest().body(Map.of("message", "Token inválido o expirado"));
        }
        return ResponseEntity.ok(Map.of("message", "Contraseña actualizada"));
    }
}
