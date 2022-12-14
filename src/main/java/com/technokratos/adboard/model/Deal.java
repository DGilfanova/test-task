package com.technokratos.adboard.model;

import java.sql.Timestamp;
import java.util.Objects;
import java.util.UUID;
import javax.persistence.*;

import lombok.*;
import org.hibernate.Hibernate;
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
public class Deal {

    @Id
    @GeneratedValue(generator = "UUID")
    @GenericGenerator(name = "UUID", strategy = "org.hibernate.id.UUIDGenerator")
    @Column(name = "id", updatable = false, nullable = false)
    private UUID id;

    @ManyToOne
    @JoinColumn(name = "user_id")
    private User user;

    @ManyToOne
    @JoinColumn(name = "ad_id")
    private Advertisement advertisement;

    @Column(name = "is_completed")
    private Boolean isCompleted;

    private Timestamp created;

    @Column(name = "is_deleted")
    private Boolean isDeleted;

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || Hibernate.getClass(this) != Hibernate.getClass(o))
            return false;
        Deal deal = (Deal) o;
        return id != null && Objects.equals(id, deal.id);
    }

    @Override
    public int hashCode() {
        return getClass().hashCode();
    }
}
