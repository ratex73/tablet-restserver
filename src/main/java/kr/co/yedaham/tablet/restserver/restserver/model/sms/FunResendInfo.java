package kr.co.yedaham.tablet.restserver.restserver.model.sms;


import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class FunResendInfo {
    private String funCtrlNo;
    private String empNm;
    private String empPhone;
    private String reason;
    private String otp;
}
