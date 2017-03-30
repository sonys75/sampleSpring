package resources.com.util;

import java.io.UnsupportedEncodingException;
import java.sql.Timestamp;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Locale;
import java.util.Map;
import java.util.Set;
import java.util.StringTokenizer;
import java.util.TimeZone;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.springframework.util.DigestUtils;

/**
 * 각종 유틸리티
 */
public class StringUtil {

    static TimeZone tz = TimeZone.getTimeZone("KST");
    
	public static String convertSalting(String salt, String passwd) {
		// 2014.04.10 보안점검에 따른 조치
    	if(salt==null) return "";
    	
		String retStr = "{" + salt.hashCode() + "}" + passwd;
		return retStr;
	}
	
    public static String strFilter(String str) {
    	// 2014.04.10 보안점검에 따른 조치
    	if(str==null) return "";
    	
    	if( str != null && str.length() > 0 ){
    		final String[] arrFilterWord = {"", "\\.", "\\?", "\\/", "\\~", "\\!", "\\@", "\\#", "\\$", "\\%",
    				                 		"\\^", "\\&", "\\*", "\\(", "\\)", "\\_", "\\+", "\\=", "\\|", "\\\\",
    				                 		"\\}", "\\]", "\\{", "\\[", "\\\"", "\\'", "\\:", "\\;", "\\<", "\\,",
    				                 		"\\>", "\\.", "\\?", "\\/" }; 
    		for(int i=0;i<arrFilterWord.length;i++){ 
    			str = str.replaceAll(arrFilterWord[i],""); 
    		}
    	}
    	return str;
    }
    
    // 날자 형식 : 날자 -> formatter
    public static String dateToString(Date date, String fmt) {
        // "yyyy-MM-dd HH:mm:ss"
        // fmt : yyyymmdd, yyyy/mm/dd, yyyy-mm-dd, ....
        try {
            java.text.SimpleDateFormat sDate = new java.text.SimpleDateFormat(fmt, Locale.KOREAN);
            return sDate.format(date);
        } catch (Exception e) {
            return null;
        }
    }

    // 날자 형식 : 문자형 날자 -> formatter
    public static Date stringToDate(String date, String fmt) {
        // "yyyy-MM-dd HH:mm:ss"
        try {
            java.text.SimpleDateFormat sdfDate = new java.text.SimpleDateFormat(fmt, Locale.KOREAN);
            return sdfDate.parse(date);
        } catch (Exception e) {
            return null;
        }
    }

    // Email validate
    public static boolean isEmail(String email) {
        boolean err = false;
        String regex = "^[_a-z0-9-]+(.[_a-z0-9-]+)*@(?:\\w+\\.)+\\w+$";
        Pattern p = Pattern.compile(regex);
        Matcher m = p.matcher(email);
        if (m.matches()) {
            err = true;
        }
        //System.out.println("Email Validate : " + err + " / matches : " + m.matches() + " / email = " + email);
        return err;
    }

    public static boolean isEmpty(String val) {
        return (val == null || val.trim().length() == 0);
    }

    /**
     * 긴제목시 해당 cutByte 만큰 자르고 뒤에 ... 을 붙여준다.
     * @param strSource
     * @param cutByte
     * @return String
     */
    public static String cutBytes(String strSource, final int cutByte) {
        //cutByte = 40;
        if (strSource == null) {
            return "";
        }
        String strPostfix = "...";
        int postfixSize = 3;
        strSource = strSource.trim();
        char[] charArray = strSource.toCharArray();
        int strIndex = 0;
        int byteLength = 0;
        for (; strIndex < strSource.length(); strIndex++) {
            int byteSize = 0;
            if (charArray[strIndex] < 256) {
                byteSize = 1;
            } else {
                byteSize = 3;
            }
            if ((byteLength + byteSize) > cutByte - postfixSize) {
                break;
            }
            byteLength = byteLength += byteSize;
        }

        if (strIndex == strSource.length()) {
            strPostfix = "";
        } else {
            if ((byteLength + postfixSize) < cutByte) {
                strPostfix = " " + strPostfix;
            }
        }
        return strSource.substring(0, strIndex) + strPostfix;
    }

    /**
     * 문자열 바이트 가져오기
     * 한글 UTF-8의 경우 데이터베이스에 입력될 때는 한글자당 3바이트를 차지한다.  
     * 
     * @param str
     * @return
     */
    public static int getByteLength(String str){
    	// 2014.04.10 보안점검에 따른 조치
    	if(str==null) return 0;
    	
    	int strLength = 0;
    	char tempChar[] = new char[str.length()];

    	for (int i = 0; i < tempChar.length; i++) {
    		tempChar[i] = str.charAt(i) ;
    		if (tempChar[i] < 128) {
    			strLength++;
    		}else{
    			strLength += 3;
    		}
    	}
    	return strLength;
    } 
    
    /**
     * str에서 sFind을 sReplace로 바꾼다.
     **/
    public static String replace(String str, String sFind, String sReplace) {
        if (str == null) {
            return str;
        }

        StringTokenizer st = new StringTokenizer(str, sFind, false);
        String res = "";
        if (str.indexOf(sFind) == 0) {
            res += sReplace;
        }
        int count = st.countTokens();
        int i = 0;

        while (st.hasMoreTokens()) {
            i++;
            if (i != count) {
                res += st.nextToken() + sReplace;
            } else {
                res += st.nextToken();
            }
        }
        return res;
    }

    /**
     * \'을 \\'로 변환.
     * "\n"을 " "로 변환.
     **/
    public static String toScriptString(String str) {
        String strRet = str;
        if (str == null) {
            return "";
        }

        strRet = strRet.replace((char) 13, (char) 0);
        strRet = strRet.replace((char) 10, (char) 0);
        strRet = replace(strRet, "\'", "\\'");
        return strRet;
    }

    /**
     * 내용에 앞에 ">"를 추가,  뒤에 <br>를 추가
     **/ 
    public static String stringToContent(String str) {
        if (str == null) {
            return str;
        }

        StringTokenizer st = new StringTokenizer(str, "\n", false);
        String res = "";
        while (st.hasMoreTokens()) {
            res += ">" + st.nextToken() + "<BR>\n";
        }
        return res;
    }

    /**
     * 내용에 앞에 ">"를 추가
     **/
    public static String stringToContent2(String str) {
        if (str == null) {
            return str;
        }

        StringTokenizer st = new StringTokenizer(str, "\n", false);
        String res = "";
        while (st.hasMoreTokens()) {
            res += ">" + st.nextToken() + "\n";
        }
        return res;
    }

    public static String content2answer(String content, String writer, java.util.Date writeDate) {
        String section = "============================================\n";
        section += dateToString(writeDate, "yyyy/mm/dd HH:mm") + "   " + writer + "님이 쓰신글입니다.\n\n" + content;
        StringBuffer sb = new StringBuffer();
        StringTokenizer st = new StringTokenizer(section, "\n");
        while (st.hasMoreTokens()) {
            sb.append(">");
            sb.append(st.nextToken() + "\n");
        }
        return "\n\n\n" + sb.toString();
    }

    /**
     * "\n"을 <br>로 변환
     **/
    public static String stringToHTML(String str) {
        if (str == null) {
            return "";
        }

        StringTokenizer st = new StringTokenizer(str, "\n", false);
        String res = "";
        while (st.hasMoreTokens()) {
            res += st.nextToken() + "<BR>\n";
        }
        return res;
    }

    /**
     * 문자를 개수 만큼만 리턴
     **/
    public static String stringToCount(String str, int count) {
        String ret = "";
        if (str == null) {
            return ret;
        }

        int firstNewLineIndex = str.indexOf("\n");
        if (count > firstNewLineIndex && firstNewLineIndex != -1) {
            count = firstNewLineIndex;
        }

        if (str.length() > count) {
            ret = stringToHTML(str.substring(0, count) + " ...");
        } else {
            ret = stringToHTML(str);
        }
        return ret;
    }

    /**
     * "\n"을 제거
     **/
    public static String removeNewLine(String str) throws Exception {
        if (str == null) {
            return str;
        }
        StringTokenizer st = new StringTokenizer(str, "\n", false);
        String res = "";
        while (st.hasMoreTokens()) {
            res += (st.nextToken());
        }
        return res;
    }

    /**
     * "$"을 "\n"으로 변경.
     **/
    public static String stringToNewLine(String str) {
        if (str == null) {
            return str;
        }

        StringTokenizer st = new StringTokenizer(str, "$", false);
        StringBuffer ret = new StringBuffer();
        while (st.hasMoreTokens()) {
            ret.append(st.nextToken()).append("\n");
        }
        return ret.toString();
    }

    /**
     * "$"을 "<BR>"으로 변경.
     **/
    public static String stringToBR(String str) {
        if (str == null) {
            return str;
        }

        StringTokenizer st = new StringTokenizer(str, "$", false);
        StringBuffer ret = new StringBuffer();
        while (st.hasMoreTokens()) {
            ret.append(st.nextToken()).append("<BR>\n");
        }
        return ret.toString();
    }

    public static String eliminateSlash(String str) {
        if (str == null) {
            return str;
        }

        String ret = str.replace('/', '\n');
        return ret.replace('?', ' ');
    }

    /*
     * hipen 2개만 지운다
     */
    public static String eliminateHipen(String str) {
        String tmp;
        if (str == null) {
            return str;
        }
        int index = str.indexOf('-');
        tmp = str.substring(0, index) + str.substring(index + 1, str.length());
        int index1 = tmp.indexOf('-');
        return tmp.substring(0, index1) + tmp.substring(index1 + 1, tmp.length());

    }

    public static boolean stringToBoolean(String str) {
        if (str == null || str.length() == 0) {
            return false;
        } else {
            if (str.equals("1")) {
                return true;
            }
            if (str.toLowerCase().equals("true")) {
                return true;
            }
            if (str.equals("on")) {
                return true;
            }
            if (str.equals("Y")) {
                return true;
            }
        }
        return false;
    }

    /**
     * "@ ~ @"에 속해있는 문자만 리턴
     **/
    public static String stringToAnnotation(String str) {
        if (str == null || str.length() == 0) {
            return "";
        }

        int startIndex = str.indexOf("@");
        int endIndex = str.indexOf("@", str.indexOf("@") + 1);

        if (startIndex == -1 || endIndex == -1 || startIndex == endIndex) {
            return "";
        }

        return str.substring(str.indexOf("@") + 1, str.indexOf("@", str.indexOf("@") + 1));
    }

    /**
     * "[*] ~ [*]"에 속해있는 문자만 리턴
     * public static String stringToAnnotation(String str) {
     * String ret 		= "";
     * String tmp		= "";
     * int beginIndex 	= 0;
     * int endIndex 	= 0;
     *
     * if(str == null || str.length() == 0 || str.indexOf('[') == -1)
     * return ret;
     * while(str.length() != 0 && str.indexOf('[') != -1) {
     * beginIndex = str.indexOf('[');
     * if(str.substring(beginIndex, beginIndex+3).equals("[*]")) {
     * tmp = str.substring(beginIndex+4, str.length());
     * if(tmp.length() != 0 && tmp.indexOf('[') != -1) {
     * endIndex = tmp.indexOf('[');
     * if(!tmp.substring(endIndex, endIndex+3).equals("[*]"))
     * tmp = tmp.substring(endIndex+1, tmp.length());
     * }
     * else
     * return ret;
     * break;
     * }
     * else
     * str = str.substring(beginIndex+1, str.length());
     * }
     *
     * ret = str.substring(beginIndex+3, beginIndex + 4 + endIndex);
     * return ret;
     * }
     **/
    /**
     * String[] --> ArrayList
     **/
    public static ArrayList<String> arrayToArrayList(String[] array) {
        ArrayList<String> ret = new ArrayList<String>();

        if (array == null) {
            return ret;
        }

        for (int i = 0; i < array.length; i++) {
            ret.add(array[i]);
        }

        return ret;
    }

    /**
     * java.util.Calendar형을 java.util.Date형으로 변환
     **/
    public static java.util.Date calendarToDate(java.util.Calendar date) {
        if (date == null) {
            return null;
        } else {
            return date.getTime();
        }
    }

    public static String formatDate(String format, Date date) {
        SimpleDateFormat formatter = new SimpleDateFormat(format, Locale.KOREAN);

        return formatter.format(date);
    }

    /**
     * 주어진 String의 크기가 주어진 크기보다 작으면 나머지는 " "로 채운다.
     * 그리고 만약 주어진 크기보다 크면 앞에서부터 자른다.
     **/
    public static String cardString(String s, int size) {
    	// 2014.04.10 보안점검에 따른 조치
    	if(s==null) return "";
    	
        final int length = s.length();

        if (length > size) {
            s = s.substring((length - size), length);
        }

        for (int i = 0; i < size - length; i++) {
            s = s + " ";
        }
        return s;
    }

    /**
     * comma를 space 로 변환
     **/
    public static String slashToSpace(String str) {
        if (str == null) {
            return str;
        }

        StringTokenizer st = new StringTokenizer(str, "/", false);
        String res = "";
        while (st.hasMoreTokens()) {
            res += st.nextToken() + " ";
        }
        return res;
    }

    /**
     * space를 slash 로 변환
     **/
    public static String spaceToSlash(String str) {
        if (str == null) {
            return str;
        }

        StringTokenizer st = new StringTokenizer(str, " ", false);
        String res = "";
        while (st.hasMoreTokens()) {
            res += st.nextToken() + "/";
        }
        return res;
    }

    /**
     * 숫자를 원표시로 변환(5000: -> 5,000 10000: -> 10,000)
     **/
    public static String priceToStr(long price) {

        if (price < 10 && price >= -999) {
            return String.valueOf(price);
        }
        String priceStr = String.valueOf(price);
        if (priceStr.startsWith("-")) {
            priceStr = priceStr.replaceAll("-", "");
        }
        int r = priceStr.length() % 3;
        StringBuffer strBuf = new StringBuffer();
        strBuf.append(priceStr.charAt(0));
        for (int i = 1; i < priceStr.length(); i++) {
            if (i % 3 == r) {
                strBuf.append(",");
            }
            strBuf.append(priceStr.charAt(i));
        }
        if (price < 0) {
            return "-" + strBuf.toString();
        } else {
            return strBuf.toString();
        }
    }

    /**
     * 숫자를 원표시로 변환(5000: -> 5,000 10000: -> 10,000)
     **/
    public static String priceToStr(String priceStr) {
        long price = 0;
        try {
            price = Long.parseLong(priceStr);
        } catch (Exception e) {
            return "";
        }

        return priceToStr(price);
    }

    /**
     * 숫자를 원표시로 변환(5000: -> 5,000만원 10000: -> 1억원)
     * 그리고, spaceCount만큼 금액과 단위사이의 공간을 남긴다.
     **/
    public static String priceToStrWithUnit(long price, int spaceCount) {
        String ret = null;
        String space = "";

        for (int i = 0; i < spaceCount; i++) {
            space += "&nbsp;";
        }

        if (price >= 10000) {
            if ((price % 10000) == 0) {
                ret = (price / 10000) + space + "억원";
            } else {
                ret = ((double) price / 10000) + space + "억원";
            }
        } else {
            ret = priceToStr(price) + space + "만원";
        }
        return ret;
    }

    public static boolean checkFormatAddress(String address) {
    	// 2014.04.10 보안점검에 따른 조치
    	if(address==null) return false;
    	
        Set set = new HashSet();
        set.add("~");
        set.add("!");
        set.add("#");
        set.add("$");
        set.add("%");
        set.add("^");
        set.add("&");
        set.add("*");
        set.add("(");
        set.add(")");
        set.add("_");
        set.add("+");
        set.add("`");
        set.add("-");
        set.add("=");
        set.add("[");
        set.add("]");
        set.add("{");
        set.add("}");
        set.add(";");
        set.add("'");
        set.add(":");
        set.add("\"");
        set.add(",");
        set.add(".");
        set.add("/");
        set.add("<");
        set.add(">");
        set.add("?");

        int cnt = 0;
        for (int i = 0; i < address.length(); i++) {
            String s = address.substring(i, i + 1);
            if (set.contains(s)) {
                cnt++;
                System.out.println("???");
            }
        }
        return (cnt == 0 ? false : true);
    }

    public static String formatAddress(final String address) {
    	// 2014.04.10 보안점검에 따른 조치
    	if(address==null) return "";
    	
        Set set = new HashSet();
        set.add("~");
        set.add("!");
        set.add("#");
        set.add("$");
        set.add("%");
        set.add("^");
        set.add("&");
        set.add("*");
        set.add("(");
        set.add(")");
        set.add("_");
        set.add("+");
        set.add("`");
        set.add("-");
        set.add("=");
        set.add("[");
        set.add("]");
        set.add("{");
        set.add("}");
        set.add(";");
        set.add("'");
        set.add(":");
        set.add("\"");
        set.add(",");
        set.add(".");
        set.add("/");
        set.add("<");
        set.add(">");
        set.add("?");

        StringBuffer strBuf = new StringBuffer();
        for (int i = 0; i < address.length(); i++) {
            String s = address.substring(i, i + 1);
            if (set.contains(s)) {
                strBuf.append("*");
            } else {
                strBuf.append(s);
            }

        }
        return strBuf.toString();
    }

    /**
     * YYMMDD 형으로 변환
     */
    public static String getYYMMDD() {
        SimpleDateFormat sdf = new SimpleDateFormat("yyMMdd");
        return sdf.format(new Date());
    }

    /**
     * NULL을 ""로 변환(String)
     */
    public static String nullToSpace(String str) {
        String res = "";
        if (str == null) {
            res = "";
        } else {
            res = str;
        }
        return res;
    }

    /**
     * NULL을 0로 변환(int)
     */
    public static int nullToInt(String str) {
        int res = 0;
        if (str == null) {
            res = 0;
        } else {
            if (str.trim().equals("")) {
                res = 0;
            } else {
                res = Integer.parseInt(str.trim());
            }
        }
        return res;
    }

    /**
     * NULL을 0로 변환(long)
     */
    public static long nullToLong(String str) {
        long res = 0;
        if (str == null) {
            res = 0;
        } else {
            if (str.trim().equals("")) {
                res = 0;
            } else {
                res = Long.parseLong(str.trim());
            }
        }
        return res;
    }

    /**
     * NULL을 0로 변환(float)
     */
    public static float nullToFloat(String str) {
        float res = 0;
        if (str == null) {
            res = 0;
        } else {
            if (str.trim().equals("")) {
                res = 0;
            } else {
                res = Float.parseFloat(str.trim());
            }
        }
        return res;
    }

    /**
     * YYYYMMDDHH24MISS를 YYYY-MM-DD HH24:MI:SS로 변환
     */
    public static String shortDateTimeToLongDateTime(String date) {
    	// 2014.04.10 보안점검에 따른 조치
    	if(date==null) return "";
    	
        String res = "";
        try {
            res = date.substring(0, 4) + "/" + date.substring(4, 6) + "/" + date.substring(6, 8) + " " + date.substring(8, 10) + ":" + date.substring(10, 12) + ":" + date.substring(12, 14);
        } catch (Exception e) {
            res = "";
        }
        return res;
    }

    public static String cropByte(String str, int i, String trail) {
        if (str == null) {
            return "";
        }
        String tmp = str;
        int slen = 0, blen = 0;
        char c;
        try {
            if (tmp.getBytes("EUC-KR").length > i) {
                while (blen + 1 < i) {
                    c = tmp.charAt(slen);
                    blen++;
                    slen++;
                    if (c > 127) {
                        blen++; //2-byte character..
                    }
                }
                tmp = tmp.substring(0, slen) + trail;
            }
        } catch (UnsupportedEncodingException e) {
            tmp = "";
        }
        return tmp;
    }

    //******************************************************************************************************
    // Explanation	: unix시간을 알아낸다.
    // Input Value  : String str
    // Return Value	: time
    //******************************************************************************************************
    public static long getUnixTimestamp() {

        Date date = new Date();
        long time = date.getTime() / 1000;

        return time;

    }

    public static long getUnixTimestamp(int year, int month, int day) {

        year -= 1900;
        month -= 1;

        Calendar cal = Calendar.getInstance();
        cal.set(year, month, day);
        Date date = cal.getTime();
        long time = date.getTime() / 1000;
        return time;
    }

    //******************************************************************************************************
    // Explanation	: unix시간을 데이타 형식으로 변화해준다.
    // Input Value  : String str
    // Return Value	: time
    //******************************************************************************************************
    public static String getFromUnixtime(long time) {

        String date = getFromUnixtime(time, "-");

        return date;

    }

    public static String getFromUnixtime(long time, String shape) {

        SimpleDateFormat formatYY = new SimpleDateFormat("yyyy");
        SimpleDateFormat formatMM = new SimpleDateFormat("MM");
        SimpleDateFormat formatDD = new SimpleDateFormat("dd");
        String yy;
        String mm;
        String dd;
        String date;

        yy = formatYY.format(new Date(time));
        mm = formatMM.format(new Date(time));
        dd = formatDD.format(new Date(time));
        date = yy + shape + mm + shape + dd;

        return date;

    }

    //******************************************************************************************************
    // Explanation	: Date 타입의 시간을 Timestamp(1970-1-1 부터의 초시간)으로 변화할때
    //				: year - year-1900, month - 0 to 11, date - 1 to 31
    // Input Value  : String str
    // Return Value	: StringBuffer buffer.toString()
    //******************************************************************************************************
    public static Timestamp getTimestamp(String str) {
    	// 2014.04.10 보안점검에 따른 조치
        if (str != null && str.length() >= 10) {
        	int year = 0, month = 0, day = 0;
            Date strDate = null;
            Timestamp strTimestamp = null;
            Calendar cal = Calendar.getInstance();
            
            year = Integer.parseInt(str.substring(0, 4));
            month = Integer.parseInt(str.substring(5, 7));
            day = Integer.parseInt(str.substring(8, 10));
            cal.set(year - 1900, month - 1, day);
            strDate = cal.getTime();  // new Date(year-1900, month-1, day);
            strTimestamp = new java.sql.Timestamp(strDate.getTime());
            return strTimestamp;
        } else {
            return null;
            //strTimestamp = "";
        }
    }
    
	/**
	 * 숫자형태의 문자열 값을 입력받아 이 값이 널문자열("")이 아닌 경우 입력받은 문자열 값이 0~9사이 값이면 앞에 0을 붙여준다
	 * 기타의 경우(입력받은 문자열 값이 숫자형태가 아니거나 널문자열이 넘어온 경우)엔 널문자열을 리턴한다
	 * @param strNum	숫자형태의 문자열값
	 * @return
	 */
	public static String makeAppendZero(String strNum){
		String strResult = "";
		if( strNum != null && !"".equals(strNum) ){
			if(isNumeric(fTrim(strNum)) == true){
				if((Integer.parseInt(strNum) >=0) && (Integer.parseInt(strNum) <=9)){
					if(strNum.length() == 1){					// 01, 02 .. 이런식으로 들어온 경우 앞에 0을 붙이면 안되기땜에 입력받은 문자열의 길이가 1인지를 체크하는 로직을 넣는다
						strResult = "0" + strNum;
					}else{
						strResult = strNum;
					}
				}else{
					strResult = strNum;
				}
			}else{
				
			}
		}else{
			strResult = "00";
		}
		return strResult;
	}
	
	/**
	 * 문자열을 입력받아 문자열의 우측 공백을 없앤 문자열을 리턴한다
	 * @param source	우측 공백을 없애고자 하는 문자열
	 * @return			우측 공백이 없어진 문자열
	 */
	public static String rTrim(String source){
		if(source == null) return null;
		
		for(int i=source.length()-1; i >=0; i--){
			if(source.charAt(i) == ' '){
				continue;
			}else{
				return source.substring(0, i+1);
			}
		}
		return null;
	}
	
	/**
	 * 문자열을 입력받아 문자열의 좌측 공백을 없앤 문자열을 리턴한다
	 * @param source	좌측 공백을 없애고자 하는 문자열
	 * @return 			좌측 공백이 없어진 문자열
	 */
	public static String lTrim(String source){
		if(source == null) return null;
		
		for(int i=0; i < source.length(); i++){
			if(source.charAt(i) == ' '){
				continue;
			}else{
				return source.substring(i, source.length());
			}
		}
		return null;
	}
	
	/**
	 * fullTrim
	 * 문자열을 입력받아 문자열의 좌우측 공백을 없앤 문자열을 리턴한다
	 * @param source	좌우측 공백을 없애고자 하는 문자열
	 * @return			좌우측 공백이 없어진 문자
	 */
	public static String fTrim(String source){
		return lTrim(rTrim(source));
	}	
	
	/**
	 * 파라미터로 입력받은 String 문자열 값을 분석하여 숫자형 데이터로만 이루어져 있는지를 체크한다
	 * @param	lst_str		분석하고자 하는 문자열
	 * @return	숫자형 데이터로만 이루어져 있다면 true, 그렇지 않다면 false를 리턴한다
	 */
	public static boolean isNumeric(final String lst_str)
	{
		boolean bResult = true; // 먼저 숫자형 데이터로만 이루어져 있다고 가정한다.
		final String lst_tmpstr = getNull(lst_str);
		if(lst_tmpstr.equals("")) // 입력값으로 빈 문자열만 들어왔으면
		{
			bResult = false;
		}
		else
		{
			final int li_strlen = lst_tmpstr.length();
			for(int i =0; i < li_strlen; i++)
			{
				final char a = lst_tmpstr.charAt(i);
				if (a < '0' || a > '9') { // 읽어들인 글자가 0보다 작거나 9보다 크면 숫자 데이터가 아니기 때문에 lb_result를 false로 설정한다.
					bResult = false;
					break;
				}
			}
		}
		return bResult;
	}	
	
	/**
	 * 입력값인 문자열이 null일 경우 ""을 리턴하고 null이 아니라면 입력받은 문자열의 앞뒤 공백이 없어진 문자열이 리턴된다
	 *
	 * @param stText		입력받은 문자열
	 * @return String		입력받은 문자열 값에 따라 null 또는 입력받은 문자열의 앞뒤공백이 제거된 문자열이 리턴된다 
	 */
	public static String getNull(String stText){
		try {
			if(stText == null) return "";
			if("null".equals(stText) ) return "";
			return stText.trim();
		}catch(Exception e) {
			//e.printStackTrace();
			return "";
		}
	}

	/**
	 * 입력값인 문자열이 null일 경우 ""을 리턴하고 null이 아니라면 입력받은 문자열의 앞뒤 공백이 없어진 문자열이 리턴된다
	 *
	 * @param objText		입력받은 문자열
	 * @return String		입력받은 문자열 값에 따라 null 또는 입력받은 문자열의 앞뒤공백이 제거된 문자열이 리턴된다 
	 */
	public static String getNull(Object objText){
		try {
			if(objText == null) return "";
			return objText.toString().trim();
		}catch(Exception e) {
			//e.printStackTrace();
			return "";
		}
	}

	/**
	 * 입력값인 문자열이 null이거나 ""일 경우 원하는 값으로 바꾸어서 리턴한다.
	 *
	 * @param stText			입력받은 문자열
	 * @param stResult			입력받은 문자열이 null이거나 "" 일 경우 바뀌어서 리턴되길 원하는 문자열
	 * @return String		
	 */
	public static String getNull(String stText, String stResult){
		try {
			if(stText == null || "".equals(stText)) return stResult;
			return stText;
		}catch(Exception e) {
			//e.printStackTrace();
			return stResult;
		}
	}
	
    public static String getRandomMD5() {
        String result = null;
        try {
            result = DigestUtils.md5DigestAsHex(String.valueOf(NumberUtil.getRandomNum(100000, 999999)).getBytes("UTF-8"));
        } catch (UnsupportedEncodingException e) {
            //throw new RuntimeException("can not make rand string");
        	result = "";
        }
        return result;
    }
    
	@SuppressWarnings("unchecked")
	public static String makeJsonString(List src) throws Exception {
		if (src == null || src.isEmpty()) return "[]";
		StringBuilder buf = new StringBuilder("[");
		for (int i = 0; i < src.size(); i++) {
			Object ele = src.get(i);
			buf.append((i == 0)?"":",");
			buf.append(parse2Json(ele));
		}
		buf.append("]");
		return buf.toString();
	}
	
	@SuppressWarnings("unchecked")
	private static String parse2Json(Object obj) throws Exception {
		if (obj == null) return "";
		if (obj instanceof Map) {
			Map<String, Object> map = (Map<String, Object>)obj;
			StringBuilder buf = new StringBuilder("{");
			Iterator<String> iter = map.keySet().iterator();
			for (int j = 0; iter.hasNext(); j++) {
				String key = iter.next();
				buf.append((j==0)?"":",");
				buf.append("\"").append(key).append("\":\"");
				buf.append((map.get(key) != null)?map.get(key).toString().replace("'", "\\'").replace("\"", "\\\"").replace("\r", "").replace("\n", " "):"").append("\"");
			}
			buf.append("}");
			return buf.toString();
		} else if (obj instanceof List) {
			return makeJsonString((List)obj);
		} else {
			throw new Exception();
		}
	}
	
	/**
	 * 두 날짜 사이의 시간차이를 구한다.
	 * 
	 * @param dateStart
	 * @param dateEnd
	 * @return
	 * @throws Exception
	 */
	public static String getDateTimeDiff(String dateStart, String dateEnd) throws Exception {
		//String dateStart = "11/03/14 09:29:58";
		//String dateEnd = "11/03/14 09:33:43";
		//dateStart = "2014-04-15 06:36:20.0";
		//dateEnd = "2014-04-15 10:36:09.0";
		// Custom date format
		SimpleDateFormat format = new SimpleDateFormat("yy-MM-dd HH:mm:ss");  

		Date d1 = null;
		Date d2 = null;
		try {
		    d1 = format.parse(dateStart);
		    d2 = format.parse(dateEnd);
		} catch (ParseException e) {
		    //e.printStackTrace();
			return "";
		}    

		// Get msec from each, and subtract.
		long diff = d2.getTime() - d1.getTime();
		long diffSeconds = diff / 1000;         
		long diffMinutes = diff / (60 * 1000);         
		long diffHours = diff / (60 * 60 * 1000);    
		long diffDays = diff / (24 * 60 * 60 * 1000); 
		
		long modSecond = diffSeconds % 60;
		long modMinutes = diffMinutes % 60;
		long modHours = diffHours % 24;
		long modDays = diffDays % 365;
		
		//System.out.println("Time in seconds: " + diffSeconds + " : "+ modSecond +" seconds.");         
		//System.out.println("Time in minutes: " + diffMinutes + " : "+ modMinutes +" minutes.");         
		//System.out.println("Time in hours: " + diffHours + " : "+ modHours +" hours.");
		//System.out.println("Time in days: " + diffDays + " : "+ modDays +" days.");
		
		String result = "";
		
		if(diffDays>0){
			result += modDays + "일 ";
		}
		if(diffHours>0){
			result += modHours + "시간 ";
		}
		if(diffMinutes>0){
			result += modMinutes + "분 ";
		}
		if(diffSeconds>0){
			result += modSecond + "초";
		}
		
		//System.out.println("Using Time : " + result );
		return result;
	}
	
	public static boolean isValidateDate(String a) {
		DateFormat formatter;
		try {
			formatter = new SimpleDateFormat("yyyyMMdd");
			formatter.setLenient(false);
			Date aa = formatter.parse(a);
			//System.out.println("debug:date=========:"+(aa.getYear() +":"+aa.getMonth() +":"+aa.getDay()));
		} catch (Exception e) {
			return false;
		}
		return true;
	}
	
	
	public static String paddingLeft(String _val, char _paddingChar, int _len){
		if(_val==null) return _val;
		if(_val.length()>=_len) return _val;
		String tmp = "";
		for(int i=0;i<_len-_val.length();i++){
			tmp += Character.toString(_paddingChar);
		}
		
		return tmp + _val;
		
	}

	/**
	 * 이미지 사이즈 변환 함수
	 * 
	 * 1. img tag의 src 부분만 추출
	 * 2. 추출된 src를 img tag 로 다시 변환후 리턴
	 * 
	 * @param contentHtml
	 * @return
	 */
	public static String getImgReplace(String contentHtml) {
		Pattern nonValidPattern = Pattern.compile("<img[^>]*src=[\"']?([^>\"']+)[\"']?[^>]*>");

		List<String> result = new ArrayList<String>();
		Matcher matcher = nonValidPattern.matcher(contentHtml);

		while (matcher.find()) {
			result.add(matcher.group(1));
			contentHtml = contentHtml.replaceAll(matcher.group(0), "<img src=\""+matcher.group(1).trim()+"\" style=\"width:100%\"/>");
		}

		return contentHtml;
	}
}
