package com.Template.templateSpring.controller;

import com.Template.templateSpring.dto.EventDTO;
import com.Template.templateSpring.dto.GuestDTO;
import com.Template.templateSpring.entity.Event;
import com.Template.templateSpring.entity.Guest;
import com.Template.templateSpring.entity.User;
import com.Template.templateSpring.mappers.Mapper;
import com.Template.templateSpring.repository.EventRepository;
import com.Template.templateSpring.repository.GuestRepository;
import com.Template.templateSpring.repository.UserRepository;
import com.Template.templateSpring.service.EmailService;
import com.Template.templateSpring.service.EventServiceImpl;
import com.Template.templateSpring.service.GuestServiceImpl;
import com.Template.templateSpring.service.ResponseMessage;

import com.Template.templateSpring.validator.EmailValidator;
import com.Template.templateSpring.validator.EventDTOValidator;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;
import java.util.*;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/event")
public class EventController {

    @Autowired
    private EventServiceImpl eventService;
    @Autowired
    private GuestServiceImpl guestService;

    @Autowired
    private Mapper<Event, EventDTO> eventMapper;


    @Autowired
    private EventRepository eventRepository;
    @Autowired
    private UserRepository userRepository;

    private ResponseMessage responseMessage;
    private EmailValidator emailValidator;

    @RequestMapping(value = "/create", method = RequestMethod.POST)
    @ResponseBody
    public ResponseMessage createEvent(@RequestBody EventDTO eventDTO) {
        eventService.createEvent(eventDTO);
        responseMessage = new ResponseMessage(eventService.getResponseCode(), eventService.getResponseMessage());
        return responseMessage;
    }


@PutMapping(path = "/{id}")
public ResponseEntity<?> fullUpdateAuthor(@PathVariable("id") Long id, @Valid  @RequestBody EventDTO eventDTO) {
    if (!eventService.isExists(id)) {
        return new ResponseEntity<>(HttpStatus.NOT_FOUND);
    }
    //check if organizer exists
    Optional<User> organizerOptional = userRepository.findById(eventDTO.getOrganizer());
    if(!organizerOptional.isPresent()){
        return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
    }

    //Body request validation
    emailValidator = new EmailValidator();
    ResponseMessage validate = EventDTOValidator.validateEventDTO(eventDTO,eventService,emailValidator);
    if (validate != null) {
        return new ResponseEntity<>( validate, HttpStatus.BAD_REQUEST);

    }

    // Check if the organizer of the retrieved event matches the provided organizer ID
    Optional<Event> eventOptional = eventRepository.findById(id);
    Event event = eventOptional.get();
    Long eventOrganizerId = Long.valueOf(event.getUser() != null ? event.getUser().getId() : null);
    if (eventOrganizerId == null && !eventOrganizerId.equals(eventDTO.getOrganizer())) {
        return new ResponseEntity<>(HttpStatus.FORBIDDEN);
    }

    // Set the event ID
    eventDTO.setId(id.toString());

    // Map the EventDTO to Event entity
    Event eventEntity = eventMapper.mapFrom(eventDTO);

    userRepository.findById(eventDTO.getOrganizer()).ifPresent(user -> eventEntity.setUser(user));


    Event savedEventEntity = eventService.save(eventEntity);



    // Create and associate Guest entities
    guestService.createAndUpdateGuest(eventDTO,savedEventEntity);

    EventDTO savedEventDTO = eventService.createAndUpdateDtoGenerationResponse( eventDTO , eventEntity);



    return new ResponseEntity<>(savedEventDTO, HttpStatus.OK);
}

    @PatchMapping(path = "/{id}")
    public ResponseEntity<?> partialUpdateEvent(
            @PathVariable("id") Long id,
            @RequestBody EventDTO eventDTO) {

       // check if event exists
        if (!eventService.isExists(id) ) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
        //Body request validation
        emailValidator = new EmailValidator();
        ResponseMessage validate = EventDTOValidator.validateEventDTO(eventDTO,eventService,emailValidator);
        if (validate != null) {
            return new ResponseEntity<>( validate, HttpStatus.BAD_REQUEST);

        }

        //update event
        Event updatedEvent = eventService.partialUpdate(id, eventDTO);
        guestService.createAndUpdateGuest(eventDTO, updatedEvent);
        //fetch response
        EventDTO updatedEventDTO = eventService.createAndUpdateDtoGenerationResponse( eventDTO , updatedEvent);

        return new ResponseEntity<>(updatedEventDTO, HttpStatus.OK);
    }


}
