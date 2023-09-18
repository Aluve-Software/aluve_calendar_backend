package com.Template.templateSpring.service;

import com.Template.templateSpring.dto.UserSignUpDto;
import com.Template.templateSpring.entity.User;
import com.Template.templateSpring.repository.UserRepository;
import com.Template.templateSpring.validator.EmailValidator;
import com.Template.templateSpring.validator.PasswordValidator;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import org.springframework.mail.SimpleMailMessage;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.net.URI;

@Service("UserSignUpService")
public class UserSignUpService implements UserService {

    @Autowired
    private UserRepository userRepository;


    @Autowired
    EmailService emailService;

    private EmailValidator emailValidator;

    private PasswordValidator passwordValidator;

    private User user;

    private PasswordEncoder passwordEncoder;

    public UserSignUpService(UserRepository userRepository, PasswordEncoder passwordEncod) {
        this.userRepository = userRepository;
        this.passwordEncoder = passwordEncod;
    }

    @Override
    public ResponseEntity<?> saveUser(UserSignUpDto userSignUpDto) {
        user = new User();
        emailValidator = new EmailValidator();
        passwordValidator = new PasswordValidator();
        if(userRepository.existsByEmail(userSignUpDto.getEmail())){
            return new ResponseEntity<>("User already exists", HttpStatus.BAD_REQUEST);
        }

        if(!emailValidator.validateEmail(userSignUpDto)){
            return new ResponseEntity<>("Your Email is not valid", HttpStatus.BAD_REQUEST);
        }

        if(!passwordValidator.validPassword(userSignUpDto) || !passwordValidator.matchingPassword(userSignUpDto)){
            return new ResponseEntity<>("Password not valid", HttpStatus.BAD_REQUEST);
        }

        user.setEmail(userSignUpDto.getEmail());
        // encrypt the password using spring security
        user.setPassword(passwordEncoder.encode(userSignUpDto.getPassword()));
        userRepository.save(user);

        SimpleMailMessage mailMessage = new SimpleMailMessage();
        mailMessage.setTo(user.getEmail());
        mailMessage.setSubject("Complete Registration!");
        mailMessage.setText("Your Registration is complete!");
        emailService.sendEmail(mailMessage);

        return ResponseEntity.ok("Successfully signed In!");

    };



}
