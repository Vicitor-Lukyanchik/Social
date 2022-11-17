package com.social.specification;

import com.social.entity.Interest;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Component;

@Component
public class InterestSpecifications {

    private InterestSpecifications (){}

    public static Specification<Interest> sortAsc() {
        return new InterestSortAscSpecification();
    }

    public static Specification<Interest> findByName(String name) {
        return InterestFindByNameSpecification.builder().name(name).build();
    }

}
