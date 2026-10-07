package org.korolev.automagazine.api.admin.entity;


import jakarta.persistence.*;
import lombok.*;
import org.korolev.automagazine.api.order.entity.OrderEntity;

import java.time.Instant;
import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "admin")
@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class AdminEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "admin_id")
    private Long id;

    @Column(name = "name", nullable = false, length = 30)
    private String name;

    @Column(name = "surname", nullable = false, length = 30)
    private String surname;

    @Column(name = "patronymic", nullable = false, length = 30)
    private String patronymic;

    @Column(name = "email", nullable = false, length = 100, unique = true)
    private String email;

    @Column(name = "password_hash", nullable = false, length = 100)
    private String passwordHash;

    @Column(name = "registered_at", nullable = false)
    @Builder.Default
    private Instant registeredAt = Instant.now();

    @Column(name = "access_level", nullable = false)
    private Integer accessLevel;

    @OneToMany(mappedBy = "admin")
    @Builder.Default
    private List<OrderEntity> orders = new ArrayList<>();
}
