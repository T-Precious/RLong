package com.rongzer.suzhou.scm.validation;

import javax.validation.Constraint;
import javax.validation.Payload;
import java.lang.annotation.*;

@Target({ElementType.METHOD, ElementType.FIELD, ElementType.TYPE})
@Retention(RetentionPolicy.RUNTIME)
@Documented
@Constraint(validatedBy = {IsDateTimeValidator.class}) // 标明由哪个类执行校验逻辑
public @interface IsDateTime {

    // 校验出错时默认返回的消息
    String message() default "时间格式错误";
    //分组校验
    Class<?>[] groups() default {};

    Class<? extends Payload>[] payload() default {};

    String dateFormat() default "yyyy-MM-dd HH:mm:ss";
}
