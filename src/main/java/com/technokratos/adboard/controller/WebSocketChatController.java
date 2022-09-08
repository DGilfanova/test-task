package com.technokratos.adboard.controller;

import java.security.Principal;
import javax.validation.Valid;

import com.technokratos.adboard.dto.request.ChatMessageRequest;
import com.technokratos.adboard.model.ChatMessage;
import com.technokratos.adboard.model.User;
import com.technokratos.adboard.service.ChatService;
import lombok.RequiredArgsConstructor;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.handler.annotation.Payload;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.stereotype.Controller;

import static com.technokratos.adboard.constant.Constant.MESSAGE_DESTINATION;

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
        Principal principal) {
        ChatMessage savedMessage = chatService.saveChatMessage(chatMessageRequest,
            ((User) ((UsernamePasswordAuthenticationToken) principal).getPrincipal()));

        messagingTemplate.convertAndSendToUser(
            chatMessageRequest.getRecipientId().toString(),MESSAGE_DESTINATION,
            chatService.createChatNotification(savedMessage));
    }
}
