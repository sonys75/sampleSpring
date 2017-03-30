package crawl.mng.cde.web;

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

import crawl.cmm.web.ListPaging;
import crawl.mng.cde.service.CodeManageService;
import crawl.mng.usr.service.UserService;
import crawl.vo.VoCodeInfo;
import crawl.vo.VoCommon;
import crawl.vo.VoUserInfo;
import resources.com.service.SessionMessage;
import resources.com.util.ReqUtils;
import resources.com.util.StringUtils;

/**
 * CodeManageController
 */
@Controller
public class CodeManageController {
 	
	@Autowired
 	UserService userService;
  
	@Autowired
 	CodeManageService codeManageService;
 	
	public Logger logger = Logger.getLogger(this.getClass());
	public StringBuffer sbDebugLog = new StringBuffer();
	
	/**
	 * 코드 분류 목록
	 * 
	 * @param Model model
	 * @param HttpServletRequest  request
	 * @return void
	 */
	@RequestMapping(value="/mng/cde/part_list.do")
	public String mngCdePartList(ModelMap model, HttpServletRequest request) throws Exception {
		sbDebugLog.setLength(0);
		try{
			//파라미터 설정
			ReqUtils req = new ReqUtils(request);

			//sbDebugLog.append("\n DEBUG AllParameter : [ " + req.getAllParamStr(request) + " ]");
			
			String sSchFld = StringUtils.getNull(req.getString("sch_fld"));
			String[] aSchFld = {"CODE_PART", "CODE_PART_NM"};
			if(!Arrays.asList(aSchFld).contains(sSchFld)){
				sSchFld =  aSchFld[0];
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
			listPage.setTotalCount(this.codeManageService.selectCntCodePart(hMap));
			listPage.setCommonPara(req.getPageParamStr(request));
			
			sbDebugLog.append("\n DEBUG Parameter : listPage.getTotalCount() = " + listPage.getTotalCount());
			sbDebugLog.append("\n DEBUG Parameter : listPage.getStartLine() = " + listPage.getStartLine());
			sbDebugLog.append("\n DEBUG Parameter : listPage.getEndLine() = " + listPage.getEndLine());
			
			hMap.put("startRow", Integer.toString(listPage.getStartLine()));
			hMap.put("endRow", Integer.toString(listPage.getEndLine()));
			
			voCommon.setListPaging(listPage);	

			List<VoCodeInfo> codeList = this.codeManageService.selectListCodePart(hMap);
			
			//검색정보 세팅
			model.addAttribute("VoComm", voCommon);
			model.addAttribute("list", codeList);
			model.addAttribute("listPage", listPage);
			
			return "mng/cde/part_list";
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
	 * 코드 분류 등록
	 * 
	 * @param Model model
	 * @param HttpServletRequest  request
	 * @return void
	 */
	@RequestMapping(value="/mng/cde/part_regist.do")
	public String mngCdePartRegist(ModelMap model, HttpServletRequest request) throws Exception {
		sbDebugLog.setLength(0);
		try{
			//파라미터 설정
			ReqUtils req = new ReqUtils(request);

			//검색어 처리
			VoCommon voCommon = new VoCommon();
			voCommon.setSch_fld(req.getString("sch_fld"));
			voCommon.setSch_word(req.getString("sch_word"));
			
			HashMap<String, String> hMap = new HashMap<String,String>();
			hMap.put("sch_fld", voCommon.getSch_fld());
			hMap.put("sch_word", voCommon.getSch_word());
			hMap.put("codePart", StringUtils.getNull(req.getString("code_part")));

			//리턴 정보 세팅
			VoCodeInfo retCodeInfo = new VoCodeInfo();
			retCodeInfo.setMode("I");

			//code_part 가 넘어올 경우 수정MODE 로 변경하고, 관련 데이터를 쿼리한다.
			if(!"".equals(hMap.get("codePart"))){
				//관련정보 쿼리
				retCodeInfo = this.codeManageService.selectCodePartByCodePart(hMap);
				
				if(retCodeInfo==null){
					SessionMessage.setMessage(request,"Process.data.select.empty","/mng/cde/part_list.do?"+req.getChkParamStr(request,"page_no,sch_fld,sch_word"));
					return "cmm/message";
				}
				retCodeInfo.setMode("U");
			}
 
			//parameter 처리
			retCodeInfo.setSch_fld(StringUtils.getNull(req.getString("sch_fld")));
			retCodeInfo.setSch_word(StringUtils.getNull(req.getString("sch_word")));
			retCodeInfo.setPage_no(StringUtils.getNull(req.getString("page_no")));
			sbDebugLog.append("\n DEBUG AllParameter : [ " + req.getAllParamStr(request) + " ]");
 
			model.addAttribute("VoCodeInfo", retCodeInfo );
			model.addAttribute("retParam", req.getChkParamStr(request,"page_no,sch_fld,sch_word") );

			return "mng/cde/part_regist";
		}catch(Exception e){
			logger.error("=+=+=+=+=+=+=+=+=+=+=+=+=+= EXCEPTION START +=+=+=+=+=+=+=+=+=+=+=+=");
			logger.error("EXCEPTION 위치 : "+ request.getServletPath());
			logger.error("EXCEPTION 내용 : \n" + e.toString());
			logger.error("=+=+=+=+=+=+=+=+=+=+=+=+=+= EXCEPTION END   +=+=+=+=+=+=+=+=+=+=+=+=");
			SessionMessage.setMessage(request,"Common.fail.Exception","/mng/cde/part_list.do");
			return "cmm/message";
		}finally{
			logger.debug("=========================== DEBUG START ============================");
			logger.debug("DEBUG 위치 : "+ request.getServletPath() + sbDebugLog.toString());
			logger.debug("=========================== DEBUG END    ===========================");
			sbDebugLog.setLength(0);
		}
	}
	
	/**
	 * 코드 분류 등록 처리
	 * 
	 * @param Model model
	 * @param HttpServletRequest  request
	 * @return void
	 */
	@RequestMapping(value="/mng/cde/part_registProc.do")
	public String mngCdePartRegistProc(ModelMap model, HttpServletRequest request) throws Exception {
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
			hMap.put("codePart", StringUtils.getNull(req.getString("code_part")));
			hMap.put("codePartNm", StringUtils.getNull(req.getString("code_part_nm")));
			hMap.put("sch_fld", voCommon.getSch_fld());
			hMap.put("sch_word", voCommon.getSch_word());
			hMap.put("regUserId", voUserInfo.getUser_id());
			hMap.put("userIp", request.getRemoteAddr());
			hMap.put("mode", StringUtils.getNull(req.getString("mode")));

			//mode 파라미터에 따른 분기처리
			if("I".equals(hMap.get("mode"))){//추가
				//중복검사
				VoCodeInfo retCodeInfo = this.codeManageService.selectCodePartByCodePart(hMap);
				if(retCodeInfo!=null){
					SessionMessage.setMessage(request,"Process.data.fail.exist.code.part");
					return "cmm/message";
				}
				this.codeManageService.insertCodePart(hMap);

				SessionMessage.setMessage(request,"Process.data.success.create","/mng/cde/part_list.do?"+req.getChkParamStr(request,"page_no,sch_fld,sch_word"),"parent");
			}else if("U".equals(hMap.get("mode"))){//수정
			
				this.codeManageService.updateCodePartByCodePart(hMap);
				SessionMessage.setMessage(request,"Process.data.success.update","/mng/cde/part_list.do?"+req.getChkParamStr(request,"page_no,sch_fld,sch_word"),"parent");
			}else if("D".equals(hMap.get("mode"))){//삭제
				//삭제전 분류코드를 사용하는 코드가 존재하는지 검사
				List<VoCodeInfo> retCodeInfo = this.codeManageService.selectListCodeInfoByCodePart(hMap);
				if( retCodeInfo.size() >0 ){
					SessionMessage.setMessage(request,"Process.data.fail.exist.code.part.used");
					return "cmm/message";
				}
				
				this.codeManageService.deleteCodePartByCodePart(hMap);
				SessionMessage.setMessage(request,"Process.data.success.delete","/mng/cde/part_list.do?"+req.getChkParamStr(request,"sch_fld,sch_word"),"parent");
			}else{
				SessionMessage.setMessage(request,"Check.field.hidden.empty","/mng/cde/part_list.do?"+req.getChkParamStr(request,"page_no,sch_fld,sch_word"),"parent");
			}

			return "cmm/message";
		}catch(Exception e){
			logger.error("=+=+=+=+=+=+=+=+=+=+=+=+=+= EXCEPTION START +=+=+=+=+=+=+=+=+=+=+=+=");
			logger.error("EXCEPTION 위치 : "+ request.getServletPath());
			logger.error("EXCEPTION 내용 : \n" + e.toString());
			logger.error("=+=+=+=+=+=+=+=+=+=+=+=+=+= EXCEPTION END   +=+=+=+=+=+=+=+=+=+=+=+=");

			SessionMessage.setMessage(request,"Common.fail.Exception","/mng/cde/part_regist.do?"+req.getChkParamStr(request,"mode,page_no,code_part,sch_fld,sch_word"),"parent");
			return "cmm/message";
		}finally{
			logger.debug("=========================== DEBUG START ============================");
			logger.debug("DEBUG 위치 : "+ request.getServletPath() + sbDebugLog.toString());
			logger.debug("=========================== DEBUG END    ===========================");
			sbDebugLog.setLength(0);
		}
	}

	/**
	 * 코드 목록
	 * 
	 * @param Model model
	 * @param HttpServletRequest  request
	 * @return void
	 */
	@RequestMapping(value="/mng/cde/list.do")
	public String mngCdeList(ModelMap model, HttpServletRequest request) throws Exception {
		sbDebugLog.setLength(0);
		try{
			//파라미터 설정
			ReqUtils req = new ReqUtils(request);

			//sbDebugLog.append("\n DEBUG AllParameter : [ " + req.getAllParamStr(request) + " ]");
			String sSchFld = StringUtils.getNull(req.getString("sch_fld"));
			String[] aSchFld = {"CODE_PART", "CODE", "CODE_NM", "CODE_DESC"};
			if(!Arrays.asList(aSchFld).contains(sSchFld)){
				sSchFld =  aSchFld[0];
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
			listPage.setTotalCount(this.codeManageService.selectCntCodeInfo(hMap));
			listPage.setCommonPara(req.getPageParamStr(request));
			
			sbDebugLog.append("\n DEBUG Parameter : listPage.getTotalCount() = " + listPage.getTotalCount());
			sbDebugLog.append("\n DEBUG Parameter : listPage.getStartLine() = " + listPage.getStartLine());
			sbDebugLog.append("\n DEBUG Parameter : listPage.getEndLine() = " + listPage.getEndLine());
			
			hMap.put("startRow", Integer.toString(listPage.getStartLine()));
			hMap.put("endRow", Integer.toString(listPage.getEndLine()));
			
			voCommon.setListPaging(listPage);	

			List<VoCodeInfo> codeList = this.codeManageService.selectListCodeInfo(hMap);
			
			//검색정보 세팅
			model.addAttribute("VoComm", voCommon);
			model.addAttribute("list", codeList);
			model.addAttribute("listPage", listPage);
			
			return "mng/cde/list";
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
	 * 코드 등록
	 * 
	 * @param Model model
	 * @param HttpServletRequest  request
	 * @return void
	 */
	@RequestMapping(value="/mng/cde/regist.do")
	public String mngCdeRegist(ModelMap model, HttpServletRequest request) throws Exception {
		sbDebugLog.setLength(0);
		try{
			//파라미터 설정
			ReqUtils req = new ReqUtils(request);

			//검색어 처리
			VoCommon voCommon = new VoCommon();
			voCommon.setSch_fld(req.getString("sch_fld"));
			voCommon.setSch_word(req.getString("sch_word"));
			
			HashMap<String, String> hMap = new HashMap<String,String>();
			hMap.put("sch_fld", voCommon.getSch_fld());
			hMap.put("sch_word", voCommon.getSch_word());
			hMap.put("codePart", StringUtils.getNull(req.getString("code_part")));
			hMap.put("code", StringUtils.getNull(req.getString("code")));

			//리턴 정보 세팅
			VoCodeInfo retCodeInfo = new VoCodeInfo();
			retCodeInfo.setMode("I");

			//code_part,code 가 넘어올 경우 수정MODE 로 변경하고, 관련 데이터를 쿼리한다.
			if(!"".equals(hMap.get("codePart")) && !"".equals(hMap.get("code"))){
				//관련정보 쿼리
				retCodeInfo = this.codeManageService.selectCodeInfoByCode(hMap);
				
				if(retCodeInfo==null){
					SessionMessage.setMessage(request,"Process.data.select.empty","/mng/cde/list.do?"+req.getChkParamStr(request,"page_no,sch_fld,sch_word"));
					return "cmm/message";
				}
				retCodeInfo.setMode("U");
			}
 
			HashMap<String, String> hCodeMap = new HashMap<String,String>();

			int nCodePartCnt = this.codeManageService.selectCntCodePart(hCodeMap);

			hCodeMap.put("startRow", "1");
			hCodeMap.put("endRow", Integer.toString(nCodePartCnt));

			List<VoCodeInfo> codePartList = this.codeManageService.selectListCodePart(hCodeMap);
			
			//parameter 처리
			retCodeInfo.setSch_fld(StringUtils.getNull(req.getString("sch_fld")));
			retCodeInfo.setSch_word(StringUtils.getNull(req.getString("sch_word")));
			retCodeInfo.setPage_no(StringUtils.getNull(req.getString("page_no")));
			sbDebugLog.append("\n DEBUG AllParameter : [ " + req.getAllParamStr(request) + " ]");
 
			model.addAttribute("VoCodeInfo", retCodeInfo );
			model.addAttribute("CodePartList", codePartList );
			model.addAttribute("retParam", req.getChkParamStr(request,"page_no,sch_fld,sch_word") );

			return "mng/cde/regist";
		}catch(Exception e){
			logger.error("=+=+=+=+=+=+=+=+=+=+=+=+=+= EXCEPTION START +=+=+=+=+=+=+=+=+=+=+=+=");
			logger.error("EXCEPTION 위치 : "+ request.getServletPath());
			logger.error("EXCEPTION 내용 : \n" + e.toString());
			logger.error("=+=+=+=+=+=+=+=+=+=+=+=+=+= EXCEPTION END   +=+=+=+=+=+=+=+=+=+=+=+=");
			SessionMessage.setMessage(request,"Common.fail.Exception","/mng/cde/list.do");
			return "cmm/message";
		}finally{
			logger.debug("=========================== DEBUG START ============================");
			logger.debug("DEBUG 위치 : "+ request.getServletPath() + sbDebugLog.toString());
			logger.debug("=========================== DEBUG END    ===========================");
			sbDebugLog.setLength(0);
		}
	}
	
	/**
	 * 코드 등록 처리
	 * 
	 * @param Model model
	 * @param HttpServletRequest  request
	 * @return void
	 */
	@RequestMapping(value="/mng/cde/registProc.do")
	public String mngCdeRegistProc(ModelMap model, HttpServletRequest request) throws Exception {
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
			hMap.put("codePart", StringUtils.getNull(req.getString("code_part")));
			hMap.put("code", StringUtils.getNull(req.getString("code")));
			hMap.put("codeNm", StringUtils.getNull(req.getString("code_nm")));
			hMap.put("codeDesc", StringUtils.getNull(req.getString("code_desc")));
			hMap.put("codeOrd", StringUtils.getNull(req.getString("code_ord")));
			hMap.put("sch_fld", voCommon.getSch_fld());
			hMap.put("sch_word", voCommon.getSch_word());
			hMap.put("regUserId", voUserInfo.getUser_id());
			hMap.put("userIp", request.getRemoteAddr());
			hMap.put("mode", StringUtils.getNull(req.getString("mode")));

			//mode 파라미터에 따른 분기처리
			if("I".equals(hMap.get("mode"))){//추가
				//중복검사
				VoCodeInfo retCodeInfo = this.codeManageService.selectCodeInfoByCode(hMap);
				if(retCodeInfo!=null){
					SessionMessage.setMessage(request,"Process.data.fail.exist.code.part");
					return "cmm/message";
				}
				this.codeManageService.insertCodeInfo(hMap);

				SessionMessage.setMessage(request,"Process.data.success.create","/mng/cde/list.do?"+req.getChkParamStr(request,"page_no,sch_fld,sch_word"),"parent");
			}else if("U".equals(hMap.get("mode"))){//수정
				this.codeManageService.updateCodeInfoByCode(hMap);
				SessionMessage.setMessage(request,"Process.data.success.update","/mng/cde/list.do?"+req.getChkParamStr(request,"page_no,sch_fld,sch_word"),"parent");
			}else if("D".equals(hMap.get("mode"))){//삭제
				this.codeManageService.deleteCodeInfoByCode(hMap);
				SessionMessage.setMessage(request,"Process.data.success.delete","/mng/cde/list.do?"+req.getChkParamStr(request,"sch_fld,sch_word"),"parent");
			}else{
				SessionMessage.setMessage(request,"Check.field.hidden.empty","/mng/cde/list.do?"+req.getChkParamStr(request,"page_no,sch_fld,sch_word"),"parent");
			}

			return "cmm/message";
		}catch(Exception e){
			logger.error("=+=+=+=+=+=+=+=+=+=+=+=+=+= EXCEPTION START +=+=+=+=+=+=+=+=+=+=+=+=");
			logger.error("EXCEPTION 위치 : "+ request.getServletPath());
			logger.error("EXCEPTION 내용 : \n" + e.toString());
			logger.error("=+=+=+=+=+=+=+=+=+=+=+=+=+= EXCEPTION END   +=+=+=+=+=+=+=+=+=+=+=+=");

			SessionMessage.setMessage(request,"Common.fail.Exception","/mng/cde/regist.do?"+req.getChkParamStr(request,"mode,page_no,code_part,code,sch_fld,sch_word"),"parent");
			return "cmm/message";
		}finally{
			logger.debug("=========================== DEBUG START ============================");
			logger.debug("DEBUG 위치 : "+ request.getServletPath() + sbDebugLog.toString());
			logger.debug("=========================== DEBUG END    ===========================");
			sbDebugLog.setLength(0);
		}
	}
	

}