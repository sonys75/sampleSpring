package crawl.cmm.cde.service.impl;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import crawl.cmm.cde.service.CodeService;
import crawl.vo.VoAuth;
import crawl.vo.VoCodeInfo;



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
@Service("codeService")
public class CodeServiceImpl implements CodeService {

    @Resource(name="codeDAO")
    private CodeDAO codeDAO;

    public VoAuth selectAuthNo(HashMap<String, String> hMap) throws Exception{
    	return (VoAuth)codeDAO.selectAuthNo(hMap);
    }
    
    public List<VoAuth> selectAuthList(HashMap<String, String> hMap) throws Exception{
    	return (List<VoAuth>)codeDAO.selectAuthList(hMap);
    }

	/**
	 * 코드 목록
	 * 
	 * @param VoCodeInfo
	 * @return List
	 */
    public List<VoCodeInfo> selectListCodeInfoByCodePart(HashMap<String, String> hMap) throws Exception{
        return (List<VoCodeInfo>) this.codeDAO.selectListCodeInfoByCodePart(hMap);
    }
    
    public boolean checkUserAuthByAuthId(String auth_id) throws Exception{
    	boolean bChkAuth = false;
    	
    	HashMap<String, String> hMap = new HashMap<String,String>();
		hMap.put("userAuth", auth_id);
		
		VoAuth voAuth = this.codeDAO.selectAuthNo(hMap);
		
		List<VoAuth> authList = new ArrayList<VoAuth>(); 
				
		if(voAuth!=null){
			hMap.put("authNo", voAuth.getAuth_no());
			authList = this.codeDAO.selectAuthList(hMap);
		}

		for(int i=0;i<authList.size();i++){
			VoAuth chkAuth = authList.get(i);
			if(chkAuth.getAuth_id().equals(auth_id)){
				bChkAuth = true;
				break;
			}
		}
		
    	return bChkAuth;
    }

    public String selectServerUrl() throws Exception{
    	return (String) codeDAO.selectServerUrl();
    }
}
