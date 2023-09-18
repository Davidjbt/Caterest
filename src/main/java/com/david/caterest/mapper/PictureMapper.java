package com.david.caterest.mapper;

import com.david.caterest.dto.PicturePostDto;
import com.david.caterest.entity.Picture;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface PictureMapper {

    PicturePostDto toPicturePostDto(Picture picture);
}
