package org.korolev.autopartsstore.api.category.entity;

import jakarta.persistence.*;
import lombok.*;
import org.korolev.autopartsstore.api.product.entity.ProductEntity;

import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "category")
@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class CategoryEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "category_id")
    private Long id;

    @Column(name = "name_category", nullable = false, length = 30)
    private String name;

    @Column(name = "description_category")
    private String description;

    @Builder.Default
    @OneToMany(mappedBy = "category", fetch = FetchType.LAZY)
    private List<ProductEntity> products = new ArrayList<>();
}
