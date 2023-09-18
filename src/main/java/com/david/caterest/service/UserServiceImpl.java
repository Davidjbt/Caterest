package com.david.caterest.service;

import com.david.caterest.dto.user.UserDto;
import com.david.caterest.dto.user.UserProfileDto;
import com.david.caterest.dto.user.UserSignUpDto;
import com.david.caterest.entity.Role;
import com.david.caterest.entity.User;
import com.david.caterest.mapper.UserMapper;
import com.david.caterest.repository.PictureRepository;
import com.david.caterest.repository.UserRepository;
import com.david.caterest.util.ImageRender;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;
import java.io.IOException;
import java.util.List;
import java.util.Optional;

@Service
@Slf4j
@RequiredArgsConstructor
public class UserServiceImpl implements UserService {

    private final PictureRepository pictureRepository;
    private final UserRepository userRepository;
    private final UserMapper userMapper;

    @Override
    public List<UserDto> findAll() {
//        return userRepository.findAll().stream().map(userMapper::entityToDto).collect(Collectors.toList());
        return null;
    }

    public User addUser(UserSignUpDto userSignUpDto) {
        User user = userMapper.toUser(userSignUpDto);

        user.setRole(Role.USER);

        return userRepository.save(user);
    }

    @Override
    public UserDto findUserById(Long id) {
        Optional<User> userOptional = userRepository.findById(id);

        //todo Implement 404 page using a handler
        if (userOptional.isEmpty()) return null;

//        return userMapper.entityToDto(userOptional.get());
        return null;
    }

    public UserDto findUserByUsername(String username) {
        Optional<User> userOptional = userRepository.findByDisplayName(username);

//        return userMapper.entityToDto(userOptional.orElse(null));
        return null;
    }

    public User findUserByEmail(String email) {
        Optional<User> userOptional = userRepository.findByEmail(email);

        return userOptional.orElse(null);
    }

    @Transactional
    public void setUserProfilePicture(User user, MultipartFile file) {

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

    @Override
    public boolean doesUserExist(UserSignUpDto user) {
        return userRepository.existsByEmail(user.getEmail()); // or username since the email will be used as the username from a spring security context.
    }

    @Override
    public void renderProfilePicture(String id, HttpServletResponse response) throws IOException {
        Optional<User> user = userRepository.findById(Long.valueOf(id));

        if (user.isEmpty()) return; // todo handle better

        ImageRender.renderImage(response, user.get().getProfilePicture());
    }

    @Override
    public UserProfileDto findUserProfileDetailsByDisplayName(String displayName) {
        Optional<User> user = userRepository.findByDisplayName(displayName);

        if (user.isEmpty()) return null; //todo handle better

        return userMapper.toUserProfileDto(user.get());
    }

}
