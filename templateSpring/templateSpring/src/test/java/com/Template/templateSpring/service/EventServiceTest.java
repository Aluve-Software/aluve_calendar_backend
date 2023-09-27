package com.Template.templateSpring.service;

import com.Template.templateSpring.dto.EventDTO;
import com.Template.templateSpring.dto.GuestDTO;
import com.Template.templateSpring.entity.Event;
import com.Template.templateSpring.entity.User;
import com.Template.templateSpring.repository.EventRepository;
import com.Template.templateSpring.repository.GuestRepository;
import com.Template.templateSpring.repository.UserRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.ArrayList;

import static org.assertj.core.api.Assertions.assertThat;

@ExtendWith(MockitoExtension.class)
public class EventServiceTest {


    @Mock
    private EventRepository eventRepository;

    @Mock
    private UserRepository userRepository;

    @Mock
    private GuestRepository guestRepository;

    @InjectMocks
    private EventServiceImpl eventService;


    private Event event;

    private User user;

    private EventDTO eventDTO;

    @BeforeEach
    public void setup(){
        userRepository = Mockito.mock(UserRepository.class);
        eventRepository = Mockito.mock(EventRepository.class);
        guestRepository = Mockito.mock(GuestRepository.class);


        event = Event.builder()
                .id(1)
                .title("Event1")
                .description("Event1 description")
                .date("2020-12-12")
                .startTime("12:00")
                .endTime("12:00")
                .location("test")
                .user(user)
                .build();

        user = User.builder()
                .id(1)
                .email("nkosi@gmail.com")
                .build();

        eventDTO = new EventDTO();
        eventDTO.setTitle("test");
        eventDTO.setDescription("test");
        eventDTO.setDate("2020-12-12");
        eventDTO.setStartTime("12:00");
        eventDTO.setEndTime("12:00");
        eventDTO.setLocation("test");
        eventDTO.setOrganizer(1L);
        GuestDTO guestDTO = new GuestDTO();
        guestDTO.setEmail("test@gmail.com");
        ArrayList<GuestDTO> guestDTOArrayList = new ArrayList<>();
        guestDTOArrayList.add(guestDTO);
        eventDTO.setGuestEmails(guestDTOArrayList);
    }


    // JUnit test for getEmployeeById method
    @DisplayName("JUnit test for create event method")
    @Test
    void createEventSuccessfully(){
        // given
        // when
        Event event = eventService.createEvent(eventDTO);
        // then
        assertThat(event).isNotNull();
    }

    @DisplayName("JUnit test for create event with empty title")
    @Test
    void create_event_with_empty_title(){
        // given
        // when
        eventDTO.setTitle("");
        Event event = eventService.createEvent(eventDTO);
        // then
        assertThat(event).isNull();
    }

    @DisplayName("JUnit test for create event with 51 character long title")
    @Test
    void create_event_with_extra_long_title(){
        // given
        // when
        eventDTO.setTitle("aaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaa");
        Event event = eventService.createEvent(eventDTO);
        // then
        assertThat(event).isNull();
    }

    @DisplayName("JUnit test for create event with empty description")
    @Test
    void create_event_with_empty_description(){
        // given
        // when
        eventDTO.setDescription("");
        Event event = eventService.createEvent(eventDTO);
        // then
        assertThat(event).isNull();
    }

    @DisplayName("JUnit test for create event with 251 character long description")
    @Test
    void create_event_with_extra_long_description(){
        // given
        // when
        eventDTO.setDescription("Dont step on the broken glass. He kept telling himself that one day it would all somehow make sense. Little Red Riding Hood decided to wear orange today. Dont step on the broken glass. He kept telling himself that one day it would all somehow make sense. Little Red Riding Hood decided to wear orange today.");
        Event event = eventService.createEvent(eventDTO);
        // then
        assertThat(event).isNull();
    }

    @DisplayName("JUnit test for create event with an empty date")
    @Test
    void create_event_with_empty_date(){
        // given
        // when
        eventDTO.setDate("");
        Event event = eventService.createEvent(eventDTO);
        // then
        assertThat(event).isNull();
    }

    @DisplayName("JUnit test for create event with an empty start time")
    @Test
    void create_event_with_empty_start_time(){
        // given
        // when
        eventDTO.setStartTime("");
        Event event = eventService.createEvent(eventDTO);
        // then
        assertThat(event).isNull();
    }

    @DisplayName("JUnit test for create event with an empty end time")
    @Test
    void create_event_with_empty_end_time(){
        // given
        // when
        eventDTO.setEndTime("");
        Event event = eventService.createEvent(eventDTO);
        // then
        assertThat(event).isNull();
    }

    @DisplayName("JUnit test for create event with empty location")
    @Test
    void create_event_with_empty_location(){
        // given
        // when
        eventDTO.setLocation("");
        Event event = eventService.createEvent(eventDTO);
        // then
        assertThat(event).isNull();
    }

    @DisplayName("JUnit test for create event with 251 character long location")
    @Test
    void create_event_with_extra_long_location(){
        // given
        // when
        eventDTO.setLocation("Dont step on the broken glass. He kept telling himself that one day it would all somehow make sense. Little Red Riding Hood decided to wear orange today. Dont step on the broken glass. He kept telling himself that one day it would all somehow make sense. Little Red Riding Hood decided to wear orange today.");
        Event event = eventService.createEvent(eventDTO);
        // then
        assertThat(event).isNull();
    }

    @DisplayName("JUnit test for create event with null user")
    @Test
    void create_event_with_null_user(){
        // given
        // when
        eventDTO.setOrganizer(null);
        Event event = eventService.createEvent(eventDTO);
        // then
        assertThat(event).isNull();
    }

}