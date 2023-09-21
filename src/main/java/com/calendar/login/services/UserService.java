/**
 * UserService class to handle user authentication
 * @author Michelle
 * @version 1
 */
package com.calendar.login.services;

import com.calendar.login.dtos.LoginCredentialsDTO;
import com.calendar.login.dtos.UserDTO;
import com.calendar.login.entities.User;
import com.calendar.login.mappers.UserMapper;
import com.calendar.login.repositories.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.nio.CharBuffer;

@Service
@RequiredArgsConstructor
public class UserService {

    private final UserRepository userRepository;
    private final UserMapper userMapper;
    private final PasswordEncoder passwordEncoder;

    /**
     *try to locate the user using email
     */
    public UserDTO login(LoginCredentialsDTO credentialsDto){
        User user= userRepository.findByEmail(credentialsDto.email());
       if (user==null) {
            throw new RuntimeException("User not found");
        }

        //compare user password with the password stored in the database
            if (passwordEncoder.matches(CharBuffer.wrap(credentialsDto.password()),user.getPassword())){
                return userMapper.toUserDto(user);
            }
            else
                throw new RuntimeException("Incorrect password");
        }



}
