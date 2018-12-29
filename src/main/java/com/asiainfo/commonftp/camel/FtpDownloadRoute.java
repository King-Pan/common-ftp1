package com.asiainfo.commonftp.camel;

import lombok.extern.slf4j.Slf4j;
import org.apache.camel.LoggingLevel;
import org.apache.camel.builder.RouteBuilder;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

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

    @Override
    public void configure() throws Exception {
        from(ftpUri).to("file:" + localDir)
                .log(LoggingLevel.INFO, log, "download file ${file:name} complete.");
    }

    /**
     * 判断是否为执行任务的主机
     *
     * @author sunk
     */
    private boolean isExecHost() {
        String hostName = "";
        try {
            hostName = InetAddress.getLocalHost().getHostName();
        } catch (Exception e) {
            log.error("get hostname fail !", e);
            return false;
        }
        return true;
    }

}
