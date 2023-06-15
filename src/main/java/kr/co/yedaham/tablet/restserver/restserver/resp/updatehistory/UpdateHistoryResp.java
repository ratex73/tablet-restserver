package kr.co.yedaham.tablet.restserver.restserver.resp.updatehistory;

import kr.co.yedaham.tablet.restserver.restserver.entity.UpdateHistoryEntity;
import kr.co.yedaham.tablet.restserver.restserver.model.updatehistory.UpdateHistoryList;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.ArrayList;

public interface UpdateHistoryResp extends JpaRepository<UpdateHistoryEntity, Long> {

    @Query(name = "findUpdateHistoryList", nativeQuery = true)
    public ArrayList<UpdateHistoryList> findUpdateHistoryList(@Param("updateid") String updateid, @Param("funno") String functrlno);

}
