package com.uniminuto.clinica.service.impl;

import com.uniminuto.clinica.service.CifrarService;
import org.springframework.stereotype.Service;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

@Service
public class CifrarServiceImpl implements CifrarService {
    @Override
    public String encriptarPassword(String passAEncriptar) {
        String algoritmo = "MD5";
        try {
            MessageDigest md = MessageDigest.getInstance(algoritmo); // Ej: "SHA-256", "MD5"
            byte[] hashBytes = md.digest(passAEncriptar.getBytes());

            // Convertir a hexadecimal
            StringBuilder sb = new StringBuilder();
            for (byte b : hashBytes) {
                sb.append(String.format("%02x", b));
            }
            return sb.toString();

        } catch (NoSuchAlgorithmException e) {
            throw new RuntimeException("Algoritmo no soportado: " + algoritmo, e);
        }
    }
}
