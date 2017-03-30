package crawl.sys.res.mng.service.impl;

import java.util.HashMap;
import java.util.List;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import crawl.sys.res.mng.service.ResourceService;
import crawl.vo.VoReso;

/**
 * Resource 처리 비즈니스 구현 클래스
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
@Service("resourceService")
public class ResourceServiceImpl implements ResourceService {

    @Resource(name="resourceDAO")
    private ResourceDAO resourceDAO;
    
    public int selectCntReso(HashMap<String, String> hMap) throws Exception{
    	return resourceDAO.selectCntReso(hMap);
    }

    public List<VoReso> selectListReso(HashMap<String, String> hMap) throws Exception{
    	return (List<VoReso>)resourceDAO.selectListReso(hMap);
    }
    
    public VoReso selectResoByResoId(HashMap<String, String> hMap) throws Exception{
    	return (VoReso)resourceDAO.selectResoByResoId(hMap);
    }
    
    public List<VoReso> selectResoAuthByResoId(HashMap<String, String> hMap) throws Exception{
    	return (List<VoReso>)resourceDAO.selectResoAuthByResoId(hMap);
    }
    
    public List<VoReso> selectListAllResoAuth(HashMap<String, String> hMap) throws Exception{
    	return (List<VoReso>)resourceDAO.selectListAllResoAuth(hMap);
    }
    
    public int selectCntResoInfoByResoPtn(HashMap<String, String> hMap) throws Exception{
    	return resourceDAO.selectCntResoInfoByResoPtn(hMap);
    }
    
    public String insertResoInfo(HashMap<String, String> hMap) throws Exception{
    	return (String)resourceDAO.insertResoInfo(hMap);
    }
    
    public void insertResoAuth(HashMap<String, String> hMap) throws Exception{
    	resourceDAO.insertResoAuth(hMap);
    }
    
    public int selectCntResoInfoByResoId(HashMap<String, String> hMap) throws Exception{
    	return resourceDAO.selectCntResoInfoByResoId(hMap);
    }

    public void updateResoInfoByResoId(HashMap<String, String> hMap) throws Exception{
    	resourceDAO.updateResoInfoByResoId(hMap);
    }
    
    public void deleteResoAuthByResoId(HashMap<String, String> hMap) throws Exception{
    	resourceDAO.deleteResoAuthByResoId(hMap);
    }
    
    public void deleteResoInfoByResoId(HashMap<String, String> hMap) throws Exception{
    	resourceDAO.deleteResoInfoByResoId(hMap);
    }

}
