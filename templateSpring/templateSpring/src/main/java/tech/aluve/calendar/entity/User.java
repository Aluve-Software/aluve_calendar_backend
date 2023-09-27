package tech.aluve.calendar.entity;

import jakarta.persistence.*;
import lombok.Getter;

@Entity
@Table(name="users")
public class User {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;
    @Getter
    @Column(nullable = false, unique = true, name = "email")
    private String email;
    @Getter
    @Column(nullable = false, name = "password")
    private String password;


    public void setId(Integer id) {
        this.id = id;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public void setPassword(String password) {
        this.password = password;
    }



}
