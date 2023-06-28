package kr.co.yedaham.tablet.restserver.restserver.entity;

import kr.co.yedaham.tablet.restserver.restserver.model.funCalc.FunCareItemInfo;

import java.time.LocalDateTime;

@DynamicUpdate
@Data
@Entity
@NoArgsConstructor
@Getter
@ToString
@Table(name="TBFU4435", schema = "tk_fsdev")
public class FunCareItemEntity {

    @Id
    private String funCtrlNo;

    @Column(name="C_SEQ")
    private int cSeq;

    @Column(name="CARE_ITEM_CD")
    private String careItemCd;

    @Column(name="USE_YN")
    private String useYn;

    @Column(name="REG_DATE")
    private LocalDateTime regDate;

    @Column(name="REG_ID")
    private String regId;
    @Column(name="UPDATE_DATE")
    private LocalDateTime updateDate;

    @Column(name="UPDATE_ID")
    private String updateId;
    @Builder
    public void FunCareItemEntity(FunCareItemInfo funCareItemInfo) {
        funCtrlNo = funCareItemInfo.getFunCtrlNO();
        cSeq = funCareItemInfo.getCSeq();
        careItemCd = funCareItemInfo.getCareItemCd();
        useYn = funCareItemInfo.getUseYn();
        regDate = funCareItemInfo.getRegDate();
        regId = funCareItemInfo.getRegId();
        updateDate = funCareItemInfo.getUpdateDate();
        updateId = funCareItemInfo.getUpdateId();
    }


}
