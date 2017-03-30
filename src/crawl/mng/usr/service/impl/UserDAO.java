package crawl.mng.usr.service.impl;

import java.sql.SQLException;
import java.util.HashMap;
import java.util.List;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import com.ibatis.sqlmap.client.SqlMapClient;

import crawl.vo.VoUserInfo;


/**
 * 로그인 처리 DAO 클래스
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
@Repository("userDAO")
public class UserDAO {
	/** log */
    protected static final Log LOG = LogFactory.getLog(UserDAO.class);
    
    @Autowired
	private SqlMapClient sql;

    public int selectCntUserInfo(HashMap<String, String> hMap) throws Exception{
    	return Integer.parseInt( sql.queryForObject("User.selectCntUserInfo", hMap).toString() );
    }
    
    @SuppressWarnings("unchecked")
	public List<VoUserInfo> selectListUserInfo(HashMap<String, String> hMap) throws Exception{
    	return (List<VoUserInfo>) sql.queryForList("User.selectListUserInfo", hMap);
    }
    
    public VoUserInfo selectUserInfoByUserId(HashMap<String, String> hMap) throws Exception{
    	return (VoUserInfo) sql.queryForObject("User.selectUserInfoByUserId", hMap);
    }

    public void insertUserInfo(HashMap<String, String> hMap) throws Exception{
    	// Transaction 시작
		sql.startTransaction(); 
		// 여기서 autoCommit설정을 변경해준다.!!!
		sql.getCurrentConnection().setAutoCommit(false);
		// autoCommit의 설정 Commit
		sql.commitTransaction(); 
		
		try {
			sql.insert("User.insertUserInfo", hMap);
			
			sql.insert("User.insertUserAuth", hMap);
			
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
 
    public void updateUserInfoByUserId(HashMap<String, String> hMap) throws Exception{
    	// Transaction 시작
		sql.startTransaction(); 
		// 여기서 autoCommit설정을 변경해준다.!!!
		sql.getCurrentConnection().setAutoCommit(false);
		// autoCommit의 설정 Commit
		sql.commitTransaction(); 
		
		try {
			sql.update("User.updateUserInfoByUserId", hMap);
			
			sql.update("User.updateUserAuthByUserId", hMap);

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
    
    public void deleteUserInfoByUserId(HashMap<String, String> hMap) throws Exception{
    	// Transaction 시작
		sql.startTransaction(); 
		// 여기서 autoCommit설정을 변경해준다.!!!
		sql.getCurrentConnection().setAutoCommit(false);
		// autoCommit의 설정 Commit
		sql.commitTransaction(); 
		
		try {
			sql.delete("User.deleteUserInfoByUserId", hMap);
			
			sql.delete("User.deleteUserAuthByUserId", hMap);
			
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
    
    public int selectCntLowCorpInfoByCorpId(HashMap<String, String> hMap) throws Exception{
    	return Integer.parseInt( sql.queryForObject("User.selectCntLowCorpInfoByCorpId", hMap).toString() );
    }
}
