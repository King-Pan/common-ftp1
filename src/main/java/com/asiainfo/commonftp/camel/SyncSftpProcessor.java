package com.asiainfo.commonftp.camel;

import org.apache.camel.Exchange;
import org.apache.camel.Processor;

/**
 * @author king-pan
 * @date 2019/1/3
 * @Description ${DESCRIPTION}
 */
public class SyncSftpProcessor implements Processor {
    @Override
    public void process(Exchange exchange) throws Exception {
        System.out.println("开始同步");
        System.out.println("同步完成");
    }
}
