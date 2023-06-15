package kr.co.yedaham.tablet.restserver.restserver.model.customer;

import lombok.AllArgsConstructor;
import lombok.Data;

@AllArgsConstructor
@Data
public class CustomerInfo {
     private String funCtrlNo;
     private String certNo;
     private String custid;
     private String custNm;
     private String birthDate;
     private String addr;
     private String contDate;
     private String custType;
     private String custTypeNm;
     private String prodNm;
     private String prodAmt;
     private String contAmt;
     private String remaPymtAmt;
     private String receiveCustNm;
     private String receiveCustReat;
     private String callNo;
     private String cancelAmtTargetYn;
     private String cancelAmt;
}
