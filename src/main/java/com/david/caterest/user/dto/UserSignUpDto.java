package com.david.caterest.user.dto;

import jakarta.validation.constraints.NotNull;
import lombok.Data;
import java.time.LocalDate;

@Data
public class UserSignUpDto {

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
