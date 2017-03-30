package resources.com.security.handler;

import java.io.IOException;
import java.util.HashMap;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.web.DefaultRedirectStrategy;
import org.springframework.security.web.RedirectStrategy;
import org.springframework.security.web.WebAttributes;
import org.springframework.security.web.authentication.SavedRequestAwareAuthenticationSuccessHandler;
import org.springframework.security.web.savedrequest.HttpSessionRequestCache;
import org.springframework.security.web.savedrequest.RequestCache;
import org.springframework.security.web.savedrequest.SavedRequest;
import org.springframework.util.StringUtils;

import crawl.lgn.service.LoginService;
import crawl.mpg.service.MyPageService;
import crawl.vo.VoUserInfo;
import resources.com.util.StringUtil;

public class LoginSuccessHandler extends SavedRequestAwareAuthenticationSuccessHandler {
	
	private RequestCache requestCache = new HttpSessionRequestCache();

	private String targetUrlParameter;

	private String defaultUrl;

	private boolean useReferer;

	private RedirectStrategy redirectStrategy = new DefaultRedirectStrategy();
	
	@Autowired
	LoginService loginService;
	
	@Autowired
	MyPageService myPageService;
	
	/*
	 * context-security.xml bean property 에 선언을 해도 되고
	 * 아래처럼 생성 함수에 선언을 해도 된다. 
	 **/
	/*
	public LoginSuccessHandler(){
        targetUrlParameter = "returnUrl";
        defaultUrl = "/mng/man/index.do";
        useReferer = false;
    }	
	*/
    public String getTargetUrlParameter() {
        return targetUrlParameter;
    }

    public void setTargetUrlParameter(String targetUrlParameter) {
        this.targetUrlParameter = targetUrlParameter;
    }

    public String getDefaultUrl() {
        return defaultUrl;
    }

    public void setDefaultUrl(String defaultUrl) {
        this.defaultUrl = defaultUrl;
    }

    public boolean isUseReferer() {
        return useReferer;
    }

    public void setUseReferer(boolean useReferer) {
        this.useReferer = useReferer;
    }
    
	@Override
	public void onAuthenticationSuccess( HttpServletRequest request
									   , HttpServletResponse response
									   , Authentication auth)
			throws IOException, ServletException {
		
		try {
			
			// Authentication 파라미터로 넘어온 로그인 아이디
			String sUserId = StringUtil.getNull(auth.getName());
						
			HashMap<String, String> hMap = new HashMap<String,String>();
			hMap.put("userId", sUserId);
			
			//로그인 아이디가 회원테이블에 있는지 확인한다.
			VoUserInfo voUserInfo = this.loginService.selectUserInfoByUserId(hMap);
			
			//존재하는 회원일 경우 로그인 실패 카운터 초기화.
			if(voUserInfo!=null){
				this.loginService.updateAccessFailCntInitByUserId(voUserInfo);
			}
 

			request.getSession().setAttribute("userInfoVO", voUserInfo);
			
			clearAuthAttributes(request);
			
			int intRedirectStrategy = decideRedirectStrategy(request, response);

			//System.out.println("intRedirectStrategy : " + intRedirectStrategy);

			switch (intRedirectStrategy) {
			case 1:
				useTargetUrl(request, response);
				break;
			case 2:
				useSessionUrl(request, response);
				break;
			case 3:
				useRefererUrl(request, response);
				break;
			default:
				useDefaultUrl(request, response);
			}
			
			
			//super.setAlwaysUseDefaultTargetUrl(true);
			//super.setDefaultTargetUrl(targetUrl);
			//super.onAuthenticationSuccess(request, response, auth);

		} catch (Exception e) {
			logger.error("=+=+=+=+=+=+=+=+=+=+=+=+=+= EXCEPTION START +=+=+=+=+=+=+=+=+=+=+=+=");
			logger.error("EXCEPTION 위치 : " + this.getClass().getName()
					+ " onAuthenticationSuccess");
			logger.error("EXCEPTION 내용 : \n" + e.toString());
			logger.error("=+=+=+=+=+=+=+=+=+=+=+=+=+= EXCEPTION END   +=+=+=+=+=+=+=+=+=+=+=+=");
		}
	}
	
	private void clearAuthAttributes(HttpServletRequest request) {
        HttpSession session = request.getSession(false);
 
        if (session == null) {
            return;
        }
 
        session.removeAttribute(WebAttributes.AUTHENTICATION_EXCEPTION);
    }
 
	/**
	 * 인증 성공후 어떤 URL로 redirect 할지를 결정한다
	 * 판단 기준은 targetUrlParameter 값을 읽은 URL이 존재할 경우 그것을 1순위
	 * 1순위 URL이 없을 경우 Spring Security가 세션에 저장한 URL을 2순위
	 * 2순위 URL이 없을 경우 Request의 REFERER를 사용하고 그 REFERER URL이 존재할 경우 그 URL을 3순위
	 * 3순위 URL이 없을 경우 Default URL을 4순위로 한다
	 * @param request
	 * @param response
	 * @return   1 : targetUrlParameter 값을 읽은 URL
	 *            2 : Session에 저장되어 있는 URL
	 *            3 : referer 헤더에 있는 url
	 *            0 : default url
	 */
	private int decideRedirectStrategy(HttpServletRequest request, HttpServletResponse response) {
		int result = 0;

		SavedRequest savedRequest = requestCache.getRequest(request, response);

		if (!"".equals(targetUrlParameter)) {
			String targetUrl = request.getParameter(targetUrlParameter);
			if (StringUtils.hasText(targetUrl)) {
				result = 1;
			} else {
				if (savedRequest != null) {
					result = 2;
				} else {
					String refererUrl = request.getHeader("REFERER");
					if (useReferer && StringUtils.hasText(refererUrl)) {
						result = 3;
					} else {
						result = 0;
					}
				}
			}

			return result;
		}

		if (savedRequest != null) {
			result = 2;
			return result;
		}

		String refererUrl = request.getHeader("REFERER");
		if (useReferer && StringUtils.hasText(refererUrl)) {
			result = 3;
		} else {
			result = 0;
		}

		return result;
	}

	private void useTargetUrl(HttpServletRequest request,
			HttpServletResponse response) throws IOException {
		SavedRequest savedRequest = requestCache.getRequest(request, response);
		if (savedRequest != null) {
			requestCache.removeRequest(request, response);
		}
		String targetUrl = request.getParameter(targetUrlParameter);
		
		System.out.println("useTargetUrl targetUrl : " + targetUrl);
		redirectStrategy.sendRedirect(request, response, targetUrl);
	}

	private void useSessionUrl(HttpServletRequest request,
			HttpServletResponse response) throws IOException {
		SavedRequest savedRequest = requestCache.getRequest(request, response);
		String targetUrl = savedRequest.getRedirectUrl();

		System.out.println("useSessionUrl targetUrl : " + targetUrl);
		redirectStrategy.sendRedirect(request, response, targetUrl);
	}

	private void useRefererUrl(HttpServletRequest request,
			HttpServletResponse response) throws IOException {
		String targetUrl = request.getHeader("REFERER");
		System.out.println("useRefererUrl targetUrl : " + targetUrl);
		redirectStrategy.sendRedirect(request, response, targetUrl);
	}

	private void useDefaultUrl(HttpServletRequest request,
			HttpServletResponse response) throws IOException {
		System.out.println("useDefaultUrl targetUrl : " + defaultUrl);
		redirectStrategy.sendRedirect(request, response, defaultUrl);
	}
}