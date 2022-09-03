package com.technokratos.adboard.repository;

import java.util.Optional;
import java.util.UUID;

import com.technokratos.adboard.model.File;
import org.springframework.data.jpa.repository.JpaRepository;

/**
 * @author d.gilfanova
 */
public interface FileRepository extends JpaRepository<File, UUID> {
    Optional<File> findByIdAndIsDeleted(UUID id, Boolean isDeleted);
}
