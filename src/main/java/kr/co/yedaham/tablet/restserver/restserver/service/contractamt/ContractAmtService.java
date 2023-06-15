package kr.co.yedaham.tablet.restserver.restserver.service.contractamt;

import kr.co.yedaham.tablet.restserver.restserver.model.contractamt.Contract;
import kr.co.yedaham.tablet.restserver.restserver.model.contractamt.ContractAmt;
import kr.co.yedaham.tablet.restserver.restserver.model.fun.FunList;
import kr.co.yedaham.tablet.restserver.restserver.model.fun.FunPostRequest;

import java.util.ArrayList;

public interface ContractAmtService {
    public ContractAmt getContractAmt(String certno, String functrlno);
    public Contract getContract(String certno);

}
