package kr.co.yedaham.tablet.restserver.restserver.model.funCalc;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@AllArgsConstructor
@NoArgsConstructor
@Data
//TBFU1006
public class FunCalcInfo {

    private FunCalcId funCalcId; //의전번호, 의전정산차수
    private Integer pymtCnt; //납입회차
    private Integer pymtAmt; //납입금액
    private Integer remaAmt; //잔여납입액
    private Integer addExpen;	//추가비용
    private Integer sProdAmt;	//기준상품금액
    private Integer disAmt; //할인금액
    private Integer reducAmt; //초기감면금액
    private Integer addMinAmt; //변동금액
    private Integer totAmt;	//의전총액
    private Integer custAmt; //고객부담금
    private Integer acceptAmt; //실수령액
    private Integer balAmt; //잔액
    private String receType; //	수납유형1
    private String receType1; //수납유형2
    private String imagAccNo; //가상계좌번호
    private String tranDate; //발생일자
    private String pymtNo;	//입금번호
    private String slipTranDate; //전표발생일자
    private String slipType; //전표유형
    private Integer slipSeq; //전표발생순번
    private LocalDateTime regDate; //등록일시
    private String regId; //등록자ID
    private LocalDateTime updateDate; //수정일시
    private String updateId; //수정자ID
    private Integer cashAmt; //카드금액
    private Integer cardAmt; //현금금액
    private String cashcheckno; //현금영수증번호
    private String billcheck; //계산서발행체크
    private Integer issueCashAmt; //의전결산현금금액(발행용도)
    private String memo; //추가사항메모;

}
