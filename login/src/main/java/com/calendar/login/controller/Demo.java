package com.calendar.login.controller;


import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/auth/demo")
@RequiredArgsConstructor
public class Demo {
    @GetMapping
    public ResponseEntity<String> sayHello(){
        return ResponseEntity.ok("End point secure");
    }

}
