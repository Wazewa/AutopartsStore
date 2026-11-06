package org.korolev.autopartsstore.api.category.entity;

import jakarta.persistence.*;
import lombok.*;
import lombok.experimental.SuperBuilder;
import org.korolev.autopartsstore.api.config.BaseEntity;

@Entity
@Table(name = "category")
@Getter
@Setter
@SuperBuilder
@AllArgsConstructor
@NoArgsConstructor
public class CategoryEntity extends BaseEntity {
    @Column
    private String nameCategory;

    @Column
    private String descriptionCategory;
}
