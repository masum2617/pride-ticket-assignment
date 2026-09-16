package com.pridesys.ticketing.app.auth.repository;

import com.pridesys.ticketing.app.auth.entity.UserEntity;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface UserRepo extends JpaRepository<UserEntity, Long> {
    Optional<UserEntity> findByEmail(String email);

    boolean existsByEmail(String email);

    List<UserEntity> findByActiveFlag(Boolean activeFlag);
}
