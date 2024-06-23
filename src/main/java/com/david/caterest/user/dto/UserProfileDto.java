package com.david.caterest.user.dto;

import lombok.Data;
import java.util.List;

@Data
public class UserProfileDto {

    private long id;
    private String displayName;
    private String biography;
    private List<Long> pictures;
}
