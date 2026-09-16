package org.korolev.automagazine.api.entities;

import jakarta.persistence.*;
import lombok.*;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "product")
public class ProductEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "product_id")
    private Long id;

    @Column(name = "name", nullable = false, length = 100)
    private String name;

    @Column(name = "price", nullable = false)
    private BigDecimal price;

    @Column(name = "article", length = 100)
    private String article;

    @Column(name = "quantity", nullable = false)
    private Integer quantity;

    @Column(name = "image_url", length = 1024)
    private String imageUrl;

    @Column(name = "brand", length = 100)
    private String brand;

    @Column(name = "comment")
    private String comment;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "category_id", nullable = false)
    private CategoryEntity category;

    @OneToMany(mappedBy = "product")
    @Builder.Default
    private List<CompatibilityEntity> compatibilites = new ArrayList<>();

    @OneToMany(mappedBy = "product")
    @Builder.Default
    private List<CartProductEntity> cartProductEntities = new ArrayList<>();

    @OneToMany(mappedBy = "product")
    @Builder.Default
    private List<OrderProductEntity> orderProductEntities = new ArrayList<>();
}
