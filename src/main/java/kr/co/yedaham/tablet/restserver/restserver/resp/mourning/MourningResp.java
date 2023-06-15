package kr.co.yedaham.tablet.restserver.restserver.resp.mourning;

import kr.co.yedaham.tablet.restserver.restserver.entity.MourningEntity;
import kr.co.yedaham.tablet.restserver.restserver.model.mourning.MourningId;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

public interface MourningResp extends JpaRepository<MourningEntity, MourningId> {
    @Query(name = "findMaxSeq", nativeQuery = true)
    Integer findMaxSeq(@Param("funno") String funno);

    public MourningEntity findByMourningIdFunCtrlNoAndUseYnAndDelYn(String funno, char useYn, char delYn);
}