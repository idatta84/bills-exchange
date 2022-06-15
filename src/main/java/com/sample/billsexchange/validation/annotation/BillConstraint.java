package com.sample.billsexchange.validation.annotation;

import com.sample.billsexchange.validation.validator.BillValidator;

import javax.validation.Constraint;
import javax.validation.Payload;
import java.lang.annotation.*;

@Documented
@Constraint(validatedBy = BillValidator.class)
@Target( { ElementType.PARAMETER, ElementType.FIELD })
@Retention(RetentionPolicy.RUNTIME)
public @interface BillConstraint {
    String message() default "Invalid bill amount";
    Class<?>[] groups() default {};
    Class<? extends Payload>[] payload() default {};
}