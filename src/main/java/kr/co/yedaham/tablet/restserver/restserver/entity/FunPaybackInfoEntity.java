package kr.co.yedaham.tablet.restserver.restserver.entity;

import kr.co.yedaham.tablet.restserver.restserver.model.funCalc.FunPaybackInfo;
import kr.co.yedaham.tablet.restserver.restserver.model.funCalc.FunPaybackInfoId;
import lombok.*;
import org.hibernate.annotations.DynamicUpdate;

import javax.persistence.Column;
import javax.persistence.EmbeddedId;
import javax.persistence.Entity;
import javax.persistence.Table;
import java.time.LocalDateTime;

@DynamicUpdate
@Data
@Entity
@NoArgsConstructor
@Getter
@ToString
@Table(name="TBCA1001", schema = "tk_fsdev")
public class FunPaybackInfoEntity {

    @EmbeddedId
    private FunPaybackInfoId funPaybackInfoId;
    @Column(name="FUN_CTRL_NO")
    private String funCtrlNo; //의전번호
    @Column(name="CHA_ID")
    private String chaId; //변경ID
    @Column(name="REF_GB")
    private String refGb; //환급구분
    @Column(name="BANK_CD")
    private String bankCd; //송금은행
    @Column(name="ACC_NO")
    private String accNo;	//계좌번호
    @Column(name="ACC_HOLD")
    private String accHold; //예금주
    @Column(name="REF_AMT")
    private int refAmt; //환급금액
    @Column(name="STATE")
    private String state; //상태(환급금 상태  1.신청 2.승인 3.출금완료
    @Column(name="REG_DATE")
    private LocalDateTime regDate;
    @Column(name="REG_ID")
    private String regId;
    @Column(name="UPDATE_DATE")
    private LocalDateTime updateDate;
    @Column(name="UPDATE_ID")
    private String updateId;
    @Column(name="APPR_DATE")
    private String apprDate; //환급금 승인일자
    @Column(name="WITH_DATE")
    private String withDate; //출금일자
    @Column(name="SLIP_TRAN_DATE")
    private String slipTranDate; //전표 발생일자
    @Column(name="SLIP_TYPE")
    private String slipType; //전표 유형
    @Column(name="SLIP_SEQ")
    private int slipSeq;	//전표 순번
    @Column(name="E_APPLY")
    private String eApply; //처리유무
    @Column(name="E_APPLY_DATE")
    private String eApplyDate;	//처리일자
    @Column(name="WITHHOLD_PYMT_CNT")
    private int withholdPymtCnt; //지급보류횟수
    @Column(name="WITHHOLD_PYMT_DATE")
    private String withholdPymtDate; //지급보류일
    @Column(name="WITHHOLD_PYMT_REASON_CD")
    private char withholdPymtReasonCd; //지급보류사유코드
    @Column(name="SEND_SEQ")
    private int sendSeq; //전송순번
    @Column(name="ACC_BIRTH_DATE")
    private String accBirthDate; //예금주생년월일

    @Builder
    public void FunPaybackInfoEntity(FunPaybackInfo funPaybackInfo) {
         funPaybackInfoId = funPaybackInfo.getFunPaybackInfoId();
         funCtrlNo = funPaybackInfo.getFunCtrlNo(); //의전번호
         chaId = funPaybackInfo.getChaId(); //변경ID
         refGb = funPaybackInfo.getRefGb(); //환급구분
         bankCd = funPaybackInfo.getBankCd(); //송금은행
         accNo = funPaybackInfo.getAccNo();	//계좌번호
         accHold = funPaybackInfo.getAccHold(); //예금주
         refAmt = funPaybackInfo.getRefAmt(); //환급금액
         state = funPaybackInfo.getState(); //상태(환급금 상태  1.신청 2.승인 3.출금완료)
         regDate = funPaybackInfo.getRegDate();
         regId = funPaybackInfo.getRegId();
         updateDate = funPaybackInfo.getUpdateDate();
         updateId = funPaybackInfo.getUpdateId();
         apprDate = funPaybackInfo.getApprDate(); //환급금 승인일자
         withDate = funPaybackInfo.getWithDate(); //출금일자
         slipTranDate = funPaybackInfo.getSlipTranDate(); //전표 발생일자
         slipType = funPaybackInfo.getSlipType(); //전표 유형
         slipSeq = funPaybackInfo.getSlipSeq();	//전표 순번
         eApply = funPaybackInfo.getEApply(); //처리유무
         eApplyDate = funPaybackInfo.getEApplyDate();	//처리일자
         withholdPymtCnt = funPaybackInfo.getWithholdPymtCnt(); //지급보류횟수
         withholdPymtDate = funPaybackInfo.getWithholdPymtDate(); //지급보류일
         withholdPymtReasonCd = funPaybackInfo.getWithholdPymtReasonCd(); //지급보류사유코드
         sendSeq = funPaybackInfo.getSendSeq(); //전송순번
         accBirthDate = funPaybackInfo.getAccBirthDate(); //예금주생년월일
    }
}
