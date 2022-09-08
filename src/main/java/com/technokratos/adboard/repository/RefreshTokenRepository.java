package com.technokratos.adboard.repository;

import java.util.Optional;
import java.util.UUID;

import com.technokratos.adboard.model.RefreshToken;
import org.springframework.data.jpa.repository.JpaRepository;

public interface RefreshTokenRepository extends JpaRepository<RefreshToken, Long> {
    Optional<RefreshToken> findByToken(UUID token);
    void removeByToken(UUID token);
    void removeById(UUID id);
    Optional<RefreshToken> findByUserId(UUID userId);
}
