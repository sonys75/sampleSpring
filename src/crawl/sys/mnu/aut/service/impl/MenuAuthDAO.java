package crawl.sys.mnu.aut.service.impl;

import java.sql.SQLException;
import java.util.HashMap;
import java.util.List;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import com.ibatis.sqlmap.client.SqlMapClient;

import crawl.vo.VoAuth;
import crawl.vo.VoMenu;


/**
 * MenuAuth 처리 DAO 클래스
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
@Repository("menuAuthDAO")
public class MenuAuthDAO {
	/** log */
    protected static final Log LOG = LogFactory.getLog(MenuAuthDAO.class);
    
    @Autowired
	private SqlMapClient sql;
 
    @SuppressWarnings("unchecked")
	public List<VoMenu> selectListMenuInfoByAuthId(HashMap<String, String> hMap) throws Exception{
    	return (List<VoMenu>) sql.queryForList("MenuAuth.selectListMenuInfoByAuthId", hMap);
    }
    
    @SuppressWarnings("unchecked")
	public List<VoMenu> selectAllListMenuInfo(HashMap<String, String> hMap) throws Exception{
    	return (List<VoMenu>) sql.queryForList("MenuAuth.selectAllListMenuInfo", hMap);
    }
    
    @SuppressWarnings("unchecked")
	public List<VoMenu> selectListMenuAuth(HashMap<String, String> hMap) throws Exception{
    	return (List<VoMenu>) sql.queryForList("MenuAuth.selectListMenuAuth", hMap);
    }
    
    public VoAuth selectAuthByAuthId(HashMap<String, String> hMap) throws Exception{
    	return (VoAuth) sql.queryForObject("MenuAuth.selectAuthByAuthId", hMap);
    }

    public void deleteMenuAuthByAuthId(HashMap<String, String> hMap) throws Exception{
    	// Transaction 시작
		sql.startTransaction(); 
		// 여기서 autoCommit설정을 변경해준다.!!!
		sql.getCurrentConnection().setAutoCommit(false);
		// autoCommit의 설정 Commit
		sql.commitTransaction(); 
		
		try {
			sql.delete("MenuAuth.deleteMenuAuthByAuthId", hMap);
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
    
    public void insertMenuAuth(HashMap<String, String> hMap) throws Exception{
    	// Transaction 시작
		sql.startTransaction(); 
		// 여기서 autoCommit설정을 변경해준다.!!!
		sql.getCurrentConnection().setAutoCommit(false);
		// autoCommit의 설정 Commit
		sql.commitTransaction(); 
		
		try {
			sql.insert("MenuAuth.insertMenuAuth", hMap);
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

