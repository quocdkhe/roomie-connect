package com.example.roomie_connect_BE.mapper;

import com.example.roomie_connect_BE.dto.request.ChatRoomRequest;
import com.example.roomie_connect_BE.dto.response.ChatRoomResponse;
import com.example.roomie_connect_BE.entity.ChatRoom;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface ChatRoomMapper {
    ChatRoomResponse toChatRoomResponseDTO(ChatRoom chatRoom);

    ChatRoom toChatRoom(ChatRoomRequest chatRoomRequestDTO);
}
