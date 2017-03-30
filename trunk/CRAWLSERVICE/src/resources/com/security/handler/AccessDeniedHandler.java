package resources.com.security.handler;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.security.web.access.AccessDeniedHandlerImpl;
import org.springframework.stereotype.Service;

@Service("accessDeniedHandler")
public class AccessDeniedHandler extends AccessDeniedHandlerImpl {

    Log log = LogFactory.getLog(getClass());

    @Override
    public void handle(HttpServletRequest request,
            HttpServletResponse response, AccessDeniedException exception)
            throws IOException, ServletException {
        log.info("############### Access Denied Handler!");
        setErrorPage("/cmm/err/accessDeniedException");
        super.handle(request, response, exception);
    }

}