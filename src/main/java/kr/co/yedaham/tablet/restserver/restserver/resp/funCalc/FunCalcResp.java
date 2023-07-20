package kr.co.yedaham.tablet.restserver.restserver.resp.funCalc;

import kr.co.yedaham.tablet.restserver.restserver.entity.FunCalcEntity;
import kr.co.yedaham.tablet.restserver.restserver.model.funCalc.FunCalcId;
import org.springframework.data.jpa.repository.JpaRepository;

public interface FunCalcResp extends JpaRepository<FunCalcEntity, FunCalcId> {

}