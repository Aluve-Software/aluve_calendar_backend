package com.Template.templateSpring.service;

import lombok.Getter;
import lombok.Setter;

public class ResponseMessage {

    @Getter
    @Setter
    private String Result_Code;
    @Getter
    @Setter
    private String Message;

    public ResponseMessage(String code, String message){
        this.Result_Code = code;
        this.Message = message;
    }


}
