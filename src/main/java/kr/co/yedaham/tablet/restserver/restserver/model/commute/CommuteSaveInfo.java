package kr.co.yedaham.tablet.restserver.restserver.model.commute;


import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.*;

import java.math.BigDecimal;


@RequiredArgsConstructor
@ToString
@Getter
@Setter
public class CommuteSaveInfo {
    private long seq;
    private String funCtrlNo;
    private String userId;
    @JsonIgnore
    private String startDate;
    @JsonIgnore
    private String endDate;
    @JsonIgnore
    private String startTime;
    @JsonIgnore
    private String endTime;
    private BigDecimal startLat;
    private BigDecimal startLng;
    private BigDecimal endLat;
    private BigDecimal endLng;
    private String funDays;
    private BigDecimal startDis;
    private BigDecimal endDis;
}
