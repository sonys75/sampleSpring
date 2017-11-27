package com.skhynix.hydesign.portal.common.interceptor;

import java.util.Enumeration;
import java.util.HashMap;
import java.util.Locale;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import net.sf.json.JSONArray;

import org.apache.commons.configuration.Configuration;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.servlet.handler.HandlerInterceptorAdapter;

import com.skhynix.hydesign.portal.biz.auth.AuthorCheckService;
import com.skhynix.hydesign.portal.common.constants.PortalConstants;
import com.skhynix.hydesign.portal.common.vo.SessionVO;

/**
 * 권한 체크 Interceptor
 * 
 * @author SK Hynix
 * @version 1.00
 * @created 2017. 10. 17
 */
public class ProjectAuthorCheckInterceptor extends HandlerInterceptorAdapter {
	
    /**
     * 권한체크 Service
     */
    @Autowired
    AuthorCheckService authorCheckService;
    
    /**
     * Configuration
     */
    @Autowired
    private Configuration configuration;
    
    private final Logger logger = LoggerFactory.getLogger(getClass());
    /* 개발용으로만 사용
	public StringBuffer sbDebugLog = new StringBuffer();
	*/
    
	/**
     * Default constructor
     */
    public ProjectAuthorCheckInterceptor() {
        // Default constructor
    }

    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
    	
    	HttpSession session = request.getSession();
        SessionVO svo = (SessionVO)session.getAttribute(PortalConstants.Key.SESSION_KEY);

        String requestScheme = request.getScheme(); //요청 프로토콜
		String sSvrNm = request.getServerName();
		int nPort = request.getServerPort();
		String requestURI = request.getRequestURI(); //요청 URI
		String requestReferer = request.getHeader("REFERER");
		String requestUserAgent = request.getHeader("User-Agent").toLowerCase();
		String requestCharacterEncoding = request.getCharacterEncoding();
		String requestContentType = request.getContentType();
		String requestMethod = request.getMethod();
		String requestRemoteAddr = getClientIP(request);
		String ajaxFg = request.getHeader("AJAX");
		
		if(requestReferer== null) requestReferer="";
		
		boolean bReturn = true;
		
		// parameter 로그
		StringBuilder sb = new StringBuilder();
		sb.append( JSONArray.fromObject(request.getParameterMap()) );
		
		// 로직 부분 호출
		Map<String, Object> requestMap = new HashMap<String, Object>();
		Enumeration<?> enumeration = request.getParameterNames();
		while (enumeration.hasMoreElements()) {
			String key = (String)enumeration.nextElement();
			String[] values = request.getParameterValues(key);
			if (values != null) {
				if( "GET".equals(request.getMethod()) || values.length == 1 ) {
					requestMap.put(key, values[0]);
				}else {
					requestMap.put(key, values);
				}
			}
		}
		
		/* 개발용으로만 사용
		sbDebugLog.setLength(0);
		sbDebugLog.append("\n >>> requestScheme : "+ requestScheme);
		sbDebugLog.append("\n >>> sSvrNm : "+ sSvrNm);
		sbDebugLog.append("\n >>> nPort : "+ nPort);
		sbDebugLog.append("\n >>> RequestURI : "+ requestURI);
		sbDebugLog.append("\n >>> RequestReferer : "+ requestReferer);
		sbDebugLog.append("\n >>> User-Agent : "+  requestUserAgent);
		sbDebugLog.append("\n >>> CharacterEncoding : "+  requestCharacterEncoding);
		sbDebugLog.append("\n >>> ContentType : "+  requestContentType);
		sbDebugLog.append("\n >>> Method : "+  requestMethod);
		sbDebugLog.append("\n >>> RemoteAddr : "+  requestRemoteAddr);
		sbDebugLog.append("\n >>> sessionKey : "+ session.getAttribute(PortalConstants.Key.SESSION_KEY));
		sbDebugLog.append("\n >>> ajaxFg : "+ ajaxFg);
		sbDebugLog.append("\n >>> requestMap : "+ requestMap.toString());
		logger.debug("\n=========================== REQUEST DEBUG START ============================"
					+ sbDebugLog.toString()
					+"\n=========================== REQUEST DEBUG END    ===========================");
		sbDebugLog.setLength(0);
		*/
		/**/
		
		if (svo == null || "".equals(svo.getEmpNo())) {
			if ("xmlhttprequest".equals(request.getHeader("X-Requested-With").toLowerCase(Locale.ENGLISH))) {
                response.sendError(999);
            } else {
                // 시스템의 로그인 사용자 정보 없을 경우, sso 권한제한 페이지 호출
                response.sendRedirect(configuration.getString("portal.sso.reject.url"));
            }
            return false;
		}
		
        Map<String, Object> pMap = new HashMap<String, Object>();
        pMap.put("sessEmpNo", svo.getEmpNo());
        
        //넘어온 파라미터 empNo 와 세션 empNo 가 다를 때 
        /*
        if(requestMap.containsKey("empNo") && !requestMap.get("empNo").equals(svo.getEmpNo())){
            bReturn = false;
            response.sendError(401);
            return bReturn;
        }
        */
       
		//프로젝트(일반/SPOT) 상세정보
		if("/work/project/develop/devProjectDetail".equals(requestURI)){
			pMap.put("prjId", requestMap.get("prjid"));
			bReturn = authorCheckService.selectProjectAuthorCheck(pMap, "PRJ_DTL_VIEW_FG");
		}
		//프로젝트(일반/SPOT) 상세정보 버튼 제어
		else if("/work/project/develop/searchAclGroupStaffMgmtList".equals(requestURI)
				|| "/work/project/develop/modifyProjectComplete".equals(requestURI)
				|| "/work/project/develop/devEditPhase".equals(requestURI)
				|| "/work/project/develop/devEditCft".equals(requestURI)
				|| "/work/project/develop/devRevision".equals(requestURI)
				|| "/work/project/develop/searchBasePrjAddReqCnt".equals(requestURI)
				|| "/work/project/develop/modifyProjectDeleteFg".equals(requestURI)){
			if(requestMap.containsKey("prjid") && !"".equals(requestMap.get("prjid"))){
				pMap.put("prjId", requestMap.get("prjid"));
				String checkAuth = "";
				if("/work/project/develop/devEditCft".equals(requestURI)
						|| "/work/project/develop/devRevision".equals(requestURI)){
					checkAuth = "BTN_AUTH_FG||PART_LEADER_FG";
				}
				
				if(!"".equals(checkAuth))
				bReturn = authorCheckService.selectProjectAuthorCheck(pMap, checkAuth);
			}
		}
		//해외 프로젝트 상세정보
		else if("/work/overseas/develop/overseasDevProjectDetail".equals(requestURI)){
			pMap.put("prjId", requestMap.get("prjid"));
			//pMap.put("prjId", "114004");//test projectid
			bReturn = authorCheckService.selectOverseasAuthorCheck(pMap);
		}
		//그룹서버 권한 체크
		else if("/work/group/develop/devGrpDetail".equals(requestURI)){
			pMap.put("grpSrvId", requestMap.get("grpSrvId"));
			bReturn = authorCheckService.selectGroupSvrAuthorCheck(pMap, "GRP_DTL_VIEW_FG||GRP_SRV_MEM_AUTH_FG");
			/*
			if(!bReturn){
				bReturn = authorCheckService.selectGroupSvrAuthorCheck(pMap, "GRP_SRV_MEM_AUTH_FG");
			}
			*/
		}
		//서버 신청 권한 체크
		else if(requestURI.startsWith("/work/systemReq/serverReq/modify")
				&& requestURI.startsWith("/work/systemReq/serverReq/create")
				&& requestURI.startsWith("/work/systemReq/serverReq/modifyServerInfo")
				&& !requestURI.startsWith("/work/systemReq/serverReq/serverComplete")){
			if(requestMap.containsKey("apprSq") && !"".equals(requestMap.get("apprSq"))){
				pMap.put("apprSq", requestMap.get("apprSq"));
				if("/work/systemReq/serverReq/modifyServerInfo".equals(requestURI)){
					bReturn = authorCheckService.selectServerAuthorCheck(pMap, "MODIFY_FG");
				}else{
					bReturn = authorCheckService.selectServerAuthorCheck(pMap, "AUTH_FG");
				}
			}
		}
		//스토리지 신청 권한 체크
		else if(requestURI.startsWith("/work/systemReq/storageReq")
				&& !requestURI.startsWith("/work/systemReq/storageReq/storageComplete")){
			if(requestMap.containsKey("apprSq") && !"".equals(requestMap.get("apprSq"))){
				pMap.put("apprSq", requestMap.get("apprSq"));
				bReturn = authorCheckService.selectStorageAuthorCheck(pMap, "AUTH_FG");
			}
			if("/work/systemReq/storageReq/searchStorageVolModHistCnt".equals(requestURI)
				|| "/work/systemReq/storageReq/modifyStorageMgmt".equals(requestURI)
				|| "/work/systemReq/storageReq/modifyStorageMgmtFinishFg".equals(requestURI)){
				bReturn = authorCheckService.selectStorageAuthorCheck(pMap, "MODIFY_FG");
			}
		}
		//개인 계정 권한 PC 다운로드 권한 체크
		else if("/work/account/privateAccount/privateAccountTrans".equals(requestURI)){
			if(requestMap.containsKey("accName") && !"".equals(requestMap.get("accName"))){
				pMap.put("accName", requestMap.get("accName"));
				bReturn = authorCheckService.selectPrivateAccountAuthorCheck(pMap, "TRANS_FG");
			}
		}
		//개인 계정 권한 PC 다운로드 요청 권한 체크
		else if("/work/account/privateAccount/privateAccountReqTrans".equals(requestURI)){
			if(requestMap.containsKey("accName") && !"".equals(requestMap.get("accName"))){
				pMap.put("accName", requestMap.get("accName"));
				bReturn = authorCheckService.selectPrivateAccountAuthorCheck(pMap, "REQ_FG");
			}
		}
		//개인 계정 전송파일 목록 체크
		else if("/common/fileTransPopup".equals(requestURI) 
				&& requestReferer.endsWith("/work/account/privateAccount/privateAccountTrans")){
			/*
			pMap.put("apprSq", requestMap.get("apprSq"));
			bReturn = authorCheckService.checkTransFileList(pMap,requestMap);
			*/
		}
		//개인 계정 업로드 파일찾기 체크
		else if("/work/group/develop/grpSrvBrowser".equals(requestURI) 
				|| "/work/group/develop/grpSrvExplore".equals(requestURI)){
			if(requestMap.containsKey("getHomeDir") && !"".equals(requestMap.get("getHomeDir"))){
				String getHomeDir = (String)requestMap.get("getHomeDir");
				/*
				if(!getHomeDir.startsWith("/") || !getHomeDir.startsWith("/user/")){
					bReturn = false;
				}
				//개인 계정 권한 PC 다운로드 요청 권한 체크
				else{
					String accName = getHomeDir.replaceAll("/user/", "");
					pMap.put("accName", accName);
					System.out.println("accName : "+ accName);
					bReturn = authorCheckService.selectPrivateAccountAuthorCheck(pMap, "REQ_FG");
				}
				*/
			}
		}
		//개인 계정 업로드 체크
		else if("/common/fileTransPopup".equals(requestURI) 
				&& requestReferer.endsWith("/work/account/privateAccount/privateAccountList")){
			
			if(requestMap.containsKey("destPath") && !"".equals(requestMap.get("destPath"))){
				/*
				String destPath = (String)requestMap.get("destPath");
				String unixAcc = (String)requestMap.get("unixAcc");
				
				if(!destPath.startsWith("/") || !destPath.startsWith("/user/"+unixAcc)){
					bReturn = false;
				}
				*/
			}
		}
		//전자결재 내 상신함
		else if(requestURI.startsWith("/work/electronic/report/")
				 && !requestURI.endsWith("List")
				 && !requestReferer.endsWith("/my/inform")){
			if(requestMap.containsKey("apprSq") && !"".equals(requestMap.get("apprSq"))){
				pMap.put("apprSq", requestMap.get("apprSq"));
				bReturn = authorCheckService.selectElectronicReportAuthorCheck(pMap, "VIEW_FG");
			}
		}
		//전자결재 내 결재함
		else if(requestURI.startsWith("/work/electronic/approval/")
				//&& !requestURI.endsWith("/storage/serverApprReqDetail")
				|| requestURI.startsWith("/admin/server/")){
			if(requestReferer.indexOf("/admin/server/") == -1 && requestReferer.indexOf("/my/approval") == -1){
				if(requestMap.containsKey("apprSq") && !"".equals(requestMap.get("apprSq"))){
					pMap.put("apprSq", requestMap.get("apprSq"));
					bReturn = authorCheckService.selectElectronicApprovalAuthorCheck(pMap, "VIEW_FG");
				}
			}
		}
		


		if(!bReturn){
			response.sendError(401);
		}
		
        return bReturn;
    }
    /**
     * ClientIP 정보 내용 입력
     * 
     * @param request
     * @return
     */
    public String getClientIP(HttpServletRequest request) {
        String ip = request.getHeader("X-FORWARDED-FOR");
        if (ip == null || ip.length() == 0) {
            ip = request.getHeader("Proxy-Client-IP");
        }
        if (ip == null || ip.length() == 0) {
            ip = request.getHeader("WL-Proxy-Client-IP"); // 웹로직
        }
        if (ip == null || ip.length() == 0) {
            ip = request.getRemoteAddr();
        }
        return ip;
    }
}
