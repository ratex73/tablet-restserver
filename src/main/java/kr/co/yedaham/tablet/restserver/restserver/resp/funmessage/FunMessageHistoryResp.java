package kr.co.yedaham.tablet.restserver.restserver.resp.funmessage;

import kr.co.yedaham.tablet.restserver.restserver.entity.FunMessageHistoryEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

public interface FunMessageHistoryResp extends JpaRepository<FunMessageHistoryEntity,Long> {
    @Query(value = "SELECT nvl(MAX(HISTORY_NO)+1,1) FROM FUN_MMS_SD_HISTORY", nativeQuery = true)
    public int getNextValMySequence();
}
