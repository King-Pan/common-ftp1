package com.asiainfo.commonftp.camel;

import lombok.extern.slf4j.Slf4j;
import org.apache.camel.component.file.GenericFile;
import org.apache.camel.component.file.GenericFileFilter;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Calendar;
import java.util.List;
import java.util.stream.Collectors;

/**
 * @author king-pan
 * @date 2018/12/29
 * @Description ${DESCRIPTION}
 */
@Slf4j
@Component
public class FtpDownloadFileFilter implements GenericFileFilter<Object> {

    @Value("${ftp.local.data.dir}")
    private String localDir;

    /**
     * 过滤下载文件
     *
     * @author sunk
     */
    @Override
    public boolean accept(GenericFile<Object> file) {
        System.out.println(file.getFileName());
        return file.getFileName().endsWith(".tar") || file.isDirectory();
    }
}
