GRANT PROXY ON ''@'' TO 'root'@'localhost' WITH GRANT OPTION;
GRANT ALL PRIVILEGES ON *.* TO 'root'@'%' WITH GRANT OPTION;
GRANT ALL PRIVILEGES ON *.* TO 'evanequip'@'%' WITH GRANT OPTION;

CREATE TABLE `API_LOG` (
  `API_DATE` char(8) NOT NULL COMMENT 'API 일자',
  `API_SEQ` int(11) NOT NULL COMMENT 'API 일련번호',
  `API_STP_ID` varchar(20) DEFAULT NULL COMMENT 'API 요청 기기아이디',
  `API_CODE` varchar(20) DEFAULT NULL COMMENT 'API 요청 코드',
  `API_RECEIVE_DATA` mediumtext COMMENT 'API 수신 데이타',
  `API_RECEIVE_DT` datetime DEFAULT NULL COMMENT 'API 수신 일시',
  `API_SEND_DATA` mediumtext COMMENT 'API 전송 데이타',
  `API_SEND_DT` datetime DEFAULT NULL COMMENT 'API 전송 일시',
  `API_ERROR` text COMMENT 'API 오류 내용',
  `API_PROC_SEC` int(11) NOT NULL COMMENT 'API 처리시간',
  `API_IP` varchar(40) NOT NULL COMMENT 'API 요청 아이피',
  PRIMARY KEY (`API_DATE`,`API_SEQ`),
  UNIQUE KEY `PK_API_LOG` (`API_DATE`,`API_SEQ`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COMMENT='API 로그';

CREATE TABLE `APP_INFO` (
  `APP_ID` varchar(20) NOT NULL COMMENT '파일일련번호',
  `APP_OS` varchar(20) DEFAULT NULL COMMENT '운영체제',
  `APP_VER` varchar(20) DEFAULT NULL COMMENT '버전',
  `APP_OS_MIN_VER` varchar(20) DEFAULT NULL COMMENT '최소지원OS버전',
  `FILE_PATH` varchar(50) NOT NULL COMMENT '파일위치',
  `FILE_NM` varchar(50) NOT NULL COMMENT '파일명(파일일련번호+파일확장자)',
  `FILE_EXT` varchar(5) NOT NULL COMMENT '파일확장자',
  `FILE_SIZE` int(12) NOT NULL COMMENT '파일사이즈',
  `ORG_FILE_NM` varchar(50) NOT NULL COMMENT '원파일명',
  `MOD_DT` datetime NOT NULL COMMENT '수정일',
  `MOD_ID` varchar(20) NOT NULL COMMENT '수정자아이디',
  `MOD_IP` varchar(80) NOT NULL COMMENT '수정자아이피',
  `REG_DT` datetime NOT NULL COMMENT '등록일',
  `REG_ID` varchar(20) NOT NULL COMMENT '등록자아이디',
  `REG_IP` varchar(80) NOT NULL COMMENT '등록자아이피',
  PRIMARY KEY (`APP_ID`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COMMENT='APP정보';

CREATE TABLE `AUTH` (
  `AUTH_ID` varchar(50) NOT NULL COMMENT '권한코드',
  `AUTH_NM` varchar(200) DEFAULT NULL COMMENT '권한명',
  `AUTH_NO` int(4) DEFAULT NULL COMMENT '권한번호',
  `UP_AUTH_NO` int(4) DEFAULT NULL COMMENT '상위권한번호',
  PRIMARY KEY (`AUTH_ID`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COMMENT='권한정보';

CREATE TABLE `AUTH_LVL` (
  `PARENT_AUTH_ID` varchar(50) NOT NULL COMMENT '부모권한코드',
  `CHILD_AUTH_ID` varchar(50) NOT NULL COMMENT '자식권한코드',
  PRIMARY KEY (`PARENT_AUTH_ID`,`CHILD_AUTH_ID`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COMMENT='권한레벨';

CREATE TABLE `CODE_PART` (
  `CODE_PART` varchar(20) NOT NULL COMMENT '코드그룹',
  `CODE_PART_NM` varchar(30) NOT NULL COMMENT '코드그룹명',
  `MOD_DT` datetime NOT NULL COMMENT '수정일',
  `MOD_ID` varchar(20) NOT NULL COMMENT '수정자아이디',
  `MOD_IP` varchar(80) NOT NULL COMMENT '수정자아이피',
  `REG_DT` datetime NOT NULL COMMENT '등록일',
  `REG_ID` varchar(20) NOT NULL COMMENT '등록자아이디',
  `REG_IP` varchar(80) NOT NULL COMMENT '등록자아이피',
  PRIMARY KEY (`CODE_PART`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COMMENT='코드그룹';

CREATE TABLE `CODE_INFO` (
  `CODE_PART` varchar(20) NOT NULL COMMENT '코드그룹',
  `CODE` varchar(20) NOT NULL COMMENT '코드',
  `CODE_NM` varchar(200) NOT NULL COMMENT '코드명',
  `CODE_ORD` varchar(5) DEFAULT NULL COMMENT '코드정렬순서',
  `CODE_DESC` varchar(500) DEFAULT NULL COMMENT '코드설명',
  `MOD_DT` datetime NOT NULL COMMENT '수정일',
  `MOD_ID` varchar(20) NOT NULL COMMENT '수정자아이디',
  `MOD_IP` varchar(80) NOT NULL COMMENT '수정자아이피',
  `REG_DT` datetime NOT NULL COMMENT '등록일',
  `REG_ID` varchar(20) NOT NULL COMMENT '등록자아이디',
  `REG_IP` varchar(80) NOT NULL COMMENT '등록자아이피',
  PRIMARY KEY (`CODE_PART`,`CODE`),
  CONSTRAINT `FK_CODE_PART_TO_CODE_INFO` FOREIGN KEY (`CODE_PART`) REFERENCES `CODE_PART` (`CODE_PART`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COMMENT='공통코드';

CREATE TABLE `CORP_AUTH` (
  `CORP_ID` varchar(20) NOT NULL COMMENT '회사아이디',
  `AUTH_ID` varchar(50) NOT NULL COMMENT '권한코드',
  PRIMARY KEY (`CORP_ID`,`AUTH_ID`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COMMENT='회사-권한';

CREATE TABLE `CORP_INFO` (
  `CORP_ID` int(10) unsigned NOT NULL AUTO_INCREMENT COMMENT '회사아이디',
  `CORP_NM` varchar(50) NOT NULL COMMENT '회사명',
  `CORP_LOGO_PATH` varchar(30) DEFAULT NULL COMMENT '로고파일위치',
  `CORP_LOGO_NM` varchar(50) DEFAULT NULL COMMENT '로고파일',
  `CORP_LOGO_EXT` varchar(5) DEFAULT NULL COMMENT '로고파일확장자',
  `CORP_LOGO_SIZE` int(12) DEFAULT NULL COMMENT '로고파일사이즈',
  `CORP_LOGO_CONT_TYPE` varchar(30) DEFAULT NULL COMMENT '로고파일컨텐츠타입',
  `CORP_LOGO_ORG_FILE_NM` varchar(50) DEFAULT NULL COMMENT '로고고유파일명',
  `CORP_URL` varchar(50) DEFAULT NULL COMMENT '홈페이지주소',
  `CORP_TEL` varchar(15) DEFAULT NULL COMMENT '전화번호',
  `CORP_POST` varchar(7) DEFAULT NULL COMMENT '우편번호',
  `CORP_ADR` varchar(150) DEFAULT NULL COMMENT '주소',
  `CORP_ADR_DSC` varchar(100) DEFAULT NULL COMMENT '상세주소',
  `UP_CORP_ID` int(10) unsigned DEFAULT '0' COMMENT '상위회사아이디',
  `MOD_DT` datetime NOT NULL COMMENT '수정일',
  `MOD_ID` varchar(20) NOT NULL COMMENT '수정자아이디',
  `MOD_IP` varchar(80) NOT NULL COMMENT '수정자아이피',
  `REG_DT` datetime NOT NULL COMMENT '등록일',
  `REG_ID` varchar(20) NOT NULL COMMENT '등록자아이디',
  `REG_IP` varchar(80) NOT NULL COMMENT '등록자아이피',
  PRIMARY KEY (`CORP_ID`)
) ENGINE=InnoDB AUTO_INCREMENT=29 DEFAULT CHARSET=utf8 COMMENT='회사정보';

CREATE TABLE `DEPT` (
  `ID` int(10) unsigned NOT NULL AUTO_INCREMENT COMMENT 'ID',
  `PARENT_ID` int(10) unsigned DEFAULT '0' COMMENT 'PARENT_ID',
  `NAME` varchar(50) DEFAULT NULL COMMENT 'NAME',
  PRIMARY KEY (`ID`)
) ENGINE=InnoDB AUTO_INCREMENT=11 DEFAULT CHARSET=utf8 COMMENT='dept';

CREATE TABLE `FILE_INFO` (
  `FILE_ID` varchar(20) NOT NULL COMMENT '파일일련번호',
  `USER_ID` varchar(20) NOT NULL COMMENT '사용자아이디',
  `FILE_PATH` varchar(50) NOT NULL COMMENT '파일위치',
  `FILE_NM` varchar(50) NOT NULL COMMENT '파일명(파일일련번호+파일확장자)',
  `FILE_EXT` varchar(5) NOT NULL COMMENT '파일확장자',
  `FILE_CONT_TYPE` varchar(80) DEFAULT NULL COMMENT '파일컨텐츠타입',
  `FILE_TYPE` char(1) NOT NULL COMMENT '파일타입(M:동영상,I:이미지,S:소리,T:텍스트,D:DOC문서,E:엑셀)',
  `FILE_SIZE` int(12) NOT NULL COMMENT '파일사이즈',
  `FILE_TM` decimal(6,1) DEFAULT NULL COMMENT '동영상일 경우 상영시간(초단위)-밀리초까지 자리수 설정',
  `FILE_WIDTH` int(4) DEFAULT NULL COMMENT '이미지가로사이즈',
  `FILE_HEIGHT` int(4) DEFAULT NULL COMMENT '이미지세로사이즈',
  `ORG_FILE_NM` varchar(50) NOT NULL COMMENT '원파일명',
  `OPEN_RAN` char(1) NOT NULL COMMENT '공개범위(0:본인[비공개],1:같은회사공개,9:모든사용자)',
  `CORP_ID` int(10) DEFAULT NULL COMMENT '회사아이디',
  `USE_YN` char(1) NOT NULL COMMENT '사용여부',
  `DEL_YN` char(1) NOT NULL COMMENT '삭제여부(삭제가 되면 공개되지 않는다)',
  `TXT_YN` char(1) NOT NULL COMMENT '안내정보여부',
  `LAST_MOD_DT` char(14) DEFAULT NULL COMMENT '최종변경시간',
  `MOD_DT` datetime NOT NULL COMMENT '수정일',
  `MOD_ID` varchar(20) NOT NULL COMMENT '수정자아이디',
  `MOD_IP` varchar(80) NOT NULL COMMENT '수정자아이피',
  `REG_DT` datetime NOT NULL COMMENT '등록일',
  `REG_ID` varchar(20) NOT NULL COMMENT '등록자아이디',
  `REG_IP` varchar(80) NOT NULL COMMENT '등록자아이피',
  PRIMARY KEY (`FILE_ID`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COMMENT='파일정보';

CREATE TABLE `FILE_TXT_INFO` (
  `FILE_ID` varchar(20) NOT NULL COMMENT '파일 일련번호',
  `IFO_SEQ` int(3) NOT NULL COMMENT '안내정보 일련번호',
  `IFO_MSG` varchar(200) DEFAULT NULL COMMENT '안내메세지',
  `IFO_SIZE` char(1) DEFAULT NULL COMMENT '글자크기(B:가장크게,L:크게,M:보통,S:작게)',
  `IFO_BOLD` char(1) DEFAULT NULL COMMENT '글자BOLD',
  `IFO_LINE_PX` int(1) DEFAULT NULL COMMENT '글자아웃라인픽셀',
  `IFO_LINE_CLR` varchar(7) DEFAULT NULL COMMENT '글자아웃라인컬러',
  `IFO_CLR` varchar(7) DEFAULT NULL COMMENT '글자색상',
  `IFO_UPAD` int(4) DEFAULT NULL COMMENT '위측여백',
  `IFO_SPAD` int(4) DEFAULT NULL COMMENT '위측여백',
  `IFO_ALN` char(1) DEFAULT NULL COMMENT '좌우정렬(L:좌측 ,C:중앙 ,R:우측)',
  PRIMARY KEY (`FILE_ID`,`IFO_SEQ`),
  CONSTRAINT `FK_FILE_INFO_TO_FILE_TXT_INFO` FOREIGN KEY (`FILE_ID`) REFERENCES `FILE_INFO` (`FILE_ID`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COMMENT='파일별 안내정보';

CREATE TABLE `MSG_LST` (
  `MSG_ID` varchar(15) NOT NULL COMMENT '메세지아이디',
  `MSG_TITLE` varchar(200) DEFAULT NULL COMMENT '메세지제목',
  `CORP_ID` int(10) unsigned DEFAULT NULL COMMENT '회사아이디',
  `USER_ID` varchar(20) NOT NULL COMMENT '사용자아이디',
  `MSG_TYPE` char(1) DEFAULT NULL COMMENT '메세지타입(G:일반, S:긴급)',
  `VIEW_TM` char(2) DEFAULT NULL COMMENT '반복시간',
  `USE_YN` char(1) DEFAULT NULL COMMENT '사용여부',
  `LAST_MOD_DT` char(14) DEFAULT NULL COMMENT '최종변경시간',
  `MOD_DT` datetime NOT NULL COMMENT '수정일',
  `MOD_ID` varchar(20) NOT NULL COMMENT '수정자아이디',
  `MOD_IP` varchar(80) NOT NULL COMMENT '수정자아이피',
  `REG_DT` datetime NOT NULL COMMENT '등록일',
  `REG_ID` varchar(20) NOT NULL COMMENT '등록자아이디',
  `REG_IP` varchar(80) NOT NULL COMMENT '등록자아이피',
  PRIMARY KEY (`MSG_ID`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COMMENT='메세지목록';

CREATE TABLE `MSG_INFO` (
  `MSG_ID` varchar(15) NOT NULL COMMENT '메세지아이디',
  `MSG_SEQ` int(3) NOT NULL COMMENT '메세지일련번호',
  `MSG` varchar(200) DEFAULT NULL COMMENT '메세지',
  PRIMARY KEY (`MSG_ID`,`MSG_SEQ`),
  CONSTRAINT `FK_MSG_LST_TO_MSG_INFO` FOREIGN KEY (`MSG_ID`) REFERENCES `MSG_LST` (`MSG_ID`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COMMENT='메세지정보';

CREATE TABLE `MSG_STP` (
  `MSG_ID` varchar(15) NOT NULL COMMENT '메세지아이디',
  `STP_ID` varchar(15) NOT NULL COMMENT '셋톱 아이디',
  `DOWN_YN` char(1) DEFAULT NULL COMMENT '다운로드여부',
  `DEL_YN` char(1) DEFAULT NULL COMMENT '삭제여부',
  PRIMARY KEY (`MSG_ID`,`STP_ID`),
  CONSTRAINT `FK_MSG_LST_TO_MSG_STP` FOREIGN KEY (`MSG_ID`) REFERENCES `MSG_LST` (`MSG_ID`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COMMENT='메세지연결셋톱';

CREATE TABLE `NEWS_LST` (
  `PROVIDE_CD` varchar(20) NOT NULL COMMENT '뉴스제공사',
  `NEWS_TIME` varchar(15) NOT NULL COMMENT '뉴스수집시간',
  `SEQ` int(3) DEFAULT NULL COMMENT '순번',
  `SUBJECT` varchar(500) DEFAULT NULL COMMENT '뉴스제목',
  `LINK` varchar(500) DEFAULT NULL COMMENT '뉴스링크',
  `CATEGORY` varchar(5) DEFAULT NULL COMMENT '뉴스분류',
  `PUBDATE` varchar(50) DEFAULT NULL COMMENT '발표시간'
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COMMENT='뉴스목록';

CREATE TABLE `PLY_VER` (
  `PLY_VER_ID` varchar(15) NOT NULL COMMENT '재생목록버전',
  `USER_ID` varchar(20) NOT NULL COMMENT '사용자아이디',
  `PLY_TITLE` varchar(50) NOT NULL COMMENT '재생목록제목',
  `DIST_STAT` char(1) NOT NULL COMMENT '배포상태(I:배포중,D:배포완료,S:배포대기)',
  `SKIN_PATH` varchar(30) DEFAULT NULL COMMENT '스킨파일위치',
  `SKIN_NM` varchar(50) DEFAULT NULL COMMENT '스킨파일',
  `SKIN_EXT` varchar(5) DEFAULT NULL COMMENT '스킨파일확장자',
  `SKIN_SIZE` int(12) DEFAULT NULL COMMENT '시킨파일사이즈',
  `SKIN_CONT_TYPE` varchar(30) DEFAULT NULL COMMENT '스킨파일컨텐츠타입',
  `SKIN_ORG_FILE_NM` varchar(50) DEFAULT NULL COMMENT '스킨고유파일명',
  `CORP_ID` int(10) DEFAULT NULL COMMENT '회사아이디',
  `USE_YN` char(1) NOT NULL COMMENT '사용여부',
  `LAST_MOD_DT` char(14) DEFAULT NULL COMMENT '최종변경시간',
  `MOD_DT` date NOT NULL COMMENT '수정일',
  `MOD_ID` varchar(20) NOT NULL COMMENT '수정자아이디',
  `MOD_IP` varchar(80) NOT NULL COMMENT '수정자아이피',
  `REG_DT` date NOT NULL COMMENT '등록일',
  `REG_ID` varchar(20) NOT NULL COMMENT '등록자아이디',
  `REG_IP` varchar(80) NOT NULL COMMENT '등록자아이피',
  PRIMARY KEY (`PLY_VER_ID`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COMMENT='재생목록버전';

CREATE TABLE `PLY_LST` (
  `PLY_VER_ID` varchar(15) NOT NULL COMMENT '재생목록버전',
  `PLY_ORD` int(3) NOT NULL COMMENT '재생순서',
  `FILE_ID` varchar(20) NOT NULL COMMENT '파일일련번호',
  `PLY_TM` int(6) DEFAULT NULL COMMENT '파일재생시간(설정된 시간만큼 플레이 됨.단, 동영상 파일보다는 작아야 됨.)',
  `TXT_YN` char(1) DEFAULT NULL COMMENT '안내여부',
  `SKIN_YN` char(1) DEFAULT NULL COMMENT '스킨사용여부',
  `ANI_TYPE` varchar(20) DEFAULT NULL COMMENT '애니메이션타입',
  `ANI_VAL1` varchar(20) DEFAULT NULL COMMENT '애니메이션상태값1',
  `ANI_VAL2` varchar(20) DEFAULT NULL COMMENT '애니메이션상태값2',
  `ANI_VAL3` varchar(20) DEFAULT NULL COMMENT '애니메이션상태값3',
  `ANI_VAL4` varchar(20) DEFAULT NULL COMMENT '애니메이션상태값4',
  PRIMARY KEY (`PLY_VER_ID`,`PLY_ORD`),
  CONSTRAINT `FK_PLY_VER_TO_PLY_LST` FOREIGN KEY (`PLY_VER_ID`) REFERENCES `PLY_VER` (`PLY_VER_ID`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COMMENT='재생버전별 파일목록';

CREATE TABLE `RESO_AUTH` (
  `RESO_ID` varchar(50) NOT NULL COMMENT '리소스아이디',
  `AUTH_ID` varchar(50) NOT NULL COMMENT '권한코드',
  `AUTH_NM` varchar(200) DEFAULT NULL COMMENT '권한명',
  PRIMARY KEY (`RESO_ID`,`AUTH_ID`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COMMENT='리소스-권한';

CREATE TABLE `RESO_INFO` (
  `RESO_ID` varchar(50) NOT NULL COMMENT '리소스아이디',
  `RESO_NM` varchar(200) DEFAULT NULL COMMENT '리소스명',
  `RESO_PTN` varchar(200) DEFAULT NULL COMMENT '리소스패턴',
  `RESO_TP` varchar(10) DEFAULT NULL COMMENT '리소스타입',
  `RESO_ORD` int(3) DEFAULT NULL COMMENT '순서',
  PRIMARY KEY (`RESO_ID`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COMMENT='리소스 정보';

CREATE TABLE `SAM_LEVEL` (
  `ID` tinyint(4) NOT NULL AUTO_INCREMENT COMMENT 'ID',
  `REF` tinyint(4) DEFAULT NULL COMMENT 'REF',
  `STEP` tinyint(4) DEFAULT NULL COMMENT 'STEP',
  `LEVEL` tinyint(4) DEFAULT NULL COMMENT 'LEVEL',
  `SUBJECT` varchar(50) DEFAULT NULL COMMENT 'SUBJECT',
  PRIMARY KEY (`ID`)
) ENGINE=InnoDB AUTO_INCREMENT=8 DEFAULT CHARSET=utf8 COMMENT='sam_level';

CREATE TABLE `STP_INFO` (
  `STP_ID` varchar(15) NOT NULL COMMENT '셋톱아이디',
  `CORP_ID` int(10) unsigned DEFAULT NULL COMMENT '회사아이디',
  `USER_ID` varchar(20) NOT NULL COMMENT '사용자아이디',
  `STP_TYPE` char(1) NOT NULL COMMENT '셋톱설치유형(R:임대[RENT],S:판매[SALE],T:테스트[TEST])',
  `STP_TITLE` varchar(200) DEFAULT NULL COMMENT '셋톱명칭',
  `STP_OS` varchar(10) DEFAULT NULL COMMENT '셋톱OS',
  `STP_APP_VER` varchar(10) DEFAULT NULL COMMENT 'APP 버전정보',
  `LAST_CONN_DT` datetime DEFAULT NULL COMMENT '셋톱 최종 접속시간',
  `MNT_TYPE` char(1) DEFAULT NULL COMMENT '모니터타입(H:수평, V:수직)',
  `CALL_CD` varchar(20) DEFAULT NULL COMMENT '호출기타입',
  `CALL_GRP` int(2) unsigned DEFAULT NULL COMMENT '호출기그룹번호',
  `CALL_PRD` int(2) unsigned DEFAULT NULL COMMENT '호출기업체번호',
  `CALL_NUM` int(1) unsigned DEFAULT NULL COMMENT '호출번호자리수',
  `SOUND_TYPE` varchar(20) DEFAULT NULL COMMENT '안내멘트타입코드',
  `CCTV_YN` char(1) DEFAULT NULL COMMENT 'CCTV사용여부',
  `AUTO_OFF_YN` char(1) DEFAULT NULL COMMENT '자동꺼짐사용',
  `AUTO_OFF_TM` char(4) DEFAULT NULL COMMENT '자동꺼짐시간',
  `NEWS_YN` char(1) DEFAULT NULL COMMENT '뉴스사용여부',
  `NEWS_START_TM` char(4) DEFAULT NULL COMMENT '뉴스시작시간',
  `NEWS_END_TM` char(4) DEFAULT NULL COMMENT '뉴스종료시간',
  `NOTI_YN` char(1) DEFAULT NULL COMMENT '공지사용여부',
  `NOTI_START_TM` char(4) DEFAULT NULL COMMENT '공지시작시간',
  `NOTI_END_TM` char(4) DEFAULT NULL COMMENT '공지종료시간',
  `NOTI_SIZE` char(1) DEFAULT NULL COMMENT '공지뉴스사이즈',
  `STP_SET_DT` datetime NOT NULL COMMENT '셋톱최초설치일',
  `USE_YN` char(1) DEFAULT NULL COMMENT '사용여부',
  `LAST_MOD_DT` char(14) DEFAULT NULL COMMENT '최종변경시간',
  `MOD_DT` datetime NOT NULL COMMENT '수정일',
  `MOD_ID` varchar(20) NOT NULL COMMENT '수정자아이디',
  `MOD_IP` varchar(80) NOT NULL COMMENT '수정자아이피',
  `REG_DT` datetime NOT NULL COMMENT '등록일',
  `REG_ID` varchar(20) NOT NULL COMMENT '등록자아이디',
  `REG_IP` varchar(80) NOT NULL COMMENT '등록자아이피',
  PRIMARY KEY (`STP_ID`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COMMENT='셋톱정보';

CREATE TABLE `STP_PLY_INFO` (
  `STP_ID` varchar(15) NOT NULL COMMENT '셋톱 아이디',
  `PLY_SEQ` int(3) NOT NULL COMMENT '재생순번',
  `PLY_VER_ID` varchar(15) NOT NULL COMMENT '재생목록버전',
  `PLY_TITLE` varchar(100) DEFAULT NULL COMMENT '재생목록별칭',
  `PLY_START_TM` char(4) DEFAULT NULL COMMENT '재생시작시간',
  `PLY_END_TM` char(4) DEFAULT NULL COMMENT '재생종료시간',
  PRIMARY KEY (`STP_ID`,`PLY_SEQ`,`PLY_VER_ID`),
  CONSTRAINT `FK_STP_INFO_TO_STP_PLY_INFO` FOREIGN KEY (`STP_ID`) REFERENCES `STP_INFO` (`STP_ID`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COMMENT='셋톱재생정보';

CREATE TABLE `MAC_INFO` (
  `STP_ID` varchar(15) NOT NULL COMMENT '셋톱 아이디',
  `MAC_SEQ` int(3) NOT NULL COMMENT '셋톱 맥 일련번호',
  `MAC_TYPE` varchar(10) DEFAULT NULL COMMENT '맥 타입',
  `MAC_ADR` varchar(50) DEFAULT NULL COMMENT '맥 주소',
  `LAST_CONN_DT` datetime DEFAULT NULL COMMENT '최종 접속 일자',
  `USE_YN` char(1) DEFAULT NULL COMMENT '사용여부',
  PRIMARY KEY (`STP_ID`,`MAC_SEQ`),
  CONSTRAINT `FK_STP_INFO_TO_MAC_INFO` FOREIGN KEY (`STP_ID`) REFERENCES `STP_INFO` (`STP_ID`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COMMENT='셋톱맥주소';

CREATE TABLE `TEST_TABLE` (
  `id` int(10) unsigned NOT NULL AUTO_INCREMENT COMMENT 'id',
  `p_id` int(10) unsigned DEFAULT '0' COMMENT 'p_id',
  `nm` varchar(50) DEFAULT NULL COMMENT 'nm',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=9 DEFAULT CHARSET=utf8 COMMENT='test_table';

CREATE TABLE `USER_AUTH` (
  `USER_ID` varchar(20) NOT NULL COMMENT '사용자아이디',
  `AUTH_ID` varchar(50) NOT NULL COMMENT '권한코드',
  PRIMARY KEY (`USER_ID`,`AUTH_ID`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COMMENT='사용자-권한';

CREATE TABLE `USER_INFO` (
  `USER_ID` varchar(20) NOT NULL COMMENT '사용자아이디',
  `CORP_ID` int(10) unsigned DEFAULT NULL COMMENT '회사아이디',
  `USER_NM` varchar(50) NOT NULL COMMENT '이름',
  `USER_PW` varchar(50) NOT NULL COMMENT '패스워드',
  `USER_EMAIL` varchar(50) DEFAULT NULL COMMENT '이메일',
  `USER_HP` varchar(15) DEFAULT NULL COMMENT '휴대전화번호',
  `USER_TEL` varchar(15) DEFAULT NULL COMMENT '일반전화번호',
  `USER_AUTH` char(1) NOT NULL COMMENT '회원등급(0:슈퍼관리자,1:회사정보관리자,2:셋톱관리자)',
  `USE_YN` char(1) NOT NULL COMMENT '사용여부',
  `ACCESS_FAIL_CNT` int(2) DEFAULT NULL COMMENT '로그인실패횟수',
  `MOD_DT` datetime NOT NULL COMMENT '수정일',
  `MOD_ID` varchar(20) NOT NULL COMMENT '수정자아이디',
  `MOD_IP` varchar(80) NOT NULL COMMENT '수정자아이피',
  `REG_DT` datetime NOT NULL COMMENT '등록일',
  `REG_ID` varchar(20) NOT NULL COMMENT '등록자아이디',
  `REG_IP` varchar(80) NOT NULL COMMENT '등록자아이피',
  PRIMARY KEY (`USER_ID`),
  KEY `FK_CORP_INFO_TO_USER_INFO` (`CORP_ID`),
  CONSTRAINT `FK_CORP_INFO_TO_USER_INFO` FOREIGN KEY (`CORP_ID`) REFERENCES `CORP_INFO` (`CORP_ID`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COMMENT='사용자';

CREATE TABLE `FILE_CHECK` (
  `CHK_DATE` varchar(8) NOT NULL COMMENT '확인날짜',
  `CHK_SEQ` int(6) NOT NULL COMMENT '일련번호',
  `CHK_TABLE` varchar(50) DEFAULT NULL COMMENT '테이블명',
  `CHK_TABLE_ID` varchar(20) DEFAULT NULL COMMENT '아이디',
  `CHK_PATH` varchar(50) DEFAULT NULL COMMENT '파일경로',
  `CHK_NM` varchar(50) DEFAULT NULL COMMENT '파일명',
  `CHK_ERR_DESC` varchar(100) DEFAULT NULL COMMENT '오류내용',
  PRIMARY KEY (`CHK_DATE`,`CHK_SEQ`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COMMENT='파일체크';