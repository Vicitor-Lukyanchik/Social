package com.social.specification;

import com.social.entity.Interest;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Component;

import java.util.Map;

@Component
public class InterestSpecifications {

    private InterestSpecifications (){}

    public static Specification<Interest> findByNameWithSortByParameters(String name, Map<String, Boolean> parameters) {
        return new InterestFindByNameAndSortByParametersSpecification(name, parameters);
    }

    public static Specification<Interest> findWithSortByParameters(Map<String, Boolean> parameters) {
        return new InterestSortByParametersSpecification(parameters);
    }

}
