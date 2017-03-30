package resources.com.service;

import javax.servlet.http.HttpServletRequest;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import crawl.vo.VoCommon;
import resources.com.util.SessionUtils;
import resources.com.util.StringUtils;
/**
 * SessionMessage
 * message 와 return Url 등을 전달할 수 있도록 함.
 * 
 */
public class SessionMessage {
	private  static final Logger logger = LoggerFactory.getLogger(SessionMessage.class);
	public static StringBuffer sbDebugLog = new StringBuffer();
	/**
	 * 메세지를 세팅함 
	 * 
	 * @param HttpServletRequest request
	 * @param String retMessage
	 * @return void
	 */
	public static void setMessage(HttpServletRequest request, String retMessage){
		sbDebugLog.setLength(0);
		try {
			try{
				sbDebugLog.append("\n retMessage = " + MessageAccessor.getMessage(retMessage));
				SessionUtils.setAttribute("retMessage",MessageAccessor.getMessage(retMessage));
			}catch(org.springframework.context.NoSuchMessageException ex){
				sbDebugLog.append("\n retMessage = " + retMessage);
				SessionUtils.setAttribute("retMessage",retMessage);
			}
		} catch (Exception e) {
			sbDebugLog.append("\n Exception = " + e.toString());
			e.printStackTrace();
		}finally{
			logger.debug("=========================== DEBUG START ============================");
			logger.debug("DEBUG 위치 : setMessage(HttpServletRequest request, String retMessage)" + sbDebugLog.toString());
			logger.debug("=========================== DEBUG END    ===========================");
			sbDebugLog.setLength(0);
		}
	}
	
	/**
	 * 메세지,URL을 세팅함 
	 * 
	 * @param HttpServletRequest request
	 * @param String retMessage
	 * @param String retUrl
	 * @return void
	 */
	public static void setMessage(HttpServletRequest request, String retMessage, String retUrl){
		sbDebugLog.setLength(0);
		try {
			try{
				sbDebugLog.append("\n retMessage = " + MessageAccessor.getMessage(retMessage));
				SessionUtils.setAttribute("retMessage",MessageAccessor.getMessage(retMessage));
			}catch(org.springframework.context.NoSuchMessageException ex){
				sbDebugLog.append("\n retMessage = " + retMessage);
				SessionUtils.setAttribute("retMessage",retMessage);
			}
			sbDebugLog.append("\n retUrl = " + retUrl);
			SessionUtils.setAttribute("retUrl",retUrl);
		} catch (Exception e) {
			sbDebugLog.append("\n Exception = " + e.toString());
			e.printStackTrace();
		}finally{
			logger.debug("=========================== DEBUG START ============================");
			logger.debug("DEBUG 위치 : setMessage(HttpServletRequest request, String retMessage, String retUrl)" + sbDebugLog.toString());
			logger.debug("=========================== DEBUG END    ===========================");
			sbDebugLog.setLength(0);
		}
	}
	
	/**
	 * 메세지,URL,TARGET을 세팅함 
	 * 
	 * @param HttpServletRequest request
	 * @param String retMessage
	 * @param String retUrl
	 * @param String retTarget
	 * @return void
	 */
	public static void setMessage(HttpServletRequest request, String retMessage, String retUrl, String retTarget){
		sbDebugLog.setLength(0);
		try {
			try{
				sbDebugLog.append("\n retMessage = " + MessageAccessor.getMessage(retMessage));
				SessionUtils.setAttribute("retMessage",MessageAccessor.getMessage(retMessage));
			}catch(org.springframework.context.NoSuchMessageException ex){
				sbDebugLog.append("\n retMessage = " + retMessage);
				SessionUtils.setAttribute("retMessage",retMessage);
			}
			sbDebugLog.append("\n retUrl = " + retUrl);
			SessionUtils.setAttribute("retUrl",retUrl);
			sbDebugLog.append("\n retTarget = " + retTarget);
			SessionUtils.setAttribute("retTarget",retTarget);
		} catch (Exception e) {
			sbDebugLog.append("\n Exception = " + e.toString());
			e.printStackTrace();
		}finally{
			logger.debug("=========================== DEBUG START ============================");
			logger.debug("DEBUG 위치 : setMessage(HttpServletRequest request, String retMessage, String retUrl, String retTarget)" + sbDebugLog.toString());
			logger.debug("=========================== DEBUG END    ===========================");
			sbDebugLog.setLength(0);
		}
	}
	
	/**
	 * 메세지,URL초기화함. 
	 * VoCommon Model에 메세지와 URL을 저장한 후
	 * Session 에 있는 메세지와 URL을 삭제한다.
	 *   
	 * @param VoCommon
	 * @return void
	 */
	public static void removeMessage(VoCommon voCommon){
		try {
			voCommon.setRetMessage(StringUtils.getNull(SessionUtils.getAttribute("retMessage")));
			voCommon.setRetUrl(StringUtils.getNull(SessionUtils.getAttribute("retUrl")));
			voCommon.setRetTarget(StringUtils.getNull(SessionUtils.getAttribute("retTarget")));
			SessionUtils.removeAttribute("retMessage");
			SessionUtils.removeAttribute("retUrl");
			SessionUtils.removeAttribute("retTarget");
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
}
