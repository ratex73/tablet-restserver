package kr.co.yedaham.tablet.restserver.restserver.model.fun;

import lombok.AllArgsConstructor;
import lombok.Data;

@AllArgsConstructor
@Data
public class FunSmsEmpList {
    private String empNo;
    private String empNm;
    private String telNo;
    private String depCd;
    private String depNm;
}
