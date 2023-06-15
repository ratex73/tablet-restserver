package kr.co.yedaham.tablet.restserver.restserver.model.commute;


import lombok.AllArgsConstructor;
import lombok.Data;

import java.math.BigDecimal;

@AllArgsConstructor
@Data
public class Commute {

    private BigDecimal seq;
    private String funCtrlNo;
    private String userId;
    private String startDate;
    private String endDate;
    private String funDays;
    private BigDecimal startLat;
    private BigDecimal startLng;
    private BigDecimal endLat;
    private BigDecimal endLng;
    private String startTime;
    private String endTime;
    private String userName;
    private String funNm;
    private BigDecimal lat;
    private BigDecimal lng;
    private String fdName;


}
