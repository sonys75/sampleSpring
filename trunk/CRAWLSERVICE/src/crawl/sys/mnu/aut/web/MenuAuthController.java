package crawl.sys.mnu.aut.web;

import java.util.HashMap;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import crawl.cmm.cde.service.CodeService;
import crawl.sys.mnu.aut.service.MenuAuthService;
import crawl.vo.VoAuth;
import crawl.vo.VoMenu;
import crawl.vo.VoUserInfo;
import resources.com.service.SessionMessage;
import resources.com.util.ReqUtils;
import resources.com.util.StringUtils;

/**
 * MenuAuthController
 */
@Controller
public class MenuAuthController {

	@Autowired
	MenuAuthService menuAuthService;
	
 	@Autowired
 	CodeService codeService;
 	
	public Logger logger = Logger.getLogger(this.getClass());
	public StringBuffer sbDebugLog = new StringBuffer();

	/**
	 * 메뉴-권한 목록
	 * 
	 * @param Model model
	 * @param HttpServletRequest  request
	 * @return void
	 */
	@RequestMapping(value="/sys/mnu/aut/list.do")
	public String sysMnuAutList(ModelMap model, HttpServletRequest request) throws Exception {
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
			
			return "sys/mnu/aut/list";
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
	 * 메뉴-권한 조회
	 * 
	 * @param Model model
	 * @param HttpServletRequest  request
	 * @return void
	 */
	@RequestMapping(value="/sys/mnu/aut/view.do")
	public String sysMnuAutView(ModelMap model, HttpServletRequest request) throws Exception {
		sbDebugLog.setLength(0);
		//파라미터 설정
		ReqUtils req = new ReqUtils(request);
		
		try{
			//검색어 처리
			HashMap<String, String> hMap = new HashMap<String,String>();
			hMap.put("authId", StringUtils.getNull(req.getString("auth_id")));

			//리턴 정보 세팅
			VoMenu voMenu = new VoMenu();
			voMenu.setAuth_id(hMap.get("authId"));
			List<VoMenu> retMenu = null;
			
			VoAuth voAuth = new VoAuth();

			//auth_id 가 넘어올 경우 관련 데이터를 쿼리한다.
			if(!"".equals(hMap.get("authId"))){
				//관련정보 쿼리
				voAuth = this.menuAuthService.selectAuthByAuthId(hMap);
				
				if(voAuth!=null){
					voMenu.setAuth_nm(voAuth.getAuth_nm());
				}
				
				//관련정보 쿼리
				retMenu = this.menuAuthService.selectListMenuInfoByAuthId(hMap);
				
				if(retMenu==null){
					SessionMessage.setMessage(request,"Process.data.select.empty","/sys/mnu/aut/list.do?"+req.getChkParamStr(request,"page_no,sch_fld,sch_word"));
					return "cmm/message";
				}
 			}

			model.addAttribute("VoMenu", voMenu );
			model.addAttribute("totalCount", retMenu.size() );
			model.addAttribute("MenuList", retMenu );
			model.addAttribute("retParam", req.getChkParamStr(request,"page_no,sch_fld,sch_word") );
 
			return "sys/mnu/aut/view";
		}catch(Exception e){
			logger.error("=+=+=+=+=+=+=+=+=+=+=+=+=+= EXCEPTION START +=+=+=+=+=+=+=+=+=+=+=+=");
			logger.error("EXCEPTION 위치 : "+ request.getServletPath());
			logger.error("EXCEPTION 내용 : \n" + e.toString());
			logger.error("=+=+=+=+=+=+=+=+=+=+=+=+=+= EXCEPTION END   +=+=+=+=+=+=+=+=+=+=+=+=");
			SessionMessage.setMessage(request,"Common.fail.Exception","/sys/mnu/aut/list.do?"+req.getChkParamStr(request,"page_no,sch_fld,sch_word"));
			return "cmm/message";
		}finally{
			logger.debug("=========================== DEBUG START ============================");
			logger.debug("DEBUG 위치 : "+ request.getServletPath() + sbDebugLog.toString());
			logger.debug("=========================== DEBUG END    ===========================");
			sbDebugLog.setLength(0);
		}
	}
	
	/**
	 * 메뉴-권한 등록
	 * 
	 * @param Model model
	 * @param HttpServletRequest  request
	 * @return void
	 */
	@RequestMapping(value="/sys/mnu/aut/regist.do")
	public String sysMnuAutRegist(ModelMap model, HttpServletRequest request) throws Exception {
		sbDebugLog.setLength(0);
		//파라미터 설정
		ReqUtils req = new ReqUtils(request);

		try{
			//검색어 처리
			HashMap<String, String> hMap = new HashMap<String,String>();
			hMap.put("authId", StringUtils.getNull(req.getString("auth_id")));

			//리턴 정보 세팅
			VoMenu voMenu = new VoMenu();
			
			voMenu.setMode("I");
			voMenu.setAuth_id(hMap.get("authId"));
			List<VoMenu> retMenu = null;
			List<VoMenu> retAuth = null;

			VoAuth voAuth = new VoAuth();
			
			//auth_id 가 넘어올 경우 수정MODE 로 변경하고, 관련 데이터를 쿼리한다.
			if(!"".equals(hMap.get("authId"))){
				//관련정보 쿼리
				voAuth = this.menuAuthService.selectAuthByAuthId(hMap);
				
				if(voAuth!=null){
					voMenu.setAuth_nm(voAuth.getAuth_nm());
				}
				//관련정보 쿼리
				retMenu = this.menuAuthService.selectAllListMenuInfo(hMap);
				
				if(retMenu==null){
					SessionMessage.setMessage(request,"Process.data.select.empty","/sys/mnu/aut/list.do?"+req.getChkParamStr(request,"page_no,sch_fld,sch_word"));
					return "cmm/message";
				}

				retAuth = this.menuAuthService.selectListMenuAuth(hMap);
				
				voMenu.setMode("U");
 			}

			sbDebugLog.append("\n DEBUG AllParameter : [ " + req.getAllParamStr(request) + " ]");

			model.addAttribute("VoMenu", voMenu );
			model.addAttribute("totalCount", retMenu.size() );
			model.addAttribute("MenuList", retMenu );
			model.addAttribute("AuthList", retAuth );
			model.addAttribute("retParam", req.getChkParamStr(request,"page_no,sch_fld,sch_word") );
  
			return "sys/mnu/aut/regist";
		}catch(Exception e){
			logger.error("=+=+=+=+=+=+=+=+=+=+=+=+=+= EXCEPTION START +=+=+=+=+=+=+=+=+=+=+=+=");
			logger.error("EXCEPTION 위치 : "+ request.getServletPath());
			logger.error("EXCEPTION 내용 : \n" + e.toString());
			logger.error("=+=+=+=+=+=+=+=+=+=+=+=+=+= EXCEPTION END   +=+=+=+=+=+=+=+=+=+=+=+=");

			SessionMessage.setMessage(request,"Common.fail.Exception","/sys/mnu/aut/list.do?"+req.getChkParamStr(request,"page_no,sch_fld,sch_word"));
			return "cmm/message";
		}finally{
			logger.debug("=========================== DEBUG START ============================");
			logger.debug("DEBUG 위치 : "+ request.getServletPath() + sbDebugLog.toString());
			logger.debug("=========================== DEBUG END    ===========================");
			sbDebugLog.setLength(0);
		}
	}
	
	/**
	 * 메뉴-권한 등록 처리
	 * 
	 * @param Model model
	 * @param HttpServletRequest  request
	 * @return void
	 */
	@RequestMapping(value="/sys/mnu/aut/registProc.do", method=RequestMethod.POST)
	public String sysMnuAutRegistProc(ModelMap model, HttpServletRequest request) throws Exception {
		sbDebugLog.setLength(0);
		
		//파라미터 설정
		ReqUtils req = new ReqUtils(request);
		
		try{
			HashMap<String, String> hMap = new HashMap<String,String>();
			hMap.put("mode", StringUtils.getNull(req.getString("mode")));
			hMap.put("authId", StringUtils.getNull(req.getString("auth_id")));

			sbDebugLog.append("\n DEBUG mode : "+ hMap.get("mode"));
			
			String[] arrMenuAuth = request.getParameterValues("menu_auth");
			
			sbDebugLog.append("\n DEBUG arrMenuAuth : "+ arrMenuAuth.length);
			
			//mode 파라미터에 따른 분기처리
			if("U".equals(hMap.get("mode"))){//수정
				this.menuAuthService.deleteMenuAuthByAuthId(hMap);

				if(arrMenuAuth!=null){
					for(int i=0;i<arrMenuAuth.length;i++){
						hMap.put("menuId", arrMenuAuth[i]);
						this.menuAuthService.insertMenuAuth(hMap);
					}
				}
				SessionMessage.setMessage(request,"Process.data.success.update","/sys/mnu/aut/list.do?"+req.getChkParamStr(request,"page_no,sch_fld,sch_word"),"parent");
			}else{
				SessionMessage.setMessage(request,"Check.field.hidden.empty","/sys/mnu/aut/list.do?"+req.getChkParamStr(request,"page_no,sch_fld,sch_word"),"parent");
			}

			return "cmm/message";
		}catch(Exception e){
			logger.error("=+=+=+=+=+=+=+=+=+=+=+=+=+= EXCEPTION START +=+=+=+=+=+=+=+=+=+=+=+=");
			logger.error("EXCEPTION 위치 : "+ request.getServletPath());
			logger.error("EXCEPTION 내용 : \n" + e.toString());
			logger.error("=+=+=+=+=+=+=+=+=+=+=+=+=+= EXCEPTION END   +=+=+=+=+=+=+=+=+=+=+=+=");

			SessionMessage.setMessage(request,"Common.fail.Exception","/sys/mnu/aut/regist.do?"+req.getChkParamStr(request,"mode,page_no,app_id,sch_fld,sch_word"),"parent");
			return "cmm/message";
		}finally{
			logger.debug("=========================== DEBUG START ============================");
			logger.debug("DEBUG 위치 : "+ request.getServletPath() + sbDebugLog.toString());
			logger.debug("=========================== DEBUG END    ===========================");
			sbDebugLog.setLength(0);
		}
	}
}