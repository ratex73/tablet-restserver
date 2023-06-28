package kr.co.yedaham.tablet.restserver.restserver.entity;

import kr.co.yedaham.tablet.restserver.restserver.model.funCalc.FunPaybackInfo;

import java.time.LocalDateTime;

@DynamicUpdate
@Data
@Entity
@NoArgsConstructor
@Getter
@ToString
@Table(name="TBCA1001", schema = "tk_fsdev")
public class FunPaybackInfoEntity {

    @Column(name="TRAN_DATE")
    private String tranDate; //발생일자
    @Column(name="CERT_NO")
    private String certNo; //증서번호
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
    private String refAmt; //환급금액
    @Column(name="STATE")
    private String state; //상태(환급금 상태  1.신청 2.승인 3.출금완료
    @Column(name="REG_DATE")
    private LocalDateTime regDate;
    @Column(name="REG_ID")
    private String regId;
    @Column(name="UPDATE_DATE")
    private LocalDateTime updateDate;
    @Column(name="UPDATE_ID")
    private LocalDateTime updateDate;
    @Column(name="APPR_DATE")
    private String apprDate; //환급금 승인일자
    @Column(name="WITH_DATE")
    private String withDate; //출금일자
    @Column(name="SLIP_TRAN_DATE")
    private String slipTranDate; //전표 발생일자
    @Column(name="SLIP_TYPE")
    private String slipType; //전표 유형
    @Column(name="SLIP_SEQ")
    private String slipSeq;	//전표 순번
    @Column(name="E_APPLY")
    private String eApply; //처리유무
    @Column(name="E_APPLY_DATE")
    private String eApplyDate;	//처리일자
    @Column(name="REF_METH")
    private String refMeth; //환급방법: 계좌이체(01), 카드취소(02)
    @Column(name="WITHHOLD_PYMT_CNT")
    private String withholdPymtCnt; //지급보류횟수
    @Column(name="WITHHOLD_PYMT_DATE")
    private String withholdPymtDate; //지급보류일
    @Column(name="WITHHOLD_PYMT_REASON_CD")
    private String withholdPymtReasonCd; //지급보류사유코드
    @Column(name="SEND_SEQ")
    private String sendSeq; //전송순번
    @Column(name="ACC_BIRTH_DATE")
    private String accBirthDate; //예금주생년월일

    public void FunPaybackInfoEntity(FunPaybackInfo funPaybackInfo) {
         tranDate; //발생일자
         certNO; //증서번호
         funCtrlNo; //의전번호
         chaId; //변경ID
         refGb; //환급구분
         bankCd; //송금은행
         accNo;	//계좌번호
         accHold; //예금주
         refAmt; //환급금액
         state; //상태(환급금 상태  1.신청 2.승인 3.출금완료)
         regDate;
         regId;
         updateDate;
         updateId;
         apprDate; //환급금 승인일자
         withDate; //출금일자
         slipTranDate; //전표 발생일자
         slipType; //전표 유형
         slipSeq;	//전표 순번
         eApply; //처리유무
         eApplyDate;	//처리일자
         refMeth; //환급방법: 계좌이체(01), 카드취소(02)
         withholdPymtCnt; //지급보류횟수
         withholdPymtDate; //지급보류일
         withholdPymtReasonCd; //지급보류사유코드
         sendSeq; //전송순번
         accBirthDate; //예금주생년월일
    }
}
