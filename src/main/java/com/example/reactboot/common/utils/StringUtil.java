package com.example.reactboot.common.utils;

import com.nhncorp.lucy.security.xss.XssPreventer;
import org.apache.commons.lang3.RandomStringUtils;
import org.apache.commons.lang3.math.NumberUtils;

import java.io.File;
import java.io.IOException;
import java.lang.reflect.Array;
import java.text.DecimalFormat;
import java.util.Collection;
import java.util.Iterator;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class StringUtil {
	public StringUtil() {
    }
    
    /**
     * 객체 내용이 빈값 혹은 null 이면 "" 변경시켜준다.
     * @param data 객체
     * @return String
     */
    public static String nvl(Object data) {
        return nvl(data, "");
    }
    
    /**
     * 객체 내용이 빈값 혹은 null 이면 특정 숫자로 변경시켜준다.
     * @param data 객체
     * @param trans 변경할 값 ex) 0
     * @return String
     */
    public static String nvl(Object data, int trans) {
        return nvl(data, Integer.toString(trans));
    }
    
    /**
     * 객체 내용이 빈값 혹은 null 이면 특정 문자로 반환
     * @param data 객체
     * @param trans 변경할 값 ex) "", null, "null"
     * @return String
     */
    public static String nvl(Object data, String trans) {
        if (trans == null) {
            trans = "";
        }

        return data != null && !"".equals(data) && !"null".equals(data) ? data.toString().trim() : trans;
    }
    
    /**
     * 객체 내용이 빈값 혹은 null 이면 특정 숫자 반환
     * @param data
     * @param trans 변경할 값 ex) 0
     * @return int
     */
    public static int nvlInt(Object data, int trans) {
        return Integer.parseInt(nvl(data, trans));
    }

    public static float nvlFloat(Object data, int trans) {
        return Float.parseFloat(nvl(data, trans));
    }
    
    /**
     * 배열안의 값 nvl 함수 처리
     * @param array 배열
     * @param index 배열index 번호
     * @return string
     */
    public static String nvlArray(Object[] array, int index) {
        return nvlArray(array, index, "").toString();
    }
    
    /**
     * 배열안의 값 nvl 함수 처리
     * @param array 배열
     * @param index 배열index 번호
     * @param trans  nvl함수 변환 값 ex) null, "", "null"
     * @return object
     */
    public static Object nvlArray(Object[] array, int index, Object trans) {
        return StringUtil.isNotEmpty(array) && array.length > index ? nvl(array[index]) : trans;
    }
    
    /**
     * Map안의 값을 nvl 함수 처리 (String) "" 처리
     * @param data 객체
     * @param key 객제 key 값
     * @return string
     */
    public static String nvlMap(Map data, String key) {
        return nvlMap(data, key, "");
    }
    
    /**
     * Map안의 값을 nvl 함수 처리 (String)
     * @param data map
     * @param key map key 값
     * @param trans nvl함수 변환 값 ex) null, "", "null"
     * @return String
     */
    public static String nvlMap(Map data, String key, String trans) {
        String result    = "";
        if (data != null && data.containsKey(key)) {
            result       = String.valueOf(data.get(key));
        }

        return nvl(result, trans);
    }
    
    /**
     * Map안의 값을 nvl 함수 처리 (int) 0으로 처리
     * @param data map
     * @param key 객체 key 값
     * @return int
     */
    public static int nvlMapInt(Map data, String key) {
        return nvlMapInt(data, key, NumberUtils.INTEGER_ZERO);
    }
    
    /**
     * Map안의 값을 nvl 함수 처리 (int)
     * @param data map
     * @param key map key 값
     * @param trans nvl함수 변환 값 ex) 0
     * @return int
     */
    public static int nvlMapInt(Map data, String key, int trans) {
        return NumberUtils.toInt(nvlMap(data, key, Integer.toString(trans)));
    }
    
    /**
     * Map안의 값을 nvl 함수 처리 실수형 값으로 넣는다
     * @param data map
     * @param key map key 값
     * @return double
     */
    public static double nvlMapDouble(Map data, String key) {
        return nvlMapDouble(data, key, NumberUtils.DOUBLE_ZERO);
    }
    
    /**
     * Map안의 값을 nvl 함수 처리 실수형 값으로 넣는다
     * @param data map
     * @param key map key 값
     * @param trans nvl함수 변환 값 ex) 0.00
     * @return double
     */
    public static double nvlMapDouble(Map data, String key, double trans) {
        return NumberUtils.toDouble(nvlMap(data, key, Double.toString(trans)));
    }
    
    /**
     * Map안의 값을 nvl 함수 처리 실수형 값으로 넣는다
     * @param data map
     * @param key map key 값
     * @return long
     */
    public static long nvlMapLong(Map data, String key) {
        return nvlMapLong(data, key, NumberUtils.LONG_ZERO);
    }
    
    /**
     * Map안의 값을 nvl 함수 처리 실수형 값으로 넣는다
     * @param data map
     * @param key map key 값
     * @param trans nvl함수 변환 값 ex) 0.00
     * @return long
     */
    public static long nvlMapLong(Map data, String key, long trans) {
        return NumberUtils.toLong(nvlMap(data, key, Long.toString(trans)));
    }
    
    /**
     * 실수형 숫자를 문자열로 변환
     * @param number 숫자
     * @return String
     */
    public static String number_format(float number) {
        return number_format(number, "#,##0");
    }
    
    /**
     * 정수형 숫자를 문자열로 변환
     * @param number 숫자
     * @return String
     */
    public static String number_format(int number) {
        return number_format(number, "#,##0");
    }
    
    /**
     * 정수형 숫자를 문자열로 변환
     * @param number 숫자
     * @return String
     */
    public static String number_format(long number) {
        return number_format(number, "#,##0");
    }
    
    /**
     * 정수형 숫자를 넘겨준 포맷으로 맞춘 문자열로 변환
     * @param number 숫자
     * @param format 변환 포멧
     * @return String
     */
    public static String number_format(int number, String format) {
        String result                    = "0";

        try {
            if (number <= 0) {
                return "0";
            }

            String strNumber            = Integer.toString(number);
            Double dblNumber            = Double.valueOf(strNumber);
            format                      = "".equals(format) ? "#,##0" : format;
            DecimalFormat formatter     = new DecimalFormat(format);
            result                      = formatter.format(dblNumber);
        } catch (Exception e) {
            System.out.println("CommonUtil [number_format] " + e.toString());
        }

        return result;
    }
    
    /**
     * Object형태로 넘어온 숫자를 문자열로 변환
     * @param number 숫자
     * @return String
     */
    public static String number_format(Object number) {
        return number_format(number, "#,##0");
    }
    
    /**
     * Object형태로 넘어온 숫자를 넘겨준 포맷에 맞는 문자열로 변환
     * @param number 숫자
     * @param format 변환 포멧
     * @return String
     */
    public static String number_format(Object number, String format) {
        String result                    = "0";

        try {
            if (number == null || "".equals(number.toString())) {
                return "0";
            }

            String strNumber            = number.toString();
            Double dblNumber            = Double.valueOf(strNumber);
            format                      = "".equals(format) ? "#,##0" : format;
            DecimalFormat formatter     = new DecimalFormat(format);
            result                      = formatter.format(dblNumber);
        } catch (Exception e) {
            System.out.println("CommonUtil [number_format] " + e.toString());
        }

        return result;
    }
    
    /**
     * Map, Collection, Array, String, File 객체형 비워있는지 여부 체크 (단 위에서 지원하지 객체형 은 null 을 기준으로 판단)
     * Map : Map == null || Map.isEmpty()
	 * Collection : Collection == null || Collection.isEmpty()
	 * Array : Array == null || Array.length == 0
	 * String : String == null || String.trim().length() == 0
     * File : File == null || !File.exists()
	 * Other : Other == null
     * @param data 비워있는지 검사할 객체
     * @return 비워있는지 여부
     */
    public static boolean isEmpty(Object data) {
        if (data == null) {
            return true;
        } else if (data instanceof Map) {
            return ((Map)data).isEmpty();
        } else if (data instanceof Collection) {
            return ((Collection)data).isEmpty();
        } else if (data.getClass().isArray()) {
            return Array.getLength(data) == 0;
        } else if (data instanceof String) {
            return ((String)data).trim().length() == 0;
        } else if (data instanceof File) {
            return !((File)data).exists();
        } else {
            return false;
        }
    }
    
    /**
     * 배열이 비거나 배열안의 모든 값이 비었는지를 검증
     * @param data
     * @return boolean
     */
    public static boolean isEmptyAll(Object... data) {
        if (isNotEmpty(data)) {
            Object[] arrayData          = data;
            int length                  = data.length;

            for(int i = 0; i < length; ++i) {
                Object target           = arrayData[i];
                if (isNotEmpty(target)) {
                    return false;
                }
            }
        }

        return true;
    }
    
    /**
     * 맵 의 값들이 비워있는지 여부 체크
     * @param map 값을 확인할 맵
     * @return 값들이 비워있는지 여부
     */
    public static boolean isEmptyMapValue(Map<String, Object> map) {
        boolean isEmpty             = true;
        Iterator iterator           = map.values().iterator();

        while(iterator.hasNext()) {
            Object target           = iterator.next();
            if (isNotEmpty(target)) {
                isEmpty             = false;
            }
        }

        return isEmpty;
    }
    
    /**
	 * Map, Collection, Array, String, File 객체형 비워있는지 여부 체크
	 * (단 위에서 지원하지 객체형 은 null 을 기준으로 판단)
	 *
	 * Map : Map != null && !Map.isEmpty()
	 * Collection : Collection != null && !Collection.isEmpty()
	 * String : String != null && String.trim().length() != 0
	 * Array : Array != null && Array.length != 0
     * File : File != null && File.exists()
	 * Other : Other != null
     * @param data 비워있는지 검사할 객체
     * @return boolean 비워있는지 여부
     */
    public static boolean isNotEmpty(Object data) {
        return !isEmpty(data);
    }
    
    /**
     * 배열이 비어있지 않고 배열안의 값이 empty가 아님을 검증
     * @param data 비워있는지 검사할 객체들 ex) object, object, ...
     * @return boolean 비워있는지 여부
     */
    public static boolean isNotEmptyAll(Object... data) {
        boolean result                  = true;
        if (isNotEmpty(data)) {
            Object[] arrayData          = data;
            int length = data.length;

            for(int i = 0; i < length; ++i) {
                Object target           = arrayData[i];
                if (isEmpty(target)) {
                    result              = false;
                    break;
                }
            }
        } else {
            result                      = false;
        }

        return result;
    }

    /**
    * 문자열을 합친다.
    * @param data 배열값
    * @param delimiter 구분자
    * @return string
    */
    public static String join(String[] data, String delimiter) {
        StringBuffer sb = new StringBuffer();

        for(int i = 0; i < data.length - 1; ++i) {
            sb.append(data[i]).append(delimiter);
        }

        sb.append(data[data.length - 1]);
        return sb.toString();
    }
    
    /**
     * 해당 문자열이, array 안에 있는지 검사한다.
     * @param data 검색할 배열
     * @param search 검색문자
     * @return string
     */
    public static boolean contains(String[] data, String search) {
        if (search != null) {
            for (int i = 0; i <  data.length; ++i) {
                if (search.equals(data[i])) {
                    return true;
                }
            }
        }
        return false;
    }
    
    /**
     * 배열 의 해당값 포함여부 (객체의 equals 메소드를 사용하여 비교)
     * @param data 배열
     * @param search 찾을 문자
     * @return 배열 의 해당값 포함여부
     */
    public static boolean arrayContains(Object[] data, Object search) {
        boolean isContains          = false;
        if (isArray(data)) {
            Object[] arrayData      = data;
            int length              = data.length;

            for(int i = 0; i < length; ++i) {
                Object obj          = arrayData[i];
                isContains          = obj.equals(search);
                if (isContains) {
                    break;
                }
            }
        }

        return isContains;
    }
    
	/**
     * 배열여부 검증
     * @param data 검증대상
     * @return boolean
     */
    public static boolean isArray(Object data) {
        return data != null && data.getClass().isArray();
    }
    
    /**
     * 해당 문자가 빈값인지 확인
     * @param data 데이터 값
     * @return boolean (true:문자열이 없는 경우, false:문자열이 있는 경우)
     */
    public static boolean isBlank(String data) {
        return data == null || data.length() == 0;
    }
    
    /**
     * Cross Site Script
     * html태그 치환
     * lucy-xss-servlet를 사용하고 있음.
     * https://github.com/naver/lucy-xss-servlet-filter
     * @param data 치환 문자열
     * @return String 치환된 문자열
     */
    public static String removeXSS(String data) {
        return XssPreventer.escape(data);
    }
    
    /**
     * Cross Site Script
     * html태그 복구
     * lucy-xss-servlet를 사용하고 있음.
     * https://github.com/naver/lucy-xss-servlet-filter
     * @param data 치환 문자열
     * @return String 치환된 문자열
     */
    public static String recoverXSS(String data) {
        return XssPreventer.unescape(data);
    }
    
    /**
     * 자바스크립트에 "", 또는 ''안에 들어갈 문자 생성 " : \" ' : \' carrige return : \n \ : \\
     * @param data 수정을 원하는 문자열
     * @return 특수문자가 Replace된 문자열
     */
    public static String convertSpChar(String data) {
        if (data == null) {
            return "";
        } else {
            StringBuffer sb = new StringBuffer();
            char[] charArray = data.toCharArray();

            for(int i = 0; i < charArray.length; ++i) {
                if (charArray[i] == '"') {
                    sb.append("\\\"");
                } else if (charArray[i] == '\'') {
                    sb.append("\\'");
                } else if (charArray[i] == '\n') {
                    sb.append("\\n");
                } else if (charArray[i] == '\\') {
                    sb.append("\\\\");
                } else {
                    sb.append(charArray[i]);
                }
            }

            return sb.toString();
        }
    }

    /**
     * HTML BODY, TD등에 텍스트로 보여줄 문자 생성 value값으로 들어갈 경우 value="str" 이런 형태이어야 한다. value='str', value=str
     * 이런건 예외상황이 발생할 수 있다. < : &lt; > : &gt; & : &amp; space : &nbsp; " : &quot;
     * @param data 수정을 원하는 문자열
     * @return 특수문자가 Replace된 문자열
     */
    public static String convertSpMark(String data) {
        if (data == null) {
            return "";
        } else {
            StringBuffer sb = new StringBuffer();
            char[] charArray = data.toCharArray();

            for(int i = 0; i < charArray.length; ++i) {
                if (charArray[i] == '<') {
                    sb.append("&lt;");
                } else if (charArray[i] == '>') {
                    sb.append("&gt;");
                } else if (charArray[i] == '&') {
                    sb.append("&amp;");
                } else if (charArray[i] == ' ') {
                    sb.append("&nbsp;");
                } else if (charArray[i] == '"') {
                    sb.append("&quot;");
                } else {
                    sb.append(charArray[i]);
                }
            }

            return sb.toString();
        }
    }
    
    /**
     * double을 소수점이하 자리수를 원하는 형태로 변환(String)으로 반환
     * @param d 값
     * @param i 소숫점이하 자리수
     * @return
     */
    public static String doubleStr(double d, int i) {
        DecimalFormat decimalformat     = null;
        String szForm                   = "######0";
        String result                   = "";
        if (i > 0) {
            for(int z = 0; z < i; ++z) {
                if (z == 0) {
                    szForm              = szForm + ".0";
                } else {
                    szForm              = szForm + "0";
                }
            }
        }

        decimalformat                   = new DecimalFormat(szForm);
        result                          = decimalformat.format(d);
        return result;
    }
    
    /**
     * double을 원하는 형태의 포맷으로 변환(String)으로 반환 i == 1 : 소숫점이하 한자리 i == 2 : 컴마로 구분된 숫자 i == 3 : 붙어있는숫자
     * @param d
     * @param i
     * @return
     */
    public static String doubleToStr(double d, int i) {
        DecimalFormat decimalformat     = null;
        String result                   = "";
        if (i == 1) {
            decimalformat               = new DecimalFormat("######0.#");
        } else if (i == 2) {
            decimalformat               = new DecimalFormat("#,###,##0");
        } else if (i == 3) {
            decimalformat               = new DecimalFormat("######0");
        } else {
            decimalformat               = new DecimalFormat("#,###,##0.#####");
        }

        result                          = decimalformat.format(d);
        return result;
    }

    /**
     * 문자열을 디코딩한다. <br>
     * 주민등록번호와 같은 정보를 get 방식으로 url 호출시 사용된다.
     * @param data 디코딩할 문자열
     * @return String 디코딩된 문자열
     */
    public static String DSDecode(String data) {
        String result            = "";

        for(int i = 0; i < data.length(); ++i) {
            result               = result + (char)(data.charAt(i) - i % 2 - 1);
        }

        if (result.length() >= 2 && result.substring(result.length() - 6).equals("PASSWD")) {
            result               = result.substring(0, result.length() - 6);
        } else {
            result               = "";
        }

        return result;
    }

    /**
     * 문자열을 인코딩한다. <br>
     * 주민등록번호와 같은 정보를 get 방식으로 url 호출시 사용된다.
     * @param data 인코딩할 문자열
     * @return String 인코딩된 문자열
     */
    public static String DSEncode(String data) {
        data                = data + "PASSWD";
        String result       = "";

        for(int i = 0; i < data.length(); ++i) {
            result          = result + (char)(data.charAt(i) + i % 2 + 1);
        }

        return result;
    }
    
    /**
     * String 내에 포함된 모든 특정 String을 다른 String으로 치환하는 메소드
     * @param data 전체 String
     * @param searchWord 치환대상이 되는 특정 String
     * @param replaceWord 특정 String을 치환할 새로운 String
     * @return 특정 String이 다른 String으로 바뀌어진 String
     */
    public static String getChangeString(String data, String searchWord, String replaceWord) {
        String result         = "";
        if (data != null && data.indexOf(searchWord) != -1) {
            while(true) {
                if (data.indexOf(searchWord) == -1) {
                    result    = result + data;
                    break;
                }

                result        = result + data.substring(0, data.indexOf(searchWord)) + replaceWord;
                data          = data.substring(data.indexOf(searchWord) + searchWord.length());
            }
        } else {
            result            = data;
        }

        return result;
    }
    
    /**
     * CSV 파일을 등록시 &quot; &tilde; &quot; 사이의 콤마(,)를 치환하는 메소드.
     * @param content 전체 String
     * @return 특정 String이 다른 String으로 바뀌어진 String
     */
    public static String getCSVChangeString(String content) {
        String temp                     = "";
        String replaceContent           = "";
        String indexStr                 = "\"";
        
        int iStartPos                   = 0;
        int iEndPos                     = 0;
        
        iStartPos                       = content.indexOf(indexStr);
        
        if (iStartPos != -1) {
            replaceContent              = content.substring(0                       , iStartPos);

            while(iStartPos != -1) {
                iEndPos                 = CoreUtil.indexOfaA(content                , indexStr            , iStartPos + 1);
                temp                    = content.substring(iStartPos + 1           , iEndPos);
                replaceContent          = replaceContent + getChangeString(temp     , ","       , "^COMMA^");
                content                 = content.substring(iEndPos + 1);
                iStartPos               = content.indexOf(indexStr);
                if (iStartPos > 0) {
                    replaceContent      = replaceContent + content.substring(0      , iStartPos);
                }
            }

            replaceContent              = replaceContent + content;
        } else {
            replaceContent              = content;
        }

        return replaceContent.replaceAll("\"", "").trim();
    }
    
    /**
     * 문자열형태의 숫자에 콤마 찍기 ex) 1000 > 1,000
     * @param nunmber 넣은 값
     * @return
     */
    public static String getComma(String nunmber) {
        nunmber                     = StringUtil.nvl(nunmber        , "0");
        DecimalFormat formatter     = new DecimalFormat("#,##0");
        return formatter.format((long)Integer.parseInt(nunmber));
    }
    
    /**
     * 전화번호 - 표시
     * @param phone 전화번호
     * @return
     */
    public static String getPhoneFormat(Object phone) {
        String delimiterPhone                   = phone.toString();
        return getPhoneFormat(delimiterPhone    , "-");
    }
    
    /**
     * 전화번호 7자리, 8자리 구분에 따른 표시
     * @param phone 전화번호
     * @param delimiter 전화번호 사이 구분자
     * @return
     */
    public static String getPhoneFormat(Object phone, String delimiter) {
        String result                    = "";
        String delimiterPhone           = phone.toString();
        int iDayLen                     = delimiterPhone.length();
        if (iDayLen < 4) {
            result                       = delimiterPhone;
        } else if (iDayLen < 8) {
            result                       = delimiterPhone.substring(0, 3) + delimiter + delimiterPhone.substring(3, 7);
        } else if (iDayLen < 9) {
            result                       = delimiterPhone.substring(0, 4) + delimiter + delimiterPhone.substring(4, 8);
        }

        return result;
    }
    
    /**
     * 오른쪽 채우기
     * @param data 변환 문자
     * @param length 채울 길이
     * @return
     */
    public static String getRight(String data, int length) {
        return data.length() < length ? "" : data.substring(data.length() - length);
    }
    
    /**
     * 문자열를 byte배열에 담아 int 값으로 변환하여 반환.
     * @param data
     * @return int
     */
    public static int getByteToInt(String data) {
        int result              = -1;
        int index               = data.length();
        byte[] b                = new byte[index];
        b                       = data.getBytes();
        int nQuot               = b.length / 3;
        int nMod                = b.length % 3;
        int x                   = 0;

        int quotLoop;
        for(quotLoop = 0; quotLoop < nQuot; ++quotLoop) {
            for(int loop = 0; loop < 3; ++x) {
                if (loop == 0) {
                    result      += Integer.valueOf(b[x]) * (nQuot - quotLoop + 1);
                } else if (loop == 1) {
                    result      += Integer.valueOf(b[x]) * 10 * (nQuot - quotLoop + 1);
                } else {
                    result      += Integer.valueOf(b[x]) * 100 * (nQuot - quotLoop + 1);
                }

                ++loop;
            }
        }

        if (nMod > 0) {
            for(quotLoop = 0; quotLoop < nMod; ++quotLoop) {
                if (quotLoop == 0) {
                    result      += Integer.valueOf(b[x]);
                } else {
                    result      += Integer.valueOf(b[x]) * 10;
                }
            }
        }

        return result;
    }
    
    /**
     * Byte길이에 구분자를 넣어 값을 넘겨준다
     * @param data 문자열
     * @param putByteLength 구분자를 넣어줄 Byte 길이
     * @param delimiter 구분자
     * @return
     */
    public static String addDelimiterByte(String data, int putByteLength, String delimiter) {
        int strLength           = 0;
        char[] tempChar         = new char[data.length()];
        StringBuffer stb        = new StringBuffer();

        try {
            for(int i = 0; i < tempChar.length; ++i) {
                tempChar[i]     = data.charAt(i);
                if (tempChar[i] < 128) {
                    ++strLength;
                } else {
                    strLength   += 2;
                }

                if (strLength > putByteLength && delimiter != null) {
                    stb.append(delimiter);
                    delimiter   = null;
                } else if (strLength > putByteLength * 2 && delimiter == null) {
                    stb.append("...");
                    break;
                }

                stb.append(tempChar[i]);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }

        return stb.toString();
    }
    
    /**
     * 문자열을 정의한 Byte로 잘라 끝을 ...으로 표현해준다.
     * @param data 전달문자
     * @param putByteLength 구분자를 넣어줄 Byte 길이
     * @param delimiter 구분자
     * @return
     */
    public static String getStringByteCut(String data, int putByteLength, String delimiter) {
        int length              = 0;
        char[] tempChar         = new char[data.length()];
        StringBuffer stb        = new StringBuffer();

        try {
            for(int i = 0; i < tempChar.length; ++i) {
                tempChar[i]     = data.charAt(i);
                if (tempChar[i] < 128) {
                    ++length;
                } else {
                    length      += 2;
                }

                stb.append(tempChar[i]);
                if (length > putByteLength) {
                    stb.append(delimiter);
                    break;
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }

        return stb.toString();
    }
    
    /**
     * 자릿수에 0 채워주기
     * @param number 입력값
     * @param cipher 자리수
     * @return string
     */
    public static String addZero(int number, int cipher) {
        String num              = String.valueOf(number);
        StringBuffer sb         = new StringBuffer();

        for(int i = 0; i < cipher - num.length(); ++i) {
            sb.append("0");
        }

        return sb.append(num).toString();
    }
    
    /**
     * 이메일 유효성 체크
     * @param email 이메일
     * @return string
     */
    public static boolean isValidEmail(String email) {
        boolean isEmail         = false;
        Pattern pattern         = Pattern.compile("[a-zA-Z0-9\\+\\.\\_\\%\\-\\+]{1,256}\\@[a-zA-Z0-9][a-zA-Z0-9\\-]{0,64}(\\.[a-zA-Z0-9][a-zA-Z0-9\\-]{0,25})+");
        Matcher matcher         = pattern.matcher(email);
        if (!matcher.matches()) {
            isEmail             = true;
        }

        return isEmail;
    }
    
    /**
     * 숫자의 왼쪽 자리를 '0'으로 채운다.
     * @param number 입력값
     * @param size 총길이
     * @return string
     * @throws IOException
     */
    public static String padLeftwithZero(int number, int size) throws IOException {
        StringBuffer sb         = new StringBuffer();
        Integer inTemp          = new Integer(number);
        String stTemp           = inTemp.toString();
        
        if (stTemp.length() < size) {
            for(int i = 0; i < size - stTemp.length(); ++i) {
                sb.append("0");
            }
        }

        sb.append(stTemp);
        return sb.toString();
    }
    
    /**
     * 왼쪽에 0이 붙은 문자를 제거한다.
     * @param data 문자
     * @return  string
     * @throws IOException
     */
    public static String removeLeftZero(String data) throws IOException {
        return data.replaceFirst("^0+(?!$)", "");
    }

    /**
     * random 숫자 조합 string 만들기
     * 10자리 이내의 자리수
     * @return
     */
    public static String randomValue(int num) {
        return RandomStringUtils.randomNumeric(num);
    }

    /**
     * 문자를 구분자를 통하여 분리하여 인자에 해당 값 제공 (전화번호, 이메일 등등 사용)
     * @param str 문자
     * @param regex 구분자
     * @param number 인자
     * @return string
     */
    public static String strSplit(String str, String regex, int number) {
        if (StringUtil.isNotEmpty(str)) {
            String[] arrStr = str.split(regex);

            if (arrStr.length > number) return arrStr[number];
            else return "";
        } else {
            return "";
        }
    }
}
