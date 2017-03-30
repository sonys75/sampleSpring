package crawl.mpg.web;

import java.util.HashMap;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import crawl.mpg.service.MyPageService;
import crawl.vo.VoUserInfo;
import resources.com.service.MessageAccessor;
import resources.com.service.SessionMessage;
import resources.com.util.CryptUtils;
import resources.com.util.ReqUtils;
import resources.com.util.StringUtils;

/**
 * MyPage Controller
 */
@Controller
public class MyPageController {
 	@Autowired
 	MyPageService myPageService;
 
	public Logger logger = Logger.getLogger(this.getClass());
	public StringBuffer sbDebugLog = new StringBuffer();

	/**
	 * 개인정보수정
	 * 
	 * @param Model model
	 * @param HttpServletRequest  request
	 * @return void
	 */
	@RequestMapping(value="/usr/edt/mypage.do")
	public String usrEdtMypage(ModelMap model, HttpServletRequest request) throws Exception {
		sbDebugLog.setLength(0);
		try{
			HttpSession session = request.getSession(false);
			VoUserInfo voUserInfo = (VoUserInfo)session.getAttribute("userInfoVO");

			HashMap<String, String> hMap = new HashMap<String,String>();
			hMap.put("userId", voUserInfo.getUser_id());
			hMap.put("corpId", voUserInfo.getCorp_id());
			
			sbDebugLog.append("\n DEBUG Parameter : user_id = " + hMap.get("userId"));
			sbDebugLog.append("\n DEBUG Parameter : corp_id = " + hMap.get("corpId"));
			//사용자 정보 세팅
			voUserInfo = this.myPageService.selectUserInfoByUserId(hMap);
 
			return "usr/edt/mypage";
		}catch(Exception e){
			logger.error("=+=+=+=+=+=+=+=+=+=+=+=+=+= EXCEPTION START +=+=+=+=+=+=+=+=+=+=+=+=");
			logger.error("EXCEPTION 위치 : "+ request.getServletPath());
			logger.error("EXCEPTION 내용 : \n" + e.toString());
			logger.error("=+=+=+=+=+=+=+=+=+=+=+=+=+= EXCEPTION END   +=+=+=+=+=+=+=+=+=+=+=+=");
			SessionMessage.setMessage(request,"Common.fail.Exception","/mng/man/index.do");
			return "cmm/message";
		}finally{
			logger.debug("=========================== DEBUG START ============================");
			logger.debug("DEBUG 위치 : "+ request.getServletPath() + sbDebugLog.toString());
			logger.debug("=========================== DEBUG END    ===========================");
			sbDebugLog.setLength(0);
		}
	}

	/**
	 * 개인정보수정 처리
	 * 
	 * @param Model model
	 * @param HttpServletRequest  request
	 * @return void
	 */
	@RequestMapping(value="/usr/edt/mypageProc.do", method=RequestMethod.POST)
	public String usrEdtMypageProc(ModelMap model, HttpServletRequest request) throws Exception {
		sbDebugLog.setLength(0);
		try{
			ReqUtils req = new ReqUtils(request);
			
			HttpSession session = request.getSession(false);
			VoUserInfo voUserInfo = (VoUserInfo)session.getAttribute("userInfoVO");

			HashMap<String, String> hMap = new HashMap<String,String>();
			hMap.put("userId", voUserInfo.getUser_id());
			hMap.put("userPw", req.getString("user_pw"));

			sbDebugLog.append("\n DEBUG Parameter : user_id = " + hMap.get("userId"));
			sbDebugLog.append("\n DEBUG Parameter : user_pw = " + hMap.get("userPw"));
			hMap.put("userPw", CryptUtils.enCrypt(StringUtils.convertSalting(hMap.get("userId"), hMap.get("userPw"))));
			
			sbDebugLog.append("\n DEBUG Parameter : user_pw = " + hMap.get("userPw"));
			
			//사용자 정보 조회
			VoUserInfo retUserInfo = this.myPageService.selectUserInfoByUserId(hMap);

			if(retUserInfo==null){
				sbDebugLog.append("\n DEBUG 메세지 : "+MessageAccessor.getMessage("Login.fail.user_id.none"));
				SessionMessage.setMessage(request,"Login.fail.user_id.none");
			}else{
				if(!retUserInfo.getUser_pw().equals(hMap.get("userPw"))){
					sbDebugLog.append("\n DEBUG 메세지 : "+MessageAccessor.getMessage("Login.fail.user_pw.wrong"));
					SessionMessage.setMessage(request,"Login.fail.user_pw.wrong");
				}else{
					if(StringUtils.getNull(req.getString("re_user_pw")).length() > 0 && req.getString("re_user_pw").equals(req.getString("re_user_pw_confirm"))){
						hMap.put("userRePw", CryptUtils.enCrypt(StringUtils.convertSalting(hMap.get("userId"), req.getString("re_user_pw"))));
						sbDebugLog.append("\n DEBUG Parameter : re_user_pw = " + req.getString("re_user_pw"));
						sbDebugLog.append("\n DEBUG Parameter : CryptUtil.Encrypt(StringUtils.convertSalting(UserId,re_user_pw)) = " + hMap.get("userRePw"));
					}
					
					if(StringUtils.getNull(req.getString("email_id")).length() > 0 && StringUtils.getNull(req.getString("email_host")).length()>0){
						hMap.put("userEmail", StringUtils.getNull(req.getString("email_id")) +"@"+ StringUtils.getNull(req.getString("email_host")));
					}else{
						hMap.put("userEmail", "");
					}
					
					hMap.put("userHp", StringUtils.getNull(req.getString("user_hp")));
					hMap.put("userTel", StringUtils.getNull(req.getString("user_tel")));
					hMap.put("userIp", request.getRemoteAddr());
					
					sbDebugLog.append("\n DEBUG Parameter : user_email = " + hMap.get("userEmail"));
					sbDebugLog.append("\n DEBUG Parameter : user_hp = " + hMap.get("userHp"));
					sbDebugLog.append("\n DEBUG Parameter : user_tel = " + hMap.get("userTel"));
					sbDebugLog.append("\n DEBUG Parameter : user_ip = " + hMap.get("userIp"));
					
					this.myPageService.updateUserInfoByUserIdPW(hMap);
					SessionMessage.setMessage(request,"Process.data.success.update","/","parent");
				}
			}
 
			return "cmm/message";
		}catch(Exception e){
			logger.error("=+=+=+=+=+=+=+=+=+=+=+=+=+= EXCEPTION START +=+=+=+=+=+=+=+=+=+=+=+=");
			logger.error("EXCEPTION 위치 : "+ request.getServletPath());
			logger.error("EXCEPTION 내용 : \n" + e.toString());
			logger.error("=+=+=+=+=+=+=+=+=+=+=+=+=+= EXCEPTION END   +=+=+=+=+=+=+=+=+=+=+=+=");
			SessionMessage.setMessage(request,"Common.fail.Exception","/usr/edt/mypage.do","parent");
			return "cmm/message";
		}finally{
			logger.debug("=========================== DEBUG START ============================");
			logger.debug("DEBUG 위치 : "+ request.getServletPath() + sbDebugLog.toString());
			logger.debug("=========================== DEBUG END    ===========================");
			sbDebugLog.setLength(0);
		}
	}
}