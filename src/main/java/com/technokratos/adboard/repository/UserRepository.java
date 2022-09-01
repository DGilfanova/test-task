package com.technokratos.adboard.repository;

import java.util.UUID;

import com.technokratos.adboard.model.User;
import org.springframework.data.jpa.repository.JpaRepository;

/**
 * @author d.gilfanova
 */
public interface UserRepository extends JpaRepository<User, UUID> {
}
