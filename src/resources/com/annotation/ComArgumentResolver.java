package resources.com.annotation;

import java.util.Enumeration;
import java.util.HashMap;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.springframework.core.MethodParameter;
import org.springframework.web.bind.support.WebDataBinderFactory;
import org.springframework.web.context.request.NativeWebRequest;
import org.springframework.web.method.support.HandlerMethodArgumentResolver;
import org.springframework.web.method.support.ModelAndViewContainer;

/**
 * ComArgumentResolver.java
 * <p><b>NOTE:</b> <pre> Spring3.1부터 AnnotationMethodHandlerAdapter은 deprecated되었으며 대신, RequestMappingHandlerAdapter를 사용해야한다. 
 * RequestMappingHandlerAdapter에서 ArgumentResolver는 webArgumentResolver가 아닌 HandlerMethodArgumentResolver를 구현해야한다. 
 * ( 클래스의 동작은 기존 CommandMapArgumentResolver와 동일 )
 *   
 * Controller에서 화면(JSP) 입력값을 받기 위해서 일반적으로 Command(Form Class) 객체를 사용하지만, Map 객체를 사용하는걸 선호할 수 있다.
 * Sping MVC는 Controller의 argument를 분석하여 argument값을 customizing 할 수 있는 WebArgumentResolver라는 interface를 제공한다.
 * CommandMapArgumentResolver는 WebArgumentResolver의 구현 클래스이다.
 * CommandMapArgumentResolver는 Controller 메소드의 argument중에 commandMap이라는 Map 객체가 있다면
 * HTTP request 객체에 있는 파라미터이름과 값을 commandMap에 담는다.</b>
 *                </pre>
 * @author 이영지
 * @since 2014.04.23
 * @version 3.0
 * @see CommandMap, {@link EgovRequestMappingHandlerAdapter}
 *
 * <pre>
 * << 개정이력(Modification Information) >>
 *   
 *   수정일      수정자           수정내용
 *  -------    --------    ---------------------------
 *   2014.04.23  이영지            최초 생성
 *
 * </pre>
 */
public class ComArgumentResolver implements HandlerMethodArgumentResolver{

	@Override
	public boolean supportsParameter(MethodParameter parameter) {
		if(Map.class.isAssignableFrom(parameter.getParameterType()) 
				&& parameter.hasParameterAnnotation(ComCommandMap.class)) {
			return true;
		}
		else {
			return false;
		}
	}

	@Override
	public Object resolveArgument(MethodParameter parameter,
			ModelAndViewContainer mavContainer, NativeWebRequest webRequest,
			WebDataBinderFactory binderFactory) throws Exception {
		
		Map<String, Object> commandMap = new HashMap<String, Object>();
		HttpServletRequest request = (HttpServletRequest) webRequest.getNativeRequest();			
		Enumeration<?> enumeration = request.getParameterNames();
		
		while(enumeration.hasMoreElements()){
			String key = (String) enumeration.nextElement();
			String[] values = request.getParameterValues(key);
			if(values!=null){
				commandMap.put(key, (values.length > 1) ? values:values[0] );
			}
		}
		
		return commandMap;
	}

}
