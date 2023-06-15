package kr.co.yedaham.tablet.restserver.restserver.model.fun;

import lombok.AllArgsConstructor;
import lombok.Data;

@AllArgsConstructor
@Data
public class BurialInfo {
    private String funno;
    private String funMeth;
    private String burialMeth;
    private String funAbre;
    private String cremaBizCd;
    private String reserDate;
    private String reserTime;
    private String reserMin;
    private String revYn;
    private String enshMeth;
    private String constBizCd;
    private String cemeZipCode;
    private String cemeAddr1;
    private String cemeAddr2;
    private String cemeType;
}
