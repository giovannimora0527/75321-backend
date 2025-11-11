package com.uniminuto.clinica.security;

import com.auth0.jwt.JWT;
import com.auth0.jwt.interfaces.DecodedJWT;
import io.jsonwebtoken.ExpiredJwtException;
import org.apache.coyote.BadRequestException;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.GenericFilterBean;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.logging.Level;
import java.util.logging.Logger;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import java.util.Collections;

@Component
public class JwtTokenFilter extends GenericFilterBean {

    /**
     * Log.
     */
    private static final Logger LOG = Logger.getLogger(JwtTokenFilter.class.getName());

    /**
     * Filtro JWT.
     * @param req Request.
     * @param res Response.
     * @param filterChain Filter chain.
     * @throws IOException IOException.
     * @throws ServletException ServletException.
     */
    @Override
    public void doFilter(ServletRequest req, ServletResponse res, FilterChain filterChain)
            throws IOException, ServletException {
        HttpServletRequest request = (HttpServletRequest) req;
        HttpServletResponse response = (HttpServletResponse) res;
        String authHeader = request.getHeader("Authorization");
        LOG.info(String.format("%s request to %s", request.getMethod(), request.getRequestURL().toString()));

        String requestURI = request.getRequestURI();
        if (requestURI != null && (requestURI.contains("/auth/recuperar-contrasena")
                || requestURI.contains("/auth/login"))) {
            try {
                filterChain.doFilter(req, res);
            } catch (BadRequestException e) {
                LOG.log(Level.WARNING, "Error en la petición: {0}", e.getMessage());
                response.setStatus(HttpServletResponse.SC_BAD_REQUEST);
                response.setContentType("application/json");
                response.setCharacterEncoding("UTF-8");
                response.getWriter().write("{\"error\": \"Bad Request\","
                        + " \"message\": \"" + e.getMessage() + "\"}");
                return;
            }
            LOG.info("Petición a /auth/login o /auth/recuperar-contrasena, se permite sin token.");
            return;
        } else if (authHeader != null && authHeader.startsWith("Bearer ")) {
            String token = authHeader.substring(7);
            try {
                if (isTokenExpired(token)) {
                    response.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
                    response.setContentType("application/json");
                    response.getWriter().write("{\"error\":\"Token expirado. Por favor, inicie sesión nuevamente.\"}");
                    response.flushBuffer();
                    return;
                }
                // Si el token es válido, establecer autenticación en el contexto de Spring
                DecodedJWT jwt = JWT.decode(token);
                String username = jwt.getSubject();
                if (username != null && SecurityContextHolder.getContext().getAuthentication() == null) {
                    UsernamePasswordAuthenticationToken authentication = new UsernamePasswordAuthenticationToken(
                        username, null, Collections.singletonList(new SimpleGrantedAuthority("USER"))
                    );
                    SecurityContextHolder.getContext().setAuthentication(authentication);
                }
            } catch (ExpiredJwtException eje) {
                response.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
                response.setContentType("application/json");
                response.getWriter().write("{\"error\":\"Token expirado. Por favor, inicie sesión nuevamente.\"}");
                response.flushBuffer();
                return;
            } catch (Exception e) {
                response.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
                response.setContentType("application/json");
                response.getWriter().write("{\"error\":\"Token inválido.\"}");
                response.flushBuffer();
                return;
            }
        }
        filterChain.doFilter(req, res);
    }

    /**
     * Valida si el token JWT está expirado.
     * @param token JWT.
     * @return true si está expirado, false si es válido.
     */
    private boolean isTokenExpired(String token) {
        DecodedJWT jwt = JWT.decode(token);
        // Extrae el claim personalizado "fecha_fin_sesion" como Date
        java.util.Date fechaFinSesion = jwt.getClaim("fecha_fin_sesion").asDate();
        if (fechaFinSesion == null) {
            return false; // Si no tiene expiración, se considera válido
        }
        return fechaFinSesion.before(new java.util.Date());
    }
}
