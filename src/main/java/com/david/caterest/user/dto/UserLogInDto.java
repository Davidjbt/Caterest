package com.david.caterest.user.dto;

import jakarta.validation.constraints.NotNull;
import lombok.Data;

@Data
public class UserLogInDto {

    @NotNull
    private String email;
    @NotNull
    private String password;
}
