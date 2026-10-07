package org.korolev.automagazine.api.cart.entity;

import jakarta.persistence.*;
import lombok.*;
import org.korolev.automagazine.api.product.entity.ProductEntity;

import java.math.BigDecimal;

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "cart_product",
        uniqueConstraints = @UniqueConstraint(
        name = "uk_cart_product",
        columnNames = {"cart_id", "product_id"}
))
public class CartProductEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "cart_product_id")
    private Long id;

    @Column(name = "price_add_at", nullable = false)
    private BigDecimal priceAddAt;

    @Column(name = "quantity", nullable = false)
    private Integer quantity;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "cart_id", nullable = false)
    private CartEntity cart;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "product_id", nullable = false)
    private ProductEntity product;


}
