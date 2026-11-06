package org.korolev.autopartsstore.user.entity;


import jakarta.persistence.*;
import lombok.*;
import lombok.experimental.SuperBuilder;
import org.korolev.autopartsstore.common.config.BaseEntity;
import org.korolev.autopartsstore.role.entity.RoleEntity;

import java.time.Instant;


@Entity
@Table(name = "users")
@Getter
@Setter
@SuperBuilder
@AllArgsConstructor
@NoArgsConstructor
public class UserEntity extends BaseEntity {

    @Column
    private String name;

    @Column
    private String surname;

    @Column
    private String patronymic;

    @Column
    private String email;

    @Column
    private String passwordHash;

    @Column
    @Builder.Default
    private Instant registeredAt = Instant.now();

    @Column
    private String phone;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "role_id", nullable = false)
    private RoleEntity role;
}
