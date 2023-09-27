package tech.aluve.calendar.controller;

import tech.aluve.calendar.dto.UserSignUpDto;
import tech.aluve.calendar.service.ResponseMessage;
import tech.aluve.calendar.service.UserSignUpServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;

@RestController
@RequestMapping("/calendar")
public class SignUpController {

    @Autowired
    private UserSignUpServiceImpl userSignUpService;
    private ResponseMessage responseMessage;

    @PostMapping( "/signup")
    public ResponseMessage signUpUSer(@RequestBody UserSignUpDto userSignUpDto){
        userSignUpService.saveUser(userSignUpDto);
        responseMessage = new ResponseMessage(userSignUpService.getResponseCode(), userSignUpService.getResponseMessage());
        return responseMessage;
    }

    @GetMapping("/verifyemail")
    public ModelAndView homepage(){
        ModelAndView view = new ModelAndView();
        view.setViewName("verifyEmail");
        return view;
    }
}
