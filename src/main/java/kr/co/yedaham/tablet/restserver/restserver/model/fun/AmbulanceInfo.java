package kr.co.yedaham.tablet.restserver.restserver.model.fun;

import lombok.AllArgsConstructor;
import lombok.Data;

@AllArgsConstructor
@Data
public class AmbulanceInfo {
    private String funno;
    private String funCd;
    private String funResGb;
    private String ambuBizCd;
    private String ambuUseYn;
    private String ambReasonCode;
    private String ambuCostUseYn;
    private Integer ambAmt;
    private String ambAmtType;
}
