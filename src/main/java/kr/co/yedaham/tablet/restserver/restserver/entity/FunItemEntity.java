package kr.co.yedaham.tablet.restserver.restserver.entity;


import kr.co.yedaham.tablet.restserver.restserver.model.funCalc.FunItemId;
import kr.co.yedaham.tablet.restserver.restserver.model.funCalc.FunItemInfo;
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
@Table(name="TBFU1004", schema = "tk_fsdev")
//의전물품정보
public class FunItemEntity {
    @EmbeddedId
    private FunItemId funItemId;

    private String state;

    @Column(name = "GRP_CD")
    private String grpCd;

    private Integer  qty;

    private Integer  amt;

    @Column(name = "CREAT_YN")
    private String creatYn;

    @Column(name = "REG_DATE")
    private LocalDateTime regDate;

    @Column(name = "REG_ID")
    private String regId;

    @Column(name = "UPDATE_DATE")
    private LocalDateTime updateDate;

    @Column(name = "UPDATE_ID")
    private String updateId;

    @Column(name = "PAY_BACK_CODE")
    private String payBackCode;

    @Column(name = "PAY_BACK_COMMENT")
    private String payBackComment;

    @PrePersist
    //저장 전 미리 호출됨
    //public void createdAt() {
    public void regDateSet() {
        this.regDate = LocalDateTime.now();
    }

    @Builder
    public FunItemEntity(FunItemInfo funItemInfo) {

        this.funItemId = funItemInfo.getFunItemId();
        this.state = funItemInfo.getState();
        this.grpCd = funItemInfo.getGrpCd();
        this.qty =  funItemInfo.getQty();
        this.amt = funItemInfo.getAmt();
        this.creatYn = funItemInfo.getCreatYn();
        this.regDate = funItemInfo.getRegDate();
        this.regId = funItemInfo.getRegId();
        this.updateDate = funItemInfo.getUpdateDate();
        this.updateId = funItemInfo.getUpdateId();
        this.payBackCode = funItemInfo.getPayBackCode();
        this.payBackComment = funItemInfo.getPayBackComment();

    }

}
