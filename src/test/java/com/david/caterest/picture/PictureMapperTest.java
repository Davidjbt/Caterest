package com.david.caterest.picture;

import com.david.caterest.picture.dto.PictureDetailsDto;
import com.david.caterest.picture.dto.PicturePostDto;
import com.david.caterest.user.User;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import java.time.LocalDateTime;

import static org.assertj.core.api.Assertions.assertThat;

class PictureMapperTest {

    private PictureMapper mapper;

    @BeforeEach
    void setUp() {
        mapper = new PictureMapperImpl();
    }

    @Test
    public void shouldMapPictureToPictureDetailsDto() {
        // Given
        User user = User.builder().id(1L).displayName("John Doe").build();
        Picture picture = new Picture();
        picture.setId(1L);
        picture.setUser(user);
        picture.setDescription("This is a picture");
        picture.setDateOfPost(LocalDateTime.now());

        // When
        PictureDetailsDto pictureDto = mapper.toPicturePostDto(picture);

        // Then
        assertThat(pictureDto.getId()).isEqualTo(picture.getId());
        assertThat(pictureDto.getUser().getId()).isEqualTo(picture.getUser().getId());
        assertThat(pictureDto.getUser().getDisplayName()).isEqualTo(picture.getUser().getDisplayName());
        assertThat(pictureDto.getDescription()).isEqualTo(picture.getDescription());
        assertThat(pictureDto.getDateOfPost()).isEqualTo(picture.getDateOfPost());
    }

    @Test
    public void shouldReturnNullWhenPictureIsNull() {
        // When & Then
        assertThat(mapper.toPicturePostDto(null)).isNull();
    }

    @Test
    public void shouldMapPicturePostDtoToPicture() {
        // Given
        PicturePostDto pictureDto = new PicturePostDto();

        // When
        Picture picture = mapper.toPicture(pictureDto);

        // Then
        assertThat(pictureDto.getDescription()).isEqualTo(picture.getDescription());
    }

    @Test
    public void shouldReturnNullWhenPicturePostDtoIsNull() {
        // When & Then
        assertThat(mapper.toPicture(null)).isNull();
    }

}