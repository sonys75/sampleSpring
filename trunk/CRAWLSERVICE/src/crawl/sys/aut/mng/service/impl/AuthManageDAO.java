package crawl.sys.aut.mng.service.impl;

import java.sql.SQLException;
import java.util.HashMap;
import java.util.List;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import com.ibatis.sqlmap.client.SqlMapClient;

import crawl.vo.VoAuth;


/**
 * AuthManage 처리 DAO 클래스
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
@Repository("authManageDAO")
public class AuthManageDAO {
	/** log */
    protected static final Log LOG = LogFactory.getLog(AuthManageDAO.class);
    
    @Autowired
	private SqlMapClient sql;

    public int selectCntAuth(HashMap<String, String> hMap) throws Exception{
    	return Integer.parseInt( sql.queryForObject("AuthManage.selectCntAuth", hMap).toString() );
    }
    
    @SuppressWarnings("unchecked")
	public List<VoAuth> selectListAuth(HashMap<String, String> hMap) throws Exception{
    	return (List<VoAuth>) sql.queryForList("AuthManage.selectListAuth", hMap);
    }
    
    public VoAuth selectAuthByAuthNo(HashMap<String, String> hMap) throws Exception{
    	return (VoAuth) sql.queryForObject("AuthManage.selectAuthByAuthNo", hMap);
    }
    
    public int selectCntAuthByAuthId(HashMap<String, String> hMap) throws Exception{
    	return Integer.parseInt( sql.queryForObject("AuthManage.selectCntAuthByAuthId", hMap).toString() );
    }

    public String insertAuth(HashMap<String, String> hMap) throws Exception{
    	// Transaction 시작
		sql.startTransaction(); 
		// 여기서 autoCommit설정을 변경해준다.!!!
		sql.getCurrentConnection().setAutoCommit(false);
		// autoCommit의 설정 Commit
		sql.commitTransaction(); 
		
		String auth_no = "";
		
		try {
			auth_no = (String) sql.insert("AuthManage.insertAuth", hMap);
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
		return auth_no;
    }

    public int selectCntAuthByUpAuthNo(HashMap<String, String> hMap) throws Exception{
    	return Integer.parseInt( sql.queryForObject("AuthManage.selectCntAuthByUpAuthNo", hMap).toString() );
    }
    
    public void updateAuthByAuthNo(HashMap<String, String> hMap) throws Exception{
    	// Transaction 시작
		sql.startTransaction(); 
		// 여기서 autoCommit설정을 변경해준다.!!!
		sql.getCurrentConnection().setAutoCommit(false);
		// autoCommit의 설정 Commit
		sql.commitTransaction(); 
		
		try {
			sql.update("AuthManage.updateAuthByAuthNo", hMap);
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
    
    public void deleteAuthByAuthNo(HashMap<String, String> hMap) throws Exception{
    	// Transaction 시작
		sql.startTransaction(); 
		// 여기서 autoCommit설정을 변경해준다.!!!
		sql.getCurrentConnection().setAutoCommit(false);
		// autoCommit의 설정 Commit
		sql.commitTransaction(); 
		
		try {
			sql.delete("AuthManage.deleteAuthByAuthNo", hMap);
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
    
    public void updateAuthLvl(HashMap<String, String> hMap) throws Exception{
    	// Transaction 시작
		sql.startTransaction(); 
		// 여기서 autoCommit설정을 변경해준다.!!!
		sql.getCurrentConnection().setAutoCommit(false);
		// autoCommit의 설정 Commit
		sql.commitTransaction(); 
		
		try {
			/*
			1	0	1	SUPERADMIN	슈퍼관리자
			2	1	2	SITEADMIN	사이트관리자
			3	2	3	ROADADMIN	ROAD관리자
			4	3	4	RESTADMIN	REST관리자
			5	4	5	RESTUSER	REST사용자
			6	5	6	AUTHENTICATED	로그인사용자
			7	6	7	ANONYMOUS	모든사용자
			8	2	3	FOODADMIN	음식점관리자
			11	8	4	CHINA	중국음식점
			12	8	4	KOREAN	한식전문점
			*/
			if("I".equals(hMap.get("mode"))){
				//up_auth_id > AUTHENTICATED 삭제 후 up_auth_id > auth_id , auth_id > AUTHENTICATED 추가
				hMap.put("parentAuthId", hMap.get("upAuthId"));
				hMap.put("childAuthId", "AUTHENTICATED");
				sql.delete("AuthManage.deleteAuthLvlByUpChild", hMap);
				
				hMap.put("parentAuthId", hMap.get("upAuthId"));
				hMap.put("childAuthId", hMap.get("authId"));
				sql.insert("AuthManage.insertAuthLvlByUpChild", hMap);
				
				hMap.put("parentAuthId", hMap.get("authId"));
				hMap.put("childAuthId", "AUTHENTICATED");
				sql.insert("AuthManage.insertAuthLvlByUpChild", hMap);
			}else if("U".equals(hMap.get("mode"))){
				//auth_id > AUTHENTICATED, up_auth_id > auth_id 삭제 후 up_auth_id > auth_id , auth_id > AUTHENTICATED 추가
				hMap.put("parentAuthId", hMap.get("authId"));
				hMap.put("childAuthId", "AUTHENTICATED");
				sql.delete("AuthManage.deleteAuthLvlByUpChild", hMap);
				
				hMap.put("parentAuthId", hMap.get("upAuthId"));
				hMap.put("childAuthId", hMap.get("authId"));
				sql.delete("AuthManage.deleteAuthLvlByUpChild", hMap);
				
				hMap.put("parentAuthId", hMap.get("upAuthId"));
				hMap.put("childAuthId", hMap.get("authId"));
				sql.insert("AuthManage.insertAuthLvlByUpChild", hMap);
				
				hMap.put("parentAuthId", hMap.get("authId"));
				hMap.put("childAuthId", "AUTHENTICATED");
				sql.insert("AuthManage.insertAuthLvlByUpChild", hMap);
			}else if("D".equals(hMap.get("mode"))){
				//auth_id > AUTHENTICATED, up_auth_id > auth_id 삭제 후 up_auth_id > AUTHENTICATED 추가
				hMap.put("parentAuthId", hMap.get("authId"));
				hMap.put("childAuthId", "AUTHENTICATED");
				sql.delete("AuthManage.deleteAuthLvlByUpChild", hMap);
				
				hMap.put("parentAuthId", hMap.get("upAuthId"));
				hMap.put("childAuthId", hMap.get("authId"));
				sql.delete("AuthManage.deleteAuthLvlByUpChild", hMap);

				hMap.put("parentAuthId", hMap.get("upAuthId"));
				hMap.put("childAuthId", "AUTHENTICATED");
				//상위 id 검사 
				int nLvlCnt = Integer.parseInt( sql.queryForObject("AuthManage.selectCntAuthLvlByUpUpChild", hMap).toString() );
				if(nLvlCnt==0){
					sql.insert("AuthManage.insertAuthLvlByUpChild", hMap);
				}
			}
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

