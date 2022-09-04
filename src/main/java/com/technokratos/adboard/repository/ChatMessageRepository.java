package com.technokratos.adboard.repository;

import java.util.List;
import java.util.UUID;

import com.technokratos.adboard.dto.enums.MessageStatus;
import com.technokratos.adboard.model.Chat;
import com.technokratos.adboard.model.ChatMessage;
import com.technokratos.adboard.model.User;
import org.springframework.data.jpa.repository.JpaRepository;

/**
 * @author d.gilfanova
 */
public interface ChatMessageRepository extends JpaRepository<ChatMessage, UUID> {
    List<ChatMessage> findAllByChatId(UUID chatId);
    List<ChatMessage> findAllByChatIdAndSenderAndStatus(UUID chatId, User sender,
        MessageStatus status);
    Long countByChatAndSenderAndStatus(Chat chat, User sender, MessageStatus status);
}
