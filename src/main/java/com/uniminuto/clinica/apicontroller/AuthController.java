package com.uniminuto.clinica.apicontroller;

import com.uniminuto.clinica.api.AuthApi;
import com.uniminuto.clinica.entity.Usuario;
import com.uniminuto.clinica.model.ForgotPasswordRq;
import com.uniminuto.clinica.model.ResetPasswordRq;
import com.uniminuto.clinica.model.RespuestaRs;
import com.uniminuto.clinica.model.UsuarioRq;
import com.uniminuto.clinica.service.UsuarioService;
import java.util.HashMap;
import java.util.Map;
import org.apache.coyote.BadRequestException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class AuthController implements AuthApi {

    @Autowired
    private UsuarioService usuarioService;

    private String convertirAHashString(String textoAConvertir) {
        String algoritmo = "MD5";
        try {
            java.security.MessageDigest md = java.security.MessageDigest.getInstance(algoritmo);
            byte[] hashBytes = md.digest(textoAConvertir.getBytes());
            StringBuilder sb = new StringBuilder();
            for (byte b : hashBytes) {
                sb.append(String.format("%02x", b));
            }
            return sb.toString();
        } catch (java.security.NoSuchAlgorithmException e) {
            throw new RuntimeException("Algoritmo no soportado: " + algoritmo, e);
        }
    }

    @Override
    public ResponseEntity<Map<String, Object>> login(UsuarioRq loginRq) {
        Map<String, Object> response = new HashMap<>();
        try {
            Usuario usuario = usuarioService.buscarUsuarioPorNombre(loginRq.getUsername().toLowerCase());
            String passwordHasheada = convertirAHashString(loginRq.getPassword());
            
            if (usuario.getPassword().equals(passwordHasheada) && usuario.isActivo()) {
                response.put("status", 200);
                response.put("mensaje", "Login exitoso");
                response.put("usuario", usuario.getUsername());
                response.put("rol", usuario.getRol());
                response.put("id", usuario.getId());
                return ResponseEntity.ok(response);
            } else {
                response.put("status", 401);
                response.put("error", "Usuario o contraseña incorrectos");
                return ResponseEntity.status(401).body(response);
            }
        } catch (BadRequestException e) {
            response.put("status", 401);
            response.put("error", "Usuario o contraseña incorrectos");
            return ResponseEntity.status(401).body(response);
        } catch (Exception e) {
            response.put("status", 500);
            response.put("error", "Error interno del servidor");
            return ResponseEntity.status(500).body(response);
        }
    }

    @Override
    public ResponseEntity<?> forgotPassword(ForgotPasswordRq request) {
        Map<String, Object> response = new HashMap<>();
        try {
            String token = usuarioService.generarTokenRecuperacion(request.getEmail());
            response.put("exitoso", true);
            response.put("mensaje", "Token generado exitosamente");
            response.put("token", token);
            return ResponseEntity.ok(response);
        } catch (BadRequestException e) {
            response.put("exitoso", false);
            response.put("mensaje", e.getMessage());
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(response);
        } catch (Exception e) {
            response.put("exitoso", false);
            response.put("mensaje", "Error interno del servidor");
            e.printStackTrace();
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(response);
        }
    }

    @Override
    public ResponseEntity<?> resetPassword(ResetPasswordRq request) {
        Map<String, Object> response = new HashMap<>();
        try {
            if (request.getNewPassword() == null || request.getConfirmPassword() == null) {
                response.put("exitoso", false);
                response.put("mensaje", "Las contraseñas no pueden estar vacías");
                return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(response);
            }
            if (!request.getNewPassword().equals(request.getConfirmPassword())) {
                response.put("exitoso", false);
                response.put("mensaje", "Las contraseñas no coinciden");
                return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(response);
            }
            if (request.getNewPassword().length() < 6) {
                response.put("exitoso", false);
                response.put("mensaje", "Mínimo 6 caracteres");
                return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(response);
            }

            RespuestaRs resultado = usuarioService.validarYResetearContrasena(
                    request.getToken(),
                    request.getNewPassword()
            );

            // NUEVO: control de null y log
            if (resultado == null) {
                response.put("exitoso", false);
                response.put("mensaje", "Error inesperado: respuesta del servicio nula");
                System.out.println("validarYResetearContrasena devolvió null");
                return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(response);
            }

            response.put("exitoso", true);
            response.put("mensaje", resultado.getMensaje() != null ? resultado.getMensaje() : "Contraseña actualizada exitosamente");
            System.out.println("Reset result: exitoso=true, mensaje=" + resultado.getMensaje());
            return ResponseEntity.ok(response);

        } catch (BadRequestException e) {
            response.put("exitoso", false);
            response.put("mensaje", e.getMessage());
            e.printStackTrace();
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(response);
        } catch (Exception e) {
            response.put("exitoso", false);
            response.put("mensaje", "Error interno del servidor");
            e.printStackTrace();
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(response);
        }
    }

}
