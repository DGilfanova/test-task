package com.technokratos.adboard.controller;

import java.util.List;
import java.util.UUID;

import com.technokratos.adboard.api.ChatApi;
import com.technokratos.adboard.dto.response.ChatMessageResponse;
import com.technokratos.adboard.dto.response.ChatResponse;
import com.technokratos.adboard.security.details.UserDetailsImpl;
import com.technokratos.adboard.service.ChatService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author d.gilfanova
 */
@Slf4j
@RestController
@RequiredArgsConstructor
public class ChatController implements ChatApi<UserDetailsImpl> {

    private final ChatService chatService;

    @Override
    public List<ChatResponse> getUserChats(@AuthenticationPrincipal UserDetailsImpl userDetails) {
        return chatService.getUserChats(userDetails.getUser());
    }

    @Override
    public List<ChatMessageResponse> getChatMessages(UUID chatId,
        @AuthenticationPrincipal UserDetailsImpl userDetails) {
        return chatService.getChatMessages(chatId, userDetails.getUser());
    }

    @Override
    public Long getCountChatNewMessages(UUID chatId,
        @AuthenticationPrincipal UserDetailsImpl userDetails) {
        return chatService.getChatNewMessageCount(chatId, userDetails.getUser());
    }
}
