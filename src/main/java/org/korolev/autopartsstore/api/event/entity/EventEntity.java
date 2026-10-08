package org.korolev.autopartsstore.api.event.entity;

import jakarta.persistence.*;
import lombok.*;
import org.korolev.autopartsstore.api.customer.entity.CustomerEntity;

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

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "customer_id")
    private CustomerEntity customer;
}
