package com.example.roomie_connect_BE.mapper;



import com.example.roomie_connect_BE.dto.request.UserRequest;
import com.example.roomie_connect_BE.dto.response.UserResponse;
import com.example.roomie_connect_BE.entity.User;
import org.mapstruct.Mapper;
import org.mapstruct.MappingTarget;
import org.mapstruct.NullValuePropertyMappingStrategy;

@Mapper(componentModel = "spring" , nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE)
public interface UserMapper {
    User toProfileUser(UserRequest userRequest);

    UserResponse toProfileUserResponse(User user);

    User updateProfileUserFromRequest(UserRequest request, @MappingTarget User user);
}
