/**
 * AuthController class to handle user login
 * @author Michelle
 * @version 1
 */
package com.calendar.login.controllers;

import com.calendar.login.dtos.LoginCredentialsDTO;
import com.calendar.login.dtos.UserDTO;
import com.calendar.login.services.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
public class AuthController {

    private final UserService userService;

    /*
      returns dto and receives dto
     */
    @GetMapping("/login") //login end point
    public ResponseEntity<UserDTO> login(@RequestBody LoginCredentialsDTO loginCredentialsDTO){
        //call service(login method)
        UserDTO userDTO = userService.login(loginCredentialsDTO);
        return ResponseEntity.ok(userDTO); //return user dto
    }
}
