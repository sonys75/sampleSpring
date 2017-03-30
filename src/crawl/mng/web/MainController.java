package crawl.mng.web;

import java.util.HashMap;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;

import crawl.crn.nws.service.NewsService;
import crawl.sys.mnu.aut.service.MenuAuthService;
import crawl.vo.VoMenu;
import crawl.vo.VoNews;
import crawl.vo.VoUserInfo;
import resources.com.service.SessionMessage;

/**
 * 메인화면 Controller
 */
@Controller
public class MainController {

 	public Logger logger;
	public StringBuffer sbDebugLog	=	new StringBuffer();

	@Autowired
	NewsService newsService;
 
	@Autowired
	MenuAuthService menuAuthService;

	@RequestMapping({"/mng/man/index.do"})
	public String index(Model model, HttpServletRequest request){
		logger = Logger.getLogger(this.getClass());
		sbDebugLog.setLength(0);

		//파라미터 설정
		//Parameters param = new Parameters(request);
		try{		
			HttpSession session = request.getSession(false);
			VoUserInfo voUserInfo = (VoUserInfo)session.getAttribute("userInfoVO");
			System.out.println("voUserInfo.getAuth_id() : "+ voUserInfo.getAuth_id());
			//System.out.println("voUserInfo.getUser_authorities() : "+ voUserInfo.getUser_authorities());
			//System.out.println("voUserInfo.getCorp_nm() : "+ voUserInfo.getCorp_nm());

			List<VoNews> newsList = this.newsService.selectListNewsRss(new HashMap<String,String>());
			model.addAttribute("newsCount", newsList.size());
			model.addAttribute("newsList", newsList);
 
			if("SUPERADMIN".equals(voUserInfo.getAuth_id())){

			}
			return "mng/man/index";
		}catch(Exception e){
			logger.error("=+=+=+=+=+=+=+=+=+=+=+=+=+= EXCEPTION START +=+=+=+=+=+=+=+=+=+=+=+=");
			logger.error("EXCEPTION 위치 : "+ request.getServletPath());
			logger.error("EXCEPTION 내용 : \n" + e.toString());
			logger.error("=+=+=+=+=+=+=+=+=+=+=+=+=+= EXCEPTION END   +=+=+=+=+=+=+=+=+=+=+=+=");
			SessionMessage.setMessage(request,"Common.fail.Exception");
   			return "cmm/message";	
		}finally{
			
			Runtime rt = Runtime.getRuntime();
			long free = rt.freeMemory();
			long total = rt.totalMemory();
			long unUsedRatio = free * 100 / total;
			
			if(unUsedRatio<10){
				logger.error("=+=+=+=+=+=+=+=+=+=+=+=+=+= GARBAGE COLLECTION CLEAR START +=+=+=+=+=+=+=+=+=+=+=+=");
				logger.error("GARBAGE COLLECTION CLEAR 위치 : "+ request.getServletPath());
				logger.error("내용 : 전체 메모리 = "+ (Long)total/1024 +"KB, 사용 메모리 = "+ (Long)(total - free)/1024 +"KB, 남은 메모리 = "+ (Long)free/1024 +"KB, 남아있는 메모리 비율 = "+unUsedRatio+"%");
				System.gc();
				System.runFinalization();
				logger.error("=+=+=+=+=+=+=+=+=+=+=+=+=+= GARBAGE COLLECTION CLEAR END   +=+=+=+=+=+=+=+=+=+=+=+=");
			}
			logger.debug("=========================== DEBUG START ============================");
			logger.debug("DEBUG 위치 : "+ request.getServletPath() + sbDebugLog.toString());
			logger.debug("=========================== DEBUG END    ===========================");
			sbDebugLog.setLength(0);
		}
	}
	
	@RequestMapping({"/cmm/lay/mng/header.do"})
	public String header(Model model, HttpServletRequest request){
		logger = Logger.getLogger(this.getClass());
		sbDebugLog.setLength(0);
		try{
			HttpSession session = request.getSession(false);
			VoUserInfo voUserInfo = (VoUserInfo)session.getAttribute("userInfoVO");
			System.out.println("voUserInfo.getAuth_id() : "+ voUserInfo.getAuth_id());
			System.out.println("voUserInfo.getUser_authorities() : "+ voUserInfo.getUser_authorities());
			//System.out.println("voUserInfo.getCorp_nm() : "+ voUserInfo.getCorp_nm());
			
			model.addAttribute("VoUserInfo", voUserInfo);
			return "cmm/lay/mng/header";
		}catch(Exception e){
			logger.error("=+=+=+=+=+=+=+=+=+=+=+=+=+= EXCEPTION START +=+=+=+=+=+=+=+=+=+=+=+=");
			logger.error("EXCEPTION 위치 : "+ request.getServletPath());
			logger.error("EXCEPTION 내용 : \n" + e.toString());
			logger.error("=+=+=+=+=+=+=+=+=+=+=+=+=+= EXCEPTION END   +=+=+=+=+=+=+=+=+=+=+=+=");
			SessionMessage.setMessage(request,"Common.fail.Exception","/");
   			return "cmm/message";
		}finally{
			/*
			logger.debug("=========================== DEBUG START ============================");
			logger.debug("DEBUG 위치 : "+ request.getServletPath() + sbDebugLog.toString());
			logger.debug("=========================== DEBUG END    ===========================");
			*/
			sbDebugLog.setLength(0);
		}
	}
	
	@RequestMapping({"/cmm/lay/mng/leftMenu.do"})
	public String leftMenu(Model model, HttpServletRequest request){
		logger = Logger.getLogger(this.getClass());
		sbDebugLog.setLength(0);
		try{		
			HttpSession session = request.getSession(false);
			VoUserInfo voUserInfo = (VoUserInfo)session.getAttribute("userInfoVO");
			
			HashMap<String, String> hMap = new HashMap<String,String>();
			hMap.put("authId", voUserInfo.getAuth_id());
			
			List<VoMenu> retMenu = null;
			
			//관련정보 쿼리
			retMenu = this.menuAuthService.selectListMenuInfoByAuthId(hMap);
			
			model.addAttribute("MenuList", retMenu );
			
			return "cmm/lay/mng/leftMenu";
		}catch(Exception e){
			logger.error("=+=+=+=+=+=+=+=+=+=+=+=+=+= EXCEPTION START +=+=+=+=+=+=+=+=+=+=+=+=");
			logger.error("EXCEPTION 위치 : "+ request.getServletPath());
			logger.error("EXCEPTION 내용 : \n" + e.toString());
			logger.error("=+=+=+=+=+=+=+=+=+=+=+=+=+= EXCEPTION END   +=+=+=+=+=+=+=+=+=+=+=+=");
			SessionMessage.setMessage(request,"Common.fail.Exception","/");
   			return "cmm/message";	
		}finally{
			/*
			logger.debug("=========================== DEBUG START ============================");
			logger.debug("DEBUG 위치 : "+ request.getServletPath() + sbDebugLog.toString());
			logger.debug("=========================== DEBUG END    ===========================");
			*/
			sbDebugLog.setLength(0);
		}
	}
	
	@RequestMapping({"/cmm/lay/mng/copyrights.do"})
	public String copyrights(Model model, HttpServletRequest request){
		logger = Logger.getLogger(this.getClass());
		sbDebugLog.setLength(0);
		try{		
			HttpSession session = request.getSession(false);
			VoUserInfo voUserInfo = (VoUserInfo)session.getAttribute("userInfoVO");
			//System.out.println("voUserInfo.getAuth_id() : "+ voUserInfo.getAuth_id());
			//System.out.println("voUserInfo.getUser_authorities() : "+ voUserInfo.getUser_authorities());
			//System.out.println("voUserInfo.getAuth_id() : "+ voUserInfo.getAuth_id());
			
			model.addAttribute("VoUserInfo", voUserInfo);
			return "cmm/lay/mng/copyrights";
		}catch(Exception e){
			logger.error("=+=+=+=+=+=+=+=+=+=+=+=+=+= EXCEPTION START +=+=+=+=+=+=+=+=+=+=+=+=");
			logger.error("EXCEPTION 위치 : "+ request.getServletPath());
			logger.error("EXCEPTION 내용 : \n" + e.toString());
			logger.error("=+=+=+=+=+=+=+=+=+=+=+=+=+= EXCEPTION END   +=+=+=+=+=+=+=+=+=+=+=+=");
			SessionMessage.setMessage(request,"Common.fail.Exception","/");
   			return "cmm/message";
		}finally{
			/*
			logger.debug("=========================== DEBUG START ============================");
			logger.debug("DEBUG 위치 : "+ request.getServletPath() + sbDebugLog.toString());
			logger.debug("=========================== DEBUG END    ===========================");
			*/
			sbDebugLog.setLength(0);
		}
	}	
}