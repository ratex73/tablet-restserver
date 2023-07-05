package kr.co.yedaham.tablet.restserver.restserver.entity;

import kr.co.yedaham.tablet.restserver.restserver.model.product.ContractDetailList;
import kr.co.yedaham.tablet.restserver.restserver.model.product.ContractDusanList;
import kr.co.yedaham.tablet.restserver.restserver.model.product.ContractList;
import lombok.Data;

import javax.persistence.*;

@SqlResultSetMapping(
        name = "findContractDetailListMapping",
        classes = @ConstructorResult(
                targetClass = ContractDetailList.class,
                columns = {
                        @ColumnResult(name = "certno", type = String.class),
                        @ColumnResult(name = "state", type = String.class),
                        @ColumnResult(name = "stateNm", type = String.class),
                        @ColumnResult(name = "prodNm", type = String.class),
                        @ColumnResult(name = "contDate", type = String.class),
                        @ColumnResult(name = "funStartDate", type = String.class),
                        @ColumnResult(name = "funRegDate", type = String.class)
                })
)




@NamedNativeQuery(name = "findContractDetailList",
        query = " SELECT \n" +
                "NB07.CERT_NO as certNo, \n" +
                "NB07.STATE as state, \n" +
                "FNCM1012('STATE', NB07.STATE) stateNm , \n" +
                "(SELECT S_PROD_DNM FROM TBPR1001 WHERE PROD_MAIN_CD = NB07.PROD_MAIN_CD) prodNm, \n" +
                "TO_CHAR(TO_DATE(NB07.CONT_DATE, 'YYYYMMDD'),'YYYY-MM-DD') as contDate, \n" +
                "TO_CHAR(TO_DATE(FU01.FUN_START_DATE, 'YYYYMMDD'),'YYYY-MM-DD') as funStartDate, \n" +
                "TO_CHAR(FU01.REG_DATE, 'YYYYMMDD') funRegDate \n" +
                "FROM TBNB1007 NB07 \n" +
                "LEFT OUTER JOIN TBFU1001 FU01 ON NB07.CERT_NO = FU01.CERT_NO \n" +
                "WHERE NB07.CUST_ID = (SELECT CUST_ID FROM TBNB1007 WHERE CERT_NO = :certNo) \n" +
                "AND NB07.CERT_NO <> :certNo \n" +
                "AND NB07.STATE NOT IN('1','0','7','3','4') \n",
        resultClass = ProductEntity.class,
        resultSetMapping = "findContractDetailListMapping")




@SqlResultSetMapping(
        name = "ProductYedahamOneTwoListMapping",
        classes = @ConstructorResult(
                targetClass = ContractList.class,
                columns = {
                        @ColumnResult(name = "certno", type = String.class),
                        @ColumnResult(name = "refalph", type = String.class),
                        @ColumnResult(name = "refnum", type = String.class),
                        @ColumnResult(name = "cd", type = String.class),
                        @ColumnResult(name = "cdnm", type = String.class),
                        @ColumnResult(name = "plinm", type = String.class),
                        @ColumnResult(name = "commt", type = String.class),
                        @ColumnResult(name = "qty", type = String.class),
                        @ColumnResult(name = "amt", type = String.class),
                        @ColumnResult(name = "payback", type = String.class),
                        @ColumnResult(name = "main_gb", type = String.class),
                        @ColumnResult(name = "init", type = String.class),
                        @ColumnResult(name = "upsellyn", type = String.class),
                        @ColumnResult(name = "ASSI_PROD_CD", type = String.class),
                        @ColumnResult(name = "STATE", type = String.class),
                        @ColumnResult(name = "CREAT_YN", type = String.class),
                        @ColumnResult(name = "FU04_QTY", type = String.class),
                        @ColumnResult(name = "FU04_AMT", type = String.class)
                })
)

/*
@NamedNativeQuery(name = "findProductYedahamOneTwoList",
        query = " SELECT \n" +
                ":certno AS CERTNO,\n" +
                "REF_ALPH AS refalph,\n" +
                "REF_NUM AS refnum,\n" +
                "CD AS cd,\n" +
                "CD_NM AS cdnm,\n" +
                "NVL(replace(PLI_NM, '/', ''),' ') AS plinm,\n" +
                "COMMT,\n" +
                "QTY,\n" +
                "NVL(AMT, '0') AS AMT,\n" +
                "NVL(PAYBACK, '0') AS PAYBACK,\n" +
                "'' AS MAIN_GB,\n" +
                "INIT,\n" +
                "UPSELLYN,\n" +
                "ASSI_PROD_CD\n" +
                "\n" +
                "FROM ( \n" +
                "SELECT \n" +
                "A.REF_ALPH,\n" +
                "A.REF_NUM,\n" +
                "NVL(B.PLI_NM,' ')PLI_NM,\n" +
                "A.CD_NM,\n" +
                "A.CD,\n" +
                "B.COMMT,\n" +
                "B.QTY,\n" +
                "B.AMT,\n" +
                "B.PAYBACK,\n" +
                "'' AS INIT,\n" +
                "(CASE WHEN A.REMARK = '2' AND B.PLI_NM IS NULL THEN '4' ELSE A.REMARK END) AS UPSELLYN,\n" +
                "RANK() OVER(partition by REF_NUM order by QTY ASC ) AS RNK,\n" +
                "B.ASSI_PROD_CD \n" +
                "FROM TBCM1012 A \n" +
                "LEFT JOIN (\n" +
                "SELECT B.ASSI_PROD_CD, B.ASSI_PROD_NM AS PLI_NM,'' AS COMMT, '1' AS QTY, B.AMT, B.AMT AS PAYBACK, SUBSTR(TABLET_PLI_GCD,3,2) AS PRODGB  FROM TBNB1008 A LEFT JOIN TBPR1002 B ON A.ASSI_PROD_CD=B.ASSI_PROD_CD\n" +
                "WHERE A.CERT_NO=:certno\n" +
                "AND B.USE_YN       = 'Y'\n" +
                "UNION ALL\n" +
                "SELECT ASSI_PROD_CD, ASSI_PROD_NM AS PLI_NM,'' AS COMMT, '1' AS QTY, AMT, AMT AS PAYBACK, SUBSTR(TABLET_PLI_GCD,3,2) AS PRODGB FROM TBPR1002\n" +
                "WHERE GUBUN = (SELECT DISTINCT CASE WHEN ASSI_PROD_CD LIKE '101%' THEN '1' \n" +
                "                                    WHEN ASSI_PROD_CD LIKE '102%' THEN '2' \n" +
                "                                    WHEN ASSI_PROD_CD LIKE '106%' THEN '3' \n" +
                "                                    WHEN ASSI_PROD_CD LIKE '107%' THEN '4' \n" +
                "                                END GUBUN \n" +
                "                FROM TBNB1008 WHERE CERT_NO = :certno))\n" +
                "B ON A.CD = B.PRODGB\n" +
                "WHERE 1=1\n" +
                "AND TYPE_CD='TABLET_CODE'\n" +
                "AND USE_YN='Y'\n" +
                "ORDER BY A.SEQ\n" +
                "\n" +
                " ) WHERE 1=1\n" +
                " AND RNK='1'\n" +
                "GROUP BY \n" +
                "REF_ALPH,\n" +
                "REF_NUM,\n" +
                "PLI_NM,\n" +
                "CD_NM,\n" +
                "CD,\n" +
                "COMMT,\n" +
                "QTY,\n" +
                "AMT,\n" +
                "PAYBACK,\n" +
                "INIT,\n" +
                "UPSELLYN,\n" +
                "ASSI_PROD_CD\n" +
                "UNION ALL\n" +
                "\n" +
                "\n" +
                "SELECT \n" +
                ":certno AS CERTNO,\n" +
                "      '만기케어' AS refalph \n" +
                "    , 99 AS refnum\n" +
                "    ,'' AS CD\n" +
                "    ,'' AS CD_NM\n" +
                "    , C.CARE_ITEM_NM AS PLI_NM\n" +
                "    , BENEFIT_CONTENTS AS COMMT\n" +
                "    , '1' AS QTY\n" +
                "    , (CASE WHEN  C.CARE_ITEM_CD ='FC04' THEN BENEFIT_VALUE ELSE 0 END) AS AMT\n" +
                "    , (CASE WHEN  C.CARE_ITEM_CD ='FC04' THEN BENEFIT_VALUE ELSE 0 END) AS PAYBACK\n" +
                "    , '99' AS MAIN_GB\n" +
                "    , '' AS INIT\n" +
                "    , '4' AS UPSELLYN\n" +
                "    , '' AS ASSI_PROD_CD\n" +
                "FROM (\n" +
                "        SELECT  AA.FUN_CTRL_NO -- 의전번호\n" +
                "              , TO_CHAR(AA.REG_DATE, 'YYYYMMDD') AS F_REG_DATE --접수일\n" +
                "              , AA.CERT_NO -- 증서번호\n" +
                "              , TRUNC(BB.DIS_APP_AMT*0.03, -3) AS AMT -- 가입금액 3%   \n" +
                "              , BB.DIS_APP_AMT AS TOT_PYAMT -- 가입금액\n" +
                "              , CC.SUM_PYAMT AS  SUM_PYAMT  -- 납입금액\n" +
                "              , BB.SUBS_DATE AS SUBS_DATE    --만기케어 날짜체크\n" +
                "        FROM TBFU1001 AA\n" +
                "        JOIN TBNB1007 BB ON BB.CERT_NO=AA.CERT_NO AND BB.STATE = '5'\n" +
                "        LEFT JOIN(\n" +
                "            SELECT                                                                                                                                                  \n" +
                "                TBBC1002.CERT_NO\n" +
                "               ,SUM(TBBC1002.PYMT_AMT) AS SUM_PYAMT\n" +
                "              FROM TBBC1002                                                                                                                                                                              \n" +
                "              WHERE PYMT_GB IN ('1', '2', '3', '6', '9')\n" +
                "             AND CERT_NO = :certno -- 증서번호\n" +
                "              GROUP BY TBBC1002.CERT_NO\n" +
                "          ) CC ON CC.CERT_NO=AA.CERT_NO\n" +
                "        WHERE 1=1\n" +
                "         AND NOT EXISTS ( --일시납 1년 경과 안된 건 제외\n" +
                "                         SELECT \n" +
                "                           NB07.CERT_NO \n" +
                "                         FROM \n" +
                "                           TBNB1007 NB07 \n" +
                "                           INNER JOIN (\n" +
                "                                      SELECT  \n" +
                "                                        MAX(CERT_NO) CERT_NO\n" +
                "                                        , MIN(PYMT_DATE) PYMT_DATE \n" +
                "                                      FROM TBBC1002 \n" +
                "                                      WHERE CERT_NO = :certno  -- 증서번호\n" +
                "                                      AND PYMT_GB IN ('1', '2', '3', '6', '9')\n" +
                "                                     ) BC02\n" +
                "                                 ON NB07.CERT_NO = BC02.CERT_NO\n" +
                "                           LEFT OUTER JOIN ( --계약변경 월납에서 일시납으로 변경한 날 체크\n" +
                "                                             SELECT \n" +
                "                                               MAX(CERT_NO) CERT_NO\n" +
                "                                               , MAX(RECP_DT) RECP_DT\n" +
                "                                             FROM TBNB1027\n" +
                "                                             WHERE \n" +
                "                                               ALT_DCD = '1' AND PYMT_CYCLE = '00'\n" +
                "                                               AND CERT_NO = :certno -- 증서번호\n" +
                "                                            ) NB27 ON NB07.CERT_NO = NB27.CERT_NO\n" +
                "                         WHERE \n" +
                "                           NB07.PYMT_CYCLE = '00' \n" +
                "                           AND NB07.STATE = '5' \n" +
                "                           AND (TO_DATE(NVL(NB27.RECP_DT, BC02.PYMT_DATE) || '000000', 'YYYYMMDDHH24MISS') + 366) > SYSDATE\n" +
                "                           AND NB07.CERT_NO = AA.CERT_NO\n" +
                "                       )\n" +
                "         AND AA.FUN_CTRL_NO =  (SELECT FUN_CTRL_NO FROM TBFU1001 WHERE CERT_NO=:certno AND FUN_CTRL_NO = :functrlno) -- 증서번호\n" +
                "         AND CC.SUM_PYAMT >= BB.DIS_APP_AMT\n" +
                ") AA\n" +
                "INNER JOIN TBFU4446 C ON C.USE_YN = 'Y' AND C.DEL_YN = 'N' AND (AA.AMT BETWEEN C.FROM_SECTION AND C.TO_SECTION ) AND (AA.F_REG_DATE BETWEEN C.START_DATE AND C.END_DATE )\n" +
                "AND AA.SUBS_DATE BETWEEN C.SUBS_START_DATE AND C.SUBS_END_DATE\n" +
                "LEFT JOIN TBFU4447 D ON AA.FUN_CTRL_NO = D.FUN_CTRL_NO AND C.C_SEQ = D.C_SEQ AND C.CARE_ITEM_CD = D.CARE_ITEM_CD AND D.USE_YN = 'Y' AND D.DEL_YN = 'N'\n" +
                " \n" +
                "ORDER BY REFNUM ASC  ",
        resultClass = ProductEntity.class,
        resultSetMapping = "ProductYedahamOneTwoListMapping")
*/

@NamedNativeQuery(name = "findProductYedahamOneTwoList",
        query = " SELECT \n" +
                "CERT_NO AS CERTNO,\n" +
                "REF_ALPH AS refalph,\n" +
                "REF_NUM AS refnum,\n" +
                "CD AS cd,\n" +
                "CD_NM AS cdnm,\n" +
                "NVL(replace(PLI_NM, '/', ''),' ') AS plinm,\n" +
                "COMMT,\n" +
                "QTY,\n" +
                "NVL(AMT, '0') AS AMT,\n" +
                "NVL(PAYBACK, '0') AS PAYBACK,\n" +
                "'' AS MAIN_GB,\n" +
                "INIT,\n" +
                "UPSELLYN,\n" +
                "ASSI_PROD_CD,\n" +
                "STATE,\n" +
                "CREAT_YN,\n" +
                "FU04_QTY,\n" +
                "FU04_AMT\n" +
                "FROM ( \n" +
                "SELECT \n" +
                ":certno AS CERT_NO,\n" +
                "A.REF_ALPH,\n" +
                "A.REF_NUM,\n" +
                "NVL(B.PLI_NM,' ')PLI_NM,\n" +
                "A.CD_NM,\n" +
                "A.CD,\n" +
                "B.COMMT,\n" +
                "B.QTY,\n" +
                "B.AMT,\n" +
                "B.PAYBACK,\n" +
                "'' AS INIT,\n" +
                "(CASE WHEN A.REMARK = '2' AND B.PLI_NM IS NULL THEN '4' ELSE A.REMARK END) AS UPSELLYN,\n" +
                "RANK() OVER(partition by A.REF_NUM order by B.QTY ASC ) AS RNK,\n" +
                "B.ASSI_PROD_CD, \n" +
                "FU04.STATE, \n" +
                "FU04.CREAT_YN, \n" +
                "FU04.QTY AS FU04_QTY, \n" +
                "FU04.AMT AS FU04_AMT \n" +
                "FROM TBCM1012 A \n" +
                "LEFT JOIN (\n" +
                "SELECT A.CERT_NO, B.ASSI_PROD_CD, B.ASSI_PROD_NM AS PLI_NM,'' AS COMMT, '1' AS QTY, B.AMT, B.AMT AS PAYBACK, SUBSTR(TABLET_PLI_GCD,3,2) AS PRODGB  FROM TBNB1008 A LEFT JOIN TBPR1002 B ON A.ASSI_PROD_CD=B.ASSI_PROD_CD\n" +
                "WHERE A.CERT_NO=:certno\n" +
                "AND B.USE_YN       = 'Y'\n" +
                "UNION ALL\n" +
                "SELECT :certno, ASSI_PROD_CD, ASSI_PROD_NM AS PLI_NM,'' AS COMMT, '1' AS QTY, AMT, AMT AS PAYBACK, SUBSTR(TABLET_PLI_GCD,3,2) AS PRODGB FROM TBPR1002\n" +
                "WHERE GUBUN = (SELECT DISTINCT CASE WHEN ASSI_PROD_CD LIKE '101%' THEN '1' \n" +
                "                                    WHEN ASSI_PROD_CD LIKE '102%' THEN '2' \n" +
                "                                    WHEN ASSI_PROD_CD LIKE '106%' THEN '3' \n" +
                "                                    WHEN ASSI_PROD_CD LIKE '107%' THEN '4' \n" +
                "                                END GUBUN \n" +
                "                FROM TBNB1008 WHERE CERT_NO = :certno))\n" +
                "B ON A.CD = B.PRODGB\n" +
                "LEFT OUTER JOIN TBFU1001 FU01 ON FU01.CERT_NO = B.CERT_NO AND FU01.STATUS <> '4'\n" +
                "LEFT OUTER JOIN TBFU1004 FU04 ON FU04.FUN_CTRL_NO = FU01.FUN_CTRL_NO AND FU04.ASSI_PROD_CD = B.ASSI_PROD_CD\n" +
                "WHERE 1=1\n" +
                "AND TYPE_CD='TABLET_CODE'\n" +
                "AND USE_YN='Y'\n" +
                "ORDER BY A.SEQ\n" +
                "\n" +
                " ) WHERE 1=1\n" +
                " AND RNK='1'\n" +
                "GROUP BY \n" +
                "CERT_NO,\n" +
                "REF_ALPH,\n" +
                "REF_NUM,\n" +
                "PLI_NM,\n" +
                "CD_NM,\n" +
                "CD,\n" +
                "COMMT,\n" +
                "QTY,\n" +
                "AMT,\n" +
                "PAYBACK,\n" +
                "INIT,\n" +
                "UPSELLYN,\n" +
                "ASSI_PROD_CD,\n" +
                "STATE,\n" +
                "CREAT_YN,\n" +
                "FU04_QTY,\n" +
                "FU04_AMT\n" +
                "\n" +
                "UNION ALL\n" +
                "\n" +
                "\n" +
                "SELECT \n" +
                ":certno AS CERTNO,\n" +
                "      '만기케어' AS refalph \n" +
                "    , 99 AS refnum\n" +
                "    ,'' AS CD\n" +
                "    ,'' AS CD_NM\n" +
                "    , C.CARE_ITEM_NM AS PLI_NM\n" +
                "    , BENEFIT_CONTENTS AS COMMT\n" +
                "    , '1' AS QTY\n" +
                "    , (CASE WHEN  C.CARE_ITEM_CD ='FC04' THEN BENEFIT_VALUE ELSE 0 END) AS AMT\n" +
                "    , (CASE WHEN  C.CARE_ITEM_CD ='FC04' THEN BENEFIT_VALUE ELSE 0 END) AS PAYBACK\n" +
                "    , '99' AS MAIN_GB\n" +
                "    , '' AS INIT\n" +
                "    , '4' AS UPSELLYN\n" +
                "    , '' AS ASSI_PROD_CD\n" +
                "    , '' STATE\n" +
                "    , '' CREAT_YN\n" +
                "    , 0 FU04_QTY\n" +
                "    , 0 FU04_AMT\n" +
                "FROM (\n" +
                "        SELECT  AA.FUN_CTRL_NO -- 의전번호\n" +
                "              , TO_CHAR(AA.REG_DATE, 'YYYYMMDD') AS F_REG_DATE --접수일\n" +
                "              , AA.CERT_NO -- 증서번호\n" +
                "              , TRUNC(BB.DIS_APP_AMT*0.03, -3) AS AMT -- 가입금액 3%   \n" +
                "              , BB.DIS_APP_AMT AS TOT_PYAMT -- 가입금액\n" +
                "              , CC.SUM_PYAMT AS  SUM_PYAMT  -- 납입금액\n" +
                "              , BB.SUBS_DATE AS SUBS_DATE    --만기케어 날짜체크\n" +
                "        FROM TBFU1001 AA\n" +
                "        JOIN TBNB1007 BB ON BB.CERT_NO=AA.CERT_NO AND BB.STATE = '5'\n" +
                "        LEFT JOIN(\n" +
                "            SELECT                                                                                                                                                  \n" +
                "                TBBC1002.CERT_NO\n" +
                "               ,SUM(TBBC1002.PYMT_AMT) AS SUM_PYAMT\n" +
                "              FROM TBBC1002                                                                                                                                                                              \n" +
                "              WHERE PYMT_GB IN ('1', '2', '3', '6', '9')\n" +
                "             AND CERT_NO = :certno -- 증서번호\n" +
                "              GROUP BY TBBC1002.CERT_NO\n" +
                "          ) CC ON CC.CERT_NO=AA.CERT_NO\n" +
                "        WHERE 1=1\n" +
                "         AND NOT EXISTS ( --일시납 1년 경과 안된 건 제외\n" +
                "                         SELECT \n" +
                "                           NB07.CERT_NO \n" +
                "                         FROM \n" +
                "                           TBNB1007 NB07 \n" +
                "                           INNER JOIN (\n" +
                "                                      SELECT  \n" +
                "                                        MAX(CERT_NO) CERT_NO\n" +
                "                                        , MIN(PYMT_DATE) PYMT_DATE \n" +
                "                                      FROM TBBC1002 \n" +
                "                                      WHERE CERT_NO = :certno  -- 증서번호\n" +
                "                                      AND PYMT_GB IN ('1', '2', '3', '6', '9')\n" +
                "                                     ) BC02\n" +
                "                                 ON NB07.CERT_NO = BC02.CERT_NO\n" +
                "                           LEFT OUTER JOIN ( --계약변경 월납에서 일시납으로 변경한 날 체크\n" +
                "                                             SELECT \n" +
                "                                               MAX(CERT_NO) CERT_NO\n" +
                "                                               , MAX(RECP_DT) RECP_DT\n" +
                "                                             FROM TBNB1027\n" +
                "                                             WHERE \n" +
                "                                               ALT_DCD = '1' AND PYMT_CYCLE = '00'\n" +
                "                                               AND CERT_NO = :certno -- 증서번호\n" +
                "                                            ) NB27 ON NB07.CERT_NO = NB27.CERT_NO\n" +
                "                         WHERE \n" +
                "                           NB07.PYMT_CYCLE = '00' \n" +
                "                           AND NB07.STATE = '5' \n" +
                "                           AND (TO_DATE(NVL(NB27.RECP_DT, BC02.PYMT_DATE) || '000000', 'YYYYMMDDHH24MISS') + 366) > SYSDATE\n" +
                "                           AND NB07.CERT_NO = AA.CERT_NO\n" +
                "                       )\n" +
                "         AND AA.FUN_CTRL_NO =  (SELECT FUN_CTRL_NO FROM TBFU1001 WHERE CERT_NO=:certno AND FUN_CTRL_NO = :functrlno) -- 증서번호\n" +
                "         AND CC.SUM_PYAMT >= BB.DIS_APP_AMT\n" +
                ") AA\n" +
                "INNER JOIN TBFU4446 C ON C.USE_YN = 'Y' AND C.DEL_YN = 'N' AND (AA.AMT BETWEEN C.FROM_SECTION AND C.TO_SECTION ) AND (AA.F_REG_DATE BETWEEN C.START_DATE AND C.END_DATE )\n" +
                "AND AA.SUBS_DATE BETWEEN C.SUBS_START_DATE AND C.SUBS_END_DATE\n" +
                "LEFT JOIN TBFU4447 D ON AA.FUN_CTRL_NO = D.FUN_CTRL_NO AND C.C_SEQ = D.C_SEQ AND C.CARE_ITEM_CD = D.CARE_ITEM_CD AND D.USE_YN = 'Y' AND D.DEL_YN = 'N'\n" +
                " \n" +
                "ORDER BY REFNUM ASC  ",
        resultClass = ProductEntity.class,
        resultSetMapping = "ProductYedahamOneTwoListMapping")

@SqlResultSetMapping(
        name = "DusanProductListMapping",
        classes = @ConstructorResult(
                targetClass = ContractDusanList.class,
                columns = {
                       	@ColumnResult(name = "CERT_NO", type = String.class),
                        @ColumnResult(name = "PLI_NM", type = String.class),
                        @ColumnResult(name = "COMMT", type = String.class),
                        @ColumnResult(name = "QTY", type = String.class),
                        @ColumnResult(name = "AMT", type = String.class),
                        @ColumnResult(name = "PAYBACK", type = String.class),
                        @ColumnResult(name = "MAIN_GB", type = String.class),
                        @ColumnResult(name = "INIT", type = String.class),
                        @ColumnResult(name = "ASSI_PROD_CD", type = String.class),
                        @ColumnResult(name = "PLIGCD", type = String.class),
                        @ColumnResult(name = "REF_ALPH", type = String.class),
                        @ColumnResult(name = "REF_NUM", type = String.class),
                        @ColumnResult(name = "CD", type = String.class),
                        @ColumnResult(name = "CDNM", type = String.class),
                        @ColumnResult(name = "UPSELLYN", type = String.class),
                        @ColumnResult(name = "STATE", type = String.class),
                        @ColumnResult(name = "CREAT_YN", type = String.class),
                        @ColumnResult(name = "FU04_QTY", type = String.class),
                        @ColumnResult(name = "FU04_AMT", type = String.class)
                })
)

/*
@NamedNativeQuery(name = "findDusanProductList",
      query = "SELECT \n" +
                "   :certno AS CERT_NO\n" +
                "  , PR02.ASSI_PROD_NM AS PLI_NM \n" +
                "  , PR02.GUBUN    AS COMMT\n" +
                "  , PR02.DIS_CNT  AS QTY\n" +
                "  , PR02.AMT  AS AMT\n" +
                "  , PR02.AMT  AS PAYBACK\n" +
                "  , PR02.MAIN_GB AS MAIN_GB  \n" +
                "  , PR02.BASI_YN AS INIT\n" +
                "  , PR02.ASSI_PROD_CD AS ASSI_PROD_CD\n" +
                "  , PR02.GRP_CD       AS PLIGCD  \n" +
                "  , CM12.REF_ALPH AS REF_ALPH\n" +
                "  , CM12.REF_NUM AS REF_NUM\n" +
                "  , CM12.CD AS CD\n" +
                "  , CM12.CD_NM AS CDNM\n" +
                "  , DECODE(CM12.REMARK,'2','4','4') AS UPSELLYN\n" +
                "FROM \n" +
                "  TBPR1002 PR02\n" +
                "  INNER JOIN TBNB1007 NB07 ON PR02.PROD_MAIN_CD = NB07.PROD_MAIN_CD AND NB07.CERT_NO = :certno\n" +
                "  LEFT JOIN TBCM1012 CM12 ON  SUBSTR(TABLET_PLI_GCD,3,2) = CM12.cd and cm12.type_cd ='TABLET_CODE'\n" +
                "WHERE 1=1\n" +
                "  AND PR02.MAIN_GB IN ('00', 'R1', 'R2', 'R3', 'R4', 'R6', 'R7', 'R8', 'B0', 'B1', 'B2' )  ---PLIDCD\n" +
                "  AND PR02.END_DATE='99991231'\n" +
                "  AND PR02.USE_YN='1'\n" +
                "  AND PR02.ASSI_PROD_CD NOT IN ('1040013', '1040014') \n" +
                "ORDER BY DECODE(MAIN_GB, 'A1', 'Z0', PR02.MAIN_GB)\n" +
                "       , PR02.CHO_TYPE\n" +
                "       , PR02.ASSI_PROD_NM\n" +
                "       , PR02.AMT\n" +
                "       , PR02.GRP_CD",
        resultClass = ProductEntity.class,
        resultSetMapping = "DusanProductListMapping")
*/

@NamedNativeQuery(name = "findDusanProductList",
      query = "SELECT \n" +
              "   :certno AS CERT_NO\n" +
              "  , PR02.ASSI_PROD_NM AS PLI_NM \n" +
              "  , PR02.GUBUN    AS COMMT\n" +
              "  , PR02.DIS_CNT  AS QTY\n" +
              "  , PR02.AMT  AS AMT\n" +
              "  , PR02.AMT  AS PAYBACK\n" +
              "  , PR02.MAIN_GB AS MAIN_GB  \n" +
              "  , PR02.BASI_YN AS INIT\n" +
              "  , PR02.ASSI_PROD_CD AS ASSI_PROD_CD\n" +
              "  , PR02.GRP_CD       AS PLIGCD  \n" +
              "  , CM12.REF_ALPH AS REF_ALPH\n" +
              "  , CM12.REF_NUM AS REF_NUM\n" +
              "  , CM12.CD AS CD\n" +
              "  , CM12.CD_NM AS CDNM\n" +
              "  , DECODE(CM12.REMARK,'2','4','4') AS UPSELLYN\n" +
              "  , '1' STATE \n" +
              "  , 'N' CREAT_YN\n" +
              "  , FU04.QTY AS FU04_QTY \n" +
              "  , FU04.AMT AS FU04_AMT \n" +
              "FROM \n" +
              "  TBPR1002 PR02\n" +
              "  INNER JOIN TBNB1007 NB07 ON PR02.PROD_MAIN_CD = NB07.PROD_MAIN_CD AND NB07.CERT_NO = :certno\n" +
              "  LEFT JOIN TBCM1012 CM12 ON  SUBSTR(TABLET_PLI_GCD,3,2) = CM12.cd and cm12.type_cd ='TABLET_CODE'\n" +
              "  LEFT JOIN TBFU1001 FU01 ON FU01.CERT_NO = NB07.CERT_NO \n" +
              "  LEFT JOIN TBFU1004 FU04 ON FU04.FUN_CTRL_NO = FU01.FUN_CTRL_NO AND FU04.ASSI_PROD_CD = PR02.ASSI_PROD_CD \n" +
              "WHERE 1=1\n" +
              "  AND PR02.MAIN_GB IN ('00', 'R1', 'R2', 'R3', 'R4', 'R6', 'R7', 'R8', 'B0', 'B1', 'B2' )  ---PLIDCD\n" +
              "  AND PR02.END_DATE='99991231'\n" +
              "  AND PR02.USE_YN='1'\n" +
              "  AND PR02.ASSI_PROD_CD NOT IN ('1040013', '1040014') \n" +
              "ORDER BY DECODE(MAIN_GB, 'A1', 'Z0', PR02.MAIN_GB)\n" +
              "       , PR02.CHO_TYPE\n" +
              "       , PR02.ASSI_PROD_NM\n" +
              "       , PR02.AMT\n" +
              "       , PR02.GRP_CD",
              resultClass = ProductEntity.class,
        resultSetMapping = "DusanProductListMapping")

@SqlResultSetMapping(
        name = "OptionalProductListMapping",
        classes = @ConstructorResult(
                targetClass = ContractList.class,
                columns = {
                        @ColumnResult(name = "certno", type = String.class),
                        @ColumnResult(name = "refalph", type = String.class),
                        @ColumnResult(name = "refnum", type = String.class),
                        @ColumnResult(name = "cd", type = String.class),
                        @ColumnResult(name = "cdnm", type = String.class),
                        @ColumnResult(name = "plinm", type = String.class),
                        @ColumnResult(name = "commt", type = String.class),
                        @ColumnResult(name = "qty", type = String.class),
                        @ColumnResult(name = "amt", type = String.class),
                        @ColumnResult(name = "payback", type = String.class),
                        @ColumnResult(name = "main_gb", type = String.class),
                        @ColumnResult(name = "init", type = String.class),
                        @ColumnResult(name = "upsellyn", type = String.class),
                        @ColumnResult(name = "ASSI_PROD_CD", type = String.class),
                        @ColumnResult(name = "STATE", type = String.class),
                        @ColumnResult(name = "CREAT_YN", type = String.class),
                        @ColumnResult(name = "FU04_QTY", type = String.class),
                        @ColumnResult(name = "FU04_AMT", type = String.class)
                })
)
@NamedNativeQuery(name = "findOptionalProductList",
        query =  " SELECT\n" +
                "                TOT_TB.CERT_NO AS CERTNO,\n" +
                "                TOT_TB.REF_ALPH AS refalph,\n" +
                "                TOT_TB.REF_NUM AS refnum,\n" +
                "                TOT_TB.CD AS cd,\n" +
                "                TOT_TB.CD_NM AS cdnm,\n" +
                "                NVL(replace(TOT_TB.PLI_NM, '/', ''),' ') AS plinm,\n" +
                "                TOT_TB.COMMT,\n" +
                "                TOT_TB.QTY,\n" +
                "                TOT_TB.AMT,\n" +
                "                TOT_TB.PAYBACK,\n" +
                "                '' AS MAIN_GB,\n" +
                "                '' AS INIT,\n" +
                "                TOT_TB.UPSELLYN,\n" +
                "                TOT_TB.ASSI_PROD_CD,\n" +
                "                FU04.STATE,\n" +
                "                FU04.CREAT_YN,\n" +
                "                FU04.QTY AS FU04_QTY,\n" +
                "                FU04.AMT AS FU04_AMT \n" +
                "                FROM (\n" +
                "                SELECT\n" +
                "                :certno AS CERT_NO, \n" +
                "                A.REF_ALPH,\n" +
                "                A.REF_NUM,\n" +
                "                B.PLI_NM,\n" +
                "                A.CD_NM,\n" +
                "                A.CD,\n" +
                "                B.COMMT,\n" +
                "                B.QTY,\n" +
                "                (B.AMT / QTY) AMT,\n" +
                "                (case when b.status <> '4' and b.dis_yn = 'N' and b.pb_dcd != '2' then b.amt / qty else b.payback / qty end) AS PAYBACK,\n" +
                "                (CASE WHEN A.REMARK = '2' AND B.PLI_NM IS NULL THEN '4' ELSE A.REMARK END) AS UPSELLYN,\n" +
                "                B.ASSI_PROD_CD\n" +
                "                FROM TBCM1012 A\n" +
                "                LEFT JOIN (\n" +
                "                          select A.ASSI_PROD_CD,SUBSTR(d.TABLET_PLI_GCD,3,2) AS PRODGB,d.PLI_NM ,d.COMMT,d.TABLET_PLI_DCD ,A.QTY, A.AMT, A.PAYBACK,b.STATUS,b.dis_yn,e.pb_dcd\n" +
                "                          from TBNB1008 a\n" +
                "                          left outer join tbfu1001 b on a.cert_no = b.cert_no and b.fun_ctrl_no = :functrlno \n" +
                "                          left outer join tbnb1007 c on b.cert_no = c.cert_no\n" +
                "                          left outer join tbpr1007 d on a.assi_prod_cd = d.plicd\n" +
                "                          left outer join tbpr1006 e on a.assi_prod_cd = e.plicd and c.prod_main_cd = e.prim_plicd\n" +
                "                          WHERE a.CERT_NO =:certno\n" +
                "                ) B ON A.CD = B.PRODGB\n" +
                "                WHERE 1=1\n" +
                "                AND TYPE_CD='TABLET_CODE'\n" +
                "                AND USE_YN='Y'\n" +
                "                AND QTY IS NOT NULL\n"+
                "                GROUP BY\n" +
                "                A.REF_ALPH,\n" +
                "                A.REF_NUM,\n" +
                "                B.PLI_NM,\n" +
                "                A.CD_NM,\n" +
                "                A.CD,\n" +
                "                B.COMMT,\n" +
                "                B.QTY,\n" +
                "                B.AMT,\n" +
                "                B.PAYBACK,\n" +
                "                B.PRODGB,\n" +
                "                A.REMARK,\n" +
                "                b.status,\n" +
                "                b.dis_yn,\n" +
                "                b.pb_dcd,\n" +
                "                B.ASSI_PROD_CD\n" +
                "                ORDER BY A.REF_NUM\n" +
                "                 ) TOT_TB \n" +
                "                 LEFT OUTER JOIN TBFU1001 FU01 ON FU01.CERT_NO = TOT_TB.CERT_NO \n" +
                "                 LEFT OUTER JOIN TBFU1004 FU04 ON FU04.FUN_CTRL_NO = FU01.FUN_CTRL_NO \n" +
                "                WHERE 1=1\n" +
                "                GROUP BY\n" +
                "                TOT_TB.CERT_NO, \n" +
                "                TOT_TB.REF_ALPH,\n" +
                "                TOT_TB.REF_NUM,\n" +
                "                TOT_TB.PLI_NM,\n" +
                "                TOT_TB.CD_NM,\n" +
                "                TOT_TB.CD,\n" +
                "                TOT_TB.COMMT,\n" +
                "                TOT_TB.QTY,\n" +
                "                TOT_TB.AMT,\n" +
                "                TOT_TB.PAYBACK,\n" +
                "                TOT_TB.UPSELLYN,\n" +
                "                TOT_TB.ASSI_PROD_CD,\n" +
                "                FU04.STATE,\n" +
                "                FU04.CREAT_YN,\n" +
                "                FU04.QTY,\n" +
                "                FU04.AMT\n" +
                "                \n" +
                "                UNION ALL\n" +
                "                SELECT\n" +
                "                :certno AS CERTNO,\n" +
                "                      '만기케어' AS refalph\n" +
                "                    , 99 AS refnum\n" +
                "                    ,'' AS CD\n" +
                "                    ,'' AS CD_NM\n" +
                "                    , C.CARE_ITEM_NM AS PLI_NM\n" +
                "                    , BENEFIT_CONTENTS AS COMMT\n" +
                "                    , 1 AS QTY\n" +
                "                    , (CASE WHEN  C.CARE_ITEM_CD ='FC04' THEN BENEFIT_VALUE ELSE 0 END) AS AMT\n" +
                "                    , (CASE WHEN  C.CARE_ITEM_CD ='FC04' THEN BENEFIT_VALUE ELSE 0 END) AS PAYBACK\n" +
                "                    ,'' AS MAIN_GB\n" +
                "                    ,'' AS INIT\n" +
                "                    ,'4' AS UPSELLYN\n" +
                "                    ,'' AS ASSI_PROD_CD\n" +
                "                    ,(CASE WHEN FU47.CARE_ITEM_CD IS NOT NULL THEN '1' ELSE ''END) AS STATE \n" +
                "                    ,'' AS CREAT_YN \n" +
                "                    , 0 AS FU04_QTY \n" +
                "                    , 0 AS FU04_AMT \n" +
                "                FROM (\n" +
                "                        SELECT  AA.FUN_CTRL_NO -- 의전번호\n" +
                "                              , TO_CHAR(AA.REG_DATE, 'YYYYMMDD') AS F_REG_DATE --접수일\n" +
                "                              , AA.CERT_NO -- 증서번호\n" +
                "                              , TRUNC(BB.DIS_APP_AMT*0.03, -3) AS AMT -- 가입금액 3%\n" +
                "                              , BB.DIS_APP_AMT AS TOT_PYAMT -- 가입금액\n" +
                "                              , CC.SUM_PYAMT AS  SUM_PYAMT  -- 납입금액\n" +
                "                              , BB.SUBS_DATE AS SUBS_DATE   --만기케어 날짜체크\n" +
                "                        FROM TBFU1001 AA\n" +
                "                        JOIN TBNB1007 BB ON BB.CERT_NO=AA.CERT_NO AND BB.STATE = '5'\n" +
                "                        LEFT JOIN(\n" +
                "                            SELECT\n" +
                "                                TBBC1002.CERT_NO\n" +
                "                               ,SUM(TBBC1002.PYMT_AMT) AS SUM_PYAMT\n" +
                "                              FROM TBBC1002\n" +
                "                              WHERE PYMT_GB IN ('1', '2', '3', '6', '9')\n" +
                "                             AND CERT_NO = :certno -- 증서번호\n" +
                "                              GROUP BY TBBC1002.CERT_NO\n" +
                "                          ) CC ON CC.CERT_NO=AA.CERT_NO\n" +
                "                        WHERE 1=1\n" +
                "                         AND NOT EXISTS ( --일시납 1년 경과 안된 건 제외\n" +
                "                                         SELECT\n" +
                "                                           NB07.CERT_NO\n" +
                "                                         FROM\n" +
                "                                           TBNB1007 NB07\n" +
                "                                           INNER JOIN (\n" +
                "                                                      SELECT\n" +
                "                                                        MAX(CERT_NO) CERT_NO\n" +
                "                                                        , MIN(PYMT_DATE) PYMT_DATE\n" +
                "                                                      FROM TBBC1002\n" +
                "                                                      WHERE CERT_NO = :certno  -- 증서번호\n" +
                "                                                      AND PYMT_GB IN ('1', '2', '3', '6', '9')\n" +
                "                                                     ) BC02\n" +
                "                                                 ON NB07.CERT_NO = BC02.CERT_NO\n" +
                "                                           LEFT OUTER JOIN ( --계약변경 월납에서 일시납으로 변경한 날 체크\n" +
                "                                                             SELECT\n" +
                "                                                               MAX(CERT_NO) CERT_NO\n" +
                "                                                               , MAX(RECP_DT) RECP_DT\n" +
                "                                                             FROM TBNB1027\n" +
                "                                                             WHERE\n" +
                "                                                               ALT_DCD = '1' AND PYMT_CYCLE = '00'\n" +
                "                                                               AND CERT_NO = :certno -- 증서번호\n" +
                "                                                            ) NB27 ON NB07.CERT_NO = NB27.CERT_NO\n" +
                "                                         WHERE\n" +
                "                                           NB07.PYMT_CYCLE = '00'\n" +
                "                                           AND NB07.STATE = '5'\n" +
                "                                           AND (TO_DATE(NVL(NB27.RECP_DT, BC02.PYMT_DATE) || '000000', 'YYYYMMDDHH24MISS') + 366) > SYSDATE\n" +
                "                                           AND NB07.CERT_NO = AA.CERT_NO\n" +
                "                                       )\n" +
                "                         AND AA.FUN_CTRL_NO =  (SELECT FUN_CTRL_NO FROM TBFU1001 WHERE CERT_NO= :certno AND FUN_CTRL_NO = :functrlno) -- 증서번호\n" +
                "                         AND CC.SUM_PYAMT >= BB.DIS_APP_AMT\n" +
                "                ) AA\n" +
                "                INNER JOIN TBFU4446 C ON C.USE_YN = 'Y' AND C.DEL_YN = 'N' AND (AA.AMT BETWEEN C.FROM_SECTION AND C.TO_SECTION ) AND (AA.F_REG_DATE BETWEEN C.START_DATE AND C.END_DATE )\n" +
                "                LEFT OUTER JOIN TBFU4447 FU47 ON FU47.FUN_CTRL_NO = :functrlno AND FU47.C_SEQ = C.C_SEQ AND FU47.CARE_ITEM_CD = C.CARE_ITEM_CD AND FU47.USE_YN = 'Y' \n" +
                "                AND AA.SUBS_DATE BETWEEN C.SUBS_START_DATE AND C.SUBS_END_DATE\n" +
                "                LEFT JOIN TBFU4447 D ON AA.FUN_CTRL_NO = D.FUN_CTRL_NO AND C.C_SEQ = D.C_SEQ AND C.CARE_ITEM_CD = D.CARE_ITEM_CD AND D.USE_YN = 'Y' AND D.DEL_YN = 'N'\n" +
                "                ORDER BY REFNUM ASC \n",
        resultClass = ProductEntity.class,
        resultSetMapping = "OptionalProductListMapping")

@SqlResultSetMapping(
        name = "NewProductListMapping",
        classes = @ConstructorResult(
                targetClass = ContractList.class,
                columns = {
                        @ColumnResult(name = "certno", type = String.class),
                        @ColumnResult(name = "refalph", type = String.class),
                        @ColumnResult(name = "refnum", type = String.class),
                        @ColumnResult(name = "cd", type = String.class),
                        @ColumnResult(name = "cdnm", type = String.class),
                        @ColumnResult(name = "plinm", type = String.class),
                        @ColumnResult(name = "commt", type = String.class),
                        @ColumnResult(name = "qty", type = String.class),
                        @ColumnResult(name = "amt", type = String.class),
                        @ColumnResult(name = "payback", type = String.class),
                        @ColumnResult(name = "main_gb", type = String.class),
                        @ColumnResult(name = "init", type = String.class),
                        @ColumnResult(name = "upsellyn", type = String.class),
                        @ColumnResult(name = "assi_prod_cd", type = String.class),
                        @ColumnResult(name = "state", type = String.class),
                        @ColumnResult(name = "creat_yn", type = String.class),
                        @ColumnResult(name = "fu04_qty", type = String.class),
                        @ColumnResult(name = "fu04_amt", type = String.class)
                })
)

@NamedNativeQuery(name = "findNewProductList",
        query = " SELECT\n" +
                "                TOT_TB.CERT_NO AS CERTNO,\n" +
                "                TOT_TB.REF_ALPH AS refalph,\n" +
                "                TOT_TB.REF_NUM AS refnum,\n" +
                "                TOT_TB.CD AS cd,\n" +
                "                TOT_TB.CD_NM AS cdnm,\n" +
                "                NVL(replace(TOT_TB.PLI_NM, '/', ''),' ') AS plinm,\n" +
                "                TOT_TB.COMMT,\n" +
                "                TOT_TB.QTY,\n" +
                "                TOT_TB.AMT,\n" +
                "                TOT_TB.PAYBACK,\n" +
                "                TOT_TB.MAIN_GB,\n" +
                //"                '' MAIN_GB,\n" +
                "                '' AS INIT,\n" +
                "                TOT_TB.UPSELLYN,\n" +
                "                TOT_TB.ASSI_PROD_CD,\n" +
                "                (CASE WHEN TOT_TB.ASSI_PROD_CD IS NOT NULL AND FU04.ASSI_PROD_CD IS NULL THEN '2' WHEN FU04.ASSI_PROD_CD IS NOT NULL AND TOT_TB.QTY <> FU04.QTY THEN '2' ELSE FU04.STATE END) STATE, \n" +
                "                FU04.CREAT_YN,\n" +
                "                FU04.QTY AS FU04_QTY,\n" +
                "                FU04.AMT AS FU04_AMT \n" +
                "                FROM (\n" +
                "                SELECT\n" +
                "                :certno AS CERT_NO,\n" +
                "                A.REF_ALPH,\n" +
                "                A.REF_NUM,\n" +
                "                B.PLI_NM,\n" +
                "                A.CD_NM,\n" +
                "                A.CD,\n" +
                "                B.COMMT,\n" +
                "                B.QTY,\n" +
                "                (B.AMT / QTY) AMT,\n" +
                "                RANK() OVER(partition by REF_NUM order by QTY ASC ) AS RNK,\n" +
                "                (case when b.status <> '4' and b.dis_yn = 'N' and b.pb_dcd != '2' then b.amt / qty else b.payback / qty end) AS PAYBACK,\n" +
                "                (CASE WHEN A.REMARK = '2' AND B.PLI_NM IS NULL THEN '4' ELSE A.REMARK END) AS UPSELLYN,\n" +
                "                B.ASSI_PROD_CD,\n" +
                "                B.MAIN_GB \n" +
                "                FROM TBCM1012 A\n" +
                "                LEFT JOIN (\n" +
                "                          select A.MAIN_GB, A.ASSI_PROD_CD,SUBSTR(d.TABLET_PLI_GCD,3,2) AS PRODGB,d.PLI_NM ,d.COMMT,d.TABLET_PLI_DCD ,A.QTY, A.AMT, A.PAYBACK,b.STATUS,b.dis_yn,e.pb_dcd \n" +
                "                          from TBNB1008 a\n" +
                "                          left outer join tbfu1001 b on a.cert_no = b.cert_no and b.fun_ctrl_no = :functrlno \n" +
                "                          left outer join tbnb1007 c on b.cert_no = c.cert_no\n" +
                "                          left outer join tbpr1007 d on a.assi_prod_cd = d.plicd\n" +
                "                          left outer join tbpr1006 e on a.assi_prod_cd = e.plicd and c.prod_main_cd = e.prim_plicd\n" +
                "                          WHERE a.CERT_NO =:certno\n" +
                "                ) B ON A.CD = B.PRODGB\n" +
                "                WHERE 1=1\n" +
                "                AND TYPE_CD='TABLET_CODE'\n" +
                "                AND USE_YN='Y'\n" +
                "                GROUP BY\n" +
                "                A.REF_ALPH,\n" +
                "                A.REF_NUM,\n" +
                "                B.PLI_NM,\n" +
                "                A.CD_NM,\n" +
                "                A.CD,\n" +
                "                B.COMMT,\n" +
                "                B.QTY,\n" +
                "                B.AMT,\n" +
                "                B.PAYBACK,\n" +
                "                B.PRODGB,\n" +
                "                A.REMARK,\n" +
                "                b.status,\n" +
                "                b.dis_yn,\n" +
                "                b.pb_dcd,\n" +
                "                B.ASSI_PROD_CD,\n" +
                "                B.MAIN_GB \n" +
                "                ORDER BY A.REF_NUM\n" +
                "                ) TOT_TB \n" +
                "                 LEFT OUTER JOIN TBFU1001 FU01 ON FU01.CERT_NO = TOT_TB.CERT_NO \n" +
                "                 LEFT OUTER JOIN TBFU1004 FU04 ON FU04.FUN_CTRL_NO = FU01.FUN_CTRL_NO AND FU04.ASSI_PROD_CD = TOT_TB.ASSI_PROD_CD\n" +
                "                WHERE 1=1\n" +
                "                 AND RNK='1'\n" +
                "                GROUP BY\n" +
                "                TOT_TB.CERT_NO, \n" +
                "                TOT_TB.REF_ALPH,\n" +
                "                TOT_TB.REF_NUM,\n" +
                "                TOT_TB.PLI_NM,\n" +
                "                TOT_TB.CD_NM,\n" +
                "                TOT_TB.CD,\n" +
                "                TOT_TB.COMMT,\n" +
                "                TOT_TB.QTY,\n" +
                "                TOT_TB.AMT,\n" +
                "                TOT_TB.PAYBACK,\n" +
                "                TOT_TB.MAIN_GB, \n" +
                "                TOT_TB.UPSELLYN,\n" +
                "                TOT_TB.ASSI_PROD_CD,\n" +
                "                FU04.ASSI_PROD_CD,\n" +
                "                FU04.STATE,\n" +
                "                FU04.CREAT_YN,\n" +
                "                FU04.QTY,\n" +
                "                FU04.AMT\n" +
                "                \n" +
                "                UNION ALL\n" +
                "                SELECT\n" +
                "                :certno AS CERTNO,\n" +
                "                      '만기케어' AS refalph\n" +
                "                    , 99 AS refnum\n" +
                "                    ,'' AS CD\n" +
                "                    ,'' AS CD_NM\n" +
                "                    , C.CARE_ITEM_NM AS PLI_NM\n" +
                "                    , BENEFIT_CONTENTS AS COMMT\n" +
                "                    , 1 AS QTY\n" +
                "                    , (CASE WHEN  C.CARE_ITEM_CD ='FC04' THEN BENEFIT_VALUE ELSE 0 END) AS AMT\n" +
                "                    , (CASE WHEN  C.CARE_ITEM_CD ='FC04' THEN BENEFIT_VALUE ELSE 0 END) AS PAYBACK\n" +
                "                    ,'' AS MAIN_GB\n" +
                "                    ,'' AS INIT\n" +
                "                    ,'4' AS UPSELLYN\n" +
                "                    ,'' AS ASSI_PROD_CD\n" +
                "                    ,(CASE WHEN FU47.CARE_ITEM_CD IS NOT NULL THEN '1' ELSE ''END) AS STATE \n" +
                "                    ,'' AS CREAT_YN \n" +
                "                    , 0 AS FU04_QTY \n" +
                "                    , 0 AS FU04_AMT \n" +
                "                FROM (\n" +
                "                        SELECT  AA.FUN_CTRL_NO -- 의전번호\n" +
                "                              , TO_CHAR(AA.REG_DATE, 'YYYYMMDD') AS F_REG_DATE --접수일\n" +
                "                              , AA.CERT_NO -- 증서번호\n" +
                "                              , TRUNC(BB.DIS_APP_AMT*0.03, -3) AS AMT -- 가입금액 3%\n" +
                "                              , BB.DIS_APP_AMT AS TOT_PYAMT -- 가입금액\n" +
                "                              , CC.SUM_PYAMT AS  SUM_PYAMT  -- 납입금액\n" +
                "                              , BB.SUBS_DATE AS SUBS_DATE   --만기케어 날짜체크\n" +
                "                        FROM TBFU1001 AA\n" +
                "                        JOIN TBNB1007 BB ON BB.CERT_NO=AA.CERT_NO AND BB.STATE = '5'\n" +
                "                        LEFT JOIN(\n" +
                "                            SELECT\n" +
                "                                TBBC1002.CERT_NO\n" +
                "                               ,SUM(TBBC1002.PYMT_AMT) AS SUM_PYAMT\n" +
                "                              FROM TBBC1002\n" +
                "                              WHERE PYMT_GB IN ('1', '2', '3', '6', '9')\n" +
                "                             AND CERT_NO = :certno -- 증서번호\n" +
                "                              GROUP BY TBBC1002.CERT_NO\n" +
                "                          ) CC ON CC.CERT_NO=AA.CERT_NO\n" +
                "                        WHERE 1=1\n" +
                "                         AND NOT EXISTS ( --일시납 1년 경과 안된 건 제외\n" +
                "                                         SELECT\n" +
                "                                           NB07.CERT_NO\n" +
                "                                         FROM\n" +
                "                                           TBNB1007 NB07\n" +
                "                                           INNER JOIN (\n" +
                "                                                      SELECT\n" +
                "                                                        MAX(CERT_NO) CERT_NO\n" +
                "                                                        , MIN(PYMT_DATE) PYMT_DATE\n" +
                "                                                      FROM TBBC1002\n" +
                "                                                      WHERE CERT_NO = :certno  -- 증서번호\n" +
                "                                                      AND PYMT_GB IN ('1', '2', '3', '6', '9')\n" +
                "                                                     ) BC02\n" +
                "                                                 ON NB07.CERT_NO = BC02.CERT_NO\n" +
                "                                           LEFT OUTER JOIN ( --계약변경 월납에서 일시납으로 변경한 날 체크\n" +
                "                                                             SELECT\n" +
                "                                                               MAX(CERT_NO) CERT_NO\n" +
                "                                                               , MAX(RECP_DT) RECP_DT\n" +
                "                                                             FROM TBNB1027\n" +
                "                                                             WHERE\n" +
                "                                                               ALT_DCD = '1' AND PYMT_CYCLE = '00'\n" +
                "                                                               AND CERT_NO = :certno -- 증서번호\n" +
                "                                                            ) NB27 ON NB07.CERT_NO = NB27.CERT_NO\n" +
                "                                         WHERE\n" +
                "                                           NB07.PYMT_CYCLE = '00'\n" +
                "                                           AND NB07.STATE = '5'\n" +
                "                                           AND (TO_DATE(NVL(NB27.RECP_DT, BC02.PYMT_DATE) || '000000', 'YYYYMMDDHH24MISS') + 366) > SYSDATE\n" +
                "                                           AND NB07.CERT_NO = AA.CERT_NO\n" +
                "                                       )\n" +
                "                         AND AA.FUN_CTRL_NO =  (SELECT FUN_CTRL_NO FROM TBFU1001 WHERE CERT_NO= :certno AND FUN_CTRL_NO = :functrlno) -- 증서번호\n" +
                "                         AND CC.SUM_PYAMT >= BB.DIS_APP_AMT\n" +
                "                ) AA\n" +
                "                INNER JOIN TBFU4446 C ON C.USE_YN = 'Y' AND C.DEL_YN = 'N' AND (AA.AMT BETWEEN C.FROM_SECTION AND C.TO_SECTION ) AND (AA.F_REG_DATE BETWEEN C.START_DATE AND C.END_DATE )\n" +
                "                LEFT OUTER JOIN TBFU4447 FU47 ON FU47.FUN_CTRL_NO = :functrlno AND FU47.C_SEQ = C.C_SEQ AND FU47.CARE_ITEM_CD = C.CARE_ITEM_CD AND FU47.USE_YN = 'Y' \n" +
                "                AND AA.SUBS_DATE BETWEEN C.SUBS_START_DATE AND C.SUBS_END_DATE\n" +
                "                LEFT JOIN TBFU4447 D ON AA.FUN_CTRL_NO = D.FUN_CTRL_NO AND C.C_SEQ = D.C_SEQ AND C.CARE_ITEM_CD = D.CARE_ITEM_CD AND D.USE_YN = 'Y' AND D.DEL_YN = 'N'\n" +
                "                ORDER BY REFNUM ASC \n",
        resultClass = ProductEntity.class,
        resultSetMapping = "NewProductListMapping")

@Data
@Entity
@Table(name="TBNB1007", schema = "tk_fsdev")
public class ProductEntity {
    @Id
    private String certNo;
}
