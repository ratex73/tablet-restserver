package kr.co.yedaham.tablet.restserver.restserver.model.comm;

import kr.co.yedaham.tablet.restserver.restserver.model.fun.FunDayTimeInfo;
import kr.co.yedaham.tablet.restserver.restserver.model.mourning.MourningInfo;
import lombok.Data;

@Data
public class MourningFunDayTimeInfo {
    private MourningInfo mourningInfo;
    private FunDayTimeInfo funDayTimeInfo;
}
