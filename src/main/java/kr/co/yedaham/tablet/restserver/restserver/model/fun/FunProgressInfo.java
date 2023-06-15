package kr.co.yedaham.tablet.restserver.restserver.model.fun;

import lombok.*;

@AllArgsConstructor
@Data
public class FunProgressInfo {
    private String regDateTime;
    private String arrvDateTime;
    private String deadDateTime;
    private String firstCallDate;
    private String mortuarySetDate;
    private String diposItemArrvDate;
    private String counselDateTime;
    private String distbArrvHopeDate;
    private String distbRealArrvDate;
    private String caskDateTime;
    private String cortDateTime;
    private String funStartDateTime;
    private String funEndDateTime;
    private String distbDelayType;
    private String funDays;
    private String prodArrvDateTime;
    private String cortSupYn;
    private String cortSupNcd;
}
