package kr.co.yedaham.tablet.restserver.restserver.model.fun;

import lombok.AllArgsConstructor;
import lombok.Data;

import java.math.BigDecimal;

@AllArgsConstructor
@Data
public class FunList {
    private String funno;
    private String regdate;
    private String regtime;
    private String certno;
    private String state;
    private String funnm;
    private String prodgb;
    private String prodcd;
    private String prodnm;
    private String custid;
    private String custnm;
    private String deadnm;
    private String mournm;
    private String fdname;
    private String suppComp;
    private String suppTel1;
    private String suppTel2;
    private BigDecimal lat;
    private BigDecimal lng;
    private String fdPhone;
    private BigDecimal adddisamt;
    private String branchPhone;
    private String mfsUserPhone;
}
