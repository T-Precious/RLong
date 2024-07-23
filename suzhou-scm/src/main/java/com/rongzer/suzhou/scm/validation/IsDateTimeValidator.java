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
public class IsDateTimeValidator implements ConstraintValidator<IsDateTime, String> {

    private String dateFormat = "yyyy-MM-dd HH:mm:ss";

    @Override
    public void initialize(IsDateTime constraintAnnotation) {
        dateFormat = constraintAnnotation.dateFormat();
    }

    @Override
    public boolean isValid(String s, ConstraintValidatorContext constraintValidatorContext) {
        if (StringUtil.isEmpty(s)) {
            return true;
        }

        try {
            LocalDateUtil.strToDateTime(s, dateFormat);
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
        return true;

    }
}