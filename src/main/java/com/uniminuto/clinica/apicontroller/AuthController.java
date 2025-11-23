package com.uniminuto.clinica.apicontroller;

import com.uniminuto.clinica.api.AuthApi;
import com.uniminuto.clinica.entity.Usuario;
import com.uniminuto.clinica.model.ForgotPasswordRq;
import com.uniminuto.clinica.model.ResetPasswordRq;
import com.uniminuto.clinica.model.RespuestaRs;
import com.uniminuto.clinica.model.UsuarioRq;
import com.uniminuto.clinica.service.UsuarioService;
import com.uniminuto.clinica.repository.UsuarioRepository;
import com.uniminuto.clinica.service.AuditoriaService;

import java.util.HashMap;
import java.util.Map;

import org.apache.coyote.BadRequestException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.mail.MessagingException;

/**
 * Controlador de autenticación con sistema de auditoría
 * Implementa AuthApi para mantener compatibilidad con rutas existentes
 */
@RestController
@RequestMapping("/auth")  // ⬅️ IMPORTANTE: Agregar esto para los endpoints nuevos
public class AuthController implements AuthApi {

    @Autowired
    private UsuarioService usuarioService;
    
    @Autowired
    private UsuarioRepository usuarioRepository;
    
    @Autowired
    private AuditoriaService auditoriaService;

    /**
     * Convierte texto a hash MD5 (método existente)
     */
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

    /**
     * LOGIN - Integrado con auditoría y bloqueo temporal
     * Implementa el método de AuthApi
     */
    @Override
    public ResponseEntity<Map<String, Object>> login(UsuarioRq loginRq) {
        Map<String, Object> response = new HashMap<>();
        String username = loginRq.getUsername().toLowerCase();
        
        System.out.println("🔑 Intento de login para usuario: " + username);
        
        try {
            // 1. VERIFICAR SI EL USUARIO ESTÁ BLOQUEADO
            if (auditoriaService.usuarioEstaBloqueado(username)) {
                long minutosRestantes = auditoriaService.obtenerTiempoBloqueoRestante(username);
                
                auditoriaService.registrarLoginFallido(username, 
                    "Intento de login en cuenta bloqueada (quedan " + minutosRestantes + " minutos)");
                
                System.out.println("🚫 Usuario bloqueado: " + username + " - " + minutosRestantes + " minutos restantes");
                
                response.put("status", 403);
                response.put("error", "Tu cuenta está bloqueada por seguridad. " +
                                     "Intenta nuevamente en " + minutosRestantes + " minutos.");
                response.put("blocked", true);
                response.put("minutosRestantes", minutosRestantes);
                
                return ResponseEntity.status(HttpStatus.FORBIDDEN).body(response);
            }
            
            // 2. BUSCAR USUARIO
            Usuario usuario;
            try {
                usuario = usuarioService.buscarUsuarioPorNombre(username);
            } catch (BadRequestException e) {
                // Usuario no existe
                auditoriaService.registrarLoginFallido(username, "Usuario no existe en el sistema");
                
                System.out.println("❌ Usuario no encontrado: " + username);
                
                response.put("status", 401);
                response.put("error", "Usuario o contraseña incorrectos");
                return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(response);
            }
            
            // 3. VALIDAR CONTRASEÑA (MD5)
            String passwordHasheada = convertirAHashString(loginRq.getPassword());
            boolean passwordCorrecta = false;
            boolean usandoPasswordTemporal = false;
            
            // Verificar contraseña normal
            if (usuario.getPassword().equals(passwordHasheada) && usuario.isActivo()) {
                passwordCorrecta = true;
                System.out.println("✅ Contraseña MD5 correcta");
            }
            // Si falla, verificar contraseña temporal
            else if (auditoriaService.validarPasswordTemporal(usuario, loginRq.getPassword())) {
                passwordCorrecta = true;
                usandoPasswordTemporal = true;
                System.out.println("🔑 Contraseña temporal válida");
            }
            
            // 4. PROCESAR RESULTADO
            if (passwordCorrecta) {
                // LOGIN EXITOSO - Reiniciar contador de intentos
                auditoriaService.reiniciarIntentosUsuario(username);
                
                System.out.println("✅ Login exitoso para: " + username);
                
                response.put("status", 200);
                response.put("mensaje", "Login exitoso");
                response.put("usuario", usuario.getUsername());
                response.put("rol", usuario.getRol());
                response.put("id", usuario.getId());
                response.put("requiereCambioPassword", usuario.getRequiereCambioPassword());
                response.put("usandoPasswordTemporal", usandoPasswordTemporal);
                
                return ResponseEntity.ok(response);
                
            } else {
                // LOGIN FALLIDO - Registrar en auditoría
                auditoriaService.registrarLoginFallido(username, "Contraseña incorrecta");
                
                // Verificar si ahora está bloqueado después de este intento
                if (auditoriaService.usuarioEstaBloqueado(username)) {
                    long minutosRestantes = auditoriaService.obtenerTiempoBloqueoRestante(username);
                    
                    System.out.println("🚫 Usuario bloqueado después de múltiples intentos: " + username);
                    
                    response.put("status", 403);
                    response.put("error", "Has superado el límite de intentos. " +
                                         "Tu cuenta está bloqueada por " + minutosRestantes + " minutos.");
                    response.put("blocked", true);
                    response.put("minutosRestantes", minutosRestantes);
                    
                    return ResponseEntity.status(HttpStatus.FORBIDDEN).body(response);
                } else {
                    // Obtener intentos restantes
                    Usuario usuarioActual = usuarioRepository.findByUsername(username).orElse(null);
                    int intentosRestantes = 3;
                    if (usuarioActual != null && usuarioActual.getIntentosFallidos() != null) {
                        intentosRestantes = 3 - usuarioActual.getIntentosFallidos();
                    }
                    
                    System.out.println("⚠️ Contraseña incorrecta. Intentos restantes: " + intentosRestantes);
                    
                    response.put("status", 401);
                    response.put("error", "Usuario o contraseña incorrectos");
                    response.put("intentosRestantes", intentosRestantes);
                    
                    return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(response);
                }
            }
            
        } catch (Exception e) {
            auditoriaService.registrarLoginFallido(username, 
                "Error en autenticación: " + e.getMessage());
            
            System.err.println("❌ Error en login: " + e.getMessage());
            e.printStackTrace();
            
            response.put("status", 500);
            response.put("error", "Error interno del servidor");
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(response);
        }
    }

    /**
     * FORGOT PASSWORD - Método existente mantenido
     * Implementa el método de AuthApi
     */
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

    /**
     * RESET PASSWORD - Método existente mantenido
     * Implementa el método de AuthApi
     */
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
    
    // ============================================
    // NUEVOS ENDPOINTS DE AUDITORÍA
    // ============================================
    
    /**
     * Solicitar contraseña temporal
     * Endpoint: POST /auth/solicitar-password-temporal
     */
    @PostMapping("/solicitar-password-temporal")
    public ResponseEntity<?> solicitarPasswordTemporal(@RequestBody Map<String, String> request) {
        Map<String, Object> response = new HashMap<>();
        try {
            String username = request.get("username");
            
            System.out.println("📧 Solicitud de password temporal para: " + username);
            
            if (username == null || username.trim().isEmpty()) {
                response.put("exitoso", false);
                response.put("mensaje", "El nombre de usuario es requerido");
                return ResponseEntity.badRequest().body(response);
            }
            
            Usuario usuario = usuarioRepository.findByUsername(username.toLowerCase()).orElse(null);
            
            if (usuario == null) {
                // Por seguridad, no revelamos si el usuario existe
                response.put("exitoso", true);
                response.put("mensaje", "Si el usuario existe, recibirás un correo con la contraseña temporal");
                return ResponseEntity.ok(response);
            }
            
            // Generar y enviar contraseña temporal
            auditoriaService.generarYEnviarPasswordTemporal(username.toLowerCase());
            
            response.put("exitoso", true);
            response.put("mensaje", "Se ha enviado una contraseña temporal a tu correo electrónico registrado");
            return ResponseEntity.ok(response);
            
        } catch (MessagingException e) {
            response.put("exitoso", false);
            response.put("mensaje", "Error al enviar el correo. Intenta nuevamente.");
            e.printStackTrace();
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(response);
        } catch (Exception e) {
            response.put("exitoso", false);
            response.put("mensaje", "Error al procesar la solicitud: " + e.getMessage());
            e.printStackTrace();
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(response);
        }
    }
    
    /**
     * Cambiar contraseña después de usar contraseña temporal
     * Endpoint: POST /auth/cambiar-password
     */
    @PostMapping("/cambiar-password")
    public ResponseEntity<?> cambiarPassword(@RequestBody CambiarPasswordRequest request) {
        Map<String, Object> response = new HashMap<>();
        try {
            String username = request.getUsername().toLowerCase();
            String nuevaPassword = request.getNuevaPassword();
            
            System.out.println("🔐 Cambio de contraseña para: " + username);
            
            Usuario usuario = usuarioRepository.findByUsername(username)
                .orElseThrow(() -> new RuntimeException("Usuario no encontrado"));
            
            // Convertir nueva contraseña a MD5
            String nuevaPasswordHasheada = convertirAHashString(nuevaPassword);
            
            // Actualizar contraseña
            usuario.setPassword(nuevaPasswordHasheada);
            
            // Limpiar contraseña temporal
            usuario.setPasswordTemporal(null);
            usuario.setFechaExpiracionTemporal(null);
            usuario.setRequiereCambioPassword(false);
            
            usuarioRepository.save(usuario);
            
            System.out.println("✅ Contraseña cambiada exitosamente para: " + username);
            
            response.put("exitoso", true);
            response.put("mensaje", "Contraseña actualizada exitosamente");
            return ResponseEntity.ok(response);
            
        } catch (Exception e) {
            response.put("exitoso", false);
            response.put("mensaje", "Error al cambiar la contraseña: " + e.getMessage());
            e.printStackTrace();
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(response);
        }
    }
    
    /**
     * Obtener información de bloqueo del usuario
     * Endpoint: GET /auth/info-bloqueo/{username}
     */
    @GetMapping("/info-bloqueo/{username}")
    public ResponseEntity<?> obtenerInfoBloqueo(@PathVariable String username) {
        Map<String, Object> response = new HashMap<>();
        try {
            Usuario usuario = usuarioRepository.findByUsername(username.toLowerCase()).orElse(null);
            
            if (usuario == null) {
                response.put("exitoso", false);
                response.put("mensaje", "Usuario no encontrado");
                return ResponseEntity.status(HttpStatus.NOT_FOUND).body(response);
            }
            
            boolean estaBloqueado = auditoriaService.usuarioEstaBloqueado(username.toLowerCase());
            long minutosRestantes = auditoriaService.obtenerTiempoBloqueoRestante(username.toLowerCase());
            
            response.put("exitoso", true);
            response.put("bloqueado", estaBloqueado);
            response.put("minutosRestantes", minutosRestantes);
            response.put("intentosFallidos", usuario.getIntentosFallidos());
            response.put("intentosRestantes", Math.max(0, 3 - (usuario.getIntentosFallidos() != null ? usuario.getIntentosFallidos() : 0)));
            
            return ResponseEntity.ok(response);
            
        } catch (Exception e) {
            response.put("exitoso", false);
            response.put("mensaje", "Error al obtener información: " + e.getMessage());
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(response);
        }
    }
    
    /**
     * Desbloquear manualmente un usuario (admin)
     * Endpoint: POST /auth/desbloquear/{username}
     */
    @PostMapping("/desbloquear/{username}")
    public ResponseEntity<?> desbloquearUsuario(@PathVariable String username) {
        Map<String, Object> response = new HashMap<>();
        try {
            System.out.println("🔓 Desbloqueando usuario: " + username);
            
            auditoriaService.desbloquearUsuario(username.toLowerCase());
            
            response.put("exitoso", true);
            response.put("mensaje", "Usuario desbloqueado exitosamente");
            return ResponseEntity.ok(response);
            
        } catch (Exception e) {
            response.put("exitoso", false);
            response.put("mensaje", "Error al desbloquear usuario: " + e.getMessage());
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(response);
        }
    }
    
    // ============================================
    // DTO
    // ============================================
    
    /**
     * DTO para el cambio de contraseña
     */
    public static class CambiarPasswordRequest {
        private String username;
        private String nuevaPassword;
        
        public String getUsername() { return username; }
        public void setUsername(String username) { this.username = username; }
        public String getNuevaPassword() { return nuevaPassword; }
        public void setNuevaPassword(String nuevaPassword) { this.nuevaPassword = nuevaPassword; }
    }
}
