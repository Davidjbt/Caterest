package com.david.caterest.user;

import com.david.caterest.user.dto.UserLogInDto;
import com.david.caterest.user.dto.UserProfileDto;
import com.david.caterest.user.dto.UserSignUpDto;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.Mappings;

@Mapper(componentModel = "spring")
public interface UserMapper {

    @Mappings({
            @Mapping(source = "username", target = "displayName"),
            @Mapping(target = "profilePicture", ignore = true),
            @Mapping(target = "password", ignore = true),
            @Mapping(target = "id", ignore = true),
            @Mapping(target = "role", ignore = true),
            @Mapping(target = "pictures", ignore = true)
    })
    User toUser(UserSignUpDto userDto);
    @Mapping(source = "email", target = "displayName")
    User toUser(UserLogInDto userDto);

    UserSignUpDto toUserLogInDto(User user);
    UserLogInDto toUserSignUpDto(User user);
    UserProfileDto toUserProfileDto(User user);

}
