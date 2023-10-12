package tech.aluve.calendar.controller;

import org.springframework.web.bind.annotation.CrossOrigin;
import tech.aluve.calendar.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.oauth2.core.user.DefaultOAuth2User;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@CrossOrigin
@RequestMapping("/dashboard")
public class HomeController {
    @Autowired
    UserRepository userRepo;
    @GetMapping
    public String displayDashboard(Model model){
        SecurityContext securityContext = SecurityContextHolder.getContext();
        if(securityContext.getAuthentication().getPrincipal() instanceof DefaultOAuth2User) {
            DefaultOAuth2User user = (DefaultOAuth2User) securityContext.getAuthentication().getPrincipal();
            model.addAttribute("userDetails", user.getAttribute("name")!= null ?user.getAttribute("name"):user.getAttribute("login"));
        }else {
            User user = (User) securityContext.getAuthentication().getPrincipal();
            tech.aluve.calendar.entity.User users = userRepo.findByEmail(user.getUsername());
            model.addAttribute("userDetails", users.getName());
        }
        return "/dashboard";

    }
}
