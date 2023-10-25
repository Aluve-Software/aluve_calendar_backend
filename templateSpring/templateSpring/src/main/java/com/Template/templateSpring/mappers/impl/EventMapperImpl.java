package com.Template.templateSpring.mappers.impl;

import com.Template.templateSpring.dto.EventDTO;
import com.Template.templateSpring.entity.Event;
import com.Template.templateSpring.mappers.Mapper;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Component;

@Component
public class EventMapperImpl implements Mapper<Event, EventDTO> {

    private ModelMapper modelMapper;

    public EventMapperImpl(ModelMapper modelMapper) {
        this.modelMapper = modelMapper;
    }

    @Override
    public EventDTO mapTo(Event event) {
        return modelMapper.map(event, EventDTO.class);
    }

    @Override
    public Event mapFrom(EventDTO eventDTO) {
        return modelMapper.map(eventDTO, Event.class);
    }
}

