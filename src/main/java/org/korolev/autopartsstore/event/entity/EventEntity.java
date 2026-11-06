package org.korolev.autopartsstore.event.entity;

import jakarta.persistence.*;
import lombok.*;
import lombok.experimental.SuperBuilder;
import org.korolev.autopartsstore.common.config.BaseEntity;
import org.korolev.autopartsstore.user.entity.UserEntity;

import java.time.Instant;

@Entity
@Table(name = "event")
@Setter
@Getter
@SuperBuilder
@NoArgsConstructor
@AllArgsConstructor
public class EventEntity extends BaseEntity {
    @Builder.Default
    @Column
    private Instant eventDate = Instant.now();

    @Enumerated(EnumType.STRING)
    @Column
    private EventType eventType;

    @Column
    private Integer sessionId;

    @Builder.Default
    @Enumerated(EnumType.STRING)
    @Column
    private EventStatus status = EventStatus.NEW;

    @Builder.Default
    @Column
    private Integer retryCount = 0;

    @Column
    private Instant sentAt;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id")
    private UserEntity user;
}
