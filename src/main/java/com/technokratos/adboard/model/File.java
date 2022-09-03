package com.technokratos.adboard.model;

import java.sql.Timestamp;
import java.util.Objects;
import java.util.UUID;
import javax.persistence.*;

import com.technokratos.adboard.dto.enums.FileType;
import lombok.*;
import org.hibernate.Hibernate;
import org.hibernate.annotations.DynamicInsert;
import org.hibernate.annotations.GenericGenerator;

@Getter
@Setter
@ToString
@RequiredArgsConstructor
@AllArgsConstructor
@Builder
@DynamicInsert
@Entity
public class File {

    @Id
    @GeneratedValue(generator = "UUID")
    @GenericGenerator(name = "UUID", strategy = "org.hibernate.id.UUIDGenerator")
    @Column(name = "id", updatable = false, nullable = false)
    private UUID id;

    private String name;

    @Column(name = "content_type")
    private String contentType;

    private Long size;

    @Enumerated(value = EnumType.STRING)
    @Column(name = "file_type")
    private FileType fileType;

    private String link;
    private Timestamp created;

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || Hibernate.getClass(this) != Hibernate.getClass(o))
            return false;
        File file = (File) o;
        return id != null && Objects.equals(id, file.id);
    }

    @Override
    public int hashCode() {
        return getClass().hashCode();
    }
}
