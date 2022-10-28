package com.social.validator;

import com.social.service.exception.ServiceException;
import org.springframework.stereotype.Component;

import javax.validation.*;
import java.util.Set;

@Component
public class BeanValidator {

    private Validator validator;

    public BeanValidator() {
        validator = Validation.buildDefaultValidatorFactory().getValidator();
    }

    public void validate(Object bean) throws AssertionError {
        Set<ConstraintViolation<Object>> violations = validator.validate(bean);
        if (violations.size() == 1) {
            throw new ValidationException(violations.stream().findFirst().get().getMessage());
        }
        throw new ServiceException("Not one validation problem");
    }
}
