package com.david.caterest.dto.user;

import com.david.caterest.dto.picture.PictureIdDto;
import lombok.Data;
import java.util.List;

@Data
public class UserProfileDto {

    private long id;
    private String displayName;
    private String biography;
    private List<PictureIdDto> pictures;
}
