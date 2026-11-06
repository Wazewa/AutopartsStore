package org.korolev.autopartsstore.product.entity;

import jakarta.persistence.*;
import lombok.*;
import lombok.experimental.SuperBuilder;
import org.korolev.autopartsstore.category.entity.CategoryEntity;
import org.korolev.autopartsstore.common.config.BaseEntity;

import java.math.BigDecimal;

@Getter
@Setter
@SuperBuilder
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "product")
public class ProductEntity extends BaseEntity {
    @Column
    private String name;

    @Column
    private BigDecimal price;

    @Column
    private String article;

    @Column
    private Integer quantity;

    @Column
    private String imageUrl;

    @Column
    private String brand;

    @Column
    private String comment;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "category_id")
    private CategoryEntity category;

}
