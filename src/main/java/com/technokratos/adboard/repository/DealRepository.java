package com.technokratos.adboard.repository;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

import com.technokratos.adboard.model.Deal;
import org.springframework.data.jpa.repository.JpaRepository;

/**
 * @author d.gilfanova
 */
public interface DealRepository extends JpaRepository<Deal, UUID> {
    Optional<Deal> findByIdAndIsDeleted(UUID id, Boolean isDeleted);
    List<Deal> findByUserIdAndAdvertisementId(UUID userId, UUID advertisementId);
}
