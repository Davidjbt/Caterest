package com.david.caterest.user;

import com.david.caterest.user.dto.UserProfileDto;
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

    private final UserRepository userRepository;
    private final UserMapper userMapper;

    @Override
    public UserProfileDto findUserProfileDetailsByDisplayName(String displayName) {
        Optional<User> user = userRepository.findByDisplayName(displayName);

        if (user.isEmpty()) return null; //todo handle better

        return userMapper.toUserProfileDto(user.get());
    }

    @Override
    public List<UserProfileDto> findUsersByMatchingDisplayName(String displayName) {
        return userRepository.findByDisplayNameContainsIgnoreCase(displayName).stream()
                .map(user -> userMapper.toUserProfileDto(user))
                .toList();
    }

    @Transactional
    public void setUserProfilePicture(User user, MultipartFile file) {

        try {
            // todo Handle if either user or file are null.
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
    public void renderProfilePicture(String id, HttpServletResponse response) throws IOException {
        Optional<User> user = userRepository.findById(Long.valueOf(id));

        if (user.isEmpty()) return; // todo handle better

        ImageRender.renderImage(response, user.get().getProfilePicture());
    }

}
