package org.korolev.automagazine.api.entities;

import jakarta.persistence.*;
import lombok.*;

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

    @Builder.Default
    @Column(name = "modified_date", nullable = false)
    private Instant modifiedDate = Instant.now();

    @OneToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "customer_id", nullable = false, unique = true)
    private CustomerEntity customer;

    @OneToMany(mappedBy = "cart")
    @Builder.Default
    private List<CartProductEntity> cartProductEntities = new ArrayList<>();
}
