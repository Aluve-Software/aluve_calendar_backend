package com.Template.templateSpring.Service;

import com.Template.templateSpring.dtos.UserRegiDTO;
import com.Template.templateSpring.models.User;
import org.springframework.security.core.userdetails.UserDetailsService;

public interface OauthUserService extends UserDetailsService {
    User save(UserRegiDTO userRegisteredDTO);
}
