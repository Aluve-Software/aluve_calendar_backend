package com.Template.templateSpring.controller;

import com.Template.templateSpring.dto.UserSignUpDto;
import com.Template.templateSpring.repository.UserRepository;
import com.Template.templateSpring.service.UserSignUpService;
import com.Template.templateSpring.validator.EmailValidator;
import com.Template.templateSpring.validator.PasswordValidator;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import com.Template.templateSpring.entity.User;
@RestController
@RequestMapping("/calendar")
public class SignUpController {

    @Autowired
    private UserSignUpService userSignUpService;

    @PostMapping( "/signUp")
    public ResponseEntity<?> signUpUSer(@RequestBody UserSignUpDto userSignUpDto){

        return userSignUpService.saveUser(userSignUpDto);
    }

    @RequestMapping(value="/confirm-account", method= {RequestMethod.GET, RequestMethod.POST})
    public ResponseEntity<?> confirmUserAccount(@RequestParam("token")String confirmationToken) {
        return userSignUpService.confirmEmail(confirmationToken);
    }
}
