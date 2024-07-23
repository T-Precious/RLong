package com.rongzer.suzhou.scm.quartz;

import com.jcraft.jsch.Channel;
import com.jcraft.jsch.ChannelSftp;
import com.jcraft.jsch.JSch;
import com.jcraft.jsch.Session;
import lombok.extern.slf4j.Slf4j;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import java.util.Vector;

/**
 * @Description TODO
 * @Author THINKPAD
 * @Date 2024/4/12 10:46
 * @Version 1.0
 **/
@Slf4j
@Component
public class RemoteFileMonitorJob {
    //远程服务器地址
    private static final String REMOTE_HOST = "192.168.11.111";
    // SSH端口，默认为22
    private static final int REMOTE_PORT = 22;
    // 远程服务器用户名
    private static final String REMOTE_USER = "root";
    // 远程服务器密码
    private static final String REMOTE_PASSWORD = "Root.123";
    // 远程文件夹路径
    private static final String REMOTE_DIRECTORY = "/home/tiancl";
    private int lastFileCount=0;

    // 每5秒执行一次
//    @Scheduled(fixedDelay= 3000)
//    public void monitorRemoteFiles() {
//        JSch jsch = new JSch();
//        Session session = null;
//
//        try {
//            session = jsch.getSession(REMOTE_USER, REMOTE_HOST, REMOTE_PORT);
//            session.setPassword(REMOTE_PASSWORD);
//
//            // 避免检查known_hosts文件
//            java.util.Properties config = new java.util.Properties();
//            config.put("StrictHostKeyChecking", "no");
//            session.setConfig(config);
//
//            session.connect();
//
//            Channel channel = session.openChannel("sftp");
//            channel.connect();
//            ChannelSftp sftpChannel = (ChannelSftp) channel;
//
//            // 获取远程文件夹中的文件数量
//            Vector<ChannelSftp.LsEntry> entries = sftpChannel.ls(REMOTE_DIRECTORY);
//            int fileCount = entries.size();
//            log.info("远程文件夹："+REMOTE_DIRECTORY+"中文件数量："+fileCount);
//            if(lastFileCount==0){
//                lastFileCount=fileCount;
//            }
//            if(fileCount < lastFileCount){
//                log.info("远程文件夹："+REMOTE_DIRECTORY+"之前文件数量："+lastFileCount+",现在文件数量："+fileCount+",有文件被删除");
//                lastFileCount=fileCount;
//            }
//            if(fileCount > lastFileCount){
//                log.info("远程文件夹："+REMOTE_DIRECTORY+"之前文件数量："+lastFileCount+",现在文件数量："+fileCount);
//                //上链业务
//                //。。。。。
//                lastFileCount=fileCount;
//            }
//            // 关闭连接
//            sftpChannel.exit();
//            channel.disconnect();
//            session.disconnect();
//
//        } catch (Exception e) {
//            log.error("监测远程服务器地址："+REMOTE_HOST+",异常信息："+e.toString());
//        }
//    }
//    public static void main(String[] args){
//        JSch jsch = new JSch();
//        Session session = null;
//
//        try {
//            session = jsch.getSession(REMOTE_USER, REMOTE_HOST, REMOTE_PORT);
//            session.setPassword(REMOTE_PASSWORD);
//
//            // 避免检查known_hosts文件
//            java.util.Properties config = new java.util.Properties();
//            config.put("StrictHostKeyChecking", "no");
//            session.setConfig(config);
//
//            session.connect();
//
//            Channel channel = session.openChannel("sftp");
//            channel.connect();
//            ChannelSftp sftpChannel = (ChannelSftp) channel;
//
//            // 获取远程文件夹中的文件数量
//            Vector<ChannelSftp.LsEntry> entries = sftpChannel.ls(REMOTE_DIRECTORY);
//            int fileCount = entries.size();
//
//            System.out.println("远程文件夹中的文件数量: " + fileCount);
//
//            // 关闭连接
//            sftpChannel.exit();
//            channel.disconnect();
//            session.disconnect();
//
//        } catch (Exception e) {
//            e.printStackTrace();
//        }
//    }
}
