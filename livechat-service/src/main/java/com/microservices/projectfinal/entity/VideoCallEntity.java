package com.microservices.projectfinal.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import java.time.Instant;
import java.util.UUID;

@Builder
@Getter
@NoArgsConstructor
@AllArgsConstructor
@EntityListeners(AuditingEntityListener.class)
@Entity
@Table(name = "video_calls")
public class VideoCallEntity {

    @Column(name = "call_session_id")
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private UUID callSessionId;

    @Column(name = "caller_id")
    private String callerId;

    @Column(name = "callee_id")
    private String calleeId;

    @Column(name = "call_status")
    private CallStatus callStatus;

    @Column(name = "start_time")
    private Instant startTime;

    @Column(name = "end_time")
    private Instant endTime;

    @Column(name = "duration")
    private Long duration;

    @Builder.Default
    private boolean recorded = false;

    @CreatedDate
    @Column(name = "created_at")
    private Instant createdAt;

    @LastModifiedDate
    @Column(name = "modified_at")
    private Instant modifiedAt;

    public enum CallStatus {
        PENDING,
        ACCEPTED,
        REJECTED,
        ENDED
    }

}
