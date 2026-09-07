package org.korolev.automagazine.entities;


import jakarta.persistence.*;
import lombok.*;

import java.time.Instant;
import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "customer")
@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class CustomerEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "customer_id")
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

    @Column(name = "phone", nullable = false, unique = true)
    private String phone;

    @OneToMany(mappedBy = "customer")
    @Builder.Default
    private List<OrderEntity> orders = new ArrayList<>();

    @OneToMany(mappedBy = "customer")
    @Builder.Default
    private List<EventEntity> events = new ArrayList<>();
}
