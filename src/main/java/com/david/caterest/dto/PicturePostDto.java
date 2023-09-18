package com.david.caterest.dto;

import com.david.caterest.dto.user.UserDto;
import lombok.Data;
import java.time.LocalDateTime;

@Data
public class PicturePostDto {
    private Long id;
    private UserDto user;
    private String description;
    private LocalDateTime dateOfPost;
}
