package kr.co.yedaham.tablet.restserver.restserver.resp.funmessage;

import kr.co.yedaham.tablet.restserver.restserver.entity.FunMessageMoursHistoryEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;


public interface FunMessageMoursHistoryResp extends JpaRepository<FunMessageMoursHistoryEntity, Long> {
    @Query(value = "SELECT nvl(MAX(HISTORY_NO)+1,1) FROM FUN_MMS_SD_MOURS_HISTORY", nativeQuery = true)
    public int getNextValMySequence();
}
