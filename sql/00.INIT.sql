CREATE TABLE `auth` (
  `AUTH_ID` varchar(50) NOT NULL COMMENT '권한코드',
  `AUTH_NM` varchar(200) DEFAULT NULL COMMENT '권한명',
  `AUTH_NO` tinyint(4) DEFAULT NULL COMMENT '권한번호',
  `UP_AUTH_NO` tinyint(4) DEFAULT NULL COMMENT '상위권한번호',
  PRIMARY KEY (`AUTH_ID`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COMMENT='권한정보';

CREATE TABLE `auth_lvl` (
  `parent_auth_id` varchar(50) NOT NULL,
  `child_auth_id` varchar(50) NOT NULL,
  PRIMARY KEY (`parent_auth_id`,`child_auth_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

CREATE TABLE `code_part` (
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

CREATE TABLE `code_info` (
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
  CONSTRAINT `FK_CODE_PART_TO_CODE_INFO` FOREIGN KEY (`CODE_PART`) REFERENCES `code_part` (`CODE_PART`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COMMENT='공통코드';



CREATE TABLE `corp_auth` (
  `CORP_ID` varchar(20) NOT NULL COMMENT '회사아이디',
  `AUTH_ID` varchar(50) NOT NULL COMMENT '권한코드',
  PRIMARY KEY (`CORP_ID`,`AUTH_ID`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COMMENT='회사-권한';

CREATE TABLE `corp_info` (
  `CORP_ID` int(10) unsigned NOT NULL AUTO_INCREMENT COMMENT '회사아이디',
  `CORP_NM` varchar(50) NOT NULL COMMENT '회사명',
  `CORP_LOGO_PATH` varchar(30) DEFAULT NULL COMMENT '로고파일위치',
  `CORP_LOGO_NM` varchar(50) DEFAULT NULL COMMENT '로고파일',
  `CORP_LOGO_EXT` varchar(5) DEFAULT NULL COMMENT '로고파일확장자',
  `CORP_LOGO_SIZE` bigint(20) DEFAULT NULL COMMENT '로고파일사이즈',
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
) ENGINE=InnoDB AUTO_INCREMENT=28 DEFAULT CHARSET=utf8 COMMENT='회사정보';

CREATE TABLE `dept` (
  `ID` int(10) unsigned NOT NULL AUTO_INCREMENT,
  `PARENT_ID` int(10) unsigned DEFAULT '0',
  `NAME` varchar(50) DEFAULT NULL,
  PRIMARY KEY (`ID`)
) ENGINE=InnoDB AUTO_INCREMENT=11 DEFAULT CHARSET=utf8;

CREATE TABLE `file_info` (
  `FILE_ID` varchar(20) NOT NULL COMMENT '파일일련번호',
  `USER_ID` varchar(20) NOT NULL COMMENT '사용자아이디',
  `FILE_PATH` varchar(50) NOT NULL COMMENT '파일위치',
  `FILE_NM` varchar(50) NOT NULL COMMENT '파일명(파일일련번호+파일확장자)',
  `FILE_EXT` varchar(5) NOT NULL COMMENT '파일확장자',
  `FILE_CONT_TYPE` varchar(80) DEFAULT NULL COMMENT '파일컨텐츠타입',
  `FILE_TYPE` char(1) NOT NULL COMMENT '파일타입(M:동영상,I:이미지,S:소리,T:텍스트,D:DOC문서,E:엑셀)',
  `FILE_SIZE` int(11) NOT NULL COMMENT '파일사이즈',
  `FILE_TM` decimal(6,1) DEFAULT NULL COMMENT '동영상일 경우 상영시간(초단위)-밀리초까지 자리수 설정',
  `FILE_WIDTH` tinyint(4) DEFAULT NULL COMMENT '이미지가로사이즈',
  `FILE_HEIGHT` tinyint(4) DEFAULT NULL COMMENT '이미지세로사이즈',
  `ORG_FILE_NM` varchar(50) NOT NULL COMMENT '원파일명',
  `OPEN_RAN` char(1) NOT NULL COMMENT '공개범위(0:본인[비공개],1:같은회사공개,9:모든사용자)',
  `USE_YN` char(1) NOT NULL COMMENT '사용여부',
  `DEL_YN` char(1) NOT NULL COMMENT '삭제여부(삭제가 되면 공개되지 않는다)',
  `FOOD_YN` char(1) NOT NULL COMMENT '음식여부',
  `LAST_MOD_DT` char(14) DEFAULT NULL COMMENT '최종변경시간',
  `MOD_DT` datetime NOT NULL COMMENT '수정일',
  `MOD_ID` varchar(20) NOT NULL COMMENT '수정자아이디',
  `MOD_IP` varchar(80) NOT NULL COMMENT '수정자아이피',
  `REG_DT` datetime NOT NULL COMMENT '등록일',
  `REG_ID` varchar(20) NOT NULL COMMENT '등록자아이디',
  `REG_IP` varchar(80) NOT NULL COMMENT '등록자아이피',
  PRIMARY KEY (`FILE_ID`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COMMENT='파일정보';

CREATE TABLE `reso_auth` (
  `RESO_ID` varchar(50) NOT NULL COMMENT '리소스아이디',
  `AUTH_ID` varchar(50) NOT NULL COMMENT '권한코드',
  `AUTH_NM` varchar(200) DEFAULT NULL COMMENT '권한명',
  PRIMARY KEY (`RESO_ID`,`AUTH_ID`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COMMENT='리소스-권한';

CREATE TABLE `reso_info` (
  `RESO_ID` varchar(50) NOT NULL COMMENT '리소스아이디',
  `RESO_NM` varchar(200) DEFAULT NULL COMMENT '리소스명',
  `RESO_PTN` varchar(200) DEFAULT NULL COMMENT '리소스패턴',
  `RESO_TP` varchar(10) DEFAULT NULL COMMENT '리소스타입',
  `RESO_ORD` tinyint(4) DEFAULT NULL COMMENT '순서',
  PRIMARY KEY (`RESO_ID`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COMMENT='리소스 정보';

CREATE TABLE `sam_level` (
  `ID` tinyint(4) NOT NULL AUTO_INCREMENT,
  `REF` tinyint(4) DEFAULT NULL,
  `STEP` tinyint(4) DEFAULT NULL,
  `LEVEL` tinyint(4) DEFAULT NULL,
  `SUBJECT` varchar(50) DEFAULT NULL,
  PRIMARY KEY (`ID`)
) ENGINE=InnoDB AUTO_INCREMENT=8 DEFAULT CHARSET=utf8;

CREATE TABLE `test_table` (
  `id` int(10) unsigned NOT NULL AUTO_INCREMENT,
  `p_id` int(10) unsigned DEFAULT '0',
  `nm` varchar(50) DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=9 DEFAULT CHARSET=utf8;

CREATE TABLE `user_auth` (
  `USER_ID` varchar(20) NOT NULL COMMENT '사용자아이디',
  `AUTH_ID` varchar(50) NOT NULL COMMENT '권한코드',
  PRIMARY KEY (`USER_ID`,`AUTH_ID`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COMMENT='사용자-권한';

CREATE TABLE `user_info` (
  `USER_ID` varchar(20) NOT NULL COMMENT '사용자아이디',
  `CORP_ID` int(10) unsigned DEFAULT '0' COMMENT '회사아이디',
  `USER_NM` varchar(50) NOT NULL COMMENT '이름',
  `USER_PW` varchar(50) NOT NULL COMMENT '패스워드',
  `USER_EMAIL` varchar(50) DEFAULT NULL COMMENT '이메일',
  `USER_HP` varchar(15) DEFAULT NULL COMMENT '휴대전화번호',
  `USER_TEL` varchar(15) DEFAULT NULL COMMENT '일반전화번호',
  `USER_AUTH` char(1) NOT NULL COMMENT '회원등급',
  `USE_YN` char(1) NOT NULL COMMENT '사용여부',
  `ACCESS_FAIL_CNT` tinyint(4) DEFAULT NULL COMMENT '로그인실패횟수',
  `MOD_DT` datetime NOT NULL COMMENT '수정일',
  `MOD_ID` varchar(20) NOT NULL COMMENT '수정자아이디',
  `MOD_IP` varchar(80) NOT NULL COMMENT '수정자아이피',
  `REG_DT` datetime NOT NULL COMMENT '등록일',
  `REG_ID` varchar(20) NOT NULL COMMENT '등록자아이디',
  `REG_IP` varchar(80) NOT NULL COMMENT '등록자아이피',
  PRIMARY KEY (`USER_ID`),
  KEY `FK_CORP_INFO_TO_USER_INFO` (`CORP_ID`),
  CONSTRAINT `FK_CORP_INFO_TO_USER_INFO` FOREIGN KEY (`CORP_ID`) REFERENCES `corp_info` (`CORP_ID`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COMMENT='사용자';

DROP FUNCTION IF EXISTS did_manage.AUTH_CONNECT_BY_PARENT;
CREATE FUNCTION did_manage.`AUTH_CONNECT_BY_PARENT`() RETURNS int(11)
    READS SQL DATA
BEGIN
  DECLARE _ID INT;
  DECLARE _PARENT INT;
  DECLARE CONTINUE HANDLER FOR NOT FOUND SET @ID = NULL;

  SET _PARENT = @ID;
  SET _ID = -1;

  IF @ID IS NULL THEN
    RETURN NULL;
  END IF;

  LOOP
    SELECT MIN(AUTH_NO)
      INTO @ID
      FROM AUTH
     WHERE UP_AUTH_NO = _PARENT
       AND AUTH_NO > _ID;

    IF @ID IS NOT NULL OR _PARENT = @START_WITH THEN
      SET @LEVEL = @LEVEL + 1;
      RETURN @ID;
    END IF;

    SET @LEVEL := @LEVEL - 1;

    SELECT AUTH_NO, UP_AUTH_NO
      INTO _ID, _PARENT
      FROM AUTH
     WHERE AUTH_NO = _PARENT;
  END LOOP;
END;


DROP FUNCTION IF EXISTS did_manage.CORP_CONNECT_BY_PARENT;
CREATE FUNCTION did_manage.`CORP_CONNECT_BY_PARENT`() RETURNS int(11)
    READS SQL DATA
BEGIN
  DECLARE _ID INT;
  DECLARE _PARENT INT;
  DECLARE CONTINUE HANDLER FOR NOT FOUND SET @ID = NULL;

  SET _PARENT = @ID;
  SET _ID = -1;

  IF @ID IS NULL THEN
    RETURN NULL;
  END IF;

  LOOP
    SELECT MIN(CORP_ID)
      INTO @ID
      FROM CORP_INFO
     WHERE UP_CORP_ID = _PARENT
       AND CORP_ID > _ID;

    IF @ID IS NOT NULL OR _PARENT = @START_WITH THEN
      SET @LEVEL = @LEVEL + 1;
      RETURN @ID;
    END IF;

    SET @LEVEL := @LEVEL - 1;

    SELECT CORP_ID, UP_CORP_ID
      INTO _ID, _PARENT
      FROM CORP_INFO
     WHERE CORP_ID = _PARENT;
  END LOOP;
END;


DROP FUNCTION IF EXISTS did_manage.DEPT_CONNECT_BY_PARENT;
CREATE FUNCTION did_manage.`DEPT_CONNECT_BY_PARENT`() RETURNS int(11)
    READS SQL DATA
BEGIN
  DECLARE _ID INT;
  DECLARE _PARENT INT;
  DECLARE CONTINUE HANDLER FOR NOT FOUND SET @ID = NULL;

  SET _PARENT = @ID;
  SET _ID = -1;

  IF @ID IS NULL THEN
    RETURN NULL;
  END IF;


  LOOP
    SELECT MIN(ID)
      INTO @ID
      FROM DEPT
     WHERE PARENT_ID = _PARENT
       AND ID > _ID;

    IF @ID IS NOT NULL OR _PARENT = @START_WITH THEN
      SET @LEVEL = @LEVEL + 1;
      RETURN @ID;
    END IF;

    SET @LEVEL := @LEVEL - 1;

    SELECT ID, PARENT_ID
      INTO _ID, _PARENT
      FROM DEPT
      WHERE ID = _PARENT;
  END LOOP;
END;