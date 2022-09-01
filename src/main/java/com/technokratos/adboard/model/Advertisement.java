package com.technokratos.adboard.model;

import java.sql.Timestamp;
import java.util.Objects;
import java.util.Set;
import java.util.UUID;
import javax.persistence.*;

import lombok.*;
import org.hibernate.Hibernate;
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
@Entity
public class Advertisement {

    @Id
    @GeneratedValue(generator = "UUID")
    @GenericGenerator(name = "UUID", strategy = "org.hibernate.id.UUIDGenerator")
    @Column(name = "id", updatable = false, nullable = false)
    private UUID id;

    private String title;
    private String content;

    @ManyToOne
    @JoinColumn(name = "user_id")
    private User user;

    @Column(name = "is_active")
    private Boolean isActive;

    @ManyToMany
    @JoinTable(name = "ad_photo",
        joinColumns = @JoinColumn(name = "ad_id"),
        inverseJoinColumns = @JoinColumn(name = "photo_id"))
    @ToString.Exclude
    private Set<File> photos;

    private Timestamp created;
    private Timestamp updated;

    @Column(name = "is_deleted")
    private Boolean isDeleted;

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || Hibernate.getClass(this) != Hibernate.getClass(o))
            return false;
        Advertisement that = (Advertisement) o;
        return id != null && Objects.equals(id, that.id);
    }

    @Override
    public int hashCode() {
        return getClass().hashCode();
    }
}
