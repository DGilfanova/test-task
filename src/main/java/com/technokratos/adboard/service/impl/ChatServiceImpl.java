package com.technokratos.adboard.service.impl;

import java.sql.Timestamp;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Objects;
import java.util.UUID;
import java.util.stream.Collectors;

import com.technokratos.adboard.dto.enums.MessageStatus;
import com.technokratos.adboard.dto.request.ChatMessageRequest;
import com.technokratos.adboard.dto.response.ChatMessageResponse;
import com.technokratos.adboard.dto.response.ChatNotification;
import com.technokratos.adboard.dto.response.ChatResponse;
import com.technokratos.adboard.exception.ChatNotFoundException;
import com.technokratos.adboard.exception.UserNotFoundException;
import com.technokratos.adboard.exception.UserUnavailableOperationException;
import com.technokratos.adboard.model.Chat;
import com.technokratos.adboard.model.ChatMessage;
import com.technokratos.adboard.model.User;
import com.technokratos.adboard.repository.ChatMessageRepository;
import com.technokratos.adboard.repository.ChatRepository;
import com.technokratos.adboard.repository.UserRepository;
import com.technokratos.adboard.service.ChatService;
import com.technokratos.adboard.utils.mapper.ChatMapper;
import com.technokratos.adboard.utils.mapper.ChatMessageMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import static com.technokratos.adboard.constant.Constant.MESSAGE_PART_END;
import static com.technokratos.adboard.constant.Constant.MESSAGE_PART_END_INDEX;
import static com.technokratos.adboard.constant.Constant.MESSAGE_PART_START_INDEX;
import static com.technokratos.adboard.constant.Constant.NOT_DELETED;

/**
 * @author d.gilfanova
 */
@Service
@RequiredArgsConstructor
public class ChatServiceImpl implements ChatService {

    private final UserRepository userRepository;
    private final ChatMessageRepository chatMessageRepository;
    private final ChatRepository chatRepository;

    private final ChatMessageMapper chatMessageMapper;
    private final ChatMapper chatMapper;

    @Transactional
    @Override
    public ChatMessage saveChatMessage(ChatMessageRequest messageRequest, User authUser) {
         User recipient = userRepository.findByIdAndIsDeleted(messageRequest.getRecipientId(), NOT_DELETED)
            .orElseThrow(UserNotFoundException::new);

        if (Objects.equals(authUser, recipient)) {
            throw new UserUnavailableOperationException("User can't communicate with himself");
        }

        Chat chat = chatRepository.findByUsersAndIsDeleted(authUser.getId(), recipient.getId(), NOT_DELETED)
            .orElseGet(() -> chatRepository.save(Chat.builder()
                .firstUser(authUser)
                .secondUser(recipient)
                .created(Timestamp.valueOf(LocalDateTime.now()))
                .build())
            );

        return chatMessageRepository.save(
            ChatMessage.builder()
                .chat(chat)
                .sender(authUser)
                .content(messageRequest.getContent())
                .created(Timestamp.valueOf(LocalDateTime.now()))
                .status(MessageStatus.NEW)
                .build()
        );
    }

    @Override
    public ChatNotification createChatNotification(ChatMessage chatMessage) {
        String messagePart = chatMessage.getContent()
            .substring(MESSAGE_PART_START_INDEX, Math.min(MESSAGE_PART_END_INDEX,
                chatMessage.getContent().length()))
            .concat(MESSAGE_PART_END);

        return ChatNotification.builder()
            .chatId(chatMessage.getChat().getId())
            .senderEmail(chatMessage.getSender().getEmail())
            .messagePart(messagePart)
            .build();
    }

    @Override
    public List<ChatResponse> getUserChats(User authUser) {
        return chatMapper.toResponseList(
            chatRepository.findAllByUserAndIsDeleted(NOT_DELETED, authUser.getId())
        );
    }

    @Transactional
    @Override
    public List<ChatMessageResponse> getChatMessages(UUID chatId, User authUser) {
        Chat chat = chatRepository.findByIdAndIsDeleted(chatId, NOT_DELETED)
            .orElseThrow(ChatNotFoundException::new);

        if (!chat.getFirstUser().equals(authUser) && !chat.getSecondUser().equals(authUser)) {
            throw new UserUnavailableOperationException("User doesn't have access to this chat");
        }

        updateMessageStatus(chat.getId(), getChatInterlocutor(chat, authUser));

        return chatMessageMapper.toResponseList(
            chatMessageRepository.findAllByChatId(chat.getId())
        );
    }

    @Transactional
    @Override
    public Long getChatNewMessageCount(UUID chatId, User authUser) {
        Chat chat = chatRepository.findByIdAndIsDeleted(chatId, NOT_DELETED)
            .orElseThrow(ChatNotFoundException::new);

        if (!chat.getFirstUser().equals(authUser) && !chat.getSecondUser().equals(authUser)) {
            throw new UserUnavailableOperationException("User doesn't have access to this chat");
        }

        return chatMessageRepository.countByChatAndSenderAndStatus(
            chat, getChatInterlocutor(chat, authUser), MessageStatus.NEW);
    }

    private void updateMessageStatus(UUID chatId, User interlocutor) {
        List<ChatMessage> chatMessages = chatMessageRepository.findAllByChatIdAndSenderAndStatus(
            chatId, interlocutor, MessageStatus.NEW);

        chatMessageRepository.saveAll(
            chatMessages
                .stream()
                .peek((e) -> e.setStatus(MessageStatus.OLD))
                .collect(Collectors.toList())
        );
    }

    private User getChatInterlocutor(Chat chat, User authUser) {
        return chat.getFirstUser().equals(authUser) ? chat.getSecondUser() : chat.getFirstUser();
    }
}
