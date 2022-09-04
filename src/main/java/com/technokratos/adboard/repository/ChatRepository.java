package com.technokratos.adboard.repository;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

import com.technokratos.adboard.model.Chat;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

/**
 * @author d.gilfanova
 */
public interface ChatRepository extends JpaRepository<Chat, UUID> {
    @Query(value = "SELECT chat FROM Chat chat "
                   + "WHERE chat.isDeleted = :is_deleted "
                   + "AND (chat.firstUser.id = :first_user AND chat.secondUser.id = :second_user "
                   + "OR chat.firstUser.id = :second_user AND chat.secondUser.id = :first_user)")
    Optional<Chat> findByUsersAndIsDeleted(@Param("first_user") UUID firstUser,
                                           @Param("second_user") UUID secondUser,
                                           @Param("is_deleted") Boolean isDeleted);

    @Query(value = "SELECT chat FROM Chat chat "
                   + "WHERE chat.isDeleted = :is_deleted "
                   + "AND (chat.firstUser.id = :user OR chat.secondUser.id = :user)")
    List<Chat> findAllByUserAndIsDeleted(@Param("is_deleted") Boolean isDeleted,
                                         @Param("user") UUID user);

    Optional<Chat> findByIdAndIsDeleted(UUID id, Boolean isDeleted);
}
