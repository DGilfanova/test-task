package com.technokratos.adboard.model;

import java.sql.Timestamp;
import java.util.UUID;
import javax.persistence.*;

import com.technokratos.adboard.dto.enums.MessageStatus;
import lombok.*;
import org.hibernate.annotations.GenericGenerator;

/**
 * @author d.gilfanova
 */
@Getter
@Setter
@ToString
@RequiredArgsConstructor
@AllArgsConstructor
@Builder
@Entity
@Table(name = "chat_message")
public class ChatMessage {

    @Id
    @GeneratedValue(generator = "UUID")
    @GenericGenerator(name = "UUID", strategy = "org.hibernate.id.UUIDGenerator")
    @Column(name = "id", updatable = false, nullable = false)
    private UUID id;

    @ManyToOne
    @JoinColumn(name = "sender_id")
    private User sender;

    @ManyToOne
    @JoinColumn(name = "chat_id")
    private Chat chat;

    @Enumerated(value = EnumType.STRING)
    private MessageStatus status;

    private String content;
    private Timestamp created;
}
