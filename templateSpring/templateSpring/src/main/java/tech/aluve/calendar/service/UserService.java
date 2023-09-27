package tech.aluve.calendar.service;

import tech.aluve.calendar.dto.UserSignUpDto;

public interface UserService {
    void saveUser(UserSignUpDto userSignUpDto);

}
