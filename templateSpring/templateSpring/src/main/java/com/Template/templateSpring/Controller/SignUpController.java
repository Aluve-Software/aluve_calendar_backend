package com.Template.templateSpring.Controller;

import com.Template.templateSpring.Dto.UserSignUpDto;
import com.Template.templateSpring.Repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import com.Template.templateSpring.Entity.User;
//@RequestMapping(path = "api", produces = MediaType.APPLICATION_JSON_VALUE)
// For simplicity of this sample, allow all origins. Real applications should configure CORS for their use case.

@RestController
@RequestMapping("/calendar")
public class SignUpController {

    @Autowired
    private UserRepository userRepository;

    @PostMapping( "/signup")
    public ResponseEntity<?> signUpUSer(@RequestBody UserSignUpDto userSignUpDto){
        if(userRepository.existsByEmail(userSignUpDto.getEmail())){
            return new ResponseEntity<>("User already exists", HttpStatus.BAD_REQUEST);
        }
        User user = new User();
        user.setEmail(userSignUpDto.getEmail());
        user.setPassword(userSignUpDto.getPassword());

        userRepository.save(user);

        return new ResponseEntity<>("User is registered successfully!", HttpStatus.OK);
    }
}
