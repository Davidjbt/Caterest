package com.david.caterest.picture;

import com.david.caterest.config.JwtService;
import com.david.caterest.picture.dto.PictureDetailsDto;
import com.david.caterest.picture.dto.PicturePostDto;
import com.david.caterest.user.User;
import com.david.caterest.user.UserRepository;
import jakarta.servlet.http.Cookie;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.mock.web.MockHttpServletRequest;
import org.springframework.mock.web.MockHttpServletResponse;
import org.springframework.mock.web.MockMultipartFile;

import java.io.IOException;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

import static org.assertj.core.api.Assertions.*;
import static org.mockito.Mockito.*;

class PictureServiceTest {

    @Mock
    private PictureRepository pictureRepository;
    @Mock
    private UserRepository userRepository;
    @Mock
    private PictureMapper pictureMapper;
    @Mock
    private JwtService jwtService;
    @InjectMocks
    private PictureServiceImpl pictureService;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    public void shouldFindAllPicturesIdOnOrderByDescendingDateOfPost() {
        // Given
        Picture newestPicture = new Picture();
        Picture secondOldestPicture = new Picture();
        Picture oldestPicture = new Picture();

        newestPicture.setId(1L);
        newestPicture.setDateOfPost(LocalDateTime.of(2024, 6, 15, 20, 30));
        secondOldestPicture.setId(2L);
        secondOldestPicture.setDateOfPost(LocalDateTime.of(2024, 6, 13, 15, 15));
        oldestPicture.setId(3L);
        oldestPicture.setDateOfPost(LocalDateTime.of(2024, 6, 10, 10, 5));

        // Mock the calls
        when(pictureRepository.findAllByOrderByDateOfPostDesc()).thenReturn(List.of(newestPicture, secondOldestPicture, oldestPicture));

        // When
        List<Long> pictureIdsInOrder = pictureService.findAllPictureIdsOrderedByDateDesc();

        // Then
        assertThat(pictureIdsInOrder).containsExactly(newestPicture.getId(), secondOldestPicture.getId(), oldestPicture.getId());
        verify(pictureRepository, times(1)).findAllByOrderByDateOfPostDesc();
    }

    @Test
    public void shouldReturnEmptyListWhenNoPictureSaved() {
        // Mock the call
        when(pictureRepository.findAllByOrderByDateOfPostDesc()).thenReturn(List.of());

        // When
        List<Long> pictureIdsInOrder = pictureService.findAllPictureIdsOrderedByDateDesc();

        // Then
        assertThat(pictureIdsInOrder).isEmpty();
        verify(pictureRepository, times(1)).findAllByOrderByDateOfPostDesc();
    }

    @Test
    public void shouldFindPictureDetailsById() {
        // Given
        Picture picture = new Picture();
        picture.setId(1L);

        PictureDetailsDto dto = new PictureDetailsDto();
        dto.setId(1L);

        // Mock the calls
        when(pictureRepository.findById(1L)).thenReturn(Optional.of(picture));
        when(pictureMapper.toPicturePostDto(picture)).thenReturn(dto);

        // When
        PictureDetailsDto pictureDetailsDto = pictureService.findPictureDetailsById("1");

        // Then
        assertThat(pictureDetailsDto.getId()).isEqualTo(picture.getId());
        verify(pictureRepository, times(1)).findById(1L);
        verify(pictureMapper, times(1)).toPicturePostDto(picture);
    }

    @Test
    public void shouldReturnNullWhenPictureByIdNotFound() {
        // Mock call
        when(pictureRepository.findById(1L)).thenReturn(Optional.empty());

        // When
        PictureDetailsDto pictureDetailsDto =pictureService.findPictureDetailsById("1");

        // Then
        assertThat(pictureDetailsDto).isNull();
    }

    @Test
    public void shouldRenderPicture() {
        // Given
        Byte[] image = {1, 2 , 3, 4, 5};
        Picture picture = new Picture();
        picture.setId(1L);
        picture.setImage(image);

        MockHttpServletResponse response = new MockHttpServletResponse();

        // Mock the call
        when(pictureRepository.findById(1L)).thenReturn(Optional.of(picture));

        // When & Then
        assertThatCode(() -> pictureService.renderPicture("1", response)).doesNotThrowAnyException();
        assertThat(response.getContentType()).isEqualTo("image/jpeg");
        assertThat(response.getContentAsByteArray()).hasSize(image.length);
        for (int i = 0; i < image.length; i++) assertThat(response.getContentAsByteArray()[i]).isEqualTo(image[i]);
    }

    @Test
    public void shouldNotRenderPictureWhenPictureNotFound() {
        // Given
        MockHttpServletResponse response = new MockHttpServletResponse();

        // Mock the call
        when(pictureRepository.findById(1L)).thenReturn(Optional.empty());

        // When & Then
        assertThatThrownBy(() ->
                pictureService.renderPicture("1", response)
        ).isInstanceOf(IOException.class)
         .hasMessage("Picture not found");
    }

    @Test
    public void shouldSavePicture() {
        // Given
        PicturePostDto picturePostDto = new PicturePostDto();
        Picture picture = new Picture();
        byte[] content = "test".getBytes();
        MockMultipartFile image = new MockMultipartFile("file", "test.jpg", "image/jpeg", content);
        MockHttpServletRequest request = new MockHttpServletRequest();
        Cookie jwtCookie = new Cookie("token", "testValue");
        User user = new User();
        user.setEmail("test@test.com");
        request.setCookies(jwtCookie);

        // Mock the calls
        when(jwtService.extractUsername(jwtCookie.getValue())).thenReturn(user.getEmail());
        when(userRepository.findByEmail(user.getEmail())).thenReturn(Optional.of(user));
        when(pictureMapper.toPicture(picturePostDto)).thenReturn(picture);

        // When
        pictureService.savePicture(picturePostDto, image, request);

        // Then
        assertThat(picture.getImage()).hasSize(content.length);
        for (int i = 0; i < content.length; i++) assertThat(picture.getImage()[i]).isEqualTo(content[i]);
        assertThat(picture.getUser().getEmail()).isEqualTo(user.getEmail());
        assertThat(user.getPictures()).contains(picture);
        verify(pictureRepository, times(1)).save(picture);
        verify(userRepository, times(1)).findByEmail(user.getEmail());
        verify(userRepository, times(1)).save(user);
    }

}
