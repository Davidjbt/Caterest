package com.david.caterest.user;

import com.david.caterest.user.dto.UserDto;
import com.david.caterest.user.dto.UserProfileDto;
import com.david.caterest.user.dto.UserSignUpDto;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.web.multipart.MultipartFile;
import java.io.IOException;
import java.util.List;

public interface UserService {
    List<UserDto> findAll();
    User addUser(UserSignUpDto user);
    UserDto findUserById(Long id);
    UserDto findUserByUsername(String username);
    User findUserByEmail(String username);
    void setUserProfilePicture(User user, MultipartFile file);
//    Boolean checkIfExits(User user);
    boolean doesUserExist(UserSignUpDto user); //todo change to UserDTO or User

    void renderProfilePicture(String id, HttpServletResponse response) throws IOException;

    UserProfileDto findUserProfileDetailsByDisplayName(String displayName);
    List<UserProfileDto> findMatchingUsers(String query);
}
