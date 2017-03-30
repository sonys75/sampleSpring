package crawl.lgn.service.impl;

import java.util.HashMap;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.stereotype.Repository;

import crawl.vo.VoUserInfo;
import resources.com.service.impl.ComAbstractDAO;


/**
 * 로그인 처리 DAO 클래스
 * @author sonys75
 * @since 2014.11.17
 * @version 1.0
 * @see
 *  
 * <pre>
 * << 개정이력(Modification Information) >>
 * 
 *   수정일      수정자          수정내용
 *  -------    --------    ---------------------------
 *  2014.11.17  sonys75          최초 생성 
 *  </pre>
 */
@Repository("loginDAO")
public class LoginDAO extends ComAbstractDAO {
	
	/** log */
    protected static final Log LOG = LogFactory.getLog(LoginDAO.class);
    
    public VoUserInfo selectUserInfoByUserId(HashMap<String, String> hMap) throws Exception{
    	return (VoUserInfo) select("loginDAO.selectUserInfoByUserId", hMap);
    }
    
    public void updateAccessFailCntInitByUserId(VoUserInfo voUserInfo) throws Exception{
    	update("loginDAO.updateAccessFailCntInitByUserId", voUserInfo);
    }
    
    public void updateAccessFailCntByUserId(VoUserInfo voUserInfo) throws Exception{
    	update("loginDAO.updateAccessFailCntByUserId", voUserInfo);
    }

}
