package tech.aluve.calendar.interfaces;

import tech.aluve.calendar.dto.UserSignUpDto;

public interface UserService {
    void saveUser(UserSignUpDto userSignUpDto);
}
