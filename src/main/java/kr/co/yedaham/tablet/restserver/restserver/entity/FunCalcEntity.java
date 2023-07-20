package kr.co.yedaham.tablet.restserver.restserver.entity;

import kr.co.yedaham.tablet.restserver.restserver.model.funCalc.FunCalcId;
import kr.co.yedaham.tablet.restserver.restserver.model.funCalc.FunCalcInfo;
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
@Table(name="TBFU1006", schema = "tk_fsdev")
//의전결산정보
public class FunCalcEntity {

    @EmbeddedId
    private FunCalcId funCalcId;

    @Column(name = "PYMT_CNT")
    private Integer pymtCnt; //납입회차

    @Column(name = "PYMT_AMT")
    private Integer pymtAmt; //납입금액

    @Column(name = "REMA_AMT")
    private Integer remaAmt; //잔여납입액

    @Column(name = "ADD_EXPEN")
    private Integer addExpen;	//추가비용

    @Column(name = "S_PROD_AMT")
    private Integer sProdAmt;	//기준상품금액

    @Column(name = "DIS_AMT")
    private Integer disAmt; //할인금액

    @Column(name = "REDUC_AMT")
    private Integer reducAmt; //초기감면금액

    @Column(name = "ADD_MIN_AMT")
    private Integer addMinAmt; //변동금액

    @Column(name = "TOT_AMT")
    private Integer totAmt;	//의전총액

    @Column(name = "CUST_AMT")
    private Integer custAmt; //고객부담금

    @Column(name = "ACCEPT_AMT")
    private Integer acceptAmt; //실수령액

    @Column(name = "BAL_AMT")
    private Integer balAmt; //잔액

    @Column(name = "RECE_TYPE")
    private String receType; //	수납유형1

    @Column(name = "RECE_TYPE1")
    private String receType1; //수납유형2

    @Column(name = "IMAG_ACC_NO")
    private String imagAccNo; //가상계좌번호

    @Column(name = "TRAN_DATE")
    private String tranDate; //발생일자

    @Column(name = "PYMT_NO")
    private String pymtNo;	//입금번호

    @Column(name = "SLIP_TRAN_DATE")
    private String slipTranDate; //전표발생일자

    @Column(name = "SLIP_TYPE")
    private String slipType; //전표유형

    @Column(name = "SLIP_SEQ")
    private Integer slipSeq; //전표발생순번

    @Column(name = "REG_DATE")
    private LocalDateTime regDate; //등록일시

    @Column(name = "REG_ID")
    private String regId; //등록자ID

    @Column(name = "UPDATE_DATE")
    private LocalDateTime updateDate; //수정일시

    @Column(name = "UPDATE_ID")
    private String updateId; //수정자ID

    @Column(name = "CASH_AMT")
    private Integer cashAmt; //카드금액

    @Column(name = "CARD_AMT")
    private Integer cardAmt; //현금금액

    private String cashcheckno; //현금영수증번호

    private String billcheck; //계산서발행체크

    @Column(name = "ISSUE_CASH_AMT")
    private Integer issueCashAmt; //의전결산현금금액(발행용도)

    private String memo; //추가사항메모;

    @Builder
    public FunCalcEntity(FunCalcInfo funCalcInfo) {
        funCalcId = funCalcInfo.getFunCalcId();
        pymtCnt = funCalcInfo.getPymtCnt(); //납입회차
        pymtAmt = funCalcInfo.getPymtAmt(); //납입금액
        remaAmt = funCalcInfo.getRemaAmt() ; //잔여납입액
        addExpen = funCalcInfo.getAddExpen();	//추가비용
        sProdAmt = funCalcInfo.getSProdAmt();	//기준상품금액
        disAmt = funCalcInfo.getDisAmt(); //할인금액
        reducAmt = funCalcInfo.getReducAmt(); //초기감면금액
        addMinAmt = funCalcInfo.getAddMinAmt(); //변동금액
        totAmt = funCalcInfo.getTotAmt();	//의전총액
        custAmt = funCalcInfo.getCustAmt(); //고객부담금
        acceptAmt = funCalcInfo.getAcceptAmt(); //실수령액
        balAmt = funCalcInfo.getBalAmt(); //잔액
        receType = funCalcInfo.getReceType(); //	수납유형1
        receType1 = funCalcInfo.getReceType1(); //수납유형2
        imagAccNo = funCalcInfo.getImagAccNo(); //가상계좌번호
        tranDate = funCalcInfo.getTranDate(); //발생일자
        pymtNo = funCalcInfo.getPymtNo();	//입금번호
        slipTranDate = funCalcInfo.getSlipTranDate(); //전표발생일자
        slipType = funCalcInfo.getSlipType(); //전표유형
        slipSeq = funCalcInfo.getSlipSeq(); //전표발생순번
        regDate = funCalcInfo.getRegDate(); //등록일시
        regId = funCalcInfo.getRegId(); //등록자ID
        updateDate = funCalcInfo.getUpdateDate(); //수정일시
        updateId = funCalcInfo.getUpdateId(); //수정자ID
        cashAmt = funCalcInfo.getCashAmt(); //카드금액
        cardAmt = funCalcInfo.getCardAmt(); //현금금액
        cashcheckno = funCalcInfo.getCashcheckno(); //현금영수증번호
        billcheck = funCalcInfo.getBillcheck(); //계산서발행체크
        issueCashAmt = funCalcInfo.getIssueCashAmt(); //의전결산현금금액(발행용도)
        memo = funCalcInfo.getMemo(); //추가사항메모;

    }
}
