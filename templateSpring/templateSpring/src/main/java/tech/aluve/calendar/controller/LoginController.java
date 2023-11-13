package tech.aluve.calendar.controller;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import tech.aluve.calendar.dto.LoginDTO;
import tech.aluve.calendar.interfaces.OauthUserService;
import tech.aluve.calendar.repository.UserRepository;

@CrossOrigin(origins = "*")
@Controller
@RequestMapping("/login")
public class LoginController {
    @Autowired
    private OauthUserService userService;

    @Autowired
    UserRepository userRepo;

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
