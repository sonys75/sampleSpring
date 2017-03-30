-- APP정보
CREATE TABLE `DID_MANAGE`.`APP_INFO` (
	`APP_ID`         VARCHAR(20) NOT NULL COMMENT '파일일련번호', -- 파일 일련번호
	`APP_OS`         VARCHAR(20) NULL     COMMENT '운영체제', -- 운영체제
	`APP_VER`        VARCHAR(20) NULL     COMMENT '버전', -- 버전
	`APP_OS_MIN_VER` VARCHAR(20) NULL     COMMENT '최소지원OS버전', -- 최소지원OS버전
	`FILE_PATH`      VARCHAR(50) NOT NULL COMMENT '파일위치', -- 파일 위치
	`FILE_NM`        VARCHAR(50) NOT NULL COMMENT '파일명(파일일련번호+파일확장자)', -- 파일명
	`FILE_EXT`       VARCHAR(5)  NOT NULL COMMENT '파일확장자', -- 파일확장자
	`FILE_SIZE`      BIGINT      NOT NULL COMMENT '파일사이즈', -- 파일사이즈
	`ORG_FILE_NM`    VARCHAR(50) NOT NULL COMMENT '원파일명', -- 원파일명
	`MOD_DT`         DATETIME    NOT NULL COMMENT '수정일', -- 수정일
	`MOD_ID`         VARCHAR(20) NOT NULL COMMENT '수정자아이디', -- 수정자아이디
	`MOD_IP`         VARCHAR(80) NOT NULL COMMENT '수정자아이피', -- 수정자아이피
	`REG_DT`         DATETIME    NOT NULL COMMENT '등록일', -- 등록일
	`REG_ID`         VARCHAR(20) NOT NULL COMMENT '등록자아이디', -- 등록자아이디
	`REG_IP`         VARCHAR(80) NOT NULL COMMENT '등록자아이피' -- 등록자아이피
)
COMMENT 'APP정보';

-- APP정보
ALTER TABLE `DID_MANAGE`.`APP_INFO`
	ADD CONSTRAINT `PK_APP_INFO` -- APP정보 기본키
		PRIMARY KEY (
			`APP_ID` -- 파일 일련번호
		);