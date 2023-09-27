package com.Template.templateSpring.controller;

import com.Template.templateSpring.dto.EventDTO;
import com.Template.templateSpring.service.EmailService;
import com.Template.templateSpring.service.EventServiceImpl;
import com.Template.templateSpring.service.ResponseMessage;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/event")
public class EventController {

    @Autowired
    private EventServiceImpl eventService;
    @Autowired
    private EmailService emailService;
    private ResponseMessage responseMessage;

    @RequestMapping(value = "/create", method = RequestMethod.POST)
    @ResponseBody
    public ResponseMessage createEvent(@RequestBody EventDTO eventDTO) {
        eventService.createEvent(eventDTO);
        responseMessage = new ResponseMessage(eventService.getResponseCode(), eventService.getResponseMessage());
        return responseMessage;
    }

}
