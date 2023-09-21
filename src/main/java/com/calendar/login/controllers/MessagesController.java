/**
 * returns dummy data
 * @author Michelle
 */
package com.calendar.login.controllers;


import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;


/*
  returns welcome message
 */
@RestController //returns json document
public class MessagesController {


    @GetMapping("/message") //name of endpoint
   public String welcomeMessage(){
       // String message ="Welcome";
        return "Welcome";
    }

}
