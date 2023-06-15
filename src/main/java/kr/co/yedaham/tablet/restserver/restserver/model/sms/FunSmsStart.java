package kr.co.yedaham.tablet.restserver.restserver.model.sms;

import lombok.AllArgsConstructor;
import lombok.Data;

@AllArgsConstructor
@Data
public class FunSmsStart {
    private String empNm;
    private String funCtrlNo;
    private String deadNm;
    private String sProdNm;
    private String funNm;
    private String caskDatetime;
    private String cortDatetime;
    private String cremaBizNm;
    private String constReserDatetime;
    private String constBizNm;
    private String busBizNm;
    private String limBizNm;
}
