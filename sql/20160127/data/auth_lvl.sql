INSERT INTO `AUTH_LVL`(`PARENT_AUTH_ID`,`CHILD_AUTH_ID`) VALUES ('AUTHENTICATED','ANONYMOUS');
INSERT INTO `AUTH_LVL`(`PARENT_AUTH_ID`,`CHILD_AUTH_ID`) VALUES ('RESTADMIN','RESTUSER');
INSERT INTO `AUTH_LVL`(`PARENT_AUTH_ID`,`CHILD_AUTH_ID`) VALUES ('RESTUSER','AUTHENTICATED');
INSERT INTO `AUTH_LVL`(`PARENT_AUTH_ID`,`CHILD_AUTH_ID`) VALUES ('ROADADMIN','RESTADMIN');
INSERT INTO `AUTH_LVL`(`PARENT_AUTH_ID`,`CHILD_AUTH_ID`) VALUES ('SITEADMIN','ROADADMIN');
INSERT INTO `AUTH_LVL`(`PARENT_AUTH_ID`,`CHILD_AUTH_ID`) VALUES ('SUPERADMIN','SITEADMIN');
