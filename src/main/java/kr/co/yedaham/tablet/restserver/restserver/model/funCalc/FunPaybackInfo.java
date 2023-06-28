package kr.co.yedaham.tablet.restserver.restserver.model.funCalc;

import java.time.LocalDateTime;

public class FunPaybackInfo {

    /*
    TRAN_DATE	발생일자
CERT_NO	증서번호
FUN_CTRL_NO	의전번호
CHA_ID	변경ID
REF_GB	환급구분
BANK_CD	송금은행
ACC_NO	계좌번호
ACC_HOLD	예금주
REF_AMT	환급금액
STATE	상태(환급금 상태  1.신청 2.승인 3.출금완료)
REG_DATE	등록일시
REG_ID	등록자ID
UPDATE_DATE	수정일시
UPDATE_ID	수정자ID
APPR_DATE	환급금 승인일자
WITH_DATE	출금일자
SLIP_TRAN_DATE	전표 발생일자
SLIP_TYPE	전표 유형
SLIP_SEQ	전표 순번
E_APPLY	처리유무
E_APPLY_DATE	처리일자
ACC_NO$$	암호화 계좌번호
REF_METH	환급방법: 계좌이체(01), 카드취소(02)
WITHHOLD_PYMT_CNT	지급보류횟수
WITHHOLD_PYMT_DATE	지급보류일
WITHHOLD_PYMT_REASON_CD	지급보류사유코드
SEND_SEQ	전송순번
ACC_BIRTH_DATE	예금주생년월일
     */
    private String tranDate; //발생일자
    private String certNO; //증서번호
    private String funCtrlNo; //의전번호
    private String chaId; //변경ID
    private String refGb; //환급구분
    private String bankCd; //송금은행
    private String accNo;	//계좌번호
    private String accHold; //예금주
    private int refAmt; //환급금액
    private String state; //상태(환급금 상태  1.신청 2.승인 3.출금완료)
    private LocalDateTime regDate;
    private String regId;
    private LocalDateTime updateDate;
    private String updateId;
    private String apprDate; //환급금 승인일자
    private String withDate; //출금일자
    private String slipTranDate; //전표 발생일자
    private String slipType; //전표 유형
    private String slipSeq;	//전표 순번
    private String eApply; //처리유무
    private String eApplyDate;	//처리일자
    private String refMeth; //환급방법: 계좌이체(01), 카드취소(02)
    private String withholdPymtCnt; //지급보류횟수
    private String withholdPymtDate; //지급보류일
    private String withholdPymtReasonCd; //지급보류사유코드
    private String sendSeq; //전송순번
    private String accBirthDate; //예금주생년월일
}
