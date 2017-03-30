package crawl.mpg.service;

import java.util.HashMap;

import crawl.vo.VoUserInfo;

/**
 * MyPage 처리 인터페이스 클래스
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
public interface MyPageService {
 
	public VoUserInfo selectUserInfoByUserId(HashMap<String, String> hMap) throws Exception;
 
	public void updateUserInfoByUserIdPW(HashMap<String, String> hMap) throws Exception;
}
