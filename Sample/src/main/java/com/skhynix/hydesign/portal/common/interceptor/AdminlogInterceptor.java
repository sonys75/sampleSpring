package com.skhynix.hydesign.portal.common.interceptor;

import java.io.File;
import java.util.ArrayList;
import java.util.Enumeration;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import net.sf.json.JSONArray;

import org.apache.commons.configuration.Configuration;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.handler.HandlerInterceptorAdapter;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.skhynix.hydesign.portal.biz.admin.manage.AdminLogDao;
import com.skhynix.hydesign.portal.biz.common.CommonService;
import com.skhynix.hydesign.portal.biz.login.LoginService;
import com.skhynix.hydesign.portal.common.constants.PortalConstants;
import com.skhynix.hydesign.portal.common.util.PortalUtil;
import com.skhynix.hydesign.portal.common.vo.SessionVO;

/**
 * interceptor
 * 
 * @author  hykim
 * @version 1.00
 * @created 2017. 6. 1.
 */
public class AdminlogInterceptor extends HandlerInterceptorAdapter {

    /**
     * Configuration
     */
    @Autowired
    private Configuration configuration;

    /**
     * 로그인 서비스
     */
    @Autowired
    private LoginService loginService;

    /**
     * 공통 서비스
     */
    @Autowired
    private CommonService commonService;
    
    @Autowired
    private AdminLogDao adminLogDao;

    /**
     * Default constructor
     */
    public AdminlogInterceptor() {
        // Default constructor
    }

    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {

        HttpSession session = request.getSession();
        SessionVO svo = (SessionVO)session.getAttribute(PortalConstants.Key.SESSION_KEY);
        
        String requestUri = request.getRequestURI();
        
        try {
        	// 메인화면일 경우
        	//if( session.getAttribute("adminlogAuthYn") == null && requestUri.startsWith("/main") ) {
        	if( requestUri.startsWith("/main") ) { // TODO :: 테스트
            	// 관리자 접속 권한 체크
            	Map<String, Object> pMap = new HashMap<String, Object>();
            	pMap.put("authGroupCd", PortalConstants.AdminAuthTypeCd.ADMIN_LOG);
            	pMap.put("empNo", svo.getOrigEmpNo());
            	/*
            	if(svo.getOrigEmpNo() == null || "".equals(svo.getOrigEmpNo())){
            		pMap.put("empNo", svo.getEmpNo());
            	}
            	*/
            	String adminlogAuthYn = adminLogDao.searchAdminlogAuthYn(pMap);
            	session.setAttribute("adminlogAuthYn", adminlogAuthYn);
        	}

        	// 사용자가 동일한 경우
            if( svo.getEmpNo().equals(svo.getOrigEmpNo()) ){
            	svo.setLoginHistId(0);
            } else {
                 // 접속이력ID가 있는 경우만.
                 if( svo.getLoginHistId() > 0  ) {
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
                	 
                	 requestMap.put("loginHistId", svo.getLoginHistId());
                	 
                	 //long jobHistId = adminLogDao.selectAdminLogJobHistId();
	                 //requestMap.put("jobHistId", jobHistId);
                	 
                	 try{
		                 requestMap.put("jobRequestUrl", request.getRequestURI()); /* 작업요청 URL */
		                 if(sb != null){
		                	 if(sb.toString().length() > 1500){
		                		requestMap.put("jobRequestParam", sb.toString().substring(0, 1500)); /* 작업요청 Parameter */
		                	 }else{
		                		 requestMap.put("jobRequestParam", sb.toString()); /* 작업요청 Parameter */
		                	 }
		                 }else{
		                	 requestMap.put("jobRequestParam", ""); /* 작업요청 Parameter */
		                 }
		                 requestMap.put("insEmpNo", svo.getOrigEmpNo());
                	 }catch(Exception e){
                		 System.out.println(" Exception " + sb.toString());
                	 }
	                 
	                 adminLogDao.insertAdminlogJobHist(requestMap);

	                 // 프로젝트 전송요청인 경우
	                 if( requestUri.indexOf("/work/project/develop/createReqTrans") > -1 ) {
		                 // set attribute
		                 request.setAttribute("jobHistId", requestMap.get("jobHistId"));
	                 }

                     // 결재인 경우(내결재함 승인, 내결재함 3DVF 승인, 내결재함 그룹서버 승인, 내결재함 해외 승인, 내결재함 외부사용자 계정 승인)
	                 if( requestUri.indexOf("/work/electronic/approval/approvalConfirm") > -1 
	     	        		|| requestUri.indexOf("/work/electronic/approval/approvalVFConfirm") > -1 
	     	        		|| requestUri.indexOf("/work/electronic/approval/approvalGrpSrvConfirm") > -1 
	     	        		|| requestUri.indexOf("/work/electronic/approval/approvalOverseasConfirm") > -1 
	     	        		|| requestUri.indexOf("/work/electronic/approval/approvalExtraUserConfirm") > -1 
	     	        		) {
	                	 // 첨부파일 param 및 대리결재ID 
	                	 long proxyApprId = setAtchFile(requestMap);

	                	 if( proxyApprId != 0 ) {
		                     // 결재ID가 있는 경우 (자가승인 외)
		                     if( requestMap.get("apprSq") != null && !"".equals(requestMap.get("apprSq"))) {
		                    	 // 대리결재 등록
		                    	 adminLogDao.insertAdminLogProxyAppr(requestMap);
		                     }
	                	 }
	                 } else {
	                	 
	                 }
                 } 
            } 
        } catch(Exception e) {
        	
        }
        return true;
    }
    
    @SuppressWarnings("unchecked")
	@Override
	public void postHandle(HttpServletRequest request, HttpServletResponse response, Object handler, ModelAndView modelAndView) throws Exception {
    	HttpSession session = request.getSession();
        SessionVO svo = (SessionVO)session.getAttribute(PortalConstants.Key.SESSION_KEY);
        if( svo != null ) { // NULL 처리
	        long jobHistId = 0;
	        if(request.getAttribute("jobHistId") != null){
	        	jobHistId = (Long)request.getAttribute("jobHistId");
	        }
	    	 // 접속이력ID가 있는 경우만.
	        if( svo.getLoginHistId() > 0 && jobHistId > 0 ) {
		    	String requestUri = request.getRequestURI();
		    	// 개발/열람/전송 화면의 디비 복구 요청
		        if( requestUri.indexOf("/work/project/develop/createReqTrans") > -1 ) {
		        	 Map<String, Object> mvMap = modelAndView.getModel();
		        	 String apprSq = String.valueOf( mvMap.get("result") );
		        	 
		        	 // param
		        	 ObjectMapper mapper = new ObjectMapper();
		        	 Map<String, Object> reqApprInfo = null;
		        	 List<Map<String, Object>> approverList = null;
		        	 Map<String, Object> requestMap = new HashMap<String, Object>();
			       	 Enumeration<?> enumeration = request.getParameterNames();
			       	 while (enumeration.hasMoreElements()) {
			       		 String key = (String)enumeration.nextElement();
			       		 String[] values = request.getParameterValues(key);
		           		 if (values != null) {
		           			 if( "GET".equals(request.getMethod()) || values.length == 1 ) {
		           				requestMap.put(key, values[0]);
			           			// 결재  param
		           				if("reqApprInfo".equals(key)) {
		           					reqApprInfo = mapper.readValue((String)values[0], HashMap.class);
		           				}
		           				if("approverList".equals(key)) {
		           					approverList = mapper.readValue((String)values[0], ArrayList.class);
		           				}
		           			 }else {
		           				requestMap.put(key, values);
		           			 }
		           		 }
			       	 }
			       	 
			       	//hysims 진단 수정(2017-10-13) 
			       	String reqDistCode = "";
					if(reqApprInfo != null && reqApprInfo.containsKey("reqDistCode")) reqDistCode = String.valueOf(reqApprInfo.get("reqDistCode"));
					
					// 프로젝트 전송인 경우
					if ( reqDistCode.equals("06") || reqDistCode.equals("07")
							|| reqDistCode.equals("08") || reqDistCode.equals("09")
							|| reqDistCode.equals("42") ) {
						//hysims 진단 수정(2017-10-13)
						if(approverList != null && reqApprInfo.containsKey("empNo")){
							// 요청자와 결재자가 동일하면
							if( reqApprInfo.get("empNo").equals(approverList.get(0).get("approverEmpNo")) ) { 
								// param set
								requestMap.put("jobHistId", jobHistId);
								requestMap.put("apprSq", apprSq);
								requestMap.put("insEmpNo", svo.getOrigEmpNo());
								
								// 첨부파일 param 및 대리결재ID 
						    	long proxyApprId = setAtchFile(requestMap);
						    	if( proxyApprId != 0 ) {
						           	// 자가승인 대리결재 등록
						           	adminLogDao.insertAdminLogProxyAppr(requestMap);
						    	}
							}
						}
					}
				}
				request.setAttribute("jobHistId", 0);
	        }
        }
	}

    /**
     * 첨부파일 Setting
     * 
     * @param request
     * @return
     */
    @SuppressWarnings("unchecked")
	private long setAtchFile(Map<String, Object> requestMap) {
    	// 대리결재ID
    	long proxyApprId = adminLogDao.selectAdminLogProxyApprId();
   	 	requestMap.put("proxyApprId", proxyApprId);
   	 
    	// 첨부파일--
    	List<Object> atchFileList = null;
        try {
            ObjectMapper mapper = new ObjectMapper();
            atchFileList = mapper.readValue((String)requestMap.get("atchFileList"), ArrayList.class);
            
            if( atchFileList != null && atchFileList.size() > 0) {
           	 // 파일 경로
           	 String tempPath = configuration.getString("portal.file.upload.path") + configuration.getString("portal.file.upload.temp.path");
           	 String contPath = new StringBuilder().append(configuration.getString("portal.file.upload.path"))
           			 .append(configuration.getString("portal.file.upload.adminlog.appr.path"))
           			 .append(File.separatorChar)
           			 .append(proxyApprId)
           			 .append(configuration.getString("portal.file.upload.attach.path"))
           			 .toString();
           	 	// 첨부 파일 복사
                boolean atchFileCopy = PortalUtil.attachFileCopy(tempPath, contPath, atchFileList);
                // 복사 성공할 경우
                if (atchFileCopy) {
                    if( atchFileList.size() > 0) {
                        requestMap.putAll((Map<String, Object>)atchFileList.get(0));
                        requestMap.put("filePhysicsPath", contPath);
                    }
                    // 임시 파일 삭제
                    PortalUtil.attachFileDelete(tempPath, atchFileList);
                }
            }
            
        } catch (Exception e) {
        	return 0;
        }
        //-- 첨부파일
        return proxyApprId;
    }
    
    /**
     * ClientIP 정보 내용 입력
     * 
     * @param request
     * @return
     */
    @SuppressWarnings("unused")
	private String getClientIP(HttpServletRequest request) {
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
