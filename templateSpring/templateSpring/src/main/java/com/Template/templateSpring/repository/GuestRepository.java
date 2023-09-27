package com.Template.templateSpring.repository;

import com.Template.templateSpring.entity.Event;
import com.Template.templateSpring.entity.Guest;

import java.util.ArrayList;

import org.springframework.data.jpa.repository.JpaRepository;

public interface GuestRepository extends JpaRepository<Guest, Long> {
    void findByEventId(Integer id);

    ArrayList<Guest> findById(Event event);
}
