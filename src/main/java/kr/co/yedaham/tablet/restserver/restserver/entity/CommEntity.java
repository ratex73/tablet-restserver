package kr.co.yedaham.tablet.restserver.restserver.entity;

import kr.co.yedaham.tablet.restserver.restserver.model.comm.FunCoComList;
import lombok.Data;

import javax.persistence.*;

@SqlResultSetMapping(
        name = "FunCoComListMapping",
        classes = @ConstructorResult(
                targetClass = FunCoComList.class,
                columns = {
                        @ColumnResult(name = "FUN_CD", type = String.class),
                        @ColumnResult(name = "FUN_NM", type = String.class),
                        @ColumnResult(name = "ADDR", type = String.class),
                        @ColumnResult(name = "TEL_NO", type = String.class),
                        @ColumnResult(name = "FUN_GB_NM", type = String.class)
                })
)


@NamedNativeQuery(name = "findFunCoComList",
        query = " SELECT                                                                                         \n" +
                "   CM05.FUN_CD                                                                                  \n" +
                "   , CM05.FUN_NM                                                                                \n" +
                "   , CM05.ADDR1 || ' ' || CM05.ADDR2 AS ADDR                                                    \n" +
                "   , CM05.TEL_NO1 AS TEL_NO                                     \n" +
                "   , (SELECT CD_NM FROM TBCM1012 WHERE TYPE_CD = 'FUN_OP_COMP' AND CD = CM05.FUN_GB) AS FUN_GB_NM  \n" +
                " FROM                                                                                           \n" +
                "   (                                                                                            \n" +
                "     SELECT                                                                                     \n" +
                "       CM20_3.AREA_GB                                                                           \n" +
                "       , CM20_3.AREA_CD                                                                         \n" +
                "       , CM20_3.AREA_NM                                                                         \n" +
                "       , CM20_3.FROM_POST                                                                       \n" +
                "       , CM20_3.TO_POST                                                                         \n" +
                "     FROM                                                                                       \n" +
                "       TBCM1020 CM20_1                                                                          \n" +
                "       INNER JOIN TBCM1020 CM20_4 ON CM20_1.FROM_POST = CM20_4.AREA_CD                          \n" +
                "       INNER JOIN TBCM1020 CM20_3 ON CM20_1.AREA_NM = CM20_3.AREA_NM                            \n" +
                "       INNER JOIN TBCM1020 CM20_5 ON CM20_3.FROM_POST = CM20_5.AREA_CD                          \n" +
                "     WHERE                                                                                      \n" +
                "       CM20_1.AREA_GB = '4'                                                                     \n" +
                "       AND CM20_1.AREA_CD BETWEEN :frareacd AND :toareacd --시군구 AREA_CD (파라메터 입력값)        \n" +
                "       AND CM20_4.AREA_NM = CM20_5.AREA_NM                                                      \n" +
                "     GROUP BY                                                                                   \n" +
                "           CM20_3.AREA_GB                                                                       \n" +
                "       , CM20_3.AREA_CD                                                                         \n" +
                "       , CM20_3.AREA_NM                                                                         \n" +
                "       , CM20_3.FROM_POST                                                                       \n" +
                "       , CM20_3.TO_POST                                                                         \n" +
                "   ) TB_ZIPCODE                                                                                 \n" +
                "   INNER JOIN TBCM1005 CM05 ON TB_ZIPCODE.AREA_CD = SUBSTR(CM05.ZIP_CODE, 1, 3)                 \n" +
                " WHERE                                                                                          \n" +
                "   (                                                                                            \n" +
                "     (LENGTH(CM05.ZIP_CODE) = 6 AND TB_ZIPCODE.AREA_GB = 2) -- 2:우편번호 6자리용               \n" +
                "     OR (LENGTH(CM05.ZIP_CODE) = 5 AND TB_ZIPCODE.AREA_GB = 4) -- 4:우편번호 5자리용            \n" +
                "    ) AND CM05.FUN_GB = :fungb --업체구분코드 (파라메터 입력값)                                 \n" +
                " ORDER BY CM05.FUN_NM                                                                           \n"
        ,
        resultClass = CommEntity.class,
        resultSetMapping = "FunCoComListMapping")

@NamedNativeQuery(name = "findFunCoComListByName",
        query = " SELECT                                                                                                                                              \n" +
                "   CM05.FUN_CD                                                                                                                                       \n" +
                "   , CM05.FUN_NM                                                                                                                                     \n" +
                "   , CM05.ADDR1 || ' ' || CM05.ADDR2 AS ADDR                                                                                                         \n" +
                "   , CM05.TEL_NO1 AS TEL_NO                                                                                          \n" +
                "   , (SELECT CD_NM FROM TBCM1012 WHERE TYPE_CD = 'FUN_OP_COMP' AND CD = CM05.FUN_GB) AS FUN_GB_NM                                                    \n" +
                " FROM                                                                                                                                                \n" +
                "   TBCM1005 CM05                                                                                                                                     \n" +
                " WHERE 1=1                                                                                                                                           \n" +
                "   AND CM05.FUN_GB IN ('A', 'B', 'C', 'D', 'E')                                                                                                      \n" +
                "   AND CM05.FUN_NM LIKE '%' || :funnm || '%'  --업체명 (파라메터 입력값)                                                                     \n" +
                "   AND CM05.ADDR1 || ' ' || CM05.ADDR2 LIKE '%' || :addr || '%'  --주소 (파라메터 입력값)                                                     \n" +
                "   AND REPLACE(CM05.TEL_NO1 || CM05.TEL_NO2 || CM05.TEL_NO3, '-', '') LIKE REPLACE(:telno, '-', '') || '%'  --전화번호 (파라메터 입력값)  \n"
        ,
        resultClass = CommEntity.class,
        resultSetMapping = "FunCoComListMapping")

@Data
@Entity
@Table(name="TBCM1005", schema = "tk_fsdev")
public class CommEntity {
    @Id
    private String funCd;
}
