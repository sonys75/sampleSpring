package crawl.sys.res.mng.service.impl;

import java.sql.SQLException;
import java.util.HashMap;
import java.util.List;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import com.ibatis.sqlmap.client.SqlMapClient;

import crawl.vo.VoReso;


/**
 * Resource 처리 DAO 클래스
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
@Repository("resourceDAO")
public class ResourceDAO {
	/** log */
    protected static final Log LOG = LogFactory.getLog(ResourceDAO.class);
    
    @Autowired
	private SqlMapClient sql;

    public int selectCntReso(HashMap<String, String> hMap) throws Exception{
    	return Integer.parseInt( sql.queryForObject("Resource.selectCntReso", hMap).toString() );
    }
    
    @SuppressWarnings("unchecked")
	public List<VoReso> selectListReso(HashMap<String, String> hMap) throws Exception{
    	return (List<VoReso>) sql.queryForList("Resource.selectListReso", hMap);
    }
    
    public VoReso selectResoByResoId(HashMap<String, String> hMap) throws Exception{
    	return (VoReso) sql.queryForObject("Resource.selectResoByResoId", hMap);
    }
    
    @SuppressWarnings("unchecked")
	public List<VoReso> selectResoAuthByResoId(HashMap<String, String> hMap) throws Exception{
    	return (List<VoReso>) sql.queryForList("Resource.selectResoAuthByResoId", hMap);
    }
    
    @SuppressWarnings("unchecked")
	public List<VoReso> selectListAllResoAuth(HashMap<String, String> hMap) throws Exception{
    	return (List<VoReso>) sql.queryForList("Resource.selectListAllResoAuth", hMap);
    }
    
    public int selectCntResoInfoByResoPtn(HashMap<String, String> hMap) throws Exception{
    	return Integer.parseInt( sql.queryForObject("Resource.selectCntResoInfoByResoPtn", hMap).toString() );
    }

    public String insertResoInfo(HashMap<String, String> hMap) throws Exception{
    	// Transaction 시작
		sql.startTransaction(); 
		// 여기서 autoCommit설정을 변경해준다.!!!
		sql.getCurrentConnection().setAutoCommit(false);
		// autoCommit의 설정 Commit
		sql.commitTransaction(); 
		
		String reso_id = "";
		
		try {
			reso_id = (String) sql.insert("Resource.insertResoInfo", hMap);
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
		return reso_id;
    }
    
    public void insertResoAuth(HashMap<String, String> hMap) throws Exception{
    	// Transaction 시작
		sql.startTransaction(); 
		// 여기서 autoCommit설정을 변경해준다.!!!
		sql.getCurrentConnection().setAutoCommit(false);
		// autoCommit의 설정 Commit
		sql.commitTransaction(); 
		
		try {
			sql.insert("Resource.insertResoAuth", hMap);
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
    
    public int selectCntResoInfoByResoId(HashMap<String, String> hMap) throws Exception{
    	return Integer.parseInt( sql.queryForObject("Resource.selectCntResoInfoByResoId", hMap).toString() );
    }

    public void updateResoInfoByResoId(HashMap<String, String> hMap) throws Exception{
    	// Transaction 시작
		sql.startTransaction(); 
		// 여기서 autoCommit설정을 변경해준다.!!!
		sql.getCurrentConnection().setAutoCommit(false);
		// autoCommit의 설정 Commit
		sql.commitTransaction(); 
		
		try {
			sql.update("Resource.updateResoInfoByResoId", hMap);
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
    
    public void deleteResoAuthByResoId(HashMap<String, String> hMap) throws Exception{
    	// Transaction 시작
		sql.startTransaction(); 
		// 여기서 autoCommit설정을 변경해준다.!!!
		sql.getCurrentConnection().setAutoCommit(false);
		// autoCommit의 설정 Commit
		sql.commitTransaction(); 
		
		try {
			sql.delete("Resource.deleteResoAuthByResoId", hMap);
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
    
    public void deleteResoInfoByResoId(HashMap<String, String> hMap) throws Exception{
    	// Transaction 시작
		sql.startTransaction(); 
		// 여기서 autoCommit설정을 변경해준다.!!!
		sql.getCurrentConnection().setAutoCommit(false);
		// autoCommit의 설정 Commit
		sql.commitTransaction(); 
		
		try {
			sql.delete("Resource.deleteResoInfoByResoId", hMap);
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

