package com.technokratos.adboard.utils.mapper;

import java.util.List;

import com.technokratos.adboard.dto.response.ChatResponse;
import com.technokratos.adboard.model.Chat;
import org.mapstruct.Mapper;

/**
 * @author d.gilfanova
 */
@Mapper(componentModel = "spring", uses = UserMapper.class)
public interface ChatMapper {

    ChatResponse toResponse(Chat chat);
    List<ChatResponse> toResponseList(List<Chat> chats);
}
