package kr.co.yedaham.tablet.restserver.restserver.entity;


import kr.co.yedaham.tablet.restserver.restserver.model.funmessage.FunMessageInfo;
import lombok.*;
import org.hibernate.annotations.DynamicUpdate;

import javax.persistence.*;
import java.time.LocalDateTime;

@DynamicUpdate
@Data
@Entity
@NoArgsConstructor
@Getter
@ToString
@Table(name="FUN_MMS_SD_HISTORY", schema = "tk_fsdev")
public class FunMessageHistoryEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "FUN_MMS_SD_HISTORY_SEQ")
    @SequenceGenerator(sequenceName = "FUN_MMS_SD_HISTORY_SEQ", allocationSize = 1, name = "FUN_MMS_SD_HISTORY_SEQ")
    @Column(name = "HISTORY_NO")
    private long historyNo;

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
    @Column(name = "FLAG")
    private String flag;
    @Column(name = "HISTORY_DATE")
    private LocalDateTime historyDate;

    @Column(name = "DEAD_AGE")
    private long deadAge;

    @Column(name = "SEND_YN")
    private String sendYn;

    @Column(name = "REPLY_YN")
    private String replyYn;

    @Column(name = "HIT_COUNT")
    private long hitCount;




    @Builder
    public FunMessageHistoryEntity(FunMessageInfo funMessageInfo) {
        this.historyNo = funMessageInfo.getHistoryNo();
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
        this.flag = funMessageInfo.getFlag();
        this.historyDate = funMessageInfo.getHistoryDate();
        this.deadAge = funMessageInfo.getDeadAge();
        this.sendYn = funMessageInfo.getSendYn();
        this.replyYn = funMessageInfo.getReplyYn();
        this.hitCount = funMessageInfo.getHitCount();
    }
}
