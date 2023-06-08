package com.david.caterest.dto.user;

import jakarta.validation.constraints.NotNull;
import lombok.Data;

@Data
public class UserLogInDto {

    @NotNull
    private String usernameOrEmail;
    @NotNull
    private String password;
}
