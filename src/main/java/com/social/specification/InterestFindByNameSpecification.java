package com.social.specification;

import com.social.entity.Interest;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.NoArgsConstructor;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Component;

import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;
import java.util.ArrayList;
import java.util.List;

@AllArgsConstructor
@NoArgsConstructor
public class InterestFindByNameSpecification implements Specification<Interest> {

    private String name = "";

    @Override
    public Predicate toPredicate(Root<Interest> root, CriteriaQuery<?> query, CriteriaBuilder criteriaBuilder) {
        List<Predicate> predicates = new ArrayList<>();

        if (name.equals("")) {
            predicates.add(criteriaBuilder.equal(root.get("name"), name));
        }
        query.where(predicates.toArray(new Predicate[0]));

        return predicates.get(0);
    }
}
