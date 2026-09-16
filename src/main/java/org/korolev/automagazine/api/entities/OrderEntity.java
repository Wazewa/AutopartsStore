package org.korolev.automagazine.api.entities;

import jakarta.persistence.*;
import lombok.*;

import java.time.Instant;
import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "orders")
@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class OrderEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "order_id")
    private Long id;

    @Builder.Default
    @Column(name = "order_date")
    private Instant orderDate = Instant.now();

    @Column(name = "status", length = 20)
    private String status;

    @Column(name = "pay_method", length = 20)
    private String payMethod;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "customer_id", nullable = false)
    private CustomerEntity customer;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "processed_by_admin_id")
    private AdminEntity admin;

    @OneToMany(mappedBy = "order")
    @Builder.Default
    private List<OrderProductEntity> orderProductEntities = new ArrayList<>();
}
