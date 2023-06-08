package com.david.caterest.dto.user;

import jakarta.validation.constraints.NotNull;

import java.time.LocalDate;

public class UserSignUpDto {

    private Byte[] profilePicture;
    @NotNull
    private String username;
    @NotNull
    private String firstName;
    @NotNull
    private String lastName;
    @NotNull
    private String email;
    @NotNull
    private String password;
    private String biography;
    @NotNull
    private LocalDate dateOfBirth;
    private String telephoneNumber;
    private String city;
    private String country;
}
