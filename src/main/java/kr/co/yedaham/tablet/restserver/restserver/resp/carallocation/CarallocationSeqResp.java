package kr.co.yedaham.tablet.restserver.restserver.resp.carallocation;

import kr.co.yedaham.tablet.restserver.restserver.entity.CarallocationSeqEntity;
import kr.co.yedaham.tablet.restserver.restserver.model.carallocation.CarallocationRentalInfo;
import kr.co.yedaham.tablet.restserver.restserver.model.carallocation.CarallocationProductInfo;
import kr.co.yedaham.tablet.restserver.restserver.model.fun.CarallocationInfo;
import kr.co.yedaham.tablet.restserver.restserver.model.fun.CarallocationReturnInfo;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.ArrayList;
import java.util.List;

public interface CarallocationSeqResp extends JpaRepository<CarallocationSeqEntity, String> {
    @Query(value = "SELECT MAX(SEQ) FROM CH014D", nativeQuery = true)
    public String getNextValMySequence();

    @Query(name = "getCarallocationList", nativeQuery = true)
    ArrayList<CarallocationInfo> getCarallocationList(@Param("functrlno") String functrlno);

    @Query(name = "getCarallocationReturnList", nativeQuery = true)
    ArrayList<CarallocationReturnInfo> getCarallocationReturnList(@Param("functrlno") String functrlno);

    @Query(name = "getCarallocationProductInfo", nativeQuery = true)
    List<CarallocationProductInfo> getProductInfo();

    @Query(name = "getCarallocationLentalList", nativeQuery = true)
    List<CarallocationRentalInfo> getLentalList(@Param("funno") String funno, @Param("itemno") String itemno);
}
