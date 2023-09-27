package com.Template.templateSpring.entity;


import com.Template.templateSpring.enums.Status;
import jakarta.persistence.*;
import lombok.*;

import java.sql.Timestamp;

@Entity
@Table(name = "event")
@Setter
@Getter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class Event {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @Column(nullable = false, name = "title")
    private String title;

    @Column(nullable = false, name = "description")
    private String description;

    @Column(nullable = false, name = "date")
    private String date;

    @Column(nullable = false, name = "start_time")
    private String startTime;

    @Column(nullable = false, name = "end_time")
    private String endTime;

    @Column(nullable = false, name = "location")
    private String location;

    @Column(nullable = false, name = "date_created")
    private Timestamp dateCreated;

    @Column(nullable = false, name = "status")
    private Status status = Status.ACTIVE;

    @ManyToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "organizer")
    private User user;

}
