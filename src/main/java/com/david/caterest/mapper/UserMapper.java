package com.david.caterest.mapper;

import com.david.caterest.dto.user.UserLogInDto;
import com.david.caterest.dto.user.UserSignUpDto;
import com.david.caterest.entity.User;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring")
public interface UserMapper {

    @Mapping(source = "username", target = "displayName")
    User toUser(UserSignUpDto userDto);
    @Mapping(source = "username", target = "displayName")
    User toUser(UserLogInDto userDto);

    UserSignUpDto toUserLogInDto(User user);
    UserLogInDto toUserSignUpDto(User user);

}
