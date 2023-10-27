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
import com.Template.templateSpring.service.ResponseMessage;

import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.stream.Collectors;

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
    private  Mapper<Guest,GuestDTO> guestMapper;
    @Autowired
    private GuestRepository guestRepository;
    @Autowired
    private EventRepository eventRepository;
    @Autowired
    private UserRepository userRepository;

    private ResponseMessage responseMessage;

    @RequestMapping(value = "/create", method = RequestMethod.POST)
    @ResponseBody
    public ResponseMessage createEvent(@RequestBody EventDTO eventDTO) {
        eventService.createEvent(eventDTO);
        responseMessage = new ResponseMessage(eventService.getResponseCode(), eventService.getResponseMessage());
        return responseMessage;
    }


@PutMapping(path = "/{id}")
public ResponseEntity<EventDTO> fullUpdateAuthor(@PathVariable("id") Long id, @Valid  @RequestBody EventDTO eventDTO) {
    if (!eventService.isExists(id)) {
        return new ResponseEntity<>(HttpStatus.NOT_FOUND);
    }
    //check if organizer exists
    Optional<User> organizerOptional = userRepository.findById(eventDTO.getOrganizer());


    if(!organizerOptional.isPresent()){
        return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
    }
    Optional<Event> eventOptional = eventRepository.findById(id);
    Event event = eventOptional.get();
    Long eventOrganizerId = Long.valueOf(event.getUser() != null ? event.getUser().getId() : null);

    // Check if the organizer of the retrieved event matches the provided organizer ID
    if (eventOrganizerId == null && !eventOrganizerId.equals(eventDTO.getOrganizer())) {
        return new ResponseEntity<>(HttpStatus.FORBIDDEN);
    }

    // Set the event ID
    eventDTO.setId(id.toString());

    // Map the EventDTO to Event entity
    Event eventEntity = eventMapper.mapFrom(eventDTO);

    userRepository.findById(eventDTO.getOrganizer()).ifPresent(user -> eventEntity.setUser(user));




    //check if the organizer is making the change
    System.out.println(eventDTO.getOrganizer());
    Event savedEventEntity = eventService.save(eventEntity);

    //check if the organizer is making the change

    // Create and associate Guest entities
    List<Guest> guestEntities = new ArrayList<>();

// Fetch existing guests with the same Event
    List<Guest> existingGuests = guestRepository.findByEvent(savedEventEntity);

// Create a map of existing guests by email for easier comparison
    Map<String, Guest> existingGuestsByEmail = existingGuests.stream()
            .collect(Collectors.toMap(Guest::getEmail, guest -> guest));

    for (GuestDTO guestDTO : eventDTO.getGuests()) {
        Guest guestEntity = existingGuestsByEmail.get(guestDTO.getEmail());

        if (guestEntity == null) {
            // If guest with the same email doesn't exist, create a new guest
            guestEntity = new Guest();
            guestEntity.setEmail(guestDTO.getEmail());
            guestEntity.setEvent(savedEventEntity);
        }

        // Update any fields in the existing or new guest if needed


        guestEntities.add(guestEntity);
    }

// Save the updated and new guest entities
    guestRepository.saveAll(guestEntities);

// Remove guests that are no longer present in the new data
    existingGuests.removeAll(guestEntities);

// Delete the removed guests
    guestRepository.deleteAll(existingGuests);
    //eventEntity.setGuests(guestEntities); // Set the guests in the event entity

    // Save the event entity along with associated guests


    // Map the saved Event entity back to EventDTO
    EventDTO savedEventDTO = eventMapper.mapTo(savedEventEntity);
    List<Guest> savedQuestEntities =  guestRepository.findByEvent(eventEntity);
    List<GuestDTO> guestDTOs = savedQuestEntities.stream()
            .map(guestMapper::mapTo)
            .collect(Collectors.toList());
    savedEventDTO.setGuestEmails(guestDTOs);

    savedEventDTO.setOrganizer(savedEventEntity.getUser().getId().longValue());

    return new ResponseEntity<>(savedEventDTO, HttpStatus.OK);
}

    @PatchMapping(path = "/{id}")
    public ResponseEntity<EventDTO> partialUpdateEvent(
            @PathVariable("id") Long id,
            @RequestBody EventDTO eventDTO) {
        if (!eventService.isExists(id)) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }

        Event updatedEvent = eventService.partialUpdate(id, eventDTO);

        // Map the updated Event entity back to EventDTO
        EventDTO updatedEventDTO = eventMapper.mapTo(updatedEvent);
        List<Guest> updatedGuestEntities = guestRepository.findByEvent(updatedEvent);
        List<GuestDTO> guestDTOs = updatedGuestEntities.stream()
                .map(guestMapper::mapTo)
                .collect(Collectors.toList());
        updatedEventDTO.setGuestEmails(guestDTOs);

        updatedEventDTO.setOrganizer(updatedEvent.getUser().getId().longValue());

        return new ResponseEntity<>(updatedEventDTO, HttpStatus.OK);
    }



}
