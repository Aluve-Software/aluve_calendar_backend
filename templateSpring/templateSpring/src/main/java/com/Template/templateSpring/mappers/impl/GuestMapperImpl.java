package com.Template.templateSpring.mappers.impl;

import com.Template.templateSpring.dto.GuestDTO;
import com.Template.templateSpring.entity.Guest;
import com.Template.templateSpring.mappers.Mapper;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class GuestMapperImpl implements Mapper<Guest, GuestDTO> {
    @Autowired
    private ModelMapper modelMapper;


    @Override
    public GuestDTO mapTo(Guest guest) {
        return modelMapper.map(guest,GuestDTO.class);
    }

    @Override
    public Guest mapFrom(GuestDTO guestDTO) {
        return modelMapper.map(guestDTO,Guest.class);
    }
}
