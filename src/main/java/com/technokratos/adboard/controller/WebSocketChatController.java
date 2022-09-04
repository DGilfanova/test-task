package com.technokratos.adboard.controller;

import java.security.Principal;
import javax.validation.Valid;

import com.technokratos.adboard.dto.request.ChatMessageRequest;
import com.technokratos.adboard.model.ChatMessage;
import com.technokratos.adboard.security.details.UserDetailsImpl;
import com.technokratos.adboard.service.ChatService;
import lombok.RequiredArgsConstructor;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.handler.annotation.Payload;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;

/**
 * @author d.gilfanova
 */
@Controller
@RequiredArgsConstructor
public class WebSocketChatController {

    private final ChatService chatService;

    private final SimpMessagingTemplate messagingTemplate;

    @MessageMapping("/chat")
    public void processMessage(@Payload @Valid ChatMessageRequest chatMessageRequest,
        @AuthenticationPrincipal UserDetailsImpl userDetails) {
        ChatMessage savedMessage = chatService.saveChatMessage(chatMessageRequest,
            userDetails.getUser());

        messagingTemplate.convertAndSendToUser(
            chatMessageRequest.getRecipientId().toString(),"/messages/queue",
            chatService.createChatNotification(savedMessage));
    }
}
