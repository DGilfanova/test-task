package com.technokratos.adboard.repository;

import java.util.UUID;

import com.technokratos.adboard.model.Deal;
import org.springframework.data.jpa.repository.JpaRepository;

/**
 * @author d.gilfanova
 */
public interface DealRepository extends JpaRepository<Deal, UUID> {
}
