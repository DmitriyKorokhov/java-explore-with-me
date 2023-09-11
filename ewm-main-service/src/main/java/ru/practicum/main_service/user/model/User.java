package ru.practicum.main_service.user.model;

import lombok.*;

import javax.persistence.*;
import javax.validation.constraints.NotBlank;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "users", schema = "public")
public class User {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;
    @Column(name = "name")
    @NotBlank(message = "The Name should not be empty")
    private String name;
    @Column(name = "email", unique = true)
    @NotBlank(message = "The Email should not be empty")
    private String email;
}