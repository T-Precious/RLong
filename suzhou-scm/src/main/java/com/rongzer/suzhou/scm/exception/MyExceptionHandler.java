package com.rongzer.suzhou.scm.exception;

import com.rongzer.eloan.common.starter.constant.CommonConst;
import com.rongzer.eloan.common.starter.vo.ResultVo;
import com.rongzer.suzhou.scm.enums.LoginExceptionEnum;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.multipart.MaxUploadSizeExceededException;

/**
 * Created with IntelliJ IDEA.
 * User: luolingling
 * Date: 2022/1/6
 * Description:
 */
@Slf4j
@RestControllerAdvice
public class MyExceptionHandler {

    /**
     * 登录异常
     *
     * @param e
     * @return ResponseResult
     */
    @ResponseStatus(HttpStatus.OK)
    @ExceptionHandler(LoginException.class)
    public ResultVo loginExceptionHandler(LoginException e) {
        String message = e.getMessage();
        LoginExceptionEnum exceptionEnum = LoginExceptionEnum.get(message);
        log.error(message);
        return new ResultVo(exceptionEnum == null ? CommonConst.ERROR_STATUS : exceptionEnum.getErrorCode(), message);
    }

    /**
     * 上传文件大小超出限制
     *
     * @param e
     * @return ResponseResult
     */
    @ResponseStatus(HttpStatus.OK)
    @ExceptionHandler(MaxUploadSizeExceededException.class)
    public ResultVo maxUploadSizeExceededException(MaxUploadSizeExceededException e) {
        log.error("", e);
        String rootCauseName = e.getRootCause().getClass().getName();

        if ("org.apache.tomcat.util.http.fileupload.FileUploadBase$FileSizeLimitExceededException".equals(rootCauseName)) {
            return new ResultVo(0, "上传文件大小超出10M限制");
        } else if ("org.apache.tomcat.util.http.fileupload.FileUploadBase$SizeLimitExceededException".equals(rootCauseName)) {
            return new ResultVo(0, "请求大小超出20M限制");
        }

        log.error("上传文件异常:" + rootCauseName);

        return new ResultVo(0, "上传文件异常");
    }


}