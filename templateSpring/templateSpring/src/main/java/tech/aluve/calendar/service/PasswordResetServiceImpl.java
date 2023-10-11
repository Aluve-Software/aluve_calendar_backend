package tech.aluve.calendar.service;

import io.jsonwebtoken.Claims;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import tech.aluve.calendar.dto.UserSignUpDto;
import tech.aluve.calendar.entity.User;
import tech.aluve.calendar.interfaces.PasswordResetService;
import tech.aluve.calendar.repository.UserRepository;
import tech.aluve.calendar.security.JwtToken;
import tech.aluve.calendar.validator.PasswordValidator;

@Service("PasswordResetServiceImpl")
public class PasswordResetServiceImpl implements PasswordResetService {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    EmailService emailService;

    private PasswordValidator passwordValidator;

    private PasswordEncoder passwordEncoder;

    private User user;

    private JwtToken token;

    private String passwordResponseCode;
    private String passwordResponseMessage;

    public PasswordResetServiceImpl (PasswordEncoder encodepassword, UserRepository userRepo, JwtToken jwtToken){
        this.passwordEncoder = encodepassword;
        this.userRepository = userRepo;
        this.token = jwtToken;
    }

    @Override
    public void authenticateUser(UserSignUpDto userPasswordReset) {
        user = new User();

        passwordValidator = new PasswordValidator();
        try {
            if(userRepository.existsByEmail(userPasswordReset.getEmail())){
                user.setEmail(userPasswordReset.getEmail());
                SimpleMailMessage simpleMailMessage = new SimpleMailMessage();
                simpleMailMessage.setTo(user.getEmail());
                simpleMailMessage.setSubject("Pending password reset");
                simpleMailMessage.setText("Click on link to reset your password!"+"http://localhost:8080/passwordreset/newpassword?token="+token.createToken(user));
                emailService.sendEmail(simpleMailMessage);
                //Setting message and code
                setPasswordResponseMessage("Successfully Authenticated!");
                setPasswordResponseCode("R00");
            }else{
                setPasswordResponseMessage("Please register first!");
                setPasswordResponseCode("R01");
            }
        }catch (Exception e) {
            System.out.println("An error occurred while processing your request: " + e.getMessage());
        }

    }

    @Override
    public void resetPassword(String userToken, UserSignUpDto newUserPassword){
        try {
            if(token.validateClaims(userToken)){
                setPasswordResponseMessage("Token has Expired");
                setPasswordResponseCode("R01");
            }else{
                User user = userRepository.findByResetToken(userToken);
                if (!passwordValidator.validPassword(newUserPassword) || !passwordValidator.matchingPassword(newUserPassword)
                        || passwordValidator.nullPassword(newUserPassword)) {
                    setPasswordResponseMessage("Password not valid");
                    setPasswordResponseCode("R01");
                }else {
                    user.setPassword(passwordEncoder.encode(newUserPassword.getPassword()));
                    userRepository.save(user);
                    setPasswordResponseMessage("Password successfully updated");
                    setPasswordResponseCode("R00");

                }
            }
        }catch (Exception e){
            System.out.println("An error occurred while processing your request: " + e.getMessage());
        }

    }

    public String getPasswordResponseCode() {
        return passwordResponseCode;
    }

    public void setPasswordResponseCode(String passwordResponseCode) {
        this.passwordResponseCode = passwordResponseCode;
    }

    public String getPasswordResponseMessage() {
        return passwordResponseMessage;
    }

    public void setPasswordResponseMessage(String passwordResponseMessage) {
        this.passwordResponseMessage = passwordResponseMessage;
    }


}
