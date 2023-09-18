package com.Template.templateSpring.controller;

import com.Template.templateSpring.dto.UserSignUpDto;
import com.Template.templateSpring.service.UserSignUpService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
@RestController
@RequestMapping("/calendar")
public class SignUpController {

    @Autowired
    private UserSignUpService userSignUpService;

    @PostMapping( "/signUp")
    public ResponseEntity<?> signUpUSer(@RequestBody UserSignUpDto userSignUpDto){

        return userSignUpService.saveUser(userSignUpDto);
    }

    @RequestMapping(value="/home")
    public String homePage(@RequestBody UserSignUpDto userSignUpDto) {
        return "Well come "+userSignUpDto.getEmail();
    }
}
