CREATE TABLE `MENU_INFO` (
  `MENU_ID` int(10) unsigned NOT NULL AUTO_INCREMENT COMMENT '메뉴아이디',
  `MENU_NM` varchar(50) NOT NULL COMMENT '메뉴명',
  `MENU_URL` varchar(50) DEFAULT NULL COMMENT '링크주소',
  `UP_MENU_ID` int(10) unsigned DEFAULT '0' COMMENT '상위메뉴아이디',
  `MOD_DT` datetime NOT NULL COMMENT '수정일',
  `MOD_ID` varchar(20) NOT NULL COMMENT '수정자아이디',
  `MOD_IP` varchar(80) NOT NULL COMMENT '수정자아이피',
  `REG_DT` datetime NOT NULL COMMENT '등록일',
  `REG_ID` varchar(20) NOT NULL COMMENT '등록자아이디',
  `REG_IP` varchar(80) NOT NULL COMMENT '등록자아이피',
  PRIMARY KEY (`MENU_ID`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COMMENT='메뉴정보';

CREATE TABLE `MENU_AUTH` (
  `MENU_ID` int(10) unsigned NOT NULL COMMENT '메뉴아이디',
  `AUTH_ID` varchar(50) NOT NULL COMMENT '권한코드',
  PRIMARY KEY (`MENU_ID`,`AUTH_ID`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COMMENT='메뉴-권한';

DROP FUNCTION IF EXISTS MENU_CONNECT_BY_PARENT;
CREATE FUNCTION `MENU_CONNECT_BY_PARENT`() RETURNS int(11)
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
    SELECT MIN(MENU_ID)
      INTO @ID
      FROM MENU_INFO
     WHERE UP_MENU_ID = _PARENT
       AND MENU_ID > _ID;

    IF @ID IS NOT NULL OR _PARENT = @START_WITH THEN
      SET @LEVEL = @LEVEL + 1;
      RETURN @ID;
    END IF;

    SET @LEVEL := @LEVEL - 1;

    SELECT MENU_ID
          ,UP_MENU_ID
      INTO _ID, _PARENT
      FROM MENU_INFO
     WHERE MENU_ID = _PARENT;
  END LOOP;
END;


USE did_manage;

INSERT INTO `MENU_INFO`(`MENU_ID`,`MENU_NM`,`MENU_URL`,`UP_MENU_ID`,`MOD_DT`,`MOD_ID`,`MOD_IP`,`REG_DT`,`REG_ID`,`REG_IP`) VALUES (1,'회원관리','',0,'2015-08-26 00:00:00','ADMIN','0:0:0:0:0:0:0:1','2015-08-23 00:00:00','SYSTEM','127.0.0.1');
INSERT INTO `MENU_INFO`(`MENU_ID`,`MENU_NM`,`MENU_URL`,`UP_MENU_ID`,`MOD_DT`,`MOD_ID`,`MOD_IP`,`REG_DT`,`REG_ID`,`REG_IP`) VALUES (2,'회사관리','/mng/crp/list.do',1,'2015-08-23 00:00:00','SYSTEM','127.0.0.1','2015-08-23 00:00:00','SYSTEM','127.0.0.1');
INSERT INTO `MENU_INFO`(`MENU_ID`,`MENU_NM`,`MENU_URL`,`UP_MENU_ID`,`MOD_DT`,`MOD_ID`,`MOD_IP`,`REG_DT`,`REG_ID`,`REG_IP`) VALUES (3,'회원관리','/mng/usr/list.do',1,'2015-08-23 00:00:00','SYSTEM','127.0.0.1','2015-08-23 00:00:00','SYSTEM','127.0.0.1');
INSERT INTO `MENU_INFO`(`MENU_ID`,`MENU_NM`,`MENU_URL`,`UP_MENU_ID`,`MOD_DT`,`MOD_ID`,`MOD_IP`,`REG_DT`,`REG_ID`,`REG_IP`) VALUES (4,'파일관리','',0,'2015-08-26 00:00:00','ADMIN','0:0:0:0:0:0:0:1','2015-08-23 00:00:00','SYSTEM','127.0.0.1');
INSERT INTO `MENU_INFO`(`MENU_ID`,`MENU_NM`,`MENU_URL`,`UP_MENU_ID`,`MOD_DT`,`MOD_ID`,`MOD_IP`,`REG_DT`,`REG_ID`,`REG_IP`) VALUES (5,'파일관리','/mng/fle/list.do',4,'2015-08-23 00:00:00','SYSTEM','127.0.0.1','2015-08-23 00:00:00','SYSTEM','127.0.0.1');
INSERT INTO `MENU_INFO`(`MENU_ID`,`MENU_NM`,`MENU_URL`,`UP_MENU_ID`,`MOD_DT`,`MOD_ID`,`MOD_IP`,`REG_DT`,`REG_ID`,`REG_IP`) VALUES (6,'재생목록관리','/mng/ply/list.do',4,'2015-08-23 00:00:00','SYSTEM','127.0.0.1','2015-08-23 00:00:00','SYSTEM','127.0.0.1');
INSERT INTO `MENU_INFO`(`MENU_ID`,`MENU_NM`,`MENU_URL`,`UP_MENU_ID`,`MOD_DT`,`MOD_ID`,`MOD_IP`,`REG_DT`,`REG_ID`,`REG_IP`) VALUES (7,'기기관리','',0,'2015-08-26 00:00:00','ADMIN','0:0:0:0:0:0:0:1','2015-08-23 00:00:00','SYSTEM','127.0.0.1');
INSERT INTO `MENU_INFO`(`MENU_ID`,`MENU_NM`,`MENU_URL`,`UP_MENU_ID`,`MOD_DT`,`MOD_ID`,`MOD_IP`,`REG_DT`,`REG_ID`,`REG_IP`) VALUES (8,'기기정보','/mng/eup/list.do',7,'2015-08-23 00:00:00','SYSTEM','127.0.0.1','2015-08-23 00:00:00','SYSTEM','127.0.0.1');
INSERT INTO `MENU_INFO`(`MENU_ID`,`MENU_NM`,`MENU_URL`,`UP_MENU_ID`,`MOD_DT`,`MOD_ID`,`MOD_IP`,`REG_DT`,`REG_ID`,`REG_IP`) VALUES (9,'공지관리','/mng/noe/list.do',7,'2015-08-23 00:00:00','SYSTEM','127.0.0.1','2015-08-23 00:00:00','SYSTEM','127.0.0.1');
INSERT INTO `MENU_INFO`(`MENU_ID`,`MENU_NM`,`MENU_URL`,`UP_MENU_ID`,`MOD_DT`,`MOD_ID`,`MOD_IP`,`REG_DT`,`REG_ID`,`REG_IP`) VALUES (10,'사이트관리','',0,'2015-08-26 00:00:00','ADMIN','0:0:0:0:0:0:0:1','2015-08-23 00:00:00','SYSTEM','127.0.0.1');
INSERT INTO `MENU_INFO`(`MENU_ID`,`MENU_NM`,`MENU_URL`,`UP_MENU_ID`,`MOD_DT`,`MOD_ID`,`MOD_IP`,`REG_DT`,`REG_ID`,`REG_IP`) VALUES (11,'코드관리','/mng/cde/list.do',10,'2015-08-23 00:00:00','SYSTEM','127.0.0.1','2015-08-23 00:00:00','SYSTEM','127.0.0.1');
INSERT INTO `MENU_INFO`(`MENU_ID`,`MENU_NM`,`MENU_URL`,`UP_MENU_ID`,`MOD_DT`,`MOD_ID`,`MOD_IP`,`REG_DT`,`REG_ID`,`REG_IP`) VALUES (12,'APP관리','/mng/app/list.do',10,'2015-08-23 00:00:00','SYSTEM','127.0.0.1','2015-08-23 00:00:00','SYSTEM','127.0.0.1');
INSERT INTO `MENU_INFO`(`MENU_ID`,`MENU_NM`,`MENU_URL`,`UP_MENU_ID`,`MOD_DT`,`MOD_ID`,`MOD_IP`,`REG_DT`,`REG_ID`,`REG_IP`) VALUES (13,'시스템관리','',0,'2015-08-26 00:00:00','ADMIN','0:0:0:0:0:0:0:1','2015-08-23 00:00:00','SYSTEM','127.0.0.1');
INSERT INTO `MENU_INFO`(`MENU_ID`,`MENU_NM`,`MENU_URL`,`UP_MENU_ID`,`MOD_DT`,`MOD_ID`,`MOD_IP`,`REG_DT`,`REG_ID`,`REG_IP`) VALUES (14,'권한관리','/sys/aut/mng/list.do',13,'2015-08-23 00:00:00','SYSTEM','127.0.0.1','2015-08-23 00:00:00','SYSTEM','127.0.0.1');
INSERT INTO `MENU_INFO`(`MENU_ID`,`MENU_NM`,`MENU_URL`,`UP_MENU_ID`,`MOD_DT`,`MOD_ID`,`MOD_IP`,`REG_DT`,`REG_ID`,`REG_IP`) VALUES (15,'자원관리','/sys/res/mng/list.do',13,'2015-08-23 00:00:00','SYSTEM','127.0.0.1','2015-08-23 00:00:00','SYSTEM','127.0.0.1');
INSERT INTO `MENU_INFO`(`MENU_ID`,`MENU_NM`,`MENU_URL`,`UP_MENU_ID`,`MOD_DT`,`MOD_ID`,`MOD_IP`,`REG_DT`,`REG_ID`,`REG_IP`) VALUES (16,'메뉴관리','/sys/mnu/mng/list.do',13,'2015-08-23 00:00:00','SYSTEM','127.0.0.1','2015-08-23 00:00:00','SYSTEM','127.0.0.1');
INSERT INTO MENU_AUTH (MENU_ID, AUTH_ID) VALUES (5, 'RESTUSER');
INSERT INTO MENU_AUTH (MENU_ID, AUTH_ID) VALUES (6, 'RESTUSER');
INSERT INTO MENU_AUTH (MENU_ID, AUTH_ID) VALUES (8, 'RESTUSER');
INSERT INTO MENU_AUTH (MENU_ID, AUTH_ID) VALUES (9, 'RESTUSER');
INSERT INTO MENU_AUTH (MENU_ID, AUTH_ID) VALUES (5, 'ITALY');
INSERT INTO MENU_AUTH (MENU_ID, AUTH_ID) VALUES (6, 'ITALY');
INSERT INTO MENU_AUTH (MENU_ID, AUTH_ID) VALUES (8, 'ITALY');
INSERT INTO MENU_AUTH (MENU_ID, AUTH_ID) VALUES (9, 'ITALY');


SELECT CONCAT(REPEAT('       ', level - 1), D.MENU_NM) AS name
      ,D.MENU_URL
      ,FUNC.*
      ,E.AUTH_ID
 FROM (
       SELECT MENU_CONNECT_BY_PARENT() AS MENU_ID
             ,@LEVEL AS LEVEL
         FROM (
               SELECT @START_WITH := 0
                     ,@ID := @START_WITH
                     ,@LEVEL := 0
              ) VARS
             ,MENU_INFO
        WHERE @ID IS NOT NULL
      ) FUNC
 JOIN MENU_INFO D
   ON FUNC.MENU_ID = D.MENU_ID
 LEFT OUTER JOIN MENU_AUTH E
              ON D.MENU_ID = E.MENU_ID
             AND E.AUTH_ID IN (SELECT AUTH_ID
                                 FROM AUTH
                                WHERE AUTH_NO = 5
                                UNION ALL
                               SELECT D.AUTH_ID
                                 FROM (
                                       SELECT AUTH_CONNECT_BY_PARENT() AS AUTH_NO
                                             ,@LEVEL AS LEVEL
                                         FROM (
                                               SELECT @START_WITH := 5
                                                     ,@ID := @START_WITH
                                                     ,@LEVEL := 0
                                              ) VARS
                                             ,AUTH
                                        WHERE @ID IS NOT NULL
                                      ) FUNC
                                 JOIN AUTH D
                                   ON FUNC.AUTH_NO = D.AUTH_NO)

;
SELECT AUTH_ID
  FROM AUTH
 WHERE AUTH_NO = 5
UNION ALL
SELECT D.AUTH_ID
FROM (
     SELECT AUTH_CONNECT_BY_PARENT() AS AUTH_NO
           ,@LEVEL AS LEVEL
       FROM (
             SELECT @START_WITH := 5
                   ,@ID := @START_WITH
                   ,@LEVEL := 0
            ) VARS
           ,AUTH
      WHERE @ID IS NOT NULL
    ) FUNC
JOIN AUTH D
 ON FUNC.AUTH_NO = D.AUTH_NO
;
SELECT *
  FROM AUTH