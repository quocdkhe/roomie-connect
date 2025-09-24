package com.example.roomie_connect_BE.mapper;


import com.example.roomie_connect_BE.dto.request.PostRequest;
import com.example.roomie_connect_BE.dto.response.PostResponse;
import com.example.roomie_connect_BE.entity.Post;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface PostMapper {
    PostResponse toPostResponseDTO(Post post);
    Post toPost(PostRequest postRequestDTO);
}
