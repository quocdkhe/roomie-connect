package com.example.roomie_connect_BE.mapper;

import com.example.roomie_connect_BE.dto.request.LocationRequest;
import com.example.roomie_connect_BE.dto.response.LocationResponse;
import com.example.roomie_connect_BE.entity.Location;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface LocationMapper {
    LocationResponse toLocationResponseDTO(Location location);

    Location toLocation(LocationRequest locationRequestDTO);
}

