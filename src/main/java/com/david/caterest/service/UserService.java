package com.david.caterest.service;

import com.david.caterest.entity.User;

import java.util.List;

public interface UserService {
    List<User> findAll();
    User findUserById(Long id);
}
