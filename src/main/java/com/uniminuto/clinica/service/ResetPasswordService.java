package com.uniminuto.clinica.service;

public interface ResetPasswordService {
    String createTokenForUsername(String username, String clientIp);
    boolean resetPasswordByToken(String token, String newPassword, String clientIp);
}
