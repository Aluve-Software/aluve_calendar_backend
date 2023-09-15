package com.Template.templateSpring.service;

import com.Template.templateSpring.dto.UserSignUpDto;
import org.springframework.http.ResponseEntity;

import java.util.List;

public interface UserService {
    ResponseEntity<?> saveUser(UserSignUpDto userSignUpDto);
    ResponseEntity<?> confirmEmail(String confirmationToken);
}
