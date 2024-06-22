package com.david.caterest.picture;

import com.david.caterest.picture.dto.PictureDetailsDto;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.Mappings;

@Mapper(componentModel = "spring")
public interface PictureMapper {

    PictureDetailsDto toPicturePostDto(Picture picture);

    @Mappings({
            @Mapping(target = "user", ignore = true),
            @Mapping(target = "image", ignore = true),
            @Mapping(target = "dateOfPost", ignore = true),
            @Mapping(target = "id", ignore = true),
            @Mapping(target = "comments", ignore = true)
    })
    Picture toPicture(PictureDetailsDto picturePostDto);
}
