package com.skhynix.hydesign.portal.common.resolver;

import java.util.Enumeration;
import java.util.HashMap;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.core.MethodParameter;
import org.springframework.web.bind.support.WebDataBinderFactory;
import org.springframework.web.context.request.NativeWebRequest;
import org.springframework.web.method.support.HandlerMethodArgumentResolver;
import org.springframework.web.method.support.ModelAndViewContainer;
import org.springframework.web.servlet.support.RequestContextUtils;

import com.skhynix.hydesign.portal.biz.login.LoginController;
import com.skhynix.hydesign.portal.common.constants.PortalConstants;
import com.skhynix.hydesign.portal.common.vo.SessionVO;

/**
 * <pre>
 * Parameter Mapping Resolver
 * Object requestMap 형태의 파라미터로 받는 요청에 대해 처리
 * </pre>
 * 
 * @author <a href="mailto:support@openit.co.kr">(주)오픈잇 | openit Inc.</a>
 * @version 1.00
 * @created 2014. 12. 24.
 */
public class ParameterMappingResolver implements HandlerMethodArgumentResolver {

    /**
     * logger
     */
    private final Logger logger = LoggerFactory.getLogger(getClass());

    /**
     * requestMap 명 정의
     */
    private final String REQUEST_MAP = "requestMap";

    /**
     * Default Constructor
     */
    public ParameterMappingResolver() {
        // Default Constructor
    }

    @Override
    public Object resolveArgument(MethodParameter methodParameter,
                                  ModelAndViewContainer modelAndViewContainer,
                                  NativeWebRequest webRequest,
                                  WebDataBinderFactory webDataBinderFactory) throws Exception {

        HttpServletRequest request = (HttpServletRequest)webRequest.getNativeRequest();

        if (REQUEST_MAP.equals(methodParameter.getParameterName())) {

            Map<String, Object> requestMap = new HashMap<String, Object>();

            Enumeration<?> enumeration = request.getParameterNames();
            while (enumeration.hasMoreElements()) {
                String key = (String)enumeration.nextElement();
                String[] values = request.getParameterValues(key);

                if (values != null) {
                    requestMap.put(key, (request.getMethod() == "GET" || values.length == 1) ? values[0] : values);
                }
            }

            // locale 정보 추가
            requestMap.put("locale", RequestContextUtils.getLocale(request));

            // session 정보 추가
            HttpSession session = request.getSession();
            if (session.getAttribute(PortalConstants.Key.SESSION_KEY) != null) {

                SessionVO svo = (SessionVO)session.getAttribute(PortalConstants.Key.SESSION_KEY);

                requestMap.put("sessEmpNo", svo.getEmpNo());
                requestMap.put("sessEmpNm", svo.getEmpNm());
                requestMap.put("sessOrgCd", svo.getOrgCd());
                requestMap.put("sessOrgNm", svo.getOrgNm());
                requestMap.put("sessJtitNm", svo.getJtitNm());
                requestMap.put("sessJtitCd", svo.getJtitCd());
                requestMap.put("sessGroupCd", svo.getGroupCd());
                requestMap.put("sessGroupNm", svo.getGroupNm());
                requestMap.put("sessIpDistCd", svo.getIpDistCd());
                requestMap.put("sessSupportManagerYn", svo.getSupportManagerYn());
                requestMap.put("remoteAddr", svo.getIpAddress());
            }else{
            	// remote 정보 추가
                requestMap.put("remoteAddr", getClientIP(request));
            }
            
            // 수정 권한 정보 추가
            requestMap.put("sessUpdateAuthFg", session.getAttribute("sessUpdateAuthFg"));

            if (logger.isDebugEnabled()) {
                logger.debug("{} : {}", REQUEST_MAP, requestMap);
            }

            return requestMap;
        }

        return null;
    }

    @Override
    public boolean supportsParameter(MethodParameter methodParameter) {
        if (REQUEST_MAP.equals(methodParameter.getParameterName())) {
            return true;
        }
        return false;
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
