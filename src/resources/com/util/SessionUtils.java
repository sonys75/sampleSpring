package resources.com.util;

import org.apache.log4j.Logger;
import org.springframework.web.context.request.RequestAttributes;
import org.springframework.web.context.request.RequestContextHolder;
/**
 * SessionUtils
 * Spring에서 제공하는 RequestContextHolder 를 이용하여
 * request 객체를 service까지 전달하지 않고 사용할 수 있게 해줌
 * 
 */
public class SessionUtils {
	
	public Logger logger;
	public StringBuffer sbDebugLog	=	new StringBuffer();
	/**
	 * attribute 값을 가져 오기 위한 method
	 * 
	 * @param String  attribute key name 
	 * @return Object attribute obj
	 */
	public static Object getAttribute(String name) throws Exception {
		return (Object)RequestContextHolder.getRequestAttributes().getAttribute(name, RequestAttributes.SCOPE_SESSION);
	}

	/**
	 * attribute 설정 method
	 * 
	 * @param String  attribute key name 
	 * @param Object  attribute obj
	 * @return void
	 */
	public static void setAttribute(String name, Object object) throws Exception {
		RequestContextHolder.getRequestAttributes().setAttribute(name, object, RequestAttributes.SCOPE_SESSION);
	}

	/**
	 * SCOPE_SESSION의 지정한 attribute 삭제 
	 * 
	 * @param String  attribute key name 
	 * @return void
	 */
	public static void removeAttribute(String name) throws Exception {
		RequestContextHolder.getRequestAttributes().removeAttribute(name, RequestAttributes.SCOPE_SESSION);
	}

	/**
	 * RequestContextHolder 안의 SCOPE_SESSION 초기화 처리
	 * 
	 * @param
	 * @return void
	 */
	public static void removeAttributes() throws Exception {
		String[] attrHolder = RequestContextHolder.getRequestAttributes().getAttributeNames(RequestAttributes.SCOPE_SESSION);

		System.out.println("=========================== LOGOUT INFO START ============================");
		for(int i=0;i<attrHolder.length;i++){
			System.out.println("##### "+ attrHolder[i] +" : "+getAttribute(attrHolder[i]));
			removeAttribute(attrHolder[i]);
		}
		System.out.println("=========================== LOGOUT INFO END    ===========================");
	}
	
	/**
	 * session id 
	 * 
	 * @param void
	 * @return String SessionId 값
	 */
	public static String getSessionId() throws Exception  {
		return RequestContextHolder.getRequestAttributes().getSessionId();
	}
}
