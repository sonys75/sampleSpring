package crawl.mng.cde.service;

import java.util.HashMap;
import java.util.List;

import crawl.vo.VoCodeInfo;

/**
 * CodeManage 처리 인터페이스 클래스
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
public interface CodeManageService {

	int selectCntCodePart(HashMap<String, String> hMap) throws Exception;
	 
	List<VoCodeInfo> selectListCodePart(HashMap<String, String> hMap) throws Exception;
	
	VoCodeInfo selectCodePartByCodePart(HashMap<String, String> hMap) throws Exception;
	
	void insertCodePart(HashMap<String, String> hMap) throws Exception;
	
	void updateCodePartByCodePart(HashMap<String, String> hMap) throws Exception;
	
	void deleteCodePartByCodePart(HashMap<String, String> hMap) throws Exception;
	
	int selectCntCodeInfo(HashMap<String, String> hMap) throws Exception;
	 
	List<VoCodeInfo> selectListCodeInfo(HashMap<String, String> hMap) throws Exception;
	
	List<VoCodeInfo> selectListCodeInfoByCodePart(HashMap<String, String> hMap) throws Exception;
	
	VoCodeInfo selectCodeInfoByCode(HashMap<String, String> hMap) throws Exception;
	
	void insertCodeInfo(HashMap<String, String> hMap) throws Exception;
	
	void updateCodeInfoByCode(HashMap<String, String> hMap) throws Exception;
	
	void deleteCodeInfoByCode(HashMap<String, String> hMap) throws Exception;
	
}
