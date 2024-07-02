package com.david.caterest.picture;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import java.time.LocalDateTime;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

@DataJpaTest
class PictureRepositoryTest {

    @Autowired
    private PictureRepository pictureRepository;

    @Test
    public void shouldFindAllPicturesByDescendingDateOfPost() {
        // Given
        Picture oldesPicture = new Picture();
        Picture secondOldestPicture = new Picture();
        Picture newestPicture = new Picture();

        oldesPicture.setDateOfPost(LocalDateTime.of(2024, 2, 2, 15, 33));
        secondOldestPicture.setDateOfPost(LocalDateTime.of(2024, 4, 2, 15, 33));
        newestPicture.setDateOfPost(LocalDateTime.of(2024, 6, 2, 15, 33));

        pictureRepository.save(oldesPicture);
        pictureRepository.save(secondOldestPicture);
        pictureRepository.save(newestPicture);
        
        // When
        List<Picture> pictures = pictureRepository.findAllByOrderByDateOfPostDesc();

        // Then
        assertThat(pictures.size()).isEqualTo(3);
        assertThat(pictures.get(0).getDateOfPost()).isEqualTo(newestPicture.getDateOfPost());
        assertThat(pictures.get(1).getDateOfPost()).isEqualTo(secondOldestPicture.getDateOfPost());
        assertThat(pictures.get(2).getDateOfPost()).isEqualTo(oldesPicture.getDateOfPost());
    }   

    @Test
    public void shouldReturnEmptyListWhenNoPictureSaved() {
        assertThat(pictureRepository.findAllByOrderByDateOfPostDesc()).isEmpty();
    }

}