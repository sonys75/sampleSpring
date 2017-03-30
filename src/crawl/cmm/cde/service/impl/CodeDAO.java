package crawl.cmm.cde.service.impl;

import java.util.HashMap;
import java.util.List;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import com.ibatis.sqlmap.client.SqlMapClient;

import crawl.vo.VoAuth;
import crawl.vo.VoCodeInfo;


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
@Repository("codeDAO")
public class CodeDAO {
	/** log */
    protected static final Log LOG = LogFactory.getLog(CodeDAO.class);
    
    @Autowired
	private SqlMapClient sql;

    public VoAuth selectAuthNo(HashMap<String, String> hMap) throws Exception{
    	return (VoAuth) sql.queryForObject("Code.selectAuthNo", hMap);
    }
    
    @SuppressWarnings("unchecked")
	public List<VoAuth> selectAuthList(HashMap<String, String> hMap) throws Exception{
    	return (List<VoAuth>) sql.queryForList("Code.selectAuthList", hMap);
    }
    
    @SuppressWarnings("unchecked")
	public List<VoCodeInfo> selectListCodeInfoByCodePart(HashMap<String, String> hMap) throws Exception{
    	return (List<VoCodeInfo>) sql.queryForList("Code.selectListCodeInfoByCodePart", hMap);
    }
    /*	
	public void insertCorpInfo(HashMap<String, String> hMap) throws Exception {
		// Transaction 시작
		sql.startTransaction();
		// 여기서 autoCommit설정을 변경해준다.!!!
		sql.getCurrentConnection().setAutoCommit(false);
		// autoCommit의 설정 Commit
		sql.commitTransaction();

		try {
			sql.insert("Corp.insertCorpInfo", hMap);
			// Transaction Commit!!
			sql.commitTransaction();
			// connection Commit!!
			sql.getCurrentConnection().commit();
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			// 트랜잭션 종료
			sql.endTransaction();
		}
	}
	*/
    
    public String selectServerUrl() throws Exception{
    	return (String) sql.queryForObject("Code.selectServerUrl");
    }
}
