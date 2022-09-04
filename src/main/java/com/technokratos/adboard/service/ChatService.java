package com.technokratos.adboard.service;

import java.util.List;
import java.util.UUID;

import com.technokratos.adboard.dto.request.ChatMessageRequest;
import com.technokratos.adboard.dto.response.ChatMessageResponse;
import com.technokratos.adboard.dto.response.ChatNotification;
import com.technokratos.adboard.dto.response.ChatResponse;
import com.technokratos.adboard.model.ChatMessage;
import com.technokratos.adboard.model.User;

/**
 * @author d.gilfanova
 */
public interface ChatService {
    ChatMessage saveChatMessage(ChatMessageRequest messageRequest, User authUser);
    ChatNotification createChatNotification(ChatMessage chatMessage);
    List<ChatResponse> getUserChats(User authUser);
    List<ChatMessageResponse> getChatMessages(UUID chatId, User authUser);
    Long getChatNewMessageCount(UUID chatId, User authUser);
}
