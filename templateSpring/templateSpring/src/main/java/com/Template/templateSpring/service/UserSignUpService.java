package com.Template.templateSpring.service;

import com.Template.templateSpring.dto.UserSignUpDto;
import com.Template.templateSpring.entity.User;
import com.Template.templateSpring.repository.UserRepository;
import com.Template.templateSpring.validator.EmailValidator;
import com.Template.templateSpring.validator.PasswordValidator;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

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

    public UserSignUpService(UserRepository userRepository, PasswordEncoder passwordEncoder) {
        this.userRepository = userRepository;
        this.passwordEncoder = passwordEncoder;
    }

    @Override
    public String saveUser(UserSignUpDto userSignUpDto) {
        user = new User();
        emailValidator = new EmailValidator();
        passwordValidator = new PasswordValidator();

        try {
            if (userRepository.existsByEmail(userSignUpDto.getEmail())) {
                return "User already exists";
            }

            if (!emailValidator.validateEmail(userSignUpDto) || emailValidator.emailNull(userSignUpDto)) {
                return "Your Email is not valid";
            }

            if (!passwordValidator.validPassword(userSignUpDto) || !passwordValidator.matchingPassword(userSignUpDto)
                    || passwordValidator.nullPassword(userSignUpDto)) {
                return "Password not valid";
            }

            user.setEmail(userSignUpDto.getEmail());
            user.setPassword(passwordEncoder.encode(userSignUpDto.getPassword()));
            userRepository.save(user);

            SimpleMailMessage mailMessage = new SimpleMailMessage();
            mailMessage.setTo(user.getEmail());
            mailMessage.setSubject("Complete Registration!");
            mailMessage.setText("Your Registration is complete!");
            emailService.sendEmail(mailMessage);

            return "Successfully signed In! Redirect to HomePage";
        } catch (Exception e) {
            return "An error occurred while processing your request: " + e.getMessage();
        }
    }

}
