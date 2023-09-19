package com.Template.templateSpring.controller;

import com.Template.templateSpring.dto.UserSignUpDto;
import com.Template.templateSpring.service.UserSignUpService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
@RestController
@RequestMapping("/calendar")
public class SignUpController {

    @Autowired
    private UserSignUpService userSignUpService;

    @PostMapping( "/signUp")
    public String signUpUSer(@RequestBody UserSignUpDto userSignUpDto){

        return userSignUpService.saveUser(userSignUpDto);
    }
}
