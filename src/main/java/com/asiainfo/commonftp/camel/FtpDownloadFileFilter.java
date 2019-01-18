package com.asiainfo.commonftp.camel;

import club.javalearn.date.utils.DateUtil;
import com.asiainfo.commonftp.entity.FileInfo;
import lombok.extern.slf4j.Slf4j;
import org.apache.camel.component.file.GenericFile;
import org.apache.camel.component.file.GenericFileFilter;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import java.io.File;

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

    @Value("${ftp.allDownload}")
    private Boolean allDownload;

    /**
     * 过滤下载文件
     *
     * @author sunk
     */
    @Override
    public boolean accept(GenericFile<Object> file) {
        String localFilePath = localDir + "/" + file.getFileName();
        log.info("文件名称:{},文件类型:{},目录:{}", file.getFileName(), file.getFile().getClass().getName(), file.isDirectory() ? "是" : "否");

        //如果设置全量下载，则直接返回true
        if (allDownload) {
            return true;
        }
        if (file.isDirectory() && file.getFileName().startsWith(DateUtil.getLastMonday())) {
            return true;
        } else {
            File localFile = new File(localFilePath);
            //本地如果存在准备下载的文件，则不下载
            if (localFile.exists()) {
                return false;
            }

            boolean flag = file.getFileName().endsWith(".tar") && file.getFileName().startsWith(DateUtil.getLastMonday());
            if (flag) {
                flag = true;
            }
            return flag;
        }
    }
}
