package com.david.caterest.dto.picture;

import com.david.caterest.dto.user.UserDto;
import lombok.Data;
import java.time.LocalDateTime;

@Data
public class PictureDetailsDto {
    private Long id;
    private UserDto user;
    private String description;
    private LocalDateTime dateOfPost;
}
