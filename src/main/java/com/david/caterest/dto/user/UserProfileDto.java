package com.david.caterest.dto.user;

import com.david.caterest.dto.PictureDto;
import lombok.Data;

import java.util.List;

@Data
public class UserProfileDto {

    private long id;
    private Byte[] profilePicture;
    private String displayName;
    private String biography;
    private List<PictureDto> pictures;
}
