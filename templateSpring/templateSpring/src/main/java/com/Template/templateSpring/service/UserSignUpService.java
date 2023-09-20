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

    private String responseCode;
    private String responseMessage;

    public UserSignUpService(UserRepository userRepository, PasswordEncoder passwordEncoder) {
        this.userRepository = userRepository;
        this.passwordEncoder = passwordEncoder;
    }

    @Override
    public void saveUser(UserSignUpDto userSignUpDto) {
        user = new User();
        emailValidator = new EmailValidator();
        passwordValidator = new PasswordValidator();

        try {
            if (userRepository.existsByEmail(userSignUpDto.getEmail())) {
                setResponseMessage("Email already exists");
                setResponseCode("R01");
            }

            if (!emailValidator.validateEmail(userSignUpDto) || emailValidator.emailNull(userSignUpDto)) {
                setResponseMessage("Your email is not valid");
                setResponseCode("R01");
            }

            if (!passwordValidator.validPassword(userSignUpDto) || !passwordValidator.matchingPassword(userSignUpDto)
                    || passwordValidator.nullPassword(userSignUpDto)) {
                setResponseMessage("Password not valid");
                setResponseCode("R01");
            };
            //Saving user to database
            user.setEmail(userSignUpDto.getEmail());
            user.setPassword(passwordEncoder.encode(userSignUpDto.getPassword()));
            userRepository.save(user);
            //Sending email to user
            SimpleMailMessage mailMessage = new SimpleMailMessage();
            mailMessage.setTo(user.getEmail());
            mailMessage.setSubject("Registration pending!");
            mailMessage.setText("Click on link to verify email address!"+"http://localhost:8080/calendar/verifyEmail");
            emailService.sendEmail(mailMessage);
            //Setting message and code
            setResponseMessage("Successfully registered!");
            setResponseCode("R00");

        } catch (Exception e) {
            System.out.println("An error occurred while processing your request: " + e.getMessage());
        }
    }

    public String getResponseMessage() {
        return responseMessage;
    }

    public void setResponseMessage(String responseMessage) {
        this.responseMessage = responseMessage;
    }

    public String getResponseCode() {
        return responseCode;
    }

    public void setResponseCode(String responseCode) {
        this.responseCode = responseCode;
    }


}
