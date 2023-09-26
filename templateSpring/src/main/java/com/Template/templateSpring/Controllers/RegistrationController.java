package com.Template.templateSpring.Controllers;

import com.Template.templateSpring.Service.OauthUserService;
import com.Template.templateSpring.dtos.UserRegiDTO;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/registration")
public class RegistrationController {

    private OauthUserService userService;

    public RegistrationController(OauthUserService userService) {
        super();
        this.userService = userService;
    }

    @ModelAttribute("user")
    public UserRegiDTO userRegistrationDto() {
        return new UserRegiDTO();
    }

    @GetMapping
    public String showRegistrationForm() {
        return "register";
    }

    @PostMapping
    public String registerUserAccount(@ModelAttribute("user")
                                      UserRegiDTO registrationDto) {
        userService.save(registrationDto);
        return "redirect:/login";
    }
}
