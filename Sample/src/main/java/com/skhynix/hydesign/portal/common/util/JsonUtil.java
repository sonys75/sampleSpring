/**
 * (주)오픈잇 | http://www.openit.co.kr
 * Copyright (c)2006-2015, openit Inc.
 * All rights reserved.
 */
package com.skhynix.hydesign.portal.common.util;

import java.util.List;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.servlet.ModelAndView;

import com.skhynix.hydesign.portal.common.constants.PortalConstants;

/**
 * json 공통화 클래스
 * 
 * @author <a href="mailto:support@openit.co.kr">(주)오픈잇 | openit Inc.</a>
 * @version 1.0
 * @created 2015. 4. 7.
 */
public class JsonUtil {

    private static final Logger logger = LoggerFactory.getLogger(JsonUtil.class);

    /**
     * JSON parsing 공통 함수 내용 입력
     * 
     * @param appendMaps Map - 파싱할 정보
     * @return
     */
    public static ModelAndView returnPageList(Map<String, Object>... appendMaps) {
        ModelAndView mav = new ModelAndView();
        mav.setViewName(PortalConstants.View.JSON_VIEW);

        for (Map map : appendMaps) {
            if (!map.containsKey("page") || map == null) {
                mav.addObject(PortalConstants.Response.RESULT_FG, PortalConstants.Response.FAIL);
            } else {
                long pageListCount = (long)map.get(PagingUtil.LIST_COUNT);
                String namespace = map.containsKey("listKey") ? map.get("listKey") + "." : "";
                if (pageListCount > 0) {
                    mav.addObject(PortalConstants.Response.RESULT_FG, PortalConstants.Response.SUCCESS);
                    if (map.containsKey(PagingUtil.LIST)) {
                        mav.addObject(namespace + "pageList", map.get(PagingUtil.LIST));
                        mav.addObject(namespace + "pageListCount", pageListCount);
                        mav.addObject(namespace + "totalCount", map.get("totalCount"));
                    } else {
                        logger.warn("Response has no data contains key [ " + PagingUtil.LIST + " ]");
                    }

                } else {
                    mav.addObject(PortalConstants.Response.RESULT_FG, PortalConstants.Response.NO_RESULT);
                    // 검색 결과 없음 메시지 (다국어 지원)
                    //mav.addObject(PortalConstants.Response.RESULT_MSG, messageSource.getMessage("common.res.search.no", null, (Locale)pMap.get("locale")));
                }
            }
        }
        return mav;
    }

    /**
     * JSON parsing 공통 함수 내용 입력
     * 
     * @param appendMaps Map - 파싱할 정보
     * @return
     */
    public static ModelAndView returnMavMulti(Object... appendMaps) {
        ModelAndView mav = new ModelAndView();
        mav.setViewName(PortalConstants.View.JSON_VIEW);

        for (Object node : appendMaps) {
            if (node instanceof java.util.Map) {
                Map map = (Map)node;
                if (!map.containsKey("page") || map == null) {
                    mav.addObject(PortalConstants.Response.RESULT_FG, PortalConstants.Response.FAIL);
                } else {
                    long pageListCount = (long)map.get(PagingUtil.LIST_COUNT);
                    String namespace = map.containsKey("listKey") ? map.get("listKey") + "." : "";
                    if (pageListCount > 0) {
                        mav.addObject(PortalConstants.Response.RESULT_FG, PortalConstants.Response.SUCCESS);
                        if (map.containsKey(PagingUtil.LIST)) {
                            mav.addObject(namespace + "pageList", map.get(PagingUtil.LIST));
                            mav.addObject(namespace + "pageListCount", pageListCount);
                            mav.addObject(namespace + "totalCount", map.get("totalCount"));
                        } else {
                            logger.warn("Response has no data contains key [ " + PagingUtil.LIST + " ]");
                        }

                    } else {
                        mav.addObject(PortalConstants.Response.RESULT_FG, PortalConstants.Response.NO_RESULT);
                        // 검색 결과 없음 메시지 (다국어 지원)
                        //mav.addObject(PortalConstants.Response.RESULT_MSG, messageSource.getMessage("common.res.search.no", null, (Locale)pMap.get("locale")));
                    }
                }
            } else if (node instanceof java.util.List) {
                List<Object> list = (List<Object>)node;

                if (list == null || list.isEmpty()) {
                    mav.addObject(PortalConstants.Response.RESULT_FG, PortalConstants.Response.FAIL);
                } else {
                    mav.addObject(PortalConstants.Response.RESULT_FG, PortalConstants.Response.SUCCESS);
                    mav.addObject("resultList", list);
                }
            }
        }
        return mav;
    }

    /**
     * 단일 건수에 대한 결과를 mav에 담아 리턴한다.
     * 
     * @param namespace
     * @param obj mpa or list
     * @return ModelAndView
     */
    public static ModelAndView returnMavSingle(Object obj) {

        ModelAndView mav = new ModelAndView();
        mav.setViewName(PortalConstants.View.JSON_VIEW);

        String responseMsg = "";//결과 메시지

        if (obj == null) {
            responseMsg = PortalConstants.Response.FAIL;
        } else if (obj instanceof java.util.Map) {
            Map map = (Map)obj;
            if (map.isEmpty() || map == null) {
                responseMsg = PortalConstants.Response.FAIL;
            } else {
                if (map.size() != 0) {
                    responseMsg = PortalConstants.Response.SUCCESS;
                    mav.addObject("result", map);
                } else {
                    responseMsg = PortalConstants.Response.NO_RESULT;
                }
            }
        } else if (obj instanceof java.util.List) {
            List<Object> list = (List<Object>)obj;
/* 성능개선안
            if (list == null) {
                responseMsg = PortalConstants.Response.FAIL;
            } else {
                if (list.size() != 0) {
                    responseMsg = PortalConstants.Response.SUCCESS;
                    mav.addObject("result", list);
                } else {
                    responseMsg = PortalConstants.Response.NO_RESULT;
                }
            }
*/
            if (list != null) {
                if(list.size() != 0){
                	responseMsg = PortalConstants.Response.SUCCESS;
                	mav.addObject("result", list);
                } 
                else {
                    responseMsg = PortalConstants.Response.NO_RESULT;
                } 
            }
               else {
                    responseMsg = PortalConstants.Response.FAIL;
            }
            

        } else if (obj instanceof Integer) {
            int num = (int)obj;

            if (num > 0) { // 등록 성공
                responseMsg = PortalConstants.Response.SUCCESS;
                mav.addObject("result", num);
            } else if (num == -1) {
                responseMsg = PortalConstants.Response.DUPLICATE;
            } else { // 등록 실패
                responseMsg = PortalConstants.Response.FAIL;
            }
        } else if (obj instanceof String) {//ssh로 디렉토리 생성시에만 사용하고있음
            String str = (String)obj;
            if (str == PortalConstants.Response.SUCCESS) {
                responseMsg = PortalConstants.Response.SUCCESS;
                mav.addObject("result", str);
            } else {
                responseMsg = PortalConstants.Response.SUCCESS;//실패 에러메시지를 받기위에 SUCCESS로함
                mav.addObject("result", str);
            }
        }

        mav.addObject(PortalConstants.Response.RESULT_FG, responseMsg);
        return mav;
    }
}
