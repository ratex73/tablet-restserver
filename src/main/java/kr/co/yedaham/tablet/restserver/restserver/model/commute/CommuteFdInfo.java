package kr.co.yedaham.tablet.restserver.restserver.model.commute;


import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class CommuteFdInfo {
    private long seq;
    private String funCtrlNo;
    private String funDays;
    private String startDate;
    private String startTime;
    private String endDate;
    private String endTime;
    private String fdName;
    private String fdId;
}
