package kr.co.yedaham.tablet.restserver.restserver.model.funCalc;

import lombok.AllArgsConstructor;
import lombok.Data;

import java.time.LocalDateTime;

@AllArgsConstructor
@Data
public class FunPaybackInfo {

    private FunPaybackInfoId funPaybackInfoId; //cert_no, tran_date, ref_meth
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
    private int slipSeq;	//전표 순번
    private String eApply; //처리유무
    private String eApplyDate;	//처리일자
    private int withholdPymtCnt; //지급보류횟수
    private String withholdPymtDate; //지급보류일
    private char withholdPymtReasonCd; //지급보류사유코드
    private int sendSeq; //전송순번
    private String accBirthDate; //예금주생년월일
}
