package com.technokratos.adboard.repository;

import java.util.UUID;

import com.technokratos.adboard.model.Advertisement;
import org.springframework.data.jpa.repository.JpaRepository;

/**
 * @author d.gilfanova
 */
public interface AdRepository extends JpaRepository<Advertisement, UUID> {
}
