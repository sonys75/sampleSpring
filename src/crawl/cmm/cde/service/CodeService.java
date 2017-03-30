package crawl.cmm.cde.service;

import java.util.HashMap;
import java.util.List;

import crawl.vo.VoAuth;
import crawl.vo.VoCodeInfo;

/**
 * Code 처리 인터페이스 클래스
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
public interface CodeService {
	
	VoAuth selectAuthNo(HashMap<String, String> hMap) throws Exception;
	
	List<VoAuth> selectAuthList(HashMap<String, String> hMap) throws Exception;
	
	List<VoCodeInfo> selectListCodeInfoByCodePart(HashMap<String, String> hMap) throws Exception;

	/**
	 * auth_id에 따른 권한이 있는지 체크 
	 * @param auth_id
	 * @return
	 * @throws Exception
	 */
	boolean checkUserAuthByAuthId(String auth_id) throws Exception;
	
 
	
	String selectServerUrl() throws Exception;
}
