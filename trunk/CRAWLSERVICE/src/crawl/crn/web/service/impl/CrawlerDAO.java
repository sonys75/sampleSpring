package crawl.crn.web.service.impl;

import java.sql.SQLException;
import java.util.HashMap;
import java.util.List;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import com.ibatis.sqlmap.client.SqlMapClient;

import crawl.vo.VoCrawler;


/**
 * crawlerDAO 처리 DAO 클래스
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
@Repository("crawlerDAO")
public class CrawlerDAO {
	/** log */
    protected static final Log LOG = LogFactory.getLog(CrawlerDAO.class);
    
    @Autowired
	private SqlMapClient sql;
    
  	public VoCrawler selectCrawlStatus(HashMap<String, String> hMap) throws Exception{
       	return (VoCrawler) sql.queryForObject("Crawl.selectCrawlStatus", hMap);
    }
  	
    public void updateCrawlStatus(HashMap<String, String> hMap) throws Exception{
    	// Transaction 시작
		sql.startTransaction(); 
		// 여기서 autoCommit설정을 변경해준다.!!!
		sql.getCurrentConnection().setAutoCommit(false);
		// autoCommit의 설정 Commit
		sql.commitTransaction(); 
		
		try {
			sql.update("Crawl.updateCrawlStatus", hMap);
			// Transaction Commit!!
			sql.commitTransaction(); 
			// connection Commit!!
			sql.getCurrentConnection().commit(); 
		} catch (SQLException e) {
			e.printStackTrace();
			throw new Exception(e);
		} finally {
			// 트랜잭션 종료
			sql.endTransaction(); 
		}
    }
    
    @SuppressWarnings("unchecked")
   	public List<VoCrawler> selectListCrawlPartByCrawlYn(HashMap<String, String> hMap) throws Exception{
       	return (List<VoCrawler>) sql.queryForList("Crawl.selectListCrawlPartByCrawlYn", hMap);
    }
    
    public int selectCntCrawlTmpByLink(HashMap<String, String> hMap) throws Exception{
    	return Integer.parseInt( sql.queryForObject("Crawl.selectCntCrawlTmpByLink", hMap).toString() );
    }
    
    public void insertCrawlTmp(HashMap<String, String> hMap) throws Exception{
    	// Transaction 시작
		sql.startTransaction(); 
		// 여기서 autoCommit설정을 변경해준다.!!!
		sql.getCurrentConnection().setAutoCommit(false);
		// autoCommit의 설정 Commit
		sql.commitTransaction(); 
		
		try {
			sql.insert("Crawl.insertCrawlTmp", hMap);
			// Transaction Commit!!
			sql.commitTransaction(); 
			// connection Commit!!
			sql.getCurrentConnection().commit(); 
		} catch (SQLException e) {
			e.printStackTrace();
			throw new Exception(e);
		} finally {
			// 트랜잭션 종료
			sql.endTransaction(); 
		}
    }
    
    
    @SuppressWarnings("unchecked")
   	public List<VoCrawler> selectListCrawl(HashMap<String, String> hMap) throws Exception{
       	return (List<VoCrawler>) sql.queryForList("Crawl.selectListCrawl", hMap);
    }
    
    public void updateCrawlTmp(HashMap<String, String> hMap) throws Exception{
    	// Transaction 시작
		sql.startTransaction(); 
		// 여기서 autoCommit설정을 변경해준다.!!!
		sql.getCurrentConnection().setAutoCommit(false);
		// autoCommit의 설정 Commit
		sql.commitTransaction(); 
		
		try {
			sql.update("Crawl.updateCrawlTmp", hMap);
			// Transaction Commit!!
			sql.commitTransaction(); 
			// connection Commit!!
			sql.getCurrentConnection().commit(); 
		} catch (SQLException e) {
			e.printStackTrace();
			throw new Exception(e);
		} finally {
			// 트랜잭션 종료
			sql.endTransaction(); 
		}
    }
    
    @SuppressWarnings("unchecked")
   	public List<VoCrawler> selectListCrawlImg(HashMap<String, String> hMap) throws Exception{
       	return (List<VoCrawler>) sql.queryForList("Crawl.selectListCrawlImg", hMap);
    }
    
    public void updateCrawlTmpByImgFind(HashMap<String, String> hMap) throws Exception{
    	// Transaction 시작
		sql.startTransaction(); 
		// 여기서 autoCommit설정을 변경해준다.!!!
		sql.getCurrentConnection().setAutoCommit(false);
		// autoCommit의 설정 Commit
		sql.commitTransaction(); 
		
		try {
			sql.update("Crawl.updateCrawlTmpByImgFind", hMap);
			// Transaction Commit!!
			sql.commitTransaction(); 
			// connection Commit!!
			sql.getCurrentConnection().commit(); 
		} catch (SQLException e) {
			e.printStackTrace();
			throw new Exception(e);
		} finally {
			// 트랜잭션 종료
			sql.endTransaction(); 
		}
    }
    
    public void updateCrawlTmpByBlockFind(HashMap<String, String> hMap) throws Exception{
    	// Transaction 시작
		sql.startTransaction(); 
		// 여기서 autoCommit설정을 변경해준다.!!!
		sql.getCurrentConnection().setAutoCommit(false);
		// autoCommit의 설정 Commit
		sql.commitTransaction(); 
		
		try {
			sql.update("Crawl.updateCrawlTmpByBlockFind", hMap);
			// Transaction Commit!!
			sql.commitTransaction(); 
			// connection Commit!!
			sql.getCurrentConnection().commit(); 
		} catch (SQLException e) {
			e.printStackTrace();
			throw new Exception(e);
		} finally {
			// 트랜잭션 종료
			sql.endTransaction(); 
		}
    }
    
    public void updateCrawlTmpByImgNotFind(HashMap<String, String> hMap) throws Exception{
    	// Transaction 시작
		sql.startTransaction(); 
		// 여기서 autoCommit설정을 변경해준다.!!!
		sql.getCurrentConnection().setAutoCommit(false);
		// autoCommit의 설정 Commit
		sql.commitTransaction(); 
		
		try {
			sql.update("Crawl.updateCrawlTmpByImgNotFind", hMap);
			// Transaction Commit!!
			sql.commitTransaction(); 
			// connection Commit!!
			sql.getCurrentConnection().commit(); 
		} catch (SQLException e) {
			e.printStackTrace();
			throw new Exception(e);
		} finally {
			// 트랜잭션 종료
			sql.endTransaction(); 
		}
    }
    
    public void updateCrawlTmpImg(HashMap<String, String> hMap) throws Exception{
    	// Transaction 시작
		sql.startTransaction(); 
		// 여기서 autoCommit설정을 변경해준다.!!!
		sql.getCurrentConnection().setAutoCommit(false);
		// autoCommit의 설정 Commit
		sql.commitTransaction(); 
		
		try {
			sql.update("Crawl.updateCrawlTmpImg", hMap);
			// Transaction Commit!!
			sql.commitTransaction(); 
			// connection Commit!!
			sql.getCurrentConnection().commit(); 
		} catch (SQLException e) {
			e.printStackTrace();
			throw new Exception(e);
		} finally {
			// 트랜잭션 종료
			sql.endTransaction(); 
		}
    }
    
    public void insertCrawlCont(HashMap<String, String> hMap) throws Exception{
    	// Transaction 시작
		sql.startTransaction(); 
		// 여기서 autoCommit설정을 변경해준다.!!!
		sql.getCurrentConnection().setAutoCommit(false);
		// autoCommit의 설정 Commit
		sql.commitTransaction(); 
		
		try {
			sql.insert("Crawl.insertCrawlCont", hMap);
			// Transaction Commit!!
			sql.commitTransaction(); 
			// connection Commit!!
			sql.getCurrentConnection().commit(); 
		} catch (SQLException e) {
			e.printStackTrace();
			throw new Exception(e);
		} finally {
			// 트랜잭션 종료
			sql.endTransaction(); 
		}
    }
    
    public void updateCrawlCont(HashMap<String, String> hMap) throws Exception{
    	// Transaction 시작
		sql.startTransaction(); 
		// 여기서 autoCommit설정을 변경해준다.!!!
		sql.getCurrentConnection().setAutoCommit(false);
		// autoCommit의 설정 Commit
		sql.commitTransaction(); 
		
		try {
			sql.update("Crawl.updateCrawlCont", hMap);
			// Transaction Commit!!
			sql.commitTransaction(); 
			// connection Commit!!
			sql.getCurrentConnection().commit(); 
		} catch (SQLException e) {
			e.printStackTrace();
			throw new Exception(e);
		} finally {
			// 트랜잭션 종료
			sql.endTransaction(); 
		}
    }
    
    
    
    
    
    
    
    
    
    
    
    
    
    
    
    
    
    
    
    @SuppressWarnings("unchecked")
   	public List<VoCrawler> selectListPartByUseYn(HashMap<String, String> hMap) throws Exception{
       	return (List<VoCrawler>) sql.queryForList("Crawler.selectListPartByUseYn", hMap);
    }
    
    public int selectCntCrawlerByLink(HashMap<String, String> hMap) throws Exception{
    	return Integer.parseInt( sql.queryForObject("Crawler.selectCntCrawlerByLink", hMap).toString() );
    }
    
    public int selectCntCrawler(HashMap<String, String> hMap) throws Exception{
    	return Integer.parseInt( sql.queryForObject("Crawler.selectCntCrawler", hMap).toString() );
    }
    
    @SuppressWarnings("unchecked")
   	public List<VoCrawler> selectListCrawler(HashMap<String, String> hMap) throws Exception{
       	return (List<VoCrawler>) sql.queryForList("Crawler.selectListCrawler", hMap);
    }
    
    

    public void insertNovel(HashMap<String, String> hMap) throws Exception{
    	// Transaction 시작
		sql.startTransaction(); 
		// 여기서 autoCommit설정을 변경해준다.!!!
		sql.getCurrentConnection().setAutoCommit(false);
		// autoCommit의 설정 Commit
		sql.commitTransaction(); 
		
		try {
			sql.insert("Crawler.insertNovel", hMap);
			// Transaction Commit!!
			sql.commitTransaction(); 
			// connection Commit!!
			sql.getCurrentConnection().commit(); 
		} catch (SQLException e) {
			e.printStackTrace();
			throw new Exception(e);
		} finally {
			// 트랜잭션 종료
			sql.endTransaction(); 
		}
    }
    
    public void updateNovel(HashMap<String, String> hMap) throws Exception{
    	// Transaction 시작
		sql.startTransaction(); 
		// 여기서 autoCommit설정을 변경해준다.!!!
		sql.getCurrentConnection().setAutoCommit(false);
		// autoCommit의 설정 Commit
		sql.commitTransaction(); 
		
		try {
			sql.update("Crawler.updateNovel", hMap);
			// Transaction Commit!!
			sql.commitTransaction(); 
			// connection Commit!!
			sql.getCurrentConnection().commit(); 
		} catch (SQLException e) {
			e.printStackTrace();
			throw new Exception(e);
		} finally {
			// 트랜잭션 종료
			sql.endTransaction(); 
		}
    }
    
    
    public void updateNovelMerge(HashMap<String, String> hMap) throws Exception{
    	// Transaction 시작
		sql.startTransaction(); 
		// 여기서 autoCommit설정을 변경해준다.!!!
		sql.getCurrentConnection().setAutoCommit(false);
		// autoCommit의 설정 Commit
		sql.commitTransaction(); 
		
		try {
			sql.update("Crawler.updateNovelMerge", hMap);
			// Transaction Commit!!
			sql.commitTransaction(); 
			// connection Commit!!
			sql.getCurrentConnection().commit(); 
		} catch (SQLException e) {
			e.printStackTrace();
			throw new Exception(e);
		} finally {
			// 트랜잭션 종료
			sql.endTransaction(); 
		}
    }
}
