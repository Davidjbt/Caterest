package com.david.caterest.service;

import com.david.caterest.entity.User;
import com.david.caterest.repository.UserRepository;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class UserServiceImpl implements UserService {

    private final UserRepository userRepository;

    public UserServiceImpl(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    @Override
    public List<User> findAll() {
        return userRepository.findAll();
    }

    @Override
    public User saveUser(User user) {
        return userRepository.save(user);
    }

    @Override
    public User findUserById(Long id) {
        Optional<User> userOptional = userRepository.findById(id);

        //todo Implement 404 page using a handler
        if (userOptional.isEmpty()) return null;

        return userOptional.get();
    }

    @Override
    public User findUserByUsernameAndPassword(String username, String password) {
        Optional<User> userOptional = userRepository.findUserByUsernameAndPassword(username, password);

        //todo Implement 404 page using a handler
        if (userOptional.isEmpty()) return null;

        return userOptional.get();
    }

    public User findUserByUsername(String username) {
        Optional<User> userOptional = userRepository.findUserByUsername(username);

        return null;
    }


}
