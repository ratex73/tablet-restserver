package kr.co.yedaham.tablet.restserver.restserver.service.contractamt;

import kr.co.yedaham.tablet.restserver.restserver.model.contractamt.Contract;
import kr.co.yedaham.tablet.restserver.restserver.model.contractamt.ContractAmt;
import kr.co.yedaham.tablet.restserver.restserver.resp.contractamt.ContractAmtResp;
import kr.co.yedaham.tablet.restserver.restserver.util.MaskingUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import java.util.ArrayList;

@Service
public class ContractAmtServiceImpl implements ContractAmtService {



    @Autowired
    private ContractAmtResp contractAmtResp;


    @Override
    public ContractAmt getContractAmt(String certno, String functrlno) {
        return contractAmtResp.findContractAmt(certno, functrlno);
    }

    @Override
    public Contract getContract(String certno) {
        Contract contract = contractAmtResp.findContract(certno);

        if(StringUtils.hasText(contract.getPymtAccNo())){
            contract.setPymtAccNo(contract.getPymtAccNo().replaceAll(MaskingUtil.ACCOUNT_PATTERN,"*"));
        }

        if(StringUtils.hasText(contract.getCustNm()) && contract.getCustNm().length() > 1){
            contract.setCustNm(contract.getCustNm().replaceAll(MaskingUtil.NAME_PATTERN, "*"));
        }

        if(StringUtils.hasText(contract.getAccHold()) && contract.getAccHold().length() > 1){
            contract.setAccHold(contract.getAccHold().replaceAll(MaskingUtil.NAME_PATTERN, "*"));
        }

        return contract;
    }
}
