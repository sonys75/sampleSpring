package crawl.sys.mnu.aut.service.impl;

import java.util.HashMap;
import java.util.List;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import crawl.sys.mnu.aut.service.MenuAuthService;
import crawl.vo.VoAuth;
import crawl.vo.VoMenu;

/**
 * MenuAuth 처리 비즈니스 구현 클래스
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
@Service("menuAuthService")
public class MenuAuthServiceImpl implements MenuAuthService {

    @Resource(name="menuAuthDAO")
    private MenuAuthDAO menuAuthDAO;

    public List<VoMenu> selectListMenuInfoByAuthId(HashMap<String, String> hMap) throws Exception{
    	return (List<VoMenu>)menuAuthDAO.selectListMenuInfoByAuthId(hMap);
    }
    
    public List<VoMenu> selectAllListMenuInfo(HashMap<String, String> hMap) throws Exception{
    	return (List<VoMenu>)menuAuthDAO.selectAllListMenuInfo(hMap);
    }
    
    public List<VoMenu> selectListMenuAuth(HashMap<String, String> hMap) throws Exception{
    	return (List<VoMenu>)menuAuthDAO.selectListMenuAuth(hMap);
    }

    public VoAuth selectAuthByAuthId(HashMap<String, String> hMap) throws Exception{
    	return (VoAuth)menuAuthDAO.selectAuthByAuthId(hMap);
    }
    
    public void deleteMenuAuthByAuthId(HashMap<String, String> hMap) throws Exception{
    	menuAuthDAO.deleteMenuAuthByAuthId(hMap);
    }
    
    public void insertMenuAuth(HashMap<String, String> hMap) throws Exception{
    	menuAuthDAO.insertMenuAuth(hMap);
    }
}
