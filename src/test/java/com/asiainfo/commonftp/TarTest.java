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
import java.util.List;

/**
 * @author king-pan
 * @date 2019/1/3
 * @Description ${DESCRIPTION}
 */
public class TarTest {

    private static final Integer BUFFER = 100 * 1024;

    @Test
    public void test() throws Exception {
        File srcFile = new File("D:\\TestData\\sunknew\\sftp\\data\\20190106\\day\\music\\i_10000_20190106_IOP-96000_00_001.tar");
        System.out.println(srcFile.getAbsolutePath());
//        TarArchiveInputStream tais = new TarArchiveInputStream(
//                new FileInputStream(srcFile));
        TarUtil.dearchive(srcFile.getAbsolutePath(), "d:/fds2");


    }


    @Test
    public void testFile(){
        File srcFile = new File("D:\\TestData\\sunknew\\sftp\\data\\20190106\\day\\music\\i_10000_20190106_IOP-96000_00_001.tar");
        System.out.println(srcFile.getName());
        System.out.println(srcFile.getAbsolutePath());
    }


    @Test
    public void testTar2(){
        File tarFile = new File("D:\\TestData\\sunknew\\sftp\\data\\20190106\\day\\music\\i_10000_20190106_IOP-96000_00_001.tar");
        String tmpPath = "D:\\TestData\\sunknew\\sftp\\tmp";
        try {
            List<File> fileList = TarUtil.dearchive(tarFile, tmpPath);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
