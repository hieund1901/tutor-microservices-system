package com.microservices.projectfinal.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import java.time.Instant;

@AllArgsConstructor
@NoArgsConstructor
@EntityListeners(AuditingEntityListener.class)
@Entity
@Table(name = "messages")
public class MessageEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;
    @Column(name = "conversation_id")
    private long conversationId;
    @Column(name = "call_session_id")
    private String callSessionId;
    @Column(name = "sender_id")
    private String senderId;
    @Column(name = "receiver_id")
    private String receiverId;

    private String message;
    private String type;

    @Column(name = "is_read")
    private boolean isRead;
    @Column(name = "sent_at")
    private Instant sentAt;
    @Column(name = "received_at")
    private Instant createdAt;
    @Column(name = "modified_at")
    private Instant modifiedAt;
}
