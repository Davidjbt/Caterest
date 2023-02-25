package com.david.caterest.mapper;

import com.david.caterest.dto.UserDto;
import com.david.caterest.entity.User;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface UserMapper {
    UserDto entityToDto(User user);
}
