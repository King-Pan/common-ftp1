package com.asiainfo.commonftp.util;

import lombok.extern.slf4j.Slf4j;
import org.apache.commons.net.ftp.FTPClientConfig;
import org.apache.commons.net.ftp.FTPFile;
import org.apache.commons.net.ftp.parser.ConfigurableFTPFileEntryParserImpl;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

/**
 * @author king-pan
 * @date 2018/12/29
 * @Description ${DESCRIPTION}
 */
@Slf4j
public class MyFTPEntryParser extends ConfigurableFTPFileEntryParserImpl {
    private Class clazz = MyFTPEntryParser.class;

    public MyFTPEntryParser() {
        this("");
    }

    public MyFTPEntryParser(String regex) {
        super("");
    }

    protected FTPClientConfig getDefaultConfiguration() {
        return new FTPClientConfig(clazz.getPackage().getName()
                + clazz.getSimpleName(), "", "", "", "", "");
    }

    @Override
    public FTPFile parseFTPEntry(String entry) {
        log.debug("开始解析，内容为: " + entry);

        FTPFile file = new FTPFile();
        file.setRawListing(entry);

        String[] temp = entry.split("\\s+");
        if (temp.length != 8) {
            return null;
        }
        String fileType = temp[0].substring(0, 1);
        if ("d".equals(fileType)) {
            file.setType(FTPFile.DIRECTORY_TYPE);
        } else {
            file.setType(FTPFile.FILE_TYPE);
            file.setSize(Integer.valueOf(temp[4]));
        }
        file.setName(temp[7]);
        file.setUser(temp[3]);

        Calendar date = Calendar.getInstance();
        Date fileDate;
        // 返回【6月22 2010】形式的日期
        if (temp[6].matches("\\d{4}")) {
            try {
                fileDate = new SimpleDateFormat("yyyyMM月dd")
                        .parse(temp[6] + temp[5]);
            } catch (ParseException e) {
                throw new RuntimeException("日期解析出错", e);
            }
            // 返回【6月22 16时56】形式的日期
        } else {
            int yyyy = date.get(Calendar.YEAR);
            try {
                fileDate = new SimpleDateFormat("yyyyMM月ddHH时mm")
                        .parse(yyyy + temp[5] + temp[6]);
            } catch (ParseException e) {
                throw new RuntimeException("日期解析出错", e);
            }
        }
        date.setTime(fileDate);
        file.setTimestamp(date);

        return file;
    }
}