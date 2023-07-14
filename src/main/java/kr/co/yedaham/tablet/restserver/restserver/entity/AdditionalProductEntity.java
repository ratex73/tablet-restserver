package kr.co.yedaham.tablet.restserver.restserver.entity;

import kr.co.yedaham.tablet.restserver.restserver.model.product.AdditionalProductList;
import lombok.Data;

import javax.persistence.*;

@SqlResultSetMapping(
        name = "AdditionalProductYedahamOneTwoListMapping",
        classes = @ConstructorResult(
                targetClass = AdditionalProductList.class,
                columns = {
                        @ColumnResult(name = "refalph", type = String.class),
                        @ColumnResult(name = "refnum", type = String.class),
                        @ColumnResult(name = "cd", type = String.class),
                        @ColumnResult(name = "cdnm", type = String.class),
                        @ColumnResult(name = "plinm", type = String.class),
                        @ColumnResult(name = "commt", type = String.class),
                        @ColumnResult(name = "qty", type = String.class),
                        @ColumnResult(name = "amt", type = String.class),
                })
)


@NamedNativeQuery(name = "findAdditionalProductYedahamOneTwoList",
        query = "SELECT \n" +
                "A.REF_ALPH AS refalph,\n" +
                "A.REF_NUM AS refnum,\n" +
                "A.CD,\n" +
                "A.CD_NM AS CDNM,\n" +
                "replace(B.PLI_NM, '/', '') AS plinm,\n" +
                "B.COMMT,\n" +
                "B.QTY,\n" +
                "NVL(B.AMT, '0') AS AMT,\n" +
                "  '1' AS STATE, \n" +
                "  'N' AS CREAT_YN \n" +
               // "  FU04.QTY AS FU04_QTY, \n" +
               // "  FU04.AMT AS FU04_AMT \n" +
                "FROM TBCM1012 A \n" +
                "LEFT JOIN (\n" +
                "SELECT ASSI_PROD_CD, ASSI_PROD_NM AS PLI_NM,'' AS COMMT, '1' AS QTY, AMT,  SUBSTR(TABLET_PLI_GCD,3,2) AS PRODGB,TABLET_UPSELL_SEQ, SELECTGB  from  TBPR1002 \n" +
                "WHERE PROD_MAIN_CD=(SELECT PROD_MAIN_CD FROM TBNB1007 WHERE CERT_NO=:certno)  -- 상품코드\n" +
                "AND ASSI_PROD_CD LIKE '6%' AND USE_YN IN('1','Y') \n" +
                "AND (SELECT TO_CHAR(REG_DATE,'YYYYMMDD') FROM TBFU1001 WHERE CERT_NO =:certno and STATUS <> 4) BETWEEN START_DATE AND END_DATE) \n" +
                "B ON A.CD = B.PRODGB\n" +
                "  LEFT OUTER JOIN TBFU1004 FU04 ON FU04.ASSI_PROD_CD = B.ASSI_PROD_CD \n" +
                "WHERE 1=1\n" +
                "AND TYPE_CD='TABLET_CODE'\n" +
                "AND ((A.REF_NUM IN ('10','22','23') and :amt = '-1') or USE_YN IN ('Y','1'))\n" +
                "AND B.SELECTGB ='Y'\n" +
                "AND REF_NUM=:grpcode\n" +
                "AND PLI_NM IS NOT NULL\n" +
                "AND (CASE WHEN PLI_NM LIKE '%페이백%' THEN 9999999 ELSE AMT END) > :amt  \n" +
                "AND REPLACE(B.PLI_NM, ' ','') NOT LIKE REPLACE(:plinm,' ','') \n" +
                "and  (case when pli_nm in('일회용품SET 300인(페이백 후 추가용)','장례도우미(페이백 후 추가용)') and :amt = -1 then 9999999  when pli_nm like '%페이백 후 추가용%' then 9999999 else nvl(b.amt,0) end ) > (SELECT nvl(max(amt),0)  from  TBPR1002 WHERE PROD_MAIN_CD=(SELECT PROD_MAIN_CD FROM TBNB1007 WHERE CERT_NO=:certno) AND ASSI_PROD_CD LIKE '6%' AND USE_YN IN('1','Y') and replace(ASSI_PROD_NM,' ','') = replace(:plinm,' ')) \n" +
                "ORDER BY B.TABLET_UPSELL_SEQ,AMT  ",
        resultClass = ProductEntity.class,
        resultSetMapping = "AdditionalProductYedahamOneTwoListMapping")

@SqlResultSetMapping(
        name = "AdditionalProductYedahamOneTwoAllListMapping",
        classes = @ConstructorResult(
                targetClass = AdditionalProductList.class,
                columns = {
                        @ColumnResult(name = "refalph", type = String.class),
                        @ColumnResult(name = "refnum", type = String.class),
                        @ColumnResult(name = "cd", type = String.class),
                        @ColumnResult(name = "cdnm", type = String.class),
                        @ColumnResult(name = "plinm", type = String.class),
                        @ColumnResult(name = "commt", type = String.class),
                        @ColumnResult(name = "qty", type = String.class),
                        @ColumnResult(name = "amt", type = String.class),
                })
)


@NamedNativeQuery(name = "findAdditionalProductYedahamOneTwoAllList",
        query = " SELECT \n" +
                "A.REF_ALPH AS refalph,\n" +
                "A.REF_NUM AS refnum,\n" +
                "A.CD,\n" +
                "A.CD_NM AS CDNM,\n" +
                "replace(B.PLI_NM, '/', '') AS plinm,\n" +
                "B.COMMT,\n" +
                "B.QTY,\n" +
                "NVL(B.AMT, '0') AS AMT, \n" +
                "  '1' AS STATE, \n" +
                "  'N' AS CREAT_YN \n" +
                //"  FU04.QTY AS FU04_QTY, \n" +
                //"  FU04.AMT AS FU04_AMT \n" +
                "FROM TBCM1012 A \n" +
                "LEFT JOIN (\n" +
                "SELECT ASSI_PROD_CD, ASSI_PROD_NM AS PLI_NM,'' AS COMMT, '1' AS QTY, AMT,  SUBSTR(TABLET_PLI_GCD,3,2) AS PRODGB,TABLET_UPSELL_SEQ, SELECTGB  from  TBPR1002 \n" +
                "WHERE PROD_MAIN_CD=(SELECT PROD_MAIN_CD FROM TBNB1007 WHERE CERT_NO=:certno)  -- 상품코드\n" +
                "AND ASSI_PROD_CD LIKE '6%') \n" +
                "B ON A.CD = B.PRODGB\n" +
                "  LEFT OUTER JOIN TBFU1004 FU04 ON FU04.ASSI_PROD_CD = B.ASSI_PROD_CD \n" +
                "WHERE 1=1\n" +
                "AND TYPE_CD='TABLET_CODE'\n" +
                "AND (REF_NUM IN ('10','22','23') OR USE_YN IN ('Y','1'))\n" +
                "AND B.SELECTGB ='Y'\n" +
                "AND PLI_NM IS NOT NULL\n" +
                "ORDER BY B.TABLET_UPSELL_SEQ,AMT ",
        resultClass = ProductEntity.class,
        resultSetMapping = "AdditionalProductYedahamOneTwoAllListMapping")

@SqlResultSetMapping(
        name = "AdditionalDusanProductListMapping",
        classes = @ConstructorResult(
                targetClass = AdditionalProductList.class,
                columns = {
                        @ColumnResult(name = "assi_prod_cd", type = String.class),
                        @ColumnResult(name = "main_gb", type = String.class),
                        @ColumnResult(name = "refalph", type = String.class),
                        @ColumnResult(name = "refnum", type = String.class),
                        @ColumnResult(name = "cd", type = String.class),
                        @ColumnResult(name = "cdnm", type = String.class),
                        @ColumnResult(name = "plinm", type = String.class),
                        @ColumnResult(name = "commt", type = String.class),
                        @ColumnResult(name = "qty", type = String.class),
                        @ColumnResult(name = "cont_qty", type = String.class),
                        @ColumnResult(name = "amt", type = String.class),
                        @ColumnResult(name = "state", type = String.class),
                        @ColumnResult(name = "creat_yn", type = String.class),
                })
)


@NamedNativeQuery(name = "findAdditionalDusanProductList",
        query = "SELECT \n" +
                "  B.ASSI_PROD_CD, \n" +
                "  'A1' AS MAIN_GB, \n" +
                "  A.REF_ALPH AS refalph,\n" +
                "  A.REF_NUM AS refnum,\n" +
                "  A.CD,\n" +
                "  A.CD_NM AS CDNM,\n" +
                "  replace(B.PLI_NM, '/', '') AS plinm,\n" +
                "  B.COMMT,\n" +
                "  B.QTY,\n" +
                "  B.QTY AS CONT_QTY, \n" +
                "  NVL(B.AMT, '0') AS AMT,\n" +
                "  '1' AS STATE, \n" +
                "  'N' AS CREAT_YN \n" +
                "FROM TBCM1012 A \n" +
                "  LEFT JOIN (\n" +
                "            SELECT ASSI_PROD_CD, ASSI_PROD_NM AS PLI_NM,'' AS COMMT, '1' AS QTY, AMT,  SUBSTR(TABLET_PLI_GCD,3,2) AS PRODGB,TABLET_UPSELL_SEQ, SELECTGB \n" +
                "            from  TBPR1002 \n" +
                "            WHERE PROD_MAIN_CD= (SELECT PROD_MAIN_CD FROM TBNB1007 WHERE CERT_NO=:certno)\n" +
                "              AND MAIN_GB IN('A1')\n" +
                "              AND USE_YN IN ('1', '0') \n" +
                "              AND (SELECT TO_CHAR(REG_DATE,'YYYYMMDD') FROM TBFU1001 WHERE CERT_NO =:certno and STATUS <> 4) BETWEEN START_DATE AND END_DATE \n" +
                "           ) B ON A.CD = B.PRODGB\n" +
                "WHERE 1=1\n" +
                "  AND TYPE_CD='TABLET_CODE'\n" +
                "  AND ((A.REF_NUM IN ('10','22','23') and :amt = '-1') or A.USE_YN IN ('Y'))\n" +
                "  AND B.SELECTGB ='Y'\n" +
                "  AND REF_NUM=:grpcode\n" +
                "  AND PLI_NM IS NOT NULL\n" +
                "  AND B.AMT > :amt \n" +
                "  AND CD_NM like :cdnm \n" +
                "  AND REPLACE(B.PLI_NM, ' ','') NOT LIKE REPLACE(:plinm,' ','') \n" +
                "  and   pli_nm not like '%' || decode(:amt,-1,'!!!!!!!!','추가용') || '%' \n" +
                "ORDER BY B.TABLET_UPSELL_SEQ,AMT ",
        resultClass = ProductEntity.class,
        resultSetMapping = "AdditionalDusanProductListMapping")

@SqlResultSetMapping(
        name = "AdditionalDusanProductAllListMapping",
        classes = @ConstructorResult(
                targetClass = AdditionalProductList.class,
                columns = {
                        @ColumnResult(name = "assi_prod_cd", type = String.class),
                        @ColumnResult(name = "main_gb", type = String.class),
                        @ColumnResult(name = "refalph", type = String.class),
                        @ColumnResult(name = "refnum", type = String.class),
                        @ColumnResult(name = "cd", type = String.class),
                        @ColumnResult(name = "cdnm", type = String.class),
                        @ColumnResult(name = "plinm", type = String.class),
                        @ColumnResult(name = "commt", type = String.class),
                        @ColumnResult(name = "qty", type = String.class),
                        @ColumnResult(name = "amt", type = String.class),
                })
)


@NamedNativeQuery(name = "findAdditionalDusanProductAllList",
        query = " SELECT \n" +
                "  B.ASSI_PROD_CD, \n" +
                "  'A1' AS MAIN_GB, \n" +
                "  A.REF_ALPH AS refalph,\n" +
                "  A.REF_NUM AS refnum,\n" +
                "  A.CD,\n" +
                "  A.CD_NM AS CDNM,\n" +
                "  replace(B.PLI_NM, '/', '') AS plinm,\n" +
                "  B.COMMT,\n" +
                "  B.QTY,\n" +
                "  NVL(B.AMT, '0') AS AMT,\n" +
                "  '1' AS STATE, \n" +
                "  'N' AS CREAT_YN \n" +
               // "  FU04.QTY AS FU04_QTY, \n" +
               // "  FU04.AMT AS FU04_AMT \n" +
                "FROM TBCM1012 A \n" +
                "LEFT JOIN (\n" +
                "SELECT ASSI_PROD_CD, ASSI_PROD_NM AS PLI_NM,'' AS COMMT, '1' AS QTY, AMT,  SUBSTR(TABLET_PLI_GCD,3,2) AS PRODGB,TABLET_UPSELL_SEQ, SELECTGB  from  TBPR1002 \n" +
                "WHERE PROD_MAIN_CD= (SELECT PROD_MAIN_CD FROM TBNB1007 WHERE CERT_NO=:certno)\n" +
                "  AND MAIN_GB IN('A1')\n" +
                "  \n" +
                "  AND END_DATE='99991231'\n" +
                "  AND USE_YN IN ('1', '0')) \n" +
                "B ON A.CD = B.PRODGB\n" +
                "  LEFT OUTER JOIN TBFU1004 FU04 ON FU04.ASSI_PROD_CD = B.ASSI_PROD_CD \n" +
                "WHERE 1=1\n" +
                "AND TYPE_CD='TABLET_CODE'\n" +
                "AND (REF_NUM IN ('10','22','23') OR USE_YN='Y')\n" +
                "AND B.SELECTGB ='Y'\n" +
                "AND PLI_NM IS NOT NULL\n" +
                "ORDER BY B.TABLET_UPSELL_SEQ,A.SEQ,AMT",
        resultClass = ProductEntity.class,
        resultSetMapping = "AdditionalDusanProductAllListMapping")

@SqlResultSetMapping(
        name = "InitAdditionalDusanProductListMapping",
        classes = @ConstructorResult(
                targetClass = AdditionalProductList.class,
                columns = {
                        @ColumnResult(name = "assi_prod_cd", type = String.class),
                        @ColumnResult(name = "main_gb", type = String.class),
                        @ColumnResult(name = "refalph", type = String.class),
                        @ColumnResult(name = "refnum", type = String.class),
                        @ColumnResult(name = "cd", type = String.class),
                        @ColumnResult(name = "cdnm", type = String.class),
                        @ColumnResult(name = "plinm", type = String.class),
                        @ColumnResult(name = "commt", type = String.class),
                        @ColumnResult(name = "qty", type = String.class),
                        @ColumnResult(name = "cont_qty", type = String.class),
                        @ColumnResult(name = "amt", type = String.class),
                        @ColumnResult(name = "state", type = String.class),
                        @ColumnResult(name = "creat_yn", type = String.class),
                })
)


@NamedNativeQuery(name = "findInitAdditionalDusanProductList",
        query = "SELECT \n" +
                "  B.ASSI_PROD_CD, \n" +
                "  'A1' AS MAIN_GB, \n" +
                "  A.REF_ALPH AS refalph,\n" +
                "  A.REF_NUM AS refnum,\n" +
                "  A.CD,\n" +
                "  A.CD_NM AS CDNM,\n" +
                "  replace(B.PLI_NM, '/', '') AS plinm,\n" +
                "  B.COMMT,\n" +
                "  FU04.QTY,\n" +
                "  B.QTY AS CONT_QTY, \n" +
                "  NVL(B.AMT, '0') AS AMT,\n" +
                "  '1' AS STATE, \n" +
                "  'N' AS CREAT_YN \n" +
                "FROM TBCM1012 A \n" +
                "  INNER JOIN (\n" +
                "            SELECT ASSI_PROD_CD, ASSI_PROD_NM AS PLI_NM,'' AS COMMT, '1' AS QTY, AMT,  SUBSTR(TABLET_PLI_GCD,3,2) AS PRODGB,TABLET_UPSELL_SEQ, SELECTGB \n" +
                "            from  TBPR1002 \n" +
                "            WHERE PROD_MAIN_CD= (SELECT PROD_MAIN_CD FROM TBNB1007 WHERE CERT_NO=:certno)\n" +
                "              AND MAIN_GB IN('A1')\n" +
                "              AND USE_YN IN ('1', '0') \n" +
                "              AND (SELECT TO_CHAR(REG_DATE,'YYYYMMDD') FROM TBFU1001 WHERE FUN_CTRL_NO =:functrlno and STATUS <> 4) BETWEEN START_DATE AND END_DATE \n" +
                "           ) B ON A.CD = B.PRODGB\n" +
                "  INNER JOIN TBFU1001 FU01 ON FU01.FUN_CTRL_NO = :functrlno \n" +
                "  INNER JOIN TBFU1004 FU04 ON FU04.FUN_CTRL_NO = FU01.FUN_CTRL_NO AND FU04.ASSI_PROD_CD = B.ASSI_PROD_CD \n" +
                "WHERE 1=1\n" +
                "  AND TYPE_CD='TABLET_CODE'\n" +
                "  AND (A.REF_NUM IN ('10','22','23') or A.USE_YN IN ('Y'))\n" +
                "  AND B.SELECTGB ='Y'\n" +
                "  AND PLI_NM IS NOT NULL\n" +
                "  AND B.AMT > -1 \n" +
                "ORDER BY B.TABLET_UPSELL_SEQ, B.AMT ",
        resultClass = ProductEntity.class,
        resultSetMapping = "InitAdditionalDusanProductListMapping")


@SqlResultSetMapping(
        name = "AdditionalNewProductListMapping",
        classes = @ConstructorResult(
                targetClass = AdditionalProductList.class,
                columns = {
                        @ColumnResult(name = "assi_prod_cd", type = String.class),
                        @ColumnResult(name = "main_gb", type = String.class),
                        @ColumnResult(name = "refalph", type = String.class),
                        @ColumnResult(name = "refnum", type = String.class),
                        @ColumnResult(name = "cd", type = String.class),
                        @ColumnResult(name = "cdnm", type = String.class),
                        @ColumnResult(name = "plinm", type = String.class),
                        @ColumnResult(name = "commt", type = String.class),
                        @ColumnResult(name = "qty", type = String.class),
                        @ColumnResult(name = "cont_qty", type = String.class),
                        @ColumnResult(name = "amt", type = String.class),
                        @ColumnResult(name = "state", type = String.class),
                        @ColumnResult(name = "creat_yn", type = String.class),
                })
)


@NamedNativeQuery(name = "findAdditionalNewProductList",
        query = "select \n" +
                "       assi_prod_cd,\n" +
                "       '41' main_gb,\n" +
                "       refalph,\n" +
                "       refnum,\n" +
                "       cd,\n" +
                "       cdnm,\n" +
                "       plinm,\n" +
                "       commt,\n" +
                "       qty,\n" +
                "       cont_qty, \n" +
                "       amt, \n" +
                "       state, \n" +
                "       creat_yn \n" +
                "from(\n" +
                "      SELECT\n" +
                "      B.PLICD AS assi_prod_cd,\n" +
                "      A.REF_ALPH AS refalph,\n" +
                "      A.REF_NUM AS refnum,\n" +
                "      A.CD,\n" +
                "      A.CD_NM AS CDNM,\n" +
                "      replace(B.PLI_NM, '/', '') AS plinm,\n" +
                "      B.COMMT,\n" +
                "      B.QTY,\n" +
                "      B.QTY AS CONT_QTY,\n" +
                "      NVL(B.AMT, '0') AS AMT,\n" +
                "       B.TABLET_UPSELL_SEQ,\n" +
                "      decode(:amt,-1,\n" +
                "                   RANK() OVER (PARTITION BY replace(replace(B.PLI_NM, '/', ''),'(일반)','') ORDER BY NVL(B.AMT, '0') DESC),\n" +
                "                   RANK() OVER (PARTITION BY replace(replace(B.PLI_NM, '/', ''),'(일반)','') ORDER BY NVL(B.AMT, '0') )\n" +
                "      ) add_type, \n" +
                "        '1' AS STATE, \n" +
                "        'N' AS CREAT_YN \n" +
                "      FROM TBCM1012 A\n" +
                "      LEFT JOIN (\n" +
                "                SELECT\n" +
                "                B.PLICD, a.pli_dcd, B.PLI_NM,B.COMMT, A.QTY, B.UNIT_AMT AS AMT, B.UNIT_AMT  , SUBSTR(TABLET_PLI_GCD,3,2) AS PRODGB,\n" +
                "                B.TABLET_UPSELL_SEQ, SELECTGB\n" +
                "                FROM TBPR1006 A RIGHT JOIN TBPR1007 B ON A.PLICD = B.PLICD\n" +
                "                WHERE A.PRIM_PLICD = (SELECT PROD_MAIN_CD FROM TBNB1007 WHERE CERT_NO=:certno)\n" +
                "                AND  A.PLI_DCD='41'\n" +
                "                AND A.USE_YN IN ('Y','1')\n" +
                "                AND (SELECT TO_CHAR(REG_DATE,'YYYYMMDD') FROM TBFU1001 WHERE CERT_NO =:certno and STATUS <> 4) BETWEEN A.STDT AND A.EDDT\n" +
                "      )B ON A.CD =  B.PRODGB\n" +
                "        LEFT OUTER JOIN TBFU1001 FU01 ON FU01.CERT_NO = :certno \n" +
                "      WHERE 1=1\n" +
                "        AND NOT EXISTS(SELECT 1 FROM TBFU1004 FU04 WHERE FU04.FUN_CTRL_NO = FU01.FUN_CTRL_NO AND FU04.ASSI_PROD_CD = B.PLICD) \n" +
                "        AND TYPE_CD='TABLET_CODE'\n" +
                "        AND ((A.REF_NUM IN ('10','22','23') and :amt = '-1') or USE_YN IN ('Y'))\n" +
                "        AND B.SELECTGB ='Y'\n" +
                "        AND REF_NUM = NVL(:grpcode, REF_NUM) \n" +
                "        AND B.PLI_NM IS NOT NULL\n" +
                "        AND CD_NM like :cdnm\n" +
                "        AND replace(REPLACE(B.PLI_NM,'/',''),' ','') <> DECODE(:plinm3,'','!!!!',(SELECT DECODE(:plinm3,:plinm1,REPLACE(SUBSTR(PLI_NM,INSTR(PLI_NM,:plinm1),INSTR(PLI_NM,'또는') -1),' ',''),DECODE(:plinm3,:plinm2,REPLACE(SUBSTR(PLI_NM,INSTR(PLI_NM,:plinm2),LENGTH(PLI_NM)),' ',''),replace(PLI_NM,' ','')) ) FROM TBPR1007 WHERE PLICD =:plicd) )\n" +
                "        AND decode(b.pli_dcd,'11',9999999,B.AMT) > DECODE( (SELECT UNIT_AMT FROM TBPR1007 WHERE PLICD =:plicd),'',0,(SELECT UNIT_AMT FROM TBPR1007 WHERE PLICD =:plicd))  )\n" +
                "where add_type = 1\n" +
                "and   plinm not like '%' || decode(:amt,-1,'!!!!!!!!','추가용') || '%' \n" +
                "ORDER BY TABLET_UPSELL_SEQ,AMT\n",
        resultClass = ProductEntity.class,
        resultSetMapping = "AdditionalNewProductListMapping")

@SqlResultSetMapping(
        name = "InitAdditionalNewProductListMapping",
        classes = @ConstructorResult(
                targetClass = AdditionalProductList.class,
                columns = {
                        @ColumnResult(name = "assi_prod_cd", type = String.class),
                        @ColumnResult(name = "main_gb", type = String.class),
                        @ColumnResult(name = "refalph", type = String.class),
                        @ColumnResult(name = "refnum", type = String.class),
                        @ColumnResult(name = "cd", type = String.class),
                        @ColumnResult(name = "cdnm", type = String.class),
                        @ColumnResult(name = "plinm", type = String.class),
                        @ColumnResult(name = "commt", type = String.class),
                        @ColumnResult(name = "qty", type = String.class),
                        @ColumnResult(name = "cont_qty", type = String.class),
                        @ColumnResult(name = "amt", type = String.class),
                        @ColumnResult(name = "state", type = String.class),
                        @ColumnResult(name = "creat_yn", type = String.class)
                })
)


@NamedNativeQuery(name = "findInitAdditionalNewProductList",
        query = "select \n" +
                "       assi_prod_cd," +
                "       '41' main_gb,\n" +
                "       refalph,\n" +
                "       refnum,\n" +
                "       cd,\n" +
                "       cdnm,\n" +
                "       plinm,\n" +
                "       commt,\n" +
                "       qty,\n" +
                "       cont_qty, \n" +
                "       amt, \n" +
                "       state, \n" +
                "       creat_yn \n" +
                "from(\n" +
                "    SELECT\n" +
                "      B.PLICD AS assi_prod_cd,\n" +
                "      A.REF_ALPH AS refalph,\n" +
                "      A.REF_NUM AS refnum,\n" +
                "      A.CD,\n" +
                "      A.CD_NM AS CDNM,\n" +
                "      replace(B.PLI_NM, '/', '') AS plinm,\n" +
                "      B.COMMT,\n" +
                "      FU04.QTY AS QTY,\n" +
                "      B.QTY AS CONT_QTY,\n" +
                "      NVL(B.AMT, '0') AS AMT,\n" +
                "       B.TABLET_UPSELL_SEQ,\n" +
                "        '1' AS STATE, \n" +
                "        'N' AS CREAT_YN \n" +
                "    FROM \n" +
                "      TBCM1012 A\n" +
                "      INNER JOIN (\n" +
                "                SELECT\n" +
                "                B.PLICD, a.pli_dcd, B.PLI_NM,B.COMMT, A.QTY, B.UNIT_AMT AS AMT, B.UNIT_AMT  , SUBSTR(TABLET_PLI_GCD,3,2) AS PRODGB,\n" +
                "                B.TABLET_UPSELL_SEQ, SELECTGB\n" +
                "                FROM TBPR1006 A RIGHT JOIN TBPR1007 B ON A.PLICD = B.PLICD\n" +
                "                WHERE A.PRIM_PLICD = (SELECT PROD_MAIN_CD FROM TBNB1007 WHERE CERT_NO=:certno)\n" +
                "                AND  A.PLI_DCD='41'\n" +
                "                AND A.USE_YN IN ('Y','1')\n" +
                "                AND (SELECT TO_CHAR(REG_DATE,'YYYYMMDD') FROM TBFU1001 WHERE FUN_CTRL_NO =:functrlno and STATUS <> 4) BETWEEN A.STDT AND A.EDDT\n" +
                "      )B ON A.CD =  B.PRODGB\n" +
                "        INNER JOIN TBFU1001 FU01 ON FU01.FUN_CTRL_NO = :functrlno \n" +
                "        INNER JOIN TBFU1004 FU04 ON FU04.FUN_CTRL_NO = FU01.FUN_CTRL_NO AND FU04.ASSI_PROD_CD = B.PLICD \n" +
                "    WHERE 1=1\n" +
                "      AND TYPE_CD='TABLET_CODE'\n" +
                "      AND (A.REF_NUM IN ('10','22','23')  or USE_YN IN ('Y'))\n" +
                "      AND B.SELECTGB ='Y'\n" +
                "      AND B.PLI_NM IS NOT NULL\n" +
                " ) \n" +
                "where 1 = 1\n" +
                "ORDER BY TABLET_UPSELL_SEQ, B.AMT\n",
        resultClass = ProductEntity.class,
        resultSetMapping = "InitAdditionalNewProductListMapping")

@SqlResultSetMapping(
        name = "AdditionalNewProductAllListMapping",
        classes = @ConstructorResult(
                targetClass = AdditionalProductList.class,
                columns = {
                        @ColumnResult(name = "assi_prod_cd", type = String.class),
                        @ColumnResult(name = "main_gb", type = String.class),
                        @ColumnResult(name = "refalph", type = String.class),
                        @ColumnResult(name = "refnum", type = String.class),
                        @ColumnResult(name = "cd", type = String.class),
                        @ColumnResult(name = "cdnm", type = String.class),
                        @ColumnResult(name = "plinm", type = String.class),
                        @ColumnResult(name = "commt", type = String.class),
                        @ColumnResult(name = "qty", type = String.class),
                        @ColumnResult(name = "cont_qty", type = String.class),
                        @ColumnResult(name = "amt", type = String.class),
                        @ColumnResult(name = "state", type = String.class),
                        @ColumnResult(name = "creat_yn", type = String.class),
                })
)


@NamedNativeQuery(name = "findAdditionalNewProductAllList",
        query = " SELECT \n" +
                "B.PLICD AS ASSI_PROD_CD," +
                "'41' AS MAIN_GB, \n" +
                "A.REF_ALPH AS refalph,\n" +
                "A.REF_NUM AS refnum,\n" +
                "A.CD,\n" +
                "A.CD_NM AS CDNM,\n" +
                "replace(B.PLI_NM, '/', '') AS plinm,\n" +
                "B.COMMT,\n" +
                "NVL(FU04.QTY, B.QTY) QTY,\n" +
                "B.QTY AS CONT_QTY, \n" +
                "NVL(B.AMT, '0') AS AMT,\n" +
                "'1' AS STATE, \n" +
                "'N' AS CREAT_YN \n" +
                //"FU04.QTY AS FU04_QTY, \n" +
                //"FU04.AMT AS FU04_AMT \n" +
                "FROM TBCM1012 A \n" +
                "LEFT JOIN (\n" +
                "SELECT\n" +
                "A.PLICD, B.PLI_NM,B.COMMT, A.QTY, B.UNIT_AMT AS AMT, B.UNIT_AMT  , SUBSTR(TABLET_PLI_GCD,3,2) AS PRODGB,\n" +
                "B.TABLET_UPSELL_SEQ, SELECTGB\n" +
                "FROM TBPR1006 A RIGHT JOIN TBPR1007 B ON A.PLICD = B.PLICD\n" +
                "WHERE A.PRIM_PLICD = (SELECT PROD_MAIN_CD FROM TBNB1007 WHERE CERT_NO=:certno)\n" +
                "AND (B.PLICD IN('2000010','2000018','2000006','2000036') OR A.PLI_DCD='41')\n" +
                "AND A.USE_YN IN ('Y','1')\n" +
                "UNION\n" +
                "SELECT\n" +
                "B.PLICD, B.PLI_NM,B.COMMT, 1 AS QTY, B.UNIT_AMT AS AMT, B.UNIT_AMT  , SUBSTR(TABLET_PLI_GCD,3,2) AS PRODGB,\n" +
                "B.TABLET_UPSELL_SEQ, SELECTGB\n" +
                "FROM   TBPR1007 B\n" +
                "WHERE  1=1\n" +
                "AND B.PLICD IN('2000010','2000018','2000006','2000036') \n" +
                ")B ON A.CD =  B.PRODGB\n" +
                "LEFT OUTER JOIN TBFU1001 FU01 ON FU01.CERT_NO = :certno \n" +
                "LEFT OUTER JOIN TBFU1004 FU04 ON FU04.FUN_CTRL_NO = FU01.FUN_CTRL_NO AND FU04.ASSI_PROD_CD = B.PLICD \n" +
                "WHERE 1=1\n" +
                "AND TYPE_CD='TABLET_CODE'\n" +
                "AND (REF_NUM IN ('10','22','23') OR USE_YN='Y')\n" +
                "AND B.SELECTGB ='Y'\n" +
                "AND PLI_NM IS NOT NULL\n" +
                "ORDER BY B.TABLET_UPSELL_SEQ,AMT ",
        resultClass = ProductEntity.class,
        resultSetMapping = "AdditionalNewProductAllListMapping")

@Data
@Entity
@Table(name="TBCS1003", schema = "tk_fsdev")
public class AdditionalProductEntity {
    @Id
    private String custId;
}
