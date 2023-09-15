package com.Template.templateSpring.service;

import com.Template.templateSpring.dto.UserSignUpDto;
import com.Template.templateSpring.entity.ConfirmationToken;
import com.Template.templateSpring.entity.User;
import com.Template.templateSpring.repository.ConfirmationTokenRepository;
import com.Template.templateSpring.repository.UserRepository;
import com.Template.templateSpring.validator.EmailValidator;
import com.Template.templateSpring.validator.PasswordValidator;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
//import org.springframework.security.crypto.password.PasswordEncoder;
import java.util.List;
import java.util.stream.Collectors;

import org.springframework.mail.SimpleMailMessage;
import org.springframework.stereotype.Service;

@Service("UserSignUpService")
public class UserSignUpService implements UserService {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    ConfirmationTokenRepository confirmationTokenRepository;

    @Autowired
    EmailService emailService;

    private EmailValidator emailValidator;

    private PasswordValidator passwordValidator;

    private User user;

//    private PasswordEncoder passwordEncoder;

    public UserSignUpService(UserRepository userRepository) {
        this.userRepository = userRepository;
//        this.passwordEncoder = passwordEncode;
    }

    @Override
    public ResponseEntity<?> saveUser(UserSignUpDto userSignUpDto) {
        user = new User();
        emailValidator = new EmailValidator();
        passwordValidator = new PasswordValidator();
        if(userRepository.existsByEmail(user.getEmail())){
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
        user.setPassword(userSignUpDto.getPassword());
        userRepository.save(user);

        ConfirmationToken confirmationToken = new ConfirmationToken(user);


        confirmationTokenRepository.save(confirmationToken);

        SimpleMailMessage mailMessage = new SimpleMailMessage();
        mailMessage.setTo(user.getEmail());
        mailMessage.setSubject("Complete Registration!");
        mailMessage.setText("To confirm your account, please click here : "
                +"http://localhost:8089/confirm-account?token="+confirmationToken.getConfirmationToken());
        emailService.sendEmail(mailMessage);

        System.out.println("Confirmation Token: " + confirmationToken.getConfirmationToken());

        return ResponseEntity.ok("Verify email by the link sent on your email address");

    };

    @Override
    public ResponseEntity<?> confirmEmail(String confirmationToken) {

        ConfirmationToken token = confirmationTokenRepository.findByConfirmationToken(confirmationToken);
        if(token != null) {
            User user = userRepository.findByEmail(token.getUserEntity().getEmail());
            user.setEnabled(true);
            userRepository.save(user);
            return new ResponseEntity<>("Email verified successfully!", HttpStatus.OK);
        }

        return new ResponseEntity<>("Error: Couldn't verify email", HttpStatus.BAD_REQUEST);
    }

    private UserSignUpDto mapToUserDto(User user){
        UserSignUpDto userDto = new UserSignUpDto();
        userDto.setEmail(user.getEmail());
        return userDto;
    }


}
