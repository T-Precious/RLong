package com.rongzer.suzhou.scm.validation;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;
import java.math.BigDecimal;

/**
 * Created with IntelliJ IDEA.
 * User: luolingling
 * Date: 2021/12/28
 * Description:
 */
public class IsDecimalValidator implements ConstraintValidator<IsDecimal, BigDecimal> {

    private int scale = 2;

    @Override
    public void initialize(IsDecimal constraintAnnotation) {
        scale = constraintAnnotation.scale();
    }

    @Override
    public boolean isValid(BigDecimal num, ConstraintValidatorContext constraintValidatorContext) {

        return num.scale() <= scale;

    }
}