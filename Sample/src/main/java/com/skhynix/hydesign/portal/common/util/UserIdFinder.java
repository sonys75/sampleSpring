package com.skhynix.hydesign.portal.common.util;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import nexcore.sprout.foundry.commons.IUserIdFinder;

import com.skhynix.hydesign.portal.common.constants.PortalConstants;
import com.skhynix.hydesign.portal.common.vo.SessionVO;

public class UserIdFinder implements IUserIdFinder {
	
	public String getUserId(HttpServletRequest request){
		
        String name = "ANONYMOUS";
        HttpSession session = request.getSession(false);
        
        if(session != null && session.getAttribute(PortalConstants.Key.SESSION_KEY) != null){
        	
        	SessionVO svo = (SessionVO)session.getAttribute(PortalConstants.Key.SESSION_KEY);
        	name = svo.getEmpNo();
        }
            
        return name;
    }
}
