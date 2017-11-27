package com.skhynix.hydesign.portal.biz.status.abnormal.scenario;

import java.util.List;
import java.util.Map;

import org.apache.ibatis.session.SqlSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

/**
 * 통계 > 이상징후 > 시나리오 관리
 * 
 * @author sonys75
 * @version 1.0
 * @since
 * @created 2017. 08. 14.
 */
@Repository
public class ScenarioDao {

    /**
     * logger
     */
/*성능개선안
    private final Logger logger = LoggerFactory.getLogger(getClass());
*/
    /**
     * SqlSession
     */
    @Autowired
    private SqlSession sqlSession;

    /**
     * 생성자
     */
    public ScenarioDao() {
        // Default Constructor
    }

    /**
     * 시나리오 목록 조회
     * 
     * @return List 시나리오 목록 조회
     */
    public List<Object> scenarioList(Map<String, Object> pMap) {
        return sqlSession.selectList("status-abnormal-scenario.scenarioList", pMap);
    }

    /**
     * 시나리오 상세 조회
     * 
     * @param pMap 파라미터맵
     * @return Map 시나리오 상세 조회
     */
    public Map<String, Object> selectScenarioDetail(Map<String, Object> pMap) {
        return sqlSession.selectOne("status-abnormal-scenario.selectScenarioDetail", pMap);
    }
    
    /**
     * 시나리오 저장
     * 
     * @param pMap 파라미터맵
     * @return int 시나리오 저장 개수
     */
    public int modifyScenario(Map<String, Object> pMap) {
        return sqlSession.update("status-abnormal-scenario.modifyScenario", pMap);
    }
}
