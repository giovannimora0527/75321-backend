package com.uniminuto.clinica.security;

import java.util.List;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.CorsConfigurationSource;
import org.springframework.web.cors.UrlBasedCorsConfigurationSource;

/**
 * Clase de configuracion para la seguridad.
 *
 * @author lmora
 */
@Configuration
@EnableWebSecurity
public class SecurityConfig {

   /**
     * Filtro de seguridad.
     *
     * @param http peticion de entrada.
     * @return Autorizado.
     * @throws Exception Excepcion.
     */
    @Bean
    public SecurityFilterChain securityFilterChain(final HttpSecurity http) throws Exception {
        http
                .cors() // Habilita CORS
                .and()
                .csrf().disable() // Deshabilita CSRF si estás probando con Postman
                .authorizeHttpRequests((requests) -> requests
                .antMatchers("/**").permitAll() // Permitir todas las rutas
                .anyRequest().authenticated()
                )
                .logout((logout) -> logout.permitAll());

        return http.build();
    }

    /**
     * Configuracion del cors.
     *
     * @return configuracion.
     */
    @Bean
    public CorsConfigurationSource corsConfigurationSource() {
        CorsConfiguration config = new CorsConfiguration();
        config.setAllowedOrigins(List.of(
                "http://localhost:4200",
                "http://localhost:8080",
                "http://127.0.0.1:8080",
                "http://127.0.0.1:4200",
                "http://10.0.5.50:8080",
                "http://10.0.5.50:4200"));

        config.setAllowedMethods(List.of("GET", "POST", "PUT", "DELETE", "OPTIONS"));
        config.setAllowedHeaders(List.of("*", "Authorization", "Content-Type"));
        config.setAllowCredentials(true);

        UrlBasedCorsConfigurationSource source = new UrlBasedCorsConfigurationSource();
        source.registerCorsConfiguration("/**", config);
        return source;
    }

}
