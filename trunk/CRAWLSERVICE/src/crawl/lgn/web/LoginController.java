package crawl.lgn.web;

import javax.servlet.http.HttpServletRequest;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;

import crawl.lgn.service.LoginService;
import crawl.vo.VoCommon;
import resources.com.util.StringUtils;

/**
 * 로그인 Controller
 */
@Controller
public class LoginController{
 	@Autowired
	LoginService loginService;
 
	public Logger logger = Logger.getLogger(this.getClass());
	public StringBuffer sbDebugLog = new StringBuffer();

	/**
	 * 로그인
	 * 
	 * @param Model model
	 * @param HttpServletRequest  request
	 * @return void
	 */
	@RequestMapping(value="/usr/lgn/login.do")
	public String login(ModelMap model, HttpServletRequest request) throws Exception {
		sbDebugLog.setLength(0);
		VoCommon sessionVO = new VoCommon();
		sessionVO.getSessionData(request);
		try{
			model.addAttribute("sessionVO", (VoCommon)sessionVO);
			
			if(!"".equals(StringUtils.getNull(sessionVO.getS_user_id()))){
				return "redirect:/mng/man/index.do";
			}
			
			String retMessage = StringUtils.getNull(request.getParameter("fail"));
			if(!"".equals(retMessage)){
				model.addAttribute("retMessage", retMessage);
			}
			return "usr/lgn/login";
		}catch(Exception e){
			logger.error("=+=+=+=+=+=+=+=+=+=+=+=+=+= EXCEPTION START +=+=+=+=+=+=+=+=+=+=+=+=");
			logger.error("EXCEPTION 위치 : "+ request.getServletPath());
			logger.error("EXCEPTION 내용 : \n" + e.toString());
			logger.error("=+=+=+=+=+=+=+=+=+=+=+=+=+= EXCEPTION END   +=+=+=+=+=+=+=+=+=+=+=+=");

			throw new Exception(e);
		}finally{
			logger.debug("=========================== DEBUG START ============================");
			logger.debug("DEBUG 위치 : "+ request.getServletPath() + sbDebugLog.toString());
			logger.debug("=========================== DEBUG END    ===========================");
			sbDebugLog.setLength(0);
		}
	}
}