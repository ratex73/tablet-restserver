package kr.co.yedaham.tablet.restserver.restserver.model.customer;

import lombok.AllArgsConstructor;
import lombok.Data;

@AllArgsConstructor
@Data
public class CustomerPaymentInfo {
    private String pymtYymm;
    private String pymtCnt;
    private String pymtDate;
    private String pymtCycle;
    private String pymtCycleNm;
    private String pymtAmt;
    private String pymtType;
    private String pymtTypeNm;
    private String pymtGb;
    private String pymtGbNm;
    private String procDate;
}
