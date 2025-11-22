package com.uniminuto.clinica.api;

import com.uniminuto.clinica.model.ForgotPasswordRq;
import com.uniminuto.clinica.model.ResetPasswordRq;
import com.uniminuto.clinica.model.UsuarioRq;
import java.util.Map;
import org.apache.coyote.BadRequestException;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@CrossOrigin(origins = "http://localhost:4200")
@RequestMapping("/auth")
public interface AuthApi {

    @PostMapping(value = "/login", 
            produces = "application/json", 
            consumes = "application/json")
            ResponseEntity<Map<String, Object>> login(@RequestBody UsuarioRq loginRq);

    @PostMapping(value = "/forgot-password", 
            produces = "application/json", 
            consumes = "application/json")
            ResponseEntity<?> forgotPassword(@RequestBody ForgotPasswordRq request);

    @PostMapping(value = "/reset-password", 
            produces = "application/json", 
            consumes = "application/json")
            ResponseEntity<?> resetPassword(@RequestBody ResetPasswordRq request);
}
