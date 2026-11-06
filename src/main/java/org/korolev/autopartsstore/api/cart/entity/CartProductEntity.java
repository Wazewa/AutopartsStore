package org.korolev.autopartsstore.api.cart.entity;

import jakarta.persistence.*;
import lombok.*;
import lombok.experimental.SuperBuilder;
import org.korolev.autopartsstore.api.config.BaseEntity;
import org.korolev.autopartsstore.api.product.entity.ProductEntity;

import java.math.BigDecimal;

@Getter
@Setter
@SuperBuilder
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "cart_product",
        uniqueConstraints = @UniqueConstraint(
        name = "uk_cart_product",
        columnNames = {"cart_id", "product_id"}
))
public class CartProductEntity extends BaseEntity {
    @Column
    private BigDecimal priceAddAt;

    @Column
    private Integer quantity;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "cart_id", nullable = false)
    private CartEntity cart;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "product_id", nullable = false)
    private ProductEntity product;


}
