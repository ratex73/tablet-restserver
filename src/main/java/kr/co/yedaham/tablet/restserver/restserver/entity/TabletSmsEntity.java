package kr.co.yedaham.tablet.restserver.restserver.entity;

import kr.co.yedaham.tablet.restserver.restserver.model.sms.FunSmsEnd;
import kr.co.yedaham.tablet.restserver.restserver.model.sms.FunSmsPhone;
import kr.co.yedaham.tablet.restserver.restserver.model.sms.FunSmsStart;
import lombok.Data;
import org.hibernate.annotations.CreationTimestamp;

import javax.persistence.*;
import java.time.LocalDate;
import java.time.LocalDateTime;


@SqlResultSetMapping(
        name = "FunSmsStartMapping",
        classes = @ConstructorResult(
                targetClass = FunSmsStart.class,
                columns = {
                        @ColumnResult(name = "EMPNM", type = String.class),
                        @ColumnResult(name = "FUN_CTRL_NO", type = String.class),
                        @ColumnResult(name = "DEAD_NM", type = String.class),
                        @ColumnResult(name = "S_PROD_DNM", type = String.class),
                        @ColumnResult(name = "FUN_NM", type = String.class),
                        @ColumnResult(name = "CASK_DATETIME", type = String.class),
                        @ColumnResult(name = "CORT_DATETIME", type = String.class),
                        @ColumnResult(name = "CREMA_BIZ_NM", type = String.class),
                        @ColumnResult(name = "CONST_RESER_DATETIME", type = String.class),
                        @ColumnResult(name = "CONST_BIZ_NM", type = String.class),
                        @ColumnResult(name = "BUS_USE_NM", type = String.class),
                        @ColumnResult(name = "LIM_USE_NM", type = String.class)
                })
)
@NamedNativeQuery(name = "findFunSmsStart",
        query = " SELECT                                                                                                                                                           \n" +
                "     BM11.EMPNM  --담당팀장                                                                                                                                       \n" +
                "   , FU01.FUN_CTRL_NO --의전번호                                                                                                                                  \n" +
                "   , FU01.DEAD_NM	--고인                                                                                                                                         \n" +
                "   , PR01.S_PROD_DNM  --상품명                                                                                                                                    \n" +
                "   , CM05.FUN_NM --장소(장례식장명)                                                                                                                               \n" +
                "   , DECODE( FU01.CASK_DATE, NULL, '', SUBSTR(FU01.CASK_DATE, 1, 4) || '년 ' || SUBSTR(FU01.CASK_DATE, 5, 2) || '월 ' || SUBSTR(FU01.CASK_DATE, 7, 2) || '일 ')   \n" +
                "     || DECODE(FU01.CASK_TIME, NULL, '', FU01.CASK_TIME || '시 ' || FU01.CASK_MIN || '분' ) AS CASK_DATETIME --입관일시                                          \n" +
                "   , DECODE( FU01.CORT_DATE, NULL, '', SUBSTR(FU01.CORT_DATE, 1, 4) || '년 ' || SUBSTR(FU01.CORT_DATE, 5, 2) || '월 ' || SUBSTR(FU01.CORT_DATE, 7, 2) || '일 ')   \n" +
                "     || DECODE( FU01.CORT_TIME, NULL, '', FU01.CORT_TIME || '시 ' || FU01.CORT_MIN || '분' ) AS CORT_DATETIME --발인일시                                                                              \n" +
                "   , CM05_H.FUN_NM AS CREMA_BIZ_NM	--화장장                                                                                                                       \n" +
                "   , '날짜:' || DECODE(FU01.RESER_DATE, NULL, '', FU01.RESER_DATE)	|| ', 시간:' || DECODE( FU01.RESER_TIME, NULL, '', FU01.RESER_TIME	|| '시 ' || FU01.RESER_MIN || '분' ) AS CONST_RESER_DATETIME--화장장 예약일시                      \n" +
                "   , CM05_J.FUN_NM AS CONST_BIZ_NM -- 장지                                                                                                                        \n" +
                "   , DECODE(FU01.BUS_USE_YN, 'Y', '사용', 'N', '사용안함', '')	AS BUS_USE_NM --버스사용여부                                                                       \n" +
                "   , DECODE(FU01.LIM_USE_YN, 'Y', '사용', 'N', '사용안함', '')	AS LIM_USE_NM 	--리무진사용여부                                                                   \n" +
                " FROM                                                                                                                                                             \n" +
                "     TBFU1001 FU01 INNER JOIN TBNB1007 NB07 ON FU01.CERT_NO = NB07.CERT_NO                                                                                        \n" +
                "     INNER JOIN TBPR1001 PR01 ON NB07.PROD_MAIN_CD = PR01.PROD_MAIN_CD                                                                                            \n" +
                "     LEFT OUTER JOIN TBBM1011 BM11 ON FU01.LEADER_ID = BM11.EMPNO AND USE_YN = '0' AND DEL_YN = '0'                                                               \n" +
                "     LEFT OUTER JOIN TBCM1005 CM05 ON FU01.FUN_CD = CM05.FUN_CD                                                                                                   \n" +
                "     LEFT OUTER JOIN TBCM1005 CM05_H ON FU01.CREMA_BIZ_CD = CM05_H.FUN_CD                                                                                         \n" +
                "     LEFT OUTER JOIN TBCM1005 CM05_J ON FU01.CONST_BIZ_CD = CM05_J.FUN_CD                                                                                         \n" +
                " WHERE                                                                                                                                                            \n" +
                "   FU01.FUN_CTRL_NO = :functrlno                                                                                                                                 \n" ,
        resultClass = FunSmsStart.class,
        resultSetMapping = "FunSmsStartMapping")

@SqlResultSetMapping(
        name = "FunSmsEndMapping",
        classes = @ConstructorResult(
                targetClass = FunSmsEnd.class,
                columns = {
                        @ColumnResult(name = "EMPNM", type = String.class),
                        @ColumnResult(name = "DEAD_NM", type = String.class),
                        @ColumnResult(name = "FUN_NM", type = String.class),
                })
)
@NamedNativeQuery(name = "findFunSmsEnd",
        query = "SELECT B.EMPNM, A.DEAD_NM, C.FUN_NM FROM TBFU1001 A\n" +
                "LEFT JOIN TBBM1011 B\n" +
                "ON A.LEADER_ID = B.EMPNO\n" +
                "LEFT JOIN TBCM1005 C\n" +
                "ON A.FUN_CD = C.FUN_CD\n" +
                "WHERE B.USE_YN = 0 AND B.DEL_YN =0 AND C.USE_YN = 'Y' AND A.FUN_CTRL_NO = :functrlno ",
        resultClass = FunSmsEnd.class,
        resultSetMapping = "FunSmsEndMapping")

@SqlResultSetMapping(
        name = "FunSendSmsMapping",
        classes = @ConstructorResult(
                targetClass = FunSmsPhone.class,
                columns = {
                        @ColumnResult(name = "EMPNO", type = String.class),
                        @ColumnResult(name = "EMPNM", type = String.class),
                        @ColumnResult(name = "CELL_PHONE", type = String.class),
                })
)
@NamedNativeQuery(name = "findFunSendSms",
        query = " SELECT EMPNO, EMPNM, WLNO1||WLNO2||WLNO3 AS CELL_PHONE FROM TBFU1001 A LEFT JOIN TBBM1011  B \n" +
                " ON  A.LEADER_ID = B.EMPNO AND B.USE_YN='0' AND B.DEL_YN='0'\n" +
                " WHERE A.FUN_CTRL_NO=:functrlno",
        resultClass = TabletSmsEntity.class,
        resultSetMapping = "FunSendSmsMapping")

@Data
@Entity
@Table(name="TABLET_SMS")
public class TabletSmsEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "SMS_SEQ")
    @SequenceGenerator(sequenceName = "TABLET_SMS_SEQ", allocationSize = 1, name = "SMS_SEQ")
    private Integer noSeq;
    private String filename;
    private String cellPhone;
    @CreationTimestamp
    private LocalDate regDate;
    private char sendYn;
    private LocalDate sendDate;
    @CreationTimestamp
    private LocalDateTime regDateTime;
}