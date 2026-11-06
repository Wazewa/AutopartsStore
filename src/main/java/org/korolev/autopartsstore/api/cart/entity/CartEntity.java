package org.korolev.autopartsstore.api.cart.entity;

import jakarta.persistence.*;
import lombok.*;
import lombok.experimental.SuperBuilder;
import org.hibernate.annotations.UpdateTimestamp;
import org.korolev.autopartsstore.api.config.BaseEntity;
import org.korolev.autopartsstore.api.user.entity.UserEntity;

import java.time.Instant;

@Getter
@Setter
@SuperBuilder
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "cart")
public class CartEntity extends BaseEntity {
    @Builder.Default
    @Column
    private Instant createdDate = Instant.now();

    @UpdateTimestamp
    @Column
    private Instant modifiedDate;

    @OneToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id", nullable = false, unique = true)
    private UserEntity user;
}
