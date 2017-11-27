/**
 * (주)오픈잇 | http://www.openit.co.kr
 * Copyright (c)2006-2014, openit Inc.
 * All rights reserved.
 */
package com.skhynix.hydesign.portal.common.constants;

/**
 * 상수 정의 클래스
 * 
 * @author <a href="mailto:support@openit.co.kr">(주)오픈잇 | openit Inc.</a>
 * @version 1.00
 * @created 2014. 12. 24.
 */
public final class PortalConstants {

    /**
     * Default Constructor
     */
    public PortalConstants() {
        // Default Constructor
    }

    /**
     * View 객체 상수
     * 
     * @author <a href="mailto:support@openit.co.kr">(주)오픈잇 | openit Inc.</a>
     * @version 1.00
     * @created 2014. 12. 24.
     */
    public final class View {

        /**
         * Default Constructor
         */
        public View() {
            // Default Constructor
        }

        /**
         * json view
         */
        public static final String JSON_VIEW = "jsonView";

        /**
         * download view
         */
        public static final String DOWNLOAD_VIEW = "downloadView";

        /**
         * image view
         */
        public static final String IMAGE_VIEW = "imageView";
    }

    /**
     * key 상수
     * 
     * @author <a href="mailto:support@openit.co.kr">(주)오픈잇 | openit Inc.</a>
     * @version 1.00
     * @created 2014. 12. 24.
     */
    public final class Key {

        /**
         * Default Constructor
         */
        public Key() {
            // Default Constructor   
        }

        /**
         * session key
         */
        public static final String SESSION_KEY = "sessKey";

        /**
         * sso auth key
         */
        public static final String SSO_AUTH_KEY = "ssoauthkey";

    }

    /**
     * 응답 관련 상수
     * 
     * @author <a href="mailto:support@openit.co.kr">(주)오픈잇 | openit Inc.</a>
     * @version 1.00
     * @created 2014. 12. 24.
     */
    public final class Response {

        /**
         * Default Constructor
         */
        public Response() {
            // Default Constructor
        }

        /**
         * 응답 결과
         */
        public static final String RESULT = "result";
        
        /**
         * 응답 결과 여부
         */
        public static final String RESULT_FG = "resultFg";

        /**
         * 응답 결과 메시지
         */
        public static final String RESULT_MSG = "resultMsg";

        /**
         * 응답 결과 여부 - 성공
         */
        public static final String SUCCESS = "SS";

        /**
         * 응답 결과 여부 - 실패
         */
        public static final String FAIL = "FL";

        /**
         * 응답 결과 여부 - 중복
         */
        public static final String DUPLICATE = "DP";

        /**
         * 응답 결과 여부 - 중복되지 않음
         */
        public static final String NOT_DUPLICATE = "NDP";

        /**
         * 응답 결과 여부 - 존재
         */
        public static final String EXIST = "EX";

        /**
         * 응답 결과 여부 - 존재하지 않음
         */
        public static final String NOT_EXIST = "NEX";

        /**
         * 응답 결과 여부 - 하위 카테고리 존재 상태 (카테고리 삭제 여부 확인)
         */
        public static final String EXIST_CHILD = "EC";

        /**
         * 응답 결과 여부 - 매핑된 상태 (카테고리 삭제 여부 확인)
         */
        public static final String EXIST_MAPPING = "EM";

        /**
         * 응답 결과 여부 - 검색 결과 없음
         */
        public static final String NO_RESULT = "NO";

        /**
         * 응답 결과 여부 - 삭제된 IP
         */
        public static final String DELETE_IP = "DI";

        /**
         * 응답 결과 여부 - 삭제할수 없는 데이터
         */
        public static final String NO_DELETE = "ND";

        /**
         * 응답 결과 여부 - Support 카테고리 매핑된 담당자 존재 상태
         */
        public static final String EXIST_SUPPORT_MANAGER = "ESM";

        /**
         * 응답 결과 여부 - Support 카테고리 요청 글 존재 상태
         */
        public static final String EXIST_SUPPORT_REQUEST = "ESR";

        /**
         * 응답 결과 여부 - 삭제될 담당자가 접수 중인 업무 존재 상태
         */
        public static final String EXIST_SUPPORT_PROCESS = "ESP";

        /**
         * 응답 결과 여부 - 현재 업무 상태 (처리중 상태)
         */
        public static final String PROCESS_STATUS = "PS";

        /**
         * 응답 결과 여부 - 현재 업무 상태 (재배정 상태)
         */
        public static final String REASSIGN_STATUS = "RS";

        /**
         * 응답 결과 여부 - 현재 업무 상태 (취소 상태)
         */
        public static final String CANCEL_STATUS = "CS";

        /**
         * 응답 결과 여부 - Support 그 외의 상태 (처리완료, 부결)
         */
        public static final String OTHER_STATUS = "OS";

        /**
         * 파일업로드 유효성 여부 key
         */
        public static final String VALID = "VALID";

        /**
         * 파일업로드 유효성 여부 - 보안 확장자
         */
        public static final String SECURITY_EXT = "SECURITY";

        /**
         * 파일업로드 유효성 여부 - 보안 확장자
         */
        public static final String FILE_NAME = "NAME";

        /**
         * 파일업로드 유효성 여부 - 파일크기 초과
         */
        public static final String SIZE_OVER = "OVER";

        /**
         * 파일업로드 유효성 여부 - 파일크기 0
         */
        public static final String SIZE_ZERO = "ZERO";

        /**
         * 파일업로드 유효성 여부 - 파일확장자
         */
        public static final String FILE_EXT = "EXT";

        /**
         * 응답 결과 여부 - Soc 카테고리 매핑된 담당자 존재 상태
         */
        public static final String EXIST_SOC_MANAGER = "ESM";

        /**
         * 응답 결과 여부 - Soc 카테고리 요청 글 존재 상태
         */
        public static final String EXIST_SOC_REQUEST = "ESR";

        /**
         * 우선순위 갯수 초과
         */
        public static final String OVER_COUNT = "OV_CNT";

        /**
         * 우선순위 PORTION 초과
         */
        public static final String OVER_PORTION = "OV_POR";
        
        /**
         * 차단
         */
        public static final String BLOCK = "BK";
    }

    /**
     * 공통 그룹코드
     * 
     * @author <a href="mailto:support@openit.co.kr">(주)오픈잇 | openit Inc.</a>
     * @version 1.00
     * @created 2014. 12. 24.
     */
    public final class GroupCode {

        /**
         * Default Constructor
         */
        public GroupCode() {
            // Default Constructor
        }

        /**
         * 도입방식
         */
        public static final String INTRO_METHOD = "0001";

        /**
         * Technology
         */
        public static final String TECHNOLOGY = "0002";

        /**
         * Format
         */
        public static final String FORMAT = "0003";

        /**
         * Device
         */
        public static final String DEVICE = "0004";

        /**
         * 정보유형코드
         */
        public static final String INFO_PRTN = "0005";

        /**
         * IP카테고리상태코드
         */
        public static final String IP_CTGR_STATUS = "0006";

        /**
         * IP타입상태코드
         */
        public static final String IP_TYPE_STATUS = "0007";

        /**
         * 요청상태코드
         */
        public static final String REQ_STATUS = "0011";

        /**
         * 승인요청상태코드
         */
        public static final String APPR_REQ_STATUS = "0012";

        /**
         * 승인자코드
         */
        public static final String APPROVER = "0013";

        /**
         * 승인상태코드
         */
        public static final String APPR_STATUS = "0014";

        /**
         * 사용자상태코드
         */
        public static final String EMP_STATUS = "0021";

        /**
         * 알림타입코드
         */
        public static final String NOTI_STATUS = "0036";

        /**
         * 뉴스레터 구분코드
         */
        public static final String NEWS_TYPE = "0041";

        /**
         * 블로그 구분코드
         */
        public static final String BLOG_TYPE = "0042";

        /**
         * Q&A 구분코드
         */
        public static final String QNA_TYPE = "0043";

        /**
         * 업무지원 요청 상태 코드
         */
        public static final String SUPPORT_REQ_STATUS = "0051";

        /**
         * 긴급도 코드
         */
        public static final String EMERGENCY_TYPE = "0052";

        /**
         * 관리자 지정 항목 입력 방식 구분 코드
         */
        public static final String SUPPORT_DEFINE_INPUT_TYPE = "0053";

        /**
         * Support 카테고리 상태 코드
         */
        public static final String SUPPORT_CTGR_STATUS = "0054";

        /**
         * Support 담당자 구분 코드
         */
        public static final String SUPPORT_MANAGER_TYPE = "0055";

        /**
         * 업무지원 처리 상태 코드
         */
        public static final String SUPPORT_PRC_STATUS = "0056";

        /**
         * 난이도 코드
         */
        public static final String DIFFICULTY_LEVEL = "0057";

        /**
         * 보안등급
         */
        public static final String ACL_LEVEL = "1000";

        /**
         * 접근그룹결재자관리
         */
        public static final String ALC_APPROVAL = "1005";

        /**
         * 프로젝트 전체 상태
         */
        public static final String PROJECT_ALL_STATUS = "1018";

        /**
         * 개발구분
         */
        public static final String DEV_DIST_CODE = "1029";

        /**
         * PRODUCT_FAMILY PRODUCT_FAMILY 선택에 따른 하위 매핑 값(병렬처리) DRAM,ReRAM,PCRAM,STT-MRAM > RNR(XRAM_RNR:1311) >
         * APPLICATION(XRAM_APPLICATION:1401) > PRODUCTMODE(XRAM_PRODUCT_MODE:1461) > TECHNOLOGY(XRAM_TECHNOLOGY:1521) >
         * DENSITY(XRAM_DENSITY:1701) Flash > RNR(FALSH_RNR:1312) > APPLICATION(FLASH_APPLICATION:1420) >
         * PRODUCTMODE(XRAM_PRODUCT_MODE:1461) > TECHNOLOGY(FLASH_TECHNOLOGY:1600) > DENSITY(FLASH_DENSITY:1740)
         * SYSTEMIC > RNR(SYSTEMIC_RNR:1313) > APPLICATION(SYSTEMIC_APPLICATION:1440) > PRODUCTMODE(사용않함) >
         * TECHNOLOGY(SYSTEMIC_TECHNOLOGY:1650) > DENSITY(SYSTEMIC_DENSITY:1780)
         */
        public static final String PRODUCTFAMILY_DIST_CODE = "1009";

        /**
         * RNR구분코드
         */
        public static final String DRAM_RNR = "1311";

        public static final String FLASH_RNR = "1312";

        public static final String SYSTEMIC_RNR = "1313";

        public static final String RAWNAND_RNR = "1314";

        public static final String RERAM_RNR = "1394";

        public static final String PCRAM_RNR = "1824";

        public static final String STTMRAM_RNR = "1830";

        public static final String ETC_ETC_RNR = "2212";

        public static final String ETC_DRAM_RNR = "2213";

        public static final String ETC_NAND_RNR = "2370";

        public static final String SRAM_RNR = "2628";

        /**
         * Application
         */
        public static final String DRAM_APPLICATION = "1401";

        public static final String RERAM_APPLICATION = "1408";

        public static final String PCRAM_APPLICATION = "1412";

        public static final String STTMRAM_APPLICATION = "1415";

        public static final String FLASH_APPLICATION = "1420";

        public static final String RAWNAND_APPLICATION = "1430";

        public static final String SYSTEMIC_APPLICATION = "1440";

        public static final String ETC_ETC_APPLICATION = "2214";

        public static final String ETC_DRAM_APPLICATION = "2215";

        public static final String ETC_NAND_APPLICATION = "2275";

        public static final String SRAM_APPLICATION = "2629";

        /**
         * Product Mode
         */
        public static final String RERAM_PRODUCT_MODE = "1445";

        public static final String PCRAM_PRODUCT_MODE = "1450";

        public static final String STTMRAM_PRODUCT_MODE = "1455";

        // FLASH일 경우 PRODUCTMODE > CELLTYPE
        public static final String FLASH_PRODUCT_MODE = "1500";

        public static final String DRAM_PRODUCT_MODE = "1461";

        public static final String RAWNAND_PRODUCT_MODE = "2060";

        public static final String ETC_ETC_PRODUCT_MODE = "2216";

        public static final String ETC_DRAM_PRODUCT_MODE = "2217";

        public static final String ETC_NAND_PRODUCT_MODE = "2274";

        public static final String SRAM_PRODUCT_MODE = "2630";

        /**
         * TECHNOLOGY
         */
        public static final String DRAM_TECHNOLOGY = "1521";

        public static final String RERAM_TECHNOLOGY = "1550";

        public static final String PCRAM_TECHNOLOGY = "1560";

        public static final String STTMRAM_TECHNOLOGY = "1570";

        public static final String FLASH_TECHNOLOGY = "1600";

        public static final String SYSTEMIC_TECHNOLOGY = "1650";

        public static final String RAWNAND_TECHNOLOGY = "2070";

        public static final String ETC_ETC_TECHNOLOGY = "2263";

        public static final String ETC_DRAM_TECHNOLOGY = "2268";

        public static final String ETC_NAND_TECHNOLOGY = "2271";

        public static final String SRAM_TECHNOLOGY = "2606";

        /**
         * DENSITY
         */
        public static final String DRAM_DENSITY = "1701";

        public static final String RERAM_DENSITY = "1730";

        public static final String PCRAM_DENSITY = "1735";

        public static final String FLASH_DENSITY = "1740";

        public static final String STTMRAM_DENSITY = "1770";

        // SYSTEMIC일 경우 DENSITY > RESOLUTION
        public static final String SYSTEMIC_DENSITY = "1780";

        public static final String RAWNAND_DENSITY = "2100";

        public static final String ETC_ETC_DENSITY = "2223";

        public static final String ETC_DRAM_DENSITY = "2224";

        public static final String ETC_NAND_DENSITY = "2276";
        
        public static final String SRAM_DENSITY = "2631";

        /**
         * 프로젝트 구분코드(SmartPiM, 일반/Spot프로젝트)
         */
        public static final String PROJECT_DIST_CODE = "1065";

        /**
         * 프로젝트 보안설정구분(일반/비공개...)
         */
        public static final String AUTH_SET_DIST_CODE = "1069";

        /**
         * 멤버 변경 이력 구분코드(삭제,추가,정보변경)
         */
        //public static final String MEMBER_HIST_DIST_CODE = "1082";

        /**
         * 권한구분코드(R, R/W ...)
         */
        //public static final String AUTH_DIST_CODE = "1087";        

        /**
         * 승인상태구분코드
         */
        public static final String APPR_STATUS_DIST_CODE = "1092";

        /**
         * 승인요청구분코드
         */
        public static final String REQ_DIST_CODE = "1099";

        /**
         * 구성원구분코드
         */
        //public static final String MEMBER_DIST_CODE = "1113";      

        /**
         * 결재부가정보구분코드
         */
        public static final String ADD_INFO_DIST_CODE = "1119";

        /**
         * 게시판구분코드(공지사항, Guide, FAQ)
         */
        public static final String BBS_DIST_CODE = "1150";

        /**
         * 게시판 유형구분코드(일반/프로젝트/도면열람/Support...)
         */
        public static final String NOTICE_CATG_DIST_CODE = "1151";

        public static final String GUIDE_CATG_DIST_CODE = "1152";

        public static final String FAQ_CATG_DIST_CODE = "1153";
        
        public static final String VOC_CATG_DIST_CODE = "2747";

        public static final String DATA_CATG_DIST_CODE = "2786"; // 2017.06.07 자료실
        
        /**
         * SDP 유형구분코드(일반/프로젝트/도면열람/Support...)
         */
        public static final String DESIGN_WORK_FLOW_DIST_CODE = "2911";
        public static final String CIRCUIT_DESIGN_GUIDE_DIST_CODE = "2912";
        public static final String PNYSICAL_DESIGN_GUIDE_DIST_CODE = "2913";
        public static final String BEST_PRACTICE_DIST_CODE = "2914";
        public static final String SDP_FAQ_DIST_CODE = "2915";
        
        /**
         * 서버구분코드(프로젝트ECS, 팀ECS, 도면열람ECS)
         */
        public static final String SERVER_DIST_CODE = "1200";

        /**
         * 그룹구분코드(그룹, 팀, 개인)
         */
        public static final String GROUP_DIST_CODE = "1220";

        /**
         * 계정구분코드(개인계정, 공용계정)
         */
        public static final String ACC_DIST_CODE = "1240";

        /**
         * 결재라인구분코드(승인자, 협조자1, 협조자2, 참조자)
         */
        public static final String APPR_LINE_DIST_CODE = "1250";

        /**
         * 위임사유구분코드(출장, 휴가, 권한위임)
         */
        public static final String MANDATE_REASON_DIST_CODE = "1350";

        /**
         * 위임역할구분코드(PC전송권한위임, PC전송외 권한위임)
         */
        public static final String MANDATE_ROLE_DIST_CODE = "1800";

        /**
         * FRAME/TP구분코드(FRAME, TP)
         */
        public static final String FT_DIST_CODE = "1820";

        /**
         * WUXI TECH 구분코드
         */
        public static final String WUXI_TECH_DIST_CODE = "1870";

        /**
         * 알림구분코드
         */
        public static final String ALARM_DIST_CODE = "1900";

        /**
         * 이메일타입구분코드
         */
        public static final String EMAIL_TYPE_DIST_CODE = "1910";

        /**
         * 이벤트구분코드
         */
        public static final String EVENT_DIST_CODE = "1920";

        /**
         * 사용이력구분코드
         */
        public static final String USED_DIST_CODE = "1930";

        /**
         * 해외전송구분코드
         */
        public static final String OVERSEA_DIST_CODE = "1940";

        /**
         * OPC/MASK구분코드
         */
        public static final String OPCMASK_DIST_CODE = "1945";

        /**
         * 프로젝트외전송구분코드
         */
        public static final String EXCEPTPRJ_DIST_CODE = "2185";

        /**
         * OS VERSION구분코드
         */
        public static final String OS_VERSION_DIST_CODE = "1952";

        /**
         * Soc 카테고리 상태 코드
         */
        public static final String SOC_CTGR_STATUS = "2020";

        /**
         * Storage 항목구분 코드
         */
        public static final String ITEM_DIST_CODE = "2000";

        /**
         * IP Type 코드
         */
        public static final String IP_TYPE_CODE = "2130";

        /**
         * 업무지원 요청유형 분류코드
         */
        public static final String REQ_TYPE_CODE = "2187";

        /**
         * FULL/PARTIAL구분코드
         */
        public static final String FULL_PARTIAL_CODE = "2362";

        /**
         * 팀 storage 구분 코드
         */
        public static final String TEAM_STORAGE_CODE = "2237";

        /**
         * Technology 구분코드
         */
        public static final String TECH_DIST_CODE = "1520";

        /**
         * 사이트 구분코드
         */
        public static final String SITE_CODE = "2341";

        /**
         * 업무지원 응답분류코드
         */
        public static final String RES_TYPE_CODE = "2400";

        /**
         * 화면캡쳐 유형코드
         */
        public static final String CAPTURE_TYPE_CODE = "2634";

        /**
         * 결재그룹코드
         */
        public static final String APPROVAL_GROUP_CODE = "2575";

        /**
         * 결재그룹코드 - 프로젝트
         */
        public static final String APPROVAL_GROUP_CODE_PRJ = "2577";

        /**
         * 결재그룹코드 - 도면열람
         */
        public static final String APPROVAL_GROUP_CODE_DBVIEW = "2578";

        /**
         * 결재그룹코드 - 계정
         */
        public static final String APPROVAL_GROUP_CODE_ACC = "2579";

        /**
         * 결재그룹코드 - Storage
         */
        public static final String APPROVAL_GROUP_CODE_STORAGE = "2580";

        /**
         * 결재그룹코드 - 그룹서버
         */
        public static final String APPROVAL_GROUP_CODE_GRP_SRV = "2581";

        /**
         * 결재그룹코드 - 해외접속
         */
        public static final String APPROVAL_GROUP_CODE_OVERSEAS = "2582";

        /**
         * 결재그룹코드 - 자동승인
         */
        public static final String APPROVAL_GROUP_CODE_AUTO = "2583";

        /**
         * 결재그룹코드 - Admin
         */
        public static final String APPROVAL_GROUP_CODE_ADMIN = "2589";
        
        /**
         * 3DVF DBList Source
         */
        public static final String THDVF_DBLIST_SOURCE = "2676";        
        
        /**
         * LSF 우선순위 / PORTION
         */
        public static final String LSF_PRIORITY = "2735";
        
        /**
         * 서비스 지역 코드
         */
        public static final String SERVICE_REGION = "2809";
        /**
         * 서비스 업체
         */
        public static final String SERVICE_COMPANY = "2827";

        /**
         * 서버가상화코드
         */
        public static final String SERVER_VIRTUALIZATION_CODE = "2829";
        
        /**
         * MAC할당방법
         */
        public static final String MAC_ASSIGNMENT_CODE = "2832";
        
        /**
         * 서버위치
         */
        public static final String SERVER_LOCATION = "2836";
        
        /**
         * 이상징후 시나리오 구분
         */
        public static final String ABNORMAL_TYPE = "2883";
        
        /**
         * 이상징후 심각도
         */
        public static final String ABNORMAL_LEVEL = "2841";
        
        /**
         * 이상징후 집중감지
         */
        public static final String ABNORMAL_DETECT = "2845";
        
        /**
         * 개인화 그룹 코드
         */
        public static final String PERSONAL_GROUP = "2906";
    }

    /**
     * 요청상태코드
     * 
     * @author <a href="mailto:support@openit.co.kr">(주)오픈잇 | openit Inc.</a>
     * @version 1.00
     * @created 2014. 12. 24.
     */
    public final class ReqStatusCd {

        /**
         * Default Constructor
         */
        public ReqStatusCd() {
            // Default Constructor
        }

        /**
         * 미정의
         */
        public static final String UNDEFINED = "00";

        /**
         * 최초등록
         */
        public static final String FIRST_REGISTRATION = "01";

        /**
         * 개정
         */
        public static final String REVISION = "02";
    }

    /**
     * 승인요청상태코드
     * 
     * @author <a href="mailto:support@openit.co.kr">(주)오픈잇 | openit Inc.</a>
     * @version 1.00
     * @created 2014. 12. 24.
     */
    public final class ApprReqStatusCd {

        /**
         * Default Constructor
         */
        public ApprReqStatusCd() {
            // Default Constructor
        }

        /**
         * 미정의
         */
        public static final String UNDEFINED = "00";

        /**
         * 승인요청
         */
        public static final String APPROVE_REQUEST = "01";

        /**
         * 협조요청
         */
        public static final String ASSIST_REQUEST = "02";

        /**
         * 합의요청
         */
        public static final String AGREE_REQUEST = "03";

        /**
         * 완료(승인)
         */
        public static final String COMPLETE_APPROVE = "04";

        /**
         * 완료(반려)
         */
        public static final String COMPLETE_REJECT = "05";
    }

    /**
     * 승인자코드
     * 
     * @author <a href="mailto:support@openit.co.kr">(주)오픈잇 | openit Inc.</a>
     * @version 1.00
     * @created 2014. 12. 24.
     */
    public final class ApproverCd {

        /**
         * Default Constructor
         */
        public ApproverCd() {
            // Default Constructor
        }

        /**
         * 미정의
         */
        public static final String UNDEFINED = "00";

        /**
         * 승인자
         */
        public static final String APPROVER = "01";

        /**
         * 협조자
         */
        public static final String ASSISTANT = "02";

        /**
         * 합의자
         */
        public static final String AGREEMENT = "03";

        /**
         * 참조자
         */
        public static final String NOTICE = "04";
    }

    /**
     * 승인상태코드
     * 
     * @author <a href="mailto:support@openit.co.kr">(주)오픈잇 | openit Inc.</a>
     * @version 1.00
     * @created 2014. 12. 24.
     */
    public final class ApprDtlStatusCd {

        /**
         * Default Constructor
         */
        public ApprDtlStatusCd() {
            // Default Constructor
        }

        /**
         * 미정의
         */
        public static final String UNDEFINED = "00";

        /**
         * 승인
         */
        public static final String APPROVE = "01";

        /**
         * 협조
         */
        public static final String ASSIST = "02";

        /**
         * 합의
         */
        public static final String AGREE = "03";

        /**
         * 반려
         */
        public static final String REJECT = "04";
    }

    /**
     * 메일 타입 코드
     * 
     * @author <a href="mailto:support@openit.co.kr">(주)오픈잇 | openit Inc.</a>
     * @version 1.00
     * @created 2014. 12. 24.
     */
    public final class EmailTypeCd {

        /**
         * Default Constructor
         */
        public EmailTypeCd() {
            // Default Constructor
        }

        /**
         * 신규 승인요청
         */
        public static final String NEW_APPROVE = "01";

        /**
         * 신규 협조요청
         */
        public static final String NEW_ASSIST = "02";

        /**
         * 신규 합의요청
         */
        public static final String NEW_AGREE = "03";

        /**
         * 신규 완료(승인)
         */
        public static final String NEW_COMPLETE = "04";

        /**
         * 개정 승인요청
         */
        public static final String REVISION_APPROVE = "11";

        /**
         * 개정 협조요청
         */
        public static final String REVISION_ASSIST = "12";

        /**
         * 개정 합의요청
         */
        public static final String REVISION_AGREE = "13";

        /**
         * 개정 완료(승인)
         */
        public static final String REVISION_COMPLETE = "14";

        /**
         * 개정 완료(참조)
         */
        public static final String REVISION_REFERENCE = "15";

        /**
         * 완료(승인)
         */
        public static final String COMPLETE_APPROVE = "21";

        /**
         * 완료(반려)
         */
        public static final String COMPLETE_REJECT = "22";

        /**
         * 즐겨찾기
         */
        public static final String NOTI_FAVORITE = "31";

        /**
         * 업데이트
         */
        public static final String NOTI_UPDATE = "32";

        /**
         * Support 요청 등록
         */
        public static final String SUPPORT_REQUEST = "41";

        /**
         * Support 요청 처리완료
         */
        public static final String SUPPORT_COMPLETE = "42";

        /**
         * Support 요청 부결
         */
        public static final String SUPPORT_REJECT = "43";

        /**
         * Support 요청 접수
         */
        public static final String SUPPORT_ACCEPT = "44";

        /**
         * Support 요청 취소
         */
        public static final String SUPPORT_CANCEL = "45";

        /**
         * Support 요청 수정
         */
        public static final String SUPPORT_MODIFY = "46";

        /**
         * LSF 우선순위 현황
         */
        public static final String PRIORITY_STAT = "47";
    }

    /**
     * 메일 발송 상태 코드
     * 
     * @author <a href="mailto:support@openit.co.kr">(주)오픈잇 | openit Inc.</a>
     * @version 1.00
     * @created 2014. 12. 24.
     */
    public final class EmailSendStatusCd {

        /**
         * Default Constructor
         */
        public EmailSendStatusCd() {
            // Default Constructor
        }

        /**
         * 신규
         */
        public static final String NEW = "01";

        /**
         * 실패
         */
        public static final String FAIL = "02";

        /**
         * 성공
         */
        public static final String COMPLETE = "03";
    }

    /**
     * 커뮤니티 상수
     * 
     * @author <a href="mailto:support@openit.co.kr">(주)오픈잇 | openit Inc.</a>
     * @version 1.00
     * @created 2014. 12. 24.
     */
    public final class Community {

        /**
         * Default Constructor
         */
        public Community() {
            // Default Constructor   
        }

        /**
         * 파일 추가
         */
        public static final String FILE_INSERT = "FI";

        /**
         * 파일 삭제
         */
        public static final String FILE_DELETE = "FD";

        /**
         * 파일 변동 없음
         */
        public static final String FILE_HOLD = "FH";

    }

    /**
     * 알림 타입 코드
     * 
     * @author <a href="mailto:support@openit.co.kr">(주)오픈잇 | openit Inc.</a>
     * @version 1.00
     * @created 2014. 12. 24.
     */
    public final class NotiTypeCd {

        /**
         * Default Constructor
         */
        public NotiTypeCd() {
            // Default Constructor
        }

        /**
         * 즐겨찾기
         */
        public static final String FAVORITE = "01";

        /**
         * 업데이트
         */
        public static final String UPDATE = "02";
    }

    /**
     * Support 카테고리 코드
     * 
     * @author <a href="mailto:support@openit.co.kr">(주)오픈잇 | openit Inc.</a>
     * @version 1.00
     * @created 2014. 12. 24.
     */
    public final class SupportCtgrCd {

        /**
         * Default Constructor
         */
        public SupportCtgrCd() {
            // Default Constructor
        }

        /**
         * Tools 카테고리
         */
        public static final String TOOLS = "000001";

        /**
         * 업무 카테고리
         */
        public static final String WORK = "000002";
    }

    /**
     * 관리자 지정 항목 구분 코드
     * 
     * @author <a href="mailto:support@openit.co.kr">(주)오픈잇 | openit Inc.</a>
     * @version 1.00
     * @created 2014. 12. 24.
     */
    public final class DefineTypeCd {

        /**
         * Default Constructor
         */
        public DefineTypeCd() {
            // Default Constructor
        }

        /**
         * INPUT TEXT
         */
        public static final String INPUT_TEXT = "01";

        /**
         * SELECT BOX
         */
        public static final String SELECT_BOX = "02";
    }

    /**
     * Support 요청 상태 코드
     * 
     * @author <a href="mailto:support@openit.co.kr">(주)오픈잇 | openit Inc.</a>
     * @version 1.00
     * @created 2014. 12. 24.
     */
    public final class SupportReqStatusCd {

        /**
         * Default Constructor
         */
        public SupportReqStatusCd() {
            // Default Constructor
        }

        /**
         * 처리 대기
         */
        public static final String PROCESS_HOLD = "00";

        /**
         * 처리중
         */
        public static final String PROCESS = "01";

        /**
         * 처리완료
         */
        public static final String PROCESS_COMPLETE = "02";

        /**
         * 재배정
         */
        public static final String REASSIGN = "03";

        /**
         * 부결
         */
        public static final String REJECT = "04";

        /**
         * 취소
         */
        public static final String CANCEL = "05";

        /**
         * HOLD
         */
        public static final String HOLD = "06";

        /**
         * RESTART
         */
        public static final String RESTART = "07";
    }

    /**
     * Support 처리 상태 코드
     * 
     * @author <a href="mailto:support@openit.co.kr">(주)오픈잇 | openit Inc.</a>
     * @version 1.00
     * @created 2014. 12. 24.
     */
    public final class SupportPrcStatusCd {

        /**
         * Default Constructor
         */
        public SupportPrcStatusCd() {
            // Default Constructor
        }

        /**
         * 접수
         */
        public static final String ACCEPT = "01";

        /**
         * 처리완료
         */
        public static final String COMPLETE = "02";

        /**
         * 재배정
         */
        public static final String REASSIGN = "03";

        /**
         * 부결
         */
        public static final String REJECT = "04";

        /**
         * HOLD
         */
        public static final String HOLD = "05";

        /**
         * RESTART
         */
        public static final String RESTART = "06";
    }

    /**
     * 일반게시판 분류 코드
     * 
     * @author <a href="mailto:support@openit.co.kr">(주)오픈잇 | openit Inc.</a>
     * @version 1.00
     * @created 2014. 12. 24.
     */
    public final class BbsContTypeCd {

        /**
         * Default Constructor
         */
        public BbsContTypeCd() {
            // Default Constructor
        }

        /**
         * Information > Memory > 기본정보 (이전 TOOLS 메뉴)
         */
        public static final String INFORM = "01";

        /**
         * Information > Memory > Release 소식 (이전 TOOLS 메뉴)
         */
        public static final String RELEASE = "02";

        /**
         * Information > Memory > Manual (이전 TOOLS 메뉴)
         */
        public static final String MANUAL = "03";

        /**
         * Information > Memory > FAQ (이전 TOOLS 메뉴)
         */
        public static final String FAQ = "04";

        /**
         * Community > Notice
         */
        public static final String NOTICE = "05";

        /**
         * Community > Data
         */
        public static final String DATA = "06";

        /**
         * Community > Data
         */
        public static final String UNKNOWN = "00";

        /**
         * Information > Memory > EOD (이전 TOOLS 메뉴)
         */
        public static final String EOD = "07";

        /**
         * Working Place > FAQ
         */
        public static final String WP_FAQ = "08";

        /**
         * Information > SoC > Release 소식
         */
        public static final String SOC_RELEASE = "09";

        /**
         * Information > SoC > Manual
         */
        public static final String SOC_MANUAL = "10";

        /**
         * Information > SoC > FAQ
         */
        public static final String SOC_FAQ = "11";

        /**
         * Information > SoC > SoC 표준 Flow Guide
         */
        public static final String SOC_FLOW = "12";

        /**
         * Information > SoC > 자료실
         */
        public static final String SOC_DATA = "13";

        /**
         * Information > SoC > SoC 소식
         */
        public static final String SOC_NEW = "14";
    }
    
    /**
     * 일반게시판 분류 코드
     * 
     * @author <a href="mailto:support@openit.co.kr">(주)오픈잇 | openit Inc.</a>
     * @version 1.00
     * @created 2014. 12. 24.
     */
    public final class SdpBbsContTypeCd {

        /**
         * Default Constructor
         */
        public SdpBbsContTypeCd() {
            // Default Constructor
        }

        /**
         * SDP > Design Work Flow
         */
        public static final String DES_WORK_FLOW = "01";

        /**
         * SDP > Circuit Design Guide
         */
        public static final String CIR_DES_GUIDE = "02";

        /**
         * SDP > Physical Design Guide
         */
        public static final String PHY_DES_GUIDE = "03";

        /**
         * SDP > Best Practice
         */
        public static final String BEST_PRTC = "04";

        /**
         * SDP > FAQ
         */
        public static final String SDP_FAQ = "05";

    }

    /**
     * 권한그룹 관리 분류 코드
     * 
     * @author <a href="mailto:support@openit.co.kr">(주)오픈잇 | openit Inc.</a>
     * @version 1.00
     * @created 2014. 12. 24.
     */
    public final class AdminAuthTypeCd {

        /**
         * Default Constructor
         */
        public AdminAuthTypeCd() {
            // Default Constructor
        }

        /**
         * Admin > 권한관리 > 권한그룹 관리 > INFRA-협조1그룹
         */
        public static final String INFRA_GROUP1_ADMIN = "000011";

        /**
         * Admin > 권한관리 > 권한그룹 관리 > INFRA-협조2그룹
         */
        public static final String INFRA_GROUP2_ADMIN = "000012";

        /**
         * Admin > 권한관리 > 권한그룹 관리 > 전체관리자권한
         */
        public static final String ALL_ADMIN = "000020";

        /**
         * Admin > 권한관리 > 권한그룹 관리 > 관리자 메일링 그룹1
         */
        public static final String ADMIN_MAILING_GROUP1 = "000021";

        /**
         * Admin > 권한관리 > 권한그룹 관리 > 관리자 메일링 그룹2
         */
        public static final String ADMIN_MAILING_GROUP2 = "000028";

        /**
         * Admin > 권한관리 > 권한그룹 관리 > SoC 관리자
         */
        public static final String SOC_ADMIN = "000025";

        /**
         * Admin > 권한관리 > 권한그룹 관리 > 해외 사용자
         */
        public static final String OVER_SEA_USER = "000026";
        
        /**
         * Admin > 권한관리 > 권한그룹 관리 > HiSOS 관리자
         */
        public static final String HI_SOS_ADMIN = "000029";
        
        /**
         * Admin > 권한관리 > 권한그룹 관리 > 운영자 접속 권한
         */
        public static final String ADMIN_LOG = "000034";
    }

    /**
     * 공통게시판 분류 코드
     * 
     * @author 
     * @version 1.00
     * @created 2014. 12. 24.
     */
    public final class BbsInfoTypeCd {

        /**
         * Default Constructor
         */
        public BbsInfoTypeCd() {
            // Default Constructor
        }

        /**
         * Admin > Coummunity > 공지사항
         */
        public static final String NOTICE_BBS_TYPE = "01";

        /**
         * Admin > Community > 가이드
         */
        public static final String GUIDE_BBS_TYPE = "02";

        /**
         * Admin > Community > FAQ
         */
        public static final String FAQ_BBS_TYPE = "03";

        /**
         * Admin > 시스템 관리 > 배너 관리
         */
        public static final String BANNER_BBS_TYPE = "04";
    }

    /**
     * 직책구분코드
     */
    public final class JpstnCdCode {

        /**
         * 그룹장
         */
        public static final String GROUP_LEADER = "JA";

        /**
         * PL
         */
        public static final String PL = "KC";
    }

    public final class VirtualFabReqDistCode {

        /**
         * 생성요청
         */
        public static final String CREATE_REQ_DIST_CODE = "48";

        /**
         * 열람요청
         */
        public static final String VIEW_REQ_DIST_CODE = "45";

        /**
         * 전송요청
         */
        public static final String TRANS_REQ_DIST_CODE = "44";
    }

    /**
     * 결재상태구분코드[1092]
     */
    public final class ApprStatusDistCode {

        /**
         * 승인요청
         */
        public static final String APPROVE_REQUEST = "01";

        /**
         * 진행중
         */
        public static final String APPROVE_ING = "02";

        /**
         * 승인
         */
        public static final String COMPLETE_APPROVE = "03";

        /**
         * 반려
         */
        public static final String COMPLETE_REJECT = "04";

        /**
         * 회수
         */
        public static final String APPROVE_RECOVERY = "05";

        /**
         * 자동승인
         */
        public static final String COMPLETE_AUTO = "06";
        
        /**
         * 자가승인
         */
        public static final String COMPLETE_SELF = "07"; // 2017.05.25
    }

    /**
     * 결재그룹코드
     */
    public final class ApprovalCommonGroupCode {

        /**
         * Default Constructor
         */
        public ApprovalCommonGroupCode() {
            // Default Constructor
        }

        /**
         * 프로젝트 코드
         */
        public static final String COMMON_GROUP_CODE_PRJ = "PRJ";

        /**
         * 자동 코드
         */
        public static final String COMMON_GROUP_CODE_AUTO = "AUTO";

        /**
         * ADMIN 코드
         */
        public static final String COMMON_GROUP_CODE_ADMIN = "ADMIN";
    }

    public final class Path {
    	
    	/**
    	 * Storage 결재요청구분코드
    	 */
    	public static final String SAMPLE = "styles/sample/";
    }
}
