package com.technokratos.adboard.model;

import java.sql.Timestamp;
import java.util.UUID;
import javax.persistence.*;

import lombok.*;
import org.hibernate.annotations.DynamicInsert;
import org.hibernate.annotations.GenericGenerator;

/**
 * @author d.gilfanova
 */
@Getter
@Setter
@ToString
@RequiredArgsConstructor
@AllArgsConstructor
@Builder
@DynamicInsert
@Entity
public class Chat {

    @Id
    @GeneratedValue(generator = "UUID")
    @GenericGenerator(name = "UUID", strategy = "org.hibernate.id.UUIDGenerator")
    @Column(name = "id", updatable = false, nullable = false)
    private UUID id;

    @ManyToOne
    @JoinColumn(name = "first_user_id")
    private User firstUser;

    @ManyToOne
    @JoinColumn(name = "second_user_id")
    private User secondUser;

    private Timestamp created;

    @Column(name = "is_deleted")
    private Boolean isDeleted;
}
