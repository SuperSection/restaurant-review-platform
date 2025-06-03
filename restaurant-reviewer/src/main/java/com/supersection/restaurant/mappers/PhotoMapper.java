package com.supersection.restaurant.mappers;

import com.supersection.restaurant.domain.dtos.PhotoDTO;
import com.supersection.restaurant.domain.entities.Photo;
import org.mapstruct.Mapper;
import org.mapstruct.ReportingPolicy;

@Mapper(componentModel = "spring", unmappedTargetPolicy = ReportingPolicy.IGNORE)
public interface PhotoMapper {

    PhotoDTO toDto(Photo photo);

}
