package org.korolev.automagazine.entities;

import jakarta.persistence.*;
import lombok.*;

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

    @Column(name = "event_type", nullable = false, length = 128)
    private String eventType;

    @Column(name = "session_id", nullable = false)
    private Integer sessionId;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "customer_id", nullable = false)
    private CustomerEntity customer;
}
