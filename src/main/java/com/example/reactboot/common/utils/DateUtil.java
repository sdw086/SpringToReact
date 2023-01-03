package com.example.reactboot.common.utils;

import java.io.IOException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

public class DateUtil {
    public DateUtil() {
    }
    
    /**
     * 구분자로 날짜 형식으로 반환해준다
     * @param date 날짜
     * @param delimiter 날짜 사이 구분자 ex) '/' 입력시 20220505 => 2022/05/05
     * @return String
     */
    public static String convertDate(String date, String delimiter) {
        if (date.length() < 8) {
            return date;
        } else {
            String strRetValue          = "";
            char[] arrCDate             = date.toCharArray();
            StringBuffer sb             = new StringBuffer(strRetValue);

            for(int count = 0; count < 8; ++count) {
                sb.append(arrCDate[count]);
                if (count == 3 || count == 5) {
                    sb.append(delimiter);
                }
            }

            strRetValue = sb.toString();
            return strRetValue;
        }
    }
    
    /**
     * 날짜사이 구분자 삭제
     * @param date 구분자가 있는 날짜 입력
     * @return
     */
    public static String reConvertDate(String date) {
        String replaceDate          = date.replace(".", "");
        replaceDate                 = replaceDate.replace("-", "");
        replaceDate                 = replaceDate.replace(":", "");
        replaceDate                 = replaceDate.replace("/", "");
        replaceDate                 = replaceDate.replace(" ", "");
        return replaceDate;
    }
    
    /**
     * 이전 날짜가 이후 날짜보다 큰지 작은지 비교 함수
     * @param prevDate 이전날짜 (yyyyMMdd)
     * @param nextDate 이후날짜 (yyyyMMdd)
     * @return boolean
     */
    public static boolean isDateCompare(String prevDate, String nextDate) {
        SimpleDateFormat dateFormat   = new SimpleDateFormat("yyyyMMdd");

        try {
            Date datePrev           = dateFormat.parse(prevDate);
            Date dateNextDate       = dateFormat.parse(nextDate);
            int iComPare            = datePrev.compareTo(dateNextDate);
            return iComPare > 0;
        } catch (ParseException e) {
            e.printStackTrace();
            return false;
        }
    }
    
    /**
     * data format 변경
     * @param date 날짜
     * @return
     */
    public static String getDateFormat(Object date) {
        return getDateFormat(date, "/");
    }
    
    /**
     * 날짜 정보 사이에 구분자를 넣어주는 함수
     *
     * @param date 날짜
     * @param delimiter 날짜사이 구분자
     * @return
     */
    public static String getDateFormat(Object date, String delimiter) {
        String result                   = "";
        if (date == null) {
            return result;
        } else {
            String day                  = date.toString();
            if (day != null && !day.equals("") && delimiter != null) {
                if (day.length() == 6) {
                    result              = day.substring(0, 4) + delimiter + day.substring(4, 6);
                } else if (day.length() < 8) {
                    result              = day;
                } else if (delimiter.equals("YMD")) {
                    result              = day.substring(0, 4) + "년 " + day.substring(4, 6) + "월 " + day.substring(6) + "일";
                } else {
                    result              = day.substring(0, 4) + delimiter + day.substring(4, 6) + delimiter + day.substring(6);
                }
            } else {
                result                  = "";
            }

            return result;
        }
    }
    
    /**
     * 오늘 날짜 값 가져오기
     * @param delimiter 날짜 사이 구분자
     * @param format 포멧 형태 ex) YYYY/MM/DD
     * @return String
     */
    public static String getCurrentDate(String delimiter, String format) {
        String result               = null;

        try {
            Calendar currentWhat    = Calendar.getInstance();

            int currentYear         = currentWhat.get(Calendar.YEAR);
            int currentMonth        = currentWhat.get(Calendar.MONTH) + 1;
            int currentDay          = currentWhat.get(Calendar.DAY_OF_MONTH);
            int currentHour         = currentWhat.get(Calendar.HOUR);
            int currentMinute       = currentWhat.get(Calendar.MINUTE);
            int currentSecond       = currentWhat.get(Calendar.SECOND);

            String yearToday        = StringUtil.padLeftwithZero(currentYear, 4);      // 4자리 스트링으로 변환
            String monthToday       = StringUtil.padLeftwithZero(currentMonth, 2);     // 2자리 스트링으로 변환
            String dayToday         = StringUtil.padLeftwithZero(currentDay, 2);       // 2자리 스트링으로 변환
            String hourToday        = StringUtil.padLeftwithZero(currentHour, 2);      // 2자리 스트링으로 변환
            String minuteToday      = StringUtil.padLeftwithZero(currentMinute, 2);    // 2자리 스트링으로 변환
            String secondToday      = StringUtil.padLeftwithZero(currentSecond, 2);    // 2자리 스트링으로 변환

            if (format.equals("YYYY/MM/DD HH:MI:SS")) {
                result              = new String(yearToday + "/" + monthToday + "/" + dayToday + " " + hourToday + ":" + minuteToday + ":" + secondToday);
            }else if (format.equals("YYYY.MM.DD HH:MI:SS")) {
                result              = new String(yearToday + "." + monthToday + "." + dayToday + " " + hourToday + ":" + minuteToday + ":" + secondToday);
            } else if (format.equals("YYYY-MM-DD")) {
                result              = new String(yearToday + "-" + monthToday + "-" + dayToday);
            } else if (format.equals("YYYYMMDD-HHMISS")) {
                result              = new String(yearToday + monthToday + dayToday + "-" + hourToday + minuteToday + secondToday);
            } else if (format.equals("YYYYMMDDHHMISS")) {
                result              = new String(yearToday + monthToday + dayToday + hourToday + minuteToday + secondToday);
            } else if (format.equals("YYYYMMDD")) {
                result              = new String(yearToday + monthToday + dayToday);
            } else if (format.equals("HH:MI:SS")) {
                result              = new String(hourToday + ":" + minuteToday + ":" + secondToday);
            } else if (format.equals("HHMISS")) {
                result              = new String(hourToday + minuteToday + secondToday);
            } else if (format.equals("YYYY")) {
                result              = new String(yearToday);
            } else if (format.equals("MM")) {
                result              = new String(monthToday);
            } else if (format.equals("DD")) {
                result              = new String(dayToday);
            } else if (format.equals("HHMI")) {
                result              = new String(hourToday + minuteToday);
            } else if (format.equals("HH:MI")) {
                result              = new String(hourToday + ":" + minuteToday);
            } else if (format.equals("dafault")) {
                result              = new String(yearToday + delimiter + monthToday + delimiter + dayToday);
            } else {
                result              = new String(yearToday + delimiter + monthToday + delimiter + dayToday);
            }
        } catch (Exception e) {
            e.printStackTrace();
            result                  = "";
        }

        return result;
    }
    
    /**
     * 오늘 날짜 구하기
     * @return String
     */
    public static String getCurrentDate() {
        return getCurrentDate("", "");
    }
    
    /**
     * 날짜사이의 기간을 가저온다
     * @param startDate 시작일 ex) YYYYMMDD
     * @param endDate 종료일 ex) YYYYMMDD
     * @return int
     * @throws IOException
     */
    public static int getPeriodByDay(String startDate, String endDate) throws IOException {
        long endTimeStamp           = Long.parseLong(getTimeStamp(endDate));
        long startTimeStamp         = Long.parseLong(getTimeStamp(startDate));
        long periodBySecond         = endTimeStamp - startTimeStamp;
        double fPeriodByDay         = (double)(periodBySecond / 86400L);
        return (int)Math.ceil(fPeriodByDay);
    }
    
    /**
     * 1970년 1월 1일 0시 0분 0초 부터 지정된 날짜까지의 초를 가져온다.
     * @param date 날짜
     * @return Stiring
     * @throws IOException
     */
    private static String getTimeStamp(String date) throws IOException {
        int endYear		        = Integer.parseInt(date.substring(0, 4));
        int endMonth	        = Integer.parseInt(date.substring(4, 6));
        int endDay		        = Integer.parseInt(date.substring(6, 8));

        Calendar cal = Calendar.getInstance();
        cal.set(endYear         , endMonth, endDay, 0, 0, 0);

        String strTimeStamp     = "";

        long timeStamp          = 0L;
        long stdYear            = 1970L;

        long passedYear         = (cal.get(Calendar.YEAR) - stdYear) * 365 * 24 * 60 * 60;
        long passedDay          = cal.get(Calendar.DAY_OF_YEAR) * 24 * 60 * 60;
        long passedTime         = (cal.get(Calendar.HOUR) * 60 * 60) + (cal.get(Calendar.MINUTE) * 60) + cal.get(Calendar.SECOND);

        timeStamp               = passedYear + passedDay + passedTime;

        strTimeStamp            = Long.toString(timeStamp);

        while (strTimeStamp.length() < 12) {
            strTimeStamp        = "0" + strTimeStamp;
        }

        return strTimeStamp;
    }
    
    /**
     * 현재 날짜 기준으로 가공한 날짜값 가져오기
     * @param delimiter
     * @param format
     * @param dateItemFormat
     * @param dateItemFormatNumberValue
     * @return
     */
    public static String getCurrentDateValueFormat(String delimiter, String format, String dateItemFormat, int dateItemFormatNumberValue) {
        String result                   = null;

        try {
            Calendar currentWhat = Calendar.getInstance();
            if ("year".equals(StringUtil.nvl(dateItemFormat, ""))) {
                currentWhat.add(1, dateItemFormatNumberValue);
            } else if ("month".equals(StringUtil.nvl(dateItemFormat, ""))) {
                currentWhat.add(2, dateItemFormatNumberValue);
            } else if ("day".equals(StringUtil.nvl(dateItemFormat, ""))) {
                currentWhat.add(5, dateItemFormatNumberValue);
            } else if ("hour".equals(StringUtil.nvl(dateItemFormat, ""))) {
                currentWhat.add(11, dateItemFormatNumberValue);
            } else if ("minute".equals(StringUtil.nvl(dateItemFormat, ""))) {
                currentWhat.add(12, dateItemFormatNumberValue);
            } else if ("second".equals(StringUtil.nvl(dateItemFormat, ""))) {
                currentWhat.add(13, dateItemFormatNumberValue);
            }

            int currentYear             = currentWhat.get(1);
            int currentMonth            = currentWhat.get(2) + 1;
            int currentDay              = currentWhat.get(5);
            int currentHour             = currentWhat.get(11);
            int currentMinute           = currentWhat.get(12);
            int currentSecond           = currentWhat.get(13);
            String yearToday            = StringUtil.padLeftwithZero(currentYear        , 4);
            String monthToday           = StringUtil.padLeftwithZero(currentMonth       , 2);
            String dayToday             = StringUtil.padLeftwithZero(currentDay         , 2);
            String hourToday            = StringUtil.padLeftwithZero(currentHour        , 2);
            String minuteToday          = StringUtil.padLeftwithZero(currentMinute      , 2);
            String secondToday          = StringUtil.padLeftwithZero(currentSecond      , 2);
            if (format.equals("YYYY/MM/DD HH:MI:SS")) {
                result                  = new String(yearToday + "/" + monthToday + "/" + dayToday + " " + hourToday + ":" + minuteToday + ":" + secondToday);
            } else if (format.equals("YYYY.MM.DD HH:MI:SS")) {
                result                  = new String(yearToday + "." + monthToday + "." + dayToday + " " + hourToday + ":" + minuteToday + ":" + secondToday);
            } else if (format.equals("YYYY-MM-DD")) {
                result                  = new String(yearToday + "-" + monthToday + "-" + dayToday);
            } else if (format.equals("YYYYMMDD-HHMISS")) {
                result                  = new String(yearToday + monthToday + dayToday + "-" + hourToday + minuteToday + secondToday);
            } else if (format.equals("YYYYMMDDHHMISS")) {
                result                  = new String(yearToday + monthToday + dayToday + hourToday + minuteToday + secondToday);
            } else if (format.equals("YYYYMMDD")) {
                result                  = new String(yearToday + monthToday + dayToday);
            } else if (format.equals("HH:MI:SS")) {
                result                  = new String(hourToday + ":" + minuteToday + ":" + secondToday);
            } else if (format.equals("HHMISS")) {
                result                  = new String(hourToday + minuteToday + secondToday);
            } else if (format.equals("YYYY")) {
                result                  = new String(yearToday);
            } else if (format.equals("MM")) {
                result                  = new String(monthToday);
            } else if (format.equals("DD")) {
                result                  = new String(dayToday);
            } else if (format.equals("HHMI")) {
                result                  = new String(hourToday + minuteToday);
            } else if (format.equals("HH:MI")) {
                result                  = new String(hourToday + ":" + minuteToday);
            } else if (format.equals("dafault")) {
                result                  = new String(yearToday + delimiter + monthToday + delimiter + dayToday);
            } else {
                result                  = new String(yearToday + delimiter + monthToday + delimiter + dayToday);
            }
        } catch (Exception e) {
            e.printStackTrace();
            result                      = "";
        }

        return result;
    }
    
    /**
     * 현재 날짜와 비교.
     * 값이 작으면 -1, 같으면 0, 크면 1을 반환한다.
     * @param data 날짜
     * @return
     */
    public static int isCurrentDateCompare(Object data) {
        if (data != null && !"".equals(data.toString())) {
            String compareDate          = StringUtil.nvl(data);
            String currentDate           = getCurrentDate();
            return compareDate.compareTo(currentDate);
        } else {
            return -2;
        }
    }
    
    /**
     * 기준일에 add 갯수 만큼의 월수를 증가 시킨다.
     * @param date 기준일
     * @param add 증가 수
     * @return String
     */
    public static String addMonth(String date, int add) {
        String result                           = null;

        try {
            String year                         = date.substring(0          , 4);
            String month                        = date.substring(4          , 6);
            String day                          = date.substring(6          , 8);
            SimpleDateFormat formatter          = new SimpleDateFormat("yyyy.MM.dd HH:mm:ss");
            formatter.getCalendar().set(Integer.parseInt(year)              , Integer.parseInt(month) - 1, Integer.parseInt(day), 0, 0, 0);
            formatter.getCalendar().add(2   , add);
            Date chkDay                         = formatter.getCalendar().getTime();
            result                              = formatter.format(chkDay);
            year                                = result.substring(0        , 4);
            month                               = result.substring(5        , 7);
            day                                 = result.substring(8        , 10);
            result                              = year + month + day;
        } catch (Exception e) {
            e.printStackTrace();
            result                              = "";
        }

        return result;
    }
    
    /**
     * 기준일에 add 갯수 만큼의 일자를 증가시킨다.
     * @param date 기준일
     * @param add 증가 수
     * @return
     */
    public static String addDay(String date, int add) {
        String result                           = null;

        try {
            String year                         = date.substring(0          , 4);
            String month                        = date.substring(4          , 6);
            String day                          = date.substring(6          , 8);
            SimpleDateFormat formatter          = new SimpleDateFormat("yyyy.MM.dd HH:mm:ss");
            formatter.getCalendar().set(Integer.parseInt(year)              , Integer.parseInt(month) - 1, Integer.parseInt(day), 0, 0, 0);
            formatter.getCalendar().add(5   , add);
            Date chkDay                         = formatter.getCalendar().getTime();
            result                              = formatter.format(chkDay);
            year                                = result.substring(0        , 4);
            month                               = result.substring(5        , 7);
            day                                 = result.substring(8        , 10);
            result                              = year + month + day;
        } catch (Exception e) {
            e.printStackTrace();
            result = "";
        }

        return result;
    }
    
    /**
     * 요일 불러오기
     * @param date 날짜
     * @return String
     */
    public static String getWeekdayName(String date) {
        if (date != null && !"".equals(date)) {
            int year                = Integer.parseInt(date.substring(0         , 4));
            int month               = Integer.parseInt(date.substring(4         , 6));
            int day                 = Integer.parseInt(date.substring(6         , 8));
            
            if (month == 1 || month == 2) {
                --year;
            }

            month                   = (month + 9) % 12 + 1;
            int yRest               = year % 100;
            int century             = year / 100;
            int week                = ((13 * month - 1) / 5 + day + yRest + yRest / 4 + century / 4 - 2 * century) % 7;
            
            if (week < 0) {
                week                = (week + 7) % 7;
            }

            String result           = "";
            switch(week) {
            case 0:
                result              = "SUN";
                break;
            case 1:
                result              = "MON";
                break;
            case 2:
                result              = "TUE";
                break;
            case 3:
                result              = "WED";
                break;
            case 4:
                result              = "THU";
                break;
            case 5:
                result              = "FRI";
                break;
            case 6:
                result              = "SAT";
            }

            return result;
        } else {
            return "";
        }
    }
    
    /**
     * 요일 번호를 얻음.(0:일요일)
     * @param date
     * @return int
     */
    public static int getWeekdayNum(String date) {
        if (date != null && !"".equals(date)) {
            int year            = Integer.parseInt(date.substring(0, 4));
            int month           = Integer.parseInt(date.substring(4, 6));
            int day             = Integer.parseInt(date.substring(6, 8));
            int restYear        = 0;
            int century         = 0;
            int result          = 0;
            
            if (month == 1 || month == 2) {
                --year;
            }

            month               = (month + 9) % 12 + 1;
            restYear            = year % 100;
            century             = year / 100;
            result = ((13 * month - 1) / 5 + day + restYear + restYear / 4 + century / 4 - 2 * century) % 7;
            
            if (result < 0) {
                result          = (result + 7) % 7;
            }

            return result;
        } else {
            return 0;
        }
    }
    
    /**
     * 1 ~ 4분기 구하기
     * @param date 날짜
     * @return String
     */
    public static String getSettle(String date) {
        String result           = "";
        int iMonth              = 0;
        if ("".equals(date)) {
            date                = getCurrentDate();
        } else {
            iMonth              = Integer.parseInt(date.substring(4, 6));
            if (iMonth <= 3) {
                result          = "1";
            } else if (iMonth <= 6) {
                result          = "2";
            } else if (iMonth <= 9) {
                result          = "3";
            } else if (iMonth <= 12) {
                result          = "4";
            }
        }

        return result;
    }

    /**
     * 현재 로컬 날짜시간 불러오기
     * @return String
     */
    public static String getNow() {
        String now          = "";
        String[] nows       = new String[3];
        int[] date          = new int[3];
        Calendar cal        = Calendar.getInstance();
        date[0]             = cal.get(2) + 1;
        date[1]             = cal.get(5);
        date[2]             = cal.get(11);

        for(int i = 0; i < date.length; ++i) {
            if (date[i] < 10) {
                nows[i]     = "0" + (new Integer(date[i])).toString();
            } else {
                nows[i]     = (new Integer(date[i])).toString();
            }

            now             = now + nows[i];
        }

        return cal.get(1) + now;
    }
    
    /**
     * 해당 월 마지막 일자 가져오기
     * @param date 날짜
     * @return int
     */
    public static int getLastDay(String date) {
        int result;
        int year            = Integer.parseInt(date.substring(0     , 4));
        int mon             = Integer.parseInt(date.substring(4     , 6));
        switch(mon) {
        case 2:
            result          = getYoonMonth(year);
            break;
        case 3:
        case 5:
        case 7:
        case 8:
        case 10:
        default:
            result          = 31;
            break;
        case 4:
        case 6:
        case 9:
        case 11:
            result          = 30;
        }

        return result;
    }
    
    /**
     * 윤달여부 Check
     * 해당년도가 윤달이 끼어있는 년도인지 판단하여 값을 반환한다.
     * @param year 년도 (yyyy)
     * @return int
     */
    public static int getYoonMonth(int year) {
        int fyear           = year / 100;
        int byear           = year % 100;
        byte result;
        if (fyear % 4 == 0 && year % 4 == 0) {
            result          = 29;
        } else if (byear != 0 && byear % 4 == 0) {
            result          = 29;
        } else {
            result          = 28;
        }
 
        return result;
    }
    
    /**
     * 년월일 사이에 구분자 sep를 첨가한다. 구분자가 "/"인 경우 "yyyymmdd" -> "yyyy/mm/dd"가 된다.
     * @param date 날짜
     * @param delimiter 구분자
     * @return
     */
    public static String date(String date, String delimiter) {
        String result               = null;
        int nLen                    = 0;
        if (date == null) {
            result                  = "";
        } else {
            nLen                    = date.length();
            if (nLen != 8) {
                result              = date;
            } else if (!date.equals("00000000") && !date.equals("       0") && !date.equals("        ")) {
                result              = date.substring(0, 4) + delimiter + date.substring(4, 6) + delimiter + date.substring(6, 8);
            } else {
                result              = "";
            }
        }

        return result;
    }
}