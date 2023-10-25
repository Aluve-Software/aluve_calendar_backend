package com.Template.templateSpring.controller;

import com.Template.templateSpring.dto.EventDTO;
import com.Template.templateSpring.dto.GuestDTO;
import com.Template.templateSpring.entity.Event;
import com.Template.templateSpring.entity.Guest;
import com.Template.templateSpring.mappers.Mapper;
import com.Template.templateSpring.repository.GuestRepository;
import com.Template.templateSpring.service.EmailService;
import com.Template.templateSpring.service.EventServiceImpl;
import com.Template.templateSpring.service.ResponseMessage;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;

@RestController
@RequestMapping("/event")
public class EventController {

    @Autowired
    private EventServiceImpl eventService;
    @Autowired
    private EmailService emailService;
    @Autowired
    private Mapper<Event, EventDTO> eventMapper;
    @Autowired
    private GuestRepository guestRepository;

    private ResponseMessage responseMessage;

    @RequestMapping(value = "/create", method = RequestMethod.POST)
    @ResponseBody
    public ResponseMessage createEvent(@RequestBody EventDTO eventDTO) {
        eventService.createEvent(eventDTO);
        responseMessage = new ResponseMessage(eventService.getResponseCode(), eventService.getResponseMessage());
        return responseMessage;
    }

//    @PutMapping(path = "/{id}")
//    public ResponseEntity<EventDTO> fullUpdateAuthor(
//            @PathVariable("id") Long id,
//            @RequestBody EventDTO eventDTO) {
//
//        if(!eventService.isExists(id)) {
//            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
//        }
//
//        eventDTO.setId(id.toString());
//        Event eventEntity = authorMapper.mapFrom(eventDTO);
//        Event savedAuthorEntity = eventService.save(eventEntity);
//        return new ResponseEntity<>(
//                authorMapper.mapTo(savedAuthorEntity),
//                HttpStatus.OK);
//    }
@PutMapping(path = "/{id}")
public ResponseEntity<EventDTO> fullUpdateAuthor(@PathVariable("id") Long id, @RequestBody EventDTO eventDTO) {
    if (!eventService.isExists(id)) {
        return new ResponseEntity<>(HttpStatus.NOT_FOUND);
    }

    // Set the event ID
    eventDTO.setId(id.toString());

    // Map the EventDTO to Event entity
    Event eventEntity = eventMapper.mapFrom(eventDTO);
    Event savedEventEntity = eventService.save(eventEntity);
    // Create and associate Guest entities
    List<Guest> guestEntities = new ArrayList<>();
    for (GuestDTO guestDTO : eventDTO.getGuests()) {
        Guest guestEntity = new Guest();
        guestEntity.setEmail(guestDTO.getEmail());
        guestEntity.setEvent(savedEventEntity); // Associate the guest with the event
        guestEntities.add(guestEntity);

        /// Save the guest entity to the database
        guestRepository.save(guestEntity);

    }

    //eventEntity.setGuests(guestEntities); // Set the guests in the event entity

    // Save the event entity along with associated guests


    // Map the saved Event entity back to EventDTO
    EventDTO savedEventDTO = eventMapper.mapTo(savedEventEntity);

    return new ResponseEntity<>(savedEventDTO, HttpStatus.OK);
}


}
