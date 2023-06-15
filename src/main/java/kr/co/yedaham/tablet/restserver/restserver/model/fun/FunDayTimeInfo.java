package kr.co.yedaham.tablet.restserver.restserver.model.fun;

import lombok.Data;

@Data
public class FunDayTimeInfo {
    private String funno;
    private String funStartDate;
    private String funStartTime;
    private String funStartMin;
    private String funEndDate;
    private String funEndTime;
    private String funEndMin;
    private String arrvDate;
    private String arrvTime;
    private String arrvMin;
    private String deadDate;
    private String deadTime;
    private String deadMin;
    private String caskDate;
    private String caskTime;
    private String caskMin;
    private String cortDate;
    private String cortTime;
    private String cortMin;
    private String funDays;
    private String prodArrvDate;
    private String prodArrvMin;
    private String prodArrvTime;
    private String cortSupYn;
    private String cortSupNcd;
    private String status;
}
