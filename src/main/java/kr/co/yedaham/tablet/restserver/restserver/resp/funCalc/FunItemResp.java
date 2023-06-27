package kr.co.yedaham.tablet.restserver.restserver.resp.funCalc;

import kr.co.yedaham.tablet.restserver.restserver.entity.FunItemEntity;
import kr.co.yedaham.tablet.restserver.restserver.model.funCalc.FunItemId;
import org.springframework.data.jpa.repository.JpaRepository;

public interface FunItemResp extends JpaRepository<FunItemEntity, FunItemId> {

}
