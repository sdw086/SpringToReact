package com.example.reactboot.common.utils;

import java.io.IOException;
import java.util.Calendar;

public class OrderNoUtil {

    private static StringUtil stringUtil;

    public OrderNoUtil() {
    }

    /**
     * 주문 번호 생성
     */
    public static String orderNo() throws IOException {
        Calendar currentWhat            = Calendar.getInstance();
        int currentYear                 = currentWhat.get(Calendar.YEAR);
        int currentMonth                = currentWhat.get(Calendar.MONTH) + 1;
        int currentDay                  = currentWhat.get(Calendar.DATE);
        int currentHour                 = currentWhat.get(Calendar.HOUR_OF_DAY);
        int currentMinute               = currentWhat.get(Calendar.MINUTE);
        int currentSecond               = currentWhat.get(Calendar.SECOND);

        String yearToday                = stringUtil.padLeftwithZero(currentYear  , 4);
        String monthToday               = stringUtil.padLeftwithZero(currentMonth , 2);
        String dayToday                 = stringUtil.padLeftwithZero(currentDay   , 2);
        String hourToday                = stringUtil.padLeftwithZero(currentHour  , 2);
        String minuteToday              = stringUtil.padLeftwithZero(currentMinute, 2);
        String secondToday              = stringUtil.padLeftwithZero(currentSecond, 2);

        // 주문 번호 생성
        int strAuthNo                   = (int)(Math.random() * 90000) + 10000;
        String orderNo                  = yearToday + monthToday + dayToday + hourToday + minuteToday + secondToday + strAuthNo;

        return orderNo;
    }
}
