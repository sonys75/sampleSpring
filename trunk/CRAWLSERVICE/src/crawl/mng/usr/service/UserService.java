package crawl.mng.usr.service;

import java.util.HashMap;
import java.util.List;

import crawl.vo.VoUserInfo;

/**
 * UserService 처리 인터페이스 클래스
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
public interface UserService {
 
	int selectCntUserInfo(HashMap<String, String> hMap) throws Exception;
 
	List<VoUserInfo> selectListUserInfo(HashMap<String, String> hMap) throws Exception;
	
	VoUserInfo selectUserInfoByUserId(HashMap<String, String> hMap) throws Exception;
	
	void insertUserInfo(HashMap<String, String> hMap) throws Exception;

	void updateUserInfoByUserId(HashMap<String, String> hMap) throws Exception;
	
	void deleteUserInfoByUserId(HashMap<String, String> hMap) throws Exception;

	int selectCntLowCorpInfoByCorpId(HashMap<String, String> hMap) throws Exception;
}
