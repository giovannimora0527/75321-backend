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
        //Buscar usuario por Username
        Optional<Usuario> usuarioOpt = usuarioRepository.findByUsername(request.getUsername());
        //Si el Usuario no existe mandamos auditoria y sak
        if (usuarioOpt.isEmpty()) {
            registrarIntentoFallidoSinUsuario(request.getUsername());
            throw new BadRequestException("Usuario o contraseña incorrectos");
        }
        Usuario usuario = usuarioOpt.get();
        //Validar si el usuario esta bloqueado
        if(usuario.getBloqueadoHasta() !=null &&usuario.getBloqueadoHasta().isAfter(LocalDateTime.now())){
            //Auitoria intento de login mientras esta bloqueado
            registrarIntentoFallido(usuario,"Intento de inicio de sesion mientras el usuario esta bloqueado");
            //Mensaje Generico
            throw  new BadRequestException("Usuario o contraseña incorrectos");
        }

        //Validamos Contraseña
        boolean passwordOk;
        if (passwordEncoder != null) {
            passwordOk = passwordEncoder.matches(request.getPassword(), usuario.getPasswordHash());
        } else {
            passwordOk = usuario.getPasswordHash().equals(this.cifrarService.encriptarPassword(request.getPassword()));
        }
        if (!passwordOk) {
            //Sumar intentos fallidos
            int intentos=usuario.getIntentosFallidos()+1;
            usuario.setIntentosFallidos(intentos);

            String descripcion="Intento fallido #"+intentos;
            //Si llega a 3 bloquea durante 5 min
            if(intentos>=3){
                usuario.setBloqueadoHasta(LocalDateTime.now().plusMinutes(5));
                usuario.setIntentosFallidos(0);
                descripcion="Usuario bloqueado por 3 intentos fallidos";
            }
            usuarioRepository.save(usuario);
            //Auditoria con su decripcion
            registrarIntentoFallido(usuario,descripcion);
            throw new BadRequestException("Usuario o contraseña incorrectos");
        }
        //Login exitoso
        usuario.setIntentosFallidos(0);
        usuario.setBloqueadoHasta(null);
        usuarioRepository.save(usuario);

        // Generar y devolver un JWT
        AutenticatorRs rta = new AutenticatorRs();
        String token = jwtUtil.generateToken(usuario);
        rta.setToken(token);

        // Creamos la sesión del usuario autenticado
        crearSesionUsuario(usuario, token);
        return rta;
    }
    //Metodos privados
    private void registrarIntentoFallidoSinUsuario(String username) {
        Auditoria audit = new Auditoria();
        audit.setFechaEvento(LocalDateTime.now());
        audit.setUsernameIngresado(username);
        audit.setDescripcionError("Intento de login fallido: usuario no existe");
        // Campos de sesión no aplican
        audit.setUserId(null);
        audit.setToken(null);
        audit.setFechaIniSesion(null);
        audit.setFechaExpiracion(null);

        auditoriaRepository.save(audit);
    }

    private void registrarIntentoFallido(Usuario usuario, String descripcion) {
        Auditoria audit = new Auditoria();
        audit.setFechaEvento(LocalDateTime.now());
        audit.setUserId(usuario.getId().intValue());
        audit.setUsernameIngresado(usuario.getUsername());
        audit.setDescripcionError(descripcion);
        // Para intentos fallidos no hay token ni sesión activa
        audit.setToken(null);
        audit.setFechaIniSesion(null);
        audit.setFechaExpiracion(null);

        auditoriaRepository.save(audit);
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
        //Campos nuevos agregados
        auditoria.setFechaEvento(LocalDateTime.now());
        auditoria.setUsernameIngresado(usuario.getUsername());
        auditoria.setDescripcionError("Inicio de sesion exitoso");

        auditoriaRepository.save(auditoria);
    }
}
