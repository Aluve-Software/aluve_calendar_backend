package com.Template.templateSpring.repository;

import com.Template.templateSpring.entity.Event;

import com.Template.templateSpring.entity.Guest;
import org.springframework.data.jpa.repository.JpaRepository;

public interface EventRepository extends JpaRepository<Event, Long> {
}
