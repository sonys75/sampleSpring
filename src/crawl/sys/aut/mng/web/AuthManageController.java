package crawl.sys.aut.mng.web;

import java.util.Arrays;
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
import crawl.cmm.web.ListPaging;
import crawl.sys.aut.mng.service.AuthManageService;
import crawl.vo.VoAuth;
import crawl.vo.VoCommon;
import crawl.vo.VoUserInfo;
import resources.com.service.SessionMessage;
import resources.com.util.ReqUtils;
import resources.com.util.StringUtils;

/**
 * AuthManageController
 */
@Controller
public class AuthManageController {
 	@Autowired
 	AuthManageService authManageService;
 
 	@Autowired
 	CodeService codeService;
 	
	public Logger logger = Logger.getLogger(this.getClass());
	public StringBuffer sbDebugLog = new StringBuffer();

	/**
	 * 권한 목록
	 * 
	 * @param Model model
	 * @param HttpServletRequest  request
	 * @return void
	 */
	@RequestMapping(value="/sys/aut/mng/list.do")
	public String sysAutMngList(ModelMap model, HttpServletRequest request) throws Exception {
		sbDebugLog.setLength(0);
		try{
			//파라미터 설정
			ReqUtils req = new ReqUtils(request);

			String sSchFld = StringUtils.getNull(req.getString("sch_fld"));
			String[] aSchFld = {"AUTH_NM", "AUTH_ID"};
			if(!Arrays.asList(aSchFld).contains(sSchFld)){
				sSchFld = aSchFld[0];
			}
			
			//검색어 처리
			VoCommon voCommon = new VoCommon();
			voCommon.setSch_fld(sSchFld);
			voCommon.setSch_word(req.getString("sch_word"));
			
			HashMap<String, String> hMap = new HashMap<String,String>();
			hMap.put("sch_fld", voCommon.getSch_fld());
			hMap.put("sch_word", voCommon.getSch_word());

			//페이징 처리 
			ListPaging listPage = new ListPaging(req.getInt("page_no", 1));
			listPage.setMovePage(request.getServletPath());
			listPage.setTotalCount(this.authManageService.selectCntAuth(hMap));
			sbDebugLog.append("\n DEBUG Parameter : listPage.getTotalCount() = " + listPage.getTotalCount());
			listPage.setCommonPara(req.getPageParamStr(request));
			
			sbDebugLog.append("\n DEBUG Parameter : listPage.getStartLine() = " + listPage.getStartLine());
			sbDebugLog.append("\n DEBUG Parameter : listPage.getEndLine() = " + listPage.getEndLine());
			hMap.put("startRow", Integer.toString(listPage.getStartLine()));
			hMap.put("endRow", Integer.toString(listPage.getEndLine()));
			
			voCommon.setListPaging(listPage);	

			List<VoAuth> authList = this.authManageService.selectListAuth(hMap);
			
			//검색정보 세팅
			model.addAttribute("VoComm", voCommon);
			model.addAttribute("list", authList);
			model.addAttribute("listPage", listPage);
			
			return "sys/aut/mng/list";
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
	 * 권한 조회
	 * 
	 * @param Model model
	 * @param HttpServletRequest  request
	 * @return void
	 */
	@RequestMapping(value="/sys/aut/mng/view.do")
	public String sysAutMngView(ModelMap model, HttpServletRequest request) throws Exception {
		sbDebugLog.setLength(0);
		//파라미터 설정
		ReqUtils req = new ReqUtils(request);
		
		try{
			//검색어 처리
			HashMap<String, String> hMap = new HashMap<String,String>();
			hMap.put("authNo", StringUtils.getNull(req.getString("auth_no")));

			//리턴 정보 세팅
			VoAuth retAuth = new VoAuth();
 
			//auth_no 가 넘어올 경우 관련 데이터를 쿼리한다.
			if(!"".equals(hMap.get("authNo"))){
				//관련정보 쿼리
				retAuth = this.authManageService.selectAuthByAuthNo(hMap);
				
				if(retAuth==null){
					SessionMessage.setMessage(request,"Process.data.select.empty","/sys/aut/mng/list.do?"+req.getChkParamStr(request,"page_no,sch_fld,sch_word"));
					return "cmm/message";
				}
 			}
			
			//parameter 처리
			retAuth.setSch_fld(StringUtils.getNull(req.getString("sch_fld")));
			retAuth.setSch_word(StringUtils.getNull(req.getString("sch_word")));
			retAuth.setPage_no(StringUtils.getNull(req.getString("page_no")));
			sbDebugLog.append("\n DEBUG AllParameter : [ " + req.getAllParamStr(request) + " ]");

			model.addAttribute("VoAuth", retAuth );
			model.addAttribute("retParam", req.getChkParamStr(request,"page_no,sch_fld,sch_word") );
 
			return "sys/aut/mng/view";
		}catch(Exception e){
			logger.error("=+=+=+=+=+=+=+=+=+=+=+=+=+= EXCEPTION START +=+=+=+=+=+=+=+=+=+=+=+=");
			logger.error("EXCEPTION 위치 : "+ request.getServletPath());
			logger.error("EXCEPTION 내용 : \n" + e.toString());
			logger.error("=+=+=+=+=+=+=+=+=+=+=+=+=+= EXCEPTION END   +=+=+=+=+=+=+=+=+=+=+=+=");
			SessionMessage.setMessage(request,"Common.fail.Exception","/sys/aut/mng/list.do?"+req.getChkParamStr(request,"page_no,sch_fld,sch_word"));
			return "cmm/message";
		}finally{
			logger.debug("=========================== DEBUG START ============================");
			logger.debug("DEBUG 위치 : "+ request.getServletPath() + sbDebugLog.toString());
			logger.debug("=========================== DEBUG END    ===========================");
			sbDebugLog.setLength(0);
		}
	}
	
	/**
	 * 권한 등록
	 * 
	 * @param Model model
	 * @param HttpServletRequest  request
	 * @return void
	 */
	@RequestMapping(value="/sys/aut/mng/regist.do")
	public String sysAutMngRegist(ModelMap model, HttpServletRequest request) throws Exception {
		sbDebugLog.setLength(0);
		//파라미터 설정
		ReqUtils req = new ReqUtils(request);

		try{
			//검색어 처리
			HashMap<String, String> hMap = new HashMap<String,String>();
			hMap.put("authNo", StringUtils.getNull(req.getString("auth_no")));

			//리턴 정보 세팅
			VoAuth retAuth = new VoAuth();
			int nUpAuthCnt = 0;
			retAuth.setMode("I");

			//app_id 가 넘어올 경우 수정MODE 로 변경하고, 관련 데이터를 쿼리한다.
			if(!"".equals(hMap.get("authNo"))){
				//관련정보 쿼리
				retAuth = this.authManageService.selectAuthByAuthNo(hMap);
				
				if(retAuth==null){
					SessionMessage.setMessage(request,"Process.data.select.empty","/sys/aut/mng/list.do?"+req.getChkParamStr(request,"page_no,sch_fld,sch_word"));
					return "cmm/message";
				}
				nUpAuthCnt = this.authManageService.selectCntAuthByUpAuthNo(hMap);
				retAuth.setMode("U");
 			}
			
			//parameter 처리
			retAuth.setSch_fld(StringUtils.getNull(req.getString("sch_fld")));
			retAuth.setSch_word(StringUtils.getNull(req.getString("sch_word")));
			retAuth.setPage_no(StringUtils.getNull(req.getString("page_no")));
			sbDebugLog.append("\n DEBUG AllParameter : [ " + req.getAllParamStr(request) + " ]");

			model.addAttribute("VoAuth", retAuth );
			model.addAttribute("UpAuthCnt", nUpAuthCnt );
			model.addAttribute("retParam", req.getChkParamStr(request,"page_no,sch_fld,sch_word") );
  
			return "sys/aut/mng/regist";
		}catch(Exception e){
			logger.error("=+=+=+=+=+=+=+=+=+=+=+=+=+= EXCEPTION START +=+=+=+=+=+=+=+=+=+=+=+=");
			logger.error("EXCEPTION 위치 : "+ request.getServletPath());
			logger.error("EXCEPTION 내용 : \n" + e.toString());
			logger.error("=+=+=+=+=+=+=+=+=+=+=+=+=+= EXCEPTION END   +=+=+=+=+=+=+=+=+=+=+=+=");
			/*			
			SessionMessage.setMessage(request,"Common.fail.Exception","/sys/aut/mng/list.do");
			throw new Exception(e);
			*/
			SessionMessage.setMessage(request,"Common.fail.Exception","/sys/aut/mng/list.do?"+req.getChkParamStr(request,"page_no,sch_fld,sch_word"));
			return "cmm/message";
		}finally{
			logger.debug("=========================== DEBUG START ============================");
			logger.debug("DEBUG 위치 : "+ request.getServletPath() + sbDebugLog.toString());
			logger.debug("=========================== DEBUG END    ===========================");
			sbDebugLog.setLength(0);
		}
	}
	
	/**
	 * 권한 등록 처리
	 * 
	 * @param Model model
	 * @param HttpServletRequest  request
	 * @return void
	 */
	@RequestMapping(value="/sys/aut/mng/registProc.do", method=RequestMethod.POST)
	public String sysAutMngRegistProc(ModelMap model, HttpServletRequest request) throws Exception {
		sbDebugLog.setLength(0);
		
		//파라미터 설정
		ReqUtils req = new ReqUtils(request);
		
		try{
			//검색어 처리
			VoCommon voCommon = new VoCommon();
			voCommon.setSch_fld(req.getString("sch_fld"));
			voCommon.setSch_word(req.getString("sch_word"));
			
			HashMap<String, String> hMap = new HashMap<String,String>();
			hMap.put("mode", StringUtils.getNull(req.getString("mode")));
			hMap.put("upAuthNo", StringUtils.getNull(req.getString("up_auth_no")));
			hMap.put("upAuthId", StringUtils.getNull(req.getString("up_auth_id")));
			hMap.put("authNo", StringUtils.getNull(req.getString("auth_no")));
			hMap.put("authId", StringUtils.getNull(req.getString("auth_id")));
			hMap.put("authNm", StringUtils.getNull(req.getString("auth_nm")));

			sbDebugLog.append("\n DEBUG mode : "+ hMap.get("mode"));
			
			//mode 파라미터에 따른 분기처리
			if("I".equals(hMap.get("mode"))){//추가
				int nAuthCnt = this.authManageService.selectCntAuthByAuthId(hMap);
				if(nAuthCnt>0){
					SessionMessage.setMessage(request,"User.id.check.unique.no","/sys/aut/mng/regist.do?"+req.getChkParamStr(request,"mode,page_no,sch_fld,sch_word"),"parent");
				}else{
					String getAuth_no = this.authManageService.insertAuth(hMap);
					//up_auth_id > AUTHENTICATED 삭제 후 up_auth_no > auth_no , auth_no > AUTHENTICATED 추가
					hMap.put("authNo", getAuth_no);
					this.authManageService.updateAuthLvl(hMap);
					SessionMessage.setMessage(request,"Process.data.success.create","/sys/aut/mng/list.do?"+req.getChkParamStr(request,"page_no,sch_fld,sch_word"),"parent");
				}
			}else if("U".equals(hMap.get("mode"))){//수정
				//int nAuthCnt = this.authManageService.selectCntAuthByAuthId(hMap);
				//int nUpAuthCnt = this.authManageService.selectCntAuthByUpAuthNo(hMap);
				//if(nAuthCnt>0){
				//	SessionMessage.setMessage(request,"User.id.check.unique.no","/sys/aut/mng/regist.do?"+req.getChkParamStr(request,"auth_no,page_no,sch_fld,sch_word"),"parent");
				//}else{
					this.authManageService.updateAuthByAuthNo(hMap);
					//auth_no > AUTHENTICATED, up_auth_no > auth_no 삭제 후 up_auth_no > auth_no , auth_no > AUTHENTICATED 추가
					this.authManageService.updateAuthLvl(hMap);
					SessionMessage.setMessage(request,"Process.data.success.update","/sys/aut/mng/list.do?"+req.getChkParamStr(request,"page_no,sch_fld,sch_word"),"parent");
				//}
			}else if("D".equals(hMap.get("mode"))){//삭제
				int nUpAuthCnt = this.authManageService.selectCntAuthByUpAuthNo(hMap);
				if(nUpAuthCnt>0){
					SessionMessage.setMessage(request,"Process.data.fail.exist.auth.upauth.used","/sys/aut/mng/view.do?"+req.getChkParamStr(request,"auth_no,page_no,sch_fld,sch_word"),"parent");
				}else{
					this.authManageService.deleteAuthByAuthNo(hMap);
					//auth_no > AUTHENTICATED, up_auth_no > auth_no 삭제 후 up_auth_no > AUTHENTICATED 추가
					this.authManageService.updateAuthLvl(hMap);
					SessionMessage.setMessage(request,"Process.data.success.delete","/sys/aut/mng/list.do?"+req.getChkParamStr(request,"sch_fld,sch_word"),"parent");
				}
			}else{
				SessionMessage.setMessage(request,"Check.field.hidden.empty","/sys/aut/mng/list.do?"+req.getChkParamStr(request,"page_no,sch_fld,sch_word"),"parent");
			}

			return "cmm/message";
		}catch(Exception e){
			logger.error("=+=+=+=+=+=+=+=+=+=+=+=+=+= EXCEPTION START +=+=+=+=+=+=+=+=+=+=+=+=");
			logger.error("EXCEPTION 위치 : "+ request.getServletPath());
			logger.error("EXCEPTION 내용 : \n" + e.toString());
			logger.error("=+=+=+=+=+=+=+=+=+=+=+=+=+= EXCEPTION END   +=+=+=+=+=+=+=+=+=+=+=+=");

			SessionMessage.setMessage(request,"Common.fail.Exception","/sys/aut/mng/regist.do?"+req.getChkParamStr(request,"mode,page_no,app_id,sch_fld,sch_word"),"parent");
			return "cmm/message";
		}finally{
			logger.debug("=========================== DEBUG START ============================");
			logger.debug("DEBUG 위치 : "+ request.getServletPath() + sbDebugLog.toString());
			logger.debug("=========================== DEBUG END    ===========================");
			sbDebugLog.setLength(0);
		}
	}

	/**
	 * 권한 검색
	 * 
	 * @param Model model
	 * @param HttpServletRequest  request
	 * @return void
	 */
	@RequestMapping(value="/sys/aut/mng/auth.do")
	public String sysAutMngAuth(ModelMap model, HttpServletRequest request) throws Exception {
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

			return "sys/aut/mng/auth";
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