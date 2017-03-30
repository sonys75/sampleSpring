package crawl.sys.res.mng.web;

import java.util.Arrays;
import java.util.HashMap;
import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import crawl.cmm.web.ListPaging;
import crawl.sys.res.mng.service.ResourceService;
import crawl.vo.VoCommon;
import crawl.vo.VoReso;
import resources.com.service.SessionMessage;
import resources.com.util.ReqUtils;
import resources.com.util.StringUtils;

/**
 * ResourceController
 */
@Controller
public class ResourceController {

	@Autowired
 	ResourceService resourceService;
 	
	public Logger logger = Logger.getLogger(this.getClass());
	public StringBuffer sbDebugLog = new StringBuffer();

	/**
	 * 자원 목록
	 * 
	 * @param Model model
	 * @param HttpServletRequest  request
	 * @return void
	 */
	@RequestMapping(value="/sys/res/mng/list.do")
	public String sysResMngList(ModelMap model, HttpServletRequest request) throws Exception {
		sbDebugLog.setLength(0);
		try{
			//파라미터 설정
			ReqUtils req = new ReqUtils(request);

			String sSchFld = StringUtils.getNull(req.getString("sch_fld"));
			String[] aSchFld = {"RESO_NM", "RESO_ID"};
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
			listPage.setTotalCount(this.resourceService.selectCntReso(hMap));
			sbDebugLog.append("\n DEBUG Parameter : listPage.getTotalCount() = " + listPage.getTotalCount());
			listPage.setCommonPara(req.getPageParamStr(request));
			
			sbDebugLog.append("\n DEBUG Parameter : listPage.getStartLine() = " + listPage.getStartLine());
			sbDebugLog.append("\n DEBUG Parameter : listPage.getEndLine() = " + listPage.getEndLine());
			hMap.put("startRow", Integer.toString(listPage.getStartLine()));
			hMap.put("endRow", Integer.toString(listPage.getEndLine()));
			
			voCommon.setListPaging(listPage);	

			List<VoReso> resoList = this.resourceService.selectListReso(hMap);
			
			//검색정보 세팅
			model.addAttribute("VoComm", voCommon);
			model.addAttribute("list", resoList);
			model.addAttribute("listPage", listPage);
			
			return "sys/res/mng/list";
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
	 * 자원 조회
	 * 
	 * @param Model model
	 * @param HttpServletRequest  request
	 * @return void
	 */
	@RequestMapping(value="/sys/res/mng/view.do")
	public String sysResMngView(ModelMap model, HttpServletRequest request) throws Exception {
		sbDebugLog.setLength(0);
		//파라미터 설정
		ReqUtils req = new ReqUtils(request);
		
		try{
			//검색어 처리
			HashMap<String, String> hMap = new HashMap<String,String>();
			hMap.put("resoId", StringUtils.getNull(req.getString("reso_id")));

			//리턴 정보 세팅
			VoReso retReso = new VoReso();
			List<VoReso> retResoAuth = null;
 
			//auth_no 가 넘어올 경우 관련 데이터를 쿼리한다.
			if(!"".equals(hMap.get("resoId"))){
				//관련정보 쿼리
				retReso = this.resourceService.selectResoByResoId(hMap);
				
				if(retReso==null){
					SessionMessage.setMessage(request,"Process.data.select.empty","/sys/res/mng/list.do?"+req.getChkParamStr(request,"page_no,sch_fld,sch_word"));
					return "cmm/message";
				}
				
				if(Integer.parseInt(retReso.getAuth_cnt())>0){
					retResoAuth = this.resourceService.selectResoAuthByResoId(hMap);
				}
 			}
			
			//parameter 처리
			retReso.setSch_fld(StringUtils.getNull(req.getString("sch_fld")));
			retReso.setSch_word(StringUtils.getNull(req.getString("sch_word")));
			retReso.setPage_no(StringUtils.getNull(req.getString("page_no")));
			sbDebugLog.append("\n DEBUG AllParameter : [ " + req.getAllParamStr(request) + " ]");

			model.addAttribute("VoReso", retReso );
			model.addAttribute("resoAuth", retResoAuth );
			model.addAttribute("retParam", req.getChkParamStr(request,"page_no,sch_fld,sch_word") );
 
			return "sys/res/mng/view";
		}catch(Exception e){
			logger.error("=+=+=+=+=+=+=+=+=+=+=+=+=+= EXCEPTION START +=+=+=+=+=+=+=+=+=+=+=+=");
			logger.error("EXCEPTION 위치 : "+ request.getServletPath());
			logger.error("EXCEPTION 내용 : \n" + e.toString());
			logger.error("=+=+=+=+=+=+=+=+=+=+=+=+=+= EXCEPTION END   +=+=+=+=+=+=+=+=+=+=+=+=");
			SessionMessage.setMessage(request,"Common.fail.Exception","/sys/res/mng/list.do?"+req.getChkParamStr(request,"page_no,sch_fld,sch_word"));
			return "cmm/message";
		}finally{
			logger.debug("=========================== DEBUG START ============================");
			logger.debug("DEBUG 위치 : "+ request.getServletPath() + sbDebugLog.toString());
			logger.debug("=========================== DEBUG END    ===========================");
			sbDebugLog.setLength(0);
		}
	}
	
	/**
	 * 자원 등록
	 * 
	 * @param Model model
	 * @param HttpServletRequest  request
	 * @return void
	 */
	@RequestMapping(value="/sys/res/mng/regist.do")
	public String sysResMngRegist(ModelMap model, HttpServletRequest request) throws Exception {
		sbDebugLog.setLength(0);
		//파라미터 설정
		ReqUtils req = new ReqUtils(request);

		try{
			//검색어 처리
			HashMap<String, String> hMap = new HashMap<String,String>();
			hMap.put("resoId", StringUtils.getNull(req.getString("reso_id")));

			//리턴 정보 세팅
			VoReso retReso = new VoReso();
			List<VoReso> retResoAuth = null;
			retReso.setMode("I");

			//app_id 가 넘어올 경우 수정MODE 로 변경하고, 관련 데이터를 쿼리한다.
			if(!"".equals(hMap.get("resoId"))){
				//관련정보 쿼리
				retReso = this.resourceService.selectResoByResoId(hMap);
				
				if(retReso==null){
					SessionMessage.setMessage(request,"Process.data.select.empty","/sys/res/mng/list.do?"+req.getChkParamStr(request,"page_no,sch_fld,sch_word"));
					return "cmm/message";
				}
				
				if(Integer.parseInt(retReso.getAuth_cnt())>0){
					retResoAuth = this.resourceService.selectResoAuthByResoId(hMap);
				}
				retReso.setMode("U");
 			}
			
			//parameter 처리
			retReso.setSch_fld(StringUtils.getNull(req.getString("sch_fld")));
			retReso.setSch_word(StringUtils.getNull(req.getString("sch_word")));
			retReso.setPage_no(StringUtils.getNull(req.getString("page_no")));
			sbDebugLog.append("\n DEBUG AllParameter : [ " + req.getAllParamStr(request) + " ]");

			model.addAttribute("VoReso", retReso );
			model.addAttribute("resoAuth", retResoAuth );
			model.addAttribute("retParam", req.getChkParamStr(request,"page_no,sch_fld,sch_word") );
  
			return "sys/res/mng/regist";
		}catch(Exception e){
			logger.error("=+=+=+=+=+=+=+=+=+=+=+=+=+= EXCEPTION START +=+=+=+=+=+=+=+=+=+=+=+=");
			logger.error("EXCEPTION 위치 : "+ request.getServletPath());
			logger.error("EXCEPTION 내용 : \n" + e.toString());
			logger.error("=+=+=+=+=+=+=+=+=+=+=+=+=+= EXCEPTION END   +=+=+=+=+=+=+=+=+=+=+=+=");
			/*			
			SessionMessage.setMessage(request,"Common.fail.Exception","/sys/res/mng/list.do");
			throw new Exception(e);
			*/
			SessionMessage.setMessage(request,"Common.fail.Exception","/sys/res/mng/list.do?"+req.getChkParamStr(request,"page_no,sch_fld,sch_word"));
			return "cmm/message";
		}finally{
			logger.debug("=========================== DEBUG START ============================");
			logger.debug("DEBUG 위치 : "+ request.getServletPath() + sbDebugLog.toString());
			logger.debug("=========================== DEBUG END    ===========================");
			sbDebugLog.setLength(0);
		}
	}
	
	/**
	 * 자원 등록 처리
	 * 
	 * @param Model model
	 * @param HttpServletRequest  request
	 * @return void
	 */
	@RequestMapping(value="/sys/res/mng/registProc.do", method=RequestMethod.POST)
	public String sysResMngRegistProc(ModelMap model, HttpServletRequest request) throws Exception {
		sbDebugLog.setLength(0);
		
		//파라미터 설정
		ReqUtils req = new ReqUtils(request);
		
		try{
			//검색어 처리
			VoCommon voCommon = new VoCommon();
			voCommon.setSch_fld(req.getString("sch_fld"));
			voCommon.setSch_word(req.getString("sch_word"));
			voCommon.setPage_no(req.getString("page_no"));
			
			HashMap<String, String> hMap = new HashMap<String,String>();
			hMap.put("mode", StringUtils.getNull(req.getString("mode")));
			hMap.put("resoId", StringUtils.getNull(req.getString("reso_id")));
			hMap.put("resoNm", StringUtils.getNull(req.getString("reso_nm")));
			hMap.put("resoPtn", StringUtils.getNull(req.getString("reso_ptn")));
			hMap.put("resoOrd", StringUtils.getNull(req.getString("reso_ord")));

			sbDebugLog.append("\n DEBUG mode : "+ hMap.get("mode"));
			
			String[] arrAuthId = request.getParameterValues("auth_id");
			String[] arrAuthNm = request.getParameterValues("auth_nm");
			
			//mode 파라미터에 따른 분기처리
			if("I".equals(hMap.get("mode"))){//추가
				int nResoCnt = this.resourceService.selectCntResoInfoByResoPtn(hMap);
				if(nResoCnt>0){
					SessionMessage.setMessage(request,"Resource.pattern.check.unique.no","/sys/res/mng/regist.do?"+req.getChkParamStr(request,"mode,page_no,sch_fld,sch_word"),"parent");
				}else{
					String getResoId = this.resourceService.insertResoInfo(hMap);
					
					hMap.put("resoId", getResoId);
					
					if(arrAuthId!=null){
						for(int i=0;i<arrAuthId.length;i++){
							hMap.put("resoId", getResoId);
							hMap.put("authId", arrAuthId[i]);
							hMap.put("authNm", hMap.get("resoNm") +"-"+ arrAuthNm[i]);
							this.resourceService.insertResoAuth(hMap);
						}
					}
					SessionMessage.setMessage(request,"Process.data.success.create","/sys/res/mng/list.do?"+req.getChkParamStr(request,"page_no,sch_fld,sch_word"),"parent");
				}
			}else if("U".equals(hMap.get("mode"))){//수정
				int nResoExistCnt = this.resourceService.selectCntResoInfoByResoId(hMap);
				
				if(nResoExistCnt==0){
					SessionMessage.setMessage(request,"Process.data.select.empty","/sys/res/mng/regist.do?"+req.getChkParamStr(request,"mode,reso_id,page_no,sch_fld,sch_word"),"parent");
				}else{
					int nResoCnt = this.resourceService.selectCntResoInfoByResoPtn(hMap);
					if(nResoCnt>0){
						SessionMessage.setMessage(request,"Resource.pattern.check.unique.no","/sys/res/mng/regist.do?"+req.getChkParamStr(request,"mode,page_no,sch_fld,sch_word"),"parent");
					}else{
						this.resourceService.updateResoInfoByResoId(hMap);

						//자원-권한 삭제
						this.resourceService.deleteResoAuthByResoId(hMap);
						
						if(arrAuthId!=null){
							for(int i=0;i<arrAuthId.length;i++){
								hMap.put("authId", arrAuthId[i]);
								hMap.put("authNm", hMap.get("resoNm") +"-"+ arrAuthNm[i]);
								this.resourceService.insertResoAuth(hMap);
							}
						}
						SessionMessage.setMessage(request,"Process.data.success.update","/sys/res/mng/list.do?"+req.getChkParamStr(request,"page_no,sch_fld,sch_word"),"parent");
					}
				}
			}else if("D".equals(hMap.get("mode"))){//삭제
				int nResoExistCnt = this.resourceService.selectCntResoInfoByResoId(hMap);
				
				if(nResoExistCnt==0){
					SessionMessage.setMessage(request,"Process.data.select.empty","/sys/res/mng/regist.do?"+req.getChkParamStr(request,"mode,reso_id,page_no,sch_fld,sch_word"),"parent");
				}else{
					//자원-권한 삭제
					this.resourceService.deleteResoAuthByResoId(hMap);
					
					//자원 삭제
					this.resourceService.deleteResoInfoByResoId(hMap);
					SessionMessage.setMessage(request,"Process.data.success.delete","/sys/res/mng/list.do?"+req.getChkParamStr(request,"sch_fld,sch_word"),"parent");
				}
			}else{
				SessionMessage.setMessage(request,"Check.field.hidden.empty","/sys/res/mng/list.do?"+req.getChkParamStr(request,"page_no,sch_fld,sch_word"),"parent");
			}

			return "cmm/message";
		}catch(Exception e){
			logger.error("=+=+=+=+=+=+=+=+=+=+=+=+=+= EXCEPTION START +=+=+=+=+=+=+=+=+=+=+=+=");
			logger.error("EXCEPTION 위치 : "+ request.getServletPath());
			logger.error("EXCEPTION 내용 : \n" + e.toString());
			logger.error("=+=+=+=+=+=+=+=+=+=+=+=+=+= EXCEPTION END   +=+=+=+=+=+=+=+=+=+=+=+=");

			SessionMessage.setMessage(request,"Common.fail.Exception","/sys/res/mng/regist.do?"+req.getChkParamStr(request,"mode,page_no,app_id,sch_fld,sch_word"),"parent");
			return "cmm/message";
		}finally{
			logger.debug("=========================== DEBUG START ============================");
			logger.debug("DEBUG 위치 : "+ request.getServletPath() + sbDebugLog.toString());
			logger.debug("=========================== DEBUG END    ===========================");
			sbDebugLog.setLength(0);
		}
	}
}