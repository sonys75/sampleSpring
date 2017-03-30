package crawl.cmm.sch.web;

import java.util.HashMap;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;

import crawl.cmm.cde.service.CodeService;
import crawl.sys.mnu.mng.service.MenuManageService;
import crawl.sys.res.mng.service.ResourceService;
import crawl.vo.VoAuth;
import crawl.vo.VoMenu;
import crawl.vo.VoReso;
import crawl.vo.VoUserInfo;
import resources.com.util.ReqUtils;

/**
 * SearchController
 */
@Controller
public class SearchController {
 	
 	@Autowired
 	CodeService codeService;
 	
 	@Autowired
 	ResourceService resourceService;
 	
 	@Autowired
	MenuManageService menuManageService;
 	
	public Logger logger = Logger.getLogger(this.getClass());
	public StringBuffer sbDebugLog = new StringBuffer();
 

	
	/**
	 * 권한 검색
	 * 
	 * @param Model model
	 * @param HttpServletRequest  request
	 * @return void
	 */
	@RequestMapping(value="/cmm/sch/auth.do")
	public String cmmSchAuth(ModelMap model, HttpServletRequest request) throws Exception {
		sbDebugLog.setLength(0);
		try{
			//파라미터 설정
			ReqUtils req = new ReqUtils(request);
			
			HttpSession session = request.getSession(false);
			VoUserInfo voUserInfo = (VoUserInfo)session.getAttribute("userInfoVO");
			
			sbDebugLog.append("\n DEBUG AllParameter : [ " + req.getAllParamStr(request) + " ]");

			HashMap<String, String> hMap = new HashMap<String,String>();
			hMap.put("userId", voUserInfo.getUser_id());
			hMap.put("corpId", voUserInfo.getCorp_id());
			hMap.put("userAuth", voUserInfo.getAuth_id());

			VoAuth voAuth = this.codeService.selectAuthNo(hMap);

			hMap.put("authNo", voAuth.getAuth_no());
			List<VoAuth> authList = this.codeService.selectAuthList(hMap);

			//검색정보 세팅
			model.addAttribute("totalCount", authList.size());
			model.addAttribute("list", authList);

			return "cmm/sch/auth";
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
	
	/**
	 * 모든 권한 검색
	 * 
	 * @param Model model
	 * @param HttpServletRequest  request
	 * @return void
	 */
	@RequestMapping(value="/cmm/sch/authAll.do")
	public String cmmSchAuthAll(ModelMap model, HttpServletRequest request) throws Exception {
		sbDebugLog.setLength(0);
		try{
			//파라미터 설정
			ReqUtils req = new ReqUtils(request);
			String sSeq = req.getString("seq");
			List<VoReso> authList = this.resourceService.selectListAllResoAuth(new HashMap<String,String>());

			//검색정보 세팅
			model.addAttribute("totalCount", authList.size());
			model.addAttribute("list", authList);
			model.addAttribute("seq", sSeq);
			
			return "cmm/sch/authAll";
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
	
	/**
	 * 메뉴 검색
	 * 
	 * @param Model model
	 * @param HttpServletRequest  request
	 * @return void
	 */
	@RequestMapping(value="/cmm/sch/menu.do")
	public String cmmSchMenu(ModelMap model, HttpServletRequest request) throws Exception {
		sbDebugLog.setLength(0);
		try{
			List<VoMenu> menuList = this.menuManageService.selectListMenu(new HashMap<String,String>());

			//검색정보 세팅
			model.addAttribute("totalCount", menuList.size());
			model.addAttribute("list", menuList);
			
			return "cmm/sch/menu";
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