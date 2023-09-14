package com.Template.templateSpring.controller;

import com.Template.templateSpring.dto.UserSignUpDto;
import com.Template.templateSpring.repository.UserRepository;
import com.Template.templateSpring.validator.EmailValidator;
import com.Template.templateSpring.validator.PasswordValidator;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import com.Template.templateSpring.entity.User;
@RestController
@RequestMapping("/api")
public class SignUpController {

    @Autowired
    private UserRepository userRepository;
    private EmailValidator emailValidator;

    private PasswordValidator passwordValidator;

    private User user;

    @PostMapping( "/signUp")
    public ResponseEntity<?> signUpUSer(@RequestBody UserSignUpDto userSignUpDto){
        user = new User();
        emailValidator = new EmailValidator();
        passwordValidator = new PasswordValidator();
        if(userRepository.existsByEmail(userSignUpDto.getEmail())){
            return new ResponseEntity<>("User already exists", HttpStatus.BAD_REQUEST);
        }

        if(!emailValidator.validateEmail(userSignUpDto)){
            return new ResponseEntity<>("Your Email is not valid", HttpStatus.BAD_REQUEST);
        }

        if(!passwordValidator.validPassword(userSignUpDto) || !passwordValidator.matchingPassword(userSignUpDto)){
            System.out.println(!passwordValidator.validPassword(userSignUpDto));
            System.out.println(!passwordValidator.matchingPassword(userSignUpDto));
            return new ResponseEntity<>("Password not valid", HttpStatus.BAD_REQUEST);
        }
        user.setEmail(userSignUpDto.getEmail());
        user.setPassword(userSignUpDto.getPassword());

        userRepository.save(user);

        return new ResponseEntity<>("User is registered successfully!", HttpStatus.OK);
    }
}
