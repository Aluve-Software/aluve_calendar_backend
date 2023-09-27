package tech.aluve.calendar.interfaces;


import org.springframework.security.core.userdetails.UserDetailsService;
import tech.aluve.calendar.dto.UserSignUpDto;
import tech.aluve.calendar.entity.User;

public interface OauthUserService extends UserDetailsService {
    User save(UserSignUpDto userRegisteredDTO);
}
