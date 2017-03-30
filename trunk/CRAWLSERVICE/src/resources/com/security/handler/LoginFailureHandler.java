package resources.com.security.handler;


import java.io.IOException;
import java.util.HashMap;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.authentication.SimpleUrlAuthenticationFailureHandler;

import crawl.lgn.service.LoginService;
import crawl.vo.VoUserInfo;
import resources.com.util.StringUtils;


public class LoginFailureHandler extends SimpleUrlAuthenticationFailureHandler{

	private String loginidname;         // 로그인 id값이 들어오는 input 태그 name
    private String loginpasswdname;     // 로그인 password 값이 들어오는 input 태그 name
    private String loginredirectname;       // 로그인 성공시 redirect 할 URL이 지정되어 있는 input 태그 name
    private String exceptionmsgname;        // 예외 메시지를 request의 Attribute에 저장할 때 사용될 key 값
    private String defaultFailureUrl;       // 화면에 보여줄 URL(로그인 화면)
    private String failcnt;       // 화면에 보여줄 URL(로그인 화면)
    
    public LoginFailureHandler(){
        this.loginidname = "user_id";
        this.loginpasswdname = "user_pw";
        this.loginredirectname = "returnUrl";
        this.exceptionmsgname = "retMessage";
        this.defaultFailureUrl = "/usr/lgn/login.do";
        this.failcnt = "0";
    }

    public String getFailcnt() {
		return failcnt;
	}

	public void setFailcnt(String failcnt) {
		this.failcnt = failcnt;
	}

	public String getLoginidname() {
        return loginidname;
    }

    public void setLoginidname(String loginidname) {
        this.loginidname = loginidname;
    }

    public String getLoginpasswdname() {
        return loginpasswdname;
    }

    public void setLoginpasswdname(String loginpasswdname) {
        this.loginpasswdname = loginpasswdname;
    }
 
    public String getExceptionmsgname() {
        return exceptionmsgname;
    }
 
    public String getLoginredirectname() {
        return loginredirectname;
    }

    public void setLoginredirectname(String loginredirectname) {
        this.loginredirectname = loginredirectname;
    }

    public void setExceptionmsgname(String exceptionmsgname) {
        this.exceptionmsgname = exceptionmsgname;
    }
 
    public String getDefaultFailureUrl() {
        return defaultFailureUrl;
    }

    public void setDefaultFailureUrl(String defaultFailureUrl) {
        this.defaultFailureUrl = defaultFailureUrl;
    }
    
    @Autowired
	LoginService loginService;
    
	@Override
    public void onAuthenticationFailure(HttpServletRequest request, HttpServletResponse response, AuthenticationException exception) throws IOException, ServletException{
 
		try {
			String sErrorMsg = "";
    		// Request 객체의 Attribute에 사용자가 실패시 입력했던 로그인 ID와 비밀번호를 저장해두어 로그인 페이지에서 이를 접근하도록 한다
            String userId = request.getParameter(loginidname);
            String loginpasswd = request.getParameter(loginpasswdname);
            String loginRedirect = request.getParameter(loginredirectname);

			//System.out.println("LoginController:createUserInfoSession:"+CryptUtil.Encrypt(StringUtils.convertSalting("admin","admin1")));
			HashMap<String, String> hMap = new HashMap<String,String>();
			hMap.put("userId", userId);
			
			//로그인 아이디가 회원테이블에 있는지 확인한다.
			VoUserInfo voUserInfo = this.loginService.selectUserInfoByUserId(hMap);
 
			//존재하는 회원일 경우 로그인 실패 카운터에 추가.
			if(voUserInfo!=null){
				//만약 USE_YN 필드가 "Y" 가 아닐 경우 사용중지되었음을 알려준다. 
				if(!"Y".equals(voUserInfo.getUse_yn())){
					sErrorMsg = "access_use_no";
				}else{
					sErrorMsg = "passwd_wrong";
					failcnt = StringUtils.getNull(voUserInfo.getAccess_fail_cnt()).toString();
					if("".equals(failcnt)){
						failcnt = "1"; 
					}else{
						failcnt = Integer.toString(Integer.parseInt(failcnt) + 1);
					}
					//비밀번호 입력 오류의 경우 총 5회까지 업데이트 한다.
					if(Integer.parseInt(failcnt) <=5){
						//비밀번호 오류횟수가 총 5회일 경우 해당 아이디를 중지한다.
						if("5".equals(failcnt)){
							voUserInfo.setUse_yn("N");
						}
						this.loginService.updateAccessFailCntByUserId(voUserInfo);
					}else{
						failcnt = "5";
					}
				}
			}else{
				sErrorMsg = "notMember";
			}

			System.out.println(loginidname+ " : " + userId);
			System.out.println(loginpasswdname+ " : " + loginpasswd);
			System.out.println(loginredirectname+ " : " + loginRedirect);
			System.out.println(exceptionmsgname+ " : " + exception.getMessage());
			System.out.println("sErrorMsg : " + sErrorMsg);
			System.out.println("failcnt : " + failcnt);
			System.out.println("defaultFailureUrl : " + defaultFailureUrl);

            request.setAttribute(loginidname, userId);
            request.setAttribute(loginpasswdname, loginpasswd);
            request.setAttribute(loginredirectname, loginRedirect);

            // Request 객체의 Attribute에 예외 메시지 저장
            request.setAttribute(exceptionmsgname, sErrorMsg);
            request.setAttribute("failcnt", failcnt);

            request.getRequestDispatcher(defaultFailureUrl).forward(request, response);
            
            //response.sendRedirect(defaultFailureUrl);
            /*
            super.setDefaultFailureUrl(defaultFailureUrl);
            super.setUseForward(true);
            super.onAuthenticationFailure(request, response, exception);
            */
    	} catch (Exception e) {
			logger.error("=+=+=+=+=+=+=+=+=+=+=+=+=+= EXCEPTION START +=+=+=+=+=+=+=+=+=+=+=+=");
			logger.error("EXCEPTION 위치 : "+ this.getClass().getName() +" onAuthenticationFailure");
			logger.error("EXCEPTION 내용 : \n" + e.toString());
			logger.error("=+=+=+=+=+=+=+=+=+=+=+=+=+= EXCEPTION END   +=+=+=+=+=+=+=+=+=+=+=+=");
		}
    	
    }
}