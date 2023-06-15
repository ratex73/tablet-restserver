package kr.co.yedaham.tablet.restserver.restserver.entity;


import kr.co.yedaham.tablet.restserver.restserver.model.funmessage.FunMessageMours;
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
@Table(name="FUN_MMS_SD_MOURS_HISTORY", schema = "tk_fsdev")
public class FunMessageMoursHistoryEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "FUN_MMS_SD_MOURS_HISTORY_SEQ")
    @SequenceGenerator(sequenceName = "FUN_MMS_SD_MOURS_HISTORY_SEQ", allocationSize = 1, name = "FUN_MMS_SD_MOURS_HISTORY_SEQ")
    @Column(name = "HISTORY_NO")
    private long historyNo;

    @Column(name = "SEQ")
    private long seq;
    @Column(name = "FUN_CTRL_NO")
    private String funCtrlNo;
    @Column(name = "REG_DATE")
    private LocalDateTime regDate;
    @Column(name = "REG_ID")
    private String regId;
    @Column(name = "UPDATE_DATE")
    private LocalDateTime updateDate;
    @Column(name = "UPDATE_ID")
    private String updateId;
    @Column(name = "DEAD_REAT")
    private String deadReat;
    @Column(name = "MOUR_REAT_NM")
    private String mourReatNm;
    @Column(name = "MOUR_REAT_ACC_NO")
    private String mourReatAccNo;
    @Column(name = "MOUR_REAT_BANK_NM")
    private String mourReatBankNm;
    @Column(name = "FUN_SEQ")
    private long funSeq;
    @Column(name = "MOUR_REAT_ORDER")
    private long mourReatOrder;
    @Column(name = "MOUR_REAT_PHONE")
    private String mourReatPhone;
    @Column(name = "MOUR_ACC_JUMIN_NO")
    private String mourAccJuminNo;

    @Column(name = "MAIN_YN")
    private String mainYn;

    @Column(name = "FLAG")
    private String flag;

    @Column(name = "HISTORY_DATE")
    private LocalDateTime historyDate;


    @Builder
    public FunMessageMoursHistoryEntity(FunMessageMours funMessageMours) {
        this.historyNo = funMessageMours.getHistoryNo();
        this.seq = funMessageMours.getSeq();
        this.funCtrlNo = funMessageMours.getFunCtrlNo();
        this.regDate = funMessageMours.getRegDate();
        this.regId = funMessageMours.getRegId();
        this.updateDate = funMessageMours.getUpdateDate();
        this.updateId = funMessageMours.getUpdateId();
        this.deadReat = funMessageMours.getDeadReat();
        this.mourReatNm = funMessageMours.getMourReatNm();
        this.mourReatAccNo = funMessageMours.getMourReatAccNo();
        this.mourReatBankNm = funMessageMours.getMourReatBankNm();
        this.funSeq = funMessageMours.getFunSeq();
        this.mourReatOrder = funMessageMours.getMourReatOrder();
        this.mourReatPhone = funMessageMours.getMourReatPhone();
        this.mourAccJuminNo = funMessageMours.getMourAccJuminNo();
        this.mainYn = funMessageMours.getMainYn();
        this.flag = funMessageMours.getFlag();
        this.historyDate = funMessageMours.getHistoryDate();
    }
}
