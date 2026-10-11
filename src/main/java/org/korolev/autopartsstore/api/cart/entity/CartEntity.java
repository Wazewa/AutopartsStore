package org.korolev.autopartsstore.api.cart.entity;

import jakarta.persistence.*;
import lombok.*;
import org.hibernate.annotations.UpdateTimestamp;
import org.korolev.autopartsstore.api.user.entity.UserEntity;

import java.time.Instant;
import java.util.ArrayList;
import java.util.List;

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "cart")
public class CartEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "cart_id")
    private Long id;

    @Builder.Default
    @Column(name = "created_date", nullable = false)
    private Instant createdDate = Instant.now();

    @UpdateTimestamp
    @Column(name = "modified_date", nullable = false)
    private Instant modifiedDate;

    @OneToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id", nullable = false, unique = true)
    private UserEntity user;

    @OneToMany(mappedBy = "cart", cascade = CascadeType.ALL, orphanRemoval = true)
    @Builder.Default
    private List<CartProductEntity> cartProductEntities = new ArrayList<>();
}
