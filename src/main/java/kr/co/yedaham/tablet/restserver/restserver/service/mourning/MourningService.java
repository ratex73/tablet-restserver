package kr.co.yedaham.tablet.restserver.restserver.service.mourning;

import kr.co.yedaham.tablet.restserver.restserver.entity.MourningEntity;
import kr.co.yedaham.tablet.restserver.restserver.model.comm.MourningFunDayTimeInfo;
import kr.co.yedaham.tablet.restserver.restserver.model.mourning.MourningInfo;
import kr.co.yedaham.tablet.restserver.restserver.model.response.SingleResult;

public interface MourningService {
    public SingleResult<MourningFunDayTimeInfo> saveMourning(MourningFunDayTimeInfo mourningFunDayTimeInfo);
}
