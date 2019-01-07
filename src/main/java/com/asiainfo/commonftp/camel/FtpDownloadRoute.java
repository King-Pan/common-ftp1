package com.asiainfo.commonftp.camel;

import com.asiainfo.commonftp.util.DownLoadUtil;
import lombok.extern.slf4j.Slf4j;
import org.apache.camel.LoggingLevel;
import org.apache.camel.builder.RouteBuilder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import java.io.File;
import java.net.InetAddress;

/**
 * @author king-pan
 * @date 2018/12/29
 * @Description ${DESCRIPTION}
 */
@Slf4j
@Component
public class FtpDownloadRoute extends RouteBuilder {
    @Value("${ftp.server.uri}")
    private String ftpUri;

    @Value("${ftp.local.data.dir}")
    private String localDir;

    private String hostNodes;

    private boolean isStart = true;

    @Autowired
    private TarProcessor tarProcessor;

    @Override
    public void configure() throws Exception {

        from(ftpUri).to("file:" + localDir).process(tarProcessor).process(new SyncSftpProcessor())
                .log(LoggingLevel.INFO, log, "download file ${file:name} complete.");
    }
}
