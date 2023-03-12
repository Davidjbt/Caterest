package com.david.caterest.service;

import com.david.caterest.dto.UserDto;
import com.david.caterest.entity.User;
import com.david.caterest.mapper.UserMapper;
import com.david.caterest.repository.UserRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
@Slf4j
public class UserServiceImpl implements UserService {

    private final UserRepository userRepository;
    private final UserMapper userMapper;

    public UserServiceImpl(UserRepository userRepository, UserMapper userMapper) {
        this.userRepository = userRepository;
        this.userMapper = userMapper;
    }

    @Override
    public List<UserDto> findAll() {
        return userRepository.findAll().stream().map(userMapper::entityToDto).collect(Collectors.toList());
    }

    @Override
    public User saveUser(User user) {
        return userRepository.save(user);
    }

    @Override
    public UserDto findUserById(Long id) {
        Optional<User> userOptional = userRepository.findById(id);

        //todo Implement 404 page using a handler
        if (userOptional.isEmpty()) return null;

        return userMapper.entityToDto(userOptional.get());
    }

    public UserDto findUserByUsername(String username) {
        Optional<User> userOptional = userRepository.findUserByUsername(username);

        return userMapper.entityToDto(userOptional.orElse(null));
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
