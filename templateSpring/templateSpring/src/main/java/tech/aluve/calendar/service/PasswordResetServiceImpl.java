package tech.aluve.calendar.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.stereotype.Service;
import tech.aluve.calendar.dto.UserSignUpDto;
import tech.aluve.calendar.entity.User;
import tech.aluve.calendar.interfaces.PasswordResetService;
import tech.aluve.calendar.repository.UserRepository;
import tech.aluve.calendar.validator.PasswordValidator;

@Service("PasswordResetServiceImpl")
public class PasswordResetServiceImpl implements PasswordResetService {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    EmailService emailService;

    private PasswordValidator passwordValidator;

    private User user;

    private String passwordResponseCode;
    private String passwordResponseMessage;

    public PasswordResetServiceImpl (){

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
                simpleMailMessage.setText("Click on link to reset your password!"+"http://localhost:8080/passwordreset/newpassword");
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
