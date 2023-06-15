package kr.co.yedaham.tablet.restserver.restserver.resp.customer;

import kr.co.yedaham.tablet.restserver.restserver.entity.CustomerEntity;
import kr.co.yedaham.tablet.restserver.restserver.model.customer.*;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface CustomerInfoResp extends JpaRepository<CustomerEntity, String> {
    @Query(name = "findCustomerInfo", nativeQuery = true)
    CustomerInfo findCustomerInfo(@Param("functrlno") String functrlno);

    @Query(name = "findPaymentInfo", nativeQuery = true)
    List<CustomerPaymentInfo> findPaymentInfo(@Param("certno") String certno);

    @Query(name = "findCorporationInfo", nativeQuery = true)
    List<CorporationInfo> findCorporationInfo(@Param("custid") String custid);

    @Query(name = "findCorporationCustomerInfo", nativeQuery = true)
    List<CorporationCustomerInfo> findCorporationCustomerInfo(@Param("custid") String custid);

    @Query(name = "findCorporationSupportInfo", nativeQuery = true)
    List<CorporationSupportInfo> findCorporationSupportInfo(@Param("custid") String custid);


    @Query(name = "findCorporationDisposableInfo", nativeQuery = true)
    List<CorporationDisposableInfo> findCorporationDisposableInfo(@Param("custid") String custid);

    @Query(name = "findCorporationRangeProductSupportInfo", nativeQuery = true)
    List<CorporationRangeProductSupportInfo> findCorporationRangeProductSupportInfo(@Param("custid") String custid);

    @Query(name = "findCorporationRangeAccountSupportInfo", nativeQuery = true)
    CorporationRangeAccountSupportInfo findCorporationRangeAccountSupportInfo(@Param("custid") String custid);

    @Query(name = "findCorporationPaymentMethodInfo", nativeQuery = true)
    List<CorporationPaymentMethodInfo> findCorporationPaymentMethodInfo(@Param("custid") String custid);

    @Query(name = "findCorporationFiledownloadInfo", nativeQuery = true)
    List<CorporationFiledownloadInfo> findCorporationFiledownloadInfo(@Param("custid") String custid);

    @Query(name = "findCorporationPrecautionsInfo", nativeQuery = true)
    List<CorporationPrecautionsInfo> findCorporationPrecautionsInfo(@Param("custid") String custid);




}
