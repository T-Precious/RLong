package com.rongzer.suzhou.scm.aspect;

import com.rongzer.eloan.common.starter.constant.CommonConst;
import com.rongzer.eloan.common.starter.vo.ResultVo;
import com.rongzer.suzhou.scm.enums.LoginExceptionEnum;
import com.rongzer.suzhou.scm.exception.LoginException;
import lombok.extern.slf4j.Slf4j;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.springframework.stereotype.Component;

/**
 * Created with IntelliJ IDEA.
 * User: luolingling
 * Date: 2022/1/7
 * Description:
 */
@Slf4j
@Aspect
@Component
public class MyControllerAspect {

    @Around("execution(* com.rongzer..controller..*Controller.*(..))")
    public Object controllerProcess(ProceedingJoinPoint pjp) throws Throwable {
        try {
            Object result = pjp.proceed();
            return result;
        } catch (LoginException e) {
            String message = e.getMessage();
            LoginExceptionEnum exceptionEnum = LoginExceptionEnum.get(message);
            log.error(message);
            return new ResultVo(exceptionEnum == null ? CommonConst.ERROR_STATUS : exceptionEnum.getErrorCode(), message).toString();
        } catch (Exception var4) {
            throw var4;
        }
    }
}