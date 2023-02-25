package com.david.caterest.dto;

import lombok.Data;

import java.util.List;

@Data
public class UserDto {

    private long id;
    private Byte[] profilePicture;
    private String username;
    private String biography;
    private List<PictureDto> pictures;
}
