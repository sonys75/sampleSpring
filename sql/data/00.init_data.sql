insert into `auth_lvl`(`parent_auth_id`,`child_auth_id`) values ('AUTHENTICATED','ANONYMOUS');
insert into `auth_lvl`(`parent_auth_id`,`child_auth_id`) values ('RESTADMIN','RESTUSER');
insert into `auth_lvl`(`parent_auth_id`,`child_auth_id`) values ('RESTUSER','AUTHENTICATED');
insert into `auth_lvl`(`parent_auth_id`,`child_auth_id`) values ('ROADADMIN','RESTADMIN');
insert into `auth_lvl`(`parent_auth_id`,`child_auth_id`) values ('SITEADMIN','ROADADMIN');
insert into `auth_lvl`(`parent_auth_id`,`child_auth_id`) values ('SUPERADMIN','SITEADMIN');

insert into `auth`(`AUTH_ID`,`AUTH_NM`,`AUTH_NO`,`UP_AUTH_NO`) values ('ANONYMOUS','�������',7,6);
insert into `auth`(`AUTH_ID`,`AUTH_NM`,`AUTH_NO`,`UP_AUTH_NO`) values ('AUTHENTICATED','�α��λ����',6,5);
insert into `auth`(`AUTH_ID`,`AUTH_NM`,`AUTH_NO`,`UP_AUTH_NO`) values ('RESTADMIN','REST������',4,3);
insert into `auth`(`AUTH_ID`,`AUTH_NM`,`AUTH_NO`,`UP_AUTH_NO`) values ('RESTUSER','REST�����',5,4);
insert into `auth`(`AUTH_ID`,`AUTH_NM`,`AUTH_NO`,`UP_AUTH_NO`) values ('ROADADMIN','ROAD������',3,2);
insert into `auth`(`AUTH_ID`,`AUTH_NM`,`AUTH_NO`,`UP_AUTH_NO`) values ('SITEADMIN','����Ʈ������',2,1);
insert into `auth`(`AUTH_ID`,`AUTH_NM`,`AUTH_NO`,`UP_AUTH_NO`) values ('SUPERADMIN','���۰�����',1,0);


insert into `corp_info`(`CORP_ID`,`CORP_NM`,`CORP_LOGO_PATH`,`CORP_LOGO_NM`,`CORP_LOGO_EXT`,`CORP_LOGO_SIZE`,`CORP_LOGO_CONT_TYPE`,`CORP_LOGO_ORG_FILE_NM`,`CORP_URL`,`CORP_TEL`,`CORP_POST`,`CORP_ADR`,`CORP_ADR_DSC`,`UP_CORP_ID`,`MOD_DT`,`MOD_ID`,`MOD_IP`,`REG_DT`,`REG_ID`,`REG_IP`) values (1,'EVAN','/attachfile/corp_logo','7e72e5a6c6552097740326040a5319a8','gif',9522,'image/gif','bg_card_1.gif','http://www.test.com','02-0000-0000','111111','������','���뱸 ���뵿',0,'2015-08-26 00:00:00','admin','0:0:0:0:0:0:0:1','2015-08-23 00:00:00','SYSTEM','127.0.0.1');
insert into `corp_info`(`CORP_ID`,`CORP_NM`,`CORP_LOGO_PATH`,`CORP_LOGO_NM`,`CORP_LOGO_EXT`,`CORP_LOGO_SIZE`,`CORP_LOGO_CONT_TYPE`,`CORP_LOGO_ORG_FILE_NM`,`CORP_URL`,`CORP_TEL`,`CORP_POST`,`CORP_ADR`,`CORP_ADR_DSC`,`UP_CORP_ID`,`MOD_DT`,`MOD_ID`,`MOD_IP`,`REG_DT`,`REG_ID`,`REG_IP`) values (2,'���ΰ���',null,null,null,null,null,null,null,null,null,null,null,1,'2015-08-23 00:00:00','SYSTEM','127.0.0.1','2015-08-23 00:00:00','SYSTEM','127.0.0.1');
insert into `corp_info`(`CORP_ID`,`CORP_NM`,`CORP_LOGO_PATH`,`CORP_LOGO_NM`,`CORP_LOGO_EXT`,`CORP_LOGO_SIZE`,`CORP_LOGO_CONT_TYPE`,`CORP_LOGO_ORG_FILE_NM`,`CORP_URL`,`CORP_TEL`,`CORP_POST`,`CORP_ADR`,`CORP_ADR_DSC`,`UP_CORP_ID`,`MOD_DT`,`MOD_ID`,`MOD_IP`,`REG_DT`,`REG_ID`,`REG_IP`) values (3,'�ްԼ�1',null,null,null,null,null,null,null,null,null,null,null,2,'2015-08-23 00:00:00','SYSTEM','127.0.0.1','2015-08-23 00:00:00','SYSTEM','127.0.0.1');
insert into `corp_info`(`CORP_ID`,`CORP_NM`,`CORP_LOGO_PATH`,`CORP_LOGO_NM`,`CORP_LOGO_EXT`,`CORP_LOGO_SIZE`,`CORP_LOGO_CONT_TYPE`,`CORP_LOGO_ORG_FILE_NM`,`CORP_URL`,`CORP_TEL`,`CORP_POST`,`CORP_ADR`,`CORP_ADR_DSC`,`UP_CORP_ID`,`MOD_DT`,`MOD_ID`,`MOD_IP`,`REG_DT`,`REG_ID`,`REG_IP`) values (4,'�ްԼ�2',null,null,null,null,null,null,null,null,null,null,null,2,'2015-08-23 00:00:00','SYSTEM','127.0.0.1','2015-08-23 00:00:00','SYSTEM','127.0.0.1');
insert into `corp_info`(`CORP_ID`,`CORP_NM`,`CORP_LOGO_PATH`,`CORP_LOGO_NM`,`CORP_LOGO_EXT`,`CORP_LOGO_SIZE`,`CORP_LOGO_CONT_TYPE`,`CORP_LOGO_ORG_FILE_NM`,`CORP_URL`,`CORP_TEL`,`CORP_POST`,`CORP_ADR`,`CORP_ADR_DSC`,`UP_CORP_ID`,`MOD_DT`,`MOD_ID`,`MOD_IP`,`REG_DT`,`REG_ID`,`REG_IP`) values (5,'�ްԼ�3',null,null,null,null,null,null,null,null,null,null,null,2,'2015-08-23 00:00:00','SYSTEM','127.0.0.1','2015-08-23 00:00:00','SYSTEM','127.0.0.1');
insert into `corp_info`(`CORP_ID`,`CORP_NM`,`CORP_LOGO_PATH`,`CORP_LOGO_NM`,`CORP_LOGO_EXT`,`CORP_LOGO_SIZE`,`CORP_LOGO_CONT_TYPE`,`CORP_LOGO_ORG_FILE_NM`,`CORP_URL`,`CORP_TEL`,`CORP_POST`,`CORP_ADR`,`CORP_ADR_DSC`,`UP_CORP_ID`,`MOD_DT`,`MOD_ID`,`MOD_IP`,`REG_DT`,`REG_ID`,`REG_IP`) values (6,'�ްԼҾ�ü1-1',null,null,null,null,null,null,null,null,null,null,null,3,'2015-08-23 00:00:00','SYSTEM','127.0.0.1','2015-08-23 00:00:00','SYSTEM','127.0.0.1');
insert into `corp_info`(`CORP_ID`,`CORP_NM`,`CORP_LOGO_PATH`,`CORP_LOGO_NM`,`CORP_LOGO_EXT`,`CORP_LOGO_SIZE`,`CORP_LOGO_CONT_TYPE`,`CORP_LOGO_ORG_FILE_NM`,`CORP_URL`,`CORP_TEL`,`CORP_POST`,`CORP_ADR`,`CORP_ADR_DSC`,`UP_CORP_ID`,`MOD_DT`,`MOD_ID`,`MOD_IP`,`REG_DT`,`REG_ID`,`REG_IP`) values (7,'�ްԼҾ�ü1-2',null,null,null,null,null,null,null,null,null,null,null,3,'2015-08-23 00:00:00','SYSTEM','127.0.0.1','2015-08-23 00:00:00','SYSTEM','127.0.0.1');
insert into `corp_info`(`CORP_ID`,`CORP_NM`,`CORP_LOGO_PATH`,`CORP_LOGO_NM`,`CORP_LOGO_EXT`,`CORP_LOGO_SIZE`,`CORP_LOGO_CONT_TYPE`,`CORP_LOGO_ORG_FILE_NM`,`CORP_URL`,`CORP_TEL`,`CORP_POST`,`CORP_ADR`,`CORP_ADR_DSC`,`UP_CORP_ID`,`MOD_DT`,`MOD_ID`,`MOD_IP`,`REG_DT`,`REG_ID`,`REG_IP`) values (8,'�ްԼҾ�ü1-3',null,null,null,null,null,null,null,null,null,null,null,3,'2015-08-23 00:00:00','SYSTEM','127.0.0.1','2015-08-23 00:00:00','SYSTEM','127.0.0.1');
insert into `corp_info`(`CORP_ID`,`CORP_NM`,`CORP_LOGO_PATH`,`CORP_LOGO_NM`,`CORP_LOGO_EXT`,`CORP_LOGO_SIZE`,`CORP_LOGO_CONT_TYPE`,`CORP_LOGO_ORG_FILE_NM`,`CORP_URL`,`CORP_TEL`,`CORP_POST`,`CORP_ADR`,`CORP_ADR_DSC`,`UP_CORP_ID`,`MOD_DT`,`MOD_ID`,`MOD_IP`,`REG_DT`,`REG_ID`,`REG_IP`) values (9,'�ްԼҾ�ü2-1',null,null,null,null,null,null,null,null,null,null,null,4,'2015-08-23 00:00:00','SYSTEM','127.0.0.1','2015-08-23 00:00:00','SYSTEM','127.0.0.1');
insert into `corp_info`(`CORP_ID`,`CORP_NM`,`CORP_LOGO_PATH`,`CORP_LOGO_NM`,`CORP_LOGO_EXT`,`CORP_LOGO_SIZE`,`CORP_LOGO_CONT_TYPE`,`CORP_LOGO_ORG_FILE_NM`,`CORP_URL`,`CORP_TEL`,`CORP_POST`,`CORP_ADR`,`CORP_ADR_DSC`,`UP_CORP_ID`,`MOD_DT`,`MOD_ID`,`MOD_IP`,`REG_DT`,`REG_ID`,`REG_IP`) values (10,'�ްԼҾ�ü2-2',null,null,null,null,null,null,null,null,null,null,null,4,'2015-08-23 00:00:00','SYSTEM','127.0.0.1','2015-08-23 00:00:00','SYSTEM','127.0.0.1');
insert into `corp_info`(`CORP_ID`,`CORP_NM`,`CORP_LOGO_PATH`,`CORP_LOGO_NM`,`CORP_LOGO_EXT`,`CORP_LOGO_SIZE`,`CORP_LOGO_CONT_TYPE`,`CORP_LOGO_ORG_FILE_NM`,`CORP_URL`,`CORP_TEL`,`CORP_POST`,`CORP_ADR`,`CORP_ADR_DSC`,`UP_CORP_ID`,`MOD_DT`,`MOD_ID`,`MOD_IP`,`REG_DT`,`REG_ID`,`REG_IP`) values (11,'�ްԼҾ�ü2-3',null,null,null,null,null,null,null,null,null,null,null,4,'2015-08-23 00:00:00','SYSTEM','127.0.0.1','2015-08-23 00:00:00','SYSTEM','127.0.0.1');
insert into `corp_info`(`CORP_ID`,`CORP_NM`,`CORP_LOGO_PATH`,`CORP_LOGO_NM`,`CORP_LOGO_EXT`,`CORP_LOGO_SIZE`,`CORP_LOGO_CONT_TYPE`,`CORP_LOGO_ORG_FILE_NM`,`CORP_URL`,`CORP_TEL`,`CORP_POST`,`CORP_ADR`,`CORP_ADR_DSC`,`UP_CORP_ID`,`MOD_DT`,`MOD_ID`,`MOD_IP`,`REG_DT`,`REG_ID`,`REG_IP`) values (12,'�ްԼҾ�ü3-1',null,null,null,null,null,null,null,null,null,null,null,5,'2015-08-23 00:00:00','SYSTEM','127.0.0.1','2015-08-23 00:00:00','SYSTEM','127.0.0.1');
insert into `corp_info`(`CORP_ID`,`CORP_NM`,`CORP_LOGO_PATH`,`CORP_LOGO_NM`,`CORP_LOGO_EXT`,`CORP_LOGO_SIZE`,`CORP_LOGO_CONT_TYPE`,`CORP_LOGO_ORG_FILE_NM`,`CORP_URL`,`CORP_TEL`,`CORP_POST`,`CORP_ADR`,`CORP_ADR_DSC`,`UP_CORP_ID`,`MOD_DT`,`MOD_ID`,`MOD_IP`,`REG_DT`,`REG_ID`,`REG_IP`) values (13,'�ްԼҾ�ü3-2',null,null,null,null,null,null,null,null,null,null,null,5,'2015-08-23 00:00:00','SYSTEM','127.0.0.1','2015-08-23 00:00:00','SYSTEM','127.0.0.1');
insert into `corp_info`(`CORP_ID`,`CORP_NM`,`CORP_LOGO_PATH`,`CORP_LOGO_NM`,`CORP_LOGO_EXT`,`CORP_LOGO_SIZE`,`CORP_LOGO_CONT_TYPE`,`CORP_LOGO_ORG_FILE_NM`,`CORP_URL`,`CORP_TEL`,`CORP_POST`,`CORP_ADR`,`CORP_ADR_DSC`,`UP_CORP_ID`,`MOD_DT`,`MOD_ID`,`MOD_IP`,`REG_DT`,`REG_ID`,`REG_IP`) values (14,'�ްԼҾ�ü3-3',null,null,null,null,null,null,null,null,null,null,null,5,'2015-08-23 00:00:00','SYSTEM','127.0.0.1','2015-08-23 00:00:00','SYSTEM','127.0.0.1');
insert into `corp_info`(`CORP_ID`,`CORP_NM`,`CORP_LOGO_PATH`,`CORP_LOGO_NM`,`CORP_LOGO_EXT`,`CORP_LOGO_SIZE`,`CORP_LOGO_CONT_TYPE`,`CORP_LOGO_ORG_FILE_NM`,`CORP_URL`,`CORP_TEL`,`CORP_POST`,`CORP_ADR`,`CORP_ADR_DSC`,`UP_CORP_ID`,`MOD_DT`,`MOD_ID`,`MOD_IP`,`REG_DT`,`REG_ID`,`REG_IP`) values (15,'ȸ��',null,null,null,null,null,null,null,null,null,null,null,1,'2015-08-23 00:00:00','SYSTEM','127.0.0.1','2015-08-23 00:00:00','SYSTEM','127.0.0.1');
insert into `corp_info`(`CORP_ID`,`CORP_NM`,`CORP_LOGO_PATH`,`CORP_LOGO_NM`,`CORP_LOGO_EXT`,`CORP_LOGO_SIZE`,`CORP_LOGO_CONT_TYPE`,`CORP_LOGO_ORG_FILE_NM`,`CORP_URL`,`CORP_TEL`,`CORP_POST`,`CORP_ADR`,`CORP_ADR_DSC`,`UP_CORP_ID`,`MOD_DT`,`MOD_ID`,`MOD_IP`,`REG_DT`,`REG_ID`,`REG_IP`) values (16,'�μ�1',null,null,null,null,null,null,null,null,null,null,null,15,'2015-08-23 00:00:00','SYSTEM','127.0.0.1','2015-08-23 00:00:00','SYSTEM','127.0.0.1');
insert into `corp_info`(`CORP_ID`,`CORP_NM`,`CORP_LOGO_PATH`,`CORP_LOGO_NM`,`CORP_LOGO_EXT`,`CORP_LOGO_SIZE`,`CORP_LOGO_CONT_TYPE`,`CORP_LOGO_ORG_FILE_NM`,`CORP_URL`,`CORP_TEL`,`CORP_POST`,`CORP_ADR`,`CORP_ADR_DSC`,`UP_CORP_ID`,`MOD_DT`,`MOD_ID`,`MOD_IP`,`REG_DT`,`REG_ID`,`REG_IP`) values (17,'�μ�2',null,null,null,null,null,null,null,null,null,null,null,15,'2015-08-23 00:00:00','SYSTEM','127.0.0.1','2015-08-23 00:00:00','SYSTEM','127.0.0.1');
insert into `corp_info`(`CORP_ID`,`CORP_NM`,`CORP_LOGO_PATH`,`CORP_LOGO_NM`,`CORP_LOGO_EXT`,`CORP_LOGO_SIZE`,`CORP_LOGO_CONT_TYPE`,`CORP_LOGO_ORG_FILE_NM`,`CORP_URL`,`CORP_TEL`,`CORP_POST`,`CORP_ADR`,`CORP_ADR_DSC`,`UP_CORP_ID`,`MOD_DT`,`MOD_ID`,`MOD_IP`,`REG_DT`,`REG_ID`,`REG_IP`) values (18,'�μ�3',null,null,null,null,null,null,null,null,null,null,null,15,'2015-08-23 00:00:00','SYSTEM','127.0.0.1','2015-08-23 00:00:00','SYSTEM','127.0.0.1');
insert into `corp_info`(`CORP_ID`,`CORP_NM`,`CORP_LOGO_PATH`,`CORP_LOGO_NM`,`CORP_LOGO_EXT`,`CORP_LOGO_SIZE`,`CORP_LOGO_CONT_TYPE`,`CORP_LOGO_ORG_FILE_NM`,`CORP_URL`,`CORP_TEL`,`CORP_POST`,`CORP_ADR`,`CORP_ADR_DSC`,`UP_CORP_ID`,`MOD_DT`,`MOD_ID`,`MOD_IP`,`REG_DT`,`REG_ID`,`REG_IP`) values (19,'�μ���Ʈ1-1',null,null,null,null,null,null,null,null,null,null,null,16,'2015-08-23 00:00:00','SYSTEM','127.0.0.1','2015-08-23 00:00:00','SYSTEM','127.0.0.1');
insert into `corp_info`(`CORP_ID`,`CORP_NM`,`CORP_LOGO_PATH`,`CORP_LOGO_NM`,`CORP_LOGO_EXT`,`CORP_LOGO_SIZE`,`CORP_LOGO_CONT_TYPE`,`CORP_LOGO_ORG_FILE_NM`,`CORP_URL`,`CORP_TEL`,`CORP_POST`,`CORP_ADR`,`CORP_ADR_DSC`,`UP_CORP_ID`,`MOD_DT`,`MOD_ID`,`MOD_IP`,`REG_DT`,`REG_ID`,`REG_IP`) values (20,'�μ���Ʈ1-2',null,null,null,null,null,null,null,null,null,null,null,16,'2015-08-23 00:00:00','SYSTEM','127.0.0.1','2015-08-23 00:00:00','SYSTEM','127.0.0.1');
insert into `corp_info`(`CORP_ID`,`CORP_NM`,`CORP_LOGO_PATH`,`CORP_LOGO_NM`,`CORP_LOGO_EXT`,`CORP_LOGO_SIZE`,`CORP_LOGO_CONT_TYPE`,`CORP_LOGO_ORG_FILE_NM`,`CORP_URL`,`CORP_TEL`,`CORP_POST`,`CORP_ADR`,`CORP_ADR_DSC`,`UP_CORP_ID`,`MOD_DT`,`MOD_ID`,`MOD_IP`,`REG_DT`,`REG_ID`,`REG_IP`) values (21,'�μ���Ʈ1-3',null,null,null,null,null,null,null,null,null,null,null,16,'2015-08-23 00:00:00','SYSTEM','127.0.0.1','2015-08-23 00:00:00','SYSTEM','127.0.0.1');
insert into `corp_info`(`CORP_ID`,`CORP_NM`,`CORP_LOGO_PATH`,`CORP_LOGO_NM`,`CORP_LOGO_EXT`,`CORP_LOGO_SIZE`,`CORP_LOGO_CONT_TYPE`,`CORP_LOGO_ORG_FILE_NM`,`CORP_URL`,`CORP_TEL`,`CORP_POST`,`CORP_ADR`,`CORP_ADR_DSC`,`UP_CORP_ID`,`MOD_DT`,`MOD_ID`,`MOD_IP`,`REG_DT`,`REG_ID`,`REG_IP`) values (22,'�μ���Ʈ2-1',null,null,null,null,null,null,null,null,null,null,null,17,'2015-08-23 00:00:00','SYSTEM','127.0.0.1','2015-08-23 00:00:00','SYSTEM','127.0.0.1');
insert into `corp_info`(`CORP_ID`,`CORP_NM`,`CORP_LOGO_PATH`,`CORP_LOGO_NM`,`CORP_LOGO_EXT`,`CORP_LOGO_SIZE`,`CORP_LOGO_CONT_TYPE`,`CORP_LOGO_ORG_FILE_NM`,`CORP_URL`,`CORP_TEL`,`CORP_POST`,`CORP_ADR`,`CORP_ADR_DSC`,`UP_CORP_ID`,`MOD_DT`,`MOD_ID`,`MOD_IP`,`REG_DT`,`REG_ID`,`REG_IP`) values (23,'�μ���Ʈ2-2',null,null,null,null,null,null,null,null,null,null,null,17,'2015-08-23 00:00:00','SYSTEM','127.0.0.1','2015-08-23 00:00:00','SYSTEM','127.0.0.1');
insert into `corp_info`(`CORP_ID`,`CORP_NM`,`CORP_LOGO_PATH`,`CORP_LOGO_NM`,`CORP_LOGO_EXT`,`CORP_LOGO_SIZE`,`CORP_LOGO_CONT_TYPE`,`CORP_LOGO_ORG_FILE_NM`,`CORP_URL`,`CORP_TEL`,`CORP_POST`,`CORP_ADR`,`CORP_ADR_DSC`,`UP_CORP_ID`,`MOD_DT`,`MOD_ID`,`MOD_IP`,`REG_DT`,`REG_ID`,`REG_IP`) values (24,'�μ���Ʈ2-3',null,null,null,null,null,null,null,null,null,null,null,17,'2015-08-23 00:00:00','SYSTEM','127.0.0.1','2015-08-23 00:00:00','SYSTEM','127.0.0.1');
insert into `corp_info`(`CORP_ID`,`CORP_NM`,`CORP_LOGO_PATH`,`CORP_LOGO_NM`,`CORP_LOGO_EXT`,`CORP_LOGO_SIZE`,`CORP_LOGO_CONT_TYPE`,`CORP_LOGO_ORG_FILE_NM`,`CORP_URL`,`CORP_TEL`,`CORP_POST`,`CORP_ADR`,`CORP_ADR_DSC`,`UP_CORP_ID`,`MOD_DT`,`MOD_ID`,`MOD_IP`,`REG_DT`,`REG_ID`,`REG_IP`) values (25,'�μ���Ʈ3-1',null,null,null,null,null,null,null,null,null,null,null,18,'2015-08-23 00:00:00','SYSTEM','127.0.0.1','2015-08-23 00:00:00','SYSTEM','127.0.0.1');
insert into `corp_info`(`CORP_ID`,`CORP_NM`,`CORP_LOGO_PATH`,`CORP_LOGO_NM`,`CORP_LOGO_EXT`,`CORP_LOGO_SIZE`,`CORP_LOGO_CONT_TYPE`,`CORP_LOGO_ORG_FILE_NM`,`CORP_URL`,`CORP_TEL`,`CORP_POST`,`CORP_ADR`,`CORP_ADR_DSC`,`UP_CORP_ID`,`MOD_DT`,`MOD_ID`,`MOD_IP`,`REG_DT`,`REG_ID`,`REG_IP`) values (26,'�μ���Ʈ3-2',null,null,null,null,null,null,null,null,null,null,null,18,'2015-08-23 00:00:00','SYSTEM','127.0.0.1','2015-08-23 00:00:00','SYSTEM','127.0.0.1');
insert into `corp_info`(`CORP_ID`,`CORP_NM`,`CORP_LOGO_PATH`,`CORP_LOGO_NM`,`CORP_LOGO_EXT`,`CORP_LOGO_SIZE`,`CORP_LOGO_CONT_TYPE`,`CORP_LOGO_ORG_FILE_NM`,`CORP_URL`,`CORP_TEL`,`CORP_POST`,`CORP_ADR`,`CORP_ADR_DSC`,`UP_CORP_ID`,`MOD_DT`,`MOD_ID`,`MOD_IP`,`REG_DT`,`REG_ID`,`REG_IP`) values (27,'�μ���Ʈ3-3',null,null,null,null,null,null,null,null,null,null,null,18,'2015-08-23 00:00:00','SYSTEM','127.0.0.1','2015-08-23 00:00:00','SYSTEM','127.0.0.1');


insert into `dept`(`ID`,`PARENT_ID`,`NAME`) values (1,0,'ROOT');
insert into `dept`(`ID`,`PARENT_ID`,`NAME`) values (2,1,'1');
insert into `dept`(`ID`,`PARENT_ID`,`NAME`) values (3,1,'2');
insert into `dept`(`ID`,`PARENT_ID`,`NAME`) values (4,2,'1.1');
insert into `dept`(`ID`,`PARENT_ID`,`NAME`) values (5,2,'1.2');
insert into `dept`(`ID`,`PARENT_ID`,`NAME`) values (6,3,'2.1');
insert into `dept`(`ID`,`PARENT_ID`,`NAME`) values (7,6,'2.1.1');
insert into `dept`(`ID`,`PARENT_ID`,`NAME`) values (8,1,'3');
insert into `dept`(`ID`,`PARENT_ID`,`NAME`) values (9,10,'1.1.1.1');
insert into `dept`(`ID`,`PARENT_ID`,`NAME`) values (10,4,'1.1.1');


insert into `reso_auth`(`RESO_ID`,`AUTH_ID`,`AUTH_NM`) values ('RES_000001','ANONYMOUS','������');
insert into `reso_auth`(`RESO_ID`,`AUTH_ID`,`AUTH_NM`) values ('RES_000002','ANONYMOUS','�̹���');
insert into `reso_auth`(`RESO_ID`,`AUTH_ID`,`AUTH_NM`) values ('RES_000003','ANONYMOUS','css');
insert into `reso_auth`(`RESO_ID`,`AUTH_ID`,`AUTH_NM`) values ('RES_000004','ANONYMOUS','js');
insert into `reso_auth`(`RESO_ID`,`AUTH_ID`,`AUTH_NM`) values ('RES_000005','ANONYMOUS','htm');
insert into `reso_auth`(`RESO_ID`,`AUTH_ID`,`AUTH_NM`) values ('RES_000006','ANONYMOUS','jsp');
insert into `reso_auth`(`RESO_ID`,`AUTH_ID`,`AUTH_NM`) values ('RES_000007','ANONYMOUS','�α���,�α׾ƿ�');
insert into `reso_auth`(`RESO_ID`,`AUTH_ID`,`AUTH_NM`) values ('RES_000008','RESTUSER','����������');
insert into `reso_auth`(`RESO_ID`,`AUTH_ID`,`AUTH_NM`) values ('RES_000009','RESTUSER','���̾ƿ� ������');
insert into `reso_auth`(`RESO_ID`,`AUTH_ID`,`AUTH_NM`) values ('RES_000010','AUTHENTICATED','������������');
insert into `reso_auth`(`RESO_ID`,`AUTH_ID`,`AUTH_NM`) values ('RES_000011','ANONYMOUS','���ϴٿ�ε�');
insert into `reso_auth`(`RESO_ID`,`AUTH_ID`,`AUTH_NM`) values ('RES_000012','AUTHENTICATED','�˻�');


insert into `reso_info`(`RESO_ID`,`RESO_NM`,`RESO_PTN`,`RESO_TP`,`RESO_ORD`) values ('RES_000001','������','/*.ico','url',10);
insert into `reso_info`(`RESO_ID`,`RESO_NM`,`RESO_PTN`,`RESO_TP`,`RESO_ORD`) values ('RES_000002','�̹���','/images/**/*','url',20);
insert into `reso_info`(`RESO_ID`,`RESO_NM`,`RESO_PTN`,`RESO_TP`,`RESO_ORD`) values ('RES_000003','css','/**/*.css','url',30);
insert into `reso_info`(`RESO_ID`,`RESO_NM`,`RESO_PTN`,`RESO_TP`,`RESO_ORD`) values ('RES_000004','js','/**/*.js','url',40);
insert into `reso_info`(`RESO_ID`,`RESO_NM`,`RESO_PTN`,`RESO_TP`,`RESO_ORD`) values ('RES_000005','htm','/**/*.htm*','url',50);
insert into `reso_info`(`RESO_ID`,`RESO_NM`,`RESO_PTN`,`RESO_TP`,`RESO_ORD`) values ('RES_000006','jsp','/*.jsp','url',60);
insert into `reso_info`(`RESO_ID`,`RESO_NM`,`RESO_PTN`,`RESO_TP`,`RESO_ORD`) values ('RES_000007','�α���,�α׾ƿ�','/usr/lgn/*','url',70);
insert into `reso_info`(`RESO_ID`,`RESO_NM`,`RESO_PTN`,`RESO_TP`,`RESO_ORD`) values ('RES_000008','����������','/mng/**/*','url',80);
insert into `reso_info`(`RESO_ID`,`RESO_NM`,`RESO_PTN`,`RESO_TP`,`RESO_ORD`) values ('RES_000009','���̾ƿ� ������','/cmm/**/*','url',90);
insert into `reso_info`(`RESO_ID`,`RESO_NM`,`RESO_PTN`,`RESO_TP`,`RESO_ORD`) values ('RES_000010','������������','/usr/edt/mypage*.do','url',100);
insert into `reso_info`(`RESO_ID`,`RESO_NM`,`RESO_PTN`,`RESO_TP`,`RESO_ORD`) values ('RES_000011','���ϴٿ�ε�','/common/downLoadFile.do','url',110);
insert into `reso_info`(`RESO_ID`,`RESO_NM`,`RESO_PTN`,`RESO_TP`,`RESO_ORD`) values ('RES_000012','�˻�','/cmm/sch/*','url',120);


insert into `sam_level`(`ID`,`REF`,`STEP`,`LEVEL`,`SUBJECT`) values (1,1,0,0,'1');
insert into `sam_level`(`ID`,`REF`,`STEP`,`LEVEL`,`SUBJECT`) values (2,2,0,0,'2');
insert into `sam_level`(`ID`,`REF`,`STEP`,`LEVEL`,`SUBJECT`) values (3,2,1,1,'_2-1');
insert into `sam_level`(`ID`,`REF`,`STEP`,`LEVEL`,`SUBJECT`) values (4,2,4,1,'_2-2');
insert into `sam_level`(`ID`,`REF`,`STEP`,`LEVEL`,`SUBJECT`) values (5,2,2,2,'__2-1-1');
insert into `sam_level`(`ID`,`REF`,`STEP`,`LEVEL`,`SUBJECT`) values (6,2,5,2,'__2-2-1');
insert into `sam_level`(`ID`,`REF`,`STEP`,`LEVEL`,`SUBJECT`) values (7,2,3,3,'___2-1-1-1');

insert into `test_table`(`id`,`p_id`,`nm`) values (1,0,'��');
insert into `test_table`(`id`,`p_id`,`nm`) values (2,1,'����');
insert into `test_table`(`id`,`p_id`,`nm`) values (3,1,'����');
insert into `test_table`(`id`,`p_id`,`nm`) values (4,2,'���ھƵ�');
insert into `test_table`(`id`,`p_id`,`nm`) values (5,2,'���ڵ�');
insert into `test_table`(`id`,`p_id`,`nm`) values (6,3,'���־Ƶ�');
insert into `test_table`(`id`,`p_id`,`nm`) values (7,6,'���־Ƶ��� ��');
insert into `test_table`(`id`,`p_id`,`nm`) values (8,1,'��ٸ� ����');

insert into `user_info`(`USER_ID`,`CORP_ID`,`USER_NM`,`USER_PW`,`USER_EMAIL`,`USER_HP`,`USER_TEL`,`USER_AUTH`,`USE_YN`,`ACCESS_FAIL_CNT`,`MOD_DT`,`MOD_ID`,`MOD_IP`,`REG_DT`,`REG_ID`,`REG_IP`) values ('admin',1,'������','nWsxfLXl5mvIuk880vK8Z/1Wk7B4DyRNiTNi8eUbCgg=','sonys75@gmail.com','010-6477-2004','','0','Y',0,'2015-08-28 00:00:00','admin','0:0:0:0:0:0:0:1','2015-08-23 00:00:00','system','127.0.0.1');
insert into `user_info`(`USER_ID`,`CORP_ID`,`USER_NM`,`USER_PW`,`USER_EMAIL`,`USER_HP`,`USER_TEL`,`USER_AUTH`,`USE_YN`,`ACCESS_FAIL_CNT`,`MOD_DT`,`MOD_ID`,`MOD_IP`,`REG_DT`,`REG_ID`,`REG_IP`) values ('test',1,'test','WsY6Ygj95HiT6AjXehtHcsOX+ftyGMTTQCM63wmRl+o=','test@test.com','010-0000-0000','02-0000-0000','0','Y',0,'2015-09-08 23:13:36','admin','0:0:0:0:0:0:0:1','2015-09-08 17:46:58','admin','0:0:0:0:0:0:0:1');


insert into `user_auth`(`USER_ID`,`AUTH_ID`) values ('admin','SUPERADMIN');
insert into `user_auth`(`USER_ID`,`AUTH_ID`) values ('test','SITEADMIN');
