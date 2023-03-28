package com.david.caterest.entity;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.Setter;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

@Setter
@Getter
@RequiredArgsConstructor
@Entity
@Table(name = "users")
public class User {

    @Id @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;
    @Lob
    private Byte[] profilePicture;
    private String username;
    private String firstName;
    private String lastName;
    private String email;
    private String password;
    private String biography;
    private LocalDate dateOfBirth;
    private String telephoneNumber;
    private String city;
    private String country;
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "user")
    private List<Picture> pictures = new ArrayList<>();

}

