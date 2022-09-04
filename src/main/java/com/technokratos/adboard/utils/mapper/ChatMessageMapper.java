package com.technokratos.adboard.utils.mapper;

import java.util.List;

import com.technokratos.adboard.dto.response.ChatMessageResponse;
import com.technokratos.adboard.model.ChatMessage;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

/**
 * @author d.gilfanova
 */
@Mapper(componentModel = "spring", uses = UserMapper.class)
public interface ChatMessageMapper {

    ChatMessageResponse toResponse(ChatMessage chatMessage);
    List<ChatMessageResponse> toResponseList(List<ChatMessage> chatMessages);
}
