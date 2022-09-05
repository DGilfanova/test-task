package com.technokratos.adboard.specification;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import javax.persistence.criteria.*;

import com.technokratos.adboard.dto.request.FilterAdRequest;
import com.technokratos.adboard.model.*;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Component;

import static com.technokratos.adboard.constant.Constant.ACTIVE;
import static com.technokratos.adboard.constant.Constant.NOT_DELETED;

/**
 * @author d.gilfanova
 */
@Component
public class AdSpecification {

    public Specification<Advertisement> getAdvertisements(FilterAdRequest request) {
        return (root, query, criteriaBuilder) -> {

            List<Predicate> predicates = new ArrayList<>();
            if (Objects.nonNull(request.getTitle())) {
                predicates.add(criteriaBuilder.like(root.get(Advertisement_.title),
                    "%" + request.getTitle() + "%"));
            }

            if (Objects.nonNull(request.getContent())) {
                predicates.add(criteriaBuilder.like(root.get(Advertisement_.content),
                    "%" + request.getContent() + "%"));
            }

            if (Objects.nonNull(request.getEmail())) {
                Join<Advertisement, User> join = root.join(Advertisement_.user, JoinType.LEFT);
                predicates.add(criteriaBuilder.like(join.get(User_.email),
                    "%" + request.getEmail().toLowerCase() + "%"));
            }

            if (Objects.nonNull(request.getMinPhotoCount())) {
                Join<Advertisement, File> join = root.join(Advertisement_.photos, JoinType.LEFT);

                query.groupBy(join.getParent().get(Advertisement_.id))
                    .having(criteriaBuilder.greaterThanOrEqualTo(
                        criteriaBuilder.count(join.get(File_.id)), request.getMinPhotoCount())
                    );
            }

            if (Objects.nonNull(request.getDateAfter())) {
                predicates.add(
                    criteriaBuilder.greaterThanOrEqualTo(root.get(Advertisement_.created),
                        request.getDateAfter())
                );
            }

            predicates.add(criteriaBuilder.equal(root.get(Advertisement_.isActive), ACTIVE));
            predicates.add(criteriaBuilder.equal(root.get(Advertisement_.isDeleted), NOT_DELETED));
            query.orderBy(criteriaBuilder.desc(root.get(Advertisement_.created)));

            return criteriaBuilder.and(predicates.toArray(new Predicate[0]));
        };
    }
}
