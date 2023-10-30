package com.Template.templateSpring.service;

import com.Template.templateSpring.dto.EventDTO;
import com.Template.templateSpring.dto.GuestDTO;
import com.Template.templateSpring.entity.Event;
import com.Template.templateSpring.entity.Guest;
import com.Template.templateSpring.entity.User;
import com.Template.templateSpring.interfaces.EventService;
import com.Template.templateSpring.mappers.Mapper;
import com.Template.templateSpring.repository.EventRepository;
import com.Template.templateSpring.repository.GuestRepository;
import com.Template.templateSpring.repository.UserRepository;
import com.Template.templateSpring.validator.EmailValidator;

import lombok.Getter;
import lombok.Setter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.stereotype.Service;

import java.lang.reflect.Field;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service("EventServiceImpl")
public class EventServiceImpl implements EventService {

    @Autowired
    private EventRepository eventRepository;
    @Autowired
    private GuestRepository guestRepository;
    @Autowired
    private GuestServiceImpl guestService;
    @Autowired
    private Mapper<Event, EventDTO> eventMapper;
    @Autowired
    private  Mapper<Guest,GuestDTO> guestMapper;

    @Setter
    @Autowired
    private UserRepository userRepository;

    private EmailValidator emailValidator;

    @Autowired
    private EmailService emailService;

    @Getter
    private String responseCode;
    @Getter
    private String responseMessage;

    public EventServiceImpl() {

    }

    @Override
    public Event createEvent(EventDTO eventDTO) {
        emailValidator = new EmailValidator();
        Event event = new Event();

        try {
            //validate the title cannot be empty or length greater than 50
            if (eventDTO.getTitle() == null || eventDTO.getTitle().isEmpty() || eventDTO.getTitle().length() > 50) {
                setResponseMessage("Title cannot be empty");
                setResponseCode("R01");
                return null;
            }

            //validate the description cannot be empty or length greater than 250
            if (eventDTO.getDescription() == null || eventDTO.getDescription().isEmpty() || eventDTO.getDescription().length() > 250) {
                setResponseMessage("Description cannot be empty");
                setResponseCode("R01");
                return null;
            }

            //validate the date cannot be empty
            if (eventDTO.getDate() == null || eventDTO.getDate().isEmpty()) {
                setResponseMessage("Date cannot be empty");
                setResponseCode("R01");
                return null;
            }

            //validate the start time cannot be empty
            if (eventDTO.getStartTime() == null || eventDTO.getStartTime().isEmpty()) {
                setResponseMessage("Start time cannot be empty");
                setResponseCode("R01");
                return null;
            }

            //validate the end time cannot be empty
            if (eventDTO.getEndTime() == null || eventDTO.getEndTime().isEmpty()) {
                setResponseMessage("End time cannot be empty");
                setResponseCode("R01");
                return null;
            }

            //validate the location cannot be empty or length greater than 250
            if (eventDTO.getLocation() == null || eventDTO.getLocation().isEmpty() || eventDTO.getLocation().length() > 250) {
                setResponseMessage("Location cannot be empty");
                setResponseCode("R01");
                return null;
            }

            //validate the organizer cannot be empty
            if (eventDTO.getOrganizer() == null) {
                setResponseMessage("Organizer cannot be empty");
                setResponseCode("R01");
                return null;
            }


            //Saving event to database
            event.setTitle(eventDTO.getTitle());
            event.setDescription(eventDTO.getDescription());
            event.setDate(eventDTO.getDate());
            event.setStartTime(eventDTO.getStartTime());
            event.setEndTime(eventDTO.getEndTime());
            event.setLocation(eventDTO.getLocation());
            event.setDateCreated(eventDTO.getDateCreated());
            UserSignUpService userSignUpService = new UserSignUpService(userRepository, null);
            Optional<User> user = userSignUpService.getUserById(eventDTO.getOrganizer());
            event.setUser(user.orElse(null));
            eventRepository.save(event);

            //create guests
            //iterate the guest emails in the eventDTO
            for (GuestDTO guestDTO : eventDTO.getGuests()) {
                //check if the guest email is valid
                if (emailValidator.validateEmail(guestDTO.getEmail())) {
                    Guest guest = new Guest();
                    //if valid, create a guest object
                    guest.setEmail(guestDTO.getEmail());
                    guest.setEvent(event);
                    //save the guest object to the database
                    guestRepository.save(guest);
                }
            }

            //Sending emails to all guests using a new thread
            Thread t1 = new Thread(new Runnable() {
                @Override
                public void run() {
                    //sendInvitationEmails(eventDTO);
                }
            });
            t1.start();

            setResponseMessage("Successfully created event!");
            setResponseCode("R00");

            return event;

        } catch (Exception e) {
            System.out.println("An error occurred while processing your request: " + e.getMessage());
            setResponseMessage("An error occurred while processing your request: " + e.getMessage());
            setResponseCode("R01");
            return null;
        }
    }


    public Event partialUpdate(Long id, EventDTO eventDTO) {
        // Retrieve the existing event
        Optional<Event> existingEventOptional = eventRepository.findById(id);
        if (existingEventOptional.isEmpty()) {
            // Handle not found case
        }

        Event existingEvent = existingEventOptional.get();
        Field[] fields = eventDTO.getClass().getDeclaredFields();
        for (Field field : fields) {
            try {
                // Set the field accessible so we can modify private fields if necessary
                field.setAccessible(true);

                // Get the value from the DTO
                Object value = field.get(eventDTO);

                if (value != null) {
                    // Find the corresponding field in the existing event
                    Field existingField = existingEvent.getClass().getDeclaredField(field.getName());
                    existingField.setAccessible(true);

                    // Set the value of the existing event's field
                    existingField.set(existingEvent, value);
                }
            } catch (NoSuchFieldException | IllegalAccessException e) {
                // Handle any exceptions as needed
                e.printStackTrace();
            }
        }

        // Save the updated event
        Event updatedEvent = eventRepository.save(existingEvent);

        return updatedEvent;
    }

    public EventDTO createAndUpdateDtoGenerationResponse(EventDTO eventDTO ,Event eventEntity){

        //save Guest
//        guestService.createAndUpdateGuest(eventDTO, eventEntity);

        // Map the updated Event entity back to EventDTO
        EventDTO updatedEventDTO = eventMapper.mapTo(eventEntity);
        List<Guest> updatedGuestEntities = guestRepository.findByEvent( eventEntity);
        List<GuestDTO> guestDTOs = updatedGuestEntities.stream()
                .map(guestMapper::mapTo)
                .collect(Collectors.toList());
        updatedEventDTO.setGuestEmails(guestDTOs);

        updatedEventDTO.setOrganizer(eventEntity.getUser().getId().longValue());
        return  updatedEventDTO;
    }


    @Override
    public boolean isExists(Long id) {
        return eventRepository.existsById(id);
    }
    public boolean isUserExists(Long id) {
        return userRepository.existsById(id);
    }

    @Override
    public Event save(Event eventEntity) {
        //save guest

        return eventRepository.save(eventEntity);
    }

    public void setResponseMessage(String responseMessage) {
        this.responseMessage = responseMessage;
    }

    public void setResponseCode(String responseCode) {
        this.responseCode = responseCode;
    }

    private void sendInvitationEmails(EventDTO eventDTO) {
        //Sending email to all guests
        for (GuestDTO guestDTO : eventDTO.getGuests()) {
            //check if the guest email is valid
            if (emailValidator.validateEmail(guestDTO.getEmail())) {
                //if valid, create a guest object
                SimpleMailMessage mailMessage = new SimpleMailMessage();
                mailMessage.setTo(guestDTO.getEmail());
                mailMessage.setSubject("Invitation to " + eventDTO.getTitle());
                mailMessage.setText("You are invited to " + eventDTO.getTitle() + " on " + eventDTO.getDate() + " at " + eventDTO.getStartTime() + " to " + eventDTO.getEndTime() + " at " + eventDTO.getLocation() + " by " + eventDTO.getOrganizer());
                emailService.sendEmail(mailMessage);
            }
        }
    }

}
