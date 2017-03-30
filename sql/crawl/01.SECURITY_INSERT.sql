/*
초기 설정 스크립트
*/
DELETE FROM AUTH;
insert into auth values ('SUPERADMIN','슈퍼관리자','1','0');
insert into auth values ('SITEADMIN','사이트관리자','2','1');
insert into auth values ('SUBADMIN','부관리자','3','2');
insert into auth values ('USER','사용자','4','3');
insert into auth values ('AUTHENTICATED','로그인사용자','5','4');
insert into auth values ('ANONYMOUS','모든사용자','6','5');

INSERT INTO AUTH (AUTH_ID,AUTH_NM) VALUES ('SUPERADMIN','슈퍼관리자');
INSERT INTO AUTH (AUTH_ID,AUTH_NM) VALUES ('SITEADMIN','사이트관리자');
INSERT INTO AUTH (AUTH_ID,AUTH_NM) VALUES ('SUBADMIN','부관리자');
INSERT INTO AUTH (AUTH_ID,AUTH_NM) VALUES ('USER','사용자');
INSERT INTO AUTH (AUTH_ID,AUTH_NM) VALUES ('AUTHENTICATED','로그인사용자');
INSERT INTO AUTH (AUTH_ID,AUTH_NM) VALUES ('ANONYMOUS','모든사용자');

DELETE FROM AUTH_LVL;
INSERT INTO AUTH_LVL (PARENT_AUTH_ID,CHILD_AUTH_ID) VALUES ('SUPERADMIN','SITEADMIN');
INSERT INTO AUTH_LVL (PARENT_AUTH_ID,CHILD_AUTH_ID) VALUES ('SITEADMIN','SUBADMIN');
INSERT INTO AUTH_LVL (PARENT_AUTH_ID,CHILD_AUTH_ID) VALUES ('SUBADMIN','USER');
INSERT INTO AUTH_LVL (PARENT_AUTH_ID,CHILD_AUTH_ID) VALUES ('USER','AUTHENTICATED');
INSERT INTO AUTH_LVL (PARENT_AUTH_ID,CHILD_AUTH_ID) VALUES ('AUTHENTICATED','ANONYMOUS');

DELETE FROM USER_INFO;
INSERT INTO USER_INFO (USER_ID,USER_NM,USER_PW,USER_EMAIL,USER_HP,USER_TEL,USER_AUTH,USE_YN,ACCESS_FAIL_CNT,MOD_DT,MOD_ID,MOD_IP,REG_DT,REG_ID,REG_IP) VALUES ('admin', '관리자', 'nWsxfLXl5mvIuk880vK8Z/1Wk7B4DyRNiTNi8eUbCgg=', NULL, NULL, NULL, '0', 'Y', 0, SYSDATE(), 'system', '127.0.0.1', SYSDATE(), 'system', '127.0.0.1');

DELETE FROM USER_AUTH;
INSERT INTO USER_AUTH (USER_ID,AUTH_ID) VALUES ('admin','SUPERADMIN');

DELETE FROM RESO_INFO;
INSERT INTO RESO_INFO (RESO_ID, RESO_NM, RESO_PTN, RESO_TP, RESO_ORD) VALUES ('RES_000001', '아이콘', '/**/*.ico', 'url', 10);
INSERT INTO RESO_INFO (RESO_ID, RESO_NM, RESO_PTN, RESO_TP, RESO_ORD) VALUES ('RES_000002', '이미지', '/images/**/*', 'url', 20);
INSERT INTO RESO_INFO (RESO_ID, RESO_NM, RESO_PTN, RESO_TP, RESO_ORD) VALUES ('RES_000003', 'css', '/**/*.css', 'url', 30);
INSERT INTO RESO_INFO (RESO_ID, RESO_NM, RESO_PTN, RESO_TP, RESO_ORD) VALUES ('RES_000004', 'js', '/**/*.js', 'url', 40);
INSERT INTO RESO_INFO (RESO_ID, RESO_NM, RESO_PTN, RESO_TP, RESO_ORD) VALUES ('RES_000005', 'htm', '/**/*.htm*', 'url', 50);
INSERT INTO RESO_INFO (RESO_ID, RESO_NM, RESO_PTN, RESO_TP, RESO_ORD) VALUES ('RES_000006', 'jsp', '/*.jsp', 'url', 60);
INSERT INTO RESO_INFO (RESO_ID, RESO_NM, RESO_PTN, RESO_TP, RESO_ORD) VALUES ('RES_000007', '로그인,로그아웃', '/usr/lgn/*', 'url', 70);
INSERT INTO RESO_INFO (RESO_ID, RESO_NM, RESO_PTN, RESO_TP, RESO_ORD) VALUES ('RES_000008', '관리페이지', '/mng/**/*', 'url', 80);
INSERT INTO RESO_INFO (RESO_ID, RESO_NM, RESO_PTN, RESO_TP, RESO_ORD) VALUES ('RES_000009', '레이아웃 페이지', '/cmm/**/*', 'url', 90);
INSERT INTO RESO_INFO (RESO_ID, RESO_NM, RESO_PTN, RESO_TP, RESO_ORD) VALUES ('RES_000010', '개인정보수정', '/usr/edt/mypage*.do', 'url', 100);
INSERT INTO RESO_INFO (RESO_ID, RESO_NM, RESO_PTN, RESO_TP, RESO_ORD) VALUES ('RES_000011', '파일다운로드', '/common/downLoadFile.do', 'url', 110);
INSERT INTO RESO_INFO (RESO_ID, RESO_NM, RESO_PTN, RESO_TP, RESO_ORD) VALUES ('RES_000012', '이미지보기', '/nvl/imgView.do', 'url', 120);
INSERT INTO RESO_INFO (RESO_ID, RESO_NM, RESO_PTN, RESO_TP, RESO_ORD) VALUES ('RES_000013', '검색', '/cmm/sch/*', 'url', 130);


DELETE FROM RESO_AUTH;
INSERT INTO RESO_AUTH (RESO_ID, AUTH_ID, AUTH_NM) VALUES ('RES_000001', 'ANONYMOUS', '아이콘');
INSERT INTO RESO_AUTH (RESO_ID, AUTH_ID, AUTH_NM) VALUES ('RES_000002', 'ANONYMOUS', '이미지');
INSERT INTO RESO_AUTH (RESO_ID, AUTH_ID, AUTH_NM) VALUES ('RES_000003', 'ANONYMOUS', 'css');
INSERT INTO RESO_AUTH (RESO_ID, AUTH_ID, AUTH_NM) VALUES ('RES_000004', 'ANONYMOUS', 'js');
INSERT INTO RESO_AUTH (RESO_ID, AUTH_ID, AUTH_NM) VALUES ('RES_000005', 'ANONYMOUS', 'htm');
INSERT INTO RESO_AUTH (RESO_ID, AUTH_ID, AUTH_NM) VALUES ('RES_000006', 'ANONYMOUS', 'jsp');
INSERT INTO RESO_AUTH (RESO_ID, AUTH_ID, AUTH_NM) VALUES ('RES_000007', 'ANONYMOUS', '로그인,로그아웃');
INSERT INTO RESO_AUTH (RESO_ID, AUTH_ID, AUTH_NM) VALUES ('RES_000008', 'SUBADMIN', '관리페이지');
INSERT INTO RESO_AUTH (RESO_ID, AUTH_ID, AUTH_NM) VALUES ('RES_000009', 'SUBADMIN', '레이아웃 페이지');
INSERT INTO RESO_AUTH (RESO_ID, AUTH_ID, AUTH_NM) VALUES ('RES_000010', 'AUTHENTICATED', '개인정보수정');
INSERT INTO RESO_AUTH (RESO_ID, AUTH_ID, AUTH_NM) VALUES ('RES_000011', 'ANONYMOUS', '파일다운로드');
INSERT INTO RESO_AUTH (RESO_ID, AUTH_ID, AUTH_NM) VALUES ('RES_000012', 'ANONYMOUS', '이미지보기');
INSERT INTO RESO_AUTH (RESO_ID, AUTH_ID, AUTH_NM) VALUES ('RES_000013', 'AUTHENTICATED', '검색');