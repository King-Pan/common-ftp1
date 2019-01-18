package com.asiainfo.commonftp.camel;

import lombok.extern.slf4j.Slf4j;
import org.apache.camel.LoggingLevel;
import org.apache.camel.builder.RouteBuilder;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

/**
 * @author king-pan
 * @date 2019/1/9
 * @Description ${DESCRIPTION}
 */
@Slf4j
@Component
public class SftpUploadRoute extends RouteBuilder {

    @Value("${sftp.server.uri}")
    private String sftpUri;

    @Override
    public void configure() throws Exception {
        from("file:D:\\TestData\\sunknew\\sftp\\temp").to(sftpUri)
                .log(LoggingLevel.INFO, log, "download file ${file:name} complete.");
    }
}
