package com.example.roomie_connect_BE.mapper;

import com.example.roomie_connect_BE.dto.request.MatchRequestRequest;
import com.example.roomie_connect_BE.dto.response.MatchRequestResponse;
import com.example.roomie_connect_BE.entity.MatchRequest;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface MatchRequestMapper {
    MatchRequestResponse toMatchRequestResponseDTO(MatchRequest matchRequest);
    MatchRequest toMatchRequest(MatchRequestRequest matchRequestRequestDTO);
}
