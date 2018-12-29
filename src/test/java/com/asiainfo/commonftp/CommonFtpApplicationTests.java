package com.asiainfo.commonftp;

import com.asiainfo.commonftp.util.FtpClientUtil;
import org.apache.commons.net.ftp.FTPClient;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

@RunWith(SpringRunner.class)
@SpringBootTest
public class CommonFtpApplicationTests {

    @Autowired
    private FtpClientUtil util;

    @Test
    public void contextLoads() {
       FTPClient client = util.connect();
        util.listRemoteFiles("upload/data");
    }

}

