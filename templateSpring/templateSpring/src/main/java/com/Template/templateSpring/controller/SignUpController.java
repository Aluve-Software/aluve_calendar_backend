package com.Template.templateSpring.controller;

import com.Template.templateSpring.dto.UserSignUpDto;
import com.Template.templateSpring.service.ResponseMessage;
import com.Template.templateSpring.service.UserSignUpService;
import jakarta.persistence.Entity;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.config.annotation.ViewControllerRegistry;

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

    @GetMapping("/home")
    public ModelAndView homepage(){
        ModelAndView view = new ModelAndView();
        view.setViewName("welcome");
        return view;
    }
}
