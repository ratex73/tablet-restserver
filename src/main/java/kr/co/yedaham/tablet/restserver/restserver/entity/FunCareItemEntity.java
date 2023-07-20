package kr.co.yedaham.tablet.restserver.restserver.entity;

import kr.co.yedaham.tablet.restserver.restserver.model.funCalc.FunCareItemInfo;
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
@Table(name="TBFU4447", schema = "tk_fsdev")
//만기케어 물품 정보
public class FunCareItemEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "TBFU4447_SEQ")
    @SequenceGenerator(sequenceName = "TBFU4447_SEQ", allocationSize = 1, name = "TBFU4447_SEQ")
    @Column(name = "SEQ")
    private Integer seq;

    private String funCtrlNo;

    @Column(name="C_SEQ")
    private Integer cSeq;

    @Column(name="CARE_ITEM_CD")
    private String careItemCd;

    @Column(name="USE_YN")
    private char useYn;

    @Column(name="DEL_YN")
    private char delYn;

    @Column(name="REG_DATE")
    private LocalDateTime regDate;

    @Column(name="REG_ID")
    private String regId;
    @Column(name="UPDATE_DATE")
    private LocalDateTime updateDate;

    @Column(name="UPDATE_ID")
    private String updateId;

    @PrePersist
    //저장 전 미리 호출됨
    public void regUpdateDateSet() {
        this.regDate = LocalDateTime.now();
        this.updateDate = LocalDateTime.now();
    }

    @Builder
    public FunCareItemEntity(FunCareItemInfo funCareItemInfo) {
        seq = funCareItemInfo.getSeq();
        funCtrlNo = funCareItemInfo.getFunCtrlNo();
        cSeq = funCareItemInfo.getCSeq();
        careItemCd = funCareItemInfo.getCareItemCd();
        useYn = funCareItemInfo.getUseYn();
        delYn = funCareItemInfo.getDelYn();
        regDate = funCareItemInfo.getRegDate();
        regId = funCareItemInfo.getRegId();
        updateDate = funCareItemInfo.getUpdateDate();
        updateId = funCareItemInfo.getUpdateId();
    }


}
