package org.korolev.autopartsstore.api.compatibility.entity;

import jakarta.persistence.*;
import lombok.*;
import lombok.experimental.SuperBuilder;
import org.korolev.autopartsstore.api.config.BaseEntity;
import org.korolev.autopartsstore.api.product.entity.ProductEntity;

@Entity
@Table(name = "compatibility")
@Setter
@Getter
@SuperBuilder
@NoArgsConstructor
@AllArgsConstructor
public class CompatibilityEntity extends BaseEntity {

    @Column
    private String brand;

    @Column
    private String model;

    @Column
    private Integer yearStart;

    @Column
    private Integer yearEnd;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "product_id", nullable = false)
    private ProductEntity product;
}
