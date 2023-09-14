package com.Template.templateSpring.Validator;
import com.Template.templateSpring.Dto.UserSignUpDto;
import java.util.regex.Matcher;
import java.util.regex.Pattern;



public class PasswordValidator{
    private Pattern pattern;
    private Matcher matcher;
    private static final String NUMBER_PATTERN = "^(?=.*[A-Za-z])(?=.*\\d)[A-Za-z\\d@$!%*?&]{8,50}$\n";
    public boolean matchingPassword(UserSignUpDto user){
        return user.getPassword().equals(user.getConfirmationPassword());
    }

    public boolean validPassword(UserSignUpDto user){
        pattern = Pattern.compile(NUMBER_PATTERN);
        matcher = pattern.matcher(user.getPassword());
        return matcher.matches();
    }
}
