package kr.co.yedaham.tablet.restserver.restserver.model.funCalc;

import lombok.AllArgsConstructor;
import lombok.Data;

import javax.persistence.Column;
import java.time.LocalDateTime;

@AllArgsConstructor
@Data
public class FunCalcInfo {

    private FunCalcId funCalcId; //의전번호, 의전정산차수
    private int pymtCnt; //납입회차
    private int pymtAmt; //납입금액
    private int remaAmt; //잔여납입액
    private int addExpen;	//추가비용
    private int sProdAmt;	//기준상품금액
    private int disAmt; //할인금액
    private int reducAmt; //초기감면금액
    private int addMinAmt; //변동금액
    private int totAmt;	//의전총액
    private int custAmt; //고객부담금
    private int acceptAmt; //실수령액
    private int balAmt; //잔액
    private String receType; //	수납유형1
    private String receType1; //수납유형2
    private String imagAccNo; //가상계좌번호
    private String tranDate; //발생일자
    private String pymtNo;	//입금번호
    private String slipTranDate; //전표발생일자
    private String slipType; //전표유형
    private int slipSeq; //전표발생순번
    private LocalDateTime regDate; //등록일시
    private String regId; //등록자ID
    private LocalDateTime updateDate; //수정일시
    private String updateId; //수정자ID
    private int cashAmt; //카드금액
    private int cardAmt; //현금금액
    private String cashcheckno; //현금영수증번호
    private String billcheck; //계산서발행체크
    private int issueCashAmt; //의전결산현금금액(발행용도)
    private String memo; //추가사항메모;
}
