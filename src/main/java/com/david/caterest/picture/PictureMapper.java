package com.david.caterest.picture;

import com.david.caterest.picture.dto.PictureDetailsDto;
import com.david.caterest.picture.dto.PicturePostDto;
import org.mapstruct.Mapper;
import org.mapstruct.ReportingPolicy;

@Mapper(componentModel = "spring", unmappedTargetPolicy = ReportingPolicy.IGNORE)
public interface PictureMapper {

    PictureDetailsDto toPicturePostDto(Picture picture);
    Picture toPicture(PicturePostDto picturePostDto);
}
