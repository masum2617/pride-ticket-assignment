package com.pridesys.ticketing.app.auth.repository;

import com.pridesys.ticketing.app.auth.entity.RoleEntity;
import org.springframework.data.jpa.repository.JpaRepository;

public interface RoleRepo extends JpaRepository<RoleEntity, Long> {
}
