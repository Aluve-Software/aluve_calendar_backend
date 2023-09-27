package com.Template.templateSpring.dto;

import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.List;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotEmpty;
import lombok.Getter;

@Getter
public class EventDTO {
    private String id;

    @NotEmpty
    @NotBlank
    private String title;

    @NotEmpty
    @NotBlank
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

    @NotEmpty
    @NotBlank
    private Long organizer;

    @NotEmpty
    @NotBlank
    private final Timestamp dateCreated = new Timestamp(System.currentTimeMillis());

    @NotEmpty
    @NotBlank
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
