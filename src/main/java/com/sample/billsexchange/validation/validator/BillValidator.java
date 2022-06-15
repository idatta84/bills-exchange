package com.sample.billsexchange.validation.validator;

import com.sample.billsexchange.model.ChangeConfig;
import com.sample.billsexchange.validation.annotation.BillConstraint;
import org.springframework.beans.factory.annotation.Autowired;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;

public class BillValidator implements ConstraintValidator<BillConstraint, Integer> {

    @Autowired
    private ChangeConfig changeConfig;

    @Override
    public void initialize(BillConstraint billConstraint) {
    }

    @Override
    public boolean isValid(Integer value, ConstraintValidatorContext context) {
        return value!=null && changeConfig.getAllowedBills().contains(value);
    }

}
