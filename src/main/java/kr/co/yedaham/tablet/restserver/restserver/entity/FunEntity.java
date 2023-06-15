package kr.co.yedaham.tablet.restserver.restserver.entity;

import kr.co.yedaham.tablet.restserver.restserver.model.fun.*;
import kr.co.yedaham.tablet.restserver.restserver.model.funmessage.FunMessageSigninInfo;
import lombok.Data;
import org.hibernate.annotations.DynamicUpdate;

import javax.persistence.*;



@SqlResultSetMapping(
        name = "getFunFuneralFirstHelpInfoMapping",
        classes = @ConstructorResult(
                targetClass = FunMessageSigninInfo.class,
                columns = {
                        @ColumnResult(name = "EMP_NO", type = String.class),
                        @ColumnResult(name = "EMP_NAME", type = String.class),
                        @ColumnResult(name = "CELL_PHONE_NO", type = String.class),
                        @ColumnResult(name = "OTP", type = String.class),
                        @ColumnResult(name = "TOKEN", type = String.class),
                        @ColumnResult(name = "ROLE", type = String.class),
                        @ColumnResult(name = "ADDR", type = String.class),
                        @ColumnResult(name = "MFS_USERID", type = String.class),
                })
)


@NamedNativeQuery(name = "getFunFuneralFirstHelpInfo",
        query = " SELECT \n" +
                "   EMP_NO\n" +
                "  ,EMP_NAME\n" +
                "  ,CELL_PHONE_NO\n" +
                "  , '' AS OTP\n" +
                "  , '' AS TOKEN\n" +
                "  , '' AS ROLE\n" +
                "  , ADDR \n" +
                "  , MFS_USERID\n" +
                " FROM TBFU4004\n" +
                " WHERE DEPT_CODE IN ('208000','205000')\n" +
                " AND USE_YN ='Y'\n" +
                " AND DEL_YN ='N'\n" +
                " AND MFS_USERID IS NOT NULL\n" +
                " AND CELL_PHONE_NO =:phone",
        resultClass = FunEntity.class,
        resultSetMapping = "getFunFuneralFirstHelpInfoMapping")




@SqlResultSetMapping(
        name = "getSubcontractorPhoneListMapping",
        classes = @ConstructorResult(
                targetClass = Subcontractor.class,
                columns = {
                        @ColumnResult(name = "USER_NAME", type = String.class),
                        @ColumnResult(name = "CELL_PHONE_NO", type = String.class),
                })
)


@NamedNativeQuery(name = "getSubcontractorPhoneList",
        query = " SELECT B.EMP_NAME AS USER_NAME, REPLACE(NVL(A.TELNO, B.CELL_PHONE_NO),'-','') AS CELL_PHONE_NO FROM SY002M A\n" +
                "LEFT JOIN VW_BO_EMPLOYEE B ON A.USERID = B.EMP_NO\n" +
                "WHERE A.USEYN = 'Y' AND A.USERID = :userid",
        resultClass = FunEntity.class,
        resultSetMapping = "getSubcontractorPhoneListMapping")

@SqlResultSetMapping(
        name = "getSubcontractorFunListMapping",
        classes = @ConstructorResult(
                targetClass = SubcontractorFunList.class,
                columns = {
                        @ColumnResult(name = "FUN_CTRL_NO", type = String.class),
                })
)


@NamedNativeQuery(name = "getSubcontractorFunList",
        query = " \n" +
                "SELECT\n" +
                "    FU1001.FUN_CTRL_NO\n" +
                "FROM\n" +
                "  GB998D GB98D INNER JOIN TBFU4004 FU404 ON GB98D.REFFLD1 = TRIM(REPLACE(FU404.BIRTH_YYMMDD, '-', '')) \n" +
                "                                            AND FU404.USE_YN = 'Y' AND FU404.DEL_YN = 'N'\n" +
                "  INNER JOIN TBFU1001 FU1001 ON FU1001.FIRST_HELP = FU404.EMP_NO                                            \n" +
                "WHERE\n" +
                "  FU1001.STATUS IN ('1', '2', '4')\n" +
                "  AND GB98D.MCODE='FUN_COMP'\n" +
                "  AND GB98D.DCODE = :userid -- 거래처번호\n" +
                "  AND FU1001.REG_DATE >= SYSDATE - 31\n" +
                "\n" +
                "UNION\n" +
                "-- 의전투입인력현황\n" +
                "SELECT\n" +
                "    FU01.FUN_CTRL_NO\n" +
                "FROM\n" +
                "  TBFU1001 FU01 INNER JOIN TBFU1003 FU03 ON FU03.FUN_CTRL_NO = FU01.FUN_CTRL_NO\n" +
                "  INNER JOIN GB998D GB98D ON GB98D.REFFLD1 = FU03.REGNO AND GB98D.MCODE = 'FUN_COMP'\n" +
                "WHERE\n" +
                "  FU01.STATUS IN ('1', '2', '4')\n" +
                "  AND GB98D.MCODE = 'FUN_COMP'\n" +
                "  AND GB98D.DCODE = :userid -- 거래처번호\n" +
                "  AND FU01.REG_DATE >= SYSDATE - 31\n" +
                "ORDER BY FUN_CTRL_NO",
        resultClass = FunEntity.class,
        resultSetMapping = "getSubcontractorFunListMapping")

@SqlResultSetMapping(
        name = "getFunCarInfoMapping",
        classes = @ConstructorResult(
                targetClass = FunCarInfo.class,
                columns = {
                        @ColumnResult(name = "LIM_USE_YN", type = String.class),
                        @ColumnResult(name = "BUS_USE_YN", type = String.class),
                        @ColumnResult(name = "LIM_FUN_NM", type = String.class),
                        @ColumnResult(name = "LIM_BIZ_CD", type = String.class),
                        @ColumnResult(name = "LIM_CEME", type = String.class),
                        @ColumnResult(name = "LIM_CEME_TX", type = String.class),
                        @ColumnResult(name = "LIM_CEME_SEC", type = String.class),
                        @ColumnResult(name = "LIM_CEME_S_TX", type = String.class),
                        @ColumnResult(name = "LIM_DEST_ETC", type = String.class),
                        @ColumnResult(name = "LIM_DIST", type = String.class),
                        @ColumnResult(name = "LIM_CHKPEOPLE", type = String.class),
                        @ColumnResult(name = "LIM_NECESSARY", type = String.class),
                        @ColumnResult(name = "LIM_DRIVER", type = String.class),
                        @ColumnResult(name = "LIM_DRIVER_TEL", type = String.class),
                        @ColumnResult(name = "LIM_USE_FROM_DATETIME", type = String.class),
                        @ColumnResult(name = "LIM_USE_TO_DATETIME", type = String.class),
                        @ColumnResult(name = "BUS_FUN_NM", type = String.class),
                        @ColumnResult(name = "BUS_BIZ_CD", type = String.class),
                        @ColumnResult(name = "BUS_CEME", type = String.class),
                        @ColumnResult(name = "BUS_CEME_TX", type = String.class),
                        @ColumnResult(name = "BUS_CEME_SEC", type = String.class),
                        @ColumnResult(name = "BUS_CEME_S_TX", type = String.class),
                        @ColumnResult(name = "BUS_DEST_ETC", type = String.class),
                        @ColumnResult(name = "BUS_DIST", type = String.class),
                        @ColumnResult(name = "BUS_CHKPEOPLE", type = String.class),
                        @ColumnResult(name = "BUS_NECESSARY", type = String.class),
                        @ColumnResult(name = "BUS_DRIVER", type = String.class),
                        @ColumnResult(name = "BUS_DRIVER_TEL", type = String.class),
                        @ColumnResult(name = "BUS_USE_FROM_DATETIME", type = String.class),
                        @ColumnResult(name = "BUS_USE_TO_DATETIME", type = String.class)
                })
)


@NamedNativeQuery(name = "findGetFunCarInfo",
        query = " SELECT                                                                                                          \n" +
                "       FU01.LIM_USE_YN	                            --리무진사용여부							                   \n" +
                "     , FU01.BUS_USE_YN	                            --버스사용여부							                     \n" +
                "     , CM05_LIM.FUN_NM   AS LIM_FUN_NM                            --리무진 업체명								                 \n" +
                "     , CM03_LIM.BIZ_CD  AS LIM_BIZ_CD                             --리무진 업체코드								               \n" +
                "     , FU01.LIM_CEME	                                --리무진 1차 장지 체크							           \n" +
                "     , FU01.LIM_CEME_TX                              --리무진 1차 장지								               \n" +
                "     , FU01.LIM_CEME_SEC	                            --리무진 2차 장지 체크							           \n" +
                "     , FU01.LIM_CEME_S_TX                            --리무진 2차 장지								               \n" +
                "     , CM03_LIM.DEST_ETC AS LIM_DEST_ETC             --리무진 행선지 기타								           \n" +
                "     , NVL(CM03_LIM.DIST, 0) AS LIM_DIST             --리무진 이동거리								               \n" +
                "     , CM03_LIM.CHKPEOPLE AS LIM_CHKPEOPLE           --리무진 탑승인원								               \n" +
                "     , CM03_LIM.NECESSARY AS LIM_NECESSARY           --리무진 사용금액								               \n" +
                "     , CM03_LIM.DIRVER AS LIM_DRIVER                 --리무진 운전기사								               \n" +
                "     , CM03_LIM.DIRVER_TEL AS LIM_DRIVER_TEL         --리무진 운전기사 연락처    								   \n" +
                "     , DECODE(CM03_LIM.USE_FROM_DATE, NULL, '', SUBSTR(CM03_LIM.USE_FROM_DATE, 1, 4) || '-'  || SUBSTR(CM03_LIM.USE_FROM_DATE, 5, 2) || '-' || SUBSTR(CM03_LIM.USE_FROM_DATE, 7, 2)) || ' ' || DECODE(CM03_LIM.USE_FROM_TIME, NULL, '', LPAD(CM03_LIM.USE_FROM_TIME, 2, '0') || ':00') AS LIM_USE_FROM_DATETIME	--리무진 사용 시작일시		\n" +
                "     , DECODE(CM03_LIM.USE_TO_DATE, NULL, '', SUBSTR(CM03_LIM.USE_TO_DATE, 1, 4) || '-'  || SUBSTR(CM03_LIM.USE_TO_DATE, 5, 2) || '-' || SUBSTR(CM03_LIM.USE_TO_DATE, 7, 2)) || ' ' ||  DECODE(CM03_LIM.USE_TO_TIME, NULL, '', LPAD(CM03_LIM.USE_TO_TIME, 2, '0') || ':00') AS LIM_USE_TO_DATETIME	    --리무진 사용 종료일시         \n" +
                "     , CM05_BUS.FUN_NM  AS BUS_FUN_NM                             --버스 업체명								                   \n" +
                "     , CM03_BUS.BIZ_CD  AS BUS_BIZ_CD                             --버스 업체코드	    							             \n" +
                "     , FU01.BUS_CEME	                                --버스 1차 장지 체크장지							         \n" +
                "     , FU01.BUS_CEME_TX	                            --버스 1차 장지 							                 \n" +
                "     , FU01.BUS_CEME_SEC	                            --버스 2차 장지 체크							             \n" +
                "     , FU01.BUS_CEME_S_TX	                        --버스 2차 장지 							                   \n" +
                "     , CM03_BUS.DEST_ETC AS BUS_DEST_ETC             --버스 행선지 기타								             \n" +
                "     , NVL(CM03_BUS.DIST, 0) AS BUS_DIST             --버스 이동거리   								             \n" +
                "     , CM03_BUS.CHKPEOPLE AS BUS_CHKPEOPLE           --버스 탑승인원								                 \n" +
                "     , CM03_BUS.NECESSARY AS BUS_NECESSARY           --버스 사용금액								                 \n" +
                "     , CM03_BUS.DIRVER AS BUS_DRIVER                 --버스 운전기사								                 \n" +
                "     , CM03_BUS.DIRVER_TEL AS BUS_DRIVER_TEL         --버스 운전기사 연락처    								     \n" +
                "     , DECODE(CM03_BUS.USE_FROM_DATE, NULL, '', SUBSTR(CM03_BUS.USE_FROM_DATE, 1, 4) || '-'  || SUBSTR(CM03_BUS.USE_FROM_DATE, 5, 2) || '-' || SUBSTR(CM03_BUS.USE_FROM_DATE, 7, 2)) || ' ' || DECODE(CM03_BUS.USE_FROM_TIME, NULL, '', LPAD(CM03_BUS.USE_FROM_TIME, 2, '0') || ':00') AS BUS_USE_FROM_DATETIME	--버스 사용 시작일				 \n" +
                "     , DECODE(CM03_BUS.USE_TO_DATE, NULL, '', SUBSTR(CM03_BUS.USE_TO_DATE, 1, 4) || '-'  || SUBSTR(CM03_BUS.USE_TO_DATE, 5, 2) || '-' || SUBSTR(CM03_BUS.USE_TO_DATE, 7, 2)) || ' ' || DECODE(CM03_BUS.USE_TO_TIME, NULL, '', LPAD(CM03_BUS.USE_TO_TIME, 2, '0') || ':00') AS BUS_USE_TO_DATETIME	    --버스 사용 종료일   				\n" +
                " FROM								                                                                               \n" +
                "     TBFU1001 FU01								                                                                   \n" +
                "     LEFT OUTER JOIN (								                                                               \n" +
                "         SELECT FUN_CTRL_NO, MIN(LIM_REQ_SEQ) LIM_REQ_SEQ, MIN(BUS_REQ_SEQ) BUS_REQ_SEQ 						 \n" +
                "         FROM (								                                                                     \n" +
                "             SELECT 								                                                                 \n" +
                "               FUN_CTRL_NO, CAR_GB								                                                   \n" +
                "               , DECODE(CAR_GB, '2', REQ_SEQ, NULL) LIM_REQ_SEQ 								                     \n" +
                "               , DECODE(CAR_GB, '1', REQ_SEQ, NULL) BUS_REQ_SEQ								                     \n" +
                "             FROM TBCM1003								                                                           \n" +
                "             WHERE FUN_CTRL_NO = :functrlno								                                                   \n" +
                "         )								                                                                           \n" +
                "         GROUP BY FUN_CTRL_NO								                                                       \n" +
                "     ) GRP_CM03 ON FU01.FUN_CTRL_NO = GRP_CM03.FUN_CTRL_NO								                           \n" +
                "     LEFT OUTER JOIN TBCM1003 CM03_LIM ON FU01.FUN_CTRL_NO = CM03_LIM.FUN_CTRL_NO 								   \n" +
                "                                          AND CM03_LIM.CAR_GB = '2'								                 \n" +
                "                                          AND GRP_CM03.LIM_REQ_SEQ = CM03_LIM.REQ_SEQ               \n" +
                "     LEFT OUTER JOIN TBCM1005 CM05_LIM ON CM03_LIM.BIZ_CD = CM05_LIM.FUN_CD								         \n" +
                "     LEFT OUTER JOIN TBCM1003 CM03_BUS ON FU01.FUN_CTRL_NO = CM03_BUS.FUN_CTRL_NO 								   \n" +
                "                                          AND CM03_BUS.CAR_GB = '1'								                 \n" +
                "                                          AND GRP_CM03.BUS_REQ_SEQ = CM03_BUS.REQ_SEQ 							 \n" +
                "     LEFT OUTER JOIN TBCM1005 CM05_BUS ON CM03_BUS.BIZ_CD = CM05_BUS.FUN_CD    								     \n" +
                " WHERE								                                                                               \n" +
                "    FU01.FUN_CTRL_NO = :functrlno								                                                               "
        ,
        resultClass = FunCarInfo.class,
        resultSetMapping = "getFunCarInfoMapping")

@SqlResultSetMapping(
        name = "getFunProgressInfoMapping",
        classes = @ConstructorResult(
                targetClass = FunProgressInfo.class,
                columns = {
                        @ColumnResult(name = "REG_DATE_TIME", type = String.class),
                        @ColumnResult(name = "ARRV_DATE_TIME", type = String.class),
                        @ColumnResult(name = "DEAD_DATE_TIME", type = String.class),
                        @ColumnResult(name = "FIRST_CALL_DT", type = String.class),
                        @ColumnResult(name = "MORTUARY_SET_DT", type = String.class),
                        @ColumnResult(name = "DISPOS_ITEM_ARRV_DT", type = String.class),
                        @ColumnResult(name = "MEET_COUNSEL_DT", type = String.class),
                        @ColumnResult(name = "DISTB_ARRV_HOPE_DATE", type = String.class),
                        @ColumnResult(name = "DISTB_REAL_ARRV_DATE", type = String.class),
                        @ColumnResult(name = "CASK_DATE_TIME", type = String.class),
                        @ColumnResult(name = "CORT_DATE_TIME", type = String.class),
                        @ColumnResult(name = "FUN_START_DATE_TIME", type = String.class),
                        @ColumnResult(name = "FUN_END_DATE_TIME", type = String.class),
                        @ColumnResult(name = "DISTB_DELAY_TYPE", type = String.class),
                        @ColumnResult(name = "FUN_DAYS", type = String.class),
                        @ColumnResult(name = "PROD_ARRV_DATE_TIME", type = String.class),
                        @ColumnResult(name = "CORT_SUP_YN", type = String.class),
                        @ColumnResult(name = "CORT_SUP_NCD", type = String.class),
                })
)


@NamedNativeQuery(name = "findFunProgressInfo",
        query = "  SELECT                                                                                             \n" +
                "    TO_CHAR(FU01.REG_DATE, 'YYYYMMDDHH24MI') REG_DATE_TIME --접수일시                                \n" +
                "    , FU01.ARRV_DATE || FU01.ARRV_TIME || FU01.ARRV_MIN AS ARRV_DATE_TIME --도착일시                 \n" +
                "    , FU01.DEAD_DATE || FU01.DEAD_TIME || FU01.DEAD_MIN AS DEAD_DATE_TIME --사망일시                 \n" +
                "    , FU4462.FIRST_CALL_DT --최초통화일시                                                  \n" +
                "    , FU4462.MORTUARY_SET_DT  --빈소차림일시                                               \n" +
                "    , FU4462.DISPOS_ITEM_ARRV_DT --일회용품도착일시(DISPOSABLE_ITEM)                       \n" +
                "    , FU4462.MEET_COUNSEL_DT --대면상담일시(개발필요)                                                \n" +
                "    , FU4462.DISTB_ARRV_HOPE_DATE --물류도착희망일시                                                 \n" +
                "    , FU4462.DISTB_REAL_ARRV_DATE --물류실제도착일시                                                 \n" +
                "    , FU01.CASK_DATE || FU01.CASK_TIME || FU01.CASK_MIN AS CASK_DATE_TIME --입관일시                 \n" +
                "    , FU01.CORT_DATE || FU01.CORT_TIME || FU01.CORT_MIN AS CORT_DATE_TIME --발인일시                 \n" +
                "    , FU01.FUN_START_DATE || FU01.FUN_START_TIME || FU01.FUN_START_MIN AS FUN_START_DATE_TIME --시작일시     \n" +
                "    , FU01.FUN_END_DATE || FU01.FUN_END_TIME || FU01.FUN_END_MIN AS FUN_END_DATE_TIME --종료일시     \n" +
                "    , FU4462.DISTB_DELAY_TYPE --상복지연유무                                               \n" +
                "    , FU01.FUN_DAYS --의전일장(수)                                                                   \n" +
                "    , FU01.PROD_ARRV_DATE || FU01.PROD_ARRV_TIME || FU01.PROD_ARRV_MIN AS PROD_ARRV_DATE_TIME --물품도착일시                                                    \n" +
                "    , FU01.CORT_SUP_YN AS CORT_SUP_YN--발인지원가능여부                                                                    \n" +
                "    , FU01.CORT_SUP_N_CD as CORT_SUP_NCD--발인지원불가능코드                                                                    \n" +
                "  FROM                                                                                               \n" +
                "    TBFU1001 FU01 LEFT JOIN TBFU4462 FU4462 ON FU01.FUN_CTRL_NO = FU4462.FUN_CTRL_NO AND FU4462.USE_YN = 'Y' AND FU4462.DEL_YN = 'N'  \n" +
                "  WHERE                                                                                              \n" +
                "    FU01.FUN_CTRL_NO = :functrlno                   \n"
        ,
        resultClass = FunEntity.class,
        resultSetMapping = "getFunProgressInfoMapping")

@SqlResultSetMapping(
        name = "getFunCustomerInfoMapping",
        classes = @ConstructorResult(
                targetClass = FunCustomerInfo.class,
                columns = {
                        @ColumnResult(name = "DEAD_NM", type = String.class),
                        @ColumnResult(name = "SEX", type = String.class),
                        @ColumnResult(name = "AGE", type = String.class),
                        @ColumnResult(name = "REAT", type = String.class),
                        @ColumnResult(name = "CLAN", type = String.class),
                        @ColumnResult(name = "DEAD_REA", type = String.class),
                        @ColumnResult(name = "RELIGION", type = String.class),
                        @ColumnResult(name = "BAPTISM_NM", type = String.class),
                        @ColumnResult(name = "CHU_DUTY", type = String.class),
                        @ColumnResult(name = "DEAD_HEIG", type = String.class),
                        @ColumnResult(name = "DEAD_WEIG", type = String.class),
                        @ColumnResult(name = "MOURNER", type = String.class),
                        @ColumnResult(name = "BERE_CD1", type = String.class),
                        @ColumnResult(name = "BERE_NUM1", type = String.class),
                        @ColumnResult(name = "BERE_CD2", type = String.class),
                        @ColumnResult(name = "BERE_NUM2", type = String.class),
                        @ColumnResult(name = "BERE_CD3", type = String.class),
                        @ColumnResult(name = "BERE_NUM3", type = String.class),
                        @ColumnResult(name = "BERE_CD4", type = String.class),
                        @ColumnResult(name = "BERE_NUM4", type = String.class),
                        @ColumnResult(name = "BERE_CD5", type = String.class),
                        @ColumnResult(name = "BERE_NUM5", type = String.class),
                        @ColumnResult(name = "BERE_CD6", type = String.class),
                        @ColumnResult(name = "BERE_NUM6", type = String.class),
                        @ColumnResult(name = "BERE_CD7", type = String.class),
                        @ColumnResult(name = "BERE_NUM7", type = String.class),
                        @ColumnResult(name = "BERE_CD8", type = String.class),
                        @ColumnResult(name = "BERE_NUM8", type = String.class),
                        @ColumnResult(name = "CASKET_SIZE", type = String.class),

                })
)


@NamedNativeQuery(name = "findFunCustomerInfo",
        query = "SELECT\n" +
                "    DEAD_NM\n" +
                "    , SEX\n" +
                "    , AGE\n" +
                "    , REAT\n" +
                "    , CLAN\n" +
                "    , DEAD_REA\n" +
                "    , RELIGION\n" +
                "    , BAPTISM_NM\n" +
                "    , CHU_DUTY\n" +
                "    , DEAD_HEIG\n" +
                "    , DEAD_WEIG\n" +
                "    , MOURNER\n" +
                "    , BERE_CD1\n" +
                "    , BERE_NUM1\n" +
                "    , BERE_CD2\n" +
                "    , BERE_NUM2\n" +
                "    , BERE_CD3\n" +
                "    , BERE_NUM3\n" +
                "    , BERE_CD4\n" +
                "    , BERE_NUM4\n" +
                "    , BERE_CD5\n" +
                "    , BERE_NUM5\n" +
                "    , BERE_CD6\n" +
                "    , BERE_NUM6\n" +
                "    , BERE_CD7\n" +
                "    , BERE_NUM7\n" +
                "    , BERE_CD8\n" +
                "    , BERE_NUM8\n" +
                "    , CASKET_SIZE --관사이즈(현재 ERP에 없는 항목임)\n" +
                "FROM \n" +
                "  TBFU1001 FU01\n" +
                "WHERE\n" +
                "  FU01.FUN_CTRL_NO = :functrlno",
        resultClass = FunEntity.class,
        resultSetMapping = "getFunCustomerInfoMapping")

@SqlResultSetMapping(
        name = "getFuneralInfo1Mapping",
        classes = @ConstructorResult(
                targetClass = FunFuneralInfo1.class,
                columns = {
                        @ColumnResult(name = "FUN_CTRL_NO", type = String.class),
                        @ColumnResult(name = "FUN_CD", type = String.class),
                        @ColumnResult(name = "FUN_NM", type = String.class),
                        @ColumnResult(name = "TEL_NO", type = String.class),
                        @ColumnResult(name = "FUN_ADDR", type = String.class),
                        @ColumnResult(name = "FUN_RES_GB", type = String.class),
                        @ColumnResult(name = "FUN_RES_GB_NM", type = String.class),
                        @ColumnResult(name = "AMBU_BIZ_CD", type = String.class),
                        @ColumnResult(name = "AMBU_BIZ_NM", type = String.class),
                        @ColumnResult(name = "AMBU_BIZ_TEL_NO", type = String.class),
                        @ColumnResult(name = "AMBU_USE_YN", type = String.class),
                        @ColumnResult(name = "AMB_REASON_CODE", type = String.class),
                        @ColumnResult(name = "AMB_REASON_NM", type = String.class),
                        @ColumnResult(name = "AMBU_COST_USE_YN", type = String.class),
                        @ColumnResult(name = "AMB_AMT_TYPE", type = String.class),
                        @ColumnResult(name = "AMB_AMT_TYPE_NM", type = String.class),
                        @ColumnResult(name = "AMB_AMT", type = String.class)
                })
)


@NamedNativeQuery(name = "findFunFuneralInfo1",
        query = "SELECT\n" +
                "    FU01.FUN_CTRL_NO\n" +
                "  , FU01.FUN_CD  --장례식장코드\n" +
                "  , CM05_FUN.FUN_NM  --장례식장명\n" +
                "  , CM05_FUN.TEL_NO1 AS TEL_NO --장례식장전화번호\n" +
                "  , CM05_FUN.ADDR1 || ' ' || CM05_FUN.ADDR2 AS FUN_ADDR --장례식장 주소\n" +
                "  , FU01.FUN_RES_GB --장례식장 예약구분\n" +
                "  , (SELECT CD_NM FROM TBCM1012 WHERE TYPE_CD = 'FUN_RES_GB' AND CD = FU01.FUN_RES_GB) AS FUN_RES_GB_NM --장례식장 예약구분명\n" +
                "  , FU01.AMBU_BIZ_CD --앰뷸런스 업체코드\n" +
                "  , CM05_AMB.FUN_NM AS AMBU_BIZ_NM--앰뷸런스 업체명\n" +
                "  , CM05_AMB.TEL_NO1 AS AMBU_BIZ_TEL_NO --앰뷸런스 전화번호\n" +
                "  , FU01.AMBU_USE_YN\n" +
                "  , FU01.AMB_REASON_CODE --앰블런스사유코드\n" +
                "  , (SELECT CD_NM FROM TBCM1012 WHERE TYPE_CD = 'AMBLU_REASON' AND CD = FU01.AMB_REASON_CODE) AS AMB_REASON_NM --앰블런스미사용사유명\n" +
                "  , FU01.AMBU_COST_USE_YN\n" +
                "  , AMB_AMT_TYPE  --앰블런스 비용처리 방식\n" +
                "  , (SELECT CD_NM FROM TBCM1012 WHERE TYPE_CD = 'AMB_AMT_TYPE' AND CD = FU01.AMB_AMT_TYPE) AS AMB_AMT_TYPE_NM  --앰블런스 비용처리 방식 코드명\n" +
                "  , FU01.AMB_AMT --앰뷸런스 금액\n" +
                "FROM\n" +
                "  TBFU1001 FU01 \n" +
                "  LEFT OUTER JOIN TBCM1005 CM05_FUN ON FU01.FUN_CD = CM05_FUN.FUN_CD\n" +
                "  LEFT OUTER JOIN TBCM1005 CM05_AMB ON FU01.AMBU_BIZ_CD = CM05_AMB.FUN_CD\n" +
                "WHERE  \n" +
                "  FU01.FUN_CTRL_NO = :functrlno\n",
        resultClass = FunEntity.class,
        resultSetMapping = "getFuneralInfo1Mapping")

@SqlResultSetMapping(
        name = "getFuneralInfo2Mapping",
        classes = @ConstructorResult(
                targetClass = FunFuneralInfo2.class,
                columns = {
                        @ColumnResult(name = "FUN_CTRL_NO", type = String.class),
                        @ColumnResult(name = "FUN_METH", type = String.class),
                        @ColumnResult(name = "FUN_METH_NM", type = String.class),
                        @ColumnResult(name = "DONATE_CORPSE_YN", type = String.class),
                        @ColumnResult(name = "DONATE_CORPSE_YN_NM", type = String.class),
                        @ColumnResult(name = "BURIAL_METH", type = String.class),
                        @ColumnResult(name = "BURIAL_METH_NM", type = String.class),
                        @ColumnResult(name = "FUN_ABRE", type = String.class),
                        @ColumnResult(name = "FUN_ABRE_NM", type = String.class),
                        @ColumnResult(name = "CREMA_BIZ_CD", type = String.class),
                        @ColumnResult(name = "CREMA_BIZ_NM", type = String.class),
                        @ColumnResult(name = "CREMA_BIZ_TEL_NO", type = String.class),
                        @ColumnResult(name = "CREMA_BIZ_ADDR", type = String.class),
                        @ColumnResult(name = "RESER_DATE", type = String.class),
                        @ColumnResult(name = "RESER_TIME", type = String.class),
                        @ColumnResult(name = "RESER_MIN", type = String.class),
                        @ColumnResult(name = "REV_YN", type = String.class),
                        @ColumnResult(name = "ENSH_METH", type = String.class),
                        @ColumnResult(name = "ENSH_METH_NM", type = String.class),
                        @ColumnResult(name = "CONST_BIZ_CD", type = String.class),
                        @ColumnResult(name = "CONST_BIZ_NM", type = String.class),
                        @ColumnResult(name = "CONST_BIZ_TEL_NO", type = String.class),
                        @ColumnResult(name = "CONST_BIZ_ADDR", type = String.class),
                        @ColumnResult(name = "CEME_ZIP_CODE", type = String.class),
                        @ColumnResult(name = "CEME_ADDR1", type = String.class),
                        @ColumnResult(name = "CEME_ADDR2", type = String.class),
                        @ColumnResult(name = "CEME_TYPE", type = String.class),
                        @ColumnResult(name = "CEME_NM", type = String.class)
                })
)

@NamedNativeQuery(name = "findFunFuneralInfo2",
        query = "SELECT\n" +
                "    FU01.FUN_CTRL_NO\n" +
                "  , FU01.FUN_METH --장법코드\n" +
                "  , (SELECT CD_NM FROM TBCM1012 WHERE TYPE_CD = 'FUNMETH' AND CD = FU01.FUN_METH) AS FUN_METH_NM --장법코드법\n" +
                "  , FU01.DONATE_CORPSE_YN --시신기증유무\n" +
                "  , DECODE(FU01.DONATE_CORPSE_YN, 'Y', '기증', '미기증') AS DONATE_CORPSE_YN_NM  \n" +
                "  , FU01.BURIAL_METH --매장방법코드\n" +
                "  , (SELECT CD_NM FROM TBCM1012 WHERE TYPE_CD = 'BURMETH' AND CD = FU01.BURIAL_METH) AS BURIAL_METH_NM  --매장방법명\n" +
                "  , FU01.FUN_ABRE --횡대사용유무코드\n" +
                "  , DECODE(FU01.FUN_ABRE, 'Y', 'Yes', 'No') AS FUN_ABRE_NM --횡대사용유무\n" +
                "  , FU01.CREMA_BIZ_CD --화장장코드\n" +
                "  , CM05_HWA.FUN_NM AS CREMA_BIZ_NM --화장장명\n" +
                "  --, CM05_HWA.TEL_NO1 || DECODE(CM05_HWA.TEL_NO2, NULL, '', '-' || CM05_HWA.TEL_NO2) || DECODE(CM05_HWA.TEL_NO3, NULL, '', '-' || CM05_HWA.TEL_NO3) AS CREMA_BIZ_TEL_NO --화장장전화번호\n" +
                "  --, REPLACE(CM05_HWA.TEL_NO1 || CM05_HWA.TEL_NO2 || CM05_HWA.TEL_NO3, '-', '') AS CREMA_BIZ_TEL_NO --화장장전화번호\n" +
                "  , CM05_HWA.TEL_NO1 || CM05_HWA.TEL_NO2 || CM05_HWA.TEL_NO3 AS CREMA_BIZ_TEL_NO --화장장전화번호\n" +
                "  , CM05_HWA.ADDR1 || ' ' || CM05_HWA.ADDR2 AS CREMA_BIZ_ADDR --화장장 주소\n" +
                "  , FU01.RESER_DATE --화장장예약일\n" +
                "  , FU01.RESER_TIME --화장장예약시간\n" +
                "  , FU01.RESER_MIN --화장장예약분\n" +
                "  , FU01.REV_YN --예약자\n" +
                "  , FU01.ENSH_METH  --봉안방법코드\n" +
                "  , (SELECT CD_NM FROM TBCM1012 WHERE TYPE_CD = 'ENSHMETH' AND CD = FU01.ENSH_METH) AS ENSH_METH_NM  --봉안방법코드명\n" +
                "  , FU01.CONST_BIZ_CD -- 장지1 코드\n" +
                "  , CM05_JANG.FUN_NM AS CONST_BIZ_NM    -- 장지1명\n" +
                "  , CM05_JANG.TEL_NO AS CONST_BIZ_TEL_NO --장지1 전화번호\n" +
                "  , CM05_JANG.ADDR AS CONST_BIZ_ADDR --장지1 주소\n" +
                "  , FU01.CEME_ZIP_CODE  -- 장지2 우편번호\n" +
                "  , DECODE(FU01.CEME_ADDR1, '', FU01.CEME_ETC, FU01.CEME_ADDR1) AS CEME_ADDR1 -- 장지2 주소1\n" +
                "  , DECODE(FU01.CEME_ADDR2, '', FU01.CEME_ETC, FU01.CEME_ADDR2) AS CEME_ADDR2 -- 장지2 주소2\n" +
                "  , FU01.CEME_TYPE --장지2 선택사항\n" +
                "  , DECODE(FU01.CEME_TYPE, 'C', '선산', 'H', '기타', '')  AS CEME_NM   --장지2 선택사항명\n" +
                "FROM\n" +
                "  TBFU1001 FU01 \n" +
                "  LEFT OUTER JOIN TBCM1005 CM05_FUN ON FU01.FUN_CD = CM05_FUN.FUN_CD\n" +
                "  LEFT OUTER JOIN TBCM1005 CM05_AMB ON FU01.AMBU_BIZ_CD = CM05_AMB.FUN_CD\n" +
                "  LEFT OUTER JOIN TBCM1005 CM05_HWA ON FU01.CREMA_BIZ_CD = CM05_HWA.FUN_CD\n" +
                "  LEFT OUTER JOIN (\n" +
                "                    SELECT \n" +
                "                      FUN_CD\n" +
                "                      , FUN_NM \n" +
                "                      , ZIP_CODE\n" +
                "                      , ADDR1 || ' ' || ADDR2 AS ADDR\n" +
                "                      , TEL_NO1 || '-' || TEL_NO2 || '-' || TEL_NO3 AS TEL_NO\n" +
                "                    FROM TBCM1005\n" +
                "                    WHERE USE_YN ='Y'\n" +
                "                  ) CM05_JANG\n" +
                "                  ON FU01.CONST_BIZ_CD = CM05_JANG.FUN_CD\n" +
                "WHERE  \n" +
                "  FU01.FUN_CTRL_NO = :functrlno\n",
        resultClass = FunEntity.class,
        resultSetMapping = "getFuneralInfo2Mapping")

@SqlResultSetMapping(
        name = "findFunWorkerInfo",
        classes = @ConstructorResult(
                targetClass = FunWorkerInfo.class,
                columns = {
                        @ColumnResult(name = "MANAGE_BRANCH_NM", type = String.class),
                        @ColumnResult(name = "MANAGE_BRANCH_HEAD_NM", type = String.class),
                        @ColumnResult(name = "ORG_BRANCH_NM", type = String.class),
                        @ColumnResult(name = "FUN_STATUS_NM", type = String.class),
                        @ColumnResult(name = "FUN_START_DATE", type = String.class),
                        @ColumnResult(name = "FIRST_HELP_NM", type = String.class),
                        @ColumnResult(name = "CASK_DATE", type = String.class),
                        @ColumnResult(name = "CASKET_SUP_NM", type = String.class),
                        @ColumnResult(name = "CORT_DATE", type = String.class),
                        @ColumnResult(name = "CORT_SUP_NM", type = String.class)
                })
)

@NamedNativeQuery(name = "findFunWorkerInfo",
        query = "  SELECT                                                                                                                                \n" +
                "      MANAGE_BRANCH_NM --진행지부명                                                                                                     \n" +
                "    , MANAGE_BRANCH_HEAD_NM --진행지부 지부장명                                                                                         \n" +
                "    , ORG_BRANCH_NM --관할지부명                                                                                                            \n" +
                "    , FUN_STATUS_NM --의전상태명                                                                                                        \n" +
                "    , FUN_START_DATE --초도지원일                                                                                                       \n" +
                "    , FIRST_HELP_NM --초도지원자명                                                                                                      \n" +
                "    , CASK_DATE --입관지원일                                                                                                            \n" +
                "    , CASKET_SUP_NM --입관지원자명                                                                                                      \n" +
                "    , CORT_DATE --발인지원일                                                                                                            \n" +
                "    , CORT_SUP_NM --발인지원자명                                                                                                        \n" +
                "  FROM (                                                                                                                                \n" +
                "      SELECT                                                                                                                            \n" +
                "          FU01.MANAG_BRANCH AS MANAGE_BRANCH_CD--진행지부코드                                                                           \n" +
                "        , VW_DEPT.DEPT_NAME AS MANAGE_BRANCH_NM --진행지부명                                                                            \n" +
                "        , VW_DEPT.HEAD_EMP_NAME AS MANAGE_BRANCH_HEAD_NM --진행지부 지부장명                                                            \n" +
                "        , FU01.MANAG_BRANCH_GROUP AS REGION_BRANCH_CD --관할지부코드                                                                    \n" +
                "        , VW_DEPT_2.DEPT_NAME AS ORG_BRANCH_NM--관할지부명                                                                                              \n" +
                "        , FU01.STATUS --의전상태코드                                                                                                    \n" +
                "        , FNCM1012('FUNSTATUS', FU01.STATUS) AS FUN_STATUS_NM --의전상태명                                                              \n" +
                "        , FU01.FUN_START_DATE --초도지원일                                                                                              \n" +
                "        , FU01.FIRST_HELP --초도지원자ID                                                                                                \n" +
                "        , FU4004.FIRST_HELP_NM --초도지원자명                                                                                           \n" +
                "        , FU4004.FIRST_HELP_HP_NO --초도지원자 전화번호                                                                                 \n" +
                "        , FU01.CASK_DATE --입관지원일                                                                                                   \n" +
                "        , NVL(FU4004_CA.EMP_NAME, HR01M_CA.EMPNM1) AS CASKET_SUP_NM --입관지원자명                                                      \n" +
                "        , FU4001.CASKET_CHG_ID --입관지원자ID                                                                                           \n" +
                "        , NVL(FU4004_CA.CELL_PHONE_NO, HR01M_CA.TELNO) AS CASKET_TEL_NO --입관지원자 전화번호                                           \n" +
                "                                                                                                                                        \n" +
                "        , FU01.CORT_DATE --발인지원일                                                                                                   \n" +
                "        , NVL(FU4004_CO.EMP_NAME, HR01M_CO.EMPNM1) AS CORT_SUP_NM --발인지원자명                                                        \n" +
                "        , FU4001.CORT_SUP_ID --발인지원자ID                                                                                             \n" +
                "        , NVL(FU4004_CO.CELL_PHONE_NO, HR01M_CO.TELNO) AS CORT_TEL_NO --입관지원자 전화번호                                             \n" +
                "                                                                                                                                        \n" +
                "      FROM                                                                                                                              \n" +
                "        TBFU1001 FU01                                                                                                                   \n" +
                "        LEFT OUTER JOIN TBFU4001 FU4001 ON FU01.FUN_CTRL_NO = FU4001.FUN_CTRL_NO                                                        \n" +
                "        LEFT OUTER JOIN TBFU4004 FU4004_CA ON FU4001.CASKET_CHG_ID = FU4004_CA.EMP_NO                                                   \n" +
                "        LEFT OUTER JOIN HR001M HR01M_CA ON FU4001.CASKET_CHG_ID = HR01M_CA.EMPNO                                                        \n" +
                "        LEFT OUTER JOIN TBFU4004 FU4004_CO ON FU4001.CORT_SUP_ID = FU4004_CO.EMP_NO                                                     \n" +
                "        LEFT OUTER JOIN HR001M HR01M_CO ON FU4001.CORT_SUP_ID = HR01M_CO.EMPNO                                                          \n" +
                "        INNER JOIN VW_BO_DEPT VW_DEPT ON VW_DEPT.DEPT_CODE = FU01.MANAG_BRANCH                                                          \n" +
                "        INNER JOIN VW_BO_DEPT VW_DEPT_2 ON VW_DEPT_2.DEPT_CODE = FU01.MANAG_BRANCH_GROUP                                                \n" +
                "        LEFT OUTER JOIN (                                                                                                               \n" +
                "                            SELECT                                                                                                      \n" +
                "                              FU4004_1.EMP_NO EMP_NO,                                                                                   \n" +
                "                              DECODE( FU4004_2.EMP_NO , NULL, FU4004_1.EMP_NAME, FU4004_2.EMP_NAME ) FIRST_HELP_NM                      \n" +
                "                              , DECODE( FU4004_2.EMP_NO , NULL, FU4004_1.CELL_PHONE_NO, FU4004_2.CELL_PHONE_NO ) FIRST_HELP_HP_NO       \n" +
                "                            FROM                                                                                                        \n" +
                "                              TBFU4004 FU4004_1                                                                                         \n" +
                "                              LEFT OUTER JOIN TBFU4004 FU4004_2 ON FU4004_1.NEW_EMP_NO = FU4004_2.EMP_NO                                \n" +
                "                            WHERE 1=1                                                                                                   \n" +
                "                              AND FU4004_1.EMP_GB IN ('4', '5')                                                                         \n" +
                "                        ) FU4004 ON FU4004.EMP_NO = FU01.FIRST_HELP                                                                     \n" +
                "      WHERE                                                                                                                             \n" +
                "        FU01.FUN_CTRL_NO = :functrlno                                                                                                        \n" +
                "  )                                                                                                                                     \n"
                ,
        resultClass = FunWorkerInfo.class,
        resultSetMapping = "findFunWorkerInfo")


@SqlResultSetMapping(
        name = "FunSmsEmpListMapping",
        classes = @ConstructorResult(
                targetClass = FunSmsEmpList.class,
                columns = {
                        @ColumnResult(name = "EMPNO", type = String.class),
                        @ColumnResult(name = "EMPNM", type = String.class),
                        @ColumnResult(name = "TELNO", type = String.class),
                        @ColumnResult(name = "DEPCD", type = String.class),
                        @ColumnResult(name = "DEPNM", type = String.class)
                })
)

@NamedNativeQuery(name = "getFunStartSmsEmpList",
        query = " SELECT                                                                                                                                              \n " +
                "   TB_1.EMPNO                                                                                                                                        \n " +
                "   , TB_1.EMPNM                                                                                                                                      \n " +
                "   , TB_1.ETCTELNO TELNO                                                                                                                             \n " +
                "   , BM10.DEPCD                                                                                                                                      \n " +
                "   , BM10.DEPNM                                                                                                                                      \n " +
                " FROM (                                                                                                                                              \n " +
                "    SELECT                                                                                                                                            \n " +
                "        EMP_NO AS EMPNO                                                                                                                               \n " +
                "      , EMP_NAME AS EMPNM                                                                                                                             \n " +
                "      ,( REPLACE(CELL_PHONE_NO,'-','') ) AS ETCTELNO                                                                                                  \n " +
                "    FROM                                                                                                                                              \n " +
                "      VW_BO_EMPLOYEE                                                                                                                                  \n " +
                "    WHERE                                                                                                                                             \n " +
                "      STF_CODE <> '97'     -- 도우미제외                                                                                                              \n " +
                "      AND FUN_CODE NOT IN('0024','9999')   -- 운전직제외                                                                                              \n " +
                "      AND REPLACE(CELL_PHONE_NO,'-','') IS NOT NULL                                                                                                   \n " +
                "      AND EMP_NO NOT IN('7130007','1100044')                                                                                                          \n " +
                "      AND EMP_NO = (SELECT LEADER_ID FROM TBFU1001 WHERE FUN_CTRL_NO = :functrlno) --> 의전번호                                                     \n " +
                "                                                                                                                                                      \n " +
                "    UNION                                                                                                                                             \n " +
                "                                                                                                                                                      \n " +
                "    SELECT                                                                                                                                            \n " +
                "      EMP_NO AS EMPNO                                                                                                                                 \n " +
                "      , EMP_NAME AS EMPNM                                                                                                                             \n " +
                "      , ( REPLACE(CELL_PHONE_NO,'-','') ) AS ETCTELNO                                                                                                 \n " +
                "    FROM                                                                                                                                              \n " +
                "      VW_BO_EMPLOYEE                                                                                                                                  \n " +
                "    WHERE                                                                                                                                             \n " +
                "      EMP_NO IN ('1100005','7100024','1110001','1100001')                                                                                             \n " +
                "                                                                                                                                                      \n " +
                "    UNION                                                                                                                                             \n " +
                "                                                                                                                                                      \n " +
                "    SELECT                                                                                                                                            \n " +
                "      EMP_NO AS EMPNO                                                                                                                                 \n " +
                "      , EMP_NAME AS EMPNM                                                                                                                             \n " +
                "      , ( REPLACE(CELL_PHONE_NO,'-','') ) AS ETCTELNO                                                                                                 \n " +
                "    FROM                                                                                                                                              \n " +
                "      VW_BO_EMPLOYEE                                                                                                                                  \n " +
                "    WHERE                                                                                                                                             \n " +
                "      ROL_CODE='27'                                                                                                                                   \n " +
                "      AND STF_CODE='11'                                                                                                                               \n " +
                "      AND DEPT_CODE = (SELECT MANAG_BRANCH FROM TBFU1001 WHERE FUN_CTRL_NO = :functrlno) --> 의전번호                                                 \n " +
                "                                                                                                                                                      \n " +
                "    UNION                                                                                                                                             \n " +
                "                                                                                                                                                      \n " +
                "    SELECT                                                                                                                                            \n " +
                "       EMP_NO AS EMPNO                                                                                                                                \n " +
                "      , EMP_NAME AS EMPNM                                                                                                                             \n " +
                "      , ( REPLACE(CELL_PHONE_NO,'-','') ) AS ETCTELNO                                                                                                 \n " +
                "    FROM                                                                                                                                              \n " +
                "      VW_BO_EMPLOYEE                                                                                                                                  \n " +
                "    WHERE                                                                                                                                             \n " +
                "      EMP_NO IN ('1100044')                                                                                                                           \n " +
                "      AND (SELECT MANAG_BRANCH FROM TBFU1001 WHERE FUN_CTRL_NO = :functrlno) IN ('203000','202000') --> 의전번호                                      \n " +
                "                                                                                                                                                      \n " +
                "    UNION                                                                                                                                             \n " +
                "                                                                                                                                                      \n " +
                "    SELECT                                                                                                                                            \n " +
                "      EMP_NO AS EMPNO                                                                                                                                 \n " +
                "      , EMP_NAME AS EMPNM                                                                                                                             \n " +
                "      , ( REPLACE(CELL_PHONE_NO,'-','') ) AS ETCTELNO                                                                                                 \n " +
                "    FROM                                                                                                                                              \n " +
                "      VW_BO_EMPLOYEE                                                                                                                                  \n " +
                "    WHERE                                                                                                                                             \n " +
                "      EMP_NO = (CASE WHEN (SELECT MANAG_BRANCH FROM TBFU1001 WHERE FUN_CTRL_NO = :functrlno) = '203000' THEN '1100029' ELSE '' END) --> 의전번호      \n " +
                "                                                                                                                                                      \n " +
                "    UNION                                                                                                                                             \n " +
                "                                                                                                                                                      \n " +
                "    SELECT                                                                                                                                            \n " +
                "      A.EMP_NO AS EMPNO                                                                                                                               \n " +
                "      , A.EMP_NAME AS EMPNM                                                                                                                           \n " +
                "      , A.CELL_PHONE_NO AS EMPTEL                                                                                                                     \n " +
                "    FROM                                                                                                                                              \n " +
                "      TBFU3100 A                                                                                                                                      \n " +
                "    WHERE                                                                                                                                             \n " +
                "      A.DUTY = '현장대리인'                                                                                                                           \n " +
                "      AND RETIRE_DATE IS NULL                                                                                                                         \n " +
                "                                                                                                                                                      \n " +
                "    UNION                                                                                                                                             \n " +
                "                                                                                                                                                      \n " +
                "    SELECT                                                                                                                                            \n " +
                "      EMP_NO AS EMPNO                                                                                                                                 \n " +
                "      , EMP_NAME AS EMPNM                                                                                                                             \n " +
                "      , ( REPLACE(CELL_PHONE_NO,'-','') ) AS ETCTELNO                                                                                                 \n " +
                "    FROM                                                                                                                                              \n " +
                "      VW_BO_EMPLOYEE                                                                                                                                  \n " +
                "    WHERE                                                                                                                                             \n " +
                "      STF_CODE <> '97'     -- 도우미제외                                                                                                              \n " +
                "      AND FUN_CODE NOT IN('0024','9999')   -- 운전직제외                                                                                              \n " +
                "      AND EMP_NO IN (                                                                                                                                 \n " +
                "                      SELECT                                                                                                                          \n " +
                "                        ADMIN_EMPNO                                                                                                                   \n " +
                "                      FROM HB001M                                                                                                                     \n " +
                "                      WHERE DEPT = (SELECT MANAG_BRANCH FROM TBFU1001 WHERE FUN_CTRL_NO = :functrlno) --> 의전번호                                    \n " +
                "                   )                                                                                                                                  \n " +
                "  ) TB_1                                                                                                                                              \n " +
                "  INNER JOIN TBBM1011 BM11 ON TB_1.EMPNO = BM11.EMPNO AND BM11.USE_YN = '0' AND BM11.DEL_YN = '0'                                                     \n " +
                "  INNER JOIN TBBM1010 BM10 ON BM11.ORGDEPCD = BM10.DEPCD AND BM10.USE_YN = '0' AND BM10.DEL_YN = '0'                                                  \n" +
                "                                                                                                                                                      \n " +
                "    UNION ALL                                                                                                                                         \n " +
                "                                                                                                                                                      \n " +
                "  SELECT                                                                                                                                              \n " +
                "    '0000000' AS EMPNO                                                                                                                                \n " +
                "    ,'상황실공용폰' AS EMPNM                                                                                                                          \n " +
                "    ,'01037736650' AS TELNO                                                                                                                           \n " +
                "    , '210001' AS DEPCD                                                                                                                               \n " +
                "    , (SELECT DEPNM FROM TBBM1010 WHERE USE_YN = '0' AND DEL_YN = '0' AND DEPCD = '210001') AS DEPNM                                                  \n " +
                "    FROM DUAL                                                                                                                                         \n " +
                "    ORDER BY EMPNM                                                                                                                                    \n "
        ,
        resultClass = FunEntity.class,
        resultSetMapping = "FunSmsEmpListMapping")

@NamedNativeQuery(name = "getFunEndSmsEmpList",
        query = " SELECT                                                                                                                                            \n" +
                "   TB_1.EMPNO                                                                                                                                      \n" +
                "   , TB_1.EMPNM                                                                                                                                    \n" +
                "   , TB_1.ETCTELNO TELNO                                                                                                                           \n" +
                "   , BM10.DEPCD                                                                                                                                    \n" +
                "   , BM10.DEPNM                                                                                                                                    \n" +
                "	FROM (                                                                                                                                            \n" +
                "   SELECT                                                                                                                                          \n" +
                "     EMP_NO AS EMPNO                                                                                                                               \n" +
                "	    , EMP_NAME AS EMPNM                                                                                                                           \n" +
                "	    , ( REPLACE(CELL_PHONE_NO,'-','') ) AS ETCTELNO                                                                                               \n" +
                "	  FROM                                                                                                                                            \n" +
                "	    VW_BO_EMPLOYEE                                                                                                                                \n" +
                "    WHERE                                                                                                                                          \n" +
                "      STF_CODE <> '97'     -- 도우미제외                                                                                                           \n" +
                "      AND FUN_CODE NOT IN('0024','9999')   -- 운전직제외                                                                                           \n" +
                "      AND REPLACE(CELL_PHONE_NO,'-','') IS NOT NULL                                                                                                \n" +
                "      AND EMP_NO NOT IN ('7130007','1100044')                                                                                                      \n" +
                "      AND EMP_NO = (SELECT LEADER_ID FROM TBFU1001 WHERE FUN_CTRL_NO = :functrlno) --> 의전번호                                                  \n" +
                "                                                                                                                                                   \n" +
                "    UNION                                                                                                                                          \n" +
                "                                                                                                                                                   \n" +
                "    SELECT                                                                                                                                         \n" +
                "      EMP_NO AS EMPNO                                                                                                                              \n" +
                "      , EMP_NAME AS EMPNM                                                                                                                          \n" +
                "      , ( REPLACE(CELL_PHONE_NO,'-','') ) AS ETCTELNO                                                                                              \n" +
                "    FROM                                                                                                                                           \n" +
                "      VW_BO_EMPLOYEE                                                                                                                               \n" +
                "    WHERE                                                                                                                                          \n" +
                "      EMP_NO IN ('1100005','1100001','1110001')                                                                                                    \n" +
                "                                                                                                                                                   \n" +
                "    UNION                                                                                                                                          \n" +
                "                                                                                                                                                   \n" +
                "    SELECT                                                                                                                                         \n" +
                "      EMP_NO AS EMPNO                                                                                                                              \n" +
                "      , EMP_NAME AS EMPNM                                                                                                                          \n" +
                "      , ( REPLACE(CELL_PHONE_NO,'-','') ) AS ETCTELNO                                                                                              \n" +
                "    FROM                                                                                                                                           \n" +
                "      VW_BO_EMPLOYEE                                                                                                                               \n" +
                "    WHERE                                                                                                                                          \n" +
                "      EMP_NO IN ('1100044')                                                                                                                        \n" +
                "      AND (SELECT MANAG_BRANCH FROM TBFU1001 WHERE FUN_CTRL_NO = :functrlno) IN ('203000','202000')--> 의전번호                                  \n" +
                "                                                                                                                                                   \n" +
                "    UNION                                                                                                                                          \n" +
                "                                                                                                                                                   \n" +
                "    SELECT                                                                                                                                         \n" +
                "      EMP_NO AS EMPNO                                                                                                                              \n" +
                "      , EMP_NAME AS EMPNM                                                                                                                          \n" +
                "      , ( REPLACE(CELL_PHONE_NO,'-','') ) AS ETCTELNO                                                                                              \n" +
                "    FROM                                                                                                                                           \n" +
                "      VW_BO_EMPLOYEE                                                                                                                               \n" +
                "    WHERE                                                                                                                                          \n" +
                "      EMP_NO = (CASE WHEN (SELECT MANAG_BRANCH FROM TBFU1001 WHERE FUN_CTRL_NO = :functrlno) = '203000' THEN '1100029' ELSE '' END) --> 의전번호 \n" +
                "                                                                                                                                                   \n" +
                "    UNION                                                                                                                                          \n" +
                "                                                                                                                                                   \n" +
                "    SELECT                                                                                                                                         \n" +
                "      EMP_NO AS EMPNO                                                                                                                              \n" +
                "      , EMP_NAME AS EMPNM                                                                                                                          \n" +
                "      , ( REPLACE(CELL_PHONE_NO,'-','') ) AS ETCTELNO                                                                                              \n" +
                "    FROM                                                                                                                                           \n" +
                "      VW_BO_EMPLOYEE                                                                                                                               \n" +
                "    WHERE                                                                                                                                          \n" +
                "      STF_CODE <> '97'     -- 도우미제외                                                                                                           \n" +
                "      AND FUN_CODE NOT IN('0024','9999')   -- 운전직제외                                                                                           \n" +
                "      AND EMP_NO IN (                                                                                                                              \n" +
                "                      SELECT                                                                                                                       \n" +
                "                        ADMIN_EMPNO                                                                                                                \n" +
                "                      FROM                                                                                                                         \n" +
                "                        HB001M                                                                                                                     \n" +
                "                      WHERE DEPT = (SELECT MANAG_BRANCH FROM TBFU1001 WHERE FUN_CTRL_NO = :functrlno) --> 의전번호                               \n" +
                "                   )                                                                                                                               \n" +
                "  ) TB_1                                                                                                                                           \n" +
                "  INNER JOIN TBBM1011 BM11 ON TB_1.EMPNO = BM11.EMPNO AND BM11.USE_YN = '0' AND BM11.DEL_YN = '0'                                                  \n" +
                "  INNER JOIN TBBM1010 BM10 ON BM11.ORGDEPCD = BM10.DEPCD AND BM10.USE_YN = '0' AND BM10.DEL_YN = '0'                                               \n" +
                "                                                                                                                                                   \n" +
                "  UNION ALL                                                                                                                                        \n" +
                "                                                                                                                                                   \n" +
                "  SELECT                                                                                                                                           \n" +
                "    '0000000' AS EMPNO                                                                                                                             \n" +
                "    ,'상황실공용폰' AS EMPNM                                                                                                                       \n" +
                "    ,'01037736650' AS TELNO                                                                                                                        \n" +
                "    , '210001' AS DEPCD                                                                                                                            \n" +
                "    , (SELECT DEPNM FROM TBBM1010 WHERE USE_YN = '0' AND DEL_YN = '0' AND DEPCD = '210001') AS DEPNM                                               \n" +
                "  FROM DUAL                                                                                                                                        \n" +
                "  ORDER BY EMPNM                                                                                                                                   \n"
        ,
        resultClass = FunEntity.class,
        resultSetMapping = "FunSmsEmpListMapping")



@Data
@DynamicUpdate
@Entity
@Table(name="TBFU1001", schema = "tk_fsdev")
public class FunEntity {
    @Id
    private String funCtrlNo;
    private String certNo;
    private String sosok;
    private String status;
    private String preYn;
    private String funStartDate;
    private String funStartTime;
    private String funStartMin;
    private String funEndDate;
    private String funEndTime;
    private String funEndMin;
    private String funDays;
    private String managBranch;
    private String presId;
    private String leaderId;
    private String managId;
    private String firstFd;
    private String chgId;
    private String mourner;
    private String deadNm;
    private String sex;
    private String age;
    private String reat;
    private String clan;
    private String deadRea;
    private String religion;
    private String baptismNm;
    private String chuDuty;
    private String deadHeig;
    private String deadWeig;
    private String casketSize;
    private String bereNum1;
    private String bereNum2;
    private String bereNum3;
    private String bereNum4;
    private String bereNum5;
    private String bereNum6;
    private String bereNum7;
    private String bereNum8;
    private String funCd;
    private String funResGb;
    private String ambuBizCd;
    private String ambuUseYn;
    private String ambReasonCode;
    private String ambuCostUseYn;
    private Integer ambAmt;
    private String ambAmtType;
    private String funMeth;
    private String burialMeth;
    private String funAbre;
    private String cremaBizCd;
    private String reserDate;
    private String reserTime;
    private String reserMin;
    private String revYn;
    private String enshMeth;
    private String constBizCd;
    private String cemeZipCode;
    private String cemeAddr1;
    private String cemeAddr2;
    private String cemeType;
    private String arrvDate;
    private String arrvTime;
    private String arrvMin;
    private String deadDate;
    private String deadTime;
    private String deadMin;
    private String caskDate;
    private String caskTime;
    private String caskMin;
    private String cortDate;
    private String cortTime;
    private String cortMin;
    private String limUseYn;
    private String busUseYn;
    private String prodArrvDate;
    private String prodArrvTime;
    private String prodArrvMin;
    @Column(name = "CORT_SUP_YN",columnDefinition = "CHAR(1)")
    private String cortSupYn;
    @Column(name = "CORT_SUP_N_CD")
    private String cortSupNcd;
}
