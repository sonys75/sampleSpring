package crawl.lgn.service.impl;

import java.util.HashMap;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;
//import kr.or.kpetro.com.cop.ems.service.EgovSndngMailRegistService;
//import kr.or.kpetro.com.cop.ems.service.SndngMailVO;
//import kr.or.kpetro.com.utl.fcc.service.EgovNumberUtil;
//import kr.or.kpetro.com.utl.fcc.service.EgovStringUtil;

import crawl.lgn.service.LoginService;
import crawl.vo.VoUserInfo;


/**
 * 로그인 처리 비즈니스 구현 클래스
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
@Service("loginService")
public class LoginServiceImpl implements LoginService {

    @Resource(name="loginDAO")
    private LoginDAO loginDAO;
    
    public VoUserInfo selectUserInfoByUserId(HashMap<String, String> hMap) throws Exception{
    	return (VoUserInfo)loginDAO.selectUserInfoByUserId(hMap);
    }

    public void updateAccessFailCntInitByUserId(VoUserInfo voUserInfo) throws Exception{
    	loginDAO.updateAccessFailCntInitByUserId(voUserInfo);
    }
    
    public void updateAccessFailCntByUserId(VoUserInfo voUserInfo) throws Exception{
    	loginDAO.updateAccessFailCntByUserId(voUserInfo);
    }
}
