package tech.aluve.calendar.interfaces;

import tech.aluve.calendar.dto.UserSignUpDto;
import tech.aluve.calendar.security.JwtToken;

import javax.naming.AuthenticationException;

public interface PasswordResetService {
    void authenticateUser(UserSignUpDto userPasswordResetDTO);
    void validateToken(String userToken) throws AuthenticationException;
    void resetPassword(UserSignUpDto newUserPassword);
}
