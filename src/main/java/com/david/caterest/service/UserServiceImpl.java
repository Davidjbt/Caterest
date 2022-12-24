package com.david.caterest.service;

import com.david.caterest.entity.User;
import com.david.caterest.repository.UserRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.List;
import java.util.Optional;

@Service
@Slf4j
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

        return userOptional.orElse(null);
    }

    public User findUserByEmail(String email) {
        Optional<User> userOptional = userRepository.findUserByEmail(email);

        return userOptional.orElse(null);
    }

    @Override
    @Transactional
    public void saveImageFile(User user, MultipartFile file) {

        try {
            // No need to deal with null case as this was checked before.
            Byte[] byteObjects = new Byte[file.getBytes().length];

            int i = 0;
            for (byte b : file.getBytes()){
                byteObjects[i++] = b;
            }

            user.setProfilePicture(byteObjects);
        } catch (IOException e) {
            //todo handle better
            log.error("Error occurred", e);

            e.printStackTrace();
        }
    }
}
