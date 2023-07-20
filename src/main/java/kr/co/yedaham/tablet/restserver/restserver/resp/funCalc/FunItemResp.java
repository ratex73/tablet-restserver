package kr.co.yedaham.tablet.restserver.restserver.resp.funCalc;

import kr.co.yedaham.tablet.restserver.restserver.entity.FunCareItemEntity;
import kr.co.yedaham.tablet.restserver.restserver.entity.FunItemEntity;
import kr.co.yedaham.tablet.restserver.restserver.model.funCalc.FunItemId;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.query.Param;

public interface FunItemResp extends JpaRepository<FunItemEntity, FunItemId> {
    //복합키 중 특정칼럼값 기준으로 삭제하기
    public Integer deleteByFunItemIdFunCtrlNo(@Param("FunCtrlNo") String funCtrlNo);

}
