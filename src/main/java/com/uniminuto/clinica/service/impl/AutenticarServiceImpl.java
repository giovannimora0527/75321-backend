package com.uniminuto.clinica.service.impl;

import com.uniminuto.clinica.entity.Usuario;
import com.uniminuto.clinica.model.AutenticatorRs;
import com.uniminuto.clinica.model.AuthenticatorRq;
import com.uniminuto.clinica.repository.UsuarioRepository;
import com.uniminuto.clinica.service.AutenticarService;
import com.uniminuto.clinica.service.CifrarService;
import org.apache.coyote.BadRequestException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.Date;
import java.util.Optional;

import com.uniminuto.clinica.entity.Session;
import com.uniminuto.clinica.repository.SessionRepository;
import com.uniminuto.clinica.security.JwtUtil;

import javax.transaction.Transactional;

@Service
public class AutenticarServiceImpl implements AutenticarService {

    @Autowired
    private UsuarioRepository usuarioRepository;

    @Autowired(required = false)
    private PasswordEncoder passwordEncoder;

    @Autowired
    private JwtUtil jwtUtil;

    @Autowired
    private CifrarService cifrarService;

    @Autowired
    private SessionRepository sessionRepository;

    @Override
    @Transactional
    public AutenticatorRs autenticar(AuthenticatorRq request)
            throws BadRequestException {

        Optional<Usuario> usuarioOpt = usuarioRepository.findByUsername(request.getUsername());
        if (usuarioOpt.isEmpty()) {
            throw new BadRequestException("Usuario o contraseña incorrectos");
        }
        Usuario usuario = usuarioOpt.get();
        boolean passwordOk;
        if (passwordEncoder != null) {
            passwordOk = passwordEncoder.matches(request.getPassword(), usuario.getPassword());
        } else {
            passwordOk = usuario.getPassword().equals(this.cifrarService.encriptarPassword(request.getPassword()));
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

    /**
     * Crea y almacena la sesión del usuario autenticado.
     *
     * @param usuario Usuario autenticado
     * @param token   Token JWT generado
     */
    private void crearSesionUsuario(Usuario usuario, String token) {
        // Elimina cualquier sesión previa del usuario
        sessionRepository.deleteByUserId(usuario.getId().intValue());
        Session session = new Session();
        session.setUserId(usuario.getId().intValue());
        session.setToken(token);
        session.setFechaIniSesion(LocalDateTime.now());
        Date fechaExpiracion = jwtUtil.getExpirationDateFromToken(token);
        session.setFechaExpiracion(fechaExpiracion.toInstant()
                .atZone(java.time.ZoneId.systemDefault())
                .toLocalDateTime());
        sessionRepository.save(session);
    }


}
