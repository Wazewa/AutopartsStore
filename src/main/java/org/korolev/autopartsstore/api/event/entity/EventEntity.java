package org.korolev.autopartsstore.api.event.entity;

import jakarta.persistence.*;
import lombok.*;
import org.korolev.autopartsstore.api.user.entity.UserEntity;

import java.time.Instant;

@Entity
@Table(name = "event")
@Setter
@Getter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class EventEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "event_id")
    private Long id;

    @Builder.Default
    @Column(name = "event_date", nullable = false)
    private Instant eventDate = Instant.now();

    @Enumerated(EnumType.STRING)
    @Column(name = "event_type", nullable = false, length = 128)
    private EventType eventType;

    @Column(name = "session_id", nullable = false)
    private Integer sessionId;

    @Builder.Default
    @Enumerated(EnumType.STRING)
    @Column(name = "status", nullable = false)
    private EventStatus status = EventStatus.NEW;

    @Builder.Default
    @Column(name = "retry_count", nullable = false)
    private Integer retryCount = 0;

    @Column(name = "sent_at")
    private Instant sentAt;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id")
    private UserEntity user;
}
