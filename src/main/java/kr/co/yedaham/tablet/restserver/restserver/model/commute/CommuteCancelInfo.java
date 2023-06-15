package kr.co.yedaham.tablet.restserver.restserver.model.commute;


import lombok.AllArgsConstructor;
import lombok.Data;

@AllArgsConstructor
@Data
public class CommuteCancelInfo {
    private long seq;
    private String funCtrlNo;
    private String funDays;
    private String startDate;
    private String startTime;
    private String endDate;
    private String endTime;
}
