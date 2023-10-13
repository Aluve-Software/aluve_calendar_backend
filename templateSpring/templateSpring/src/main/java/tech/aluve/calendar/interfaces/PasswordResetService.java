package tech.aluve.calendar.interfaces;

import jakarta.servlet.http.HttpServletRequest;

import javax.naming.AuthenticationException;

public interface PasswordResetService {
    void authenticateUser(String userEmail, HttpServletRequest servRequest);
    void validateToken(String userToken) throws AuthenticationException;
    void resetPassword(String newPass, String confirmPass);
}
