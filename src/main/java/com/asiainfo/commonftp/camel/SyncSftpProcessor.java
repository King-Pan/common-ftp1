package com.asiainfo.commonftp.camel;

import lombok.extern.slf4j.Slf4j;
import org.apache.camel.Exchange;
import org.apache.camel.LoggingLevel;
import org.apache.camel.Processor;
import org.apache.camel.builder.RouteBuilder;
import org.apache.camel.main.Main;
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
public class SyncSftpProcessor implements Processor {


    @Override
    public void process(Exchange exchange) throws Exception {
        log.info("开始同步");
        log.info("从Exchange中获取解压文件列表");
        List<File> fileList = (List<File>) exchange.getProperties().get("listFile");
        log.info("同步文件名称");
        for (File file : fileList) {
            log.info(file.getAbsolutePath());
        }
        Main main = new Main();
        main.addRouteBuilder(new SftpUploadRoute());

    }

    private static class SftpUploadRoute extends RouteBuilder {


        @Override
        public void configure() throws Exception {
            log.info("=========================SftpUploadRoute====================================");
            from("file:" + "D:\\TestData\\sunknew\\sftp\\tmp\\home\\ceshi\\bin\\hivedata\\music\\20190106\\000000_0").to("file:" + "D:\\TestData\\sunknew\\sftp\\target").
                    log(LoggingLevel.INFO, log, "download file ${file:name} complete.");
            log.info("=========================SftpUploadRoute====================================");
        }
    }
}
