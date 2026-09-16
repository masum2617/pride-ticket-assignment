package com.pridesys.ticketing.app.auth.entity;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.pridesys.ticketing.app.common.entity.BaseEntity;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.util.HashSet;
import java.util.Set;

@Entity
@Table(name = "roles")
@Getter
@Setter
public class RoleEntity extends BaseEntity {

    @Id
    @Column(name = "id")
    private Long id;

    @Column(name = "role_name", nullable = false, columnDefinition = "text")
    private String roleName;

    @ManyToMany(mappedBy = "roles", fetch = FetchType.LAZY)
    @JsonBackReference
    private Set<UserEntity> users = new HashSet<>();
}
