package crawl.sys.mnu.mng.service.impl;

import java.util.HashMap;
import java.util.List;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import crawl.sys.mnu.mng.service.MenuManageService;
import crawl.vo.VoMenu;

/**
 * MenuManage 처리 비즈니스 구현 클래스
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
@Service("menuManageService")
public class MenuManageServiceImpl implements MenuManageService {

    @Resource(name="menuManageDAO")
    private MenuManageDAO menuManageDAO;

    public List<VoMenu> selectListMenu(HashMap<String, String> hMap) throws Exception{
    	return (List<VoMenu>)menuManageDAO.selectListMenu(hMap);
    }
    
    public VoMenu selectMenuInfoByMenuId(HashMap<String, String> hMap) throws Exception{
    	return (VoMenu)menuManageDAO.selectMenuInfoByMenuId(hMap);
    }

    
    public int selectCntLowerMenuInfoByMenuId(HashMap<String, String> hMap) throws Exception{
    	return menuManageDAO.selectCntLowerMenuInfoByMenuId(hMap);
    }
    
    public String insertMenuInfo(HashMap<String, String> hMap) throws Exception{
    	return (String)menuManageDAO.insertMenuInfo(hMap);
    }

    public void updateMenuInfoByMenuId(HashMap<String, String> hMap) throws Exception{
    	menuManageDAO.updateMenuInfoByMenuId(hMap);
    }
    
    public void deleteMenuAuthByMenuId(HashMap<String, String> hMap) throws Exception{
    	menuManageDAO.deleteMenuAuthByMenuId(hMap);
    }
    
    public void deleteMenuInfoByMenuId(HashMap<String, String> hMap) throws Exception{
    	menuManageDAO.deleteMenuInfoByMenuId(hMap);
    }

}
