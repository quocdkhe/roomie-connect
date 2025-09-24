package com.example.roomie_connect_BE.mapper;

import com.example.roomie_connect_BE.dto.request.MessageRequest;
import com.example.roomie_connect_BE.dto.response.MessageResponse;
import com.example.roomie_connect_BE.entity.Message;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface MessageMapper {
    MessageResponse toMessageResponseDTO(Message message);

    Message toMessage(MessageRequest messageRequestDTO);
}
