package kr.co.yedaham.tablet.restserver.restserver.service.funCalc;

import kr.co.yedaham.tablet.restserver.restserver.model.fun.FunList;
import kr.co.yedaham.tablet.restserver.restserver.model.funCalc.*;
import kr.co.yedaham.tablet.restserver.restserver.model.response.CommonResult;

import java.util.List;

public interface FunCalcService {

    //의전물품 저장
    public boolean saveFunCalc(List<FunItemInfo> funItemInfo);

    //의전물품 및 정산저장
    public CommonResult saveFunItemCalc(FunItemCalcDto funItemCalcDto);

    //의전정산서 문자 전송 가능여부
    public String funReportSendYn(String functrlno, String calcType);

    //의전 파일명 조회
    public String getFunFileName(String functrlno, String calcType);
}
