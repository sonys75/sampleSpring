package crawl.crn.web.service;

import java.util.HashMap;
import java.util.List;

import crawl.vo.VoCrawler;

/**
 * News 처리 인터페이스 클래스
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
public interface CrawlerService {
	
	VoCrawler selectCrawlStatus(HashMap<String, String> hMap) throws Exception;
	
	void updateCrawlStatus(HashMap<String, String> hMap) throws Exception;
	
	List<VoCrawler> selectListCrawlPartByCrawlYn(HashMap<String, String> hMap) throws Exception;
	
	int selectCntCrawlTmpByLink(HashMap<String, String> hMap) throws Exception;
	
	void insertCrawlTmp(HashMap<String, String> hMap) throws Exception;
	
	List<VoCrawler> selectListCrawl(HashMap<String, String> hMap) throws Exception;
	
	void updateCrawlTmp(HashMap<String, String> hMap) throws Exception;
	
	List<VoCrawler> selectListCrawlImg(HashMap<String, String> hMap) throws Exception;
	
	void updateCrawlTmpByImgFind(HashMap<String, String> hMap) throws Exception;
	
	void updateCrawlTmpByBlockFind(HashMap<String, String> hMap) throws Exception;
	
	void updateCrawlTmpByImgNotFind(HashMap<String, String> hMap) throws Exception;
	
	void updateCrawlTmpImg(HashMap<String, String> hMap) throws Exception;
	
	void insertCrawlCont(HashMap<String, String> hMap) throws Exception;
	
	void updateCrawlCont(HashMap<String, String> hMap) throws Exception;
	
	
	
	
	
	
	
	
	
	List<VoCrawler> selectListPartByUseYn(HashMap<String, String> hMap) throws Exception;
	
	int selectCntCrawlerByLink(HashMap<String, String> hMap) throws Exception;
	
	int selectCntCrawler(HashMap<String, String> hMap) throws Exception;
	
	List<VoCrawler> selectListCrawler(HashMap<String, String> hMap) throws Exception;

	void insertNovel(HashMap<String, String> hMap) throws Exception;
	
	void updateNovel(HashMap<String, String> hMap) throws Exception;
	
	void updateNovelMerge(HashMap<String, String> hMap) throws Exception;

}
