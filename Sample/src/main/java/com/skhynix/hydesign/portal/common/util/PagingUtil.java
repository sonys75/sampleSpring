package com.skhynix.hydesign.portal.common.util;

import java.math.BigDecimal;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.lang3.StringUtils;
import org.apache.ibatis.session.SqlSession;

/**
 * pagging 관련 util
 * 
 * @author <a href="mailto:support@openit.co.kr">(주)오픈잇 | openit Inc.</a>
 * @version 1.00
 * @created 2014. 12. 24.
 */
public final class PagingUtil {

    /**
     * 현재 페이지
     */
    public static final String PAGE_NO = "pageNo";

    /**
     * 페이지 당 리스트 수
     */
    public static final String LIST_SIZE = "listSize";

    /**
     * page 처리용 first row name
     */
    public static final String FIRST_ROW = "firstRow";

    /**
     * page 처리용 last row name
     */
    public static final String LAST_ROW = "lastRow";

    /**
     * SQL list result
     */
    public static final String LIST = "list";

    /**
     * SQL list resultCount
     */
    public static final String LIST_COUNT = "listCount";

    /**
     * Default Constructor
     */
    private PagingUtil() {
        // Default Constructor
    }

    /**
     * paging 목록을 Map에 담아 반환한다.
     * 
     * @param sqlSession session 정보
     * @param sqlId 수행할 sql query id
     * @param pMap 파라미터 map
     * @return Map paging 목록과 카운트 수가 담겨있는 Map
     */
    public static Map<String, Object> selectPageList(SqlSession sqlSession, String sqlId, Map<String, Object> pMap) {

        String paramPageNo = (String)pMap.get(PagingUtil.PAGE_NO);
        String paramListSize = (String)pMap.get(PagingUtil.LIST_SIZE);

        int pageNo = StringUtils.isNumeric(paramPageNo) ? Integer.parseInt(paramPageNo) : 1;
        int listSize = StringUtils.isNumeric(paramListSize) ? Integer.parseInt(paramListSize) : 10;

        long listCount = (Long)sqlSession.selectOne(sqlId + "Count", pMap);

        pMap.put(PagingUtil.FIRST_ROW, Integer.valueOf((pageNo - 1) * listSize) + 1);
        pMap.put(PagingUtil.LAST_ROW, Integer.valueOf(listSize * pageNo));

        Map<String, Object> rMap = new HashMap<String, Object>();
        rMap.put(PagingUtil.LIST, sqlSession.selectList(sqlId, pMap));
        rMap.put(PagingUtil.LIST_COUNT, listCount);

        return rMap;
    }
    

    /**
     * paging 목록을 Map에 담아 반환한다.(목록쿼리에서 Count 추출)
     * 
     * @param sqlSession session 정보
     * @param sqlId 수행할 sql query id
     * @param pMap 파라미터 map
     * @return Map paging 목록과 카운트 수가 담겨있는 Map
     */
    public static Map<String, Object> selectPageListByOnce(SqlSession sqlSession, String sqlId, Map<String, Object> pMap) {
    	    	
        String paramPageNo = (String)pMap.get(PagingUtil.PAGE_NO);
        String paramListSize = (String)pMap.get(PagingUtil.LIST_SIZE);

        int pageNo = StringUtils.isNumeric(paramPageNo) ? Integer.parseInt(paramPageNo) : 1;
        int listSize = StringUtils.isNumeric(paramListSize) ? Integer.parseInt(paramListSize) : 10;

        pMap.put(PagingUtil.FIRST_ROW, Integer.valueOf((pageNo - 1) * listSize) + 1);
        pMap.put(PagingUtil.LAST_ROW, Integer.valueOf(listSize * pageNo));
        
    	List<Object> itemList = sqlSession.selectList(sqlId, pMap);
    	long listCount = 0;
    	if(itemList != null && itemList.size() > 0) {
    		HashMap<String, Object> tmpMap = (HashMap<String, Object>)itemList.get(0);
    		BigDecimal totCnt = (BigDecimal)tmpMap.get("TOT_CNT");
    		listCount = totCnt.longValue();
    	}

        Map<String, Object> rMap = new HashMap<String, Object>();
        rMap.put(PagingUtil.LIST, itemList);
        rMap.put(PagingUtil.LIST_COUNT, listCount);

        return rMap;
    }
}
