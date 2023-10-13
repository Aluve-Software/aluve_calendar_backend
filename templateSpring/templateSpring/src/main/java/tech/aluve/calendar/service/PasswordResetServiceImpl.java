package tech.aluve.calendar.service;

import jakarta.servlet.http.HttpServletRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import tech.aluve.calendar.entity.User;
import tech.aluve.calendar.interfaces.PasswordResetService;
import tech.aluve.calendar.repository.UserRepository;
import tech.aluve.calendar.security.JwtToken;
import tech.aluve.calendar.validator.PasswordValidator;
import javax.naming.AuthenticationException;

@Service("PasswordResetServiceImpl")
public class PasswordResetServiceImpl implements PasswordResetService {

    @Autowired
    private UserRepository userRepository;
    @Autowired
    EmailService emailService;
    private PasswordValidator passwordValidator;
    private PasswordEncoder passwordEncoder;
    private JwtToken token;
    private String userEmail;
    private String passwordResponseCode;
    private String passwordResponseMessage;

    public PasswordResetServiceImpl (PasswordEncoder encodepassword, UserRepository userRepo, JwtToken jwtToken){
        this.passwordEncoder = encodepassword;
        this.userRepository = userRepo;
        this.token = jwtToken;
    }

    @Override
    public void authenticateUser(String userEmail, HttpServletRequest servRequest) {
        String schemeName = servRequest.getScheme();
        String serverName = servRequest.getServerName();
        passwordValidator = new PasswordValidator();
        try {
            if(userRepository.existsByEmail(userEmail)){
                User user = userRepository.findByEmail(userEmail);
                String newToken = token.createToken(user);
                setUserEmail(userEmail);
                user.setEmail(userEmail);
                SimpleMailMessage simpleMailMessage = new SimpleMailMessage();
                simpleMailMessage.setTo(user.getEmail());
                simpleMailMessage.setSubject("Pending password reset");
                simpleMailMessage.setText("Click on link to reset your password!"+schemeName+"://"+serverName+"/passwordreset/verifytoken?token="+newToken);
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
    public void validateToken(String userToken) throws AuthenticationException {
        if(!token.validateClaims(userToken)){
            setPasswordResponseMessage("Token has Expired");
            setPasswordResponseCode("R01");
        }else {
            setPasswordResponseMessage("Token Valid and Redirected to password reset page");
            setPasswordResponseCode("R00");
        }

    }

    @Override
    public void resetPassword(String newPass, String confirmPass){
        try {
            passwordValidator = new PasswordValidator();
            User user = userRepository.findByEmail(getUserEmail());
            System.out.println(getUserEmail());
            if (!passwordValidator.passwordValid(newPass) || !passwordValidator.Passwordsmatch(newPass, confirmPass, user)
                    || passwordValidator.passwordNull(newPass, confirmPass)) {
                setPasswordResponseMessage("Password not valid");
                setPasswordResponseCode("R01");
            }else {
                user.setPassword(passwordEncoder.encode(newPass));
                userRepository.save(user);
                setPasswordResponseMessage("Password successfully updated");
                setPasswordResponseCode("R00");

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


    public String getUserEmail() {
        return userEmail;
    }

    public void setUserEmail(String userEmail) {
        this.userEmail = userEmail;
    }


}
