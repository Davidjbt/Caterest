package com.david.caterest.user;

import com.david.caterest.picture.Picture;
import com.david.caterest.user.dto.UserProfileDto;
import com.david.caterest.user.dto.UserSignUpDto;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.Mappings;
import org.mapstruct.ReportingPolicy;
import java.util.List;

@Mapper(componentModel = "spring", unmappedTargetPolicy = ReportingPolicy.IGNORE)
public interface UserMapper {

    @Mappings({
            @Mapping(source = "username", target = "displayName"),
            @Mapping(target = "password", ignore = true)
    })
    User toUser(UserSignUpDto userDto);

    @Mapping(source = "pictures", target = "pictures")
    UserProfileDto toUserProfileDto(User user);

    default List<Long> map(List<Picture> pictures) {
        return pictures.stream()
                .map(Picture::getId)
                .toList();
    }

}
