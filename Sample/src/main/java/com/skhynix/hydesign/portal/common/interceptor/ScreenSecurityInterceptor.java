package com.skhynix.hydesign.portal.common.interceptor;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.servlet.handler.HandlerInterceptorAdapter;

import com.skhynix.hydesign.portal.biz.common.CommonService;
import com.skhynix.hydesign.portal.biz.security.ScreenSecurityService;
import com.skhynix.hydesign.portal.common.constants.PortalConstants;
import com.skhynix.hydesign.portal.common.vo.SessionVO;

/**
 * 화면 보안에 대한 Interceptor
 * 
 * @author SK Hynix
 * @version 1.00
 * @created 2015. 12. 18.
 */
public class ScreenSecurityInterceptor extends HandlerInterceptorAdapter {
	
    /**
     * 화면보안 Service
     */
    @Autowired
    ScreenSecurityService securityService;
    
    protected static final String DEFAULT_SECURITY_OPTION = "00010600";
    
    /**
     * Default constructor
     */
    public ScreenSecurityInterceptor() {
        // Default constructor
    }

    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
    	
    	HttpSession session = request.getSession();
        SessionVO svo = (SessionVO)session.getAttribute(PortalConstants.Key.SESSION_KEY);
    	
        Map<String, Object> pMap = new HashMap<String, Object>();
        pMap.put("sessEmpNo", svo.getEmpNo());

    	boolean allowRemoteFg = securityService.searchAllowRemoteFg(pMap);
    	boolean nonSecuFg = securityService.searchNonSecurityFg(pMap);
    	
    	if(allowRemoteFg) session.setAttribute("remoteYn", "Y");	// 원격 허용
    	else session.setAttribute("remoteYn", "N");					// 원격 차단
    	
    	if(nonSecuFg) session.setAttribute("nonSecuYn", "Y");	// 화면보안 해제 
    	else session.setAttribute("nonSecuYn", "N");			// 화면보안 적용 
    	
        return true;
    }
}
