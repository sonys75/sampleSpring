package com.skhynix.hydesign.portal.biz.status.abnormal.scenario;

import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * 통계 > 이상징후 > 시나리오 관리
 * 
 * @author sonys75
 * @version 1.0
 * @since
 * @created 2017. 08. 14.
 */
@Service
public class ScenarioService {

    /**
     * logger
     */
/*성능개선안
    private final Logger logger = LoggerFactory.getLogger(getClass());
*/
    /**
     * 시나리오 DAO
     */
    @Autowired
    private ScenarioDao scenarioDao;

    /**
     * 생성자
     */
    public ScenarioService() {
        // Default Constructor
    }

    /**
     * 시나리오 목록 조회
     * 
     * @return List 시나리오 목록 조회
     */
    public List<Object> scenarioList(Map<String, Object> pMap) {
        return scenarioDao.scenarioList(pMap);
    }

    /**
     * 시나리오 상세 조회
     * 
     * @param pMap 파라미터맵
     * @return Map 시나리오 상세 정보
     */
    public Map<String, Object> selectScenarioDetail(Map<String, Object> pMap) {
        Map<String, Object> result = scenarioDao.selectScenarioDetail(pMap);
        return result;
    }
    
    /**
     * 시나리오 저장
     * 
     * @param pMap 파라미터맵
     * @return Map 시나리오 저장
     */
    public int modifyScenario(Map<String, Object> pMap) {
        return scenarioDao.modifyScenario(pMap);
    }
}
