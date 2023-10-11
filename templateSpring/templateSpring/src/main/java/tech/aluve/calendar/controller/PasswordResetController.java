package tech.aluve.calendar.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import tech.aluve.calendar.dto.UserSignUpDto;
import tech.aluve.calendar.security.JwtToken;
import tech.aluve.calendar.service.PasswordResetServiceImpl;
import tech.aluve.calendar.validator.ResponseMessage;

@RestController
@RequestMapping("/passwordreset")
public class PasswordResetController {

    @Autowired
    private PasswordResetServiceImpl passwordResetServiceImpl;

    private ResponseMessage responseMessage;

    @PostMapping("/permission")
    public ResponseMessage validateUser(@RequestBody UserSignUpDto userPasswordResetDTO){
        passwordResetServiceImpl.authenticateUser(userPasswordResetDTO);
        responseMessage = new ResponseMessage(passwordResetServiceImpl.getPasswordResponseCode(), passwordResetServiceImpl.getPasswordResponseMessage());
        return responseMessage;
    }

    @PostMapping("/newpassword")
    public ResponseMessage newPassword(@RequestBody UserSignUpDto userPasswordReset){
//        passwordResetServiceImpl.authenticateUser(userPasswordReset);
        return new ResponseMessage("r001", "new password reset ");
    }
}
