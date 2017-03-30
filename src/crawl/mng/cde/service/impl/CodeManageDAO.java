package crawl.mng.cde.service.impl;

import java.sql.SQLException;
import java.util.HashMap;
import java.util.List;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import com.ibatis.sqlmap.client.SqlMapClient;

import crawl.vo.VoCodeInfo;


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
@Repository("codeManageDAO")
public class CodeManageDAO {
	/** log */
    protected static final Log LOG = LogFactory.getLog(CodeManageDAO.class);
    
    @Autowired
	private SqlMapClient sql;

    public int selectCntCodePart(HashMap<String, String> hMap) throws Exception{
    	return Integer.parseInt( sql.queryForObject("CodeManage.selectCntCodePart", hMap).toString() );
    }
    
    @SuppressWarnings("unchecked")
   	public List<VoCodeInfo> selectListCodePart(HashMap<String, String> hMap) throws Exception{
       	return (List<VoCodeInfo>) sql.queryForList("CodeManage.selectListCodePart", hMap);
    }
    
    public VoCodeInfo selectCodePartByCodePart(HashMap<String, String> hMap) throws Exception{
    	return (VoCodeInfo) sql.queryForObject("CodeManage.selectCodePartByCodePart", hMap);
    }
    
    public void insertCodePart(HashMap<String, String> hMap) throws Exception{
    	// Transaction 시작
		sql.startTransaction();
		// 여기서 autoCommit설정을 변경해준다.!!!
		sql.getCurrentConnection().setAutoCommit(false);
		// autoCommit의 설정 Commit
		sql.commitTransaction();

		try {
			sql.insert("CodeManage.insertCodePart", hMap);
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
    
    public void updateCodePartByCodePart(HashMap<String, String> hMap) throws Exception{
    	// Transaction 시작
		sql.startTransaction();
		// 여기서 autoCommit설정을 변경해준다.!!!
		sql.getCurrentConnection().setAutoCommit(false);
		// autoCommit의 설정 Commit
		sql.commitTransaction();

		try {
			sql.update("CodeManage.updateCodePartByCodePart", hMap);
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
    
    public void deleteCodePartByCodePart(HashMap<String, String> hMap) throws Exception{
    	// Transaction 시작
		sql.startTransaction();
		// 여기서 autoCommit설정을 변경해준다.!!!
		sql.getCurrentConnection().setAutoCommit(false);
		// autoCommit의 설정 Commit
		sql.commitTransaction();

		try {
			sql.delete("CodeManage.deleteCodePartByCodePart", hMap);
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
    
    public int selectCntCodeInfo(HashMap<String, String> hMap) throws Exception{
    	return Integer.parseInt( sql.queryForObject("CodeManage.selectCntCodeInfo", hMap).toString() );
    }
    
    @SuppressWarnings("unchecked")
   	public List<VoCodeInfo> selectListCodeInfo(HashMap<String, String> hMap) throws Exception{
       	return (List<VoCodeInfo>) sql.queryForList("CodeManage.selectListCodeInfo", hMap);
    }
    
    @SuppressWarnings("unchecked")
   	public List<VoCodeInfo> selectListCodeInfoByCodePart(HashMap<String, String> hMap) throws Exception{
       	return (List<VoCodeInfo>) sql.queryForList("CodeManage.selectListCodeInfoByCodePart", hMap);
    }
    
    public VoCodeInfo selectCodeInfoByCode(HashMap<String, String> hMap) throws Exception{
    	return (VoCodeInfo) sql.queryForObject("CodeManage.selectCodeInfoByCode", hMap);
    }

    public void insertCodeInfo(HashMap<String, String> hMap) throws Exception{
    	// Transaction 시작
		sql.startTransaction();
		// 여기서 autoCommit설정을 변경해준다.!!!
		sql.getCurrentConnection().setAutoCommit(false);
		// autoCommit의 설정 Commit
		sql.commitTransaction();

		try {
			sql.insert("CodeManage.insertCodeInfo", hMap);
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
    
    public void updateCodeInfoByCode(HashMap<String, String> hMap) throws Exception{
    	// Transaction 시작
		sql.startTransaction();
		// 여기서 autoCommit설정을 변경해준다.!!!
		sql.getCurrentConnection().setAutoCommit(false);
		// autoCommit의 설정 Commit
		sql.commitTransaction();

		try {
			sql.update("CodeManage.updateCodeInfoByCode", hMap);
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
    
    public void deleteCodeInfoByCode(HashMap<String, String> hMap) throws Exception{
    	// Transaction 시작
		sql.startTransaction();
		// 여기서 autoCommit설정을 변경해준다.!!!
		sql.getCurrentConnection().setAutoCommit(false);
		// autoCommit의 설정 Commit
		sql.commitTransaction();

		try {
			sql.delete("CodeManage.deleteCodeInfoByCode", hMap);
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
