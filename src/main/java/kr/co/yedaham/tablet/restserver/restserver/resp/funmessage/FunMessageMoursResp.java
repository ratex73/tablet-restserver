package kr.co.yedaham.tablet.restserver.restserver.resp.funmessage;

import kr.co.yedaham.tablet.restserver.restserver.entity.FunMessageEntity;
import kr.co.yedaham.tablet.restserver.restserver.entity.FunMessageMoursEntity;
import kr.co.yedaham.tablet.restserver.restserver.model.funmessage.FunMessageMours;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface FunMessageMoursResp extends JpaRepository<FunMessageMoursEntity, Long> {

    @Query(value = "SELECT nvl(MAX(SEQ)+1,1) FROM FUN_MMS_SD_MOURS", nativeQuery = true)
    public int getNextValMySequence();

    public FunMessageMoursEntity findBySeqAndFunCtrlNo(@Param("seq") long seq, @Param("funCtrlNo") String funCtrlNo);

    public List<FunMessageMoursEntity> findByFunCtrlNo(@Param("funCtrlNo") String funCtrlNo);

    public List<FunMessageMoursEntity> findByFunSeq(@Param("funSeq") long seq);

    public List<FunMessageMoursEntity> findByFunCtrlNoAndDeadReatAndFunSeq(@Param("funCtrlNo") String funCtrlNo,@Param("deadReat") String deadReat,@Param("seq") long seq);

    public FunMessageMoursEntity findByFunCtrlNoAndFunSeqAndMainYn(@Param("funCtrlNo") String funCtrlNo, @Param("seq") long seq, @Param("mainYn") String mainYn);


}
