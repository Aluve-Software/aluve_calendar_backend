/**
 * CustomPasswordEncoder class
 * @author Michelle
 */
package com.calendar.login.configuration;

import org.springframework.context.annotation.Bean;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;

@Component
public class CustomPasswordEncoder {

    @Bean
    public PasswordEncoder passwordEncoder() {

        return new BCryptPasswordEncoder(); //to securely verify user password
    }
}
