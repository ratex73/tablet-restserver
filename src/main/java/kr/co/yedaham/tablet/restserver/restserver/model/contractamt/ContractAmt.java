package kr.co.yedaham.tablet.restserver.restserver.model.contractamt;

import lombok.AllArgsConstructor;
import lombok.Data;

@AllArgsConstructor
@Data
public class ContractAmt {
    private Integer sProdAmt;
    private Integer orgSProdAmt;
    private Integer disAmt;
    private Integer disAppAmt;
    private Integer pymtAmt;
    private Integer mProdAmt;
    private Integer excpAmt;
}

