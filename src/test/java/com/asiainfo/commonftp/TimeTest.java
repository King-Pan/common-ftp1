package com.asiainfo.commonftp;

import org.joda.time.DateTime;
import org.junit.Test;

/**
 * @author king-pan
 * @date 2019/1/4
 * @Description ${DESCRIPTION}
 */
public class TimeTest {

    @Test
    public void test() {
        DateTime now = new DateTime();
        now = now.withDayOfWeek(7);
        now = now.plusDays(-7);
        System.out.println(now.toString("yyyyMMdd"));

    }
}
