package kr.co.yedaham.tablet.restserver.restserver.service.customer;

import kr.co.yedaham.tablet.restserver.restserver.model.customer.*;

import java.util.List;

public interface CustomerService {
    public CustomerInfo getCustomerInfo(String functrlno);
    public List<CustomerPaymentInfo> getPaymentInfo(String certno);
    public List<CorporationInfo> getCorporationInfo(String custid);
    public List<CorporationCustomerInfo> getCorporationCustomerInfo(String custid);
    public List<CorporationSupportInfo> getCorporationSupportInfo(String custid);
    public List<CorporationDisposableInfo> getCorporationDisposableInfo(String custid);
    public CorporationRangeAccountSupportInfo getCorporationRangeProductSupportInfo(String custid);
    public List<CorporationPaymentMethodInfo> getCorporationPaymentMethodInfo(String custid);
    public List<CorporationFiledownloadInfo> getCorporationFiledownloadInfo(String custid);
    public List<CorporationPrecautionsInfo> getCorporationPrecautionsInfo(String custid);

}
