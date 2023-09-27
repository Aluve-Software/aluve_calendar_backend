package com.Template.templateSpring.interfaces;

import com.Template.templateSpring.dto.EventDTO;
import com.Template.templateSpring.entity.Event;

public interface EventService {
    Event createEvent(EventDTO eventDTO);
}