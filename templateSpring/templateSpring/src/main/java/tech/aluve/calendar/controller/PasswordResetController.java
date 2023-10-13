package tech.aluve.calendar.controller;

import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.repository.query.Param;
import org.springframework.web.bind.annotation.*;
import tech.aluve.calendar.dto.UserSignUpDto;
import tech.aluve.calendar.entity.User;
import tech.aluve.calendar.security.JwtToken;
import tech.aluve.calendar.service.PasswordResetServiceImpl;
import tech.aluve.calendar.validator.ResponseMessage;

import javax.naming.AuthenticationException;

@RestController
@RequestMapping("/passwordreset")
public class PasswordResetController {

    @Autowired
    private PasswordResetServiceImpl passwordResetServiceImpl;

    private ResponseMessage responseMessage;

    @PostMapping("/permission")
    public ResponseMessage validateUser(@RequestParam("email")String userEmail, HttpServletRequest servRequest){
        passwordResetServiceImpl.authenticateUser(userEmail, servRequest);
        responseMessage = new ResponseMessage(passwordResetServiceImpl.getPasswordResponseCode(), passwordResetServiceImpl.getPasswordResponseMessage());
        return responseMessage;
    }

    @GetMapping("/verifytoken")
    public ResponseMessage validateToken(@RequestParam("token")String userToken) throws AuthenticationException {
        passwordResetServiceImpl.validateToken(userToken);
        responseMessage = new ResponseMessage(passwordResetServiceImpl.getPasswordResponseCode(), passwordResetServiceImpl.getPasswordResponseMessage());
        return responseMessage;
    }

    @PostMapping("/newpassword")
    public ResponseMessage resetPassword(@RequestParam("password")String newPassword, @RequestParam("confirmPassword")String conPassword){
        passwordResetServiceImpl.resetPassword(newPassword, conPassword);
        responseMessage = new ResponseMessage(passwordResetServiceImpl.getPasswordResponseCode(), passwordResetServiceImpl.getPasswordResponseMessage());
        return responseMessage;
    }


}
