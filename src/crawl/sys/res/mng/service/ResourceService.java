package crawl.sys.res.mng.service;

import java.util.HashMap;
import java.util.List;

import crawl.vo.VoReso;

/**
 * Resource 처리 인터페이스 클래스
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
public interface ResourceService {
 
	int selectCntReso(HashMap<String, String> hMap) throws Exception;
 
	List<VoReso> selectListReso(HashMap<String, String> hMap) throws Exception;
	
	VoReso selectResoByResoId(HashMap<String, String> hMap) throws Exception;
	
	List<VoReso> selectResoAuthByResoId(HashMap<String, String> hMap) throws Exception;
	
	List<VoReso> selectListAllResoAuth(HashMap<String, String> hMap) throws Exception;
	
	int selectCntResoInfoByResoPtn(HashMap<String, String> hMap) throws Exception;
	
	String insertResoInfo(HashMap<String, String> hMap) throws Exception;
	
	void insertResoAuth(HashMap<String, String> hMap) throws Exception;
	
	int selectCntResoInfoByResoId(HashMap<String, String> hMap) throws Exception;

	void updateResoInfoByResoId(HashMap<String, String> hMap) throws Exception;
	
	void deleteResoAuthByResoId(HashMap<String, String> hMap) throws Exception;
	
	void deleteResoInfoByResoId(HashMap<String, String> hMap) throws Exception;

}
