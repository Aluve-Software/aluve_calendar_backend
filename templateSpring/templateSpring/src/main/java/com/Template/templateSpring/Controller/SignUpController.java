package com.Template.templateSpring.Controller;

import com.Template.templateSpring.Repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/calendar")
public class SignUpController {

    @Autowired
    private UserRepository userRepository;

//    @PostMapping("/signup")
}
