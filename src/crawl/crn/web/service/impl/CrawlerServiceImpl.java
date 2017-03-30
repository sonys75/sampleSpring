package crawl.crn.web.service.impl;

import java.util.HashMap;
import java.util.List;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import crawl.crn.web.service.CrawlerService;
import crawl.vo.VoCrawler;



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
@Service("crawlerService")
public class CrawlerServiceImpl implements CrawlerService {

    @Resource(name="crawlerDAO")
    private CrawlerDAO crawlerDAO;
    
    public VoCrawler selectCrawlStatus(HashMap<String, String> hMap) throws Exception{
    	return (VoCrawler)crawlerDAO.selectCrawlStatus(hMap);
    }
    
    public void updateCrawlStatus(HashMap<String, String> hMap) throws Exception{
    	crawlerDAO.updateCrawlStatus(hMap);
    }
    
    public List<VoCrawler> selectListCrawlPartByCrawlYn(HashMap<String, String> hMap) throws Exception{
    	return (List<VoCrawler>)crawlerDAO.selectListCrawlPartByCrawlYn(hMap);
    }
    
    public int selectCntCrawlTmpByLink(HashMap<String, String> hMap) throws Exception{
    	return crawlerDAO.selectCntCrawlTmpByLink(hMap);
    }
    
    public void insertCrawlTmp(HashMap<String, String> hMap) throws Exception{
    	crawlerDAO.insertCrawlTmp(hMap);
    }
    
    public List<VoCrawler> selectListCrawl(HashMap<String, String> hMap) throws Exception{
    	return (List<VoCrawler>)crawlerDAO.selectListCrawl(hMap);
    }
    
    public void updateCrawlTmp(HashMap<String, String> hMap) throws Exception{
    	crawlerDAO.updateCrawlTmp(hMap);
    }
    
    public List<VoCrawler> selectListCrawlImg(HashMap<String, String> hMap) throws Exception{
    	return (List<VoCrawler>)crawlerDAO.selectListCrawlImg(hMap);
    }
    
    public void updateCrawlTmpByImgFind(HashMap<String, String> hMap) throws Exception{
    	crawlerDAO.updateCrawlTmpByImgFind(hMap);
    }
    
    public void updateCrawlTmpByBlockFind(HashMap<String, String> hMap) throws Exception{
    	crawlerDAO.updateCrawlTmpByBlockFind(hMap);
    }
    
    public void updateCrawlTmpByImgNotFind(HashMap<String, String> hMap) throws Exception{
    	crawlerDAO.updateCrawlTmpByImgNotFind(hMap);
    }
    
    public void updateCrawlTmpImg(HashMap<String, String> hMap) throws Exception{
    	crawlerDAO.updateCrawlTmpImg(hMap);
    }
    
    public void insertCrawlCont(HashMap<String, String> hMap) throws Exception{
    	crawlerDAO.insertCrawlCont(hMap);
    }
    
    public void updateCrawlCont(HashMap<String, String> hMap) throws Exception{
    	crawlerDAO.updateCrawlCont(hMap);
    }
    
    
    
    
    
    
    
    
    
    
    public List<VoCrawler> selectListPartByUseYn(HashMap<String, String> hMap) throws Exception{
    	return (List<VoCrawler>)crawlerDAO.selectListPartByUseYn(hMap);
    }
    
    public int selectCntCrawlerByLink(HashMap<String, String> hMap) throws Exception{
    	return crawlerDAO.selectCntCrawlerByLink(hMap);
    }
    
    public int selectCntCrawler(HashMap<String, String> hMap) throws Exception{
    	return crawlerDAO.selectCntCrawler(hMap);
    }

    public List<VoCrawler> selectListCrawler(HashMap<String, String> hMap) throws Exception{
    	return (List<VoCrawler>)crawlerDAO.selectListCrawler(hMap);
    }
    
    
    
    
    public void insertNovel(HashMap<String, String> hMap) throws Exception{
    	crawlerDAO.insertNovel(hMap);
    }
    
    public void updateNovel(HashMap<String, String> hMap) throws Exception{
    	crawlerDAO.updateNovel(hMap);
    }
    
    
    public void updateNovelMerge(HashMap<String, String> hMap) throws Exception{
    	crawlerDAO.updateNovelMerge(hMap);
    }
 
}
