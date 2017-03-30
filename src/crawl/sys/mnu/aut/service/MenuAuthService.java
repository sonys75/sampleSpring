package crawl.sys.mnu.aut.service;

import java.util.HashMap;
import java.util.List;

import crawl.vo.VoAuth;
import crawl.vo.VoMenu;

/**
 * MenuAuth 처리 인터페이스 클래스
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
public interface MenuAuthService {

	List<VoMenu> selectListMenuInfoByAuthId(HashMap<String, String> hMap) throws Exception;
	
	List<VoMenu> selectAllListMenuInfo(HashMap<String, String> hMap) throws Exception;
	
	List<VoMenu> selectListMenuAuth(HashMap<String, String> hMap) throws Exception;

	VoAuth selectAuthByAuthId(HashMap<String, String> hMap) throws Exception;
	
	void deleteMenuAuthByAuthId(HashMap<String, String> hMap) throws Exception;
	
	void insertMenuAuth(HashMap<String, String> hMap) throws Exception;
	
}
