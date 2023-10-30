package com.Template.templateSpring.dto;

import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.List;

import com.Template.templateSpring.validator.ValidDateCreated;
import jakarta.validation.Valid;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.Getter;


@Getter
public class EventDTO {
    private String id;

    @NotEmpty
    @NotBlank
    @Size(min = 3, max = 200, message = "Invalid title: Must be of 3 - 30 characters")
    private String title;

    @NotEmpty
    @NotBlank
    @Size(min = 3, max = 200, message = "Invalid description: Must be of 3 - 30 characters")
    private String description;

    @NotEmpty
    @NotBlank
    private String date;

    @NotEmpty
    @NotBlank
    private String startTime;

    @NotEmpty
    @NotBlank
    private String endTime;

    @NotEmpty
    @NotBlank
    private String location;

    @NotNull(message = "organizer is mandatory")
    private Long organizer;


    @ValidDateCreated
    private final Timestamp dateCreated = new Timestamp(System.currentTimeMillis());

    @NotEmpty
    @Valid
    private ArrayList<GuestDTO> guests;

    public EventDTO() {
        // TODO document why this constructor is empty
    }

    public void setId(String id) {
        this.id = id;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public void setStartTime(String startTime) {
        this.startTime = startTime;
    }

    public void setEndTime(String endTime) {
        this.endTime = endTime;
    }

    public void setLocation(String location) {
        this.location = location;
    }

    public void setOrganizer(Long organizer) {
        this.organizer = organizer;
    }

    public void setGuestEmails(List<GuestDTO> guests) {
        this.guests = (ArrayList<GuestDTO>) guests;
    }
}
