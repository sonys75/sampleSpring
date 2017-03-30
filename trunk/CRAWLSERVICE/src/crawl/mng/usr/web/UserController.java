package crawl.mng.usr.web;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;

import crawl.cmm.cde.service.CodeService;
import crawl.cmm.web.ListPaging;
import crawl.mng.usr.service.UserService;
import crawl.vo.VoAuth;
import crawl.vo.VoCommon;
import crawl.vo.VoUserInfo;
import resources.com.service.ReadProperties;
import resources.com.service.SessionMessage;
import resources.com.util.CryptUtils;
import resources.com.util.ReqUtils;
import resources.com.util.StringUtils;

/**
 * UserController
 */
@Controller
public class UserController {
 	
	@Autowired
 	UserService userService;
 
	@Autowired
 	CodeService codeService;
 	
	public Logger logger = Logger.getLogger(this.getClass());
	public StringBuffer sbDebugLog = new StringBuffer();

	//로고파일 업로드 디렉토리
	public String LogoFileUpLoadPath 	= ReadProperties.getProperty("FileUpLoad.corp_logo.path");
	public String LogoFileRealSavePath	= "";
	public Long LogoFileMaxSize  = Long.parseLong(ReadProperties.getProperty("FileUpLoad.corp_logo.maxsize")) * Long.parseLong(ReadProperties.getProperty("FileUpLoad.default.unit"));
	
	/**
	 * 사용자 목록
	 * 
	 * @param Model model
	 * @param HttpServletRequest  request
	 * @return void
	 */
	@RequestMapping(value="/mng/usr/list.do")
	public String mngCrpList(ModelMap model, HttpServletRequest request) throws Exception {
		sbDebugLog.setLength(0);
		try{
			
			//파라미터 설정
			ReqUtils req = new ReqUtils(request);
			
			HttpSession session = request.getSession(false);
			VoUserInfo voUserInfo = (VoUserInfo)session.getAttribute("userInfoVO");
			
			//sbDebugLog.append("\n DEBUG AllParameter : [ " + req.getAllParamStr(request) + " ]");
			
			String sSchFld = StringUtils.getNull(req.getString("sch_fld"));
			String[] aSchFld = {"USER_NM", "USER_ID"};
			if(!Arrays.asList(aSchFld).contains(sSchFld)){
				sSchFld = aSchFld[0];
			}
			
			//검색어 처리
			VoCommon voCommon = new VoCommon();
			voCommon.setSch_fld(sSchFld);
			voCommon.setSch_word(req.getString("sch_word"));
			
			HashMap<String, String> hMap = new HashMap<String,String>();
			hMap.put("userId", voUserInfo.getUser_id());
			hMap.put("corpId", voUserInfo.getCorp_id());
			hMap.put("userAuth", voUserInfo.getAuth_id());
			hMap.put("sch_fld", voCommon.getSch_fld());
			hMap.put("sch_word", voCommon.getSch_word());
			
			//sbDebugLog.append("\n DEBUG userAuth : " + hMap.get("userAuth") );
			
			//페이징 처리 
			ListPaging listPage = new ListPaging(req.getInt("page_no", 1));
			listPage.setMovePage(request.getServletPath());
			listPage.setTotalCount(this.userService.selectCntUserInfo(hMap));
			listPage.setCommonPara(req.getPageParamStr(request));
			/*
			sbDebugLog.append("\n DEBUG Parameter : listPage.getTotalCount() = " + listPage.getTotalCount());
			sbDebugLog.append("\n DEBUG Parameter : listPage.getStartLine() = " + listPage.getStartLine());
			sbDebugLog.append("\n DEBUG Parameter : listPage.getEndLine() = " + listPage.getEndLine());
			*/
			hMap.put("startRow", Integer.toString(listPage.getStartLine()));
			hMap.put("endRow", Integer.toString(listPage.getEndLine()));
			
			voCommon.setListPaging(listPage);	

			List<VoUserInfo> userList = this.userService.selectListUserInfo(hMap);
			
			//검색정보 세팅
			model.addAttribute("VoComm", voCommon);
			model.addAttribute("list", userList);
			model.addAttribute("listPage", listPage);
			
			return "mng/usr/list";
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
	 * 사용자 조회
	 * 
	 * @param Model model
	 * @param HttpServletRequest  request
	 * @return void
	 */
	@RequestMapping(value="/mng/usr/view.do")
	public String mngCrpView(ModelMap model, HttpServletRequest request) throws Exception {
		sbDebugLog.setLength(0);
		try{
			
			//파라미터 설정
			ReqUtils req = new ReqUtils(request);
			
			HttpSession session = request.getSession(false);
			VoUserInfo voUserInfo = (VoUserInfo)session.getAttribute("userInfoVO");
			
			//검색어 처리
			VoCommon voCommon = new VoCommon();
			voCommon.setSch_fld(req.getString("sch_fld"));
			voCommon.setSch_word(req.getString("sch_word"));
			
			HashMap<String, String> hMap = new HashMap<String,String>();
			hMap.put("userId", StringUtils.getNull(req.getString("user_id")));
			hMap.put("corpId", voUserInfo.getCorp_id());
			hMap.put("userAuth", voUserInfo.getAuth_id());
			hMap.put("sch_fld", voCommon.getSch_fld());
			hMap.put("sch_word", voCommon.getSch_word());

			sbDebugLog.append("\n DEBUG userId : "+ hMap.get("userId"));
			sbDebugLog.append("\n DEBUG corpId : "+ hMap.get("corpId"));
			
			sbDebugLog.append("\n DEBUG getAuth_id : "+ voUserInfo.getAuth_id());
			sbDebugLog.append("\n DEBUG getUser_auth : "+ voUserInfo.getUser_auth());
			sbDebugLog.append("\n DEBUG getUser_authorities : "+ voUserInfo.getUser_authorities());
			
			//리턴 정보 세팅
			VoUserInfo retUserInfo = new VoUserInfo();

			//userId 가 넘어올 경우 수정MODE 로 변경하고, 관련 데이터를 쿼리한다.
			if(!"".equals(hMap.get("userId"))){
				//관련정보 쿼리
				retUserInfo = this.userService.selectUserInfoByUserId(hMap);
				
				if(retUserInfo==null){
					
					SessionMessage.setMessage(request,"Process.data.select.empty","/mng/usr/list.do?"+req.getChkParamStr(request,"page_no,sch_fld,sch_word"));
					return "mng/usr/list";	
				}

			}
			
			VoAuth voAuth = this.codeService.selectAuthNo(hMap);
			
			List<VoAuth> authList = new ArrayList(); 
					
			if(voAuth!=null){
				hMap.put("authNo", voAuth.getAuth_no());
				authList = this.codeService.selectAuthList(hMap);
			}

			boolean bChkAuth = false;
			
			for(int i=0;i<authList.size();i++){
				VoAuth chkAuth = authList.get(i);
				if(chkAuth.getAuth_id().equals(retUserInfo.getAuth_id())){
					bChkAuth = true;
					break;
				}
			}
			
			HashMap<String, String> hCodeMap = new HashMap<String,String>();
			hCodeMap.put("corpId", voUserInfo.getCorp_id());
 
			//parameter 처리
			retUserInfo.setSch_fld(StringUtils.getNull(req.getString("sch_fld")));
			retUserInfo.setSch_word(StringUtils.getNull(req.getString("sch_word")));
			retUserInfo.setPage_no(StringUtils.getNull(req.getString("page_no")));
			sbDebugLog.append("\n DEBUG AllParameter : [ " + req.getAllParamStr(request) + " ]");

			model.addAttribute("VoUserInfo", retUserInfo );
			model.addAttribute("retParam", req.getChkParamStr(request,"page_no,sch_fld,sch_word") );
 
			model.addAttribute("ChkAuth", bChkAuth);
			
			sbDebugLog.append("\n DEBUG ChkAuth : " + bChkAuth );

			return "mng/usr/view";
		}catch(Exception e){
			logger.error("=+=+=+=+=+=+=+=+=+=+=+=+=+= EXCEPTION START +=+=+=+=+=+=+=+=+=+=+=+=");
			logger.error("EXCEPTION 위치 : "+ request.getServletPath());
			logger.error("EXCEPTION 내용 : \n" + e.toString());
			logger.error("=+=+=+=+=+=+=+=+=+=+=+=+=+= EXCEPTION END   +=+=+=+=+=+=+=+=+=+=+=+=");
			SessionMessage.setMessage(request,"Common.fail.Exception","/mng/usr/list.do");
			return "cmm/message";
		}finally{
			logger.debug("=========================== DEBUG START ============================");
			logger.debug("DEBUG 위치 : "+ request.getServletPath() + sbDebugLog.toString());
			logger.debug("=========================== DEBUG END    ===========================");
			sbDebugLog.setLength(0);
		}
	}

	/**
	 * 사용자 등록
	 * 
	 * @param Model model
	 * @param HttpServletRequest  request
	 * @return void
	 */
	@RequestMapping(value="/mng/usr/regist.do")
	public String mngUsrRegist(ModelMap model, HttpServletRequest request) throws Exception {
		sbDebugLog.setLength(0);
		try{
			
			//파라미터 설정
			ReqUtils req = new ReqUtils(request);
			
			HttpSession session = request.getSession(false);
			VoUserInfo voUserInfo = (VoUserInfo)session.getAttribute("userInfoVO");
			
			//검색어 처리
			VoCommon voCommon = new VoCommon();
			voCommon.setSch_fld(req.getString("sch_fld"));
			voCommon.setSch_word(req.getString("sch_word"));
			
			HashMap<String, String> hMap = new HashMap<String,String>();
			hMap.put("userId", StringUtils.getNull(req.getString("user_id")));
			hMap.put("corpId", voUserInfo.getCorp_id());
			hMap.put("userAuth", voUserInfo.getAuth_id());
			hMap.put("sch_fld", voCommon.getSch_fld());
			hMap.put("sch_word", voCommon.getSch_word());

			sbDebugLog.append("\n DEBUG userId : "+ hMap.get("userId"));
			sbDebugLog.append("\n DEBUG corpId : "+ hMap.get("corpId"));

			boolean bChkAuth = true;
			
			//리턴 정보 세팅
			VoUserInfo retUserInfo = new VoUserInfo();
			retUserInfo.setMode("I");

			VoAuth voAuth = this.codeService.selectAuthNo(hMap);
			
			List<VoAuth> authList = new ArrayList(); 
					
			if(voAuth!=null){
				hMap.put("authNo", voAuth.getAuth_no());
				authList = this.codeService.selectAuthList(hMap);
			}
			
			//userId 가 넘어올 경우 수정MODE 로 변경하고, 관련 데이터를 쿼리한다.
			if(!"".equals(hMap.get("userId"))){
				//관련정보 쿼리
				retUserInfo = this.userService.selectUserInfoByUserId(hMap);
				
				if(retUserInfo==null){
					SessionMessage.setMessage(request,"Process.data.select.empty","/mng/usr/list.do?"+req.getChkParamStr(request,"page_no,sch_fld,sch_word"));
					return "cmm/message";
				}
				retUserInfo.setMode("U");
				
				bChkAuth = false;
				
				for(int i=0;i<authList.size();i++){
					VoAuth chkAuth = authList.get(i);
					if(chkAuth.getAuth_id().equals(retUserInfo.getAuth_id())){
						bChkAuth = true;
						break;
					}
				}
			}
			
			HashMap<String, String> hCodeMap = new HashMap<String,String>();
			hCodeMap.put("corpId", voUserInfo.getCorp_id());
			
			//parameter 처리
			retUserInfo.setSch_fld(StringUtils.getNull(req.getString("sch_fld")));
			retUserInfo.setSch_word(StringUtils.getNull(req.getString("sch_word")));
			retUserInfo.setPage_no(StringUtils.getNull(req.getString("page_no")));
			sbDebugLog.append("\n DEBUG AllParameter : [ " + req.getAllParamStr(request) + " ]");

			model.addAttribute("AuthList", authList );
			model.addAttribute("VoUserInfo", retUserInfo );
			model.addAttribute("retParam", req.getChkParamStr(request,"page_no,sch_fld,sch_word") );
			model.addAttribute("ChkAuth", bChkAuth);
			
			return "mng/usr/regist";
		}catch(Exception e){
			logger.error("=+=+=+=+=+=+=+=+=+=+=+=+=+= EXCEPTION START +=+=+=+=+=+=+=+=+=+=+=+=");
			logger.error("EXCEPTION 위치 : "+ request.getServletPath());
			logger.error("EXCEPTION 내용 : \n" + e.toString());
			logger.error("=+=+=+=+=+=+=+=+=+=+=+=+=+= EXCEPTION END   +=+=+=+=+=+=+=+=+=+=+=+=");
			SessionMessage.setMessage(request,"Common.fail.Exception","/mng/usr/list.do");
			return "cmm/message";
		}finally{
			logger.debug("=========================== DEBUG START ============================");
			logger.debug("DEBUG 위치 : "+ request.getServletPath() + sbDebugLog.toString());
			logger.debug("=========================== DEBUG END    ===========================");
			sbDebugLog.setLength(0);
		}
	}
	
	/**
	 * 아이디 중복 체크
	 * 
	 * @param Model model
	 * @param HttpServletRequest  request
	 * @return void
	 */
    @RequestMapping({"/mng/usr/idDupCheck.do"})
    public String mngUsrIdDupCheck(Model model, HttpServletRequest request) {
		logger = Logger.getLogger(this.getClass());
		sbDebugLog.setLength(0);

		try{		
			//파라미터 설정
			ReqUtils req = new ReqUtils(request);
			
			HashMap<String, String> hMap = new HashMap<String,String>();
			hMap.put("userId", StringUtils.getNull(req.getString("user_id")));
			
			sbDebugLog.append("\n DEBUG AllParameter : [ " + req.getAllParamStr(request) + " ]");
			
			VoUserInfo retUserInfo = this.userService.selectUserInfoByUserId(hMap);
			
			if(retUserInfo==null){
				model.addAttribute("uniqueID", "yes");
			}else{
				model.addAttribute("uniqueID", "no");
			}
			
			model.addAttribute("status", "success");
			return "jsonView";
		}catch(Exception e){
			logger.error("=+=+=+=+=+=+=+=+=+=+=+=+=+= EXCEPTION START +=+=+=+=+=+=+=+=+=+=+=+=");
			logger.error("EXCEPTION 위치 : "+ request.getServletPath());
			logger.error("EXCEPTION 내용 : \n" + e.toString());
			logger.error("=+=+=+=+=+=+=+=+=+=+=+=+=+= EXCEPTION END   +=+=+=+=+=+=+=+=+=+=+=+=");

			model.addAttribute("status", "fail");
			return "jsonView";
		}finally{
			logger.debug("=========================== DEBUG START ============================");
			logger.debug("DEBUG 위치 : "+ request.getServletPath() + sbDebugLog.toString());
			logger.debug("=========================== DEBUG END    ===========================");
			sbDebugLog.setLength(0);
		}
    }
    
	/**
	 * 사용자 등록 처리
	 * 
	 * @param Model model
	 * @param HttpServletRequest  request
	 * @return void
	 */
	@RequestMapping(value="/mng/usr/registProc.do")
	public String mngCrpRegistProc(ModelMap model, HttpServletRequest request) throws Exception {
		sbDebugLog.setLength(0);
		
		//파라미터 설정
		ReqUtils req = new ReqUtils(request);
		
		try{
			HttpSession session = request.getSession(false);
			VoUserInfo voUserInfo = (VoUserInfo)session.getAttribute("userInfoVO");
			
			//검색어 처리
			VoCommon voCommon = new VoCommon();
			voCommon.setSch_fld(req.getString("sch_fld"));
			voCommon.setSch_word(req.getString("sch_word"));
			
			HashMap<String, String> hMap = new HashMap<String,String>();
			hMap.put("userId", StringUtils.getNull(req.getString("user_id")));
			hMap.put("userNm", StringUtils.getNull(req.getString("user_nm")));
			hMap.put("reUserPw", StringUtils.getNull(req.getString("re_user_pw")));
			hMap.put("reUserPwConfirm", StringUtils.getNull(req.getString("re_user_pw_confirm")));
			hMap.put("authId", StringUtils.getNull(req.getString("auth_id")));
			hMap.put("corpId", StringUtils.getNull(req.getString("corp_id")));
			if(StringUtils.getNull(req.getString("email_id")).length()>0 && StringUtils.getNull(req.getString("email_host")).length()>0){
				hMap.put("userEmail", StringUtils.getNull(req.getString("email_id"))+"@"+StringUtils.getNull(req.getString("email_host")));
			}else{
				hMap.put("userEmail", "");
			}
			hMap.put("userHp", StringUtils.getNull(req.getString("user_hp")));
			hMap.put("userTel", StringUtils.getNull(req.getString("user_tel")));
			hMap.put("useYn", StringUtils.getNull(req.getString("use_yn")));
			hMap.put("accessFailCnt", StringUtils.getNull(req.getString("access_fail_cnt")));
			hMap.put("userAuth", voUserInfo.getAuth_id());
			hMap.put("sch_fld", voCommon.getSch_fld());
			hMap.put("sch_word", voCommon.getSch_word());
			hMap.put("regUserId", voUserInfo.getUser_id());
			hMap.put("userIp", request.getRemoteAddr());
			
			hMap.put("mode", StringUtils.getNull(req.getString("mode")));

			sbDebugLog.append("\n DEBUG mode : "+ StringUtils.getNull(req.getString("mode")));
			
			//mode 파라미터에 따른 분기처리
			if("I".equals(hMap.get("mode"))){//추가
				//User_pw 암호화 저장
				hMap.put("userPw", CryptUtils.enCrypt(StringUtils.convertSalting(hMap.get("userId"),hMap.get("reUserPw"))));
				
				sbDebugLog.append("\n DEBUG Parameter : CryptUtils.enCrypt(StringUtils.convertSalting(UserId,UserPw)) = " + hMap.get("userPw"));
				
				this.userService.insertUserInfo(hMap);

				SessionMessage.setMessage(request,"Process.data.success.create","/mng/usr/list.do?"+req.getChkParamStr(request,"page_no,sch_fld,sch_word"),"parent");
			}else if("U".equals(hMap.get("mode"))){//수정
				if(!"".equals(hMap.get("reUserPw"))){
					//User_pw 암호화 저장
					hMap.put("userPw", CryptUtils.enCrypt(StringUtils.convertSalting(hMap.get("userId"),hMap.get("reUserPw"))));
					sbDebugLog.append("\n DEBUG Parameter : CryptUtils.enCrypt(StringUtils.convertSalting(UserId,UserPw)) = " + hMap.get("userPw"));
				}
				
				this.userService.updateUserInfoByUserId(hMap);
				SessionMessage.setMessage(request,"Process.data.success.update","/mng/usr/list.do?"+req.getChkParamStr(request,"page_no,sch_fld,sch_word"),"parent");
			}else if("D".equals(hMap.get("mode"))){//삭제
				this.userService.deleteUserInfoByUserId(hMap);
				SessionMessage.setMessage(request,"Process.data.success.delete","/mng/usr/list.do?"+req.getChkParamStr(request,"sch_fld,sch_word"),"parent");
			}else{
				SessionMessage.setMessage(request,"Check.field.hidden.empty","/mng/usr/list.do?"+req.getChkParamStr(request,"page_no,sch_fld,sch_word"),"parent");
			}

			return "cmm/message";
		}catch(Exception e){
			logger.error("=+=+=+=+=+=+=+=+=+=+=+=+=+= EXCEPTION START +=+=+=+=+=+=+=+=+=+=+=+=");
			logger.error("EXCEPTION 위치 : "+ request.getServletPath());
			logger.error("EXCEPTION 내용 : \n" + e.toString());
			logger.error("=+=+=+=+=+=+=+=+=+=+=+=+=+= EXCEPTION END   +=+=+=+=+=+=+=+=+=+=+=+=");

			SessionMessage.setMessage(request,"Common.fail.Exception","/mng/usr/regist.do?"+req.getChkParamStr(request,"mode,page_no,user_id,sch_fld,sch_word"),"parent");
			return "cmm/message";
		}finally{
			logger.debug("=========================== DEBUG START ============================");
			logger.debug("DEBUG 위치 : "+ request.getServletPath() + sbDebugLog.toString());
			logger.debug("=========================== DEBUG END    ===========================");
			sbDebugLog.setLength(0);
		}
	}

}