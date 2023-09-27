package com.Template.templateSpring.validator;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class EmailValidator{
    private Pattern pattern;
    private Matcher matcher;
    private static final String EMAIL_PATTERN = "^(?=.{1,50}$)[a-zA-Z0-9._%+-]+@[a-zA-Z0-9.-]+\\.[a-zA-Z]{2,}$";
    public boolean validateEmail(String email) {
        pattern = Pattern.compile(EMAIL_PATTERN);
        matcher = pattern.matcher(email);
        return matcher.matches();
    }
    public boolean emailNull(String email){
        return email == null;
    }

}