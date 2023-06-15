package kr.co.yedaham.tablet.restserver.restserver.service.customer;

import kr.co.yedaham.tablet.restserver.restserver.model.customer.*;
import kr.co.yedaham.tablet.restserver.restserver.resp.customer.CustomerInfoResp;
import kr.co.yedaham.tablet.restserver.restserver.util.MaskingUtil;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.util.ObjectUtils;
import org.springframework.util.StringUtils;

import java.util.List;

@RequiredArgsConstructor
@Service
public class CustomerServiceImpl implements CustomerService{

    private final CustomerInfoResp customerInfoResp;

    @Override
    public CustomerInfo getCustomerInfo(String funcrtlno) {
        CustomerInfo customerInfo = customerInfoResp.findCustomerInfo(funcrtlno);
        if(StringUtils.hasText(customerInfo.getCustNm()) && customerInfo.getCustNm().length() > 1) {
            customerInfo.setCustNm(customerInfo.getCustNm().replaceAll(MaskingUtil.NAME_PATTERN, "*"));
        }

        if(StringUtils.hasText(customerInfo.getCallNo()) && customerInfo.getCallNo().length() > 6) {
            customerInfo.setCallNo(customerInfo.getCallNo().replaceAll(MaskingUtil.PHONE_PATTERN,"*"));
        }

        if(StringUtils.hasText(customerInfo.getReceiveCustNm()) && customerInfo.getReceiveCustNm().length() > 1) {
            customerInfo.setReceiveCustNm(customerInfo.getReceiveCustNm().replaceAll(MaskingUtil.NAME_PATTERN,"*"));
        }

        return customerInfo;
    }

    @Override
    public List<CustomerPaymentInfo> getPaymentInfo(String certno) {
        return customerInfoResp.findPaymentInfo(certno);
    }

    @Override
    public List<CorporationInfo> getCorporationInfo(String custid) {
        return customerInfoResp.findCorporationInfo(custid);
    }

    @Override
    public List<CorporationCustomerInfo> getCorporationCustomerInfo(String custid) {
        List<CorporationCustomerInfo> corporationCustomerInfo = customerInfoResp.findCorporationCustomerInfo(custid);
        for (CorporationCustomerInfo coporCustomer:corporationCustomerInfo) {
            if(StringUtils.hasText(coporCustomer.getName()) && coporCustomer.getName().length() > 1) {
                coporCustomer.setName(coporCustomer.getName().replaceAll(MaskingUtil.NAME_PATTERN,"*"));
            }

            if(StringUtils.hasText(coporCustomer.getTel()) && coporCustomer.getTel().length() > 6) {
                coporCustomer.setTel(coporCustomer.getTel().replaceAll(MaskingUtil.PHONE_PATTERN ,"*"));
            }
        }

        return corporationCustomerInfo;
    }

    @Override
    public List<CorporationSupportInfo> getCorporationSupportInfo(String custid) {
        return customerInfoResp.findCorporationSupportInfo(custid);
    }

    @Override
    public List<CorporationDisposableInfo> getCorporationDisposableInfo(String custid) {
        return customerInfoResp.findCorporationDisposableInfo(custid);
    }

    @Override
    public CorporationRangeAccountSupportInfo getCorporationRangeProductSupportInfo(String custid) {
        CorporationRangeAccountSupportInfo corporationRangeAccountSupportInfo = customerInfoResp.findCorporationRangeAccountSupportInfo(custid);
        if(!ObjectUtils.isEmpty(corporationRangeAccountSupportInfo)) {
            if(StringUtils.hasText(corporationRangeAccountSupportInfo.getAccountNo()) && corporationRangeAccountSupportInfo.getAccountNo().length() > 7) {
                corporationRangeAccountSupportInfo.setAccountNo(corporationRangeAccountSupportInfo.getAccountNo().replaceAll(MaskingUtil.ACCOUNT_PATTERN,"*"));
            }

            if(StringUtils.hasText(corporationRangeAccountSupportInfo.getAccountHold()) && corporationRangeAccountSupportInfo.getAccountHold().length() > 1) {
                corporationRangeAccountSupportInfo.setAccountHold(corporationRangeAccountSupportInfo.getAccountHold().replaceAll(MaskingUtil.NAME_PATTERN,"*"));
            }

            corporationRangeAccountSupportInfo.setList(customerInfoResp.findCorporationRangeProductSupportInfo(custid));
        }

        return corporationRangeAccountSupportInfo;
    }

    @Override
    public List<CorporationPaymentMethodInfo> getCorporationPaymentMethodInfo(String custid) {
        return customerInfoResp.findCorporationPaymentMethodInfo(custid);
    }

    @Override
    public List<CorporationFiledownloadInfo> getCorporationFiledownloadInfo(String custid) {
        return customerInfoResp.findCorporationFiledownloadInfo(custid);
    }

    @Override
    public List<CorporationPrecautionsInfo> getCorporationPrecautionsInfo(String custid) {
        return customerInfoResp.findCorporationPrecautionsInfo(custid);
    }
}
