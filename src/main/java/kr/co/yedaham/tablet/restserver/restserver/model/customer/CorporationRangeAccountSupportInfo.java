package kr.co.yedaham.tablet.restserver.restserver.model.customer;

import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@NoArgsConstructor
@Data
public class CorporationRangeAccountSupportInfo {
    private String seq;
    private String custid;
    private String thflower;
    private String bankCode;
    private String accountNo;
    private String accountHold;
    private String bizRgstCd;
    private List<CorporationRangeProductSupportInfo> list;

    public CorporationRangeAccountSupportInfo(String seq, String custid, String thflower, String bankCode, String accountNo, String accountHold, String bizRgstCd) {
        this.seq = seq;
        this.custid = custid;
        this.thflower = thflower;
        this.bankCode = bankCode;
        this.accountNo = accountNo;
        this.accountHold = accountHold;
        this.bizRgstCd = bizRgstCd;
    }
}

