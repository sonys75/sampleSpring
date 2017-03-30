package resources.com.interceptors;

import java.util.Collection;
import java.util.Iterator;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.apache.log4j.Logger;
import org.springframework.security.access.hierarchicalroles.RoleHierarchy;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.AuthorityUtils;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.handler.HandlerInterceptorAdapter;

import crawl.vo.VoCommon;
import crawl.vo.VoUserInfo;
import resources.com.service.SessionMessage;
import resources.com.util.StringUtils;
import resources.com.util.WebUtil;
import resources.com.vo.ComUserInfo;

public class AuthenticInterceptor extends HandlerInterceptorAdapter {
	/*
	private Set<String> permittedURL;
	private Set<String> sslURL;
	
	public void setSslURL(Set<String> sslURL) {
		this.sslURL = sslURL;
	}
	
	public void setPermittedURL(Set<String> permittedURL) {
		this.permittedURL = permittedURL;
	}
	*/
	@Resource(name="roleHierarchy")
	private RoleHierarchy roleHierarchy;
	
	public Logger logger = Logger.getLogger(this.getClass());
	public StringBuffer sbDebugLog = new StringBuffer();
	
	/**
	 * 세션에 계정정보(LoginVO)가 있는지 여부로 인증 여부를 체크한다.
	 * 계정정보(LoginVO)가 없다면, 로그인 페이지로 이동한다.
	 */
	@Override
	public boolean preHandle(HttpServletRequest request,
			HttpServletResponse response, Object handler) throws Exception {	
		
		String requestScheme = StringUtils.getNull(request.getScheme()); //요청 프로토콜
		String sSvrNm = StringUtils.getNull(request.getServerName());
		int nPort = request.getServerPort();
		String requestURI = StringUtils.getNull(request.getRequestURI()); //요청 URI
		String requestReferer = StringUtils.getNull(request.getHeader("REFERER"));
		String requestUserAgent = request.getHeader("User-Agent").toLowerCase();
		String requestCharacterEncoding = request.getCharacterEncoding();
		String requestContentType = request.getContentType();
		String requestMethod = request.getMethod();
		String requestRemoteAddr = WebUtil.getClientIP(request);
		
		boolean bMoblieConnect = false;
		
		if(requestUserAgent.matches("(?i).*((android|bb\\d+|meego).+mobile|avantgo|bada\\/|blackberry|blazer|compal|elaine|fennec|hiptop|iemobile|ip(hone|od)|iris|kindle|lge |maemo|midp|mmp|mobile.+firefox|netfront|opera m(ob|in)i|palm( os)?|phone|p(ixi|re)\\/|plucker|pocket|psp|series(4|6)0|symbian|treo|up\\.(browser|link)|vodafone|wap|windows ce|xda|xiino).*") || requestUserAgent.substring(0,4).matches("(?i)1207|6310|6590|3gso|4thp|50[1-6]i|770s|802s|a wa|abac|ac(er|oo|s\\-)|ai(ko|rn)|al(av|ca|co)|amoi|an(ex|ny|yw)|aptu|ar(ch|go)|as(te|us)|attw|au(di|\\-m|r |s )|avan|be(ck|ll|nq)|bi(lb|rd)|bl(ac|az)|br(e|v)w|bumb|bw\\-(n|u)|c55\\/|capi|ccwa|cdm\\-|cell|chtm|cldc|cmd\\-|co(mp|nd)|craw|da(it|ll|ng)|dbte|dc\\-s|devi|dica|dmob|do(c|p)o|ds(12|\\-d)|el(49|ai)|em(l2|ul)|er(ic|k0)|esl8|ez([4-7]0|os|wa|ze)|fetc|fly(\\-|_)|g1 u|g560|gene|gf\\-5|g\\-mo|go(\\.w|od)|gr(ad|un)|haie|hcit|hd\\-(m|p|t)|hei\\-|hi(pt|ta)|hp( i|ip)|hs\\-c|ht(c(\\-| |_|a|g|p|s|t)|tp)|hu(aw|tc)|i\\-(20|go|ma)|i230|iac( |\\-|\\/)|ibro|idea|ig01|ikom|im1k|inno|ipaq|iris|ja(t|v)a|jbro|jemu|jigs|kddi|keji|kgt( |\\/)|klon|kpt |kwc\\-|kyo(c|k)|le(no|xi)|lg( g|\\/(k|l|u)|50|54|\\-[a-w])|libw|lynx|m1\\-w|m3ga|m50\\/|ma(te|ui|xo)|mc(01|21|ca)|m\\-cr|me(rc|ri)|mi(o8|oa|ts)|mmef|mo(01|02|bi|de|do|t(\\-| |o|v)|zz)|mt(50|p1|v )|mwbp|mywa|n10[0-2]|n20[2-3]|n30(0|2)|n50(0|2|5)|n7(0(0|1)|10)|ne((c|m)\\-|on|tf|wf|wg|wt)|nok(6|i)|nzph|o2im|op(ti|wv)|oran|owg1|p800|pan(a|d|t)|pdxg|pg(13|\\-([1-8]|c))|phil|pire|pl(ay|uc)|pn\\-2|po(ck|rt|se)|prox|psio|pt\\-g|qa\\-a|qc(07|12|21|32|60|\\-[2-7]|i\\-)|qtek|r380|r600|raks|rim9|ro(ve|zo)|s55\\/|sa(ge|ma|mm|ms|ny|va)|sc(01|h\\-|oo|p\\-)|sdk\\/|se(c(\\-|0|1)|47|mc|nd|ri)|sgh\\-|shar|sie(\\-|m)|sk\\-0|sl(45|id)|sm(al|ar|b3|it|t5)|so(ft|ny)|sp(01|h\\-|v\\-|v )|sy(01|mb)|t2(18|50)|t6(00|10|18)|ta(gt|lk)|tcl\\-|tdg\\-|tel(i|m)|tim\\-|t\\-mo|to(pl|sh)|ts(70|m\\-|m3|m5)|tx\\-9|up(\\.b|g1|si)|utst|v400|v750|veri|vi(rg|te)|vk(40|5[0-3]|\\-v)|vm40|voda|vulc|vx(52|53|60|61|70|80|81|83|85|98)|w3c(\\-| )|webc|whit|wi(g |nc|nw)|wmlb|wonu|x700|yas\\-|your|zeto|zte\\-")) {
			bMoblieConnect = true;
		}
		/*
		localhost에서 테스트 하는 경우 0:0:0:0:0:0:0:1 값으로 넘어 오는 경우가 있다.
		이 값은 IPv6 에서 IPv4의 127.0.0.1 과 같은 값이다.
		Tomcat으로  개발시 이게 문제가 되는 경우 vm arguments에 -Djava.net.preferIPv4Stack=true 값을 넣어 주면 된다.
		*/
		if(requestURI.indexOf("/WEB-INF/jsp/cmm/lay/")==-1){
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
			sbDebugLog.append("\n >>> MobileConnect : "+ bMoblieConnect);
			
			logger.debug("\n=========================== REQUEST DEBUG START ============================"
						+ sbDebugLog.toString()
						+"\n=========================== REQUEST DEBUG END    ===========================");
			sbDebugLog.setLength(0);
		}

		if(!requestURI.contains("/syc/")){
			ComUserInfo comUserInfo = new ComUserInfo();
			
			Authentication auth = SecurityContextHolder.getContext().getAuthentication();
			StringBuffer sbUserAuth	= new StringBuffer();
			
			if(auth!=null){
				Object principal = auth.getPrincipal();
				String user_id = "";
				String user_nm = "";
				String user_auth = "";

				if(principal != null && principal instanceof ComUserInfo){
					user_id = ((ComUserInfo)principal).getUser_id();
					user_nm = ((ComUserInfo)principal).getUser_nm();
					
					@SuppressWarnings("unchecked")
					Collection<SimpleGrantedAuthority> authorities = (Collection<SimpleGrantedAuthority>)((ComUserInfo)principal).getAuthorities();
					
					Iterator<SimpleGrantedAuthority> itr = authorities.iterator();
				    while (itr.hasNext()) {
						SimpleGrantedAuthority grantAuth = (SimpleGrantedAuthority) itr.next();
						user_auth = grantAuth.getAuthority();
				    }
		
				    @SuppressWarnings("unchecked")
					Collection<GrantedAuthority> ga = (Collection<GrantedAuthority>) roleHierarchy.getReachableGrantedAuthorities(AuthorityUtils.createAuthorityList(new String[]{user_auth}));
		
				    String tempAuth = "";
				    Iterator<GrantedAuthority> itrGa = ga.iterator();
				    while (itrGa.hasNext()) {
				    	tempAuth = itrGa.next().toString();
				    	if(sbUserAuth.length()==0){
				    		sbUserAuth.append(tempAuth);
				    	}else{
				    		sbUserAuth.append(","+tempAuth);
				    	}
				    }
				    
				    comUserInfo.setUser_id(user_id);
				    comUserInfo.setUser_nm(user_nm);
				    comUserInfo.setUser_auth(user_auth);
				    comUserInfo.setUser_authorities(sbUserAuth.toString());
				}
			}
			
			HttpSession session = request.getSession(false);
			if(session != null){
				VoUserInfo voUserInfo = (VoUserInfo)session.getAttribute("userInfoVO");
				if(!"".equals(StringUtils.getNull(sbUserAuth))){
					voUserInfo.setUser_authorities(sbUserAuth.toString());
					request.getSession().setAttribute("userInfoVO", voUserInfo);
				}
			}
		}
		/*
		request.getSession().setAttribute("SvrNm", sSvrNm);
 
		boolean isPermittedURL = false; 
		boolean isSslURL = false; 
 
		// SSL 로 리다이렉션 해야하는 URL
		if(requestURI.indexOf(".do")>=0 && "http".equalsIgnoreCase(requestScheme)){
			for(Iterator<String> it = this.sslURL.iterator(); it.hasNext();){
				String urlPattern = request.getContextPath() + (String) it.next();
				if(Pattern.matches(urlPattern, requestURI)){// 정규표현식을 이용해서 요청 URI가 허용된 URL에 맞는지 점검함.
					isSslURL = true;
				}
			}

			//로컬 개발환경을 제외하고는 https 로 리다이렉션 하게 한다.
			//if(!"localhost".equalsIgnoreCase(sSvrNm) && !"127.0.0.1".equalsIgnoreCase(sSvrNm) && !"10.200.93.170".equalsIgnoreCase(sSvrNm)){
			if(!"localhost".equalsIgnoreCase(sSvrNm) && !"127.0.0.1".equalsIgnoreCase(sSvrNm)){
				//스키마가 http일 경우
				if(isSslURL){
					
					System.out.println(" +=+=+=+=+=+=+=+=+=+=+=+=+=+=+=+=+=+=+=+=+=+=+=+=+=+=+=+=+=+= ");
					System.out.println(" >>> requestScheme : "+ requestScheme);
					System.out.println(" >>> sSvrNm : "+ sSvrNm);
					System.out.println(" >>> nPort : "+ nPort);
					System.out.println(" >>> RefererURI : "+ refererURI);
					System.out.println(" >>> isSslURL : "+ isSslURL);
					System.out.println(" +=+=+=+=+=+=+=+=+=+=+=+=+=+=+=+=+=+=+=+=+=+=+=+=+=+=+=+=+=+= ");
					
					response.sendRedirect("https://"+sSvrNm+":40443"+requestURI);
		    		return false;
				}
			}
		}
		
		//로컬환경일 경우 인증서 로그인 페이지에서 오류가 발생함으로 해당 페이지는 별도의 인증페이지로 리다이렉션 하게 한다.
		if("localhost".equalsIgnoreCase(sSvrNm) || "127.0.0.1".equalsIgnoreCase(sSvrNm)){
			//RequestURI 가 인증서 페이지에 걸렸을 경우
			//if("/ca/lgn/login.do".equals(requestURI) || "/ca/crt/regist.do".equals(requestURI) || "/ca/crt/renew.do".equals(requestURI) || "/mm/mng/withdrawal.do".equals(requestURI)){
			if("/ca/lgn/login.do".equals(requestURI) || "/mm/mng/withdrawal.do".equals(requestURI)){
				response.sendRedirect(requestURI.replaceAll(".do", "_dev.do"));
	    		return false;
			}
		}
		
		// 로그인 없이 허용된 URL
		for(Iterator<String> it = this.permittedURL.iterator(); it.hasNext();){
			String urlPattern = request.getContextPath() + (String) it.next();
			if(Pattern.matches(urlPattern, requestURI)){// 정규표현식을 이용해서 요청 URI가 허용된 URL에 맞는지 점검함.
				isPermittedURL = true;
			}
		}
		
		//System.out.println(" >>> isPermittedURL : "+ isPermittedURL);
		

		if(!isPermittedURL){
			VoUserInfo userInfoVO = (VoUserInfo) ComUserDetailsHelper.getAuthenticatedUser();
			if(userInfoVO != null && !"".equals(StringUtils.getNull(userInfoVO.getGuardian_member_cd()))){
				return true;
			}else{
				//접속권한이 없을 경우 로그인 페이지로 리다이렉션 한다.
				
				System.out.println(" +=+=+=+=+=+=+=+=+=+=+=+=+=+=+=+=+=+=+=+=+=+=+=+=+=+=+=+=+=+= ");
				System.out.println(" >>> RequestURI : "+ requestURI);
				System.out.println(" >>> RequestReferer : "+ requestReferer);
				System.out.println(" >>> sSvrNm : "+ sSvrNm);
				System.out.println(" >>> isPermittedURL : "+ isPermittedURL);
				System.out.println(" +=+=+=+=+=+=+=+=+=+=+=+=+=+=+=+=+=+=+=+=+=+=+=+=+=+=+=+=+=+= ");
				
	    		response.sendRedirect("/ca/lgn/login.do");
	    		return false;
			}
		}else{
			return true;
		}
		*/
		return true;
	}
	
	public void postHandle(HttpServletRequest request, HttpServletResponse response, Object handler, ModelAndView modelAndView) throws Exception {

		String requestURI = StringUtils.getNull(request.getRequestURI()); //요청 URI

		if(!requestURI.contains("/syc/")){
			/*
			ComUserInfo comUserInfo = new ComUserInfo();
			
			Authentication auth = SecurityContextHolder.getContext().getAuthentication();
			StringBuffer sbUserAuth	= new StringBuffer();
			
			if(auth!=null){
				Object principal = auth.getPrincipal();
				String user_id = "";
				String user_nm = "";
				String user_auth = "";

				if(principal != null && principal instanceof ComUserInfo){
					user_id = ((ComUserInfo)principal).getUser_id();
					user_nm = ((ComUserInfo)principal).getUser_nm();
					
					@SuppressWarnings("unchecked")
					Collection<SimpleGrantedAuthority> authorities = (Collection<SimpleGrantedAuthority>)((ComUserInfo)principal).getAuthorities();
					
					Iterator<SimpleGrantedAuthority> itr = authorities.iterator();
				    while (itr.hasNext()) {
						SimpleGrantedAuthority grantAuth = (SimpleGrantedAuthority) itr.next();
						user_auth = grantAuth.getAuthority();
				    }
		
				    @SuppressWarnings("unchecked")
					Collection<GrantedAuthority> ga = (Collection<GrantedAuthority>) roleHierarchy.getReachableGrantedAuthorities(AuthorityUtils.createAuthorityList(new String[]{user_auth}));
		
				    String tempAuth = "";
				    Iterator<GrantedAuthority> itrGa = ga.iterator();
				    while (itrGa.hasNext()) {
				    	tempAuth = itrGa.next().toString();
				    	if(sbUserAuth.length()==0){
				    		sbUserAuth.append(tempAuth);
				    	}else{
				    		sbUserAuth.append(","+tempAuth);
				    	}
				    }
				    
				    comUserInfo.setUser_id(user_id);
				    comUserInfo.setUser_nm(user_nm);
				    comUserInfo.setUser_auth(user_auth);
				    comUserInfo.setUser_authorities(sbUserAuth.toString());
				    
				    System.out.println("encode Test : "+ CryptUtils.strToEncode(comUserInfo.getUser_nm()));
				    System.out.println("decode Test : "+ CryptUtils.encodeToStr(CryptUtils.strToEncode(comUserInfo.getUser_nm())));
				    
				    System.out.println( comUserInfo.getUser_id());
				    System.out.println( comUserInfo.getUser_nm());
				    System.out.println( comUserInfo.getUser_auth());
				    System.out.println( comUserInfo.getUser_authorities());
				    
				}
			}
			*/
			/*
			HttpSession session = request.getSession(false);
			if(session!=null){
			VoUserInfo voUserInfo = (VoUserInfo)session.getAttribute("userInfoVO");
			
			System.out.println( voUserInfo.getUser_id());
		    System.out.println( voUserInfo.getUser_nm());
		    System.out.println( voUserInfo.getUser_auth());
		    System.out.println( voUserInfo.getUser_authorities());
			
			
			if(!"".equals(StringUtils.getNull(sbUserAuth))){
				voUserInfo.setUser_authorities(sbUserAuth.toString());
				
				request.getSession().setAttribute("userInfoVO", voUserInfo);
			}
			
			modelAndView.addObject("userInfoVO", voUserInfo );
			}
			*/
			VoCommon voCommon = new VoCommon();
			SessionMessage.removeMessage(voCommon);
			modelAndView.addObject("VoCommon", voCommon );
			
			//modelAndView.addObject("comUserInfo", comUserInfo);
		}
	}
}
