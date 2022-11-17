package com.social.specification;

import com.social.entity.Interest;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Component;

import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;

@Component
public class InterestSortDescSpecification implements Specification<Interest> {

    @Override
    public Predicate toPredicate(Root<Interest> root, CriteriaQuery<?> query, CriteriaBuilder criteriaBuilder) {
        return query.orderBy(criteriaBuilder.desc(root.get("name"))).getRestriction();
    }
}
