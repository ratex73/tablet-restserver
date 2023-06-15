package kr.co.yedaham.tablet.restserver.restserver.resp.carallocation;

import kr.co.yedaham.tablet.restserver.restserver.entity.CarallocationReturnEntity;
import kr.co.yedaham.tablet.restserver.restserver.model.carallocation.CarallocationReturnProduct;
import kr.co.yedaham.tablet.restserver.restserver.model.carallocation.CarallocationReturnSignatureId;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface CarallocationReturnResp extends JpaRepository<CarallocationReturnEntity, CarallocationReturnSignatureId> {
    @Query(name = "findReturnProductListMapping", nativeQuery = true)
    List<CarallocationReturnProduct> findReturnProductList(@Param("funno") String funno);
}
