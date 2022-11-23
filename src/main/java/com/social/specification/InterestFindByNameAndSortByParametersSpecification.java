package com.social.specification;

import com.social.entity.Interest;
import org.springframework.data.jpa.domain.Specification;

import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;
import java.util.Map;

public class InterestFindByNameAndSortByParametersSpecification implements Specification<Interest> {

    private String name;
    private Map<String, Boolean> parameters;

    public InterestFindByNameAndSortByParametersSpecification(String name, Map<String, Boolean> parameters) {
        this.name = name;
        this.parameters = parameters;
    }

    @Override
    public Predicate toPredicate(Root<Interest> root, CriteriaQuery<?> query, CriteriaBuilder criteriaBuilder) {
        for (Map.Entry<String, Boolean> parameter : parameters.entrySet()) {
            if (parameter.getValue()) {
                query.orderBy(criteriaBuilder.desc(root.get(parameter.getKey())));
            } else {
                query.orderBy(criteriaBuilder.asc(root.get(parameter.getKey())));
            }
        }
        return criteriaBuilder.like(criteriaBuilder.lower(root.get("name")), "%" + name.toLowerCase() + "%");
    }
}
