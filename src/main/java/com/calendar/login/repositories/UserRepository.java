/**
 * Repository interface that manages user entities
 * @author Michelle
 * @version 1
 */
package com.calendar.login.repositories;
import com.calendar.login.entities.User;
import org.springframework.data.jpa.repository.JpaRepository;


/*
  extends JpaRepository for database operations
 */
public interface UserRepository extends JpaRepository<User,Long> {

    User findByEmail(String email); //looks for user by email
}
