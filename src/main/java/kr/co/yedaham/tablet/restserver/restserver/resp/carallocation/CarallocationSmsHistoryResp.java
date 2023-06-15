package kr.co.yedaham.tablet.restserver.restserver.resp.carallocation;

import kr.co.yedaham.tablet.restserver.restserver.entity.CarallocationSmsHistoryEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

public interface CarallocationSmsHistoryResp extends JpaRepository<CarallocationSmsHistoryEntity, String> {
    @Query(value = "SELECT nvl(max(high_line)+1,1) FROM CH013M WHERE FUN_CTRL_NO =:funno", nativeQuery = true)
    public int getNextValMySequence(@Param("funno") String funno);

    public CarallocationSmsHistoryEntity findByFunCtrlNoAndItemCkNo(@Param("funCtrlNo") String funCtrlNo, @Param("itemCkNo")String itemCkNo);

}