package com.Template.templateSpring.service;

import com.Template.templateSpring.dto.EventDTO;
import com.Template.templateSpring.dto.GuestDTO;
import com.Template.templateSpring.entity.Event;
import com.Template.templateSpring.entity.Guest;
import com.Template.templateSpring.repository.GuestRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Service
public class GuestServiceImpl {

    @Autowired
    private GuestRepository guestRepository;

    public void createAndUpdateGuest(EventDTO eventDTO, Event event){
        if(eventDTO.getGuests() == null){
              return;
        }
        List<Guest> guestEntities = new ArrayList<>();

// Fetch existing guests with the same Event
        List<Guest> existingGuests = guestRepository.findByEvent(event);

// Create a map of existing guests by email for easier comparison
        Map<String, Guest> existingGuestsByEmail = existingGuests.stream()
                .collect(Collectors.toMap(Guest::getEmail, guest -> guest));

        for (GuestDTO guestDTO : eventDTO.getGuests()) {
            Guest guestEntity = existingGuestsByEmail.get(guestDTO.getEmail());

            if (guestEntity == null) {
                // If guest with the same email doesn't exist, create a new guest
                guestEntity = new Guest();
                guestEntity.setEmail(guestDTO.getEmail());
                guestEntity.setEvent(event);
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
    }
}
