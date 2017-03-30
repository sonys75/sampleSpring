package crawl.sys.aut.mng.service;

import java.util.HashMap;
import java.util.List;

import crawl.vo.VoAuth;

/**
 * AuthManage 처리 인터페이스 클래스
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
public interface AuthManageService {
 
	int selectCntAuth(HashMap<String, String> hMap) throws Exception;
 
	List<VoAuth> selectListAuth(HashMap<String, String> hMap) throws Exception;
	
	VoAuth selectAuthByAuthNo(HashMap<String, String> hMap) throws Exception;
	
	int selectCntAuthByAuthId(HashMap<String, String> hMap) throws Exception;
	
	String insertAuth(HashMap<String, String> hMap) throws Exception;
	
	int selectCntAuthByUpAuthNo(HashMap<String, String> hMap) throws Exception;
	
	void updateAuthByAuthNo(HashMap<String, String> hMap) throws Exception;
	
	void deleteAuthByAuthNo(HashMap<String, String> hMap) throws Exception;
	
	void updateAuthLvl(HashMap<String, String> hMap) throws Exception;
}
