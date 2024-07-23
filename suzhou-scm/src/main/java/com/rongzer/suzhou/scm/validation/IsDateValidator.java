package com.rongzer.suzhou.scm.validation;

import com.rongzer.util.LocalDateUtil;
import com.rongzer.util.StringUtil;


import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;

/**
 * Created with IntelliJ IDEA.
 * User: tiancl
 * Date: 2022/10/17
 * Description:
 */
public class IsDateValidator implements ConstraintValidator<IsDate, String> {

    private String dateFormat = "yyyy-MM-dd";

    @Override
    public void initialize(IsDate constraintAnnotation) {
        dateFormat = constraintAnnotation.dateFormat();
    }

    @Override
    public boolean isValid(String s, ConstraintValidatorContext constraintValidatorContext) {
        if (StringUtil.isEmpty(s)) {
            return true;
        }

        try {
            LocalDateUtil.strToDate(s, dateFormat);
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
        return true;

    }
}