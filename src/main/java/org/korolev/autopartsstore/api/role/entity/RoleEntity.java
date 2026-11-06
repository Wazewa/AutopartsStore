package org.korolev.autopartsstore.api.role.entity;

import jakarta.persistence.Entity;
import jakarta.persistence.Table;
import jakarta.persistence.Column;
import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.Getter;
import lombok.experimental.SuperBuilder;
import org.korolev.autopartsstore.api.config.BaseEntity;

@Entity
@Table(name = "roles")
@Getter
@Setter
@SuperBuilder
@AllArgsConstructor
@NoArgsConstructor
public class RoleEntity extends BaseEntity {
    @Column
    private String roleCode;

    @Column
    private String roleDescription;
}
