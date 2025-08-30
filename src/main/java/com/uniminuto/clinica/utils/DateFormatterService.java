package com.uniminuto.clinica.utils;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.time.format.DateTimeFormatter;
import java.util.Date;
import java.util.Locale;
import org.springframework.stereotype.Service;

/**
 *
 * @author lmora
 */
@Service
public class DateFormatterService {

    /**
     * Convierte un objeto Date a LocalDateTime con zona horaria del sistema.
     *
     * @param date Fecha a convertir.
     * @return LocalDateTime correspondiente.
     */
    public LocalDateTime convertDateToLocalDateTime(Date date) {
        if (date == null) {
            return null;
        }
        return date.toInstant().atZone(ZoneId.systemDefault()).toLocalDateTime();
    }

    /**
     * Parsea un string a LocalDateTime usando un formato personalizado.
     *
     * @param dateString Cadena de fecha.
     * @param pattern Patrón de formato (ej: "dd/MM/yyyy HH:mm:ss").
     * @return LocalDateTime parseado.
     */
    public LocalDateTime parseStringToLocalDateTime(String dateString, String pattern) {
        if (dateString == null || pattern == null) {
            return null;
        }
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern(pattern);
        return LocalDateTime.parse(dateString, formatter);
    }

    /**
     * Formatea un LocalDateTime a String usando un patrón dado.
     *
     * @param dateTime LocalDateTime a formatear.
     * @param pattern Patrón de formato (ej: "yyyy-MM-dd HH:mm:ss").
     * @return String formateado.
     */
    public String formatLocalDateTime(LocalDateTime dateTime, String pattern) {
        if (dateTime == null || pattern == null) {
            return null;
        }
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern(pattern);
        return dateTime.format(formatter);
    }

    /**
     * Parsea un string a LocalDateTime usando un formato personalizado.
     *
     * @param dateString Cadena de fecha.
     * @param pattern Patrón de formato (ej: "dd/MM/yyyy").
     * @return LocalDateTime parseado.
     */
    public LocalDate parseStringToLocalDate(String dateString, String pattern) {
        if (dateString == null || pattern == null) {
            return null;
        }
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern(pattern, Locale.forLanguageTag("es"));
        return LocalDate.parse(dateString, formatter);
    }
}
