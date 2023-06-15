package kr.co.yedaham.tablet.restserver.restserver.model.fun;

import lombok.AllArgsConstructor;
import lombok.Data;

@AllArgsConstructor
@Data
public class FunSmsInfo {
    private String smsType;
    private String funno;
    private String smsContents;
    private String fdPhone;
}
