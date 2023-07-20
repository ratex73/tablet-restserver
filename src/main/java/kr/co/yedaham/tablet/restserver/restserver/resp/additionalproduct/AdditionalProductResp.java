package kr.co.yedaham.tablet.restserver.restserver.resp.additionalproduct;

import kr.co.yedaham.tablet.restserver.restserver.entity.AdditionalProductEntity;
import kr.co.yedaham.tablet.restserver.restserver.model.product.AdditionalProductList;
import kr.co.yedaham.tablet.restserver.restserver.model.product.ProductList;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.ArrayList;

public interface AdditionalProductResp extends JpaRepository<AdditionalProductEntity, String> {
    @Query(name = "findAdditionalProductYedahamOneTwoList", nativeQuery = true)
    ArrayList<AdditionalProductList> findAdditionalProductYedahamOneTwoList(@Param("certno") String certno, @Param("grpcode") String grpcode
            , @Param("amt") String amt, @Param("plinm") String plinm);

    @Query(name = "findAdditionalProductYedahamOneTwoAllList", nativeQuery = true)
    ArrayList<AdditionalProductList> findAdditionalProductYedahamOneTwoAllList(@Param("certno") String certno);

    @Query(name = "findAdditionalDusanProductList", nativeQuery = true)
    ArrayList<AdditionalProductList> findAdditionalDusanProductList(@Param("certno") String certno, @Param("grpcode") String grpcode
            , @Param("amt") String amt, @Param("cdnm") String cdnm, @Param("plinm") String plinm);

    @Query(name = "findAdditionalDusanProductAllList", nativeQuery = true)
    ArrayList<AdditionalProductList> findAdditionalDusanProductAllList(@Param("certno") String certno);

    @Query(name = "findAdditionalNewProductList", nativeQuery = true)
    ArrayList<AdditionalProductList> findAdditionalNewProductList(@Param("certno") String certno, @Param("grpcode") String grpcode
            , @Param("amt") String amt, @Param("cdnm") String cdnm, @Param("plicd") String plicd
            , @Param("plinm1") String plinm1 , @Param("plinm2") String plinm2 , @Param("plinm3") String plinm3);

    @Query(name = "findAdditionalNewProductAllList", nativeQuery = true)
    ArrayList<AdditionalProductList> findAdditionalNewProductAllList(@Param("certno") String certno);

    @Query(name = "findInitAdditionalNewProductList", nativeQuery = true)
    ArrayList<AdditionalProductList> findInitAdditionalNewProductList(@Param("functrlno") String functrlno, @Param("certno") String certno);

    @Query(name = "findInitAdditionalDusanProductList", nativeQuery = true)
    ArrayList<AdditionalProductList> findInitAdditionalDusanProductList(@Param("functrlno") String functrlno, @Param("certno") String certno);

    @Query(name = "findInitAdditionalYedahamOneTwoProductList", nativeQuery = true)
    ArrayList<AdditionalProductList> findInitAdditionalYedahamOneTwoProductList(@Param("functrlno") String functrlno, @Param("certno") String certno);
}
