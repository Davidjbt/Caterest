package com.david.caterest.user;

import com.david.caterest.user.dto.UserProfileDto;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.web.multipart.MultipartFile;
import java.io.IOException;
import java.util.List;

public interface UserService {
    UserProfileDto findUserProfileDetailsByDisplayName(String displayName);
    List<UserProfileDto> findUsersByMatchingDisplayName(String displayName);
    void setUserProfilePicture(User user, MultipartFile file);
    void renderProfilePicture(String id, HttpServletResponse response) throws IOException;
}
