package org.korolev.autopartsstore.api.role.entity;

import jakarta.persistence.Column;
import jakarta.persistence.Embeddable;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;

import java.io.Serializable;

@Getter
@Setter
@EqualsAndHashCode
@Embeddable
public class CustomerRoleId implements Serializable {
    @Column(name = "customer_id", nullable = false)
    private Long customerId;

    @Column(name = "role_id", nullable = false)
    private Long roleId;


}