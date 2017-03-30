SELECT FILE_ID
      ,USER_ID
      ,FILE_PATH
      ,FILE_NM
      ,FILE_EXT
      ,FILE_CONT_TYPE
      ,FILE_TYPE
      ,FILE_SIZE
      ,FILE_TM
      ,FILE_WIDTH
      ,FILE_HEIGHT
      ,ORG_FILE_NM
      ,OPEN_RAN
      ,CORP_ID
      ,USE_YN
      ,DEL_YN
      ,FOOD_YN
      ,LAST_MOD_DT
      ,DATE_FORMAT(MOD_DT, '%Y-%m-%d') AS MOD_DT
      ,MOD_ID
      ,MOD_IP
      ,DATE_FORMAT(REG_DT, '%Y-%m-%d') AS REG_DT
      ,REG_ID
      ,REG_IP 
  FROM FILE_INFO 
 WHERE USE_YN='Y'
   AND OPEN_RAN = '9'
 UNION ALL
SELECT FILE_ID
      ,USER_ID
      ,FILE_PATH
      ,FILE_NM
      ,FILE_EXT
      ,FILE_CONT_TYPE
      ,FILE_TYPE
      ,FILE_SIZE
      ,FILE_TM
      ,FILE_WIDTH
      ,FILE_HEIGHT
      ,ORG_FILE_NM
      ,OPEN_RAN
      ,CORP_ID
      ,USE_YN
      ,DEL_YN
      ,FOOD_YN
      ,LAST_MOD_DT
      ,DATE_FORMAT(MOD_DT, '%Y-%m-%d') AS MOD_DT
      ,MOD_ID
      ,MOD_IP
      ,DATE_FORMAT(REG_DT, '%Y-%m-%d') AS REG_DT
      ,REG_ID
      ,REG_IP 
  FROM FILE_INFO
 WHERE USE_YN='Y'
   AND OPEN_RAN = '1'
   AND CORP_ID IN (
        		       SELECT D.CORP_ID
        		         FROM (
        		               SELECT CORP_CONNECT_BY_PARENT() AS CORP_ID
        		                     ,@LEVEL AS LEVEL
        		                 FROM (
        		                       SELECT @START_WITH := 1
        		                             ,@ID := @START_WITH
        		                             ,@LEVEL := 0
        		                      ) VARS
        		                     ,CORP_INFO
        		                WHERE @ID IS NOT NULL
        		              ) FUNC
        		         JOIN CORP_INFO D
        		           ON FUNC.CORP_ID = D.CORP_ID
                    UNION ALL
                   SELECT D.CORP_ID
                     FROM (
                           SELECT B._ID
                                 ,@LV2 := @LV2 + 1 
                                  AS LEVEL
                             FROM (
                                   SELECT @R AS _ID
                                         ,( 
                                           SELECT @R := UP_CORP_ID 
                                             FROM CORP_INFO 
                                            WHERE CORP_ID = _ID 
                                          ) 
                                          AS PARENT
                                         ,@L := @L + 1 
                                          AS LV
                                     FROM (
                                           SELECT @R := 1
                                                 ,@L := 0
                                          ) VARS
                                         ,CORP_INFO D
                                    WHERE @R <> 0
                                    ORDER BY LV DESC
                                  ) B, (SELECT @LV2 := 0 ) VARS2
                          ) FUNC
                      JOIN CORP_INFO D
                        ON FUNC._ID = D.CORP_ID
                  )
 UNION ALL
SELECT FILE_ID
      ,USER_ID
      ,FILE_PATH
      ,FILE_NM
      ,FILE_EXT
      ,FILE_CONT_TYPE
      ,FILE_TYPE
      ,FILE_SIZE
      ,FILE_TM
      ,FILE_WIDTH
      ,FILE_HEIGHT
      ,ORG_FILE_NM
      ,OPEN_RAN
      ,CORP_ID
      ,USE_YN
      ,DEL_YN
      ,FOOD_YN
      ,LAST_MOD_DT
      ,DATE_FORMAT(MOD_DT, '%Y-%m-%d') AS MOD_DT
      ,MOD_ID
      ,MOD_IP
      ,DATE_FORMAT(REG_DT, '%Y-%m-%d') AS REG_DT
      ,REG_ID
      ,REG_IP 
  FROM FILE_INFO
 WHERE USE_YN='Y'
   AND OPEN_RAN = '0'   
   AND USER_ID = 'TEST10'
;

select *
  from file_info
 where use_yn='Y'
   and open_ran = '1'
   and corp_id in (
          		       SELECT CORP_ID
          		         FROM CORP_INFO
          		        WHERE CORP_ID = 16
          		        UNION ALL
          		       SELECT D.CORP_ID
          		         FROM (
          		               SELECT CORP_CONNECT_BY_PARENT() AS CORP_ID
          		                     ,@LEVEL AS LEVEL
          		                 FROM (
          		                       SELECT @START_WITH := 16
          		                             ,@ID := @START_WITH
          		                             ,@LEVEL := 0
          		                      ) VARS
          		                     ,CORP_INFO
          		                WHERE @ID IS NOT NULL
          		              ) FUNC
          		         JOIN CORP_INFO D
          		           ON FUNC.CORP_ID = D.CORP_ID    
                  SELECT d.corp_id
                    FROM (
                          SELECT B._id
                                ,@lv2 := @lv2 + 1 
                                 AS level
                            FROM (
                                  SELECT @r AS _id
                                        ,( 
                                          SELECT @r := up_corp_id 
                                            FROM corp_info 
                                           WHERE corp_id = _id 
                                         ) 
                                         AS parent
                                        ,@l := @l + 1 
                                         AS lv
                                    FROM (
                                          SELECT @r := 16
                                                ,@l := 0
                                         ) vars
                                        ,corp_info d
                                   WHERE @r <> 0
                                   ORDER BY lv DESC
                                 ) B, (SELECT @lv2 := 0 ) vars2
                         ) func
                     Join corp_info d
                       ON func._id = d.corp_id
                     union all
                    
                     )
   or corp_id in (
				          		       SELECT CORP_ID
				          		         FROM CORP_INFO
				          		        WHERE CORP_ID = 16
				          		        UNION ALL
				          		       SELECT D.CORP_ID
				          		         FROM (
				          		               SELECT CORP_CONNECT_BY_PARENT() AS CORP_ID
				          		                     ,@LEVEL AS LEVEL
				          		                 FROM (
				          		                       SELECT @START_WITH := 16
				          		                             ,@ID := @START_WITH
				          		                             ,@LEVEL := 0
				          		                      ) VARS
				          		                     ,CORP_INFO
				          		                WHERE @ID IS NOT NULL
				          		              ) FUNC
				          		         JOIN CORP_INFO D
				          		           ON FUNC.CORP_ID = D.CORP_ID   
                 )
;

select *
  from file_info a
      ,(
       SELECT CORP_ID
         FROM CORP_INFO
        WHERE CORP_ID = 15
        UNION ALL
       SELECT D.CORP_ID
         FROM (
               SELECT CORP_CONNECT_BY_PARENT() AS CORP_ID
                     ,@LEVEL AS LEVEL
                 FROM (
                       SELECT @START_WITH := 15
                             ,@ID := @START_WITH
                             ,@LEVEL := 0
                      ) VARS
                     ,CORP_INFO
                WHERE @ID IS NOT NULL
              ) FUNC
         JOIN CORP_INFO D
           ON FUNC.CORP_ID = D.CORP_ID) b
 where a.use_yn='Y'
   and a.open_ran = '1'
   and b.corp_id in (a.corp_id)  
;



				          		       SELECT CORP_ID
				          		         FROM CORP_INFO
				          		        WHERE CORP_ID = 15
				          		        UNION ALL
				          		       SELECT D.CORP_ID
				          		         FROM (
				          		               SELECT CORP_CONNECT_BY_PARENT() AS CORP_ID
				          		                     ,@LEVEL AS LEVEL
				          		                 FROM (
				          		                       SELECT @START_WITH := 15
				          		                             ,@ID := @START_WITH
				          		                             ,@LEVEL := 0
				          		                      ) VARS
				          		                     ,CORP_INFO
				          		                WHERE @ID IS NOT NULL
				          		              ) FUNC
				          		         JOIN CORP_INFO D
				          		           ON FUNC.CORP_ID = D.CORP_ID
                                 
;

SELECT CONCAT(REPEAT(' ', level - 1), d.name) AS name
      ,d.Id
      ,d.Parent_id
      ,func.level 
  FROM (
        SELECT B._id
              ,@lv2 := @lv2 + 1 
               AS level
          FROM (
                SELECT @r AS _id
                      ,( 
                        SELECT @r := Parent_Id 
                          FROM Dept 
                         WHERE Id = _id 
                       ) 
                       AS parent
                      ,@l := @l + 1 
                       AS lv
                  FROM (
                        SELECT @r := 6
                              ,@l := 0
                       ) vars
                      ,Dept d
                 WHERE @r <> 0
                 ORDER BY lv DESC
               ) B, (SELECT @lv2 := 0 ) vars2
       ) func
   Join Dept d
     ON func._id = d.Id
     
select * from dept   

select * from corp_info


SELECT CONCAT(REPEAT(' ', level - 1), d.corp_nm) AS name
      ,d.corp_id
      ,d.up_corp_id
      ,func.level 
  FROM (
        SELECT B._id
              ,@lv2 := @lv2 + 1 
               AS level
          FROM (
                SELECT @r AS _id
                      ,( 
                        SELECT @r := up_corp_id 
                          FROM corp_info 
                         WHERE corp_id = _id 
                       ) 
                       AS parent
                      ,@l := @l + 1 
                       AS lv
                  FROM (
                        SELECT @r := 16
                              ,@l := 0
                       ) vars
                      ,corp_info d
                 WHERE @r <> 0
                 ORDER BY lv DESC
               ) B, (SELECT @lv2 := 0 ) vars2
       ) func
   Join corp_info d
     ON func._id = d.corp_id
     
     
SELECT d.corp_id
  FROM (
        SELECT B._id
              ,@lv2 := @lv2 + 1 
               AS level
          FROM (
                SELECT @r AS _id
                      ,( 
                        SELECT @r := up_corp_id 
                          FROM corp_info 
                         WHERE corp_id = _id 
                       ) 
                       AS parent
                      ,@l := @l + 1 
                       AS lv
                  FROM (
                        SELECT @r := 16
                              ,@l := 0
                       ) vars
                      ,corp_info d
                 WHERE @r <> 0
                 ORDER BY lv DESC
               ) B, (SELECT @lv2 := 0 ) vars2
       ) func
   Join corp_info d
     ON func._id = d.corp_id