package com.microservices.projectfinal.mapper;

import com.microservices.projectfinal.dto.TimeDTO;
import com.microservices.projectfinal.entity.DimTimeEntity;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface TimeMapper {
    TimeDTO toTimeDTO(DimTimeEntity dimTimeEntity);
}
