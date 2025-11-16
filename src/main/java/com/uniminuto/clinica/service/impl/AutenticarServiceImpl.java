package com.uniminuto.clinica.service.impl;

import com.uniminuto.clinica.entity.Auditoria;
import com.uniminuto.clinica.entity.Usuario;
import com.uniminuto.clinica.model.AutenticatorRs;
import com.uniminuto.clinica.model.AuthenticatorRq;
import com.uniminuto.clinica.repository.AuditoriaRepository;
import com.uniminuto.clinica.repository.UsuarioRepository;
import com.uniminuto.clinica.security.JwtUtil;
import com.uniminuto.clinica.service.AutenticarService;
import com.uniminuto.clinica.service.CifrarService;
import lombok.AllArgsConstructor;
import org.apache.coyote.BadRequestException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.Date;
import java.util.Optional;

@Service
@AllArgsConstructor
public class AutenticarServiceImpl implements AutenticarService {

    private final UsuarioRepository usuarioRepository;

    private final PasswordEncoder passwordEncoder;

    //Inyectar el JwUtil
    private  final JwtUtil jwtUtil;

    private final CifrarService cifrarService;

    //inyectar auditoria Repository para el metodo privado
    private final AuditoriaRepository auditoriaRepository;

    @Override
    @Transactional
    public AutenticatorRs autenticar(AuthenticatorRq request) throws BadRequestException {
        Optional<Usuario> usuarioOpt = usuarioRepository.findByUsername(request.getUsername());
        if (usuarioOpt.isEmpty()) {
            throw new BadRequestException("Usuario o contraseña incorrectos");
        }
        Usuario usuario = usuarioOpt.get();
        boolean passwordOk;
        if (passwordEncoder != null) {
            passwordOk = passwordEncoder.matches(request.getPassword(), usuario.getPasswordHash());
        } else {
            passwordOk = usuario.getPasswordHash().equals(this.cifrarService.encriptarPassword(request.getPassword()));
        }
        if (!passwordOk) {
            throw new BadRequestException("Usuario o contraseña incorrectos");
        }
        // Generar y devolver un JWT
        AutenticatorRs rta = new AutenticatorRs();
        String token = jwtUtil.generateToken(usuario);
        rta.setToken(token);

        // Creamos la sesión del usuario autenticado
        crearSesionUsuario(usuario, token);
        return rta;
    }
    private void crearSesionUsuario(Usuario usuario, String token) {
        // Elimina cualquier sesión previa del usuario
        auditoriaRepository.deleteByUserId(usuario.getId().intValue());
        Auditoria auditoria = new Auditoria();
        auditoria.setUserId(usuario.getId().intValue());
        auditoria.setToken(token);
        auditoria.setFechaIniSesion(LocalDateTime.now());
        Date fechaExpiracion = jwtUtil.getExpirationDateFromToken(token);
        auditoria.setFechaExpiracion(fechaExpiracion.toInstant()
                .atZone(java.time.ZoneId.systemDefault())
                .toLocalDateTime());
        auditoriaRepository.save(auditoria);
    }
}
