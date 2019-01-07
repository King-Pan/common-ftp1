package com.asiainfo.commonftp.entity;

import lombok.Data;

import java.io.File;
import java.util.List;

/**
 * @author king-pan
 * @date 2019/1/7
 * @Description 文件信息
 */
@Data
public class FileInfo {

    /**
     * 文件下载名称
     */
    private String downLoadName;

    /**
     * jar包中的文件
     */
    private List<File> tarFiles;
    /**
     *
     */
    private String unTarName;


}
