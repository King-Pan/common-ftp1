package com.asiainfo.commonftp.camel;

import com.asiainfo.commonftp.util.TarUtil;
import lombok.extern.slf4j.Slf4j;
import org.apache.camel.Exchange;
import org.apache.camel.Message;
import org.apache.camel.Processor;
import org.apache.camel.component.file.GenericFile;
import org.apache.commons.net.ftp.FTPFile;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import java.io.File;
import java.util.List;

/**
 * @author king-pan
 * @date 2019/1/3
 * @Description ${DESCRIPTION}
 */
@Slf4j
@Component
public class TarProcessor implements Processor {

    @Value("${ftp.local.data.dir}")
    private String localDir;

    @Value("${ftp.local.work.directory}")
    private String tmpPath;

    @Override
    public void process(Exchange exchange) throws Exception {
        log.info("开始解压");
        Message message = exchange.getIn();
        GenericFile<?> gf = (GenericFile<?>) message.getBody();
        FTPFile ftpFile = (FTPFile) gf.getFile();
        System.out.println(ftpFile.getName());
        System.out.println(ftpFile.isFile());

        String localFilePath = localDir + "/" + gf.getFileName();

        File tarFile = new File(localFilePath);
        log.info("下载文件名:{},是否存在:{}", localFilePath, tarFile.exists() ? "存在" : "不存在");
        List<File> fileList = TarUtil.dearchive(tarFile, tmpPath);
        exchange.getProperties().put("listFile", fileList);
        log.info("解压文件内容");
        for (File file : fileList) {
            log.info(file.getAbsolutePath());
        }
        log.info("解压完成");
    }
}
