package com.david.caterest.service;

import com.david.caterest.dto.user.UserDto;
import com.david.caterest.dto.user.UserSignUpDto;
import com.david.caterest.entity.User;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

public interface UserService {
    List<UserDto> findAll();
    User addUser(UserSignUpDto user);
    UserDto findUserById(Long id);
    UserDto findUserByUsername(String username);
    User findUserByEmail(String username);
    void setUserProfilePicture(UserSignUpDto user, MultipartFile file);
//    Boolean checkIfExits(User user);
    boolean doesUserExist(UserSignUpDto user); //todo change to UserDTO or User
}
