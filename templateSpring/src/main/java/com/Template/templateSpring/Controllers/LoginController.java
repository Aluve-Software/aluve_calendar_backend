package com.Template.templateSpring.Controllers;

import com.Template.templateSpring.Repos.UserRepo;
import com.Template.templateSpring.Service.OauthUserService;
import com.Template.templateSpring.dtos.LoginDTO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

@Controller
@RequestMapping("/login")
public class LoginController {
    @Autowired
    private OauthUserService userService;

    @Autowired
    UserRepo userRepo;

    @ModelAttribute("user")
    public LoginDTO userLoginDTO() {
        return new LoginDTO();
    }

    @GetMapping
    public String login() {
        return "login";
    }

    @PostMapping
    public void loginUser(@ModelAttribute("user")
                          LoginDTO userLoginDTO) {
        System.out.println("UserDTO"+userLoginDTO);
        userService.loadUserByUsername(userLoginDTO.getUsername());
    }
}
