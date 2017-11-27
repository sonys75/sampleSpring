package com.skhynix.hydesign.portal.biz.status.abnormal.scenario;

import java.io.UnsupportedEncodingException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.List;
import java.util.Locale;
import java.util.Map;

import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.MessageSource;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

import com.skhynix.hydesign.portal.biz.admin.system.code.CodeService;
import com.skhynix.hydesign.portal.common.constants.PortalConstants;
import com.skhynix.hydesign.portal.common.util.JsonUtil;

/**
 * 통계 > 이상징후 > 시나리오 관리
 * 
 * @author sonys75
 * @version 1.0
 * @since
 * @created 2017. 08. 14.
 */
@Controller
@RequestMapping(value = "/status/abnormal/scenario")
public class ScenarioController {

    /**
     * logger
     */
/*성능개선안
    private final Logger logger = LoggerFactory.getLogger(getClass());
*/
    /**
     * 생성자
     */
    public ScenarioController() {
        // Default Constructor
    }

    @Autowired
    private ScenarioService scenarioService;

    /**
     * CommonCode 서비스
     */
    @Autowired
    private CodeService codeService;
    
    /**
     * Message Source
     */
    @Autowired
    private MessageSource messageSource;

    /**
     * 통계 > 이상징후 > 시나리오 관리 화면
     * 
     * @return ModelAndView 시나리오 관리 화면
     */
    @RequestMapping(value = "/list")
    public ModelAndView list(Object requestMap) {

        ModelAndView mav = new ModelAndView();

        Map<String, Object> pMap = (Map<String, Object>)requestMap;

        mav.setViewName("status/abnormal/scenario/list");
        
        pMap.put("commonGroupCode", PortalConstants.GroupCode.ABNORMAL_TYPE);
        List<Object> typeCD = codeService.searchCommonCodeList(pMap);
        mav.addObject("typeCD", typeCD);
        
        pMap.put("commonGroupCode", PortalConstants.GroupCode.ABNORMAL_LEVEL);
        List<Object> levelCD = codeService.searchCommonCodeList(pMap);
        mav.addObject("levelCD", levelCD);
        
        return mav;
    }

    /**
     * 시나리오 목록 조회
     * 
     * @param requestMap Request객체(맵)
     * @return ModelAndView 시나리오 목록
     */
    @RequestMapping(value = "/jsonList")
    public ModelAndView jsonList(Object requestMap) {

        // 파라미터 맵 조회
        Map<String, Object> pMap = (Map<String, Object>)requestMap;

        ModelAndView mav = new ModelAndView();
        mav.setViewName(PortalConstants.View.JSON_VIEW);

        //시나리오 목록
        mav.addObject("scenarioList", scenarioService.scenarioList(pMap));

        return mav;
    }

    /**
     * 시나리오 목록 엑셀 다운로드
     * 
     * @param requestMap Request객체(맵)
     * @param response
     * @return
     * @throws UnsupportedEncodingException
     */
    @RequestMapping(value = "/excelDownload")
    public ModelAndView excelDownload(Object requestMap, HttpServletResponse response) throws UnsupportedEncodingException {
        // 파라미터 맵 조회
        Map<String, Object> pMap = (Map<String, Object>)requestMap;

        ModelAndView mav = new ModelAndView();
        mav.setViewName("status/abnormal/scenario/excelDownload");

        //시나리오 목록
        mav.addObject("excelDataList", scenarioService.scenarioList(pMap));

        SimpleDateFormat fileFormat = new SimpleDateFormat("yyyyMMdd_HHmm");
        Calendar cal = Calendar.getInstance();
        String today = fileFormat.format(cal.getTime());
        String fileName = "";
        fileName = "이상징후_시나리오_" + today + ".xls";
        fileName = new String(fileName.getBytes("euc-kr"), "8859_1");
        response.setHeader("Content-Disposition", "attachment;filename=" + fileName);
        response.setHeader("Content-Description", "JSP Generated Data");

        return mav;
    }
    
    /**
     * 시나리오 상세 조회
     * 
     * @param requestMap Request객체(맵)
     * @return ModelAndView 시나리오 팝업
     */
    @RequestMapping(value = "/editPopUp")
    public ModelAndView editPopUp(Object requestMap) {

        // 파라미터 맵 조회
        Map<String, Object> pMap = (Map<String, Object>)requestMap;
        
        ModelAndView mav = new ModelAndView();
        mav.setViewName(PortalConstants.View.JSON_VIEW);
 
        mav.addObject("scenarioData", scenarioService.selectScenarioDetail(pMap));

        return mav;
    }
    
    /**
     * 시나리오 저장
     * 
     * @param requestMap Request객체(맵)
     * @return ModelAndView 시나리오 팝업
     */
    @RequestMapping(value = "/save")
    public ModelAndView save(Object requestMap) {
        ModelAndView mav = new ModelAndView();
        mav.setViewName(PortalConstants.View.JSON_VIEW);

        // 파라미터 맵 조회
        Map<String, Object> pMap = (Map<String, Object>)requestMap;

        int updCnt = scenarioService.modifyScenario(pMap);

        // 수정을 성공한 경우
        if (updCnt > 0) {
            mav.addObject(PortalConstants.Response.RESULT_FG, PortalConstants.Response.SUCCESS);

            // 수정 결과 메시지 (다국어 지원)
            mav.addObject(PortalConstants.Response.RESULT_MSG,
                          messageSource.getMessage("common.res.apply.success", null, (Locale)pMap.get("locale")));
        } else { // 수정을 실패한 경우
            mav.addObject(PortalConstants.Response.RESULT_FG, PortalConstants.Response.FAIL);

            // 수정 결과 메시지 (다국어 지원)
            mav.addObject(PortalConstants.Response.RESULT_MSG,
                          messageSource.getMessage("common.res.modify.fail", null, (Locale)pMap.get("locale")));
        }
        return mav;
    }
}
