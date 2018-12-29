package com.asiainfo.commonftp.util;

import lombok.extern.slf4j.Slf4j;
import org.apache.commons.net.ftp.*;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.text.DecimalFormat;
import java.util.Arrays;
import java.util.Comparator;
import java.util.List;
import java.util.StringTokenizer;

/**
 * @author king-pan
 * @date 2018/12/29
 * @Description ${DESCRIPTION}
 */
@Slf4j
@Component
public class FtpClientUtil {

    @Value("${ftp.user}")
    private String user;
    @Value("${ftp.password}")
    private String password;
    @Value("${ftp.host}")
    private String host;
    @Value("${ftp.port}")
    private int port;
    @Value("${ftp.ftpPath}")
    private String ftpPath;

    private String ANONYMOUS_USER = "anonymous";

    public FTPClient connect() {
        FTPClient client = new FTPClient();
        try {
            //连接FTP Server
            client.connect(this.host, this.port);
            //登陆
            if (this.user == null || "".equals(this.user)) {
                //使用匿名登陆
                client.login(ANONYMOUS_USER, ANONYMOUS_USER);
            } else {
                client.login(this.user, this.password);
            }
            //设置文件格式
            client.setFileType(FTPClient.BINARY_FILE_TYPE);
            //获取FTP Server 应答
            int reply = client.getReplyCode();
            if (!FTPReply.isPositiveCompletion(reply)) {
                client.disconnect();
                return null;
            }
            //切换工作目录
            changeWorkingDirectory(client, false);
            log.info("连接到FTP：{}:{}", host, port);
        } catch (IOException e) {
            log.error(e.getMessage(), e);
            client = null;
        }
        return client;
    }


    /**
     * 切换工作目录，远程目录不存在时，创建目录
     *
     * @param client
     * @throws IOException
     */
    private void changeWorkingDirectory(FTPClient client, Boolean autoCreateDir) throws IOException {
        if (this.ftpPath != null && !"".equals(this.ftpPath)) {
            boolean ok = client.changeWorkingDirectory(this.ftpPath);
            System.out.println(client.listNames());
            if (!ok && autoCreateDir) {
                //ftpPath 不存在，手动创建目录
                StringTokenizer token = new StringTokenizer(this.ftpPath, "\\//");
                while (token.hasMoreTokens()) {
                    String path = token.nextToken();
                    client.makeDirectory(path);
                    client.changeWorkingDirectory(path);
                }
            }
            if (!ok && !autoCreateDir) {
                throw new RuntimeException("远程ftp目录不存在:" + this.ftpPath);
            }
        }
    }

    /**
     * 断开FTP连接
     *
     * @param ftpClient
     * @throws IOException
     */
    public void close(FTPClient ftpClient) throws IOException {
        if (ftpClient != null && ftpClient.isConnected()) {
            ftpClient.logout();
            ftpClient.disconnect();
        }
        log.info("断开FTP连接：{}:{}", host, port);
    }

    /**
     * 下载文件
     *
     * @param localPath 本地存放路径
     * @return
     */
    public int download(String localPath) {
        //  连接ftp server
        FTPClient ftpClient = connect();
        if (ftpClient == null) {
            System.out.println("连接FTP服务器[" + host + ":" + port + "]失败！");
            return 0;
        }

        File dir = new File(localPath);
        if (!dir.exists()) {
            dir.mkdirs();
        }
        FTPFile[] ftpFiles = null;
        try {
            ftpFiles = ftpClient.listFiles();
            if (ftpFiles == null || ftpFiles.length == 0) {
                return 0;
            }
        } catch (IOException e) {
            log.error(e.getMessage(), e);
            return 0;
        }
        int c = 0;
        for (int i = 0; i < ftpFiles.length; i++) {
            FileOutputStream fos = null;
            try {
                String name = ftpFiles[i].getName();
                fos = new FileOutputStream(new File(dir.getAbsolutePath() + File.separator + name));
                System.out.println("<<<开始下载文件：" + name);

                long now = System.currentTimeMillis();
                boolean ok = ftpClient.retrieveFile(new String(name.getBytes("UTF-8"), "ISO-8859-1"), fos);
                //下载成功
                if (ok) {
                    long times = System.currentTimeMillis() - now;
                    log.info("下载成功：大小：{},上传时间：{}秒", formatSize(ftpFiles[i].getSize()), times / 1000);
                    c++;
                } else {
                    log.error("下载失败");
                }
            } catch (IOException e) {
                log.error("下载失败");
                log.error(e.getMessage(), e);
            } finally {
                try {
                    if (fos != null) {
                        fos.close();
                    }
                    close(ftpClient);
                } catch (Exception e) {
                }
            }
        }
        return c;
    }

    /**
     * 列出服务器上文件和目录
     *
     * @param regStr --匹配的正则表达式
     */
    public void listRemoteFiles(String regStr) {
        try {
            FTPClient ftpClient = connect();
            String[] files = ftpClient.listNames();
            FTPFile[] ftpFile = ftpClient.listFiles();
            //查看当前目录
            String workingDirectory = ftpClient.printWorkingDirectory();
            System.out.println(workingDirectory);
            List<FTPFile> ftpFileList = Arrays.asList(ftpFile);
            ftpFileList.sort(new Comparator<FTPFile>() {
                @Override
                public int compare(FTPFile o1, FTPFile o2) {
                    return Integer.parseInt(o1.getName()) - Integer.parseInt(o2.getName());
                }
            });
            ftpClient.configure(new FTPClientConfig(MyFTPEntryParser.class.getName()));
            //获取指定目录下的文件及目录
            FTPListParseEngine engine = ftpClient
                    .initiateListParsing("/upload/data/");
            workingDirectory = ftpClient.printWorkingDirectory();
            System.out.println(workingDirectory);
            System.out.println(engine.hasNext());
            while(engine.hasNext()){
                FTPFile[] files2 = engine.getNext(5);
                for(int i=0;i<files2.length;i++){
                    //获取文件名
                    System.out.println(files2[i].getName());
                    //获取文件大小
                    long size = files2[i].getSize();
                    System.out.println(size/1024+"kb");
                }
            }
            log.info("排序后的文件名:" + ftpFileList);
            if (files == null || files.length == 0) {
                log.info("没有任何文件!");
            } else {
                for (int i = 0; i < files.length; i++) {
                    log.info(files[i]);
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private static final DecimalFormat DF = new DecimalFormat("#.##");

    /**
     * 格式化文件大小（B，KB，MB，GB）
     *
     * @param size
     * @return
     */
    private String formatSize(long size) {
        if (size < 1024) {
            return size + " B";
        } else if (size < 1024 * 1024) {
            return size / 1024 + " KB";
        } else if (size < 1024 * 1024 * 1024) {
            return (size / (1024 * 1024)) + " MB";
        } else {
            double gb = size / (1024 * 1024 * 1024);
            return DF.format(gb) + " GB";
        }
    }
}
