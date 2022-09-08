package com.technokratos.adboard.repository;

import java.util.Optional;
import java.util.UUID;

import com.technokratos.adboard.model.Advertisement;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

/**
 * @author d.gilfanova
 */
public interface AdRepository extends JpaRepository<Advertisement, UUID>,
    JpaSpecificationExecutor<Advertisement> {
    Optional<Advertisement> findByIdAndIsActiveAndIsDeleted(UUID id, Boolean isActive,
        Boolean isDeleted);

    Optional<Advertisement> findByIdAndIsDeleted(UUID id, Boolean isDeleted);
}
