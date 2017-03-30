package crawl.crn.nws.service.impl;

import java.sql.SQLException;
import java.util.HashMap;
import java.util.List;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import com.ibatis.sqlmap.client.SqlMapClient;

import crawl.vo.VoNews;


/**
 * Code 처리 DAO 클래스
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
@Repository("newsDAO")
public class NewsDAO {
	/** log */
    protected static final Log LOG = LogFactory.getLog(NewsDAO.class);
    
    @Autowired
	private SqlMapClient sql;
    
    @SuppressWarnings("unchecked")
   	public List<VoNews> selectListNewsRss(HashMap<String, String> hMap) throws Exception{
       	return (List<VoNews>) sql.queryForList("News.selectListNewsRss", hMap);
    }

    public void insertNewsRss(HashMap<String, String> hMap) throws Exception{
    	// Transaction 시작
		sql.startTransaction();
		// 여기서 autoCommit설정을 변경해준다.!!!
		sql.getCurrentConnection().setAutoCommit(false);
		// autoCommit의 설정 Commit
		sql.commitTransaction();

		try {
			sql.insert("News.insertNewsRss", hMap);
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

    public void deleteNewsRss() throws Exception{
    	// Transaction 시작
		sql.startTransaction();
		// 여기서 autoCommit설정을 변경해준다.!!!
		sql.getCurrentConnection().setAutoCommit(false);
		// autoCommit의 설정 Commit
		sql.commitTransaction();

		try {
			sql.delete("News.deleteNewsRss");
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
