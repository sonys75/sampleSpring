package crawl.mng.cde.service.impl;

import java.util.HashMap;
import java.util.List;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import crawl.mng.cde.service.CodeManageService;
import crawl.vo.VoCodeInfo;



/**
 * CodeManage 처리 비즈니스 구현 클래스
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
@Service("codeManageService")
public class CodeManageServiceImpl implements CodeManageService {

    @Resource(name="codeManageDAO")
    private CodeManageDAO codeManageDAO;

    public int selectCntCodePart(HashMap<String, String> hMap) throws Exception{
    	return codeManageDAO.selectCntCodePart(hMap);
    }
    
    public List<VoCodeInfo> selectListCodePart(HashMap<String, String> hMap) throws Exception{
    	return (List<VoCodeInfo>)codeManageDAO.selectListCodePart(hMap);
    }    
    
    public VoCodeInfo selectCodePartByCodePart(HashMap<String, String> hMap) throws Exception{
    	return (VoCodeInfo)codeManageDAO.selectCodePartByCodePart(hMap);
    }
    
    public void insertCodePart(HashMap<String, String> hMap) throws Exception{
    	codeManageDAO.insertCodePart(hMap);
    }
    
    public void updateCodePartByCodePart(HashMap<String, String> hMap) throws Exception{
    	codeManageDAO.updateCodePartByCodePart(hMap);
    }
    
    public void deleteCodePartByCodePart(HashMap<String, String> hMap) throws Exception{
    	codeManageDAO.deleteCodePartByCodePart(hMap);
    }
    
    public int selectCntCodeInfo(HashMap<String, String> hMap) throws Exception{
    	return codeManageDAO.selectCntCodeInfo(hMap);
    }
    
    public List<VoCodeInfo> selectListCodeInfo(HashMap<String, String> hMap) throws Exception{
    	return (List<VoCodeInfo>)codeManageDAO.selectListCodeInfo(hMap);
    }
    
    public List<VoCodeInfo> selectListCodeInfoByCodePart(HashMap<String, String> hMap) throws Exception{
    	return (List<VoCodeInfo>) codeManageDAO.selectListCodeInfoByCodePart(hMap);
    }

    public VoCodeInfo selectCodeInfoByCode(HashMap<String, String> hMap) throws Exception{
    	return (VoCodeInfo)codeManageDAO.selectCodeInfoByCode(hMap);
    }
    
    public void insertCodeInfo(HashMap<String, String> hMap) throws Exception{
    	codeManageDAO.insertCodeInfo(hMap);
    }
    
    public void updateCodeInfoByCode(HashMap<String, String> hMap) throws Exception{
    	codeManageDAO.updateCodeInfoByCode(hMap);
    }
    
    public void deleteCodeInfoByCode(HashMap<String, String> hMap) throws Exception{
    	codeManageDAO.deleteCodeInfoByCode(hMap);
    }
}
