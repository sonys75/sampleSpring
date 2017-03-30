package crawl.crn.nws.service.impl;

import java.util.HashMap;
import java.util.List;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import crawl.crn.nws.service.NewsService;
import crawl.vo.VoNews;



/**
 * News 처리 비즈니스 구현 클래스
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
@Service("newsService")
public class NewsServiceImpl implements NewsService {

    @Resource(name="newsDAO")
    private NewsDAO newsDAO;

    public List<VoNews> selectListNewsRss(HashMap<String, String> hMap) throws Exception{
    	return (List<VoNews>)newsDAO.selectListNewsRss(hMap);
    }
    
    public void insertNewsRss(HashMap<String, String> hMap) throws Exception{
    	newsDAO.insertNewsRss(hMap);
    }
 
    public void deleteNewsRss() throws Exception{
    	newsDAO.deleteNewsRss();
    }
}
