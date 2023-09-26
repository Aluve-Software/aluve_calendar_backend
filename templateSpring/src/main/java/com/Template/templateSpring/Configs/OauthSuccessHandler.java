package com.Template.templateSpring.Configs;


import com.Template.templateSpring.Repos.UserRepo;
import com.Template.templateSpring.Service.OauthUserService;
import com.Template.templateSpring.dtos.UserRegiDTO;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.oauth2.core.user.DefaultOAuth2User;
import org.springframework.security.web.DefaultRedirectStrategy;
import org.springframework.security.web.authentication.AuthenticationSuccessHandler;
import org.springframework.stereotype.Component;

import java.io.IOException;

@Component
public class OauthSuccessHandler implements AuthenticationSuccessHandler {

    @Autowired
    UserRepo userRepo;

    @Autowired
    OauthUserService oauthUserService;

    @Override
    public void onAuthenticationSuccess(HttpServletRequest request, HttpServletResponse response,
                                        Authentication authentication) throws IOException, ServletException {

        String redirectUrl = null;
        if(authentication.getPrincipal() instanceof DefaultOAuth2User) {
            DefaultOAuth2User  userDetails = (DefaultOAuth2User ) authentication.getPrincipal();
            String username = userDetails.getAttribute("email") !=null?userDetails.getAttribute("email"):userDetails.getAttribute("login")+"@gmail.com" ;
            if(userRepo.findByEmail(username) == null) {
                UserRegiDTO user = new UserRegiDTO();
                user.setEmail_id(username);
                user.setName(userDetails.getAttribute("email") !=null?userDetails.getAttribute("email"):userDetails.getAttribute("login"));
                user.setPassword(("N/A"));
                user.setRole("USER");
                oauthUserService.save(user);
            }
        }  redirectUrl = "/dashboard";
        new DefaultRedirectStrategy().sendRedirect(request, response, redirectUrl);
    }




}
