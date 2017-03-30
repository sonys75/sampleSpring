package crawl.mng.usr.service.impl;

import java.util.HashMap;
import java.util.List;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import crawl.mng.usr.service.UserService;
import crawl.vo.VoUserInfo;



/**
 * UserService 처리 비즈니스 구현 클래스
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
@Service("userService")
public class UserServiceImpl implements UserService {

    @Resource(name="userDAO")
    private UserDAO userDAO;
    
    public int selectCntUserInfo(HashMap<String, String> hMap) throws Exception{
    	return userDAO.selectCntUserInfo(hMap);
    }

    public List<VoUserInfo> selectListUserInfo(HashMap<String, String> hMap) throws Exception{
    	return (List<VoUserInfo>)userDAO.selectListUserInfo(hMap);
    }
    
    public VoUserInfo selectUserInfoByUserId(HashMap<String, String> hMap) throws Exception{
    	return (VoUserInfo)userDAO.selectUserInfoByUserId(hMap);
    }
    
    public void insertUserInfo(HashMap<String, String> hMap) throws Exception{
    	userDAO.insertUserInfo(hMap);
    }

    public void updateUserInfoByUserId(HashMap<String, String> hMap) throws Exception{
    	userDAO.updateUserInfoByUserId(hMap);
    }
    
    public void deleteUserInfoByUserId(HashMap<String, String> hMap) throws Exception{
    	userDAO.deleteUserInfoByUserId(hMap);
    }

    public int selectCntLowCorpInfoByCorpId(HashMap<String, String> hMap) throws Exception{
    	return userDAO.selectCntLowCorpInfoByCorpId(hMap);
    }
}
