package crawl.sys.aut.mng.service.impl;

import java.util.HashMap;
import java.util.List;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import crawl.sys.aut.mng.service.AuthManageService;
import crawl.vo.VoAuth;

/**
 * AuthManage 처리 비즈니스 구현 클래스
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
@Service("authManageService")
public class AuthManageServiceImpl implements AuthManageService {

    @Resource(name="authManageDAO")
    private AuthManageDAO authManageDAO;
    
    public int selectCntAuth(HashMap<String, String> hMap) throws Exception{
    	return authManageDAO.selectCntAuth(hMap);
    }

    public List<VoAuth> selectListAuth(HashMap<String, String> hMap) throws Exception{
    	return (List<VoAuth>)authManageDAO.selectListAuth(hMap);
    }
    
    public VoAuth selectAuthByAuthNo(HashMap<String, String> hMap) throws Exception{
    	return (VoAuth)authManageDAO.selectAuthByAuthNo(hMap);
    }
    
    public int selectCntAuthByAuthId(HashMap<String, String> hMap) throws Exception{
    	return authManageDAO.selectCntAuthByAuthId(hMap);
    }
    
    public String insertAuth(HashMap<String, String> hMap) throws Exception{
    	return (String)authManageDAO.insertAuth(hMap);
    }
    
    public int selectCntAuthByUpAuthNo(HashMap<String, String> hMap) throws Exception{
    	return authManageDAO.selectCntAuthByUpAuthNo(hMap);
    }
    
    public void updateAuthByAuthNo(HashMap<String, String> hMap) throws Exception{
    	authManageDAO.updateAuthByAuthNo(hMap);
    }
    
    public void deleteAuthByAuthNo(HashMap<String, String> hMap) throws Exception{
    	authManageDAO.deleteAuthByAuthNo(hMap);
    }

    public void updateAuthLvl(HashMap<String, String> hMap) throws Exception{
    	authManageDAO.updateAuthLvl(hMap);
    }
}
