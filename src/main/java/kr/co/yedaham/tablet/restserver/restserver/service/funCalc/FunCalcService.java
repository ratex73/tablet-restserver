package kr.co.yedaham.tablet.restserver.restserver.service.funCalc;

import kr.co.yedaham.tablet.restserver.restserver.model.fun.FunList;
import kr.co.yedaham.tablet.restserver.restserver.model.funCalc.*;

import java.util.List;

public interface FunCalcService {

    //의전물품 저장
    public boolean saveFunCalc(List<FunItemInfo> funItemInfo);

    //의전물품 및 정산저장
    public boolean saveFunItemCalc(FunItemCalcDto funItemCalcDto);
}
