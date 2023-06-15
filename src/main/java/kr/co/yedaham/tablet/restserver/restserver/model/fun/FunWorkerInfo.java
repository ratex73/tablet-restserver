package kr.co.yedaham.tablet.restserver.restserver.model.fun;

import lombok.AllArgsConstructor;
import lombok.Data;

@AllArgsConstructor
@Data
public class FunWorkerInfo {
    private String manageBranchNm;
    private String manageBranchHeadNm;
    private String orgBranchNm;
    private String funStatusNm;
    private String funStartDate;
    private String firstHelpNm;
    private String caskDate;
    private String casketSupNm;
    private String cortDate;
    private String cortSupNm;
}
