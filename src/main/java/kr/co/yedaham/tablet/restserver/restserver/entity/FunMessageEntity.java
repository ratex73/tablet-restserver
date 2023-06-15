package kr.co.yedaham.tablet.restserver.restserver.entity;



import kr.co.yedaham.tablet.restserver.restserver.model.funmessage.FunMessageInfo;
import kr.co.yedaham.tablet.restserver.restserver.model.funmessage.FunMessageInfos;
import lombok.*;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.DynamicUpdate;

import javax.persistence.*;


@SqlResultSetMapping(
        name = "getFunMessageListMapping",
        classes = @ConstructorResult(
                targetClass = FunMessageInfos.class,
                columns = {
                        @ColumnResult(name = "SEQ", type = long.class),
                        @ColumnResult(name = "FUN_CTRL_NO", type = String.class),
                        @ColumnResult(name = "REG_DATE", type = String.class),
                        @ColumnResult(name = "REG_TIME", type = String.class),
                        @ColumnResult(name = "UPDATE_DATE", type = String.class),
                        @ColumnResult(name = "UPDATE_TIME", type = String.class),
                        @ColumnResult(name = "MOUR_NM", type = String.class),
                        @ColumnResult(name = "REAT", type = String.class),
                        @ColumnResult(name = "CERT_NO", type = String.class),
                        @ColumnResult(name = "DEAD_NM", type = String.class),
                        @ColumnResult(name = "CEME", type = String.class),
                        @ColumnResult(name = "CHU_DUTY", type = String.class),
                        @ColumnResult(name = "SEX", type = String.class),
                        @ColumnResult(name = "DEAD_REA", type = String.class),
                        @ColumnResult(name = "DEAD_DATE", type = String.class),
                        @ColumnResult(name = "CASK_DATE", type = String.class),
                        @ColumnResult(name = "CORT_DATE", type = String.class),
                        @ColumnResult(name = "MEMO", type = String.class),
                        @ColumnResult(name = "MOUR_ACC_STATE", type = String.class),
                        @ColumnResult(name = "FUN_PLACE", type = String.class),
                        @ColumnResult(name = "FUN_TEL_NO", type = String.class),
                        @ColumnResult(name = "STATE", type = long.class),
                        @ColumnResult(name = "FUN_NM", type = String.class),
                        @ColumnResult(name = "CEME2", type = String.class),
                        @ColumnResult(name = "MOUR_PHONE", type = String.class),
                        @ColumnResult(name = "DEAD_AGE", type = long.class),
                        @ColumnResult(name = "SEND_YN", type = String.class),
                        @ColumnResult(name = "REPLY_YN", type = String.class),

                })
)






@NamedNativeQuery(name = "getFunMessageList",
        query = "SELECT FUN.SEQ AS SEQ,\n" +
                "       FUN.FUN_CTRL_NO AS FUN_CTRL_NO,\n" +
                "       FUN.REG_DATE AS REG_DATE,\n" +
                "       FUN.REG_TIME AS REG_TIME,\n" +
                "       FUN.UPDATE_DATE AS UPDATE_DATE,\n" +
                "       FUN.UPDATE_TIME AS UPDATE_TIME,\n" +
                "       FUN.MOUR_NM AS MOUR_NM,\n" +
                "       FUN.REAT AS REAT,\n" +
                "       FUN.CERT_NO AS CERT_NO,\n" +
                "       FUN.DEAD_NM AS DEAD_NM,\n" +
                "       FUN.CEME AS CEME,\n" +
                "       FUN.CHU_DUTY AS CHU_DUTY,\n" +
                "       FUN.SEX AS SEX,\n" +
                "       FUN.DEAD_REA AS DEAD_REA,\n" +
                "       FUN.DEAD_DATE AS DEAD_DATE,\n" +
                "       FUN.CASK_DATE AS CASK_DATE,\n" +
                "       FUN.CORT_DATE AS CORT_DATE,\n" +
                "       FUN.MEMO AS MEMO,\n" +
                "       FUN.MOUR_ACC_STATE AS MOUR_ACC_STATE,\n" +
                "       FUN.FUN_PLACE AS FUN_PLACE,\n" +
                "       CM05.TEL_NO1 AS FUN_TEL_NO,\n" +
                "       FUN.STATE AS STATE,\n" +
                "       CM05.FUN_NM AS FUN_NM,\n" +
                "       FUN.CEME2 AS CEME2,\n" +
                "       '' AS MOUR_PHONE,\n" +
                "       FUN.DEAD_AGE AS DEAD_AGE,\n" +
                "       FUN.SEND_YN AS SEND_YN,\n" +
                "       FUN.REPLY_YN AS REPLY_YN\n" +
                "FROM FUN_MMS_SD FUN LEFT JOIN TBFU1001 FU01\n" +
                "                           ON FUN.FUN_CTRL_NO = FU01.FUN_CTRL_NO\n" +
                "                    LEFT JOIN TBCM1005 CM05\n" +
                "                           ON FU01.FUN_CD = CM05.FUN_CD\n" +
                "WHERE FUN.FUN_CTRL_NO =:funCtrlNo\n" +
                "ORDER BY SEQ",
        resultClass = FunMessageEntity.class,
        resultSetMapping = "getFunMessageListMapping")









@DynamicUpdate
@Data
@Entity
@NoArgsConstructor
@Getter
@ToString
@Table(name="FUN_MMS_SD", schema = "tk_fsdev")
public class FunMessageEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "FUN_MMS_SD_SEQ")
    @SequenceGenerator(sequenceName = "FUN_MMS_SD_SEQ", allocationSize = 1, name = "FUN_MMS_SD_SEQ")
    @Column(name = "SEQ")
    private long seq;
    @Column(name = "FUN_CTRL_NO")
    private String funCtrlNo;
    @Column(name = "REG_DATE")
    private String regDate;
    @Column(name = "REG_TIME")
    private String regTime;
    @Column(name = "UPDATE_DATE")
    private String updateDate;
    @Column(name = "UPDATE_TIME")
    private String updateTime;
    @Column(name = "MOUR_NM")
    private String mourNm;
    @Column(name = "REAT")
    private String reat;
    @Column(name = "CERT_NO")
    private String certNo;
    @Column(name = "DEAD_NM")
    private String deadNm;
    @Column(name = "CEME")
    private String ceme;
    @Column(name = "FUN_NM")
    private String funNm;
    @Column(name = "CHU_DUTY")
    private String chuDuty;
    @Column(name = "SEX")
    private String sex;
    @Column(name = "DEAD_REA")
    private String deadRea;
    @Column(name = "DEAD_DATE")
    private String deadDate;
    @Column(name = "CASK_DATE")
    private String caskDate;
    @Column(name = "CORT_DATE")
    private String cortDate;
    @Column(name = "MEMO")
    private String memo;
    @Column(name = "MOUR_ACC_STATE")
    private String mourAccState;
    @Column(name = "FUN_PLACE")
    private String funPlace;
    @Column(name = "FUN_TEL_NO")
    private String funTelNo;
    @Column(name = "STATE")
    private long state;
    @Column(name = "CEME2")
    private String ceme2;

    @Column(name = "DEAD_AGE")
    private long deadAge;

    @Column(name = "SEND_YN")
    private String sendYn;

    @Column(name = "REPLY_YN")
    private String replyYn;

    @Column(name = "HIT_COUNT")
    private long hitCount;



    @Builder
    public FunMessageEntity(FunMessageInfo funMessageInfo) {
        this.seq = funMessageInfo.getSeq();
        this.funCtrlNo = funMessageInfo.getFunCtrlNo();
        this.regDate = funMessageInfo.getRegDate();
        this.regTime = funMessageInfo.getRegTime();
        this.updateDate = funMessageInfo.getUpdateDate();
        this.updateTime = funMessageInfo.getUpdateTime();
        this.mourNm = funMessageInfo.getMourNm();
        this.reat = funMessageInfo.getReat();
        this.certNo = funMessageInfo.getCertNo();
        this.deadNm = funMessageInfo.getDeadNm();
        this.ceme = funMessageInfo.getCeme();
        this.funNm = funMessageInfo.getFunNm();
        this.chuDuty = funMessageInfo.getChuDuty();
        this.sex = funMessageInfo.getSex();
        this.deadRea = funMessageInfo.getDeadRea();
        this.deadDate = funMessageInfo.getDeadDate();
        this.caskDate = funMessageInfo.getCaskDate();
        this.cortDate = funMessageInfo.getCortDate();
        this.memo = funMessageInfo.getMemo();
        this.mourAccState = funMessageInfo.getMourAccState();
        this.funPlace = funMessageInfo.getFunPlace();
        this.funTelNo = funMessageInfo.getFunTelNo();
        this.state = funMessageInfo.getState();
        this.ceme2 = funMessageInfo.getCeme2();
        this.deadAge = funMessageInfo.getDeadAge();
        this.sendYn = funMessageInfo.getSendYn();
        this.replyYn = funMessageInfo.getReplyYn();
        this.hitCount = funMessageInfo.getHitCount();
    }


}
