package com.uniminuto.clinica.security;

import com.auth0.jwt.algorithms.Algorithm;
import com.uniminuto.clinica.entity.Usuario;
import com.auth0.jwt.JWT;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.JwtException;
import io.jsonwebtoken.security.Keys;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import com.auth0.jwt.interfaces.DecodedJWT;

import java.security.Key;
import java.util.Date;

@Component
public class JwtUtil {

    @Value("${jwt.secret}")
    private String secretKey;

    @Value("${jwt.expiration}")
    private long jwtExpiration;

    private Key getSigningKey() {
        return Keys.hmacShaKeyFor(secretKey.getBytes());
    }

    // Generar un token
    public String generateToken(Usuario usuario) {
        Algorithm algorithm = Algorithm.HMAC256(secretKey.getBytes());
        return JWT.create()
                .withSubject(usuario.getUsername())
                .withClaim("fecha_inicio_sesion", new Date())
                .withClaim("fecha_fin_sesion", new Date(System.currentTimeMillis() + jwtExpiration))
                .withClaim("correo", usuario.getEmail())
                //.withClaim("perfil_id", usuario.getEmail())
                .sign(algorithm);
    }

    // Obtener el username del token
    public String extractUsername(String token) {
        return Jwts.parserBuilder()
                .setSigningKey(getSigningKey())
                .build()
                .parseClaimsJws(token)
                .getBody()
                .getSubject();
    }

    // Obtener la fecha de expiración del token (usando el claim personalizado fecha_fin_sesion)
    public Date getExpirationDateFromToken(String token) {
        DecodedJWT jwt = JWT.decode(token);
        // El claim fecha_fin_sesion se almacena como Date
        return jwt.getClaim("fecha_fin_sesion").asDate();
    }

    // Validar si el token es correcto
    public boolean validateToken(String token) {
        try {
            Jwts.parserBuilder()
                    .setSigningKey(getSigningKey())
                    .build()
                    .parseClaimsJws(token);
            return true;
        } catch (JwtException | IllegalArgumentException e) {
            return false;
        }
    }
}
