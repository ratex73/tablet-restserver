package kr.co.yedaham.tablet.restserver.restserver.resp.funmessage;


import kr.co.yedaham.tablet.restserver.restserver.entity.FunMessageEntity;
import kr.co.yedaham.tablet.restserver.restserver.entity.FunMessageMoursEntity;
import kr.co.yedaham.tablet.restserver.restserver.model.funmessage.FunMessageInfo;
import kr.co.yedaham.tablet.restserver.restserver.model.funmessage.FunMessageInfos;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;


public interface FunMessageResp extends JpaRepository<FunMessageEntity, Long> {

    @Query(value = "SELECT nvl(MAX(SEQ)+1,1) FROM FUN_MMS_SD", nativeQuery = true)
    public int getNextValMySequence();


    @Query(name = "getFunMessageList", nativeQuery = true)
    public List<FunMessageInfos> getFunMessageInfos(@Param("funCtrlNo") String funCtrlNo);

    public FunMessageEntity findTopByFunCtrlNoOrderBySeqDesc(@Param("funCtrlNo") String funCtrlNo);

    public FunMessageEntity findByFunCtrlNoAndSeq(@Param("funCtrlNo") String funCtrlNo,@Param("seq") long seq);

}
