package com.david.caterest.entity;

import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.Setter;

import javax.persistence.*;
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
    private String userName;
    private String firstName;
    private String lastName;
    private LocalDate dateOfBirth;
    private String telephoneNumber;
    private String city;
    private String country;
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "user")
    private List<Picture> pictures = new ArrayList<>();

}

