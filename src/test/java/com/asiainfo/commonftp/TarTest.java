package com.asiainfo.commonftp;

import com.asiainfo.commonftp.util.TarUtil;
import org.apache.commons.compress.archivers.tar.TarArchiveEntry;
import org.apache.commons.compress.archivers.tar.TarArchiveInputStream;
import org.apache.commons.compress.archivers.tar.TarUtils;
import org.junit.Test;

import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;

/**
 * @author king-pan
 * @date 2019/1/3
 * @Description ${DESCRIPTION}
 */
public class TarTest {

    private static final Integer BUFFER = 100 * 1024;

    @Test
    public void test() throws Exception {
        File srcFile = new File("D:\\TestData\\sunknew\\sftp\\data\\20181230\\day\\music\\i_10000_20181230_IOP-96000_00_001.tar");
        System.out.println(srcFile.getAbsolutePath());
//        TarArchiveInputStream tais = new TarArchiveInputStream(
//                new FileInputStream(srcFile));
        TarUtil.dearchive(srcFile.getAbsolutePath(), "d:/fds");


    }
}
