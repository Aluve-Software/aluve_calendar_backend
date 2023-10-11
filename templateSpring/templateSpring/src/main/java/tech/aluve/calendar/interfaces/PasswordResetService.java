package tech.aluve.calendar.interfaces;

import tech.aluve.calendar.dto.UserSignUpDto;
import tech.aluve.calendar.security.JwtToken;

public interface PasswordResetService {
    void authenticateUser(UserSignUpDto userPasswordResetDTO);
    void resetPassword(String userToken, UserSignUpDto newUserPassword);
}
