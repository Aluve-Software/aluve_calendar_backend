package com.Template.templateSpring.validator;

import com.Template.templateSpring.dto.EventDTO;
import com.Template.templateSpring.dto.GuestDTO;
import com.Template.templateSpring.interfaces.EventService;
import com.Template.templateSpring.service.EventServiceImpl;
import com.Template.templateSpring.service.ResponseMessage;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import java.time.LocalDate;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;
import java.util.List;

public class EventDTOValidator {

    private final EventService eventService;

    public EventDTOValidator(EventService eventService) {
        this.eventService = eventService;
    }




    public static ResponseMessage validateEventDTO(EventDTO eventDTO, EventServiceImpl eventService, EmailValidator emailValidator) {
        if (eventDTO == null) {
//            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
            return new ResponseMessage("R01", "Invalid request: Event data is missing");
        }

        ResponseMessage titleValidationResult = validateTitle(eventDTO.getTitle());
        if (titleValidationResult != null) {
            return titleValidationResult;
        }

        ResponseMessage descriptionValidationResult = validateDescription(eventDTO.getDescription());
        if (descriptionValidationResult != null) {
            return descriptionValidationResult;
        }

        ResponseMessage dateValidationResult = validateDate(eventDTO.getDate());
        if (dateValidationResult != null) {
            return dateValidationResult;
        }

        ResponseMessage startTimeValidationResult = validateTime(eventDTO.getStartTime());
        if (startTimeValidationResult != null) {
            return startTimeValidationResult;
        }

        ResponseMessage endTimeValidationResult = validateTime(eventDTO.getEndTime());
        if (endTimeValidationResult != null) {
            return endTimeValidationResult;
        }

        ResponseMessage locationValidationResult = validateLocation(eventDTO.getLocation());
        if (locationValidationResult != null) {
            return locationValidationResult;
        }

        ResponseMessage organizerValidationResult = validateOrganizer( eventService,eventDTO.getOrganizer());
        if (organizerValidationResult != null) {
            return organizerValidationResult;
        }

        ResponseMessage guestsValidationResult = validateGuests(eventDTO.getGuests(),emailValidator);
        if (guestsValidationResult != null) {
            return guestsValidationResult;
        }

        return null; // No validation errors
    }

    private static ResponseMessage validateTitle(String title) {
        if (title != null) {
            String trimmedTitle = title.trim();
            if (trimmedTitle.isEmpty() || trimmedTitle.length() < 3 || trimmedTitle.length() > 100) {
                return new ResponseMessage("R01", "Invalid title: Must be 3 - 100 characters");
//                return new ResponseEntity<>("Invalid title: Must be 3 - 100 characters", HttpStatus.BAD_REQUEST);
            }
        }
        return null;
    }

    private static ResponseMessage validateDescription(String description) {
        if (description != null) {
            String trimmedDescription = description.trim();
            if (trimmedDescription.isEmpty() || trimmedDescription.length() < 3 || trimmedDescription.length() > 100) {
//                return new ResponseEntity<>("Invalid description: Must be 3 - 100 characters", HttpStatus.BAD_REQUEST);
                return new ResponseMessage("R01", "Invalid description: Must be 3 - 100 characters");
            }
        }
        return null;
    }

    private static ResponseMessage validateDate(String date) {
        if (date != null) {
            try {
                LocalDate parsedDate = LocalDate.parse(date);
                LocalDate currentDate = LocalDate.now();
                if (parsedDate.isBefore(currentDate)) {
                    return new ResponseMessage("R01", "Date should not be in the past");
                }
            } catch (DateTimeParseException e) {
                return new ResponseMessage("R01", "Invalid date format");
            }
        }
        return null;
    }

    private static ResponseMessage validateTime(String time) {
        if (time != null) {
            try {
                DateTimeFormatter formatter = DateTimeFormatter.ofPattern("HH:mm");
                LocalTime parsedTime = LocalTime.parse(time, formatter);
            } catch (DateTimeParseException e) {
                return new ResponseMessage("R01", "Invalid time format: Use HH:mm format (e.g., 13:45)");
            }
        }
        return null;
    }

    private static ResponseMessage validateLocation(String location) {
        if (location != null) {
            String trimmedLocation = location.trim();
            if (trimmedLocation.isEmpty() || trimmedLocation.length() < 3 || trimmedLocation.length() > 100) {
                return new ResponseMessage("R01", "Invalid time format: Use HH:mm format (e.g., 13:45)");
            }
        }
        return null;
    }

    public static ResponseMessage validateOrganizer(EventServiceImpl eventService, Long organizerId) {
        if (organizerId != null) {
            if (organizerId <= 0) {
                return new ResponseMessage("R01", "Invalid organizer: User ID must be a positive number");
            }
            if (!eventService.isUserExists(organizerId)) {
                return new ResponseMessage("R01", "Invalid organizer: User ID does not exist");
            }
        }
        return null;
    }

    private static ResponseMessage validateGuests(List<GuestDTO> guests,EmailValidator emailValidator) {
        if (guests != null) {
            for (GuestDTO guestDTO : guests) {
                if (guestDTO.getEmail() != null && !emailValidator.validateEmail(guestDTO.getEmail())) {
                    return new ResponseMessage("R01", "Invalid email format for guest: ");
                }
            }
        }
        return null;
    }
}

