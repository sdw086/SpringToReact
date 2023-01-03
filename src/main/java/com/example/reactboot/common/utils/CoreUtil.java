package com.example.reactboot.common.utils;

import org.apache.commons.lang3.math.NumberUtils;

import javax.imageio.ImageIO;
import javax.servlet.http.HttpServletRequest;
import java.awt.*;
import java.awt.image.ImageObserver;
import java.io.*;
import java.security.MessageDigest;
import java.util.List;
import java.util.*;

public class CoreUtil {
	private static Random random = new Random();

    public CoreUtil() {
    }
    
    /**
     * 대소문자 구분없이 String 값에서 시작위치를 주고 찾고자하는 값이 몇번째 순서에 있는지 리턴하는 메소드.
     * @param data String 값
     * @param search 찾고자 하는 값
     * @param searchIndex String 의 시작위치
     * @return 찾고자 하는 값의 String 의 위치
     */
    public static int indexOfaA(String data, String search, int searchIndex) {
        search              = search.toLowerCase();
        data                = data.toLowerCase();
        int index           = data.indexOf(search, searchIndex);
        return index;
    }
    
    /**
     * URL 중에서 해당 필드 값을 삭제함
     * @param param URL 파라메터
     * @param deleteWord 삭제할 필드
     * @return String
     */
    public static String removeParam(String param, String deleteWord) {
        String returnParam                    = "";

        try {
            if (param != null && !"".equals(param)) {
                if (deleteWord != null && !"".equals(deleteWord)) {
                    int iStartPos           = indexOfaA(param, deleteWord, 0);
                    if (iStartPos < 0) {
                        return param;
                    } else {
                        for(returnParam = param; iStartPos >= 0; iStartPos = indexOfaA(returnParam, deleteWord, 0)) {
                            int iEndPos     = indexOfaA(returnParam, "&", iStartPos);
                            if (iEndPos <= 0) {
                                iEndPos     = returnParam.length() - 1;
                            }

                            returnParam       = returnParam.substring(0, iStartPos) + returnParam.substring(iEndPos + 1);
                        }

                        return returnParam;
                    }
                } else {
                    return "";
                }
            } else {
                return "";
            }
        } catch (Exception e) {
            System.out.println("CommonUtil[removeParam]=>" + e.toString());
            return returnParam;
        }
    }
    
    /**
     * MD5 암호화
     * @param password 암호화할 문자열
     * @return String 암호화된 문자열
     */
    public static String getEncryptString(String password) {
        MessageDigest md5;

        try {
            md5                 = MessageDigest.getInstance("MD5");
        } catch (Exception e) {
            return "";
        }

        String md5Password      = "";
        byte[] bip              = md5.digest(password.getBytes());

        for(int i = 0; i < bip.length; ++i) {
            String eip          = "" + Integer.toHexString(bip[i] & 255);
            if (eip.length() < 2) {
                eip             = "0" + eip;
            }

            md5Password         = md5Password + eip;
        }

        return md5Password;
    }
    
    /**
     * 파일 확장자 가져오기
     * @param fileName 파일명
     * @return
     */
    public static String getFileExt(String fileName) {
        return fileName != null && !"".equals(fileName) ? fileName.substring(fileName.lastIndexOf(".") + 1, fileName.length()) : "";
    }
    
    /**
     * 파일 이름 가저오기
     * @param fileName 파일명
     * @return String
     */
    public static String getFileName(Object fileName) {
        return fileName != null && !"".equals(fileName) ? getFileName(fileName.toString()) : "";
    }
    
    /**
     * 파일 이름 가져오기
     * @param fileName
     * @return
     */
    public static String getFileName(String fileName) {
        return fileName != null && !"".equals(fileName) ? fileName.substring(fileName.lastIndexOf("/") + 1, fileName.length()) : "";
    }
    
    /**
     * 파일 내용 가져오기
     * @param filePath 파일path
     * @return String 반환
     */
    public static String getFileRead(String filePath) {
        String content          = "";

        try {
            Reader reader       = new InputStreamReader(new FileInputStream(filePath), "UTF-8");

            BufferedReader bufferedReader;
            String fileContent;
            for(bufferedReader = new BufferedReader(reader); (fileContent = bufferedReader.readLine()) != null; content = content + fileContent + "\n") {
            }

            bufferedReader.close();
        } catch (IOException e) {
            e.printStackTrace();
        }

        return content;
    }
    
    /**
     * 파일 사이즈 가져오기
     * @param file 파일정보
     * @return String
     */
    public static String getFileSize(Object file) {
        String fileSize             = "";
        if (file == null) {
            return "";
        } else {
            Long nSize              = Long.parseLong(file.toString());
            nSize                   = nSize / 1000L;
            if (nSize < 1L) {
                nSize               = 1L;
            }

            if (nSize < 1000L) {
                fileSize            = String.format("%dKB"      , nSize);
            } else {
                fileSize            = String.format("%.2fMB"    , (double)nSize / 1000.0D);
            }

            return fileSize;
        }
    }
    
    /**
     * 이미지 리사이즈 함수
     * @param request HttpServletRequest 클래스
     * @param imageFile 이미지 파일명
     * @param fixWidth 넓이
     * @param fixHeight 높이
     * @return
     */
    public static int[] getImageResize(HttpServletRequest request, String imageFile, int fixWidth, int fixHeight) {
        int nRate;
        int[] arrSize               = new int[]{0, 0};

        try {
            if (imageFile == null || "".equals(imageFile)) {
                return arrSize;
            }

            String strPath          = request.getRealPath("/") + imageFile;
            InputStream is          = new BufferedInputStream(new FileInputStream(strPath));
            Image image             = ImageIO.read(is);
            int width               = image.getWidth((ImageObserver)null);
            int height              = image.getHeight((ImageObserver)null);
            if (width >= fixWidth) {
                // 원하는 길이보다 넓이가 넓은 경우
                nRate               = fixWidth * 100 / width;
                width               = width * nRate / 100;
                height              = height * nRate / 100;
            }

            if (height >= fixHeight) {
                // 원하는 길이보다 넓이가 넓은 경우
                nRate               = fixHeight * 100 / height;
                width               = width * nRate / 100;
                height              = height * nRate / 100;
            }

            arrSize[0]              = width;
            arrSize[1]              = height;
        } catch (Exception e) {
            e.printStackTrace();
        }

        return arrSize;
    }
    
    /**
     * 이미지 사이즈 조정하여 string에 width, height 내용 담는 함수
     * @param request HttpServletRequest 클래스
     * @param imageFile 이미지 파일명
     * @param fixWidth 넓이
     * @param fixHeight 높이
     * @return
     */
    public static String getImageResizeStr(HttpServletRequest request, String imageFile, int fixWidth, int fixHeight) {
        String imageWH          = "";

        try {
            int[] nArrSize      = getImageResize(request, imageFile, fixWidth, fixHeight);
            if (nArrSize[0] > 0 && nArrSize[1] > 0) {
                imageWH         = " width = '" + nArrSize[0] + "' height='" + nArrSize[1] + "'";
            }
        } catch (Exception e) {
            e.printStackTrace();
        }

        return imageWH;
    }
    
    /**
     * list 안에 같은 key를 같는 내용 string으로 가져오는 함수
     * Map<String, Object> map1 = new HashMap<>();
     * map1.put("aa", "11");
     * Map<String, Object> map2 = new HashMap<>();
     * map2.put("aa", "22");
     * List<Map<String, Object>> list  = new ArrayList<>();
     * listaa.add(map1);
     * listaa.add(map2);
     *
     * 위의 소스와 같이 같은 데이터를 뽑아낼때 사용
     *
     * @param list list 조회 대상
     * @param key key 값
     * @return String 전달 데이터 ex) 11, 22
     */
    public static String getInStr(List list, String key) {
        String inStr        = "";

        for(int nLoop = 0; nLoop < list.size(); ++nLoop) {
            Map map         = (Map)list.get(nLoop);
            String strMap   = StringUtil.nvlMap(map, key);
            if (!"".equals(strMap)) {
                if (!"".equals(inStr)) {
                    inStr   = inStr + ",";
                }

                inStr       = inStr + StringUtil.nvlMap(map, key);
            }
        }

        return inStr;
    }
    
    /**
     * Map에 숫자를 담은 List 생성
     * @param start 시작
     * @param end 끝
     * @param increase 증가값
     * @return
     */
    public static List<Map<String, Integer>> getNumericalProgressionList(int start, int end, int increase) {
        List<Map<String, Integer>> list     = new ArrayList((Math.abs(end - start) + 1) / Math.abs(increase));
        Map<String, Integer> map;

        for(int i = start; i >= start && end >= i || i >= end && start >= i; i += increase) {
            map                             = new HashMap();
            map.put("code"                  , i);
            map.put("value"                 , i);
            list.add(map);
        }

        return list;
    }
    
    /**
     * Map에 숫자를 담은 List 생성 자릿수 맞춤 1~100 까지라면 001~100
     * @param start 시작
     * @param end 끝
     * @param increase 증가값
     * @return
     */
    public static List<Map<String, String>> getNumericalProgressionListWithLpad(int start, int end, int increase) {
        List<Map<String, Integer>> numericalProgressionList         = getNumericalProgressionList(start, end, increase);
        List<Map<String, String>> list                              = new ArrayList(numericalProgressionList.size());
        
        int maxLength                                               = Integer.toString(Math.abs(start)).length() > Integer.toString(Math.abs(end)).length() ? Integer.toString(Math.abs(start)).length() : Integer.toString(Math.abs(end)).length();
        
        Map<String, String> map;
        Iterator iterator                                           = numericalProgressionList.iterator();

        while(iterator.hasNext()) {
            Map<String, Integer> numericalProgressionMap            = (Map)iterator.next();
            map                                                     = new HashMap();
            map.put("code"                                          , String.format("%0" + maxLength + "d", numericalProgressionMap.get("code")));
            map.put("value"                                         , String.format("%0" + maxLength + "d", numericalProgressionMap.get("value")));
            list.add(map);
        }

        return list;
    }
    
    /**
     * request 객체 정보를 기반으로 context Root 전까지의 URL 정보를 반환한다.
     * @param request HttpServletRequest 객체
     * @return URL 정보 (예. http://www.abc.com:7001)
     */
    public static String getServletUrl(HttpServletRequest request) {
        String serverName       = request.getServerName();
        int serverPort          = request.getServerPort();
        String scheme           = request.getScheme();
        String serverUrl        = scheme + "://" + serverName + (serverPort == 80 ? "" : ":" + serverPort);
        return serverUrl;
    }
    
    /**
     * 1 ~ 4 분기 구하기
     * @param day 날짜
     * @return String
     */
    public static String getSettle(String day) {
        String quarter      = "";
        if ("".equals(day)) {
            day             = DateUtil.getCurrentDate();
        }

        int iMonth          = Integer.parseInt(day.substring(4, 6));
        if (iMonth <= 3) {
            quarter         = "1";
        } else if (iMonth <= 6) {
            quarter         = "2";
        } else if (iMonth <= 9) {
            quarter         = "3";
        } else if (iMonth <= 12) {
            quarter         = "4";
        }

        return quarter;
    }
    
    /**
     * 주소 * 가리기. ex) 서울시 강남구 도곡2동 999-99 -> 서울시 강남구 ***동 ***-**
     * @param addr 주소
     * @return String
     */
    public static String getStrAddr(String addr) {
        if (addr == null) {
            return "";
        } else {
            // 서울시 강남구 도곡2동 999-99 -> 서울시 강남구 ***동 ***-**
            String[] arrayAddr          = addr.split(" ");
            StringBuffer sb             = new StringBuffer();
            String hAddr;
            
            if (arrayAddr != null && arrayAddr.length > 0) {
                int idx                 = 0;
                int hideLen             = 0;
                String addrGbn          = "";

                for(int i = 0; i < arrayAddr.length; ++i) {
                    hAddr          = arrayAddr[i] + " ";
                    if (hAddr.indexOf("읍 ") > -1 || hAddr.indexOf("면 ") > -1 || hAddr.indexOf("동 ") > -1) {
                        if (hAddr.indexOf("읍 ") > -1) {
                            idx         = hAddr.indexOf("읍 ");
                            addrGbn     = "읍 ";
                        } else if (hAddr.indexOf("면 ") > -1) {
                            idx         = hAddr.indexOf("면 ");
                            addrGbn     = "면 ";
                        } else if (hAddr.indexOf("동 ") > -1) {
                            idx         = hAddr.indexOf("동 ");
                            addrGbn     = "동 ";
                        }

                        hideLen         = hAddr.length() - idx;

                        String ast;
                        
                        for(ast = "*"; ast.length() < hideLen; ast = ast.concat("*")) {
                        }

                        hAddr      = ast + addrGbn;
                    }

                    sb.append(hAddr);
                }
            }

            hAddr                  = sb.toString();
            StringBuffer sbf            = new StringBuffer();

            for(int i = 0; i < hAddr.length(); ++i) {
                char c                  = hAddr.charAt(i);
                if (String.valueOf(c).matches("[\\d]+")) {
                    sbf.append("*");
                } else {
                    sbf.append(c);
                }
            }

            return sbf.toString();
        }
    }
    
    /**
     * email 암호화 @ 앞 3글자 * 변환
     * @param email 이메일 입력
     * @return String
     */
    public static String getStrEmail(String email) {
        String hEmail             = "";

        try {
            if (email == null) {
                return "";
            } else {
                String[] arrInputStr    = email.split("@");
                if (arrInputStr[0].length() > 3) {
                    hEmail        = email.substring(0                                  , arrInputStr[0].length() - 3);
                    hEmail        = hEmail + "***@";
                    hEmail        = hEmail + StringUtil.nvlArray(arrInputStr      , 1);
                } else {
                    hEmail        = "***@" + StringUtil.nvlArray(arrInputStr           , 1);
                }

                return hEmail;
            }
        } catch (Exception e) {
            e.printStackTrace();
            return email;
        }
    }
    
    /**
     * String 값 * 변환
     * @param value 입력값
     * @param length 원본 문자 출력 갯수
     * @return String
     */
    public static String getStrFrontStr(String value, int length) {
        String hValue              = "";

        try {
            if (value == null) {
                return "";
            } else {
                if (value.length() > length) {
                    hValue         = value.substring(0, length);

                    for(byte nLoop = 0; nLoop < value.length() - length; ++length) {
                        hValue     = hValue + "*";
                    }
                } else {
                    hValue         = value;
                }

                return hValue;
            }
        } catch (Exception e) {
            e.printStackTrace();
            return value;
        }
    }
    
    /**
     * String 값 * 변환 (이름)
     * @param name 입력값
     * @return String
     */
    public static String getStrName(String name) {
        String hName           = "";

        try {
            if (name == null) {
                return "";
            } else {
                if (name.length() > 1) {
                    hName      = name.substring(0, 1);
                    hName      = hName + "*";
                    if (name.length() > 2) {
                        hName  = hName + name.substring(name.length() - 1, name.length());
                    }
                } else {
                    hName      = name;
                }

                return hName;
            }
        } catch (Exception e) {
            e.printStackTrace();
            return name;
        }
    }
    
    /**
     * String 값 * 변환 (전화번호) ex) "011-111-1111" > "011-****-1111"
     * @param phone 변환할 값
     * @return String
     */
    public static String getStrPhone(String phone) {
        String hPhone                 = "";

        try {
            if (phone == null) {
                return "";
            } else {
                if (phone.length() >= 8) {
                    if (phone.indexOf("-") > -1 && phone.lastIndexOf("-") > 4) {
                        hPhone        = phone.substring(0                                         , phone.indexOf("-") + 1);

                        for(int nLoop = phone.indexOf("-") + 1; nLoop < phone.lastIndexOf("-"); ++nLoop) {
                            hPhone    = hPhone + "*";
                        }

                        hPhone        = hPhone + phone.substring(phone.lastIndexOf("-")       , phone.length());
                    } else if (phone.indexOf("-") == -1) {
                        hPhone        = phone.substring(0                                         , 3);
                        hPhone        = hPhone + "****";
                        hPhone        = hPhone + phone.substring(7                                , phone.length());
                    }
                } else {
                    hPhone = phone;
                }

                return hPhone;
            }
        } catch (Exception e) {
            e.printStackTrace();
            return phone;
        }
    }
    
    /**
     * 배열에 값이 들어있을경우 해당 인덱스 반환
     * @param array 배열
     * @param key 비교 값
     * @return int
     */
    public static int indexOfContainsValue(Object[] array, Object key) {
        int i = 0;

        for(int size = StringUtil.isArray(array) ? array.length : 0; i < size; ++i) {
            if (StringUtil.nvl(array[i]).equals(StringUtil.nvl(key))) {
                return i;
            }
        }

        return -1;
    }
    
    /**
     * 임시비밀번호 생성
     * @param length
     * @return
     */
    public static String shufflePasswd(int length) {
        char[] charSet      = new char[]{'0', '1', '2', '3', '4', '5', '6', '7', '8', '9', 'a', 'b', 'c', 'd', 'e', 'f', 'g', 'h', 'i', 'j', 'k', 'l', 'm', 'n', 'o', 'p', 'q', 'r', 's', 't', 'u', 'v', 'w', 'x', 'y', 'z'};
        int idx;
        StringBuffer sb     = new StringBuffer();

        for(int i = 0; i < length; ++i) {
            idx = (int)((double)charSet.length * Math.random());
            sb.append(charSet[idx]);
        }

        return sb.toString();
    }
    
    /**
     * 숫자인지 아닌지 체크 함수
     * @param data 확인 data
     * @return boolean
     */
    public static boolean isNumerical(Object data) {
        return NumberUtils.isNumber(data == null ? "" : data.toString());
    }

    /**
     * AES128 암호화
     */


    /**
     * AES128 복호화
     */
}
