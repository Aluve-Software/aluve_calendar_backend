package com.Template.templateSpring.Dto;

public class UserSignUpDto {
    private String email;
    private String password;

    public UserSignUpDto() {

    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }
}
