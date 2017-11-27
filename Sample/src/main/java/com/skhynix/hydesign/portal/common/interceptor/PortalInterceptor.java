package com.skhynix.hydesign.portal.common.interceptor;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Enumeration;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Locale;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.apache.commons.configuration.Configuration;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.servlet.handler.HandlerInterceptorAdapter;
import org.springframework.web.servlet.i18n.SessionLocaleResolver;

import com.skhynix.hydesign.portal.biz.common.CommonService;
import com.skhynix.hydesign.portal.biz.login.LoginService;
import com.skhynix.hydesign.portal.common.constants.PortalConstants;
import com.skhynix.hydesign.portal.common.vo.SessionVO;

/**
 * interceptor
 * 
 * @author <a href="mailto:support@openit.co.kr">(주)오픈잇 | openit Inc.</a>
 * @version 1.00
 * @created 2014. 12. 24.
 */
public class PortalInterceptor extends HandlerInterceptorAdapter {

    /**
     * logger
     */
    
    private final Logger logger = LoggerFactory.getLogger(getClass());
     
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

    /**
     * Default constructor
     */
    public PortalInterceptor() {
        // Default constructor
    }

    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {

        HttpSession session = request.getSession();
        SessionVO svo = (SessionVO)session.getAttribute(PortalConstants.Key.SESSION_KEY);
        
        //팝업 js의 cache 갱신을 위해 셋팅
        String deployNo = (String)session.getAttribute("deployNo");
        if (deployNo == null) {
            deployNo = String.valueOf(System.currentTimeMillis());
            session.setAttribute("deployNo", deployNo);
        }

        // 세션 허용인 경우
        String requestUri = request.getRequestURI();

        if (   requestUri.startsWith("/common")
            || requestUri.startsWith("/login")
            || requestUri.startsWith("/ecs")
            || requestUri.startsWith("/aspera")
            || requestUri.startsWith("/cab")
            || requestUri.startsWith("/extraLogin")
            || requestUri.startsWith("/notice")) {
            return true;
        }

        // 세션이 유효한 경우
        if (svo != null && !"".equals(svo.getEmpNo())) {
            //aspera전송일경우 (권한필요)
            if (requestUri.startsWith("/aspera")) {
                return true;
            }
            // 권한 허용인 경우
            if (requestUri.startsWith("/main") || requestUri.startsWith("/my")) {
                session.setAttribute("sessGnbMenuCd", "");
                return true;
            }

            // 요청메뉴에 대한 권한체크
            String requestMenu = ""; // 요청메뉴
            String functionUri = "";

            if (requestUri.lastIndexOf("/") > 1) {
                requestMenu = requestUri.substring(0, requestUri.indexOf("/", 1));
                functionUri = requestUri.substring(requestUri.lastIndexOf("/") + 1, requestUri.length());
            }
            
            // 2017.06.01 운영자로그인, 운영자접속해제인 경우
            if (requestUri.startsWith("/admin/manage/adminlog") 
            		&& ( functionUri.startsWith("adminLogin") || functionUri.startsWith("adminLogOut") ) ){
            	return true;
            }
            
            // 2017.07.26 검색엔진조회결과로 링크클릭시 링크정보를 저장한다.
            if(request.getParameter("linkView") != null){
            	Map<String, Object> pMapLink = new HashMap<String, Object>();
            	String linkUrl = "http://design.skhynix.com" + requestUri + "?" + request.getQueryString();
            	
            	pMapLink.put("linkUrl", linkUrl);
            	pMapLink.put("sessEmpNo", svo.getEmpNo());
            	
            	commonService.createSearchLinkHist(pMapLink);
            	//등록처리
            }

            Map<String, Object> menuListMap = svo.getMenuListMap();
            List<Object> menuList = new ArrayList<Object>();

            if (menuListMap != null && !menuListMap.isEmpty()) {

                // 요청 메뉴별 사용자 권한 매핑 메뉴 세팅
                if (requestMenu.equals("/work") || requestMenu.equals("/working")) {
                    session.setAttribute("sessGnbMenuCd", "010000");
                    menuList = (List<Object>)menuListMap.get("010000");

                } else if (requestMenu.equals("/status")) {
                    session.setAttribute("sessGnbMenuCd", "030000");
                    menuList = (List<Object>)menuListMap.get("030000");

                } else if (requestMenu.equals("/support")) {
                    session.setAttribute("sessGnbMenuCd", "020000");
                    menuList = (List<Object>)menuListMap.get("020000");

                } else if (requestMenu.equals("/information")) {
                    session.setAttribute("sessGnbMenuCd", "021000");
                    menuList = (List<Object>)menuListMap.get("021000");

                } else if (requestMenu.equals("/tools")) {
                    session.setAttribute("sessGnbMenuCd", "021000");
                    menuList = (List<Object>)menuListMap.get("021000");

                } else if (requestMenu.equals("/community")) {
                    session.setAttribute("sessGnbMenuCd", "001000");
                    menuList = (List<Object>)menuListMap.get("001000");

                } else if (requestMenu.equals("/sdp")) {
                    session.setAttribute("sessGnbMenuCd", "100063");
                    menuList = (List<Object>)menuListMap.get("100063");

                } else if (requestMenu.equals("/admin")) {
                    session.setAttribute("sessGnbMenuCd", "000000");
                    menuList = (List<Object>)menuListMap.get("000000");

                } else {
                    session.setAttribute("sessGnbMenuCd", "");
                }

                Map<String, Object> menuMap;

                String checkMenu = "";
                if (requestUri.lastIndexOf("/") > 1) {
                    checkMenu = requestUri.substring(0, requestUri.lastIndexOf("/"));
                }

                if (menuList != null && !menuList.isEmpty()) {
                    // 요청 메뉴에 대한 권한 체크

                    boolean authCheck = false;

                    String sessUpdateAuthFg = "N";

                    for (int i = 0, len = menuList.size(); i < len; i++) {
                        menuMap = (Map<String, Object>)menuList.get(i);

                        String checkRequestMenu = "";

                        if ((String)menuMap.get("MENU_URL") != null) {
                            checkRequestMenu = (String)menuMap.get("MENU_URL");

                            if (checkRequestMenu.lastIndexOf("/") > 1) {
                                checkRequestMenu = checkRequestMenu.substring(0, checkRequestMenu.lastIndexOf("/"));
                            }
                        }

                        if (checkRequestMenu.startsWith(checkMenu)) {
                            authCheck = true;

                            session.setAttribute("sessMenuLevel", menuMap.get("MENU_LEVEL"));
                            session.setAttribute("sessMenuCd", menuMap.get("MENU_CD"));
                            session.setAttribute("sessParMenuCd", menuMap.get("PAR_MENU_CD"));

                            if (menuMap.get("UPDATE_AUTH_FG").equals("Y")) {
                                sessUpdateAuthFg = "Y";
                            }
                        }
                        
                        
                    }
                    
                    if(!authCheck && "/support/hisos/cadSw/".startsWith(checkMenu)) {
                    	authCheck = true;

                        session.setAttribute("sessMenuLevel", "0003");
                        session.setAttribute("sessMenuCd", "100026");
                        session.setAttribute("sessParMenuCd", "100016");                        

                        sessUpdateAuthFg = "Y";
                    }

                    if (sessUpdateAuthFg.equals("N")) {
                        if (functionUri.startsWith("create") || functionUri.startsWith("modify")
                            || functionUri.startsWith("remove")) {
                            response.sendError(401);
                            return false;
                        }
                    }

                    if (authCheck) {
                        session.setAttribute("sessUpdateAuthFg", sessUpdateAuthFg);
                        String ajaxFg = request.getHeader("AJAX");
                        if (!"true".equals(ajaxFg)) {
                        	Map<String, Object> pMap = new HashMap<String, Object>();
                        	String sessMenuCd = (String)session.getAttribute("sessMenuCd");
                        	
                        	pMap.put("url", requestUri);
                        	pMap.put("empNo", svo.getEmpNo());
                        	pMap.put("orgCd", svo.getOrgCd());
                        	pMap.put("menuCd", sessMenuCd);
                        	
                        	if(sessMenuCd != null)
                        		commonService.createItSystemUseage(pMap);
                        	//else 
                        		//logger.info("Failed Insert Usage ({}:{})", svo.getEmpNo(), requestUri);
                        }
                        
                        return true;
                    }
                }
            }

            // 권한 없음 페이지로 이동
            response.sendError(401);
            return false;

        } else {

            // sso 인증 페이지로 이동
            if (request.getMethod() == "GET") {
                //logger.info(">>>>>>>>>>>>>>>>>>>>>> PortalInterceptor getHeader(\"SM_SKHY\") : " + request.getHeader("SM_SKHY"));           
                String ssoEmpNo = request.getHeader("SM_SKHY");

                if (ssoEmpNo != null && !"".equals(ssoEmpNo)) {

                    login(request, response, ssoEmpNo);
                    
                    //SSO로그인 후 Post data가 소실되어, 메뉴의 대표URL로 보냄.
                    String url = requestUri;
                    Enumeration<String> params = request.getParameterNames();
                    if(params == null || !params.hasMoreElements()) {
                    
	                    String checkMenu = "";
	                    if (requestUri.lastIndexOf("/") > 1) {
	                        checkMenu = requestUri.substring(0, requestUri.lastIndexOf("/"));
	                    	
	                    	svo = (SessionVO)session.getAttribute(PortalConstants.Key.SESSION_KEY);
		                    Map<String, Object> menuListMap = svo.getMenuListMap();
		                    Iterator<String> menuKeys = menuListMap.keySet().iterator();
		                    List<Object> menuList = null;
		                    Map<String, Object> menuMap = null;
		                    String menuUrl = "";
		                    
		                    while(menuKeys.hasNext()) {
		                    	menuList = (List<Object>)menuListMap.get(menuKeys.next());

		                    	for (int i = 0, len = menuList.size(); i < len; i++) {
		                            menuMap = (Map<String, Object>)menuList.get(i);
		                            if ((String)menuMap.get("MENU_URL") != null) {
		                            	menuUrl = (String)menuMap.get("MENU_URL");                       	
		                            	if(menuUrl.startsWith(checkMenu)) {
		                            		url = menuUrl; //메뉴의 대표URL로 보냄.
		                            	}                                
		                            }
		                    	}                            
		                    }
	                    }
	                    
                    } else {
                    	
                    	//메일 등 외부 링크를 통해 오는 경우,
	                    String strParam = "";
	                    while(params.hasMoreElements()) {
	                        String name = (String)params.nextElement();
	                        String value = request.getParameter(name);
	                        strParam += name + "=" + value + "&";
	                    }
	                    if(strParam.length() > 0) {
	                    	url += "?" + strParam;
	                    }
                    }
                    
                    //경로가 절대경로로 시작되지 않을 경우 sso 권한제한 페이지 호출 (2017-10-13) 
                    if(!url.startsWith("/")){
                    	response.sendRedirect(configuration.getString("portal.sso.reject.url"));
                    }else{
                    	response.sendRedirect(url);
                    }
                    
                } else {
                    // 시스템의 로그인 사용자 정보 없을 경우, sso 권한제한 페이지 호출
                    response.sendRedirect(configuration.getString("portal.sso.reject.url"));
                }
            } else if ("xmlhttprequest".equals(request.getHeader("X-Requested-With").toLowerCase(Locale.ENGLISH))) {
                response.sendError(999);
            } else {
                // 시스템의 로그인 사용자 정보 없을 경우, sso 권한제한 페이지 호출
                response.sendRedirect(configuration.getString("portal.sso.reject.url"));
            }
            return false;
        }

    }

    private void login(HttpServletRequest request, HttpServletResponse response, String ssoEmpNo) throws Exception {

        Map<String, Object> pMap = new HashMap<String, Object>();

        pMap.put("sessEmpNo", ssoEmpNo);

        // 사용자 정보 조회 
        Map<String, Object> empMap = loginService.searchEmp(pMap);

        if (empMap != null && !empMap.isEmpty()) {

            HttpSession session = request.getSession();
            session.setAttribute(SessionLocaleResolver.LOCALE_SESSION_ATTRIBUTE_NAME, Locale.KOREA);
            SessionVO svo = new SessionVO();

            int orgLevel = 4;
            String groupCd = (String)empMap.get("ORG_CD");
            String groupNm = (String)empMap.get("ORG_NM");

            if (empMap.containsKey("ORG_LEVEL") && empMap.get("ORG_LEVEL") != null
                && !("").equals(empMap.get("ORG_LEVEL"))
                && orgLevel < ((BigDecimal)empMap.get("ORG_LEVEL")).intValue()) {
                //                if (empMap.containsKey("ORG_LEVEL") && orgLevel < ((BigDecimal)empMap.get("ORG_LEVEL")).intValue()) {
                pMap.put("orgCd", groupCd);
                pMap.put("orgLevel", orgLevel);

                // 4레벨 조직 조회
                Map orgMap = loginService.searchOrg(pMap);
                if (orgMap != null && !orgMap.isEmpty()) {
                    groupCd = (String)orgMap.get("ORG_CD");
                    groupNm = (String)orgMap.get("ORG_NM");
                }
            }

            // 매핑 메뉴 리스트 맵
            Map<String, Object> menuListMap = new HashMap<String, Object>();

            // 매핑 메뉴 리스트
            List<Object> authMappingMenuList;

            //clientIp
            String clientIp = getClientIP(request);
            pMap.put("remoteHost", clientIp);
            pMap.put("subRemoteHost",
                     clientIp.split("\\.")[0] + "." + clientIp.split("\\.")[1] + "." + clientIp.split("\\.")[2]);

            //해외 IP 체크
            Map<String, Object> ipMap = loginService.searchGlobalIpAcessesCheck(pMap);

            // gnb 메뉴 리스트
            List<Object> gnbMenuList = null;

            if (null == ipMap) {
                //국내
                session.setAttribute(SessionLocaleResolver.LOCALE_SESSION_ATTRIBUTE_NAME, Locale.KOREA);
                gnbMenuList = loginService.searchGnbMenuList(pMap);
            } else {
                //해외
                session.setAttribute(SessionLocaleResolver.LOCALE_SESSION_ATTRIBUTE_NAME, Locale.US);
                gnbMenuList = loginService.searchGlobalGnbMenuList(pMap);
                svo.setIpDistCd((String)ipMap.get("IP_DIST_CODE"));
            }

            Map<String, Object> gnbMenuMap;
            for (int i = 0, len = gnbMenuList.size(); i < len; i++) {
                gnbMenuMap = (Map<String, Object>)gnbMenuList.get(i);

                String menuCd = (String)gnbMenuMap.get("MENU_CD");

                pMap.put("menuCd", menuCd);

                if (null == ipMap) {
                    //국내
                    authMappingMenuList = loginService.searchAuthMappingMenuList(pMap);
                } else {
                    //해외
                    authMappingMenuList = loginService.searchGlobalAuthMappingMenuList(pMap);
                }

                menuListMap.put(menuCd, authMappingMenuList);

                if (menuCd.equals("000000")) {
                    String serverName = System.getProperty("weblogic.Name");
                    svo.setServerDist(serverName);
                    svo.setAdminAuthYn("Y");
                }
            }

            // 시스템의 사용자 정보가 있는 경우
            svo.setOrigEmpNo(ssoEmpNo);
            svo.setEmpNo(ssoEmpNo);
            svo.setEmpNm((String)empMap.get("EMP_NM"));
            svo.setOrgCd((String)empMap.get("ORG_CD"));
            svo.setOrgNm((String)empMap.get("ORG_NM"));
            svo.setJtitCd((String)empMap.get("JTIT_CD"));
            svo.setGroupCd(groupCd);
            svo.setGroupNm(groupNm);

            svo.setGnbMenuList(gnbMenuList);
            svo.setMenuListMap(menuListMap);

            // 세션정보에 직위명, 직급명((직급코드추가))
            svo.setJtitNm((String)empMap.get("JTIT_NM"));
            svo.setJpstnCd((String)empMap.get("JPSTN_CD"));
            svo.setJpstnNm((String)empMap.get("JPSTN_NM"));

            String ipAddr = getClientIP(request);
            ipAddr = ipAddr == null ? "" : ipAddr;
            svo.setIpAddress(ipAddr);

            if (svo.getOrgCd() == null) {
                svo.setOrgCd("");
            }
            if (svo.getGroupCd() == null) {
                svo.setGroupCd("");
            }

            int smCnt = commonService.searchSupportManagerCount(pMap);

            if (smCnt > 0) {
                svo.setSupportManagerYn("Y");
            } else {
                svo.setSupportManagerYn("N");
            }

            loginService.createLog(svo);

            // session에 사용자 정보 셋팅
            session.setAttribute(PortalConstants.Key.SESSION_KEY, svo);
        } else {
            response.sendRedirect(configuration.getString("portal.sso.reject.url"));
        }
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
