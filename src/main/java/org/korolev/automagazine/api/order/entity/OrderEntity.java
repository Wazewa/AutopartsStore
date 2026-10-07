package org.korolev.automagazine.api.order.entity;

import jakarta.persistence.*;
import lombok.*;
import org.korolev.automagazine.api.admin.entity.AdminEntity;
import org.korolev.automagazine.api.customer.entity.CustomerEntity;

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
    @Column(name = "order_date", nullable = false)
    private Instant orderDate = Instant.now();

    @Enumerated(EnumType.STRING)
    @Column(name = "status", length = 20, nullable = false)
    private OrderStatus status;

    @Enumerated(EnumType.STRING)
    @Column(name = "pay_method", length = 20, nullable = false)
    private PayMethod payMethod;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "customer_id", nullable = false)
    private CustomerEntity customer;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "processed_by_admin_id")
    private AdminEntity admin;

    @OneToMany(mappedBy = "order", cascade = CascadeType.ALL, orphanRemoval = true)
    @Builder.Default
    private List<OrderProductEntity> orderProductEntities = new ArrayList<>();
}
