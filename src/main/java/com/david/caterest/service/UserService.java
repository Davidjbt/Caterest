package com.david.caterest.service;

import com.david.caterest.entity.User;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

public interface UserService {
    List<User> findAll();
    User saveUser(User user);
    User findUserById(Long id);
    User findUserByUsernameAndPassword(String username, String password);
    User findUserByUsername(String username);
    User findUserByEmail(String username);
    void saveImageFile(User user, MultipartFile file);
//    Boolean checkIfExits(User user);
}
