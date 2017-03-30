package crawl.mpg.service.impl;

import java.util.HashMap;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import crawl.mpg.service.MyPageService;
import crawl.vo.VoUserInfo;



/**
 * MyPage 처리 비즈니스 구현 클래스
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
//@Transactional
@Service("myPageService")
public class MyPageServiceImpl implements MyPageService {

    @Resource(name="myPageDAO")
    private MyPageDAO myPageDAO;
    
    public VoUserInfo selectUserInfoByUserId(HashMap<String, String> hMap) throws Exception{
    	return (VoUserInfo)myPageDAO.selectUserInfoByUserId(hMap);
    }

    public void updateUserInfoByUserIdPW(HashMap<String, String> hMap) throws Exception{
    	myPageDAO.updateUserInfoByUserIdPW(hMap);
    }
}
