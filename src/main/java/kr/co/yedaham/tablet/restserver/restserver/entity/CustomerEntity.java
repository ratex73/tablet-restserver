package kr.co.yedaham.tablet.restserver.restserver.entity;

import kr.co.yedaham.tablet.restserver.restserver.model.customer.*;
import lombok.Data;

import javax.persistence.*;


@SqlResultSetMapping(
        name = "CustomerInfoMapping",
        classes = @ConstructorResult(
                targetClass = CustomerInfo.class,
                columns = {
                        @ColumnResult(name = "FUN_CTRL_NO", type = String.class),
                        @ColumnResult(name = "CERT_NO", type = String.class),
                        @ColumnResult(name = "CUST_ID", type = String.class),
                        @ColumnResult(name = "CUST_NM", type = String.class),
                        @ColumnResult(name = "BIRTH_DATE", type = String.class),
                        @ColumnResult(name = "ADDR", type = String.class),
                        @ColumnResult(name = "CONT_DATE", type = String.class),
                        @ColumnResult(name = "CUST_TYPE", type = String.class),
                        @ColumnResult(name = "CUST_TYPE_NM", type = String.class),
                        @ColumnResult(name = "PROD_NM", type = String.class),
                        @ColumnResult(name = "PROD_AMT", type = String.class),
                        @ColumnResult(name = "CONT_AMT", type = String.class),
                        @ColumnResult(name = "REMA_PYMT_AMT", type = String.class),
                        @ColumnResult(name = "RECEIVE_CUST_NM", type = String.class),
                        @ColumnResult(name = "RECEIVE_CUST_REAT", type = String.class),
                        @ColumnResult(name = "CALL_NO", type = String.class),
                        @ColumnResult(name = "CANCEL_AMT_TARGET_YN", type = String.class),
                        @ColumnResult(name = "CANCEL_AMT", type = String.class)
                })
)

@NamedNativeQuery(name = "findCustomerInfo",
        query = "SELECT\n" +
                "    FUN_CTRL_NO  --의전번호\n" +
                "  , CERT_NO  --증서번호\n" +
                "  , CUST_ID  --고객ID\n" +
                "  , CUST_NM --고객명\n" +
                "  , BIRTH_DATE --주민번호, 사업자번호\n" +
                "  , ADDR  --주소\n" +
                "  , CONT_DATE  --계약일자\n" +
                "  , CUST_TYPE  --고객구분\n" +
                "  , CUST_TYPE_NM --고객구분명\n" +
                "  , PROD_NM  --상품명\n" +
                "  , PROD_AMT -- 상품금액\n" +
                "  , CONT_AMT --계약금액\n" +
                "  , REMA_PYMT_AMT  --잔여금액\n" +
                "  , RECEIVE_CUST_NM  --접수자\n" +
                "  , RECEIVE_CUST_REAT  --관계\n" +
                "  , CALL_NO --통화가능연락처\n" +
                "  , CANCEL_AMT_TARGET_YN --취소위약금(대상여부)\n" +
                "  , CASE\n" +
                "      WHEN FUN_STATUS = '4' THEN FUN_CUST_AMT\n" +
                "      WHEN CANCEL_AMT_TARGET_YN = 'N' THEN 0\n" +
                "      ELSE CANCEL_AMT\n" +
                "    END CANCEL_AMT --취소위약금액\n" +
                "FROM (\n" +
                "    SELECT\n" +
                "      NB07.CERT_NO\n" +
                "      , NB07.CUST_ID\n" +
                "      , NVL(CS02.CUST_NM, CS03.CUST_NM) CUST_NM\n" +
                "      , CASE\n" +
                "          WHEN CS02.CUST_ID IS NOT NULL THEN SUBSTR(CS02.BIRTH_DATE, 1, 4) || '-' || SUBSTR(CS02.BIRTH_DATE, 5, 2) || '-' || SUBSTR(CS02.BIRTH_DATE, 7, 2)\n" +
                "          ELSE SUBSTR(CS03.BIZ_RGST_CD, 1, 3) || '-' || SUBSTR(CS03.BIZ_RGST_CD, 4, 2) || '-' || SUBSTR(CS03.BIZ_RGST_CD, 6, 5) \n" +
                "        END BIRTH_DATE\n" +
                "      , CASE\n" +
                "          WHEN CS02.CUST_ID IS NOT NULL THEN '(' || CS02.HOME_ZIP_CODE || ')' || CS02.HOME_ADDR1 || ' ' || CS02.HOME_ADDR2\n" +
                "          ELSE '(' || CS03.ZIP_CODE || ')' || CS03.ADDR1 || ' ' || CS03.ADDR2\n" +
                "        END ADDR\n" +
                "      , SUBSTR(NB07.CONT_DATE, 1, 4) || '-' || SUBSTR(NB07.CONT_DATE, 5, 2) || '-' || SUBSTR(NB07.CONT_DATE, 7, 2) AS CONT_DATE\n" +
                "      , NB07.CUST_TYPE\n" +
                "      , FNCM1012('CUSTTYPE', NB07.CUST_TYPE) CUST_TYPE_NM\n" +
                "      , FU01.FUN_CTRL_NO\n" +
                "      , FU01.STATUS AS FUN_STATUS\n" +
                "      , PR01.S_PROD_DNM AS PROD_NM\n" +
                "      , NB07.PROD_AMT\n" +
                "      , NB07.DIS_APP_AMT AS CONT_AMT\n" +
                "      , NB07.DIS_APP_AMT - NVL(\n" +
                "                               (SELECT SUM(PYMT_AMT) FROM TBBC1002 BC02 WHERE BC02.CERT_NO = NB07.CERT_NO AND PYMT_GB IN ('1', '2', '3', '6', '9') GROUP BY BC02.CERT_NO)\n" +
                "                               , 0) REMA_PYMT_AMT\n" +
                "      , FU01.RECEIVE_CUST_NM\n" +
                "      , (SELECT CD_NM FROM TBCM1012 WHERE TYPE_CD = 'RECEIVE_CUST_REAT' AND CD = FU01.RECEIVE_CUST_REAT) AS RECEIVE_CUST_REAT\n" +
                "      , CASE\n" +
                "          WHEN LENGTH(FU01.MOUR_TEL_NO) = 0 THEN ''\n" +
                "          WHEN LENGTH(FU01.MOUR_TEL_NO) = 11 THEN SUBSTR(FU01.MOUR_TEL_NO, 1, 3) || '-' || SUBSTR(FU01.MOUR_TEL_NO, 4, 4) || '-' || SUBSTR(FU01.MOUR_TEL_NO, 8, 4)\n" +
                "          ELSE FU01.MOUR_TEL_NO\n" +
                "        END AS CALL_NO\n" +
                "      , CASE\n" +
                "            WHEN FU01.STATUS = '3' THEN 'N'\n" +
                "            WHEN FU01.STATUS_ARR = '06' THEN 'Y'\n" +
                "            WHEN NVL(NB07.CONT_DATE, TO_CHAR(FU01.REG_DATE,'YYYYMMDD') ) < '20140301' THEN 'N'\n" +
                "            WHEN NVL(NB07.CONT_DATE, TO_CHAR(FU01.REG_DATE,'YYYYMMDD') ) >= '20190701' AND (FU01.STATUS_ARR IS NULL OR FU01.STATUS_ARR IN ('01','02','06')) THEN 'Y'   \n" +
                "            WHEN NVL(NB07.CONT_DATE, TO_CHAR(FU01.REG_DATE,'YYYYMMDD') ) BETWEEN '20140301' AND '20190631' AND (FU01.STATUS_ARR IS NULL OR FU01.STATUS_ARR IN ('02','06')) THEN 'Y' \n" +
                "            ELSE 'N'\n" +
                "        END CANCEL_AMT_TARGET_YN\n" +
                "      , FU06.CUST_AMT FUN_CUST_AMT\n" +
                "      , NVL(TB_CANCEL.AMT, 0) AS CANCEL_AMT\n" +
                "    FROM\n" +
                "      TBNB1007 NB07 INNER JOIN TBPR1001 PR01 ON NB07.PROD_MAIN_CD = PR01.PROD_MAIN_CD\n" +
                "      INNER JOIN TBFU1001 FU01 ON NB07.CERT_NO = FU01.CERT_NO  \n" +
                "      LEFT OUTER JOIN TBFU1006 FU06 ON FU01.FUN_CTRL_NO = FU06.FUN_CTRL_NO\n" +
                "      LEFT OUTER JOIN TBCS1002 CS02 ON NB07.CUST_ID = CS02.CUST_ID\n" +
                "      LEFT OUTER JOIN TBCS1003 CS03 ON NB07.CUST_ID = CS03.CUST_ID\n" +
                "      LEFT OUTER JOIN (\n" +
                "                        SELECT PROD_MAIN_CD, ASSI_PROD_NM, AMT FROM (\n" +
                "                            SELECT \n" +
                "                              PROD_MAIN_CD, ASSI_PROD_NM, AMT  \n" +
                "                            FROM TBPR1002\n" +
                "                            WHERE 1=1\n" +
                "                            AND ASSI_PROD_NM LIKE '%의전취소위약금(취소위약금)%'\n" +
                "                            AND (SELECT TO_CHAR(REG_DATE, 'YYYYMMDD') FROM TBFU1001 WHERE FUN_CTRL_NO =:functrlno) BETWEEN START_DATE AND END_DATE  --> 의전등록일자 (YYYYMMDD)\n" +
                "                            UNION ALL\n" +
                "                            SELECT\n" +
                "                            PROD_MAIN_CD\n" +
                "                            , ASSI_PROD_NM\n" +
                "                            , AMT\n" +
                "                             FROM (\n" +
                "                                    SELECT\n" +
                "                                     A.PRIM_PLICD AS PROD_MAIN_CD\n" +
                "                                   , B.PLI_NM AS ASSI_PROD_NM\n" +
                "                                   , B.UNIT_AMT AS AMT\n" +
                "                                      , ROW_NUMBER() OVER(PARTITION BY A.PRIM_PLICD ORDER BY B.STDT DESC) RNK\n" +
                "                                    FROM\n" +
                "                                      TBPR1006 A JOIN TBPR1007 B ON A.PLICD = B.PLICD\n" +
                "                                    WHERE 1=1\n" +
                "                                      AND B.PLI_NM = '의전취소위약금(취소위약금)'\n" +
                "                                      AND B.USE_YN = '1'\n" +
                "                                      AND A.USE_YN = '1'\n" +
                "                                      AND (SELECT TO_CHAR(REG_DATE, 'YYYYMMDD') FROM TBFU1001 WHERE FUN_CTRL_NO =:functrlno)  BETWEEN B.STDT AND B.EDDT  -- 의전등록일자 (YYYYMMDD)\n" +
                "                                      AND NVL((SELECT NB07.CUST_ID FROM TBFU1001 FU01, TBNB1007 NB07 WHERE FU01.CERT_NO = NB07.CERT_NO AND FU01.FUN_CTRL_NO =:functrlno), 'NONE') NOT IN ('C20160801464', 'C20140209371') -- 고객ID\n" +
                "                                   )\n" +
                "                            WHERE RNK = 1\n" +
                "                            UNION ALL\n" +
                "                            SELECT\n" +
                "                              A.PRIM_PLICD AS PROD_MAIN_CD\n" +
                "                             , B.PLI_NM AS ASSI_PROD_NM\n" +
                "                             , B.UNIT_AMT AS AMT\n" +
                "                            FROM\n" +
                "                              TBPR1006 A JOIN TBPR1007 B ON A.PLICD = B.PLICD\n" +
                "                            WHERE B.PLICD IN ('2001620')\n" +
                "                              AND B.USE_YN = '1'\n" +
                "                              AND A.USE_YN = '1'\n" +
                "                              AND NVL((SELECT NB07.CUST_ID FROM TBFU1001 FU01, TBNB1007 NB07 WHERE FU01.CERT_NO = NB07.CERT_NO AND FU01.FUN_CTRL_NO =:functrlno), 'NONE') IN ('C20160801464', 'C20140209371')  -- 고객ID\n" +
                "                        )\n" +
                "                      ) TB_CANCEL\n" +
                "                   ON TB_CANCEL.PROD_MAIN_CD = NB07.PROD_MAIN_CD\n" +
                "    WHERE\n" +
                "        FU01.FUN_CTRL_NO = :functrlno  --의전번호\n" +
                ")",
        resultClass = CustomerEntity.class,
        resultSetMapping = "CustomerInfoMapping")
@SqlResultSetMapping(
        name = "PaymentInfoMapping",
        classes = @ConstructorResult(
                targetClass = CustomerPaymentInfo.class,
                columns = {
                        @ColumnResult(name = "PYMT_YYMM", type = String.class),
                        @ColumnResult(name = "PYMT_CNT", type = String.class),
                        @ColumnResult(name = "PYMT_DATE", type = String.class),
                        @ColumnResult(name = "PYMT_CYCLE", type = String.class),
                        @ColumnResult(name = "PYMT_CYCLE_NM", type = String.class),
                        @ColumnResult(name = "PYMT_AMT", type = String.class),
                        @ColumnResult(name = "PYMT_TYPE", type = String.class),
                        @ColumnResult(name = "PYMT_TYPE_NM", type = String.class),
                        @ColumnResult(name = "PYMT_GB", type = String.class),
                        @ColumnResult(name = "PYMT_GB_NM", type = String.class),
                        @ColumnResult(name = "PROC_DATE", type = String.class),
                })
)

@NamedNativeQuery(name = "findPaymentInfo",
        query = "SELECT\n" +
                "   TBBC1002.PYMT_YYMM       --납입월분\n" +
                "  ,TBBC1002.PYMT_CNT        --회차\n" +
                "  ,TBBC1002.PYMT_DATE       --입금일자\n" +
                "  ,TBBC1002.PYMT_CYCLE      \n" +
                "  , CASE WHEN TBBC1002.PYMT_CNT !='0' THEN TBCM1012_PYMTCY.CD_NM\n" +
                "    ELSE ''\n" +
                "    END AS PYMT_CYCLE_NM    --납입주기\n" +
                "  ,TBBC1002.PYMT_AMT        --납입금\n" +
                "  ,TBBC1003.PYMT_TYPE       \n" +
                "  ,TBCM1012_PYMTTYPE.CD_NM AS PYMT_TYPE_NM --납입방법\n" +
                "  ,TBBC1002.PYMT_GB\n" +
                "  ,TBCM1012_PYMTGB.CD_NM AS PYMT_GB_NM      --납입구분\n" +
                "  ,TBBC1003.PROC_DATE                       --처리일자\n" +
                "FROM TBBC1002 \n" +
                "JOIN TBBC1003 ON TBBC1002.PYMT_NO=TBBC1003.PYMT_NO\n" +
                "JOIN TBCM1012 TBCM1012_PYMTGB ON TBCM1012_PYMTGB.TYPE_CD='PYMTGB' AND TBCM1012_PYMTGB.CD=TBBC1002.PYMT_GB\n" +
                "JOIN TBCM1012 TBCM1012_PYMTTYPE ON TBCM1012_PYMTTYPE.TYPE_CD='PYMTTYPE' AND TBCM1012_PYMTTYPE.CD=TBBC1003.PYMT_TYPE\n" +
                "LEFT JOIN TBCM1012 TBCM1012_PYMTCY ON TBCM1012_PYMTCY.TYPE_CD='PYMTCY' AND TBCM1012_PYMTCY.CD=TBBC1002.PYMT_CYCLE\n" +
                "WHERE TBBC1002.CERT_NO = :certno\n" +
                "ORDER BY \n" +
                "   TBBC1002.PYMT_YYMM DESC \n" +
                "  ,TBBC1003.PYMT_NO DESC \n" +
                "  ,TBBC1002.PYMT_GB ASC",
        resultClass = CustomerEntity.class,
        resultSetMapping = "PaymentInfoMapping")

@SqlResultSetMapping(
        name = "CorporationInfoMapping",
        classes = @ConstructorResult(
                targetClass = CorporationInfo.class,
                columns = {
                        @ColumnResult(name = "SEQ", type = String.class),
                        @ColumnResult(name = "S_PROD_NM", type = String.class),
                        @ColumnResult(name = "PROD_AMT", type = String.class),
                        @ColumnResult(name = "POSITION", type = String.class),
                })
)

@NamedNativeQuery(name = "findCorporationInfo",
        query = "SELECT TBCS1030.SEQ\n" +
                "     , TBPR1001.S_PROD_NM\n" +
                "     , TBCS1030.PROD_AMT\n" +
                "     , TBCS1030.POSITION\n" +
                "FROM TBCS1030\n" +
                "JOIN TBPR1001 ON TBCS1030.PROD_MAIN_CD = TBPR1001.PROD_MAIN_CD     \n" +
                "WHERE 1=1\n" +
                "  AND TBCS1030.USE_YN = 'Y' AND TBCS1030.DEL_YN = 'N'  \n" +
                "  AND CUST_ID IN (\n" +
                "    SELECT CUST_ID FROM TBNB1007 WHERE CUST_ID = :custid \n" +
                "    UNION ALL \n" +
                "    SELECT HCUST_ID FROM TBNB1007 WHERE CUST_ID = :custid \n" +
                "  ) ",
        resultClass = CustomerEntity.class,
        resultSetMapping = "CorporationInfoMapping")

@SqlResultSetMapping(
        name = "CorporationCustomerInfoMapping",
        classes = @ConstructorResult(
                targetClass = CorporationCustomerInfo.class,
                columns = {
                        @ColumnResult(name = "SEQ", type = String.class),
                        @ColumnResult(name = "CUST_ID", type = String.class),
                        @ColumnResult(name = "NAME", type = String.class),
                        @ColumnResult(name = "TEL", type = String.class),
                        @ColumnResult(name = "EMAIL", type = String.class),
                })
)

@NamedNativeQuery(name = "findCorporationCustomerInfo",
        query = "SELECT TBCS1031.SEQ\n" +
                ", TBCS1031.CUST_ID\n" +
                ", TBCS1031.NAME\n" +
                ", TBCS1031.TEL\n" +
                ", TBCS1031.EMAIL\n" +
                "FROM TBCS1031\n" +
                "WHERE 1=1\n" +
                "  AND TBCS1031.USE_YN = 'Y' AND TBCS1031.DEL_YN = 'N'  \n" +
                "  AND CUST_ID IN (\n" +
                "    SELECT CUST_ID FROM TBNB1007 WHERE CUST_ID = :custid \n" +
                "    UNION ALL \n" +
                "    SELECT HCUST_ID FROM TBNB1007 WHERE CUST_ID = :custid \n" +
                "  ) ",
        resultClass = CustomerEntity.class,
        resultSetMapping = "CorporationCustomerInfoMapping")

@SqlResultSetMapping(
        name = "CorporationSupportInfoMapping",
        classes = @ConstructorResult(
                targetClass = CorporationSupportInfo.class,
                columns = {
                        @ColumnResult(name = "SEQ", type = String.class),
                        @ColumnResult(name = "CUST_ID", type = String.class),
                        @ColumnResult(name = "SUPPORT", type = String.class),
                        @ColumnResult(name = "ETC", type = String.class),
                })
)

@NamedNativeQuery(name = "findCorporationSupportInfo",
        query = "SELECT TBCS1032.SEQ\n" +
                ", TBCS1032.CUST_ID\n" +
                ", FNCM1012('SUPPORT',TBCS1032.SUPPORT) SUPPORT\n" +
                ", TBCS1032.ETC\n" +
                "FROM TBCS1032   \n" +
                "WHERE 1=1\n" +
                "  AND TBCS1032.USE_YN = 'Y' AND TBCS1032.DEL_YN = 'N'  \n" +
                "  AND CUST_ID IN (\n" +
                "    SELECT CUST_ID FROM TBNB1007 WHERE CUST_ID = :custid \n" +
                "    UNION ALL \n" +
                "    SELECT HCUST_ID FROM TBNB1007 WHERE CUST_ID = :custid \n" +
                "  ) ",
        resultClass = CustomerEntity.class,
        resultSetMapping = "CorporationSupportInfoMapping")

@SqlResultSetMapping(
        name = "CorporationDisposableInfoMapping",
        classes = @ConstructorResult(
                targetClass = CorporationDisposableInfo.class,
                columns = {
                        @ColumnResult(name = "SEQ", type = String.class),
                        @ColumnResult(name = "CUST_ID", type = String.class),
                        @ColumnResult(name = "DISPOPRODUCT", type = String.class),
                        @ColumnResult(name = "DELIVERY", type = String.class),
                        @ColumnResult(name = "ETC", type = String.class),
                })
)

@NamedNativeQuery(name = "findCorporationDisposableInfo",
        query = "SELECT TBCS1034.SEQ\n" +
                ", TBCS1034.CUST_ID\n" +
                ", FNCM1012('DISPOPRODUCT',TBCS1034.DISPOPRODUCT) DISPOPRODUCT\n" +
                ", FNCM1012('DELIVERY',TBCS1034.DELIVERY) DELIVERY\n" +
                ", TBCS1034.ETC\n" +
                "FROM TBCS1034   \n" +
                "WHERE 1=1\n" +
                "  AND TBCS1034.USE_YN = 'Y' AND TBCS1034.DEL_YN = 'N'  \n" +
                "  AND CUST_ID IN (\n" +
                "    SELECT CUST_ID FROM TBNB1007 WHERE CUST_ID = :custid \n" +
                "    UNION ALL \n" +
                "    SELECT HCUST_ID FROM TBNB1007 WHERE CUST_ID = :custid \n" +
                "  ) ",
        resultClass = CustomerEntity.class,
        resultSetMapping = "CorporationDisposableInfoMapping")

@SqlResultSetMapping(
        name = "CorporationRangeProductSupportInfoMapping",
        classes = @ConstructorResult(
                targetClass = CorporationRangeProductSupportInfo.class,
                columns = {
                        @ColumnResult(name = "SEQ", type = String.class),
                        @ColumnResult(name = "CUST_ID", type = String.class),
                        @ColumnResult(name = "RANGE", type = String.class),
                        @ColumnResult(name = "GRPARENTS", type = String.class),
                        @ColumnResult(name = "PARENTS", type = String.class),
                        @ColumnResult(name = "OWN", type = String.class),
                        @ColumnResult(name = "PARTNER", type = String.class),
                        @ColumnResult(name = "SIGRPARENTS", type = String.class),
                        @ColumnResult(name = "SIPARENTS", type = String.class),
                        @ColumnResult(name = "BROTHER", type = String.class),
                        @ColumnResult(name = "CHILDREN", type = String.class),
                        @ColumnResult(name = "GRANDSON", type = String.class),
                })
)

@NamedNativeQuery(name = "findCorporationRangeProductSupportInfo",
        query = "SELECT\n" +
                "SEQ\n" +
                ",CUST_ID\n" +
                ",FNCM1012('RANGE',RANGE) RANGE\n" +
                ",GRPARENTS\n" +
                ",PARENTS\n" +
                ",OWN\n" +
                ",PARTNER\n" +
                ",SIGRPARENTS\n" +
                ",SIPARENTS\n" +
                ",BROTHER\n" +
                ",CHILDREN\n" +
                ",GRANDSON\n" +
                "FROM TBCS1033\n" +
                "WHERE 1=1\n" +
                "  AND TBCS1033 .USE_YN = 'Y' AND TBCS1033.DEL_YN = 'N'  \n" +
                "  AND TBCS1033.CUST_ID IN (\n" +
                "    SELECT CUST_ID FROM TBNB1007 WHERE CUST_ID = :custid \n" +
                "    UNION ALL \n" +
                "    SELECT HCUST_ID FROM TBNB1007 WHERE CUST_ID = :custid \n" +
                "  ) ",
        resultClass = CustomerEntity.class,
        resultSetMapping = "CorporationRangeProductSupportInfoMapping")

@SqlResultSetMapping(
        name = "CorporationRangeAccountSupportInfoMapping",
        classes = @ConstructorResult(
                targetClass = CorporationRangeAccountSupportInfo.class,
                columns = {
                        @ColumnResult(name = "SEQ", type = String.class),
                        @ColumnResult(name = "CUST_ID", type = String.class),
                        @ColumnResult(name = "THFLOWER", type = String.class),
                        @ColumnResult(name = "BANK_CD", type = String.class),
                        @ColumnResult(name = "ACC_NO", type = String.class),
                        @ColumnResult(name = "ACC_HOLD", type = String.class),
                        @ColumnResult(name = "BIZ_RGST_CD", type = String.class),
                })
)

@NamedNativeQuery(name = "findCorporationRangeAccountSupportInfo",
        query = "SELECT \n" +
                "SEQ\n" +
                ",CUST_ID\n" +
                ",THFLOWER\n" +
                ",FNCM1012('BKCD',BANK_CD) BANK_CD\n" +
                ",ACC_NO\n" +
                ",ACC_HOLD\n" +
                ",BIZ_RGST_CD\n" +
                "FROM TBCS1029 \n" +
                "WHERE 1=1\n" +
                "  AND TBCS1029.USE_YN = 'Y' AND TBCS1029.DEL_YN = 'N'  \n" +
                "  AND CUST_ID IN (\n" +
                "    SELECT CUST_ID FROM TBNB1007 WHERE CUST_ID = :custid \n" +
                "    UNION ALL \n" +
                "    SELECT HCUST_ID FROM TBNB1007 WHERE CUST_ID = :custid \n" +
                "  ) ",
        resultClass = CustomerEntity.class,
        resultSetMapping = "CorporationRangeAccountSupportInfoMapping")

@SqlResultSetMapping(
        name = "CorporationPaymentMethodInfoMapping",
        classes = @ConstructorResult(
                targetClass = CorporationPaymentMethodInfo.class,
                columns = {
                        @ColumnResult(name = "SEQ", type = String.class),
                        @ColumnResult(name = "CUST_ID", type = String.class),
                        @ColumnResult(name = "PYMT_METH", type = String.class),
                        @ColumnResult(name = "ETC", type = String.class),
                })
)

@NamedNativeQuery(name = "findCorporationPaymentMethodInfo",
        query = " SELECT\n" +
                "SEQ\n" +
                ",CUST_ID\n" +
                ",FNCM1012('PYMT_METH', PYMT_METH) AS PYMT_METH\n" +
                ",ETC\n" +
                "FROM TBCS1035\n" +
                "WHERE 1=1\n" +
                "  AND TBCS1035 .USE_YN = 'Y' AND TBCS1035.DEL_YN = 'N'  \n" +
                "  AND TBCS1035.CUST_ID IN (\n" +
                "    SELECT CUST_ID FROM TBNB1007 WHERE CUST_ID = :custid \n" +
                "    UNION ALL \n" +
                "    SELECT HCUST_ID FROM TBNB1007 WHERE CUST_ID = :custid \n" +
                "  ) ",
        resultClass = CustomerEntity.class,
        resultSetMapping = "CorporationPaymentMethodInfoMapping")

@SqlResultSetMapping(
        name = "CorporationFiledownloadInfoMapping",
        classes = @ConstructorResult(
                targetClass = CorporationFiledownloadInfo.class,
                columns = {
                        @ColumnResult(name = "SEQ", type = String.class),
                        @ColumnResult(name = "CUST_ID", type = String.class),
                        @ColumnResult(name = "FILE_NM", type = String.class),
                        @ColumnResult(name = "UPLOAD_DATE", type = String.class),
                })
)

@NamedNativeQuery(name = "findCorporationFiledownloadInfo",
        query = " SELECT\n" +
                "SEQ\n" +
                ",CUST_ID\n" +
                ",FILE_NM\n" +
                ",TO_CHAR(UPDATE_DATE, 'YYYY-MM-DD') UPLOAD_DATE\n" +
                "FROM TBCS1037\n" +
                "WHERE 1=1\n" +
                "  AND TBCS1037 .USE_YN = 'Y' \n" +
                "  AND TBCS1037.CUST_ID IN (\n" +
                "    SELECT CUST_ID FROM TBNB1007 WHERE CUST_ID = :custid \n" +
                "    UNION ALL \n" +
                "    SELECT HCUST_ID FROM TBNB1007 WHERE CUST_ID = :custid \n" +
                "  ) ",
        resultClass = CustomerEntity.class,
        resultSetMapping = "CorporationFiledownloadInfoMapping")

@SqlResultSetMapping(
        name = "CorporationPrecautionsInfoMapping",
        classes = @ConstructorResult(
                targetClass = CorporationPrecautionsInfo.class,
                columns = {
                        @ColumnResult(name = "SEQ", type = String.class),
                        @ColumnResult(name = "CUST_ID", type = String.class),
                        @ColumnResult(name = "ETC", type = String.class),
                })
)

@NamedNativeQuery(name = "findCorporationPrecautionsInfo",
        query = "SELECT\n" +
                "SEQ,CUST_ID,ETC\n" +
                "FROM TBCS1036\n" +
                "WHERE 1=1\n" +
                "  AND TBCS1036 .USE_YN = 'Y' AND TBCS1036.DEL_YN = 'N'  \n" +
                "  AND TBCS1036.CUST_ID IN (\n" +
                "    SELECT CUST_ID FROM TBNB1007 WHERE CUST_ID = :custid \n" +
                "    UNION ALL \n" +
                "    SELECT HCUST_ID FROM TBNB1007 WHERE CUST_ID = :custid \n" +
                "  ) ",
        resultClass = CustomerEntity.class,
        resultSetMapping = "CorporationPrecautionsInfoMapping")

@Data
@Entity
@Table(name="TBCS1003", schema = "tk_fsdev")
public class CustomerEntity {
    @Id
    private String custId;
}
