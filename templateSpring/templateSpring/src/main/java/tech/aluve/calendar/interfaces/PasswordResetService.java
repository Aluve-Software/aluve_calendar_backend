package tech.aluve.calendar.interfaces;

import tech.aluve.calendar.dto.UserSignUpDto;

public interface PasswordResetService {
    void authenticateUser(UserSignUpDto userPasswordResetDTO);
}
