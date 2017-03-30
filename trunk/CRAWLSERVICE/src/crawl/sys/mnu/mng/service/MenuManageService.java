package crawl.sys.mnu.mng.service;

import java.util.HashMap;
import java.util.List;

import crawl.vo.VoMenu;

/**
 * MenuManage 처리 인터페이스 클래스
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
public interface MenuManageService {
	List<VoMenu> selectListMenu(HashMap<String, String> hMap) throws Exception;
	
	VoMenu selectMenuInfoByMenuId(HashMap<String, String> hMap) throws Exception;

	int selectCntLowerMenuInfoByMenuId(HashMap<String, String> hMap) throws Exception;
	
	String insertMenuInfo(HashMap<String, String> hMap) throws Exception;

	void updateMenuInfoByMenuId(HashMap<String, String> hMap) throws Exception;
	
	void deleteMenuAuthByMenuId(HashMap<String, String> hMap) throws Exception;
	
	void deleteMenuInfoByMenuId(HashMap<String, String> hMap) throws Exception;

}
