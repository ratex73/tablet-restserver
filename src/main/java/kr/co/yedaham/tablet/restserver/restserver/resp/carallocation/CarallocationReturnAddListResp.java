package kr.co.yedaham.tablet.restserver.restserver.resp.carallocation;

import kr.co.yedaham.tablet.restserver.restserver.entity.CarallocationReturnAddListEntity;
import kr.co.yedaham.tablet.restserver.restserver.model.carallocation.CarallocationReturnAdd;
import kr.co.yedaham.tablet.restserver.restserver.model.carallocation.CarallocationReturnProduct;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface CarallocationReturnAddListResp extends JpaRepository<CarallocationReturnAddListEntity, Long> {


    @Query(name = "findReturnAddInfoMapping", nativeQuery = true)
    CarallocationReturnAdd findReturnAddInfo(@Param("funno") String funno,@Param("type") String type);


    @Query(value =
            " select \n" +
            " nvl(SUM(SCENE_YEDAHAM_COUNT),0) + nvl(SUM(SCENE_OUTER_COUNT),0) + nvl(SUM(DELIVERY_YEDAHAM_COUNT),0) + \n" +
            " nvl(SUM(DELIVERY_OUTER_COUNT),0)  + nvl(SUM(ETC_YEDAHAM_COUNT),0)  + nvl(SUM(ETC_OUTER_COUNT),0) as totalReturnCount \n" +
            " from add_ch013d \n" +
            " where fun_ctrl_no = :funno \n" , nativeQuery = true)
    public int getCompareReturnAddCount(@Param("funno") String funno);
}
