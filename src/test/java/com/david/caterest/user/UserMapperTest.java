package com.david.caterest.user;


import com.david.caterest.picture.Picture;
import com.david.caterest.user.dto.UserProfileDto;
import com.david.caterest.user.dto.UserSignUpDto;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import java.time.LocalDate;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

class UserMapperTest {

    private UserMapper mapper;

    @BeforeEach
    void setUp() {
        mapper = new UserMapperImpl();
    }

    @Test
    public void shouldMapUserSignUpDtoToUser() {
        UserSignUpDto dto = new UserSignUpDto();
        dto.setUsername("ElJuan");
        dto.setFirstName("Juan");
        dto.setLastName("Smith");
        dto.setEmail("juan.smith@gmail.com");
        dto.setPassword("juan123");
        dto.setBiography("Tester");
        dto.setDateOfBirth(LocalDate.of(1990, 1, 1));
        dto.setTelephoneNumber("123456789");
        dto.setCity("Madrid");
        dto.setCountry("Spain");

        User user = mapper.toUser(dto);

        assertThat(user.getDisplayName()).isEqualTo(dto.getUsername());
        assertThat(user.getCountry()).isEqualTo(dto.getCountry());
        assertThat(user.getFirstName()).isEqualTo(dto.getFirstName());
        assertThat(user.getLastName()).isEqualTo(dto.getLastName());
        assertThat(user.getEmail()).isEqualTo(dto.getEmail());
        assertThat(user.getPassword()).isNull();
        assertThat(user.getBiography()).isEqualTo(dto.getBiography());
        assertThat(user.getCity()).isEqualTo(dto.getCity());
        assertThat(user.getTelephoneNumber()).isEqualTo(dto.getTelephoneNumber());
        assertThat(user.getDateOfBirth()).isEqualTo(dto.getDateOfBirth());
    }

    @Test
    public void shouldReturnNullWhenSignUpDtoIsNull() {
        assertThat(mapper.toUser(null)).isNull();
    }

    @Test
    public void shouldMapUserToUserProfileDto() {
        User user = new User();
        user.setId(1L);
        user.setDisplayName("ElJuan");
        user.setBiography("Tester");

        Picture picture = new Picture();
        picture.setId(1L);

        user.setPictures(List.of(picture));

        UserProfileDto dto = mapper.toUserProfileDto(user);

        assertThat(dto.getId()).isEqualTo(user.getId());
        assertThat(dto.getDisplayName()).isEqualTo(user.getDisplayName());
        assertThat(dto.getBiography()).isEqualTo(user.getBiography());
        assertThat(dto.getPictures()).containsAll(user.getPictures().stream().map(p -> p.getId()).toList());
    }

    @Test
    public void shouldReturnNullWhenUserIsNull() {
        assertThat(mapper.toUserProfileDto(null)).isNull();
    }

}
