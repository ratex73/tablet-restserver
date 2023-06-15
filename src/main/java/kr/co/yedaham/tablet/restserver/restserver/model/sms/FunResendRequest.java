package kr.co.yedaham.tablet.restserver.restserver.model.sms;


import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class FunResendRequest {
    private String funCtrlNo;
    private String empNm;
    private String empPhone;
    private String reason;
}
