package crawl.sys.mnu.mng.web;

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

import crawl.sys.mnu.mng.service.MenuManageService;
import crawl.vo.VoMenu;
import crawl.vo.VoUserInfo;
import resources.com.service.SessionMessage;
import resources.com.util.ReqUtils;
import resources.com.util.StringUtils;

/**
 * MenuManageController
 */
@Controller
public class MenuManageController {

	@Autowired
	MenuManageService menuManageService;
 	
	public Logger logger = Logger.getLogger(this.getClass());
	public StringBuffer sbDebugLog = new StringBuffer();

	/**
	 * 메뉴 목록
	 * 
	 * @param Model model
	 * @param HttpServletRequest  request
	 * @return void
	 */
	@RequestMapping(value="/sys/mnu/mng/list.do")
	public String sysMnuMngList(ModelMap model, HttpServletRequest request) throws Exception {
		sbDebugLog.setLength(0);
		try{

			List<VoMenu> menuList = this.menuManageService.selectListMenu(new HashMap<String,String>());
			
			//검색정보 세팅
			model.addAttribute("list", menuList);

			return "sys/mnu/mng/list";
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
	 * 메뉴 조회
	 * 
	 * @param Model model
	 * @param HttpServletRequest  request
	 * @return void
	 */
	@RequestMapping(value="/sys/mnu/mng/view.do")
	public String sysMnuMngView(ModelMap model, HttpServletRequest request) throws Exception {
		sbDebugLog.setLength(0);
		//파라미터 설정
		ReqUtils req = new ReqUtils(request);
		
		try{
			//검색어 처리
			HashMap<String, String> hMap = new HashMap<String,String>();
			hMap.put("menuId", StringUtils.getNull(req.getString("menu_id")));

			//리턴 정보 세팅
			VoMenu retMenu = new VoMenu();

			//menu_id 가 넘어올 경우 관련 데이터를 쿼리한다.
			if(!"".equals(hMap.get("menuId"))){
				//관련정보 쿼리
				retMenu = this.menuManageService.selectMenuInfoByMenuId(hMap);
				
				if(retMenu==null){
					SessionMessage.setMessage(request,"Process.data.select.empty","/sys/mnu/mng/list.do?"+req.getChkParamStr(request,"page_no,sch_fld,sch_word"));
					return "cmm/message";
				}
 			}

			model.addAttribute("VoMenu", retMenu );

			model.addAttribute("retParam", req.getChkParamStr(request,"page_no,sch_fld,sch_word") );
 
			return "sys/mnu/mng/view";
		}catch(Exception e){
			logger.error("=+=+=+=+=+=+=+=+=+=+=+=+=+= EXCEPTION START +=+=+=+=+=+=+=+=+=+=+=+=");
			logger.error("EXCEPTION 위치 : "+ request.getServletPath());
			logger.error("EXCEPTION 내용 : \n" + e.toString());
			logger.error("=+=+=+=+=+=+=+=+=+=+=+=+=+= EXCEPTION END   +=+=+=+=+=+=+=+=+=+=+=+=");
			SessionMessage.setMessage(request,"Common.fail.Exception","/sys/mnu/mng/list.do?"+req.getChkParamStr(request,"page_no,sch_fld,sch_word"));
			return "cmm/message";
		}finally{
			logger.debug("=========================== DEBUG START ============================");
			logger.debug("DEBUG 위치 : "+ request.getServletPath() + sbDebugLog.toString());
			logger.debug("=========================== DEBUG END    ===========================");
			sbDebugLog.setLength(0);
		}
	}
	
	/**
	 * 메뉴 등록
	 * 
	 * @param Model model
	 * @param HttpServletRequest  request
	 * @return void
	 */
	@RequestMapping(value="/sys/mnu/mng/regist.do")
	public String sysMnuMngRegist(ModelMap model, HttpServletRequest request) throws Exception {
		sbDebugLog.setLength(0);
		//파라미터 설정
		ReqUtils req = new ReqUtils(request);

		try{
			//검색어 처리
			HashMap<String, String> hMap = new HashMap<String,String>();
			hMap.put("menuId", StringUtils.getNull(req.getString("menu_id")));

			//리턴 정보 세팅
			VoMenu retMenu = new VoMenu();
			retMenu.setMode("I");
			int nCntMenu = 0;
			//menu_id 가 넘어올 경우 수정MODE 로 변경하고, 관련 데이터를 쿼리한다.
			if(!"".equals(hMap.get("menuId"))){
				//관련정보 쿼리
				retMenu = this.menuManageService.selectMenuInfoByMenuId(hMap);
				
				if(retMenu==null){
					SessionMessage.setMessage(request,"Process.data.select.empty","/sys/mnu/mng/list.do?"+req.getChkParamStr(request,"page_no,sch_fld,sch_word"));
					return "cmm/message";
				}

				nCntMenu = this.menuManageService.selectCntLowerMenuInfoByMenuId(hMap);
				retMenu.setMode("U");
 			}
			
			//parameter 처리
			sbDebugLog.append("\n DEBUG AllParameter : [ " + req.getAllParamStr(request) + " ]");

			model.addAttribute("VoMenu", retMenu );
			model.addAttribute("cntMenu", nCntMenu );
			model.addAttribute("retParam", req.getChkParamStr(request,"page_no,sch_fld,sch_word") );
  
			return "sys/mnu/mng/regist";
		}catch(Exception e){
			logger.error("=+=+=+=+=+=+=+=+=+=+=+=+=+= EXCEPTION START +=+=+=+=+=+=+=+=+=+=+=+=");
			logger.error("EXCEPTION 위치 : "+ request.getServletPath());
			logger.error("EXCEPTION 내용 : \n" + e.toString());
			logger.error("=+=+=+=+=+=+=+=+=+=+=+=+=+= EXCEPTION END   +=+=+=+=+=+=+=+=+=+=+=+=");
			/*			
			SessionMessage.setMessage(request,"Common.fail.Exception","/sys/mnu/mng/list.do");
			throw new Exception(e);
			*/
			SessionMessage.setMessage(request,"Common.fail.Exception","/sys/mnu/mng/list.do?"+req.getChkParamStr(request,"page_no,sch_fld,sch_word"));
			return "cmm/message";
		}finally{
			logger.debug("=========================== DEBUG START ============================");
			logger.debug("DEBUG 위치 : "+ request.getServletPath() + sbDebugLog.toString());
			logger.debug("=========================== DEBUG END    ===========================");
			sbDebugLog.setLength(0);
		}
	}
	
	/**
	 * 메뉴 등록 처리
	 * 
	 * @param Model model
	 * @param HttpServletRequest  request
	 * @return void
	 */
	@RequestMapping(value="/sys/mnu/mng/registProc.do", method=RequestMethod.POST)
	public String sysMnuMngRegistProc(ModelMap model, HttpServletRequest request) throws Exception {
		sbDebugLog.setLength(0);
		
		//파라미터 설정
		ReqUtils req = new ReqUtils(request);
		
		try{
			HttpSession session = request.getSession(false);
			VoUserInfo voUserInfo = (VoUserInfo)session.getAttribute("userInfoVO");

			HashMap<String, String> hMap = new HashMap<String,String>();
			hMap.put("mode", StringUtils.getNull(req.getString("mode")));
			hMap.put("menuId", StringUtils.getNull(req.getString("menu_id")));
			hMap.put("menuNm", StringUtils.getNull(req.getString("menu_nm")));
			hMap.put("menuUrl", StringUtils.getNull(req.getString("menu_url")));
			hMap.put("upMenuId", StringUtils.getNull(req.getString("up_menu_id"),"0"));
			hMap.put("upMenuNm", StringUtils.getNull(req.getString("up_menu_nm")));
			hMap.put("userIp", request.getRemoteAddr());
			hMap.put("userId", voUserInfo.getUser_id());

			sbDebugLog.append("\n DEBUG mode : "+ hMap.get("mode"));

			//mode 파라미터에 따른 분기처리
			if("I".equals(hMap.get("mode"))){//추가
				this.menuManageService.insertMenuInfo(hMap);

				SessionMessage.setMessage(request,"Process.data.success.create","/sys/mnu/mng/list.do?"+req.getChkParamStr(request,"page_no,sch_fld,sch_word"),"parent");
			}else if("U".equals(hMap.get("mode"))){//수정
				this.menuManageService.updateMenuInfoByMenuId(hMap);
				SessionMessage.setMessage(request,"Process.data.success.update","/sys/mnu/mng/list.do?"+req.getChkParamStr(request,"page_no,sch_fld,sch_word"),"parent");
			}else if("D".equals(hMap.get("mode"))){//삭제
				//메뉴-권한 삭제
				this.menuManageService.deleteMenuAuthByMenuId(hMap);
				
				//메뉴 삭제
				this.menuManageService.deleteMenuInfoByMenuId(hMap);
				SessionMessage.setMessage(request,"Process.data.success.delete","/sys/mnu/mng/list.do?"+req.getChkParamStr(request,"sch_fld,sch_word"),"parent");
			}else{
				SessionMessage.setMessage(request,"Check.field.hidden.empty","/sys/mnu/mng/list.do?"+req.getChkParamStr(request,"page_no,sch_fld,sch_word"),"parent");
			}

			return "cmm/message";
		}catch(Exception e){
			logger.error("=+=+=+=+=+=+=+=+=+=+=+=+=+= EXCEPTION START +=+=+=+=+=+=+=+=+=+=+=+=");
			logger.error("EXCEPTION 위치 : "+ request.getServletPath());
			logger.error("EXCEPTION 내용 : \n" + e.toString());
			logger.error("=+=+=+=+=+=+=+=+=+=+=+=+=+= EXCEPTION END   +=+=+=+=+=+=+=+=+=+=+=+=");

			SessionMessage.setMessage(request,"Common.fail.Exception","/sys/mnu/mng/regist.do?"+req.getChkParamStr(request,"mode,page_no,app_id,sch_fld,sch_word"),"parent");
			return "cmm/message";
		}finally{
			logger.debug("=========================== DEBUG START ============================");
			logger.debug("DEBUG 위치 : "+ request.getServletPath() + sbDebugLog.toString());
			logger.debug("=========================== DEBUG END    ===========================");
			sbDebugLog.setLength(0);
		}
	}
}