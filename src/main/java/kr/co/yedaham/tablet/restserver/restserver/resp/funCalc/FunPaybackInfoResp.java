package kr.co.yedaham.tablet.restserver.restserver.resp.funCalc;

import kr.co.yedaham.tablet.restserver.restserver.entity.FunPaybackInfoEntity;
import kr.co.yedaham.tablet.restserver.restserver.model.funCalc.FunPaybackInfoId;
import org.springframework.data.jpa.repository.JpaRepository;

public interface FunPaybackInfoResp extends JpaRepository<FunPaybackInfoEntity, FunPaybackInfoId> {

}
