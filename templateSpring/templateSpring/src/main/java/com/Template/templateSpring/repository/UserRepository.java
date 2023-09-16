package com.Template.templateSpring.repository;

import com.Template.templateSpring.dto.UserSignUpDto;
import com.Template.templateSpring.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UserRepository extends JpaRepository<User, Long> {
    User findByEmail(String email);
    Boolean existsByEmail(String email);
}
