package com.david.caterest.picture.dto;

import com.david.caterest.user.dto.UserDto;
import lombok.Data;
import java.time.LocalDateTime;

@Data
public class PictureDetailsDto {
    private Long id;
    private UserDto user;
    private String description;
    private LocalDateTime dateOfPost;
}
