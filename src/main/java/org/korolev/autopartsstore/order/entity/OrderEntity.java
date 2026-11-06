package org.korolev.autopartsstore.order.entity;

import jakarta.persistence.*;
import lombok.*;
import lombok.experimental.SuperBuilder;
import org.korolev.autopartsstore.common.config.BaseEntity;
import org.korolev.autopartsstore.user.entity.UserEntity;

import java.time.Instant;

@Entity
@Table(name = "orders")
@Getter
@Setter
@SuperBuilder
@AllArgsConstructor
@NoArgsConstructor
public class OrderEntity extends BaseEntity {
    @Builder.Default
    @Column
    private Instant orderDate = Instant.now();

    @Enumerated(EnumType.STRING)
    @Column
    private OrderStatus status;

    @Enumerated(EnumType.STRING)
    @Column
    private PayMethod payMethod;

    @Version
    @Column
    private Long version;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id", nullable = false)
    private UserEntity user;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "processed_by_user_id")
    private UserEntity admin;
}
