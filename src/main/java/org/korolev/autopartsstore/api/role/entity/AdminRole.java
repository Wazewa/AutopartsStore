package org.korolev.autopartsstore.api.role.entity;

import jakarta.persistence.EmbeddedId;
import jakarta.persistence.Entity;
import jakarta.persistence.Table;
import jakarta.persistence.MapsId;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.FetchType;
import jakarta.persistence.JoinColumn;
import lombok.Getter;
import lombok.Setter;
import org.korolev.autopartsstore.api.admin.entity.AdminEntity;

@Getter
@Setter
@Entity
@Table(name = "admin_roles")
public class AdminRole {
    @EmbeddedId
    private AdminRoleId id;

    @MapsId("adminId")
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "admin_id", nullable = false)
    private AdminEntity admin;

    @MapsId("roleId")
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "role_id", nullable = false)
    private RoleEntity role;


}