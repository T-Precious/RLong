package com.rongzer.suzhou.scm.Listener;

import lombok.extern.slf4j.Slf4j;

import javax.servlet.ServletContextEvent;
import javax.servlet.ServletContextListener;
import javax.servlet.annotation.WebListener;
import java.io.File;

/**
 * @Description TODO
 * @Author THINKPAD
 * @Date 2024/4/11 13:30
 * @Version 1.0
 **/
@WebListener
@Slf4j
public class ContextListener implements ServletContextListener {
    @Override
    public void contextInitialized(ServletContextEvent sce) {
        final File file = new File("D:\\test");
        new Thread(() -> {
            try {
                new WatchDir(file, true, new FileActionCallback(){});
            } catch (Exception e) {
                e.printStackTrace();
            }
        }).start();
        log.info("正在监视文件夹:" + file.getAbsolutePath());
    }
}
