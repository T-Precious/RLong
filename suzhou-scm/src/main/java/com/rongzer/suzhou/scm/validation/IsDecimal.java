package com.rongzer.suzhou.scm.validation;

import javax.validation.Constraint;
import javax.validation.Payload;
import java.lang.annotation.*;

@Target({ElementType.METHOD, ElementType.FIELD, ElementType.TYPE})
@Retention(RetentionPolicy.RUNTIME)
@Documented
@Constraint(validatedBy = {IsDecimalValidator.class}) // 标明由哪个类执行校验逻辑
public @interface IsDecimal {

    // 校验出错时默认返回的消息
    String message() default "浮点数格式错误";
    //分组校验
    Class<?>[] groups() default {};

    Class<? extends Payload>[] payload() default {};

    //保留小数位数最大值
    int scale() default 2;
}
