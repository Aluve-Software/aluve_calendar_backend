package com.Template.templateSpring.controller;

import com.Template.templateSpring.dto.UserSignUpDto;
import com.Template.templateSpring.service.ResponseMessage;
import com.Template.templateSpring.service.UserSignUpService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
@RestController
@RequestMapping("/calendar")
public class SignUpController {

    @Autowired
    private UserSignUpService userSignUpService;
    private ResponseMessage responseMessage;

    @PostMapping( "/signUp")
    public ResponseMessage signUpUSer(@RequestBody UserSignUpDto userSignUpDto){
        userSignUpService.saveUser(userSignUpDto);
        responseMessage = new ResponseMessage(userSignUpService.getResponseCode(), userSignUpService.getResponseMessage());
        return responseMessage;
    }
}
