package kr.co.yedaham.tablet.restserver.restserver.model.funmessage;


import lombok.*;

@Data
@AllArgsConstructor
public class FunMessageSigninInfo {
    private String empNo;
    private String empName;
    private String cellPhoneNo;
    private String otp;
    private String token;
    private String role;
    private String addr;
    private String mfsUserId;
}
