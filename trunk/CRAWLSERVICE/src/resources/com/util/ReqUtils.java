package resources.com.util;

import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.util.Enumeration;

import javax.servlet.http.HttpServletRequest;

/**
 * HTTP으로 각종 파라메터 처리
 */
public class ReqUtils {

    private HttpServletRequest req;

    public ReqUtils(HttpServletRequest req) {
        this.req = req;
    }

    public String getString(String name) {
        return getString(name, null);
    }

    public String getString(String name, String defaultValue) {
        String strValue = getReq().getParameter(name);

        if (strValue == null) {
            return defaultValue;
        }
        return strValue;
    }

    public String[] getStringArray(String name) {
        String[] strArray = getReq().getParameterValues(name);

        if (strArray == null) {
            return null;
        }

        String[] newStrArray = new String[strArray.length];

        for (int i = 0; i < strArray.length; ++i) {
            newStrArray[i] = strArray[i];
        }

        return newStrArray;
    }

    public int getInt(String name) {
        return getInt(name, 0);
    }

    public int getInt(String name, int defaultValue) {
        String strValue = getReq().getParameter(name);
        int intValue = defaultValue;

        if (strValue == null) {
            return intValue;
        }
        try {
            intValue = Integer.parseInt(strValue);
        } catch (NumberFormatException e) {
        }
        return intValue;
    }

    public int[] getIntArray(String name) {
        return getIntArray(name, 0);
    }

    public int[] getIntArray(String name, int defaultValue) {
        String[] strArray = getReq().getParameterValues(name);

        if (strArray == null) {
            return null;
        }

        int[] intArray = new int[strArray.length];

        for (int i = 0; i < strArray.length; ++i) {
            try {
                intArray[i] = Integer.parseInt(strArray[i]);
            } catch (NumberFormatException e) {
                intArray[i] = defaultValue;
            }
        }

        return intArray;
    }

    public float getFloat(String name) {
        return getFloat(name, 0.0F);
    }

    public float getFloat(String name, float defaultValue) {
        String strValue = getReq().getParameter(name);
        float floatValue = defaultValue;

        if (strValue == null) {
            return floatValue;
        }
        try {
            floatValue = Float.parseFloat(strValue);
        } catch (NumberFormatException e) {
        }
        return floatValue;
    }

    public long getLong(String name) {
        return getLong(name, 0L);
    }

    public long getLong(String name, long defaultValue) {
        String strValue = getReq().getParameter(name);
        long longValue = defaultValue;

        if (strValue == null) {
            return longValue;
        }
        try {
            longValue = Long.parseLong(strValue);
        } catch (NumberFormatException e) {
        }
        return longValue;
    }

    public long[] getLongArray(String name) {
        return getLongArray(name, 0L);
    }

    public long[] getLongArray(String name, long defaultValue) {
        String[] strArray = getReq().getParameterValues(name);

        if (strArray == null) {
            return null;
        }

        long[] longArray = new long[strArray.length];

        for (int i = 0; i < strArray.length; ++i) {
            try {
                longArray[i] = Long.parseLong(strArray[i]);
            } catch (NumberFormatException e) {
                longArray[i] = defaultValue;
            }
        }

        return longArray;
    }

    public double getDouble(String name) {
        return getDouble(name, 0.0D);
    }

    public double getDouble(String name, double defaultValue) {
        String strValue = getReq().getParameter(name);
        double doubleValue = defaultValue;

        if (strValue == null) {
            return defaultValue;
        }
        try {
            doubleValue = Double.parseDouble(strValue);
        } catch (Exception e) {
        }
        return doubleValue;
    }

    public boolean getBoolean(String name) {
        return getBoolean(name, false);
    }

    public boolean getBoolean(String name, boolean defaultValue) {
        String strValue = getReq().getParameter(name);

        if (strValue == null) {
            return defaultValue;
        }

        if (strValue.equals("0")) {
            return false;
        }
        if (strValue.equals("1")) {
            return true;
        }
        if (strValue.toLowerCase().equals("true")) {
            return true;
        }
        if (strValue.toLowerCase().equals("false")) {
            return false;
        }
        if (strValue.toLowerCase().equals("yes")) {
            return true;
        }
        if (strValue.toLowerCase().equals("no")) {
            return false;
        }
        return defaultValue;
    }

    public HttpServletRequest getReq() {
        return this.req;
    }

    public void setReq(HttpServletRequest req) {
        this.req = req;
    }
    
    /**
	 * getPageParamStr
	 * 페이징 처리시에 파라미터를 String 형식으로 넘긴다.
	 * page_no 파라미터를 넘기지 않는다.
	 * 
	 * @param HttpServletRequest req
	 * @return String
	 */
    public String getPageParamStr(HttpServletRequest req) {
    	StringBuffer returnParam = new StringBuffer();
		
    	Enumeration enumer = req.getParameterNames();
		
		while(enumer.hasMoreElements()){
			Object obj = enumer.nextElement();
			String paramvalue = StringUtils.getNull(req.getParameterValues((String)obj)[0]);
			try {
				if(!"page_no".equals(obj.toString()) && !"x".equals(obj.toString()) &&  !"y".equals(obj.toString()) &&  !"".equals(paramvalue)){
					if(!"".equals(returnParam.toString()))returnParam.append("&");
					returnParam.append(obj.toString()+"="+URLEncoder.encode(paramvalue,"UTF-8"));
				}
			} catch (UnsupportedEncodingException e) {

			}
		}
        return returnParam.toString();
    }
    
    /**
	 * getAllParamStr
	 * 모든 request 파라미터를 String 형식으로 넘긴다.
	 * 
	 * @param HttpServletRequest req
	 * @return String
	 */
    public String getAllParamStr(HttpServletRequest req) {
    	StringBuffer returnParam = new StringBuffer();
		
    	Enumeration enumer = req.getParameterNames();
		
		while(enumer.hasMoreElements()){
			Object obj = enumer.nextElement();
			String paramvalue = StringUtils.getNull(req.getParameterValues((String)obj)[0]);
			try {
				if(!"x".equals(obj.toString()) &&  !"y".equals(obj.toString()) &&  !"".equals(paramvalue)){
					if(!"".equals(returnParam.toString()))returnParam.append("&");
					returnParam.append(obj.toString()+"="+URLEncoder.encode(paramvalue,"UTF-8"));
				}
			} catch (UnsupportedEncodingException e) {

			}
		}
        return returnParam.toString();
    }
    
    /**
	 * getChkParamStr
	 * 선택된 request 파라미터를 String 형식으로 넘긴다.
	 * 
	 * @param HttpServletRequest req
	 * @param String params
	 * @return String
	 */
    public String getChkParamStr(HttpServletRequest req,String params) {
    	StringBuffer returnParam = new StringBuffer();
    	Enumeration enumer = req.getParameterNames();
    	String arrParams[] = params.split(",");
		while(enumer.hasMoreElements()){
			Object obj = enumer.nextElement();
			String paramvalue = StringUtils.getNull(req.getParameterValues((String)obj)[0]);
			try {
				if(!"x".equals(obj.toString()) &&  !"y".equals(obj.toString()) &&  !"".equals(paramvalue)){
					for(int i=0;i<arrParams.length;i++){
						if(arrParams[i].equals(obj.toString())){
							if(!"".equals(returnParam.toString()))returnParam.append("&");
							returnParam.append(obj.toString()+"="+URLEncoder.encode(paramvalue,"UTF-8"));
						}
					}
				}
			} catch (UnsupportedEncodingException e) {

			}
		}
        return returnParam.toString();
    }
}