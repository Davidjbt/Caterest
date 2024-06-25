package com.david.caterest.user.dto;

import jakarta.validation.constraints.NotNull;
import lombok.Data;

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
}
