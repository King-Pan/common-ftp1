package com.asiainfo.commonftp.util;

import com.asiainfo.commonftp.entity.FileInfo;

/**
 * @author king-pan
 * @date 2019/1/7
 * @Description ${DESCRIPTION}
 */
public class DownLoadUtil {

    public static final ThreadLocal<FileInfo> INFOS = new ThreadLocal<>();
}
