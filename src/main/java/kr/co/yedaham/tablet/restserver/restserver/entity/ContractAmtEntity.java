package kr.co.yedaham.tablet.restserver.restserver.entity;

import kr.co.yedaham.tablet.restserver.restserver.model.contractamt.Contract;
import kr.co.yedaham.tablet.restserver.restserver.model.contractamt.ContractAmt;
import kr.co.yedaham.tablet.restserver.restserver.model.product.ProductList;
import lombok.Data;

import javax.persistence.*;


@SqlResultSetMapping(
        name = "ContractAmtMapping",
        classes = @ConstructorResult(
                targetClass = ContractAmt.class,
                columns = {
                        @ColumnResult(name = "S_PROD_AMT", type = Integer.class),
                        @ColumnResult(name = "ORG_S_PROD_AMT", type = Integer.class),
                        @ColumnResult(name = "DIS_AMT", type = Integer.class),
                        @ColumnResult(name = "DIS_APP_AMT", type = Integer.class),
                        @ColumnResult(name = "PYMT_AMT", type = Integer.class),
                        @ColumnResult(name = "M_PROD_AMT", type = Integer.class),
                        @ColumnResult(name = "EXCP_AMT", type = Integer.class),
                })
)


@NamedNativeQuery(name = "findContractAmt",
        query = "SELECT\n" +
                "  DECODE(A.PROD_MAIN_CD,'1040001',0,A.S_PROD_AMT) AS S_PROD_AMT, -- 상품금액\n" +
                "  A.S_PROD_AMT AS ORG_S_PROD_AMT, -- 원상품금액 (두산상품금액 확인용)\n" +
                "  DECODE(A.PROD_MAIN_CD,'1040001',0,(CASE WHEN A.PYMT_CYCLE ='00' AND FU01.PRE_YN <>'1' AND SUBSTR(SUBS_DATE,1,4)+1||SUBSTR(SUBS_DATE,5,4) > TO_CHAR(FU01.REG_DATE,'YYYYMMDD') THEN A.S_PROD_AMT - ABS(DIS_CNT_AMT)\n" +
                "                                          WHEN A.PYMT_CYCLE ='00' AND FU01.PRE_YN = '1' AND SUBSTR(SUBS_DATE,1,4)+1||SUBSTR(SUBS_DATE,5,4) > TO_CHAR(FU01.REG_DATE,'YYYYMMDD') THEN (A.S_PROD_AMT - ABS(DIS_CNT_AMT)) *1.1\n" +
                "                                          WHEN A.PYMT_CYCLE<>'00' AND FU01.PRE_YN = '1' THEN DIS_APP_AMT * 1.1\n" +
                "                                          WHEN A.PYMT_CYCLE<>'00' AND FU01.DIS_YN ='N' THEN A.S_PROD_AMT\n" +
                "                                          ELSE A.DIS_APP_AMT END)) AS DIS_APP_AMT,\n" +
                "  DECODE(A.PROD_MAIN_CD,'1040001',0,(CASE WHEN A.PYMT_CYCLE ='00' AND FU01.PRE_YN <>'1' AND SUBSTR(SUBS_DATE,1,4)+1||SUBSTR(SUBS_DATE,5,4) > TO_CHAR(FU01.REG_DATE,'YYYYMMDD') THEN DIS_CNT_AMT\n" +
                "                                          WHEN A.PYMT_CYCLE ='00' AND FU01.PRE_YN = '1' AND SUBSTR(SUBS_DATE,1,4)+1||SUBSTR(SUBS_DATE,5,4) > TO_CHAR(FU01.REG_DATE,'YYYYMMDD') THEN DIS_CNT_AMT\n" +
                "                                          WHEN A.PYMT_CYCLE<>'00' AND FU01.PRE_YN = '1' THEN TERM_DIS_AMT + ABS(DIS_CNT_AMT)\n" +
                "                                          WHEN A.PYMT_CYCLE<>'00' AND FU01.DIS_YN ='N'  THEN 0\n" +
                "                                          ELSE TERM_DIS_AMT + ABS(DIS_CNT_AMT) END)) AS DIS_AMT,\n" +
                "  DECODE(T1.PYMT_AMT,'',0,T1.PYMT_AMT) AS PYMT_AMT, -- 납입금액\n" +
                "  PR08.M_PROD_AMT AS M_PROD_AMT, -- 가전제품금액\n" +
                "  A.EXCP_AMT \n" +
                "FROM TBNB1007 A LEFT JOIN (\n" +
                "                          SELECT\n" +
                "                                TBBC1002.CERT_NO\n" +
                "                               ,SUM(TBBC1002.PYMT_AMT) AS PYMT_AMT\n" +
                "                           FROM TBBC1002\n" +
                "                           WHERE PYMT_GB IN ('1','2','3','6','9')\n" +
                "                         GROUP BY TBBC1002.CERT_NO) T1 ON  T1.CERT_NO=A.CERT_NO\n" +
                "  LEFT JOIN TBFU1001 FU01 ON A.CERT_NO = FU01.CERT_NO AND FU01.FUN_CTRL_NO = :functrlno \n" +
                "  LEFT JOIN TBFU1006 FU06 ON FU01.FUN_CTRL_NO = FU06.FUN_CTRL_NO\n" +
                "  LEFT JOIN TBPR1008 PR08 ON A.PROD_MAIN_CD = PR08.PROD_MAIN_CD AND PR08.DEPCD = EMP_TO_DEP(A.SOLI_ID)\n" +
                "WHERE A.CERT_NO = :certno",
        resultClass = ContractAmtEntity.class,
        resultSetMapping = "ContractAmtMapping")

@SqlResultSetMapping(
        name = "ContractMapping",
        classes = @ConstructorResult(
                targetClass = Contract.class,
                columns = {
                        @ColumnResult(name = "CERT_NO", type = String.class),
                        @ColumnResult(name = "PROD_NM", type = String.class),
                        @ColumnResult(name = "FNR_TYPE_NM", type = String.class),
                        @ColumnResult(name = "SUBSDT", type = String.class),
                        @ColumnResult(name = "CONTDT", type = String.class),
                        @ColumnResult(name = "CONTST_NM", type = String.class),
                        @ColumnResult(name = "SUBS_CUST_NM", type = String.class),
                        @ColumnResult(name = "CUST_NM", type = String.class),
                        @ColumnResult(name = "TOT_PYAMT", type = String.class),
                        @ColumnResult(name = "PYAMT", type = String.class),
                        @ColumnResult(name = "DIS_AMT", type = String.class),
                        @ColumnResult(name = "PYCL_NM", type = String.class),
                        @ColumnResult(name = "PYPRD_NM", type = String.class),
                        @ColumnResult(name = "PYCNT", type = String.class),
                        @ColumnResult(name = "LST_PYYM", type = String.class),
                        @ColumnResult(name = "CF_DNM", type = String.class),
                        @ColumnResult(name = "DIS_NM", type = String.class),
                        @ColumnResult(name = "PYST_NM", type = String.class),
                        @ColumnResult(name = "DL_PYAMT", type = String.class),
                        @ColumnResult(name = "SUM_PYAMT", type = String.class),
                        @ColumnResult(name = "PYMT_METH_NM", type = String.class),
                        @ColumnResult(name = "PYMT_BANK_NM", type = String.class),
                        @ColumnResult(name = "PYMT_ACC_NO", type = String.class),
                        @ColumnResult(name = "ACC_HOLD", type = String.class),
                        @ColumnResult(name = "ACC_REAT", type = String.class),
                        @ColumnResult(name = "TRANS_DATE_NM", type = String.class),
                        @ColumnResult(name = "CH_DATE", type = String.class),
                        @ColumnResult(name = "RET_DATE", type = String.class),
                        @ColumnResult(name = "FNR_DATE", type = String.class)
                })
)


@NamedNativeQuery(name = "findContract",
        query = "SELECT \n" +
                " T00.CERT_NO -- 증서번호\n" +
                ",T00.PROD_NM -- 상품명              \n" +
                ",T00.FNR_TYPE_NM  --장법(매장화장)\n" +
                ",T00.SUBSDT       --청약일자        \n" +
                ",T00.CONTDT       --계약일자         \n" +
                ",T00.CONTST_NM      --계약상태        \n" +
                ",T00.SUBS_CUST_NM   --청약자     \n" +
                ",T00.CUST_NM        -- 계약자       \n" +
                ",T00.TOT_PYAMT      -- 계약금     \n" +
                ",T00.PYAMT          -- 월납입금       \n" +
                ",T00.DIS_AMT        -- 할인금액            \n" +
                ",T00.PYCL_NM        -- 납입주기       \n" +
                ",T00.PYPRD_NM       -- 납입기간       \n" +
                ",T00.PYCNT          -- 납입횟수                 \n" +
                ",T00.LST_PYYM       -- 최종납입월       \n" +
                ",T00.CF_DNM         -- 할인구분       \n" +
                ",T00.DIS_NM         -- 할인내역        \n" +
                ",T00.PYST_NM       -- 납입상태        \n" +
                ",T00.DL_PYAMT      -- 연체금        \n" +
                ",T00.SUM_PYAMT     -- 납입합계        \n" +
                ",T00.PYMT_METH_NM  -- 납입방법          \n" +
                ",T00.PYMT_BANK_NM    -- 은행          \n" +
                ",T00.PYMT_ACC_NO     -- 계좌번호      \n" +
                ",T00.ACC_HOLD        --예금주      \n" +
                ",T00.ACC_REAT         -- 관계     \n" +
                ",T00.TRANS_DATE_NM    --이체일자     \n" +
                ",T00.CH_DATE          --계약변경일     \n" +
                ",T00.RET_DATE         --철회해약일     \n" +
                ",T00.FNR_DATE         --의전일자\n" +
                "FROM \n" +
                "(SELECT                                                                                                                                                                                                     \n" +
                "  TBNB1007.CERT_NO                                                                                                                                                                                                                                                                                                                                                                               \n" +
                " ,TBPR1001.S_PROD_NM AS PROD_NM                                                                                                                                                                            \n" +
                " ,TBPR1001.S_PROD_DNM AS PROD_DNM                                                                                                                                                                                                                                                                                                                                                                 \n" +
                " ,CASE                                                                                                                                                                                                     \n" +
                "    WHEN TBNB1007.FNR_TYPE='0' THEN '공통'                                                                                                                                                                 \n" +
                "    WHEN TBNB1007.FNR_TYPE='1' THEN '매장'                                                                                                                                                                 \n" +
                "    WHEN TBNB1007.FNR_TYPE='2' THEN '화장'                                                                                                                                                                 \n" +
                "  END AS FNR_TYPE_NM                                                                                                                                                                                       \n" +
                " ,TBNB1007.SUBS_DATE AS SUBSDT                                                                                                                                                                             \n" +
                " ,TBNB1007.CONT_DATE AS CONTDT                                                                                                                                                                                                                                                                                                                                                          \n" +
                " ,CASE         \n" +
                "    WHEN TBNB1007.STATE IN('2','6','8') THEN '정상'\n" +
                "    WHEN TBNB1007.STATE='7' AND TBNB1103.CONFIRM_STATE = '02' AND TBNB1103.APVR_DATE IS NOT NULL THEN FNCM1012('STATE',TBNB1007.STATE)||'(거절)'\n" +
                "    ELSE FNCM1012('STATE',TBNB1007.STATE)\n" +
                "  END AS CONTST_NM\n" +
                " ,NVL(TBCS100A.CUST_NM,TBCS100B.CUST_NM) AS SUBS_CUST_NM\n" +
                " ,NVL(TBCS1002.CUST_NM,TBCS1003.CUST_NM) AS CUST_NM\n" +
                " ,NVL2(TBCS1003.CUST_ID,'1','2') AS PSGP_DCD                                                                                                                                                               \n" +
                " ,TBNB1007.S_PROD_AMT - TBNB1007.EXCP_AMT AS PROD_AMT                                                                                                                                                      \n" +
                " ,TBNB1007.DIS_APP_AMT AS TOT_PYAMT                                                                                                                                                                        \n" +
                " ,TBNB1007.MTH_AMT AS PYAMT                                                                                                                                                                                                                                                                                                                                                   \n" +
                " ,CASE                                                                                                                                                                                                     \n" +
                "    WHEN TBNB1007.PROD_CD LIKE 'A%' OR TBNB1007.PROD_CD LIKE 'B%' THEN TBNB1007.TERM_DIS_AMT - TBNB1007.DIS_CNT_AMT                                                                                        \n" +
                "    ELSE TBNB1007.TERM_DIS_AMT + TBNB1007.DIS_CNT_AMT                                                                                                                                                      \n" +
                "  END AS DIS_AMT                                                                                                                                                                                          \n" +
                " ,FNCM1012('PYMTCY',TBNB1007.PYMT_CYCLE) AS PYCL_NM                                                                                                                                                         \n" +
                "  ,CASE WHEN TBNB1007.PYMT_CYCLE='00' THEN '-'                                                                                                                                                             \n" +
                "    ELSE FNCM1012('PYMTTERM',TBNB1007.PYMT_TERM)                                                                                                                                                                                                                                                                                                                                \n" +
                "  END AS PYPRD_NM                                                                                                                                                                                          \n" +
                ",TBNB1007.FULL_PYMT_CNT||'/'||TBNB1007.TOT_PYMT_CNT AS PYCNT                                                                                                                                              \n" +
                " ,TBNB1007.FULL_PYMT_MTH AS LST_PYYM                                                                                                                                                                       \n" +
                " ,TBNB1033.CF_DCD                                                                                                                                                                                          \n" +
                " ,CASE WHEN TBNB1033.CF_DCD='00' THEN '일반'                                                                                                                                                               \n" +
                "    WHEN TBNB1033.CF_DCD='10' THEN '공제회'                                                                                                                                                                \n" +
                "    WHEN TBNB1033.CF_DCD='20' THEN '이벤트'                                                                                                                                                                \n" +
                "    WHEN TBNB1033.CF_DCD='40' THEN '법인'                                                                                                                                                                  \n" +
                "  END AS CF_DNM                                                                                                                                                                                                                                                                                                                                                                                         \n" +
                " ,TBPR1015.DIS_NM                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                        \n" +
                " ,CASE WHEN TBNB1007.STATE='1' AND FLOOR(MONTHS_BETWEEN(SYSDATE,SUBSTR(TBNB1007.SUBS_DATE,1,6)||'01')) > 0  THEN '연체' ||  '('||TBBO1001.PRSN_OVER_PYMT_CNT ||')'                                         \n" +
                "    WHEN TBNB1007.STATE IN('0','3','4','5','7') THEN '중지'                                                                                                                                                \n" +
                "    WHEN TBNB1007.STATE IN('2','8') AND TBNB1007.PYMT_CYCLE='01' AND FLOOR(MONTHS_BETWEEN(SYSDATE,TBNB1007.FULL_PYMT_MTH||'01')) > 1 THEN '연체' ||  '('||TBBO1001.PRSN_OVER_PYMT_CNT ||')'                \n" +
                "    WHEN TBNB1007.STATE IN('2','8') AND TBNB1007.PYMT_CYCLE='12' AND FLOOR(MONTHS_BETWEEN(SYSDATE,ADD_MONTHS(TBNB1007.FULL_PYMT_MTH||'01',12))) > 12 THEN '연체' ||  '('||TBBO1001.PRSN_OVER_PYMT_CNT ||')'\n" +
                "    WHEN TBNB1007.STATE IN('2','8') AND TBNB1007.PYMT_CYCLE='00' AND FLOOR(MONTHS_BETWEEN(SYSDATE,TBNB1007.FULL_PYMT_DATE||'01')) > 1 THEN '연체' ||  '('||'1' ||')'  -- 일시납은 만기월 기준\n" +
                "    ELSE '정상'                                                                                                                                                                                            \n" +
                "  END PYST_NM                                                                                                                                                                                              \n" +
                " ,CASE WHEN TBNB1007.STATE='1' AND FLOOR(MONTHS_BETWEEN(SYSDATE,SUBSTR(TBNB1007.SUBS_DATE,1,6)||'01')) > 0 THEN TBNB1007.MTH_AMT                                                                           \n" +
                "    WHEN TBNB1007.STATE='1' THEN 0                                                                                                                                                                         \n" +
                "    WHEN TBNB1007.STATE IN('0','3','4','5','7') THEN 0\n" +
                "    WHEN FLOOR(MONTHS_BETWEEN(SYSDATE,TBNB1007.FULL_PYMT_MTH||'01')) <= 0 THEN 0 --Prepayment/This Month Payment\n" +
                "    WHEN TBNB1007.STATE = '6' OR NVL(TBBC1002.SUM_PYAMT,0) >= TBNB1007.DIS_APP_AMT THEN 0                                                                                                                                                  \n" +
                "    WHEN TO_CHAR(SYSDATE,'YYYYMM') > TBNB1007.FULL_PYMT_DATE THEN TBNB1007.DIS_APP_AMT -  NVL(TBBC1002.SUM_PYAMT,0)                                                                                           \n" +
                "    WHEN TBNB1007.PYMT_CYCLE='01' AND FLOOR(MONTHS_BETWEEN(SYSDATE,TBNB1007.FULL_PYMT_MTH||'01')) > 0 THEN FLOOR(MONTHS_BETWEEN(SYSDATE,TBNB1007.FULL_PYMT_MTH||'01')-1) * TBNB1007.MTH_AMT                \n" +
                "    WHEN TBNB1007.PYMT_CYCLE='12' AND FLOOR(MONTHS_BETWEEN(SYSDATE,TBNB1007.FULL_PYMT_MTH||'01')) > 12 THEN FLOOR(MONTHS_BETWEEN(SYSDATE,TBNB1007.FULL_PYMT_MTH||'01')/12) * TBNB1007.MTH_AMT              \n" +
                "    ELSE 0                                                                                                                                                                                                 \n" +
                "  END AS DL_PYAMT                                                                                                                                                                                          \n" +
                " ,CASE WHEN TBNB1007.STATE='1' THEN TBNB1007.MTH_AMT                                                                                                                                                       \n" +
                "    WHEN TBNB1007.STATE IN('0','3','4','5','7') THEN 0\n" +
                "    WHEN FLOOR(MONTHS_BETWEEN(SYSDATE,TBNB1007.FULL_PYMT_MTH||'01')) <= 0 THEN 0 --Prepayment/This Month Payment\n" +
                "    WHEN TBNB1007.STATE = '6' OR NVL(TBBC1002.SUM_PYAMT,0) >= TBNB1007.DIS_APP_AMT THEN 0                                                                                                                                                  \n" +
                "    WHEN TO_CHAR(SYSDATE,'YYYYMM') > TBNB1007.FULL_PYMT_DATE THEN TBNB1007.DIS_APP_AMT -  NVL(TBBC1002.SUM_PYAMT,0)                                                                                           \n" +
                "    WHEN TBNB1007.PYMT_CYCLE='01' THEN FLOOR(MONTHS_BETWEEN(SYSDATE,TBNB1007.FULL_PYMT_MTH||'01')) * TBNB1007.MTH_AMT                                                                                      \n" +
                "    WHEN TBNB1007.PYMT_CYCLE='12' THEN FLOOR(MONTHS_BETWEEN(SYSDATE,TBNB1007.FULL_PYMT_MTH||'01')/12) * TBNB1007.MTH_AMT                                                                                   \n" +
                "    ELSE 0                                                                                                                                                                                                 \n" +
                "  END AS TOT_DL_PYAMT                                                                                                                                                                                      \n" +
                " ,NVL(TBBC1002.SUM_PYAMT,0) AS SUM_PYAMT                                                                                                                                                                                                                                                                                                                                                    \n" +
                " ,FNCM1012('PYMTMETH',TBNB1007.PYMT_METH) AS PYMT_METH_NM                                                                                                                                                                                                                                                                                                                             \n" +
                " ,FNCM1012('BKCD',DECODE(TBNB1007.PYMT_METH,'2',TBNB1049_50.BANK_CD,TBNB1010.BANK_CD)) AS PYMT_BANK_NM                                                                                                     \n" +
                " ,pls_decrypt_b64_id(DECODE(TBNB1007.PYMT_METH,'2',TBNB1049_50.ACC_NO,TBNB1010.ACC_NO),2) AS PYMT_ACC_NO                                                                                                   \n" +
                " ,TBNB1010.STATE AS ACC_STATE                                                                                                                                                                              \n" +
                " ,CASE WHEN TBNB1007.CMS_STATE='2' THEN FNCM1012('CMSSTATE',TBNB1007.CMS_STATE)                                                                                                                            \n" +
                "       ELSE FNCM1012('ACCSTATE',TBNB1010.STATE)                                                                                                                                                            \n" +
                "       END AS ACC_STATE_NM                                                                                                                                                                                 \n" +
                " ,TBNB1010.ACC_HOLD                                                                                                                                                                                                                       \n" +
                " ,FNCM1012('REAT',TBNB1010.ACC_REAT) AS ACC_REAT                                                                                                                                                                                                                                                                                                                                                \n" +
                ",CASE WHEN TBNB1007.REAL_PYMT_TERM = '1'\n" +
                "                THEN ''\n" +
                "                ELSE (CASE WHEN TBNB1007.PYMT_METH = '1' THEN TBNB1010.TRANS_DATE || ' 일'\n" +
                "                           WHEN TBNB1007.PYMT_METH = '4' THEN ''\n" +
                "                           ELSE '말일'\n" +
                "                      END)\n" +
                "                END AS TRANS_DATE_NM                                                                                                                                                                                                                                                                                                                                                                       \n" +
                "  ,TBNB1027.APLY_DT AS CH_DATE                                                                                                                                                                             \n" +
                "  , CASE WHEN TBNB1007.STATE <>'7' THEN TBNB1023.APPLI_DATE \n" +
                "        ELSE \n" +
                "           CASE WHEN TBNB1023.APPLI_DATE IS NOT NULL THEN TBNB1023.APPLI_DATE \n" +
                "           ELSE TO_CHAR(TBNB1007.UPDATE_DATE, 'YYYYMMDD')\n" +
                "           END\n" +
                "        END AS RET_DATE                                                                                                                                                                             \n" +
                "  ,TBFU1001.FUN_START_DATE AS FNR_DATE                                                                                                                                                                     \n" +
                "FROM TBNB1007                                                                                                                                                                                              \n" +
                "JOIN TBPR1001 ON TBPR1001.S_PROD_CD=TBNB1007.PROD_CD AND TBPR1001.PROD_MAIN_CD=TBNB1007.PROD_MAIN_CD                                                                                                       \n" +
                "JOIN TBNB1035 ON TBNB1035.CERT_NO=TBNB1007.CERT_NO                                                                                                                                                         \n" +
                "JOIN TBNB1033 ON TBNB1033.CFCD=TBNB1035.CFCD                                                                                                                                                               \n" +
                "JOIN TBBM1011 ON TBBM1011.EMPNO =TBNB1007.SOLI_ID  AND TBBM1011.USE_YN = '0' AND TBBM1011.DEL_YN = '0'\n" +
                "JOIN TBBM1010 ON TBBM1010.DEPCD =TBBM1011.DEPCD   AND TBBM1010.USE_YN = '0' AND TBBM1010.DEL_YN = '0'  \n" +
                "JOIN TBPR1015 ON TBPR1015.DIS_CD=TBNB1007.DIS_CD AND TBPR1015.S_PROD_CD='A10001'                                                                                 \n" +
                "LEFT JOIN TBNB1010 ON TBNB1007.PYMT_METH IN ('1','4') AND TBNB1010.CERT_NO=TBNB1007.CERT_NO                                                                                                                \n" +
                "LEFT JOIN (                                                                                                                                                                                                \n" +
                "            SELECT                                                                                                                                                                                         \n" +
                "              AA.CUST_ID                                                                                                                                                                                   \n" +
                "              , BB.CERT_NO                                                                                                                                                                                 \n" +
                "              , AA.BANK_CD                                                                                                                                                                                 \n" +
                "              , AA.IMAG_ACC_NO AS ACC_NO                                                                                                                                                                   \n" +
                "            FROM TBNB1049 AA                                                                                                                                                                               \n" +
                "            INNER JOIN TBNB1050 BB ON AA.IMAG_ACC_NO = BB.IMAG_ACC_NO                                                                                                                                      \n" +
                "            WHERE AA.IMAG_ACC_GB BETWEEN '02' AND '03' -- 개인, 법인 다회성 계좌                                                                                                                           \n" +
                "            AND AA.CANCEL_DATE IS NULL                                                                                                                                                                     \n" +
                "          ) TBNB1049_50 ON TBNB1007.PYMT_METH='2' AND TBNB1007.CUST_ID=TBNB1049_50.CUST_ID AND TBNB1007.CERT_NO=TBNB1049_50.CERT_NO   -------- 2012.11.16 가상계좌 테이블 변경으로 수정                    \n" +
                "LEFT JOIN(                                                                                                                                                                                                 \n" +
                "  SELECT                                                                                                                                                                                                   \n" +
                "    TBBC1002.CERT_NO                                                                                                                                                                                       \n" +
                "   ,SUM(CASE WHEN PYMT_GB IN ('1','2','3') THEN TBBC1002.PYMT_AMT ELSE 0 END) AS SUM_PYAMT\n" +
                "   ,SUM(CASE WHEN PYMT_GB IN ('5') THEN TBBC1002.PYMT_AMT ELSE 0 END) AS FUN_SUM_PYAMT\n" +
                "  FROM TBBC1002                                                                                                                                                                                            \n" +
                "  WHERE PYMT_GB IN ('1','2','3','5')                                                                                                                                                                                    \n" +
                "  GROUP BY TBBC1002.CERT_NO) TBBC1002 ON TBBC1002.CERT_NO=TBNB1007.CERT_NO                                                                                                                                 \n" +
                "LEFT JOIN TBCS1002 ON TBNB1007.PSGP_CERT_DCD IN('11','12','19','23') AND TBCS1002.CUST_ID=TBNB1007.CUST_ID                                                                                                 \n" +
                "LEFT JOIN TBCS1003 ON TBNB1007.PSGP_CERT_DCD IN('21') AND TBCS1003.CUST_ID=TBNB1007.CUST_ID                                                                                                                \n" +
                "LEFT JOIN TBCS1002 TBCS100A ON TBNB1007.PSGP_CERT_DCD IN('11','12','19','23') AND TBCS100A.CUST_ID=TBNB1007.ONCE_CUST_ID                                                                                   \n" +
                "LEFT JOIN TBCS1003 TBCS100B ON TBNB1007.PSGP_CERT_DCD IN('21') AND TBCS100B.CUST_ID=TBNB1007.ONCE_CUST_ID                                                                                                  \n" +
                "LEFT JOIN TBNB1023 ON TBNB1007.CERT_NO=TBNB1023.CERT_NO                                                                                                                                                    \n" +
                "LEFT JOIN TBFU1001 ON TBNB1007.CERT_NO=TBFU1001.CERT_NO AND TBFU1001.STATUS<>'4'                                                                                                                           \n" +
                "LEFT JOIN TBNB1027 ON TBNB1007.CERT_NO=TBNB1027.CERT_NO AND TBNB1027.APLY_ST='1' AND ALT_DCD='0'                                                                                                           \n" +
                "LEFT JOIN TBNB1047 ON TBNB1007.CERT_NO=TBNB1047.CERT_NO                                                                                                                                                    \n" +
                "LEFT JOIN TBNB1062 ON TBNB1007.CERT_NO=TBNB1062.CERT_NO                                                                                                                                                    \n" +
                "LEFT JOIN TBBC1023 ON TBNB1007.CERT_NO=TBBC1023.CERT_NO AND TBNB1007.STATE IN ('2','6','8')   \n" +
                "LEFT JOIN TBCA1001 ON TBNB1007.CERT_NO = TBCA1001.CERT_NO\n" +
                "LEFT OUTER JOIN TBIF1024 B ON B.DEPOSIT_ACCT_NO = RPAD(TBCA1001.ACC_NO, 16, ' ')\n" +
                "                                      AND B.DEPOSIT_BANK_CODE = LPAD(TBCA1001.BANK_CD, 8, '0')\n" +
                "                                      AND B.BIZ_DATE = '20' || REPLACE(TBCA1001.WITH_DATE, '/', '')\n" +
                "LEFT JOIN TBBO1001 ON TBNB1007.CERT_NO = TBBO1001.CERT_NO AND TBBO1001.CLOSE_YYMM = FNCM1012('CLOSEMTH',1)                                                                                                 \n" +
                "LEFT JOIN (                                                                                                                                                                                                \n" +
                "        SELECT                                                                                                                                                                                             \n" +
                "                GAM103.CERT_NO                                                                                                                                                                             \n" +
                "              ,  GAM103.GFT_CD                                                                                                                                                                             \n" +
                "               , GAM101.GFT_NM                                                                                                                                                                             \n" +
                "            FROM                                                                                                                                                                                           \n" +
                "                TKLMM103 GAM103 ,                                                                                                                                                                          \n" +
                "                TKLMM101 GAM101                                                                                                                                                                            \n" +
                "            WHERE                                                                                                                                                                                          \n" +
                "                GAM103.GFT_CD = GAM101.GFT_CD                                                                                                                                                              \n" +
                "                AND GAM103.USE_YN= '0' AND GAM103.DEL_YN ='0'                                                                                                                                              \n" +
                "            ) GAM ON TBNB1007.CERT_NO = GAM.CERT_NO\n" +
                "LEFT JOIN TBNB1103 ON TBNB1007.CERT_NO = TBNB1103.CERT_NO\n" +
                "LEFT JOIN TBNB1003 ON TBNB1007.SUBS_NO = TBNB1003.SUBS_NO      \n" +
                "WHERE TBNB1007.CERT_NO= :certno ) T00",
        resultClass = ContractAmtEntity.class,
        resultSetMapping = "ContractMapping")



@Data
@Entity
@Table(name="TBCS1002", schema = "tk_fsdev")
public class ContractAmtEntity {
    @Id
    private String custId;
}
