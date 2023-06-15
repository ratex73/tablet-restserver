package kr.co.yedaham.tablet.restserver.restserver.model.mourning;

import lombok.Data;

import java.time.LocalDateTime;

@Data
public class MourningInfo {
    private MourningId mourningId;
    private String distbArrvHopeDate;
    private String distbRealArrvDate;
    private String distbArrvDiffMin;
    private char useYn;
    private char delYn;
    private String regId;
    private LocalDateTime regDate;
    private String updateId;
    private LocalDateTime updateDate;
    private String distbDelayType;
    private String firstCallDt;
    private String mortuarySetDt;
    private String disposItemArrvDt;
    private String meetCounselDt;
}
