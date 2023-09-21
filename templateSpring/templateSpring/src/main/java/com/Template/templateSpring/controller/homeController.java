package com.Template.templateSpring.controller;


import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class homeController {
    @GetMapping ("/home/v1")
    public String home(){
        return "Welcome Home";
    }

}
