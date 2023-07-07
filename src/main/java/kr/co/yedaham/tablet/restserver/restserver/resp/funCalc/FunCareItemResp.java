package kr.co.yedaham.tablet.restserver.restserver.resp.funCalc;

import kr.co.yedaham.tablet.restserver.restserver.entity.FunCareItemEntity;
import kr.co.yedaham.tablet.restserver.restserver.entity.FunMessageEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.query.Param;

public interface FunCareItemResp extends JpaRepository<FunCareItemEntity, String> {

    public FunCareItemEntity findByFunCtrlNoAndUseYn(@Param("funCtrlNo") String funCtrlNo, @Param("useYn") char useYn);

}
