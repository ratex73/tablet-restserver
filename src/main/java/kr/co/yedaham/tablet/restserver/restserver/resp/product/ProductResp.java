package kr.co.yedaham.tablet.restserver.restserver.resp.product;

import kr.co.yedaham.tablet.restserver.restserver.entity.ProductEntity;
import kr.co.yedaham.tablet.restserver.restserver.model.product.ContractDetailList;
import kr.co.yedaham.tablet.restserver.restserver.model.product.ContractDusanList;
import kr.co.yedaham.tablet.restserver.restserver.model.product.ContractList;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.ArrayList;

public interface ProductResp extends JpaRepository<ProductEntity, String> {
    @Query(name = "findProductYedahamOneTwoList", nativeQuery = true)
    ArrayList<ContractList> findProductYedahamOneTwoList(@Param("certno") String certno, @Param("functrlno") String functrlno);

    @Query(name = "findDusanProductList", nativeQuery = true)
    ArrayList<ContractDusanList> findDusanProductList(@Param("certno") String certno, @Param("functrlno") String functrlno);

    @Query(name = "findNewProductList", nativeQuery = true)
    ArrayList<ContractList> findNewProductList(@Param("certno") String certno, @Param("functrlno") String functrlno);

    @Query(name = "findOptionalProductList", nativeQuery = true)
    ArrayList<ContractList> findOptionalProductList(@Param("certno") String certno, @Param("functrlno") String functrlno);

    @Query(name = "findContractDetailList", nativeQuery = true)
    ArrayList<ContractDetailList> findContractDetailList(@Param("certNo") String certNo);

}
