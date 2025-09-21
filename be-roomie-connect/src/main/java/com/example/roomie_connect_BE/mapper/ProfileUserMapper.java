package com.example.roomie_connect_BE.mapper;


import com.example.roomie_connect_BE.dto.request.ProfileUserRequest;
import com.example.roomie_connect_BE.dto.response.ProfileUserResponse;
import com.example.roomie_connect_BE.entity.ProfileUser;
import org.mapstruct.Mapper;
import org.mapstruct.MappingTarget;
import org.mapstruct.NullValuePropertyMappingStrategy;

@Mapper(componentModel = "spring" , nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE)
public interface ProfileUserMapper {
    ProfileUser toProfileUser(ProfileUserRequest profileUserRequest);

    ProfileUserResponse toProfileUserResponse(ProfileUser profileUser);

    ProfileUser updateProfileUserFromRequest(ProfileUserRequest request, @MappingTarget ProfileUser profileUser);
}
