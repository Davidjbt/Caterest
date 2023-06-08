package com.david.caterest.mapper;

import com.david.caterest.dto.user.UserLogInDto;
import com.david.caterest.dto.user.UserSignUpDto;
import com.david.caterest.entity.User;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring")
public interface UserMapper {

    @Mapping(source = "username", target = "displayName")
    User userDtoToUser(UserSignUpDto userDto);
    User userDtoToUser(UserLogInDto userDto);

    UserSignUpDto userToUserLogInDto(User user);
    UserLogInDto userToUserSignUpDto(User user);

}
