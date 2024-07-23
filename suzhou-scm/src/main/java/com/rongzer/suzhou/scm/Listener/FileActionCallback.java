package com.rongzer.suzhou.scm.Listener;

import lombok.extern.slf4j.Slf4j;

import java.io.File;

/**
 * @Description TODO
 * @Author THINKPAD
 * @Date 2024/4/11 11:54
 * @Version 1.0
 **/
@Slf4j
public class FileActionCallback {
    public void delete(File file) {
        log.info("文件已删除\t" + file.getAbsolutePath());
    }

//    public void modify(File file) {
//        log.info("文件已修改\t" + file.getAbsolutePath());
//    }

    public void create(File file) {
        log.info("文件已创建\t" + file.getAbsolutePath());
    }
}
