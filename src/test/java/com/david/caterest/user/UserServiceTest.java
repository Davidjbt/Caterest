package com.david.caterest.user;

import com.david.caterest.user.dto.UserProfileDto;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.mock.web.MockMultipartFile;
import java.io.IOException;
import java.util.List;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.*;

class UserServiceTest {

    @Mock
    private UserRepository userRepository;
    @Mock
    private UserMapper userMapper;
    @InjectMocks
    private UserServiceImpl userService;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    public void shouldFindUserProfileByDisplayName() {
        // Given
        String displayName = "John Doe";
        User user = User.builder()
                .displayName(displayName)
                .build();
        UserProfileDto dto = new UserProfileDto();
        dto.setDisplayName(displayName);

        // Mock the calls
        when(userRepository.findByDisplayName(displayName)).thenReturn(Optional.of(user));
        when(userMapper.toUserProfileDto(user)).thenReturn(dto);

        // When
        UserProfileDto userProfileDto = userService.findUserProfileDetailsByDisplayName(displayName);

        // Then
        assertThat(userProfileDto.getDisplayName()).isEqualTo(displayName);
        verify(userRepository, times(1)).findByDisplayName(displayName);
        verify(userMapper, times(1)).toUserProfileDto(user);
    }

    @Test
    public void shouldNotFindUserProfileWhenGivenInvalidDisplayName() {
        // Given
        String displayName = "John Doe";

        // Mock the calls
        when(userRepository.findByDisplayName(displayName)).thenReturn(Optional.empty());

        // When
        UserProfileDto dto = userService.findUserProfileDetailsByDisplayName(displayName);

        // Then
        assertThat(dto).isNull();
        verify(userRepository, times(1)).findByDisplayName(displayName);
    }

    @Test
    public void shouldFindUsersWithMatchingDisplayName() {
        // Given
        String displayName = "John";

        User user1 = User.builder().displayName("John Doe").build();
        UserProfileDto dto1 = new UserProfileDto();
        dto1.setDisplayName(user1.getDisplayName());

        User user2 = User.builder().displayName("jOHn Smith").build();
        UserProfileDto dto2 = new UserProfileDto();
        dto2.setDisplayName(user2.getDisplayName());

        // Mock the calls
        when(userRepository.findByDisplayNameContainsIgnoreCase(displayName)).thenReturn(List.of(user1, user2));
        when(userMapper.toUserProfileDto(user1)).thenReturn(dto1);
        when(userMapper.toUserProfileDto(user2)).thenReturn(dto2);

        // When
        List<UserProfileDto> dtos = userService.findUsersByMatchingDisplayName(displayName);

        // Then
        assertThat(dtos.size()).isEqualTo(2);
        assertThat(dtos.get(0).getDisplayName()).containsIgnoringCase(displayName);
        assertThat(dtos.get(1).getDisplayName()).containsIgnoringCase(displayName);
        verify(userRepository, times(1)).findByDisplayNameContainsIgnoreCase(displayName);
        verify(userMapper, times(1)).toUserProfileDto(user1);
        verify(userMapper, times(1)).toUserProfileDto(user2);
        verify(userMapper, times(2)).toUserProfileDto(any());
    }

    @Test
    public void shouldReturnEmptyListWhenGivenUnmatchedDisplayName() {
        // Given
        String displayName = "John Doe";

        // Mock the calls
        when(userRepository.findByDisplayNameContainsIgnoreCase(displayName)).thenReturn(List.of());

        // When
        List<UserProfileDto> dtos = userService.findUsersByMatchingDisplayName(displayName);

        // Then
        assertThat(dtos).isEmpty();
        verify(userRepository, times(1)).findByDisplayNameContainsIgnoreCase(displayName);
        verify(userMapper, never()).toUserProfileDto(any());
    }

    @Test
    public void shouldSetUserProfilePicture() throws IOException {
        // Given
        MockMultipartFile file = new MockMultipartFile("file", "test.jpg", "image/jpeg", "test".getBytes());
        User user = User.builder()
                .displayName("John Doe")
                .build();

        // When
        userService.setUserProfilePicture(user, file);

        // Then
        assertThat(user.getProfilePicture()).hasSize(file.getBytes().length);
        for (int i = 0; i < file.getBytes().length; i++)
            assertThat(user.getProfilePicture()).contains(file.getBytes()[i]);
    }

    @Test
    public void shouldThrowIOExceptionWhenGettingFileBytes() throws IOException {
        // Given
        MockMultipartFile file = mock(MockMultipartFile.class);
        User user = User.builder()
                .displayName("John Doe")
                .build();

        // Mock the calls
        when(file.getBytes()).thenThrow(new IOException());

        // When
        userService.setUserProfilePicture(user, file); // Stack trace is printed

        // Then
        assertThat(user.getProfilePicture()).isNull();
        verify(file, times(1)).getBytes();
    }

}
